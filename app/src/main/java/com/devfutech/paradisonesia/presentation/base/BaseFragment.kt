package com.devfutech.paradisonesia.presentation.base

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.devfutech.paradisonesia.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder

open class BaseFragment : Fragment() {

    protected val dialogLoading: AlertDialog by lazy {
        MaterialAlertDialogBuilder(requireContext(), R.style.DialogTheme)
            .setView(R.layout.layout_dialog_loading)
            .setCancelable(false)
            .setBackground(ColorDrawable(Color.TRANSPARENT))
            .create()
    }

    protected fun argsBundleDialog(body: String?, title: String, animation: Int) = bundleOf(
        "body" to body,
        "title" to title,
        "animation" to animation
    )

    private fun navOptions(popUpToHome: Boolean?): NavOptions {
        return NavOptions.Builder().apply {
            setEnterAnim(R.anim.nav_default_enter_anim)
            setExitAnim(R.anim.nav_default_exit_anim)
            setPopEnterAnim(R.anim.nav_default_pop_enter_anim)
            setPopExitAnim(R.anim.nav_default_pop_exit_anim)
            if (popUpToHome == true) setPopUpTo(R.id.homeCustomerFragment, false)
        }.build()
    }

    protected fun navigationTo(
        destination: Int,
        bundle: Bundle? = null,
        popUpToHome: Boolean? = false
    ) {
        findNavController().navigate(destination, bundle, navOptions(popUpToHome))
    }
}