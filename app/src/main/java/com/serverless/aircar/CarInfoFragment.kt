package com.serverless.aircar

import android.R
import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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


class CarInfoFragment : Fragment() {

    private var _binding: FragmentCarInfoBinding? = null
    private val binding get() = _binding!!

    private lateinit var number: String
    private lateinit var content: String
    private lateinit var cid: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        arguments?.let {
            number = it.get("number").toString()
            content = it.get("content").toString()
            Log.d("HJ", number+"/"+content)
        }
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

    private fun initRecycler(cid: String) {
        val adapter = RecyclerAdapter()
        val datas = mutableListOf<CarInfo>()
        binding.recyclerView.adapter = adapter

        val jsonString = requireContext().assets.open("$cid.json").reader().readText()
        val carImgArray = JSONObject(jsonString).getJSONArray("imageList")
        val imageList = mutableListOf<String>()
        for (i in 0 until carImgArray.length()) {
            imageList.add(carImgArray.getString(i))
        }
        val hostObject = JSONObject(jsonString).getJSONObject("host")

        datas.apply {
            add(CarInfo(carImgList = imageList))
            add(CarInfo(hostProfileImg = hostObject.getString("image"), hostName = hostObject.getString("name"), hostRate = hostObject.getString("grade")))
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