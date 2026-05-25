package com.example.metodosnumericos.api

import com.example.metodosnumericos.model.PuntosRequest
import com.example.metodosnumericos.model.ResultadoResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface MetodosApi {

    @POST("resolver/{metodo}")
    suspend fun resolverMetodo(
        @Path("metodo") metodo: String,
        @Body body: PuntosRequest
    ): Response<ResultadoResponse>
}