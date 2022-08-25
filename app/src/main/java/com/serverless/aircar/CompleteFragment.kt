package com.serverless.aircar

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.serverless.aircar.databinding.FragmentCompleteBinding

class CompleteFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentCompleteBinding.inflate(inflater, container, false)
        context ?: return binding.root

        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.btnComplete.setOnClickListener {
            val direction =
                CompleteFragmentDirections.actionPaymentFragmentToHomeFragment()
            findNavController().navigate(direction)
        }

        return binding.root
    }
}