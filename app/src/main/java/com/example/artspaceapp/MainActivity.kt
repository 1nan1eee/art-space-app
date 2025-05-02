package com.example.artspaceapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.lifecycle.ViewModelProvider
import com.example.artspaceapp.ui.viewmodel.ArtworkViewModel
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.TextStyle

class MainActivity : ComponentActivity() {
    private lateinit var viewModel: ArtworkViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[ArtworkViewModel::class.java]

        setContent {
            ArtworkGallery(viewModel = viewModel)
        }
    }
}

@Composable
fun ArtworkGallery(viewModel: ArtworkViewModel, modifier: Modifier = Modifier) {
    val artworks by viewModel.artworks.observeAsState(emptyList()) // подписка на изменения списка картин
    val currentIndex by viewModel.currentIndex.observeAsState(0) // подписка на изменения текущего индекса

    val artwork = if (artworks.isNotEmpty()) artworks[currentIndex] else null // получаем текущее произведение искусства

    val configuration = LocalConfiguration.current
    val isPortrait = configuration.orientation == android.content.res.Configuration.ORIENTATION_PORTRAIT

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        if (isPortrait) { // портретная ориентация
            Column(
                modifier = Modifier.align(Alignment.Center)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                artwork?.let {
                    Image(
                        painter = painterResource(id = it.imageResId),
                        contentDescription = "Artwork Image",
                        modifier = Modifier.width(350.dp)
                    )

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(vertical = 5.dp)
                    ) {
                        Text(text = it.title, style = MaterialTheme.typography.titleLarge)
                        Text(text = it.description, style = MaterialTheme.typography.bodyMedium)
                    }
                }
            }

            Row(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .padding(50.dp),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Button(onClick = {
                    viewModel.previousArtwork()
                }) {
                    Text("Предыдущая")
                }

                Button(onClick = {
                    viewModel.nextArtwork()
                }) {
                    Text("Следующая")
                }
            }
        } else { // ландшафтная ориентация
            Row(
                modifier = Modifier.align(Alignment.Center)
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(onClick = {
                    viewModel.previousArtwork()
                }) {
                    Text("Предыдущая")
                }

                artwork?.let {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    ) {
                        Image(
                            painter = painterResource(id = it.imageResId),
                            contentDescription = "Artwork Image",
                            modifier = Modifier.height(250.dp)
                        )
                        Text(text = it.title, style = MaterialTheme.typography.titleLarge)
                        Text(text = it.description, style = MaterialTheme.typography.bodyMedium)
                    }
                }

                Button(onClick = {
                    viewModel.nextArtwork()
                }) {
                    Text("Следующая")
                }
            }
        }
    }
}
