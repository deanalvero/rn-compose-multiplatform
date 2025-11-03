package io.github.deanalvero.rn.cmp.data

sealed class TextContent {
    data class Literal(val text: String) : TextContent()
    data class Binding(val stateKey: String) : TextContent()
}
