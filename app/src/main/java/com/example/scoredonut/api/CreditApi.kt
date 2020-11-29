package com.example.scoredonut.api

import com.example.scoredonut.model.CreditScoreResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface CreditApi {

    @GET("{credit}")
    fun getCreditScore(
        @Path("credit") credit: String,
    ): Single<CreditScoreResponse?>

}