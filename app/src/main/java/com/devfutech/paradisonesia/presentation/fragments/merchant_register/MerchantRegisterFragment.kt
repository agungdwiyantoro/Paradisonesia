package com.devfutech.paradisonesia.presentation.fragments.merchant_register

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.net.Uri
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
import coil.loadAny
import com.devfutech.paradisonesia.R
import com.devfutech.paradisonesia.databinding.MerchantRegisterFragmentBinding
import com.devfutech.paradisonesia.external.Resource
import com.devfutech.paradisonesia.external.extension.snackBar
import com.devfutech.paradisonesia.external.extension.visible
import com.devfutech.paradisonesia.external.utils.FileUtils
import com.devfutech.paradisonesia.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.MultipartBody
import okhttp3.MultipartBody.Part.Companion.createFormData
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File


@AndroidEntryPoint
class MerchantRegisterFragment : BaseFragment() {

    companion object {
        const val REQUEST_CODE = "101"
    }

    private val binding: MerchantRegisterFragmentBinding by lazy {
        MerchantRegisterFragmentBinding.inflate(layoutInflater)
    }
    private val viewModel: MerchantRegisterViewModel by viewModels()

    private var code = 0
    private var ktp: MultipartBody.Part? = null
    private var npwp: MultipartBody.Part? = null
    private var siup: MultipartBody.Part? = null

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
        setMerchantRegister()
    }

    private fun setMerchantRegister() {
        lifecycleScope.launchWhenStarted {
            viewModel.submit.collect { result ->
                when (result) {
                    is Resource.Loading -> dialogLoading.show()
                    is Resource.Failure -> {
                        dialogLoading.dismiss()
                        binding.root.snackBar(result.error)
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

    private fun setupAction() {
        binding.apply {
            appBar.ivBack.setOnClickListener { findNavController().navigateUp() }
            ivIdentityCard.setOnClickListener {
                code = 0
                mPermissionResult.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
            }
            /*
            ivNpwp.setOnClickListener {
                code = 1
                mPermissionResult.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
            }
            ivSiup.setOnClickListener {
                code = 2
                mPermissionResult.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
            }

             */
            btnSubmit.setOnClickListener {
                /*
                viewModel.merchantRegister(
                    payload = mapOf(
                        "name" to etMerchantName.text?.trim().toString(),
                        "address" to etMerchantAddress.text?.trim().toString(),
                        "description" to etMerchantDescription.text?.trim().toString(),
                    )/*, ktp = ktp.toString(), npwp = npwp, siup = siup*/
                    , mapOf("ktp_number" to etMerchantKTPNumber.text?.trim().toString().toLong())
                )

                 */
                findNavController().navigate(R.id.action_merchantRegisterFragment_to_merchantReviewedStatusFragment)
            }
        }
    }

    private val mPermissionResult =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                showFileChooser()
            } else {
                binding.root.snackBar("Permission ditolak")
            }
        }

    private fun showFileChooser() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        resultLauncher.launch(intent)
    }

    private val resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val uri = result.data?.data
                println("Uri : $uri")
                when (code) {
                    0 -> {
                        binding.ivIdentityCard.loadAny(uri)
                        FileUtils.textRecognizer(requireContext(), uri!!, binding.etMerchantKTPNumber)
                        binding.ilMerchantKTPNumber.visibility = View.VISIBLE
                        uri?.let {
                            ktp = prepareFilePart("ktp", it)
                        }
                    }
                    /*
                    1 -> {
                        binding.ivNpwp.loadAny(uri)
                        uri?.let {
                            npwp = prepareFilePart("npwp", it)
                        }
                    }
                    else -> {
                        binding.ivSiup.loadAny(uri)
                        uri?.let {
                            siup = prepareFilePart("siup", it)
                        }
                    }
                     */
                }
            }
        }

    private fun setupView() {
        binding.apply {
            appBar.ivBack.visible()
            appBar.tvTitle.text = resources.getString(R.string.label_merchant_submission)
            tvLegal.apply {
                movementMethod = LinkMovementMethod.getInstance()
                setLinkTextColor(ContextCompat.getColor(requireContext(), R.color.blue_0063a7))
            }
        }
    }

    private fun prepareFilePart(partName: String, fileUri: Uri): MultipartBody.Part? {
        val file: File? = FileUtils.getFile(requireContext(), fileUri)
        file?.let {
            // create RequestBody instance from file
            val requestFile: RequestBody = file.asRequestBody(MultipartBody.FORM)

            // MultipartBody.Part is used to send also the actual file name
            return createFormData(partName, file.name, requestFile)
        }
        return null
    }
}