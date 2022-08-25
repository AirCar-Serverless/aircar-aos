package com.serverless.aircar

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.serverless.aircar.databinding.FragmentCarInfoBinding
import com.serverless.aircar.databinding.FragmentHomeBinding
import net.daum.mf.map.api.MapView

class CarInfoFragment : Fragment() {

    private var _binding: FragmentCarInfoBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCarInfoBinding.inflate(inflater, container, false)
        context ?: return binding.root

//        val locationView = MapView(context)
//        binding.locationView.addView(locationView)
        

        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initRecycler()
//        val adapter = RecyclerAdapter()
//        val datas = mutableListOf<CarInfo>()
//        binding.recyclerView.adapter = adapter
//
//        datas.apply {
//            add(CarInfo())
//            add(CarInfo(hostProfileImg = "", hostName = "abcde", hostRate = 4.5))
//            add(CarInfo(
//                carName = "Genesis",
//                carType1 = "가나",
//                carType2 = "다라",
//                carType3 = "마바",
//                carType4 = "사아",
//                carType5 = "자차\n카타\n파하",
//                carType6 = "끝끝"
//            ))
//            add(CarInfo(carRate = 3.0))
//            add(CarInfo(clientProfileImg = "", clientName = "qwerty", reviewDate = "2주", review = "생각보다 별로였어요"))
//            add(CarInfo(clientProfileImg = "", clientName = "qwerty2", reviewDate = "2주", review = "생각보다 별로였어요"))
//            add(CarInfo(clientProfileImg = "", clientName = "qwerty3", reviewDate = "2주", review = "생각보다 별로였어요"))
//            add(CarInfo())
//        }
//        adapter.submitList(datas)
//        adapter.notifyDataSetChanged()
    }

    private fun initRecycler() {
        val adapter = RecyclerAdapter()
        val datas = mutableListOf<CarInfo>()
        binding.recyclerView.adapter = adapter

        datas.apply {
            add(CarInfo())
            add(CarInfo(hostProfileImg = "", hostName = "abcde", hostRate = 4.5))
            add(CarInfo(
                carName = "Genesis",
                carType1 = "가나",
                carType2 = "다라",
                carType3 = "마바",
                carType4 = "사아",
                carType5 = "자차\n카타\n파하",
                carType6 = "끝끝"
            ))
            add(CarInfo(carRate = 3.0))
            add(CarInfo(clientProfileImg = "", clientName = "qwerty", reviewDate = "2주", review = "생각보다 별로였어요"))
            add(CarInfo(clientProfileImg = "", clientName = "qwerty2", reviewDate = "2주", review = "생각보다 별로였어요"))
            add(CarInfo(clientProfileImg = "", clientName = "qwerty3", reviewDate = "2주", review = "생각보다 별로였어요"))
            add(CarInfo())

            adapter.submitList(datas)
            adapter.notifyDataSetChanged()
        }
//        adapter.submitList(datas)
//        adapter.notifyDataSetChanged()
    }
}