package com.devfutech.paradisonesia.presentation.fragments.edit_profile

import android.os.Bundle
import com.devfutech.paradisonesia.presentation.base.BaseFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.view.get
import androidx.navigation.fragment.findNavController
import coil.load
import com.devfutech.paradisonesia.R
import com.devfutech.paradisonesia.databinding.EditProfileBinding
import com.devfutech.paradisonesia.external.extension.inputError
import com.devfutech.paradisonesia.external.extension.isEmailValid
import com.devfutech.paradisonesia.external.utils.FileUtils.getDate
import com.devfutech.paradisonesia.external.utils.FileUtils.safeNavigate
import com.devfutech.paradisonesia.external.utils.FileUtils.simpleSpinnerAdapter
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
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

            titleBar.ivBack.visibility = View.VISIBLE
            titleBar.tvTitle.setText(resources.getString(R.string.edit_profile_data))
        }
    }

    fun setupAction(){
        binding.apply {
            ivIcDate.setOnClickListener({
                llcompDatePicker.visibility = View.VISIBLE
            })

            btConfirm.setOnClickListener({
                etCalendarPick.setText(dpBirthdate.getDate().toString())
                llcompDatePicker.visibility = View.GONE
            })

            titleBar.ivBack.setOnClickListener({
                findNavController().safeNavigate(R.id.action_editProfile_to_accountFragment)
            })

            btSave.setOnClickListener({
                val validation = arrayOfNulls<Boolean>(3)
                validation[0] = if (ilEmail.inputError(
                        etFullNameValue.text.toString().trim(), resources.getString(
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
            })

            Firebase.auth.currentUser?.let { account ->
                ivProfile.load(account.photoUrl) {
                    crossfade(true)
                    placeholder(R.drawable.ic_image_not_available)
                    error(R.drawable.ic_image_not_available)
                }
            }
        }
    }


}