package com.deiovannagroup.whatsapp_concept.views.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.deiovannagroup.whatsapp_concept.views.fragments.ChatsFragment
import com.deiovannagroup.whatsapp_concept.views.fragments.ContactsFragment

class ViewPagerAdapter(
    private val tabs: List<String>,
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle,
) : FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount() = tabs.size
    override fun createFragment(position: Int): Fragment {
        return when (position) {
            1 -> ContactsFragment()
            else -> ChatsFragment()
        }
    }

}