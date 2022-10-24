package com.devfutech.paradisonesia.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.get
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.devfutech.paradisonesia.R
import com.devfutech.paradisonesia.data.local.preferences.IsMerchantPreference
import com.devfutech.paradisonesia.databinding.ActivityMainBinding
import com.devfutech.paradisonesia.external.extension.gone
import com.devfutech.paradisonesia.external.extension.toast
import com.devfutech.paradisonesia.external.extension.visible
import com.devfutech.paradisonesia.presentation.fragments.home_customer.HomeCustomerFragment
import com.devfutech.paradisonesia.presentation.fragments.home_merchant.HomeMerchantFragment
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private lateinit var navController: NavController
    private var doubleBackToExitPressedOnce = false
    private var topLeves: List<String> = listOf()

    override fun onCreate(savedInstanceState: Bundle?) {
      //  val splashScreen = installSplashScreen()

        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupView()

    }


    private fun setupView() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        navController = navHostFragment.navController
        binding.bnMain.setupWithNavController(navController)

     //   binding.bnMain.getMenu().clear()

        if(IsMerchantPreference(getSharedPreferences(IsMerchantPreference.IS_MERCHANT_PREFERENCE, Context.MODE_PRIVATE)).getIsMerchant()){
           // binding.bnMain.inflateMenu(R.menu.bottom_menu)
           // setupTopMenuCustomer()
           // setupActionCustomer()

            setReviewVisibility(true)
        }

        else {
           // binding.bnMain.inflateMenu(R.menu.bottom_menu_merchant)
            setReviewVisibility(false)

        }

        setupTopMenuMerchant()
        setupActionMerchant(false)
    }

    private fun setupTopMenuCustomer() {
        topLeves = listOf(
            resources.getString(R.string.label_home),
            resources.getString(R.string.label_booking),
            resources.getString(R.string.label_inbox),
            resources.getString(R.string.label_account)
        )
    }

    private fun setupTopMenuMerchant() {
        topLeves = listOf(
            resources.getString(R.string.label_home),
            resources.getString(R.string.label_review),
            resources.getString(R.string.label_booking),
            resources.getString(R.string.label_inbox),
            resources.getString(R.string.label_account)
        )
    }

    fun setReviewVisibility(visiblity : Boolean) {
        binding.bnMain.menu.findItem(R.id.merchantReviewFragment).setVisible(visiblity)
    }

    fun setCountBadgeInbox(count: Int) {
        binding.bnMain.getOrCreateBadge(R.id.inboxFragment).apply {
            number = count
            maxCharacterCount = 3
        }
    }

    fun setCountBadgeBooking(count: Int) {
        binding.bnMain.getOrCreateBadge(R.id.bookingFragment).apply {
            number = count
            maxCharacterCount = 3
        }
    }

    private fun setupActionCustomer() {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.homeCustomerFragment, R.id.bookingFragment, R.id.inboxFragment, R.id.accountFragment -> binding.bnMain.visible()
                else -> binding.bnMain.gone()
            }
        }
    }

    fun setupActionMerchant(isMerchant: Boolean) {
        if (isMerchant) {
            navController.addOnDestinationChangedListener { _, destination, _ ->
                when (destination.id) {
                    R.id.homeMerchantFragment, R.id.merchantReviewFragment, R.id.bookingFragment, R.id.inboxFragment, R.id.accountFragment -> binding.bnMain.visible()
                    else -> binding.bnMain.gone()
                }
            }
        } else {
//            binding.bnMain.menu.add(0, R.id.homeCustomerFragment, 0, R.string.label_home)
            navController.addOnDestinationChangedListener { _, destination, _ ->
                when (destination.id) {
                    R.id.homeMerchantFragment, R.id.merchantReviewFragment, R.id.bookingFragment, R.id.inboxFragment, R.id.accountFragment -> binding.bnMain.visible()
                    else -> binding.bnMain.gone()
                }
            }
        }

        binding.bnMain.setupWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onBackPressed() {
        if (topLeves.find { menu -> menu == navController.currentDestination?.label } != null) {
            if (doubleBackToExitPressedOnce) {
                finish()
            }
            this.doubleBackToExitPressedOnce = true
            toast("Please click BACK again to exit")
            Handler(Looper.getMainLooper()).postDelayed(
                { doubleBackToExitPressedOnce = false },
                2000
            )
        } else {
            navController.navigateUp()
        }
    }

}