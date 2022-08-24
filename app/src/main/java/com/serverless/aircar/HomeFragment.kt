package com.serverless.aircar

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.serverless.aircar.databinding.FragmentHomeBinding
import net.daum.mf.map.api.MapView

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentHomeBinding.inflate(inflater, container, false)
        context ?: return binding.root

        val mapView = MapView(context)
        binding.mapView.addView(mapView)

        return binding.root
    }
}