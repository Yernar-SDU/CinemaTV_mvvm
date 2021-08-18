package com.example.cinematv_mvvm.ui.login

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.annotation.RequiresApi
import androidx.core.view.isInvisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.cinematv_mvvm.R
import com.example.cinematv_mvvm.databinding.FragmentLoginPageBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {
    private var _binding: FragmentLoginPageBinding? = null
    private val binding get() = _binding!!
    private val viewModel: LoginViewModel by viewModels()
    private var firstCallSuccess = true
    private var firstCallSessionId = true
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login_page, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        setObservers()
    }

    private fun initView() {
        binding.loginButton.setOnClickListener {

            viewModel.getRequestToken()
        }
    }

    private fun setObservers() {
        viewModel.requestToken.observe(viewLifecycleOwner){
            if (it.isNotEmpty())
                viewModel.loginValidate(binding.editTextEmail.text.toString(),binding.editTextPassword.text.toString(), it)
        }
        viewModel.success.observe(viewLifecycleOwner){
            if (firstCallSuccess){
                firstCallSuccess = false
                return@observe
            }
            if (it){
                binding.incorrectUsernamePasswdTextview.visibility = View.INVISIBLE
            }else{
                binding.incorrectUsernamePasswdTextview.visibility = View.VISIBLE
            }
        }
        viewModel.session_id.observe(viewLifecycleOwner){
            if (firstCallSessionId){
                firstCallSessionId = false
                return@observe
            }
            Log.i("upsTAKE", "setObservers session id: ${it}")
            val action = LoginFragmentDirections.toProfileLoggedIn()
            findNavController().navigate(action)
        }
    }


}