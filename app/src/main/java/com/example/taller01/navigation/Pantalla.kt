package com.example.taller01.navigation

import androidx.navigation3.runtime.NavKey
import com.example.taller01.data.Producto
import kotlinx.serialization.Serializable

// Rutas de navegación de la app.
// DetalleProducto lleva el objeto Producto completo.
sealed interface Pantalla : NavKey {
    @Serializable
    data object ListaProductos : Pantalla

    @Serializable
    data class DetalleProducto(val producto: Producto) : Pantalla
}
