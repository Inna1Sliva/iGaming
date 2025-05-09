package com.template.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.template.ViewModel
import com.template.R
import com.template.databinding.FragmentMainBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainFragment : Fragment() {
    private val vm: ViewModel by viewModel<ViewModel>()

    private var _binding:FragmentMainBinding? = null
    private val binding get() = _binding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(inflater,container, false )
      
      vm.userData.observe(viewLifecycleOwner){ user->
        user.forEach {
            binding?.toolbar?.check?.text = it.check
        }
      }

        startFragment(MainBehaviorFragment())
        return binding?.root
    }



    private fun startFragment(fragment: Fragment) {
        var fragmentTransaction = parentFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.containerFragment, fragment)
       fragmentTransaction.commit()
    }

}