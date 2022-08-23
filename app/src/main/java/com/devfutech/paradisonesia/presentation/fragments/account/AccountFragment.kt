package com.devfutech.paradisonesia.presentation.fragments.account

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
import com.devfutech.paradisonesia.databinding.AccountFragmentBinding
import com.devfutech.paradisonesia.di.GoogleSignInModule
import com.devfutech.paradisonesia.external.Resource
import com.devfutech.paradisonesia.external.extension.snackBar
import com.devfutech.paradisonesia.external.extension.toast
import com.devfutech.paradisonesia.external.utils.FileUtils.safeNavigate
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
                        binding.root.snackBar(result.error)
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
                }
            }
        }.launchIn(lifecycleScope)
    }

    private fun setupAction() {
        binding.apply {
            tvRegisterAsMerchant.setOnClickListener {
                if (Firebase.auth.currentUser?.isEmailVerified == true) {
                    viewModel.checkStatus()
                } else {
                    requireContext().toast("Email belum diverifikasi")
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
                }
            })

            llEditProfile.setOnClickListener({
                findNavController().safeNavigate(R.id.action_accountFragment_to_editProfile)
            })
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
            }
        }
    }
}