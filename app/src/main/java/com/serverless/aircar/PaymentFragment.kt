package com.serverless.aircar

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.serverless.aircar.data.CarInfo
import com.serverless.aircar.data.Location
import com.serverless.aircar.databinding.FragmentPaymentBinding
import com.serverless.aircar.utilities.CAR_INFO_FILENAME
import org.json.JSONObject
import java.text.DecimalFormat

class PaymentFragment : Fragment() {

    private lateinit var binding: FragmentPaymentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPaymentBinding.inflate(inflater, container, false)
        context ?: return binding.root

        val args: PaymentFragmentArgs by navArgs()
        val cid = args.cid

        loadCarInfo(cid)

        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.btnComplete.setOnClickListener {
            val direction =
                PaymentFragmentDirections.actionPaymentFragmentToCompleteFragment()
            findNavController().navigate(direction)
        }

        return binding.root
    }

    private fun loadCarInfo(cid: String) {
        val jsonString = requireContext().assets.open("$cid.json").reader().readText()
        val carInfoObject = JSONObject(jsonString).getJSONObject("carInfo")
        val reviewObject = JSONObject(jsonString).getJSONObject("review")
        val priceInfoObject = JSONObject(jsonString).getJSONObject("priceInfo")
        with(binding) {
            carName.text = carInfoObject.getString("name")
            carLimit.text = carInfoObject.getString("limit")
            oilType.text = carInfoObject.getString("oilType")
            tvScore.text = reviewObject.getDouble("stars").toString()
            reviewCount.text = reviewObject.getJSONArray("reviews").length().toString()

            val stArr = priceInfoObject.getString("st").split('T')
            val etArr = priceInfoObject.getString("et").split('T')

            val dateArr = stArr[0].split('-')
            val stTimeArr = stArr[1].split(':')
            val etTimeArr = etArr[1].split(':')
            val month = dateArr[1]
            val day = dateArr[2]
            val stHour = stTimeArr[0]
            val stMinute = stTimeArr[1]
            val etHour = etTimeArr[0]
            val etMinute = etTimeArr[1]
            rentTime.text = "${month}월 ${day}일 ${stHour}:${stMinute} - ${etHour}:${etMinute}"

            val price = DecimalFormat("#,###").format(priceInfoObject.getInt("price"))
            price1.text = price
            price2.text = price
        }
    }
}