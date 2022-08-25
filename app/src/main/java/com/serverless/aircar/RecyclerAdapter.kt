package com.serverless.aircar

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.models.SlideModel
import com.serverless.aircar.databinding.*
import de.hdodenhof.circleimageview.CircleImageView
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.interfaces.ItemChangeListener
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView

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
                carType1 = item.carType1,
                carType2 = item.carType2,
                carType3 = item.carType3,
                carType4 = item.carType4,
                carType5 = item.carType5,
                carType6 = item.carType6
            )
            with(binding){
                txtCarInfoName.text = carInfo.carName
                txtContent1.text = carInfo.carType1
                txtContent2.text = carInfo.carType2
                txtContent3.text = carInfo.carType3
                txtContent4.text = carInfo.carType4
                txtContent5.text = carInfo.carType5
                txtContent6.text = carInfo.carType6
            }
        }
    }
    // RATE
    class RateHolder(private val binding: ListItemReviewStarsBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(item: CarInfo){
            var carRate = CarInfoData.CarReviewInfo(
                carRate = item.carRate
            )
            binding.iconRating.rating = carRate.carRate.toFloat()
        }
    }
    // REVIEW
    class CarReviewHolder(private val binding: ListItemReviewBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(item: CarInfo){
            var reviewData = CarInfoData.CarReview(
                clientProfileImg = item.clientProfileImg,
                clientName = item.clientName,
                reviewDate = item.reviewDate,
                review = item.review
            )
            with(binding){
                if(reviewData.clientProfileImg.isNotBlank()) Glide.with(itemView).load(reviewData.clientProfileImg).into(imgClientProfile)
                else Glide.with(itemView).load(R.drawable.img_default_profile).into(imgClientProfile)
                txtClientName.text = reviewData.clientName
                txtClientTime.text = "${reviewData.reviewDate} 전"
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
    class LocationHolder(private val binding: ListItemLocationBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(item: CarInfo){
//            mapView = MapView()
//            binding.viewLocationMap.addView(mapView)
//            mapView.currentLocationTrackingMode = MapView.CurrentLocationTrackingMode.TrackingModeOnWithHeading
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> InfoType.IMAGE.ordinal
            1 -> InfoType.HOSTINFO.ordinal
            2 -> InfoType.CARINFO.ordinal
            3 -> InfoType.RATE.ordinal
            7 -> InfoType.BUTTON.ordinal
            8 -> InfoType.LOCATION.ordinal
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
        IMAGE, HOSTINFO, CARINFO, RATE, REVIEW, BUTTON, LOCATION
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