package com.example.lab09

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.lab09.ProductViewModel

@Composable
fun ProductListScreen(navController: NavHostController, viewModel: ProductViewModel) {
    LazyColumn(modifier = Modifier.fillMaxSize().padding(8.dp)) {
        items(viewModel.listaProductos) { product ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .clickable { navController.navigate("productDetail/${product.id}") },
                elevation = CardDefaults.cardElevation(6.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = rememberAsyncImagePainter(model = product.image),
                        contentDescription = product.title,
                        modifier = Modifier.size(80.dp).padding(8.dp),
                        contentScale = ContentScale.Fit
                    )
                    Column(modifier = Modifier.padding(8.dp)) {
                        Text(product.title, fontWeight = FontWeight.Bold)
                        Text("S/. ${product.price}", color = MaterialTheme.colorScheme.primary)
                    }
                }
            }
        }
    }
}

@Composable
fun ProductDetailScreen(viewModel: ProductViewModel, id: Int) {
    val producto = viewModel.productoSeleccionado.value
    if (producto == null) {
        viewModel.obtenerProductoPorId(id)
        CircularProgressIndicator(modifier = Modifier.padding(20.dp))
    } else {
        Column(modifier = Modifier.padding(16.dp)) {
            Image(
                painter = rememberAsyncImagePainter(model = producto.image),
                contentDescription = producto.title,
                modifier = Modifier.fillMaxWidth().height(250.dp),
                contentScale = ContentScale.Fit
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(producto.title, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
            Text("Categoría: ${producto.category}")
            Text("Precio: S/. ${producto.price}")
            Text(producto.description, modifier = Modifier.padding(top = 8.dp))
        }
    }
}