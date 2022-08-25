package com.serverless.aircar

data class CarInfo(
    var carImgList: List<String> = listOf(),
    var hostProfileImg: String = "",
    var hostName: String = "",
    var hostRate: String = "",
    var carName: String = "",
    var carInfoType: String = "",
    var carInfoShapeType: String = "",
    var carInfoCount: Int = 0,
    var carInfoFuelType: String = "",
    var carInfoDetail: String = "",
    var carOptions: List<String> = listOf(),
    var reviewCnt: Int = 0,
    var carRate: Double = 0.0,
    var clientProfileImg: String = "",
    var clientName: String = "",
    var reviewDate: String = "",
    var reviewRate: Double = 0.0,
    var review: String = "",
    var location_lat: String = "33.4503",
    var location_lng: String = "126.9184"
)

sealed class CarInfoData(
){
    data class HostInfo(
        var hostProfileImg: String,
        var hostName: String,
        var hostRate: String
    ): CarInfoData()

    data class CarInfo(
        var carName: String,
        var carInfoType: String,
        var carInfoShapeType: String,
        var carInfoCount: Int,
        var carInfoFuelType: String,
        var carInfoDetail: String//나중에 수정
    ): CarInfoData()

    data class CarReviewInfo(
        var carReviewCnt: Int,
        var carRate: Double
    ): CarInfoData()

    data class CarReview(
        var clientProfileImg: String,
        var clientName: String,
        var reviewDate: String,
        var reviewRate: Double,
        var review: String,
    ): CarInfoData()

    data class CarLocation(
        var location_lat: String,
        var location_lng: String
    ): CarInfoData()

}


//data class IssueData(
//    var id: Int,
//    var url: String = "",
//    var number: String = "",
//    var title: String = "",
//    var body: String = ""
//)
//
//sealed class Issue(open var url: String,
//) {
//    data class GithubIssue(
//        override var url: String,
//        var number: String,
//        var title: String,
//        var body: String = ""
//    ) : Issue(url)
//
//    data class Image(
//        override var url: String = "",
//        val imageLink: String,
//        val body: String
//    ) : Issue(url)
//}
