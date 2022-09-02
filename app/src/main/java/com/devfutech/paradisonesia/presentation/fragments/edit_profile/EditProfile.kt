package com.devfutech.paradisonesia.presentation.fragments.edit_profile

import android.content.Intent
import android.os.Bundle
import com.devfutech.paradisonesia.presentation.base.BaseFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.text.isDigitsOnly
import androidx.core.view.get
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import coil.load
import com.devfutech.paradisonesia.R
import com.devfutech.paradisonesia.data.local.preferences.AuthPreference
import com.devfutech.paradisonesia.databinding.EditProfileBinding
import com.devfutech.paradisonesia.domain.model.user.Customer
import com.devfutech.paradisonesia.external.Resource
import com.devfutech.paradisonesia.external.extension.inputError
import com.devfutech.paradisonesia.external.extension.isEmailValid
import com.devfutech.paradisonesia.external.extension.snackBar
import com.devfutech.paradisonesia.external.utils.FileUtils.getDate
import com.devfutech.paradisonesia.external.utils.FileUtils.isDateValidFormat
import com.devfutech.paradisonesia.external.utils.FileUtils.safeNavigate
import com.devfutech.paradisonesia.external.utils.FileUtils.simpleSpinnerAdapter
import com.devfutech.paradisonesia.presentation.fragments.signin.SigninViewModel
import com.facebook.CallbackManager
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class EditProfile : BaseFragment(){

    private val binding: EditProfileBinding by lazy{
        EditProfileBinding.inflate(layoutInflater)
    }

    //private val viewModel by viewModels<EditProfileViewModel>()
    private val callbackManager by lazy {
        CallbackManager.Factory.create()
    }

    @Inject
    lateinit var googleSignInClient: GoogleSignInClient

    val firebaseAuth = FirebaseAuth.getInstance()

    @Inject
    lateinit var authPreference: AuthPreference

    lateinit var email: String
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
       // setupObserve()
    }

    fun setupView(){
        binding.apply {
            val items = resources.getStringArray(R.array.gender_spinner)
            spGender.adapter = simpleSpinnerAdapter(requireContext(), items)

            titleBar.ivBack.visibility = View.VISIBLE
            titleBar.tvTitle.setText(resources.getString(R.string.edit_profile_data))

            Timber.tag("GORILLA").d("TO " + AuthPreference.TOKEN)
        }
    }

    /*
    private fun setupObserve() {
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
                            binding.apply {
                                tieFullNameValue.text?.clear()
                                tieCalendarPickValue.text?.clear()
                                tieEmailValue.text?.clear()
                                tieAddressValue.text?.clear()
                                tiePhoneNumberValue.text?.clear()
                            }
                            findNavController().navigateUp()
                        }
                    }
                }
            }
        }
    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callbackManager.onActivityResult(requestCode, resultCode, data)

    }

*/
    fun setupAction(){
        binding.apply {
            ivIcDate.setOnClickListener({
                layoutCalendar.llcompDatePicker.visibility = View.VISIBLE
            })

            layoutCalendar.btConfirm.setOnClickListener({
                tieCalendarPickValue.setText( layoutCalendar.dpBirthdate.getDate())
                tieCalendarPickValue.setHint("")
                layoutCalendar.llcompDatePicker.visibility = View.GONE
            })

            titleBar.ivBack.setOnClickListener({
                findNavController().safeNavigate(R.id.action_editProfile_to_accountFragment)
            })

            btSave.setOnClickListener ({
                loginCheck()
            })

            Firebase.auth.currentUser?.let { account ->
                ivProfile.load(account.photoUrl) {
                    crossfade(true)
                    placeholder(R.drawable.ic_image_not_available)
                    error(R.drawable.ic_image_not_available)
                }

                tieEmailValue.setText(account.email)
                email = account.email!!
            }


        }
    }


    fun loginCheck(){
        binding.apply {
            val validation = arrayOfNulls<Boolean>(5)
            validation[0] = if (tilFullName.inputError(
                    tieFullNameValue.text.toString().trim(),
                    resources.getString(R.string.empty_fields,
                        resources.getString(R.string.label_full_name)
                    )
                )
            ) {
                if (tieFullNameValue.text.toString().contains(Regex("[^A-Za-z\\s]"))) {
                    tilFullName.error = resources.getString(
                        R.string.wrong_format,
                        resources.getString(R.string.label_full_name)
                    )
                    false
                } else {
                    true
                }
            } else {
                false
            }

            validation[1] = if (tilCalendarPick.inputError(
                    tieCalendarPickValue.text.toString().trim(),
                    resources.getString(R.string.empty_fields,
                        resources.getString(R.string.label_birthdate))
                )
            ) {
                true
            } else
            {
                false
            }

            validation[2] = if (tilAddress.inputError(
                    tieAddressValue.text.toString().trim(),
                    resources.getString(R.string.empty_fields,
                        resources.getString(R.string.label_address))
                )
            ) {
                true
            } else {
                false
            }

            validation[3] = if (tilEmail.inputError(
                    tieEmailValue.text.toString().trim(),
                    resources.getString(R.string.empty_fields,
                        resources.getString(R.string.label_email))
                )
            ) {
                if (!tieEmailValue.text.toString().trim().isEmailValid()) {
                    tilEmail.error = resources.getString(
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

            validation[4] = if (tilPhoneNumber.inputError(
                    tiePhoneNumberValue.text.toString().trim(),
                    resources.getString(R.string.empty_fields,
                        resources.getString(R.string.label_phone_number))
                )
            ) {
                if (tiePhoneNumberValue.text.toString().trim().length!=12) {
                    tilPhoneNumber.error = resources.getString(R.string.wrong_length_phone)
                    false
                } else {
                    true
                }
            } else {
                false
            }


            if (!validation.contains(false)) {

                /*
                viewModel.createFirebaseUser(
                    email = etEmail.text.toString().trim(),
                    password = etPassword.text.toString().trim()
                )


                viewModel.checkUserToServer(
                    name = tieFullNameValue.text.toString(),
                    email = email,
                    phone = tiePhoneNumberValue.text.toString(),
                    address = tieAddressValue.text.toString(),
                    gender = spGender.selectedItemPosition.toString(),
                    birth_date = tieCalendarPickValue.text.toString(),
                    image = "jskdjlfdjf"
                )
  */
                root.snackBar("PROFILE UPDATED")
            }
        }
    }
}