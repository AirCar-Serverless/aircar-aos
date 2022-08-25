package com.serverless.aircar

data class CarInfo(
    var carImgList: List<String> = listOf(),
    var hostProfileImg: String = "",
    var hostName: String = "",
    var hostRate: Double = 0.0,
    var carName: String = "",
    var carType1: String = "",
    var carType2: String = "",
    var carType3: String = "",
    var carType4: String = "",
    var carType5: String = "",
    var carType6: String = "",
    var carRate: Double = 0.0,
    var clientProfileImg: String = "",
    var clientName: String = "",
    var reviewDate: String = "",
    var review: String = "",
    var location_lat: String = "33.4503",
    var location_lng: String = "126.9184"
)

sealed class CarInfoData(
){
    data class HostInfo(
        var hostProfileImg: String,
        var hostName: String,
        var hostRate: Double
    ): CarInfoData()

    data class CarInfo(
        var carName: String,
        var carType1: String,
        var carType2: String,
        var carType3: String,
        var carType4: String,
        var carType5: String,
        var carType6: String //나중에 수정
    ): CarInfoData()

    data class CarReviewInfo(
        var carRate: Double
    ): CarInfoData()

    data class CarReview(
        var clientProfileImg: String,
        var clientName: String,
        var reviewDate: String,
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
