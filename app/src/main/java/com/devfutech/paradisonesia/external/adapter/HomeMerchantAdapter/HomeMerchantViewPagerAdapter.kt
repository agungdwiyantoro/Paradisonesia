package com.devfutech.paradisonesia.external.adapter.HomeMerchantAdapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.devfutech.paradisonesia.presentation.fragments.signin.SigninFragment
import com.devfutech.paradisonesia.presentation.fragments.signup.SignupFragment

private const val NUM_TABS = 3

class HomeMerchantViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int {
        return NUM_TABS
    }

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> return SigninFragment()// CatFragment()
            1 -> return SignupFragment()
        }
        return SigninFragment()
    }
}