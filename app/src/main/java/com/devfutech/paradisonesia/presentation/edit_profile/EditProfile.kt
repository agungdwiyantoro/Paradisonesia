package com.devfutech.paradisonesia.presentation.edit_profile

import android.opengl.Visibility
import android.os.Bundle
import com.devfutech.paradisonesia.databinding.EditProfileBinding
import com.devfutech.paradisonesia.presentation.base.BaseFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditProfile : BaseFragment(){

    private val binding: EditProfileBinding by lazy{
        EditProfileBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAction()

    }

    fun setupAction(){
        binding.apply {
            etCalendarPick.setOnClickListener{
                dpBirthdate.visibility = View.VISIBLE
            }
        }
    }
}