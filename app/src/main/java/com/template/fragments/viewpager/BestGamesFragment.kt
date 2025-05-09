package com.template.fragments.viewpager

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.template.ViewModel
import com.template.adapter.AdapterListLinkGames
import com.template.adapter.LoadMoreAdapter
import com.template.databinding.FragmentBestGamesBinding
import org.koin.androidx.viewmodel.ext.android.viewModel


class BestGamesFragment : Fragment() {
    private lateinit var adapterListLinkGames: AdapterListLinkGames
    private lateinit var loadMoreAdapter: LoadMoreAdapter
    private val vm: ViewModel by viewModel<ViewModel>()

    private var _binding: FragmentBestGamesBinding ?= null
    private val binding get() = _binding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       _binding = FragmentBestGamesBinding.inflate(inflater, container,false)

       viewLifecycleOwner.lifecycleScope.launchWhenCreated{
            vm.pagingDataFlow.collect{
                adapterListLinkGames.submitData(it)
            }
        }
        with(binding!!){
            RecyclerListBestGames.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            RecyclerListBestGames.setHasFixedSize(false)
            adapterListLinkGames = AdapterListLinkGames(requireContext())
            RecyclerListBestGames.adapter = adapterListLinkGames
        }

        return binding!!.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}