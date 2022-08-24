package com.serverless.aircar

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.serverless.aircar.databinding.FragmentCarInfoBinding

class CarInfoFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentCarInfoBinding.inflate(inflater, container, false)
        context ?: return binding.root

        return binding.root
    }
}