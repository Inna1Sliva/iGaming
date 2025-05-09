package com.template.fragments.viewpager

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.template.ViewModel
import com.template.adapter.AdapterListLinkSlots
import com.template.databinding.FragmentTopSlotBinding
import org.koin.androidx.viewmodel.ext.android.viewModel


class TopSlotFragment : Fragment() {
    private val vm: ViewModel by viewModel<ViewModel>()
    private lateinit var adapterListLinkSlots: AdapterListLinkSlots
    private var _binding: FragmentTopSlotBinding? = null
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= FragmentTopSlotBinding.inflate(inflater,container, false )

        vm.slots.observe(viewLifecycleOwner){ list->
            adapterListLinkSlots.setSlotsList(list)

        }
        with(binding!!){
            slotList.layoutManager=GridLayoutManager(requireContext(), 2)
            adapterListLinkSlots = AdapterListLinkSlots(requireContext())
            slotList.adapter = adapterListLinkSlots
        }

        return binding?.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}