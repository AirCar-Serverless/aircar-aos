package com.serverless.aircar

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.serverless.aircar.adapters.RecyclerAdapter
import com.serverless.aircar.databinding.FragmentCarInfoBinding
import com.serverless.aircar.utilities.CAR1_FILENAME
import net.daum.android.map.MapView
import org.json.JSONArray
import org.json.JSONObject

class CarInfoFragment : Fragment() {

    private var _binding: FragmentCarInfoBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCarInfoBinding.inflate(inflater, container, false)
        context ?: return binding.root

        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initRecycler()
    }

    private fun initRecycler() {
        val adapter = RecyclerAdapter()
        val datas = mutableListOf<CarInfo>()
        binding.recyclerView.adapter = adapter

        val jsonString = requireContext().assets.open(CAR1_FILENAME).reader().readText()
        val carImgArray = JSONObject(jsonString).getJSONArray("imageList")
//        val optionArray = JSONObject(jsonString).getJSONArray("carInfo")
//        Log.d("HJ", optionArray.toString())

        datas.apply {
            add(CarInfo(carImgList = carImgArray.toArrayList()))
            add(CarInfo(hostProfileImg = "", hostName = "abcde", hostRate = 4.5))
            add(CarInfo(
                carName = "Genesis",
                carInfoType = "가나",
                carInfoShapeType = "다라",
                carInfoCount = 5,
                carInfoFuelType = "사아",
                carInfoDetail = "금연차량"
            ))
            add(CarInfo(carOptions = listOf("에어백", "블루투스", "열선시트", "후방 감지 센서", "네비게이션")))
            add(CarInfo(reviewCnt = 30, carRate = 3.0))
            add(CarInfo(clientProfileImg = "", clientName = "qwerty", reviewDate = "2주", reviewRate = 4.8, review = "생각보다 별로였어요"))
            add(CarInfo(clientProfileImg = "", clientName = "qwerty2", reviewDate = "2주", reviewRate = 4.8, review = "생각보다 별로였어요"))
            add(CarInfo(clientProfileImg = "", clientName = "qwerty3", reviewDate = "2주", reviewRate = 4.8, review = "생각보다 별로였어요"))
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