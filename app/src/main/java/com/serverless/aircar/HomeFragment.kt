package com.serverless.aircar

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.serverless.aircar.data.CarInfo
import com.serverless.aircar.data.Location
import com.serverless.aircar.databinding.FragmentHomeBinding
import com.serverless.aircar.utilities.CAR_INFO_FILENAME
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView
import org.json.JSONObject

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var mapView: MapView
    val carInfoMap = HashMap<Location, CarInfo>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        context ?: return binding.root

        // TODO: delete code
        Glide.with(requireContext())
            .load("https://i.imgur.com/tDdSdB2.png")
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(binding.imgCar)

        showMap()
        loadCarInfo()

        return binding.root
    }

    private fun showMap(): MapView {
        // 지도 뷰 띄우기
        mapView = MapView(context)
        binding.mapView.addView(mapView)
        mapView.currentLocationTrackingMode = MapView.CurrentLocationTrackingMode.TrackingModeOnWithHeading

        return mapView
    }

    private fun showMarker(price: String, lat: Double, lng: Double) {
        // 마커 찍기
        val marker = MapPOIItem()
        marker.apply {
            itemName = price
            mapPoint = MapPoint.mapPointWithGeoCoord(lat, lng)
            markerType = MapPOIItem.MarkerType.RedPin
        }
        mapView.addPOIItem(marker)
    }

    private fun loadCarInfo() {
        val jsonString = requireContext().assets.open(CAR_INFO_FILENAME).reader().readText()
        val jsonArray = JSONObject(jsonString).getJSONArray("carList")

        for (i in 0 until jsonArray.length()) {
            val jsonObject = jsonArray.getJSONObject(i)
            jsonObject.apply {
                val cid = jsonObject.getString("cid")
                val imageUrl = jsonObject.getString("image")
                val stars = jsonObject.getDouble("stars")
                val reviewCount = jsonObject.getInt("reviews")
                val price = jsonObject.getInt("price")
                val name = jsonObject.getString("name")
                val oilType = jsonObject.getString("oilType")
                val startTime = jsonObject.getJSONObject("available").getString("st")
                val endTime = jsonObject.getJSONObject("available").getString("et")
                val lat = jsonObject.getJSONObject("location").getDouble("lat")
                val lng = jsonObject.getJSONObject("location").getDouble("lng")

                carInfoMap[Location(lat, lng)] = CarInfo(cid, imageUrl, stars, reviewCount, price, name, oilType, startTime, endTime)
                showMarker(price.toString(), lat, lng)
            }
        }
    }
}