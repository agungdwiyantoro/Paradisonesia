package com.devfutech.paradisonesia.presentation.fragments.account

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import coil.load
import com.devfutech.paradisonesia.BuildConfig
import com.devfutech.paradisonesia.R
import com.devfutech.paradisonesia.data.local.preferences.IsMerchantPreference
import com.devfutech.paradisonesia.databinding.AccountFragmentBinding
import com.devfutech.paradisonesia.di.GoogleSignInModule
import com.devfutech.paradisonesia.external.Resource
import com.devfutech.paradisonesia.external.extension.snackBar
import com.devfutech.paradisonesia.external.extension.toast
import com.devfutech.paradisonesia.external.utils.FileUtils.safeNavigate
import com.devfutech.paradisonesia.presentation.MainActivity
import com.devfutech.paradisonesia.presentation.base.BaseFragment
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.Component
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class AccountFragment : BaseFragment() {

    @Inject
    lateinit var googleSignInClient: GoogleSignInClient

    private val binding: AccountFragmentBinding by lazy {
        AccountFragmentBinding.inflate(layoutInflater)
    }

    private val viewModel by viewModels<AccountViewModel>()

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
        setStatusMerchant()
    }

    private fun setStatusMerchant() {
        viewModel.status.onEach {
            it.getContentIfNotHandled()?.let { result ->
                when (result) {
                    is Resource.Loading -> dialogLoading.show()
                    is Resource.Failure -> {
                        dialogLoading.dismiss()
                        Timber.tag("AccountFragment").d("re " + result.error)
                        //binding.root.snackBar(result.error)
                        if (result.error == "Belum terdaftar") {
                            findNavController().navigate(R.id.action_accountFragment_to_merchantRegisterFragment)
                        }
                    }
                    is Resource.Success -> {
                        dialogLoading.dismiss()
                        result.data?.let { data ->
                            findNavController().navigate(
                                AccountFragmentDirections.actionAccountFragmentToMerchantReviewedStatusFragment(
                                    data
                                )
                            )
                        }
                    }
                    else -> {}
                }
            }
        }.launchIn(lifecycleScope)
    }

    private fun setupAction() {
        binding.apply {
            tvRegisterAsMerchant.setOnClickListener {
                /*
                if (Firebase.auth.currentUser?.isEmailVerified == true) {
                    viewModel.checkStatus()
                } else {
                    requireContext().toast("Email belum diverifikasi")
                }
                 */
                if(IsMerchantPreference(activity?.getSharedPreferences(IsMerchantPreference.IS_REGISTERED_MERCHANT, Context.MODE_PRIVATE)!!).getIsRegisteredMerchant()==false) {
                    findNavController().navigate(R.id.action_accountFragment_to_merchantRegisterFragment)
                }
                else if(IsMerchantPreference(activity?.getSharedPreferences(IsMerchantPreference.IS_REGISTERED_MERCHANT, Context.MODE_PRIVATE)!!).getIsRegisteredMerchant()==true
                    &&IsMerchantPreference(activity?.getSharedPreferences(IsMerchantPreference.IS_MERCHANT_PREFERENCE, Context.MODE_PRIVATE)!!).getIsMerchant()==true){
                    IsMerchantPreference(activity?.getSharedPreferences(IsMerchantPreference.IS_MERCHANT_PREFERENCE, Context.MODE_PRIVATE)!!).setIsMerchant(false)
                    tvRegisterAsMerchant.text = getString(R.string.label_switch_as_merchant)
                    (activity as MainActivity).setupActionMerchant(R.id.homeMerchantFragment, R.id.homeCustomerFragment)
                    (activity as MainActivity).setReviewVisibility(false)
                    (activity as MainActivity).orderThePosition()
                }
                else{
                    IsMerchantPreference(activity?.getSharedPreferences(IsMerchantPreference.IS_MERCHANT_PREFERENCE, Context.MODE_PRIVATE)!!).setIsMerchant(true)
                    tvRegisterAsMerchant.text = getString(R.string.label_switch_as_customer)
                    (activity as MainActivity).setupActionMerchant(R.id.homeCustomerFragment, R.id.homeMerchantFragment)
                    (activity as MainActivity).setReviewVisibility(true)
                    (activity as MainActivity).orderThePosition()
                }
            }
            includedNonLogin.btnGoToLoginPage.setOnClickListener {
                findNavController().navigate(R.id.action_accountFragment_to_signinFragment)
            }

            llSignOut.setOnClickListener({
                Firebase.auth.signOut()
                    .also {
                        googleSignInClient.signOut()
                    }
                    .also {
                        findNavController().navigate(R.id.action_accountFragment_to_signinFragment)
                    }.also {
                        requireContext().toast(getString(R.string.signed_out))
                    }.also {
                        IsMerchantPreference(activity?.getSharedPreferences(IsMerchantPreference.IS_MERCHANT_PREFERENCE, Context.MODE_PRIVATE)!!).setIsMerchant(false)
                        (activity as MainActivity).setupActionMerchant(R.id.homeMerchantFragment, R.id.homeCustomerFragment)
                        (activity as MainActivity).setReviewVisibility(false)
                    }
            })

            llEditProfile.setOnClickListener({
                findNavController().safeNavigate(R.id.action_accountFragment_to_editProfile)
            })

            llPrivacyPolicy.setOnClickListener{
                IsMerchantPreference(activity?.getSharedPreferences(IsMerchantPreference.IS_MERCHANT_PREFERENCE, Context.MODE_PRIVATE)!!).setIsMerchant(false)
                (activity as MainActivity).setupActionMerchant(R.id.homeMerchantFragment, R.id.homeCustomerFragment)
                (activity as MainActivity).setReviewVisibility(false)
            }
        }
    }

    private fun setupView() {
        Timber.tag("CurrentPengguna").d("XOmP" + FirebaseAuth.getInstance().currentUser)
        binding.apply {
            vfAccount.displayedChild = if (Firebase.auth.currentUser == null) 1 else 0
            titleBar.tvTitle.text = resources.getString(R.string.label_account)
            Firebase.auth.currentUser?.let { account ->
                ivProfile.load(account.photoUrl) {
                    crossfade(true)
                    placeholder(R.drawable.ic_image_not_available)
                    error(R.drawable.ic_image_not_available)
                }
                tvAccountGreetings.text = resources.getString(
                    R.string.label_account_greetings,
                    if (account.displayName.isNullOrEmpty()) account.email else account.displayName
                )
                tvEmail.text = account.email
                tvRegisterAsMerchant.isVisible = account.isEmailVerified
                tvVersionApps.text = BuildConfig.VERSION_NAME

                Timber.tag("ACCOUNT FRAGMENT").d("JAN " + IsMerchantPreference(activity?.getSharedPreferences(IsMerchantPreference.IS_REGISTERED_MERCHANT, Context.MODE_PRIVATE)!!).getIsRegisteredMerchant()
                + " , " + IsMerchantPreference(activity?.getSharedPreferences(IsMerchantPreference.IS_MERCHANT_PREFERENCE, Context.MODE_PRIVATE)!!).getIsMerchant())

                if(IsMerchantPreference(activity?.getSharedPreferences(IsMerchantPreference.IS_REGISTERED_MERCHANT, Context.MODE_PRIVATE)!!).getIsRegisteredMerchant()==false){
                    tvRegisterAsMerchant.text = getString(R.string.label_register_as_a_merchant)
                }

                else if(IsMerchantPreference(activity?.getSharedPreferences(IsMerchantPreference.IS_REGISTERED_MERCHANT, Context.MODE_PRIVATE)!!).getIsRegisteredMerchant()==true
                    &&IsMerchantPreference(activity?.getSharedPreferences(IsMerchantPreference.IS_MERCHANT_PREFERENCE, Context.MODE_PRIVATE)!!).getIsMerchant()==true){
                    tvRegisterAsMerchant.text = getString(R.string.label_switch_as_customer)
                }
                else{
                    tvRegisterAsMerchant.text = getString(R.string.label_switch_as_merchant)
                }


            }
        }
    }
}