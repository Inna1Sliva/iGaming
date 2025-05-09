package com.template.adapter


import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.template.fragments.viewpager.BestGamesFragment
import com.template.fragments.viewpager.TopSlotFragment

class AdapterFragmentState(fragmentManager: FragmentManager, lifecycle: Lifecycle): FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int {
            return  2
    }

    override fun createFragment(position: Int): Fragment {
      return when(position){
          0 -> {
              TopSlotFragment()
          }
          1 ->{
              BestGamesFragment()
          }
          else ->{
              BestGamesFragment()
          }
      }
    }
}