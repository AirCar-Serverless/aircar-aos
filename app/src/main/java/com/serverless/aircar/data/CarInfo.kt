package com.serverless.aircar.data

data class CarInfo(
    val cid: String,
    val imageUrl: String,
    val stars: Double,
    val reviewCount: Int,
    val price: Int,
    val name: String,
    val oilType: String,
    val startTime: String,
    val endTime: String
)