package com.serverless.aircar

import android.graphics.Canvas
import android.graphics.ColorFilter
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.serverless.aircar.databinding.FragmentHomeBinding
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    var persistenetBottomSheet: BottomSheetBehavior<View>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        context ?: return binding.root

        Glide.with(requireContext())
            .load("https://i.imgur.com/tDdSdB2.png")
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(binding.imgCar)

        val mapView = showMap()
        showMarker(mapView)

        val persistenetBottomSheet: BottomSheetBehavior<View> =
            BottomSheetBehavior.from(binding.bottomSheet)

        return binding.root
    }

    private fun showMap(): MapView {
        // 지도 뷰 띄우기
        val mapView = MapView(context)
        binding.mapView.addView(mapView)
        mapView.currentLocationTrackingMode = MapView.CurrentLocationTrackingMode.TrackingModeOnWithHeading

        return mapView
    }

    private fun showMarker(mapView: MapView) {
        // 마커 찍기
        val marker = MapPOIItem()
        marker.apply {
            itemName = "Marker!!"
            mapPoint = MapPoint.mapPointWithGeoCoord(33.4502, 126.9185)
            markerType = MapPOIItem.MarkerType.RedPin
        }
        mapView.addPOIItem(marker)
    }
}