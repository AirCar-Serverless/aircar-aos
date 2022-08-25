package com.serverless.aircar

import android.R
import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.serverless.aircar.adapters.RecyclerAdapter
import com.serverless.aircar.data.Location
import com.serverless.aircar.databinding.FragmentCarInfoBinding
import com.serverless.aircar.utilities.CAR1_FILENAME
import com.serverless.aircar.utilities.CAR_INFO_FILENAME
import org.json.JSONArray
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.*
import kotlin.collections.ArrayList


class CarInfoFragment : Fragment() {

    private var _binding: FragmentCarInfoBinding? = null
    private val binding get() = _binding!!

    private lateinit var cid: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCarInfoBinding.inflate(inflater, container, false)
        context ?: return binding.root

        val args: CarInfoFragmentArgs by navArgs()
        cid = args.cid
        initRecycler(cid)

        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.btnCarInfoBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.reservationBtn.setOnClickListener {
            val direction =
                CarInfoFragmentDirections.actionCarInfoFragmentToPaymentFragment(cid)
            findNavController().navigate(direction)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun initRecycler(cid: String) {
        val adapter = RecyclerAdapter()
        val datas = mutableListOf<CarInfo>()
        binding.recyclerView.adapter = adapter

        val jsonString = requireContext().assets.open("$cid.json").reader().readText()

        val hostObject = JSONObject(jsonString).getJSONObject("host")
        val carInfoObject = JSONObject(jsonString).getJSONObject("carInfo")
        val reviewObject = JSONObject(jsonString).getJSONObject("review")
        val locationObject = JSONObject(jsonString).getJSONObject("location")
        val priceInfoObject = JSONObject(jsonString).getJSONObject("priceInfo")

        val carImgArray = JSONObject(jsonString).getJSONArray("imageList")
        val reviewArray = reviewObject.getJSONArray("reviews")
        val firstReviewObject: JSONObject = reviewArray[0] as JSONObject
        val secondReviewObject: JSONObject = reviewArray[1] as JSONObject
        val thirdReviewObject: JSONObject = reviewArray[2] as JSONObject

        datas.apply {
            add(CarInfo(carImgList = carImgArray.toArrayList()))
            add(CarInfo(hostProfileImg = hostObject.getString("image"), hostName = hostObject.getString("name"), hostRate = hostObject.getString("grade")))
            add(CarInfo(
                carName = carInfoObject.getString("name"),
                carInfoType = carInfoObject.getString("type"),
                carInfoShapeType = carInfoObject.getString("gearType"),
                carInfoCount = carInfoObject.getString("limit"),
                carInfoFuelType = carInfoObject.getString("oilType"),
                carInfoDetail = carInfoObject.getInt("smoke")
            ))
            add(CarInfo(carOptions = carInfoObject.getJSONArray("options").toArrayList()))
            add(CarInfo(reviewCnt = reviewArray.length(), carRate = reviewObject.getDouble("stars")))
            add(CarInfo(
                clientProfileImg = firstReviewObject.getString("image"),
                clientName = firstReviewObject.getString("name"),
                reviewDate = firstReviewObject.getString("date"),
                reviewRate = firstReviewObject.getDouble("rate"),
                review = firstReviewObject.getString("content")))
            add(CarInfo(
                clientProfileImg = secondReviewObject.getString("image"),
                clientName = secondReviewObject.getString("name"),
                reviewDate = secondReviewObject.getString("date"),
                reviewRate = secondReviewObject.getDouble("rate"),
                review = secondReviewObject.getString("content")))
            add(CarInfo(
                clientProfileImg = thirdReviewObject.getString("image"),
                clientName = thirdReviewObject.getString("name"),
                reviewDate = thirdReviewObject.getString("date"),
                reviewRate = thirdReviewObject.getDouble("rate"),
                review = thirdReviewObject.getString("content")))
            add(CarInfo())
//            add(CarInfo(location_lat = "33.4503", location_lng = "126.9184"))

            adapter?.let {
                adapter.submitList(datas)
                adapter.notifyDataSetChanged()
            }
        }
    }
}

fun JSONArray.toArrayList(): ArrayList<String> {
    val list = arrayListOf<String>()
    for (i in 0 until this.length()) {
        list.add(this.getString(i))
    }

    return list
}

@RequiresApi(Build.VERSION_CODES.O)
fun calculateDate(date: String): Int{
    val time = "2011-12-03T10:15:30" // 변환할 문자열
    val now = LocalDateTime.now() // 현재 시간
    val convertTime = LocalDateTime.parse(date, DateTimeFormatter.ISO_LOCAL_DATE_TIME)

    val compareTime = ChronoUnit.DAYS.between(now, convertTime)
    return compareTime as Int
}