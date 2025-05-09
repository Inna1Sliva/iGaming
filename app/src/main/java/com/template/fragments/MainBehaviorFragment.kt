package com.template.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2.ORIENTATION_HORIZONTAL
import com.template.ViewModel
import com.template.R
import com.template.adapter.AdapterViewPager
import com.template.databinding.FragmentMainBehaviorBinding
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainBehaviorFragment : Fragment() {
    private val vm: ViewModel by viewModel<ViewModel>()
    private lateinit var adapterViewPager: AdapterViewPager
    private lateinit var adapterState: FragmentStateAdapter

    private var _binding:FragmentMainBehaviorBinding? =null
    private val binding get() = _binding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBehaviorBinding.inflate(inflater,container,false)

        vm.banner.observe(viewLifecycleOwner){ images->
            adapterViewPager.setImageList(images)
        }

        startFragment(TabFragment())

        binding?.viewPager?.apply {
            adapterViewPager= AdapterViewPager(requireContext())
            orientation= ORIENTATION_HORIZONTAL
            adapter = adapterViewPager
        }





        return binding!!.root



    }

    private fun startFragment(fragment: Fragment) {
        var fragmentTransaction = parentFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentContainer, fragment)
        fragmentTransaction.commit()
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}