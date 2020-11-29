package com.example.scoredonut.api

import com.example.scoredonut.model.CreditScoreResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface CreditApi {

    @GET("{creditEndpoint}")
    fun getCreditScore(
        @Path("creditEndpoint") creditEndpoint: String,
    ): Single<CreditScoreResponse?>

}