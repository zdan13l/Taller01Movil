package com.example.taller01.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.SuggestionChipDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.taller01.R
import com.example.taller01.data.Producto

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaDetalleProducto(
    producto: Producto,
    alVolver: () -> Unit,
    modificador: Modifier = Modifier
) {
    val contexto = LocalContext.current
    val mensajeSms = stringResource(
        R.string.mensaje_sms,
        producto.titulo,
        producto.descripcion
    )

    Scaffold(
        modifier = modificador,
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(R.string.titulo_detalle_producto))
                },
                navigationIcon = {
                    IconButton(onClick = alVolver) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.volver)
                        )
                    }
                }
            )
        }
    ) { paddingInterno ->
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(paddingInterno),
            contentPadding = PaddingValues(16.dp)
        ) {
            item {
                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(producto.imagenes.ifEmpty { listOf(producto.miniatura) }) { urlImagen ->
                        AsyncImage(
                            model = urlImagen,
                            contentDescription = producto.titulo,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .height(200.dp)
                                .fillMaxWidth(0.9f)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
            }

            item {
                Text(
                    text = producto.titulo,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.clickable {
                        val intencionLlamada = Intent(Intent.ACTION_DIAL).apply {
                            data = Uri.parse("tel:${producto.id}")
                        }
                        contexto.startActivity(intencionLlamada)
                    }
                )
                Spacer(modifier = Modifier.height(4.dp))
            }

            item {
                Text(
                    text = stringResource(R.string.categoria, producto.categoria),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(12.dp))
            }

            item {
                Text(
                    text = producto.descripcion,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.clickable {
                        val intencionSms = Intent(Intent.ACTION_SEND).apply {
                            setDataAndType(Uri.parse("smsto:"), "text/plain")
                            putExtra(Intent.EXTRA_TEXT, mensajeSms)
                        }
                        contexto.startActivity(intencionSms)
                    }
                )
                Spacer(modifier = Modifier.height(16.dp))
            }

            item {
                Text(
                    text = stringResource(R.string.etiquetas),
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(8.dp))

                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(producto.etiquetas) { etiqueta ->
                        SuggestionChip(
                            onClick = {},
                            label = { Text(etiqueta) }
                        )
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
            }

            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    elevation = CardDefaults.cardElevation(1.dp)
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text(
                            text = stringResource(R.string.dimensiones),
                            style = MaterialTheme.typography.titleMedium
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = stringResource(
                                R.string.ancho,
                                producto.dimensiones.ancho
                            )
                        )
                        Text(
                            text = stringResource(
                                R.string.alto,
                                producto.dimensiones.alto
                            )
                        )
                        Text(
                            text = stringResource(
                                R.string.profundidad,
                                producto.dimensiones.profundidad
                            )
                        )
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
            }

            item {
                Text(
                    text = stringResource(
                        R.string.resenas,
                        producto.resenas.size
                    ),
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(8.dp))
            }

            items(producto.resenas) { resena ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    shape = RoundedCornerShape(12.dp),
                    elevation = CardDefaults.cardElevation(1.dp)
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = resena.nombreAutor,
                                fontWeight = FontWeight.SemiBold
                            )

                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Filled.Star,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.tertiary
                                )
                                Text(text = resena.calificacion.toString())
                            }
                        }

                        Text(
                            text = resena.comentario,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }

            item {
                HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
                Text(
                    text = stringResource(R.string.precio, producto.precio),
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
    }
}