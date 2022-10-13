package com.devfutech.paradisonesia.presentation.fragments.signin

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.chuckerteam.chucker.BuildConfig
import com.devfutech.paradisonesia.R
import com.devfutech.paradisonesia.data.local.preferences.AuthPreference
import com.devfutech.paradisonesia.databinding.SigninFragmentBinding
import com.devfutech.paradisonesia.external.Resource
import com.devfutech.paradisonesia.external.config.BaseConfig
import com.devfutech.paradisonesia.external.extension.*
import com.devfutech.paradisonesia.presentation.base.BaseFragment
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import com.midtrans.sdk.corekit.models.snap.TransactionResult
import com.midtrans.sdk.corekit.core.MidtransSDK
import com.midtrans.sdk.uikit.SdkUIFlowBuilder
import com.midtrans.sdk.corekit.models.BillingAddress
import com.midtrans.sdk.corekit.models.CustomerDetails
import com.midtrans.sdk.corekit.models.ShippingAddress
import com.midtrans.sdk.corekit.core.TransactionRequest
import com.midtrans.sdk.corekit.models.ItemDetails
import com.devfutech.paradisonesia.external.utils.FileUtils.safeNavigate
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.flow.collect
import timber.log.Timber


@AndroidEntryPoint
class SigninFragment : BaseFragment(){

    private val binding: SigninFragmentBinding by lazy {
        SigninFragmentBinding.inflate(layoutInflater)
    }
    private val viewModel: SigninViewModel by viewModels()

    private val callbackManager by lazy {
        CallbackManager.Factory.create()
    }

    @Inject
    lateinit var googleSignInClient: GoogleSignInClient

    val firebaseAuth = FirebaseAuth.getInstance()

    @Inject
    lateinit var authPreference: AuthPreference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        setupAction()
        setupObserve()
        setupFacebook()
        midtransInit()
    }

    private fun setupView() {
        binding.apply {
            appBar.tvTitle.text = resources.getString(R.string.label_login)
            appBar.ivBack.visible()
        }
    }

    private fun setupFacebook() {
        binding.btnFacebook.setPermissions("email", "public_profile")
        binding.btnFacebook.fragment = this
        binding.btnFacebook.registerCallback(callbackManager, object :
            FacebookCallback<LoginResult> {
            override fun onSuccess(loginResult: LoginResult) {
                handleFacebookAccessToken(loginResult.accessToken)
            }

            override fun onCancel() {
                requireContext().toast("Cancel Facebook Login")
            }

            override fun onError(error: FacebookException) {
                requireContext().toast(error.message)
            }
        })
    }

    private fun handleFacebookAccessToken(accessToken: AccessToken) {
        viewModel.firebaseAuthWithProvider(
            idToken = accessToken.token,
            isGoogle = false
        )
    }

    private fun setupObserve() {
        lifecycleScope.launchWhenStarted {
            viewModel.googleSignIn.collect { result ->
                when (result) {
                    is Resource.Loading -> dialogLoading.show()
                    is Resource.Failure -> {
                        dialogLoading.dismiss()
                        navigationTo(
                            R.id.action_global_dialog_messgae,
                            argsBundleDialog(result.error, "Failure Signin", R.raw.error)
                        )
                    }
                    is Resource.Success -> {
                        dialogLoading.dismiss()
                        result.data?.let {
                            binding.apply {
                                etEmail.text?.clear()
                                etPassword.text?.clear()
                            }
                            findNavController().navigateUp()
                        }
                    }
                    else -> {}
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callbackManager.onActivityResult(requestCode, resultCode, data)
    }

    private fun setupAction() {
        binding.apply {
            ivGoogle.setOnClickListener {
                resultLauncher.launch(googleSignInClient.signInIntent)
            }
            btnLogin.setOnClickListener {
                loginRequest()
                //mPermissionResult.launch(Manifest.permission.READ_PHONE_STATE)
            }

            tvSignup.setOnClickListener {
                navigationTo(R.id.action_signinFragment_to_signupFragment)
            }
            ivFacebook.setOnClickListener {
                btnFacebook.performClick()
            }
            appBar.ivBack.setOnClickListener {
                findNavController().navigateUp()
            }
        }
    }

    private val mPermissionResult =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                startTransaction()
            } else {
                binding.root.snackBar("Permission ditolak")
            }
        }

    private fun startTransaction() {
        //TODO FORAMT ORDER ID {{invoice_number}}-{{payment_id}}
        val transactionRequest = TransactionRequest("sadas", 7500.0)

        val customerDetails = CustomerDetails()
        customerDetails.customerIdentifier = "budi-6789"
        customerDetails.phone = "08123456789"
        customerDetails.firstName = "Budi"
        customerDetails.lastName = "Utomo"
        customerDetails.email = "budi@utomo.com"

        val shippingAddress = ShippingAddress()
        shippingAddress.address = "Jalan Andalas Gang Sebelah No. 1"
        shippingAddress.city = "Jakarta"
        shippingAddress.postalCode = "10220"
        customerDetails.shippingAddress = shippingAddress

        val billingAddress = BillingAddress()
        billingAddress.address = "Jalan Andalas Gang Sebelah No. 1"
        billingAddress.city = "Jakarta"
        billingAddress.postalCode = "10220"
        customerDetails.billingAddress = billingAddress

        transactionRequest.setCustomerDetails(customerDetails)

        val itemDetails1 = ItemDetails("1", 5000.0, 1, "Test 1")
        val itemDetails2 = ItemDetails("2", 2500.0, 1, "Test 2")

        val itemDetailsList: ArrayList<ItemDetails> = ArrayList()
        itemDetailsList.add(itemDetails1)
        itemDetailsList.add(itemDetails2)

        transactionRequest.itemDetails = itemDetailsList
        MidtransSDK.getInstance().transactionRequest = transactionRequest
        MidtransSDK.getInstance().startPaymentUiFlow(requireContext())
    }

    private fun midtransInit() {
        SdkUIFlowBuilder.init()
            .setClientKey(BaseConfig().getMidtransClientKeyStagingCustomer()) // client_key is mandatory
            .setContext(requireContext()) // context is mandatory
            .setTransactionFinishedCallback { result ->
                if (result.response != null) {
                    when (result.status) {
                        TransactionResult.STATUS_SUCCESS -> binding.root.snackBar(
                            "Transaction Finished. ID: " + result.response.transactionId,
                        )
                        TransactionResult.STATUS_PENDING -> binding.root.snackBar(
                            "Transaction Pending. ID: " + result.response.transactionId
                        )
                        TransactionResult.STATUS_FAILED -> binding.root.snackBar(
                            "Transaction Failed. ID: " + result.response.transactionId
                                .toString() + ". Message: " + result.response
                                .statusMessage
                        )
                    }
                    result.response.validationMessages
                } else if (result.isTransactionCanceled) {
                    binding.root.snackBar("Transaction Canceled")
                } else {
                    if (result.status.equals(TransactionResult.STATUS_INVALID,true)) {
                        binding.root.snackBar("Transaction Invalid")
                    } else {
                        binding.root.snackBar("Transaction Finished with failure.")
                    }
                }
            } // set transaction finish callback (sdk callback)
            .setMerchantBaseUrl("${BaseConfig().getBaseUrlStagingCustomer()}transactions/") //set merchant url (required)
            .enableLog(BuildConfig.DEBUG) // enable sdk log (optional)
//            .setColorTheme(
//                CustomColorTheme(
//                    "#FFE51255",
//                    "#B61548",
//                    "#FFE51255"
//                )
//            ) // set theme. it will replace theme on snap theme on MAP ( optional)
            .setLanguage("id") //`en` for English and `id` for Bahasa
            .buildSDK()
    }

    private fun loginRequest() {
        binding.apply {
            val validation = arrayOfNulls<Boolean>(2)
            validation[0] = if (ilEmail.inputError(
                    etEmail.text.toString().trim(),
                    resources.getString(
                        R.string.empty_fields,
                        resources.getString(R.string.label_email)
                    )
                )
            ) {
                if (!etEmail.text.toString().trim().isEmailValid()) {
                    ilEmail.error = resources.getString(
                        R.string.wrong_format,
                        resources.getString(R.string.label_email)
                    )
                    false
                } else {
                    true
                }
            } else {
                false
            }
            validation[1] = if (ilPassword.inputError(
                    etPassword.text.toString().trim(),
                    resources.getString(
                        R.string.empty_fields,
                        resources.getString(R.string.label_password)
                    )
                )
            ) {
                if (etPassword.text.toString().trim().length < 8) {
                    ilPassword.error = resources.getString(
                        R.string.minimum_characters,
                        resources.getString(R.string.label_password)
                    )
                    false
                } else {
                    true
                }
            } else {
                false
            }

            if (!validation.contains(false)) {
                viewModel.signInAuth(
                    email = etEmail.text.toString().trim(),
                    password = etPassword.text.toString().trim()
                )
            }
        }
    }

    private val resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val task: Task<GoogleSignInAccount> =
                    GoogleSignIn.getSignedInAccountFromIntent(result.data)
                handleSignInResult(task)
            }
        }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account: GoogleSignInAccount? = completedTask.getResult(ApiException::class.java)
            if(account!=null) {
                //UpdateUI(account)
                viewModel.firebaseAuthWithProvider(
                    idToken = account.idToken!!,
                    isGoogle = true
                ).also {
                   // authPreference.setToken(account.idToken!!)
                    //SavedPreference.setToken(requireContext(), account.idToken.toString())
                    Timber.tag("JANCOOOK").d("acc " + account.idToken)
                   // Timber.tag("JANCOOOK2").d("lolok" + SavedPreference.getToken(requireContext()))
                    //requireContext().toast(resources.getString(R.string.signed_in) + authPreference.getToken())
                }.also {
                    findNavController().safeNavigate(R.id.action_signinFragment_to_accountFragment)
                }
            }
        } catch (e: ApiException) {
            binding.root.snackBar(e.message)
        }
    }

    private fun UpdateUI(account: GoogleSignInAccount){
        val credential = GoogleAuthProvider.getCredential(account.idToken,null)
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener({
                task ->
                if(task.isSuccessful){
                   // SavedPreference.setEmail(requireContext(), account.email.toString())
                   // SavedPreference.setUsername(requireContext(), account.displayName.toString())
                    requireContext().toast(getString(R.string.signed_in)).also {
                        //findNavController().navigate(R.id.action_signinFragment_to_accountFragment)
                        findNavController().safeNavigate(R.id.action_signinFragment_to_accountFragment)
                    }
                }
            })
    }
}