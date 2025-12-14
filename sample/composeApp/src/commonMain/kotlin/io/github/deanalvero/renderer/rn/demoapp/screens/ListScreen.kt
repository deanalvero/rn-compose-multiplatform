package io.github.deanalvero.renderer.rn.demoapp.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import io.github.deanalvero.renderer.rn.demoapp.data.ComponentData

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ComponentListScreen(
    components: List<ComponentData>,
    onComponentSelected: (ComponentData) -> Unit
) {
    Scaffold(
        topBar = { TopAppBar(title = { Text("React Native Compose Multiplatform") }) }
    ) { padding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(padding)
        ) {
            items(components) { component ->
                ListItem(
                    headlineContent = { Text(component.name, fontWeight = FontWeight.Bold) },
                    modifier = Modifier
                        .clickable { onComponentSelected(component) }
                )
                HorizontalDivider()
            }
        }
    }
}