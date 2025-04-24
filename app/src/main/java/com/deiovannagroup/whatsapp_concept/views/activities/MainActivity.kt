package com.deiovannagroup.whatsapp_concept.views.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuProvider
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.deiovannagroup.whatsapp_concept.R
import com.deiovannagroup.whatsapp_concept.databinding.ActivityMainBinding
import com.deiovannagroup.whatsapp_concept.repositories.AuthRepository
import com.deiovannagroup.whatsapp_concept.services.FirebaseAuthService
import com.deiovannagroup.whatsapp_concept.viewmodels.MainViewModel
import com.deiovannagroup.whatsapp_concept.views.adapters.ViewPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val mainViewModel by lazy {
        MainViewModel(
            AuthRepository(
                FirebaseAuthService()
            ),
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setEdgeToEdgeLayout()

        initToolbar()
        initTabsNavigation()
    }

    private fun initTabsNavigation() {
        val tabLayout = binding.tabLayoutMain
        val viewPager = binding.viewPageMain

        val tabs = resources.getStringArray(R.array.tabs).asList()
        viewPager.adapter = ViewPagerAdapter(
            tabs,
            supportFragmentManager,
            lifecycle,
        )

        tabLayout.isTabIndicatorFullWidth = true
        TabLayoutMediator(
            tabLayout,
            viewPager,
        ) { tab, position ->
            tab.text = tabs[position]
        }.attach()
    }

    private fun initToolbar() {
        val toolbar = binding.includeMainToolbar.tbMain
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            title = getString(R.string.app_name)
        }

        addMenuProvider(
            object : MenuProvider {
                override fun onCreateMenu(
                    menu: Menu,
                    menuInflater: MenuInflater
                ) {
                    menuInflater.inflate(
                        R.menu.menu_main,
                        menu,
                    )
                }

                override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                    when (menuItem.itemId) {
                        R.id.item_profile -> {
                            startActivity(
                                Intent(
                                    applicationContext,
                                    ProfileActivity::class.java,
                                )
                            )
                        }

                        R.id.item_sign_out -> {
                            AlertDialog.Builder(this@MainActivity).apply {
                                setTitle(getString(R.string.sign_out))
                                setMessage(getString(R.string.sign_out_message))
                                setNegativeButton(getString(R.string.cancel), null)
                                setPositiveButton(getString(R.string.sign_out)) { _, _ ->
                                    mainViewModel.signOutUser()

                                    startActivity(
                                        Intent(
                                            applicationContext,
                                            LoginActivity::class.java,
                                        )
                                    )
                                }
                            }
                                .create()
                                .show()
                        }
                    }
                    return true
                }
            }
        )

    }

    private fun setEdgeToEdgeLayout() {
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(
                systemBars.left,
                systemBars.top,
                systemBars.right,
                systemBars.bottom,
            )
            insets
        }
    }
}