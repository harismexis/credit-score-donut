package com.example.scoredonut.api

import com.example.scoredonut.model.CreditResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface CreditApi {

    @GET("{credit}")
    fun getCreditInfo(
        @Path("credit") credit: String,
    ): Single<CreditResponse?>

}