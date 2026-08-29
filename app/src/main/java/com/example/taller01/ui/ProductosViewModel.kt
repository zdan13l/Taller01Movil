package com.example.taller01.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taller01.data.Producto
import com.example.taller01.data.RepositorioProductos
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

// Estados posibles de la pantalla de lista de productos
sealed interface EstadoUiProductos {
    data object Cargando : EstadoUiProductos
    data class Exito(val productos: List<Producto>) : EstadoUiProductos
    data class Error(val mensaje: String) : EstadoUiProductos
}

class ProductosViewModel(
    private val repositorio: RepositorioProductos = RepositorioProductos()
) : ViewModel() {

    private val _estadoUi = MutableStateFlow<EstadoUiProductos>(EstadoUiProductos.Cargando)
    val estadoUi: StateFlow<EstadoUiProductos> = _estadoUi.asStateFlow()

    // Evita volver a consultar el API si el ViewModel se recompone (por ejemplo, al rotar la pantalla)
    private var yaCargado = false

    init {
        cargarProductos()
    }

    private fun cargarProductos() {
        if (yaCargado) return
        yaCargado = true
        viewModelScope.launch {
            _estadoUi.value = EstadoUiProductos.Cargando
            _estadoUi.value = try {
                val productos = repositorio.obtenerProductos()
                EstadoUiProductos.Exito(productos)
            } catch (excepcion: Exception) {
                EstadoUiProductos.Error(excepcion.message ?: "Error desconocido al cargar productos")
            }
        }
    }
}
