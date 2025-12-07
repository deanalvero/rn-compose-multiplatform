package io.github.deanalvero.renderer.rn.demoapp.data

import androidx.compose.runtime.Composable

data class ComponentData(
    val name: String,
    val codeSnippet: String,
    val content: @Composable () -> Unit
)