package com.devfutech.paradisonesia.presentation.fragments.edit_profile

import android.os.Bundle
import com.devfutech.paradisonesia.presentation.base.BaseFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.view.get
import com.devfutech.paradisonesia.R
import com.devfutech.paradisonesia.databinding.EditProfileBinding
import com.devfutech.paradisonesia.external.utils.FileUtils.getDate
import com.devfutech.paradisonesia.external.utils.FileUtils.simpleSpinnerAdapter
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
        setupView()
        setupAction()

    }

    fun setupView(){
        binding.apply {
            val items = resources.getStringArray(R.array.gender_spinner)
            spGender.adapter = simpleSpinnerAdapter(requireContext(), items)
        }
    }

    fun setupAction(){
        binding.apply {
            etCalendarPick.setOnClickListener({

            })

            btConfirm.setOnClickListener({
                etCalendarPick.setText(dpBirthdate.getDate().toString())
                llcompDatePicker.visibility = View.GONE
            }
        )
    }
}

}