package com.example.taller01.ui.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.taller01.data.Producto
import com.example.taller01.ui.EstadoUiProductos
import com.example.taller01.ui.components.TarjetaProducto

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PantallaListaProductos(
    estadoUi: EstadoUiProductos,
    alHacerClicProducto: (Producto) -> Unit,
    modificador: Modifier = Modifier
) {
    when (estadoUi) {
        is EstadoUiProductos.Cargando -> {
            Box(modifier = modificador.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        is EstadoUiProductos.Error -> {
            Box(modifier = modificador.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = estadoUi.mensaje)
            }
        }

        is EstadoUiProductos.Exito -> {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = modificador.fillMaxSize(),
                contentPadding = PaddingValues(8.dp)
            ) {
                // Encabezado fijo (StickyHeader) con el total de productos, pedido en el taller
                stickyHeader {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.primaryContainer
                    ) {
                        Text(
                            text = "Total de productos: ${estadoUi.productos.size}",
                            modifier = Modifier.padding(12.dp),
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                }
                items(
                    items = estadoUi.productos,
                    key = { it.id }
                ) { producto ->
                    TarjetaProducto(
                        producto = producto,
                        alHacerClic = alHacerClicProducto,
                        modificador = Modifier.padding(6.dp)
                    )
                }
            }
        }
    }
}
