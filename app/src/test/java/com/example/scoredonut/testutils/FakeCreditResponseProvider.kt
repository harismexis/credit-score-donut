package com.example.scoredonut.testutils

import com.example.scoredonut.model.CreditResponse
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject

private fun convertToCreditResponse(jsonString: String?): CreditResponse {
    val gson = GsonBuilder().setLenient().create()
    val json: JsonObject = gson.fromJson(jsonString, JsonObject::class.java)
    return Gson().fromJson(json, CreditResponse::class.java)
}

fun getFakeCreditResponseValid(): CreditResponse {
    return convertToCreditResponse(getCreditResponseValid())
}

fun getFakeCreditResponseNoScore(): CreditResponse {
    return convertToCreditResponse(getCreditResponseNoScore())
}

fun getFakeCreditResponseNoMaxScore(): CreditResponse {
    return convertToCreditResponse(getCreditResponseNoMaxScore())
}

fun getFakeCreditResponseNoCreditReportInfo(): CreditResponse {
    return convertToCreditResponse(getCreditResponseNoCreditReportInfo())
}
