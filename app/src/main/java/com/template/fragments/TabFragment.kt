package com.template.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.template.R
import com.template.adapter.AdapterFragmentState
import com.template.databinding.FragmentTabBinding


class TabFragment : Fragment() {
    private lateinit var adapterState: FragmentStateAdapter

    private var _binding: FragmentTabBinding?= null
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTabBinding.inflate(inflater,container,false)


       adapterState = AdapterFragmentState(parentFragmentManager,lifecycle)
      with(binding!!){
          viewPagerFragment.adapter = adapterState
          viewPagerFragment.isUserInputEnabled=false
          tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
              override fun onTabSelected(tab: TabLayout.Tab?) {
                  if (tab != null) {
                      viewPagerFragment.currentItem = tab.position
                  }
              }

              override fun onTabUnselected(tab: TabLayout.Tab?) {

              }

              override fun onTabReselected(tab: TabLayout.Tab?) {

              }

          })
      }
        return binding?.root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}