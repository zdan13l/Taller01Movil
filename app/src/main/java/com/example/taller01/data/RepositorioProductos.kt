package com.example.taller01.data

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

private const val URL_PRODUCTOS = "https://dummyjson.com/products?limit=194"

// Encargado de consultar el API de DummyJSON. El ViewModel lo invoca una sola vez.
class RepositorioProductos {

    private val cliente = HttpClient(OkHttp) {
        install(ContentNegotiation) {
            json(Json { ignoreUnknownKeys = true })
        }
    }

    suspend fun obtenerProductos(): List<Producto> {
        val respuesta: RespuestaProductos = cliente.get(URL_PRODUCTOS).body()
        return respuesta.productos
    }
}
