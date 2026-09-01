package com.example.taller01.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

// Respuesta que entrega el API al consultar la lista de productos.
@Serializable
data class RespuestaProductos(
    @SerialName("products") val productos: List<Producto> = emptyList(), val total: Int = 0,
    @SerialName("skip") val omitidos: Int = 0,
    @SerialName("limit") val limite: Int = 0
)

// Representa un producto individual devuelto por el API.
@Serializable
data class Producto(val id: Int,
    @SerialName("title") val titulo: String,
    @SerialName("description") val descripcion: String,
    @SerialName("category") val categoria: String,
    @SerialName("price") val precio: Double,
    @SerialName("rating") val calificacion: Double,
    @SerialName("tags") val etiquetas: List<String> = emptyList(),
    @SerialName("brand") val marca: String? = null,
    @SerialName("thumbnail") val miniatura: String,
    @SerialName("images") val imagenes: List<String> = emptyList(),
    @SerialName("dimensions") val dimensiones: Dimensiones = Dimensiones(),
    @SerialName("reviews") val resenas: List<Resena> = emptyList()
)

@Serializable
data class Dimensiones(
    @SerialName("width") val ancho: Double = 0.0,
    @SerialName("height") val alto: Double = 0.0,
    @SerialName("depth") val profundidad: Double = 0.0
)

@Serializable
data class Resena(
    @SerialName("rating") val calificacion: Int = 0,
    @SerialName("comment") val comentario: String = "",
    @SerialName("date") val fecha: String = "",
    @SerialName("reviewerName") val nombreAutor: String = "",
    @SerialName("reviewerEmail") val correoAutor: String = ""
)
