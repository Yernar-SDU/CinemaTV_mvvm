package com.example.cinematv_mvvm.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.cinematv_mvvm.R
import com.example.cinematv_mvvm.databinding.FragmentProfileLoggedInBinding
import com.example.cinematv_mvvm.databinding.FragmentProfileNotLoggedInBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ProfileFragment: Fragment() {
    private lateinit var bindingLoggedIn: FragmentProfileLoggedInBinding
    private lateinit var bindingNotLoggedIn: FragmentProfileNotLoggedInBinding
    private val viewModel: ProfileViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        return if (viewModel.isUserLoggedIn()){
            bindingLoggedIn = DataBindingUtil.inflate(
                inflater, R.layout.fragment_profile_logged_in, container, false
            )
            bindingLoggedIn.lifecycleOwner = this
            bindingLoggedIn.root
        }else{
            bindingNotLoggedIn = DataBindingUtil.inflate(
                inflater, R.layout.fragment_profile_not_logged_in, container, false
            )
            bindingNotLoggedIn.lifecycleOwner = this
            bindingNotLoggedIn.root
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setObservers()
        initView()
    }

    private fun initView() {

        if (viewModel.isUserLoggedIn()){
        }else{
            bindingNotLoggedIn.loginButton.setOnClickListener{
                val action = ProfileFragmentDirections.toLogin()
                findNavController().navigate(action)
            }
        }


    }

    private fun setObservers() {

    }

}