package com.devfutech.paradisonesia.presentation.fragments.booking

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.get
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.devfutech.paradisonesia.R
import com.devfutech.paradisonesia.databinding.BookingFragmentBinding
import com.devfutech.paradisonesia.presentation.base.BaseFragment
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.android.material.tabs.TabLayout

import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BookingFragment : BaseFragment() {

    private val binding:BookingFragmentBinding by lazy {
        BookingFragmentBinding.inflate(layoutInflater)
    }
    private val viewModel by viewModels<BookingViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       // setupView()
        setupAction()
    }

    private fun setupAction() {
        binding.apply {
            includedNonLogin.btnGoToLoginPage.setOnClickListener {
                findNavController().navigate(R.id.action_bookingFragment_to_signinFragment)
            }
        }
    }

    private fun setupView() {
        binding.apply {
           // vfBooking.displayedChild = if (Firebase.auth.currentUser == null) 1 else 0
        }
    }

    private fun setupTabLayout(){
        val selectedPosition = binding.tbLayout.selectedTabPosition


    }

}