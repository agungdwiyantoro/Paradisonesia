package com.devfutech.paradisonesia.presentation.fragments.inbox

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.devfutech.paradisonesia.R
import com.devfutech.paradisonesia.databinding.InboxFragmentBinding
import com.devfutech.paradisonesia.presentation.base.BaseFragment
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InboxFragment : BaseFragment() {

    private val binding:InboxFragmentBinding by lazy {
        InboxFragmentBinding.inflate(layoutInflater)
    }
    private val viewModel by viewModels<InboxViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        setupAction()
    }

    private fun setupAction() {
        binding.apply {
            includedNonLogin.btnGoToLoginPage.setOnClickListener {
                findNavController().navigate(R.id.action_inboxFragment_to_signinFragment)
            }
        }
    }

    private fun setupView() {
        binding.apply {
            vfInbox.displayedChild = if (Firebase.auth.currentUser == null) 1 else 0
            includedEmptyInbox.root.visibility = View.VISIBLE
        }
    }

}