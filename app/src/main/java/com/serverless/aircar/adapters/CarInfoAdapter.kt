package com.serverless.aircar.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.serverless.aircar.data.CarInfo
import com.serverless.aircar.databinding.ListItemCarBinding
import java.text.DecimalFormat

class CarInfoAdapter : ListAdapter<CarInfo, RecyclerView.ViewHolder>(CarInfoDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return CarInfoViewHolder(
            ListItemCarBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val carInfo = getItem(position)
        (holder as CarInfoViewHolder).bind(carInfo)
    }

    class CarInfoViewHolder(
        private val binding: ListItemCarBinding
    ) :RecyclerView.ViewHolder(binding.root) {
        init {
            binding.setClickListener {
                // TODO: 화면 이동
            }
        }

        fun bind(item: CarInfo) {
            binding.apply {
                carInfo = item
                tvPrice.text = DecimalFormat("#,###원").format(item.price)
                executePendingBindings()
            }
            Glide.with(itemView)
                .load(item.imageUrl)
                .transform(CenterCrop(), RoundedCorners(15))
                .into(binding.imgCar)
        }
    }
}

private class CarInfoDiffCallback : DiffUtil.ItemCallback<CarInfo>() {

    override fun areItemsTheSame(oldItem: CarInfo, newItem: CarInfo): Boolean {
        return oldItem.cid == newItem.cid
    }

    override fun areContentsTheSame(oldItem: CarInfo, newItem: CarInfo): Boolean {
        return oldItem == newItem
    }
}
