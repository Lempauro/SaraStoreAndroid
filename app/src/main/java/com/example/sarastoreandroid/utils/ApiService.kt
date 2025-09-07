package com.example.sarastoreandroid.utils

import com.example.sarastoreandroid.models.Product
import retrofit2.Call
import retrofit2.http.GET

/**
 * Interfaz ApiService: endpoints expuestos por el backend.
 */
interface ApiService {

    /**
     * GET /api/productos  -> devuelve la lista de productos.
     * Ajusta la ruta si el backend usa otro nombre.
     */
    @GET("productos")
    fun getProducts(): Call<List<Product>>
}
