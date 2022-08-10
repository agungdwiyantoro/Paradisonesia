package com.devfutech.paradisonesia.presentation.fragments.forgot_password

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.devfutech.paradisonesia.R
import com.devfutech.paradisonesia.databinding.ForgotPasswordFragmentBinding
import com.devfutech.paradisonesia.external.extension.visible
import com.devfutech.paradisonesia.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ForgotPasswordFragment : BaseFragment() {

    private val binding:ForgotPasswordFragmentBinding by lazy {
        ForgotPasswordFragmentBinding.inflate(layoutInflater)
    }
    private val viewModel by viewModels<ForgotPasswordViewModel>()

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
            appBar.tvTitle.text = resources.getString(R.string.label_forgot_password)
            appBar.ivBack.visible()
        }
    }

}