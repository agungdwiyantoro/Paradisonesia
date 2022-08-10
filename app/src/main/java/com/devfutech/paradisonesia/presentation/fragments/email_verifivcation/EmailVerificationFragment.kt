package com.devfutech.paradisonesia.presentation.fragments.email_verifivcation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.devfutech.paradisonesia.R
import com.devfutech.paradisonesia.databinding.EmailVerificationFragmentBinding
import com.devfutech.paradisonesia.external.extension.visible
import com.devfutech.paradisonesia.presentation.base.BaseFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class EmailVerificationFragment : BaseFragment() {

    private val binding: EmailVerificationFragmentBinding by lazy {
        EmailVerificationFragmentBinding.inflate(layoutInflater)
    }
    private val viewModel by viewModels<EmailVerificationViewModel>()

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
    }

    private fun setupAction() {
        binding.apply {
            appBar.ivBack.setOnClickListener {
                findNavController().navigateUp()
            }
        }
    }

    private fun setupView() {
        binding.apply {
            appBar.tvTitle.text = resources.getString(R.string.label_account_verification)
            tvSendEmailVerification.text = resources.getString(R.string.label_account_verification_instruction,Firebase.auth.currentUser?.email)
            appBar.ivBack.visible()
        }
    }
}