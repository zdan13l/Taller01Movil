package com.example.taller01

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.example.taller01.navigation.Pantalla
import com.example.taller01.ui.ProductosViewModel
import com.example.taller01.ui.screens.PantallaDetalleProducto
import com.example.taller01.ui.screens.PantallaListaProductos
import com.example.taller01.ui.theme.Taller01Theme

class ActividadPrincipal : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Taller01Theme {
                AplicacionEcommerce()
            }
        }
    }
}

// Composable, encargado de crear el ViewModel (consulta API solo una vez).
@Composable
fun AplicacionEcommerce(modeloVista: ProductosViewModel = viewModel()) {
    val estadoUi by modeloVista.estadoUi.collectAsState()
    val pilaNavegacion = rememberNavBackStack(Pantalla.ListaProductos)

    NavDisplay(
        backStack = pilaNavegacion,
        modifier = Modifier.fillMaxSize(),
        onBack = { pilaNavegacion.removeLastOrNull() },
        entryProvider = entryProvider {
            entry<Pantalla.ListaProductos> {
                PantallaListaProductos(
                    estadoUi = estadoUi,
                    alHacerClicProducto = { producto -> pilaNavegacion.add(Pantalla.DetalleProducto(producto)) }
                )
            }
            entry<Pantalla.DetalleProducto> { clave ->
                PantallaDetalleProducto(
                    producto = clave.producto,
                    alVolver = { pilaNavegacion.removeLastOrNull() }
                )
            }
        }
    )
}
