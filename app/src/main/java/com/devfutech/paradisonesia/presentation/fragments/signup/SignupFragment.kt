package com.devfutech.paradisonesia.presentation.fragments.signup

import android.app.Activity
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.devfutech.paradisonesia.R
import com.devfutech.paradisonesia.databinding.SignupFragmentBinding
import com.devfutech.paradisonesia.external.Resource
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
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@AndroidEntryPoint
class SignupFragment : BaseFragment() {

    private val binding: SignupFragmentBinding by lazy {
        SignupFragmentBinding.inflate(layoutInflater)
    }

    private val viewModel by viewModels<SignupViewModel>()

    private val callbackManager by lazy {
        CallbackManager.Factory.create()
    }

    @Inject
    lateinit var googleSignInClient: GoogleSignInClient

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
    }

    private fun setupView() {
        binding.apply {
            etEmail.clearInput(ilEmail)
            etPassword.clearInput(ilPassword)
            appBar.tvTitle.text = resources.getString(R.string.label_register)
            appBar.ivBack.visible()
            tvLegal.apply {
                movementMethod = LinkMovementMethod.getInstance()
                setLinkTextColor(ContextCompat.getColor(requireContext(),R.color.blue_0063a7))
            }
        }
    }

    private fun setupObserve() {
        lifecycleScope.launchWhenStarted {
            viewModel.signUp.collect { result ->
                when (result) {
                    is Resource.Loading -> dialogLoading.show()
                    is Resource.Failure -> {
                        dialogLoading.dismiss()
                        navigationTo(
                            R.id.action_global_dialog_messgae,
                            argsBundleDialog(
                                body = result.error,
                                title = "Failure Signup",
                                animation = R.raw.error
                            )
                        )
                    }
                    is Resource.Success -> {
                        result.data?.let { user ->
                            if (!user.isEmailVerified) {
                                sendEmailVerification(result.data)
                            } else {
                                dialogLoading.dismiss()
                                navigationTo(
                                    R.id.action_global_dialog_messgae, argsBundleDialog(
                                        body = "Let's journey inside this apps",
                                        title = "Succesfully",
                                        animation = R.raw.mark_success
                                    )
                                )
                            }
                        }
                    }
                    else -> {}
                }
            }
        }

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
                            findNavController().navigateUp()
                        }
                    }
                    else -> {}
                }
            }
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
            activity = requireActivity(),
            idToken = accessToken.token,
            isGoogle = false
        )
    }

    private fun sendEmailVerification(user: FirebaseUser) {
        user.sendEmailVerification()
            .addOnCompleteListener { task ->
                dialogLoading.dismiss()
                if (task.isSuccessful) {
                    navigationTo(
                        R.id.action_signupFragment_to_emailVerificationFragment,
                        popUpToHome = true
                    )
                } else {
                    navigationTo(
                        R.id.action_global_dialog_messgae, argsBundleDialog(
                            body = "Failed send email verification to ${user.email}",
                            title = "Failure Signup",
                            animation = R.raw.error
                        )
                    )
                }
            }
    }

    private fun setupAction() {
        binding.apply {
            btnSignup.setOnClickListener {
                val validation = arrayOfNulls<Boolean>(2)
                validation[0] = if (ilEmail.inputError(
                        etEmail.text.toString().trim(), resources.getString(
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
                        etPassword.text.toString().trim(), resources.getString(
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
                    viewModel.createFirebaseUser(
                        email = etEmail.text.toString().trim(),
                        password = etPassword.text.toString().trim()
                    )
                }
            }
            appBar.ivBack.setOnClickListener {
                findNavController().navigateUp()
            }

            ivFacebook.setOnClickListener {
                btnFacebook.performClick()
            }
            ivGoogle.setOnClickListener {
                resultLauncher.launch(googleSignInClient.signInIntent)
            }

            tvSignin.setOnClickListener {
                findNavController().navigateUp()
            }
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)
            viewModel.firebaseAuthWithProvider(
                activity = requireActivity(),
                idToken = account.idToken!!,
                isGoogle = true
            )
        } catch (e: ApiException) {
            binding.root.snackBar(e.message)
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

}