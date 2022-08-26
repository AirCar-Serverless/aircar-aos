package com.serverless.aircar

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.serverless.aircar.adapters.CarInfoAdapter
import com.serverless.aircar.data.CarInfo
import com.serverless.aircar.data.Location
import com.serverless.aircar.databinding.FragmentHomeBinding
import com.serverless.aircar.utilities.CAR_INFO_FILENAME
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView
import org.json.JSONObject
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    val carInfoMap = HashMap<Location, CarInfo>()
    private lateinit var carId: String

    private val now = System.currentTimeMillis()
    private val currentDate = Date(now)
    private var rentTime = ""
    private var returnTime = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initTime()
    }

    override fun onResume() {
        val calendar = Calendar.getInstance()
        calendar.time = rentDate
        rentTime = getDate(rentDate, calendar)

        calendar.add(Calendar.DATE, 1)
        returnTime = getDate(returnDate, calendar)

        checkDate()
        super.onResume()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        context ?: return binding.root

        checkDate()
        showMap()
        loadCarInfo()

        val adapter = CarInfoAdapter(object : CarInfoAdapter.HolderEvent {
            override fun onClickIssue(cid: String) {
                val direction =
                    HomeFragmentDirections.actionHomeFragmentToCarInfoFragment(cid)
                findNavController().navigate(direction)
                binding.mapView.removeView(mapView)
            }
        })
        binding.carList.adapter = adapter
        adapter.submitList(getCarInfoList())

        mapView.setPOIItemEventListener(markerListener)
        mapView.setMapViewEventListener(mapViewEventListener)

        binding.constraintTime.setOnClickListener {
            val direction =
                HomeFragmentDirections.actionHomeFragmentToTimeSetFragment()
            findNavController().navigate(direction)
        }

        binding.lnCarInfo.setOnClickListener {
            Log.d("jaemin", carId)
            val direction =
                HomeFragmentDirections.actionHomeFragmentToCarInfoFragment(carId)
            findNavController().navigate(direction)
            binding.mapView.removeView(mapView)
        }

        return binding.root
    }

    private fun initTime() {
        val calendar = Calendar.getInstance()
        calendar.time = currentDate
        rentDate = currentDate
        rentTime = getDate(currentDate, calendar)

        calendar.add(Calendar.HOUR, 2)
        returnDate = calendar.time
        returnTime = getDate(calendar.time, calendar)
    }

    private fun checkDate() {
        val rentStr = rentTime.split(' ')[0]
        val returnStr = returnTime.split(' ')[0]

        if (rentStr == returnStr) {
            binding.rentTime.text = rentTime
            binding.returnTime.text = returnTime.removePrefix("$returnStr ")
        } else {
            binding.rentTime.text = rentTime
            binding.returnTime.text = returnTime
        }

        binding.tvUsingTime.text = ((returnDate.time - rentDate.time) / 3600000).toString()
    }

    private fun getDate(selectedDate: Date, calendar: Calendar): String {
        val dateFormat = SimpleDateFormat("MM-dd", Locale.getDefault())
        val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        val moreDaysFormat = SimpleDateFormat("MM월 dd일 HH:mm", Locale.getDefault())

        return if (dateFormat.format(currentDate) == dateFormat.format(selectedDate)) {
            "오늘 ${timeFormat.format(selectedDate)}"
        } else if (dateFormat.format(calendar.time) == dateFormat.format(selectedDate)) {
            "내일 ${timeFormat.format(selectedDate)}"
        } else {
            moreDaysFormat.format(selectedDate)
        }
    }

    private fun showMap(): MapView {
        // 지도 뷰 띄우기
        mapView = MapView(context)
        binding.mapView.addView(mapView)
        mapView.currentLocationTrackingMode = MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeading

        return mapView
    }

    private fun showMarker(price: Int, lat: Double, lng: Double) {
        // 마커 찍기
        val marker = MapPOIItem()
        marker.apply {
            itemName = DecimalFormat("#,###원").format(price) // 금액 표시
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
                showMarker(price, lat, lng)
            }
        }
    }

    private fun showCarInfo(
        imageUrl: String,
        price: Int,
        name: String,
        oilType: String,
        reviewCount: Int,
        stars: Double) {

        with(binding) {
            // 이미지 표시
            Glide.with(requireContext())
                .load(imageUrl)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(imgCar)

            tvPrice.text = DecimalFormat("#,###원").format(price) // 금액 표시
            tvName.text = name // 차량 이름 표시
            tvOilType.text = oilType // 오일 타입 표시
            tvReviewCount.text = reviewCount.toString() // 리뷰 개수 표시
            tvScore.text = stars.toString() // 평점 표시
        }
    }

    private fun getCarInfoList(): List<CarInfo> {
        val carInfoList = mutableListOf<CarInfo>()
        carInfoMap.map {
            carInfoList.add(it.value)
        }

        return carInfoList
    }

    // marker 클릭 리스너
    private val markerListener = object : MapView.POIItemEventListener {
        override fun onPOIItemSelected(p0: MapView?, p1: MapPOIItem?) {
            binding.lnCarInfo.visibility = View.VISIBLE

            val lat = String.format("%.4f", p1!!.mapPoint.mapPointGeoCoord.latitude).toDouble()
            val lng = String.format("%.4f", p1.mapPoint.mapPointGeoCoord.longitude).toDouble()
            val carInfo = carInfoMap[Location(lat, lng)]!!
            carInfo.apply {
                carId = cid
                showCarInfo(imageUrl, price, name, oilType, reviewCount, stars)
            }
        }
        @Deprecated("Deprecated in Java")
        override fun onCalloutBalloonOfPOIItemTouched(p0: MapView?, p1: MapPOIItem?) {}
        override fun onCalloutBalloonOfPOIItemTouched(
            p0: MapView?,
            p1: MapPOIItem?,
            p2: MapPOIItem.CalloutBalloonButtonType?
        ) {}
        override fun onDraggablePOIItemMoved(p0: MapView?, p1: MapPOIItem?, p2: MapPoint?) {}
    }

    // 지도 클릭 리스너
    private val mapViewEventListener = object : MapView.MapViewEventListener{
        override fun onMapViewSingleTapped(p0: MapView?, p1: MapPoint?) {
            binding.lnCarInfo.visibility = View.INVISIBLE
        }
        override fun onMapViewInitialized(p0: MapView?) {}
        override fun onMapViewCenterPointMoved(p0: MapView?, p1: MapPoint?) {}
        override fun onMapViewZoomLevelChanged(p0: MapView?, p1: Int) {}
        override fun onMapViewDoubleTapped(p0: MapView?, p1: MapPoint?) {}
        override fun onMapViewLongPressed(p0: MapView?, p1: MapPoint?) {}
        override fun onMapViewDragStarted(p0: MapView?, p1: MapPoint?) {}
        override fun onMapViewDragEnded(p0: MapView?, p1: MapPoint?) {}
        override fun onMapViewMoveFinished(p0: MapView?, p1: MapPoint?) {}
    }

    companion object {
        lateinit var rentDate: Date
        lateinit var returnDate: Date
        lateinit var mapView: MapView
    }
}