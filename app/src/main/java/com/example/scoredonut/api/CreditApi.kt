package com.example.scoredonut.api

import com.example.scoredonut.model.CreditResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface CreditApi {

    @GET("{creditEndpoint}")
    suspend fun getCreditScore(
        @Path("creditEndpoint") creditEndpoint: String,
    ): CreditResponse?

}