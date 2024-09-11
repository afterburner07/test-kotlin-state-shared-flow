package com.example.testkotlinflows10_09_2024.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.testkotlinflows10_09_2024.databinding.FragmentHomeBinding
import com.example.testkotlinflows10_09_2024.MainViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this)[HomeViewModel::class.java]
        val mainViewModel: MainViewModel by viewModels<MainViewModel>()

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textHome
/*
        homeViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
*/
        // For Fragment use viewLifecycleOwner.lifecycleScope
        viewLifecycleOwner.lifecycleScope.launch {
            // For Fragment use viewLifecycleOwner.repeatOnLifecycle
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
/*
                launch {
                    mainViewModel.textAsStateFlow.collect {
                        // do something with values
                        binding.textHome.text = it
                    }
                }
*/
                launch {
                    mainViewModel.textAsStateFlow.collect {
                        // do something with values
                        binding.textHome.text = it
                    }
                }
                launch {
                    mainViewModel.updateTextAsSharedFlow.collect{
                        // do something with values
                        binding.sharedFlowTextHome.text = it
                    }
                }
/*
                mainViewModel.updateTextAsSharedFlow.collectLatest {
                    // do something with values
                    binding.sharedFlowTextHome.text = it
                }
*/
                val a = ""
                // However, here if we start collecting flow then it will never going to be collected
                // Therefore, to overcome this we should use launch { }
/*
                launch {
                    viewModel.userDetails.collect {
                        // do something with user details
                    }
                }
*/
            }
        }

        binding.updateFromStateFlowButton.setOnClickListener {
//            homeViewModel.updateTextFromStateFlow()
            mainViewModel.updateTextFromStateFlow()
        }
        binding.updateFromSharedFlowButton.setOnClickListener {
//            homeViewModel.updateTextFromSharedFlow()
            mainViewModel.updateTextFromSharedFlow()
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}