package com.example.sarastoreandroid.utils

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Clase ApiClient
 * Se encarga de gestionar la conexión con el backend de la aplicación.
 * Utiliza Retrofit para realizar peticiones HTTP y convertir las respuestas JSON en objetos de Kotlin.
 */
object ApiClient {

    // Dirección base del backend de la aplicación.
    // Si el backend cambia de servidor o de puerto, solo es necesario modificar esta constante.
    private const val BASE_URL = "http://10.0.2.2:8080/api/"

    // Instancia de Retrofit configurada con la URL base y el convertidor de JSON.
    val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL) // URL base del backend
            .addConverterFactory(GsonConverterFactory.create()) // Conversor de JSON a objetos de Kotlin
            .build()
    }
}
