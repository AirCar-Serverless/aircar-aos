package com.serverless.aircar.data

data class Option(
    var options: List<String> = listOf()
)

sealed class OptionData(
){
    data class OptionImg(
        var optionImg: String
    ): OptionData()
    data class OptionType(
        var optionType: String
    ): OptionData()
}