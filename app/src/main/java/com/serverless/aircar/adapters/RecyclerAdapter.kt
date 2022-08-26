package com.serverless.aircar.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.denzcoskun.imageslider.models.SlideModel
import com.serverless.aircar.databinding.*
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.interfaces.ItemChangeListener
import com.serverless.aircar.CarInfo
import com.serverless.aircar.CarInfoData
import com.serverless.aircar.HomeFragment
import com.serverless.aircar.R
import com.serverless.aircar.data.Option
import net.daum.android.map.MapView

class RecyclerAdapter() : ListAdapter<CarInfo, RecyclerView.ViewHolder>(DIFF_CAR_INFO) {

    var datas = mutableListOf<CarInfo>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            //이미지 슬라이드
            InfoType.IMAGE.ordinal -> {
                val viewBinding = ListItemImgBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                ImageHolder(viewBinding)
            }
            //호스트 정보
            InfoType.HOSTINFO.ordinal -> {
                val viewBinding = ListItemHostInfoBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                HostHolder(viewBinding)
            }
            //차량 정보
            InfoType.CARINFO.ordinal -> {
                val viewBinding = ListItemCarInfoBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                CarInfoHolder(viewBinding)
            }
            InfoType.OPTION.ordinal -> {
                val viewBinding = ListItemOptionBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                GridHolder(viewBinding)
            }
            //리뷰 정보
            InfoType.RATE.ordinal -> {
                val viewBinding = ListItemReviewStarsBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                RateHolder(viewBinding)
            }
            //리뷰
            InfoType.REVIEW.ordinal -> {
                val viewBinding = ListItemReviewBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                CarReviewHolder(viewBinding)
            }
            //지도
            InfoType.LOCATION.ordinal -> {
                val viewBinding = ListItemLocationBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                LocationHolder(viewBinding)
            }
            //버튼
            else -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_button, parent, false)
                ButtonHolder(view)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ImageHolder -> {
                holder.bind(getItem(position))
            }
            is HostHolder -> {
                holder.bind(getItem(position))
            }
            is CarInfoHolder -> {
                holder.bind(getItem(position))
            }
            is GridHolder -> {
                holder.bind(getItem(position))
            }
            is RateHolder -> {
                holder.bind(getItem(position))
            }
            is CarReviewHolder -> {
                holder.bind(getItem(position))
            }
            is ButtonHolder -> {
                holder.bind()
            }
            is LocationHolder -> {

                holder.bind(getItem(position))
            }
        }
    }

    // 이미지, 텍스트에 따른 각 홀더 만들어두기
    //IMAGE
    class ImageHolder(private val binding: ListItemImgBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(item: CarInfo){
            val imageList = ArrayList<SlideModel>()
            for (img in item.carImgList){
                imageList.add(SlideModel(img))
            }
//            imageList.add(SlideModel("https://bit.ly/2YoJ77H"))
            val imageListSize = imageList.size
            binding.imageSlider.setImageList(imageList, ScaleTypes.CENTER_CROP)
            binding.txtImgSlidePos.text = "1/${imageListSize}"
            binding.imageSlider.setItemChangeListener(object : ItemChangeListener {
                override fun onItemChanged(position: Int) {
                    //println("Pos: " + position)
                    binding.txtImgSlidePos.text = "${position+1}/${imageListSize}"
                }
            })
        }
    }
    // HOSTINFO
    class HostHolder(private val binding: ListItemHostInfoBinding):RecyclerView.ViewHolder(binding.root){
        @SuppressLint("SetTextI18n")
        fun bind(item: CarInfo){
            var hostInfo = CarInfoData.HostInfo(
                hostProfileImg = item.hostProfileImg,
                hostName = item.hostName,
                hostRate = item.hostRate
            )
            with(binding){
                if(hostInfo.hostProfileImg.isNotBlank()) Glide.with(itemView).load(hostInfo.hostProfileImg).into(imgHostProfile)
                else Glide.with(itemView).load(R.drawable.img_default_profile).into(imgHostProfile)
                txtHostName.text = hostInfo.hostName
            }
        }
    }
    // CARINFO
    class CarInfoHolder(private val binding: ListItemCarInfoBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(item: CarInfo){
            var carInfo = CarInfoData.CarInfo(
                carName = item.carName,
                carInfoType = item.carInfoType,
                carInfoShapeType = item.carInfoShapeType,
                carInfoCount = item.carInfoCount,
                carInfoFuelType = item.carInfoFuelType,
                carInfoDetail = item.carInfoDetail
            )
            with(binding){
                txtCarInfoName.text = carInfo.carName
                txtCarInfoType.text = carInfo.carInfoType
                txtCarInfoShapeType.text = carInfo.carInfoShapeType
                txtCarInfoCount.text = carInfo.carInfoCount
                txtCarInfoFuelType.text = carInfo.carInfoFuelType
                if(carInfo.carInfoDetail==0){
                    txtCarInfoDetail.text = "금연차량"
                }
            }
        }
    }
    // GRID
    class GridHolder(private val binding: ListItemOptionBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(item: CarInfo){
            val adapter = GridRecyclerAdapter()
            var listManager = GridLayoutManager(binding.root.context, 4)
            binding.gridRecyclerView.adapter = adapter
            binding.gridRecyclerView.layoutManager = listManager
            adapter?.let {
                adapter.submitList(item.carOptions)
                adapter.notifyDataSetChanged()
            }
        }
    }

    // RATE
    class RateHolder(private val binding: ListItemReviewStarsBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(item: CarInfo){
            var carRate = CarInfoData.CarReviewInfo(
                carReviewCnt = item.reviewCnt,
                carRate = item.carRate
            )
            binding.txtReviewTitle.text = "리뷰 (${carRate.carReviewCnt})"
            binding.txtReviewRate.text = carRate.carRate.toString()
        }
    }
    // REVIEW
    class CarReviewHolder(private val binding: ListItemReviewBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(item: CarInfo){
            var reviewData = CarInfoData.CarReview(
                clientProfileImg = item.clientProfileImg,
                clientName = item.clientName,
                reviewDate = item.reviewDate,
                reviewRate = item.reviewRate,
                review = item.review
            )
            with(binding){
                if(reviewData.clientProfileImg.isNotBlank()) Glide.with(itemView).load(reviewData.clientProfileImg).into(imgClientProfile)
                else Glide.with(itemView).load(R.drawable.img_default_profile).into(imgClientProfile)
                txtClientName.text = reviewData.clientName
                txtClientTime.text = "${reviewData.reviewDate} 전"
                txtRatingNum.text = reviewData.reviewRate.toString()
                txtReviewContent.text = reviewData.review
            }
        }
    }
    // BUTTON
    class ButtonHolder(v: View): RecyclerView.ViewHolder(v){
        private var view: View = v
        fun bind(){
        }
    }
    //LOCATION
    inner class LocationHolder(private val binding: ListItemLocationBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(item: CarInfo){
            binding.viewLocationMap.addView()
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> InfoType.IMAGE.ordinal
            1 -> InfoType.HOSTINFO.ordinal
            2 -> InfoType.CARINFO.ordinal
            3 -> InfoType.OPTION.ordinal
            4 -> InfoType.RATE.ordinal
            8 -> InfoType.BUTTON.ordinal
            9 -> InfoType.LOCATION.ordinal
            else -> InfoType.REVIEW.ordinal
        }
    }

//    private fun showMarker(price: String, lat: Double, lng: Double) {
//        // 마커 찍기
//        val marker = MapPOIItem()
//        marker.apply {
//            itemName = price
//            mapPoint = MapPoint.mapPointWithGeoCoord(lat, lng)
//            markerType = MapPOIItem.MarkerType.RedPin
//        }
//        mapView.addPOIItem(marker)
//    }

    enum class InfoType {
        IMAGE, HOSTINFO, CARINFO, OPTION, RATE, REVIEW, LOCATION, BUTTON
    }

    companion object {
        val DIFF_CAR_INFO: DiffUtil.ItemCallback<CarInfo> =
            object : DiffUtil.ItemCallback<CarInfo>() {
                override fun areItemsTheSame(
                    oldItem: CarInfo,
                    newItem: CarInfo
                ): Boolean {
                    return oldItem == newItem
                }

                override fun areContentsTheSame(
                    oldItem: CarInfo,
                    newItem: CarInfo
                ): Boolean {
                    return oldItem.carName == newItem.carName
                }
            }
    }
}