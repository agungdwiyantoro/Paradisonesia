package com.devfutech.paradisonesia.presentation.base

import android.app.Dialog
import android.os.Bundle
import com.devfutech.paradisonesia.R
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

/**
 * Created by devfutech on 10/8/2021.
 */
open class BaseBottomSheet : BottomSheetDialogFragment() {
    override fun getTheme(): Int = R.style.BottomSheetDialogTheme

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        BottomSheetDialog(requireContext(), theme)

}