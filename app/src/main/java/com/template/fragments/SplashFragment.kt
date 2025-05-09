package com.template.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.template.AuthIntent
import com.template.ViewModel
import com.template.R
import com.template.databinding.FragmentSplashBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class SplashFragment : Fragment() {
    private val vm: ViewModel by viewModel<ViewModel>()

    private var _binding:FragmentSplashBinding? =null
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding =FragmentSplashBinding.inflate(inflater,container, false )

        vm.handleIntent(AuthIntent.CheckUser)
        lifecycleScope.launch {
            delay(3000)
         vm.stateSplash.collectLatest { state->
             if (state.isLoading){

                 findNavController().navigate(R.id.action_splashFragment_to_mainFragment)
             }else if (state.errorMessage != null){
                 findNavController().navigate(R.id.action_splashFragment_to_siginUpFragment)

             }
         }

        }
        return binding?.root
    }


}