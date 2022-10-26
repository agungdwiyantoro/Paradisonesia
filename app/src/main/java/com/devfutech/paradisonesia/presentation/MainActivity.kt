package com.devfutech.paradisonesia.presentation

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.devfutech.paradisonesia.R
import com.devfutech.paradisonesia.data.local.preferences.IsMerchantPreference
import com.devfutech.paradisonesia.databinding.ActivityMainBinding
import com.devfutech.paradisonesia.external.extension.gone
import com.devfutech.paradisonesia.external.extension.toast
import com.devfutech.paradisonesia.external.extension.visible
import dagger.hilt.android.AndroidEntryPoint

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
        setupTopMenuMerchant()

        if(IsMerchantPreference(getSharedPreferences(IsMerchantPreference.IS_MERCHANT_PREFERENCE, Context.MODE_PRIVATE)).getIsMerchant()){
           // binding.bnMain.inflateMenu(R.menu.bottom_menu)
           // setupTopMenuCustomer()
           // setupActionCustomer()

            setupActionMerchant(R.id.homeMerchantFragment)
            setReviewVisibility(true)


        }

        else {
           // binding.bnMain.inflateMenu(R.menu.bottom_menu_merchant)

            setupActionMerchant(R.id.homeCustomerFragment)

/*
            navController.addOnDestinationChangedListener { _, destination, _ ->
                when (destination.id) {
                    R.id.homeCustomerFragment, R.id.merchantReviewFragment, R.id.bookingFragment, R.id.inboxFragment, R.id.accountFragment -> binding.bnMain.visible()
                    else -> binding.bnMain.gone()
                }
            }


 */
            setReviewVisibility(false)
        }


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

    fun orderThePosition(){
        binding.bnMain.menu.getItem(4).setChecked(true)
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

    fun setupActionMerchant(itemID: Int) {
        binding.bnMain.menu.clear()
        binding.bnMain.menu.add(0, itemID, 0, R.string.label_home).setIcon(R.drawable.ic_home)
        binding.bnMain.menu.add(0, R.id.merchantReviewFragment, 1, R.string.label_review).setIcon(R.drawable.ic_review)
        binding.bnMain.menu.add(0, R.id.bookingFragment, 2, R.string.label_booking).setIcon(R.drawable.ic_booking)
        if(itemID==R.id.homeMerchantFragment) {
            binding.bnMain.menu.add(0, R.id.inboxFragment, 3, R.string.notification)
                .setIcon(R.drawable.ic_notifications)
        }
        else{
            binding.bnMain.menu.add(0, R.id.inboxFragment, 3, R.string.label_inbox)
                .setIcon(R.drawable.ic_inbox)
        }
        binding.bnMain.menu.add(0,  R.id.accountFragment, 4, R.string.label_account).setIcon(R.drawable.ic_account)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                itemID, R.id.merchantReviewFragment, R.id.bookingFragment, R.id.inboxFragment, R.id.accountFragment -> binding.bnMain.visible()
                else -> binding.bnMain.gone()
            }
        }
    }

    /*
       fun setupActionMerchant(item: Int, itemDes: Int) {
           binding.bnMain.menu.removeItem(item)
           binding.bnMain.menu.add(0, itemDes, 0, R.string.label_home).setIcon(R.drawable.ic_home)
           navController.addOnDestinationChangedListener { _, destination, _ ->
               when (destination.id) {
                   itemDes, R.id.merchantReviewFragment, R.id.bookingFragment, R.id.inboxFragment, R.id.accountFragment -> binding.bnMain.visible()
                   else -> binding.bnMain.gone()
               }
           }
       }

       fun setupActionMerchant(item: Int) {
           if(item==R.id.homeCustomerFragment){
               binding.bnMain.menu.removeItem(R.id.homeMerchantFragment)
               binding.bnMain.menu.add(0, R.id.homeCustomerFragment, 0, R.string.label_home).setIcon(R.drawable.ic_home)
               navController.addOnDestinationChangedListener { _, destination, _ ->
                   when (destination.id) {
                       R.id.homeCustomerFragment, R.id.merchantReviewFragment, R.id.bookingFragment, R.id.inboxFragment, R.id.accountFragment -> binding.bnMain.visible()
                       else -> binding.bnMain.gone()
                   }
               }
           }
           if(item==R.id.homeMerchantFragment){
               binding.bnMain.menu.removeItem(R.id.homeCustomerFragment)
               binding.bnMain.menu.add(0, R.id.homeMerchantFragment, 0, R.string.label_home).setIcon(R.drawable.ic_home)
               navController.addOnDestinationChangedListener { _, destination, _ ->
                   when (destination.id) {
                       R.id.homeMerchantFragment, R.id.merchantReviewFragment, R.id.bookingFragment, R.id.inboxFragment, R.id.accountFragment -> binding.bnMain.visible()
                       else -> binding.bnMain.gone()
                   }
               }
           }
          // binding.bnMain.setupWithNavController(navController)
       }

        */

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