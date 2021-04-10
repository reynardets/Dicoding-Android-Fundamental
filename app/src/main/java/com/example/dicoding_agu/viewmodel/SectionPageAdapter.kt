package com.example.dicoding_agu.viewmodel

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.dicoding_agu.fragment.FollowersFragment
import com.example.dicoding_agu.fragment.FollowingFragment

class SectionPageAdapter(activity: AppCompatActivity, private val user: String?) :
    FragmentStateAdapter(activity) {

    private lateinit var bundleFollowers: Bundle
    private lateinit var bundleFollowing: Bundle

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> {
                fragment = FollowingFragment()
                bundleFollowing = Bundle()
                bundleFollowing.putString(FollowingFragment.USERNAME, user)
                fragment.setArguments(bundleFollowing)
            }
            1 -> {
                fragment = FollowersFragment()
                bundleFollowers = Bundle()
                bundleFollowers.putString(FollowersFragment.USERNAME, user)
                fragment.setArguments(bundleFollowers)
            }
        }
        return fragment as Fragment
    }

}