package com.example.lab09

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items // ✅ Importar items para LazyColumn
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
import androidx.compose.runtime.setValue // ✅ Importar setValue para 'var post by remember'
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

// 👇 INSERTAR ESTOS IMPORTS
import com.example.lab09.api.PostApiService
import com.example.lab09.model.PostModel



@Composable
fun ScreenPosts(navController: NavHostController, servicio: PostApiService) {
    var listaPosts: SnapshotStateList<PostModel> = remember { mutableStateListOf() }

    // Añadir manejo de errores básico
    var errorMessage by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        try {
            val listado = servicio.getUserPosts()
            listaPosts.clear()
            listaPosts.addAll(listado)
        } catch (e: Exception) {
            errorMessage = "Error al cargar: ${e.message}"
            Log.e("ScreenPosts", "Error fetching posts", e)
        }
    }

    Column { // Usamos Column para poner el mensaje de error encima de LazyColumn
        if (errorMessage != null) {
            Text(text = errorMessage!!, color = androidx.compose.ui.graphics.Color.Red, modifier = Modifier.padding(16.dp))
        }

        LazyColumn { // ✅ items DEBE IR DENTRO DEL BLOQUE LazyColumn
            items(listaPosts) { item ->
                Row(modifier = Modifier.padding(8.dp)) {
                    Text(text = item.id.toString(), Modifier.weight(0.05f), textAlign = TextAlign.End)
                    Spacer(Modifier.padding(horizontal=1.dp))
                    Text(text = item.title, Modifier.weight(0.7f))
                    IconButton(
                        onClick = {
                            navController.navigate("postsVer/${item.id}")
                            Log.e("POSTS","ID = ${item.id}")
                        },
                        Modifier.weight(0.1f)
                    ) {
                        Icon(imageVector = Icons.Outlined.Search, contentDescription = "Ver")
                    }
                }
            }
        }
    }
}

@Composable
fun ScreenPost(navController: NavHostController, servicio: PostApiService, id: Int) {
    var post by remember { mutableStateOf<PostModel?>(null) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(id) { // Usamos 'id' como clave para que se recargue si el id cambia
        try {
            val xpost = servicio.getUserPostById(id)
            post = xpost // ✅ Asignación directa
        } catch (e: Exception) {
            errorMessage = "Error al cargar post $id: ${e.message}"
            Log.e("ScreenPost", "Error fetching post $id", e)
        }
    }

    Column(
        Modifier
            .padding(8.dp)
            .fillMaxSize()
    ) {
        if (errorMessage != null) {
            Text(text = errorMessage!!, color = androidx.compose.ui.graphics.Color.Red)
        } else if (post != null){
            OutlinedTextField(
                value = post!!.id.toString(),
                onValueChange = {},
                label = { Text("id") },
                readOnly = true
            )
            OutlinedTextField(
                value = post!!.userId.toString(),
                onValueChange = {},
                label = { Text("userId") },
                readOnly = true
            )
            OutlinedTextField(
                value = post!!.title, // ✅ Eliminado .toString() si ya es String
                onValueChange = {},
                label = { Text("title") },
                readOnly = true
            )
            OutlinedTextField(
                value = post!!.body, // ✅ Eliminado .toString() si ya es String
                onValueChange = {},
                label = { Text("body") },
                readOnly = true
            )
        } else {
            // Mensaje mientras carga, si no hubo error
            Text("Cargando post $id...")
        }
    }
}