package com.example.lab09.screens

import com.example.lab09.model.PostModel // Clase de datos
import com.example.lab09.api.PostApiService // Interfaz de Retrofit

import android.util.Log
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.ui.graphics.Color

// -------------------------------------------------------------------
// FUNCIÓN 1: MUESTRA LA LISTA DE POSTS
// -------------------------------------------------------------------
@Composable
fun ScreenPosts(navController: NavHostController, servicio: PostApiService) {
    var listaPosts: SnapshotStateList<PostModel> = remember { mutableStateListOf() }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        try {
            val listado = servicio.getUserPosts()
            listaPosts.clear()
            listaPosts.addAll(listado)
        } catch (e: Exception) {
            errorMessage = "Error: ${e.message}"
            // CORRECCIÓN: Sintaxis posicional de Log.e
            Log.e("ScreenPosts", "Error fetching posts", e)
        }
    }

    Column {
        if (errorMessage != null) {
            Text(
                text = errorMessage!!,
                color = Color.Red,
                modifier = Modifier.padding(16.dp)
            )
        }

        LazyColumn {
            items(listaPosts) { item ->
                Row(modifier = Modifier.padding(all = 8.dp)) {
                    Text(
                        text = item.id.toString(),
                        modifier = Modifier.weight(0.05f),
                        textAlign = TextAlign.End
                    )
                    Spacer(Modifier.padding(horizontal = 1.dp))
                    Text(
                        text = item.title,
                        modifier = Modifier.weight(0.7f)
                    )
                    IconButton(
                        onClick = {
                            navController.navigate(route = "postsVer/${item.id}")
                            // CORRECCIÓN: Sintaxis posicional de Log.e
                            Log.e("POSTS", "ID = ${item.id}")
                        },
                        modifier = Modifier.weight(0.1f)
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Search,
                            contentDescription = "Ver"
                        )
                    }
                }
            }
        }
    }
}

// -------------------------------------------------------------------
// FUNCIÓN 2: MUESTRA EL DETALLE DE UN POST
// -------------------------------------------------------------------
@Composable
fun ScreenPost(navController: NavHostController, servicio: PostApiService, id: Int) {
    var post by remember { mutableStateOf<PostModel?>(null) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(id) {
        try {
            isLoading = true
            val xpost = servicio.getUserPostById(id)
            post = xpost
        } catch (e: Exception) {
            errorMessage = "Error: ${e.message}"
            // CORRECCIÓN: Sintaxis posicional de Log.e
            Log.e("ScreenPost", "Error fetching post $id", e)
        } finally {
            isLoading = false
        }
    }

    Column(
        Modifier
            .padding(8.dp)
            .fillMaxSize()
    ) {
        if (isLoading) {
            Text("Cargando...")
        } else if (errorMessage != null) {
            Text(
                text = errorMessage!!,
                color = Color.Red
            )
        } else if (post != null) {
            OutlinedTextField(
                value = post!!.id.toString(),
                onValueChange = {},
                label = { Text("id") },
                readOnly = true,
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = post!!.userId.toString(),
                onValueChange = {},
                label = { Text("userId") },
                readOnly = true,
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = post!!.title,
                onValueChange = {},
                label = { Text("title") },
                readOnly = true,
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = post!!.body,
                onValueChange = {},
                label = { Text("body") },
                readOnly = true,
                modifier = Modifier.fillMaxWidth()
            )
        } else {
            Text("No se encontró el post")
        }
    }
}