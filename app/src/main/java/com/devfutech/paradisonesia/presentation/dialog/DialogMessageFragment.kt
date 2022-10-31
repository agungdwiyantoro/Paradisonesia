package com.devfutech.paradisonesia.presentation.dialog

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.devfutech.paradisonesia.R
import com.devfutech.paradisonesia.databinding.DialogMessageFragmentBinding
import com.devfutech.paradisonesia.presentation.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DialogMessageFragment : DialogFragment() {

    private val binding: DialogMessageFragmentBinding by lazy {
        DialogMessageFragmentBinding.inflate(layoutInflater)
    }

    //private val args by navArgs<DialogMessageFragmentArgs>()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        Dialog(requireContext(), R.style.DialogTheme)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
         //   lottieAnimation.setAnimation(args.animation)
         //   txtTitleMessage.text = args.title
         //   txtBodyMessage.text = args.body
            btDialogOk.setOnClickListener{
                (activity as MainActivity).logout()
            }
        }
    }

    override fun onDismiss(dialog: DialogInterface) {
        //findNavController().navigateUp()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        dismiss()
    }


    companion object {

        const val TAG = "SimpleDialog"

        private const val KEY_TITLE = "KEY_TITLE"
        private const val KEY_SUBTITLE = "KEY_SUBTITLE"

        fun newInstance(title: String, subTitle: String): DialogMessageFragment {
            val args = Bundle()
            args.putString(KEY_TITLE, title)
            args.putString(KEY_SUBTITLE, subTitle)
            val fragment = DialogMessageFragment()
            fragment.arguments = args
            return fragment
        }

    }
}