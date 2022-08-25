package com.serverless.aircar.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.denzcoskun.imageslider.models.SlideModel
import com.serverless.aircar.databinding.*
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.interfaces.ItemChangeListener
import com.serverless.aircar.CarInfo
import com.serverless.aircar.CarInfoData
import com.serverless.aircar.R
import com.serverless.aircar.data.Option
import net.daum.android.map.MapView

class GridRecyclerAdapter() : ListAdapter<String, RecyclerView.ViewHolder>(DIFF_OPTION) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val viewBinding = GridItemOptionBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return GridHolder(viewBinding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is GridHolder ->{
                holder.bind(getItem(position))
            }
        }
    }

    class GridHolder(private val binding: GridItemOptionBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(item: String){
            var txt: String
            with(binding){
                when (item) {
                    "에어백" -> {
                        Glide.with(itemView).load(R.drawable.ic_option_airbag).into(imgGridOption)
                        txt = "에어백"
                    }
                    "블루투스" -> {
                        Glide.with(itemView).load(R.drawable.ic_option_bluetooth).into(imgGridOption)
                        txt = "블루투스"
                    }
                    "열선시트" -> {
                        Glide.with(itemView).load(R.drawable.ic_option_heat).into(imgGridOption)
                        txt = "열선시트"
                    }
                    "후방 감지 센서" -> {
                        Glide.with(itemView).load(R.drawable.ic_option_sensor).into(imgGridOption)
                        txt = "후방센서"
                    }
                    "네비게이션" -> {
                        Glide.with(itemView).load(R.drawable.ic_option_nav).into(imgGridOption)
                        txt = "네비게이션"
                    }
                    "에어컨" -> {
                        Glide.with(itemView).load(R.drawable.ic_option_air).into(imgGridOption)
                        txt = "에어컨"
                    }
                    else -> {//블랙박스
                        Glide.with(itemView).load(R.drawable.ic_option_blackbox).into(imgGridOption)
                        txt = "블랙박스"
                    }
                }
                txtGridOption.text = txt
            }

        }
    }

    companion object {
        val DIFF_OPTION: DiffUtil.ItemCallback<String> =
            object : DiffUtil.ItemCallback<String>() {
                override fun areItemsTheSame(
                    oldItem: String,
                    newItem: String
                ): Boolean {
                    return oldItem == newItem
                }

                override fun areContentsTheSame(
                    oldItem: String,
                    newItem: String
                ): Boolean {
                    return oldItem == newItem
                }
            }
    }
}