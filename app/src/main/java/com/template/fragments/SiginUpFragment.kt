package com.template.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.template.AuthIntent
import com.template.ViewModel
import com.template.R
import com.template.databinding.FragmentSiginUpBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class SiginUpFragment : Fragment() {
    private lateinit var auth: FirebaseAuth
    private val vm: ViewModel by viewModel<ViewModel>()
    private var _binding:FragmentSiginUpBinding? = null
    private val binding get() = _binding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       _binding = FragmentSiginUpBinding.inflate(inflater, container,false)
        auth = Firebase.auth
        binding?.buttNext?.setOnClickListener  {

                val email = binding?.email?.text.toString().trim()
                val password = binding?.password?.text.toString()
            if (isEmailValid(email) && isPasswordValid(password)) {
                //authUsers(email, password)
                vm.handleIntent(AuthIntent.AuthUsers(email, password, requireActivity()))
                findNavController().navigate(R.id.action_siginUpFragment_to_mainFragment)
                if (email.isEmpty() && password.isEmpty()) {
                    Toast.makeText(
                        requireContext(),
                        "Введите данные для регистрации",
                        Toast.LENGTH_SHORT
                    ).show()

                } else if (email.isEmpty()) {
                    binding?.email?.requestFocus()
                    Toast.makeText(requireContext(), "Введите Почту", Toast.LENGTH_SHORT).show()
                } else if (password.isEmpty()) {
                    binding?.password?.requestFocus()
                    Toast.makeText(requireContext(), "Введите пароль", Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(requireContext(), "Извените, что-то пошло не так. Пожалуйста проверьте ваш пароль и почту ", Toast.LENGTH_SHORT).show()

            }


        }
       //observerViewModel()

        return binding?.root
    }
    fun authUsers(email: String, password: String) {
       auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(requireContext(), "Регистрация успешна", Toast.LENGTH_SHORT).show()
                    // Переход на главный экран после успешной регистрации

                } else {
                    // Toast.makeText(this, "Ошибка регистрации: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }
    private fun isEmailValid(email:String):Boolean{
        return  android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
    private fun isPasswordValid(password: String):Boolean{
        return password.length >=6
    }


    }


