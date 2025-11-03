package io.github.deanalvero.rn.cmp.composable.basic

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import io.github.deanalvero.rn.cmp.data.AttributeValue
import io.github.deanalvero.rn.cmp.data.ReactNativeState
import io.github.deanalvero.rn.cmp.data.TextContent
import io.github.deanalvero.rn.cmp.data.UINode
import io.github.deanalvero.rn.cmp.modifier.applyReactNativeStyle
import io.github.deanalvero.rn.cmp.parser.ColorParser

@Composable
fun TextComposable(node: UINode, state: ReactNativeState) {
    val style = node.attributes["style"] as? AttributeValue.StyleObject

    val textContent = when (val content = node.textContent) {
        is TextContent.Literal -> content.text
        is TextContent.Binding -> {
            val stateValue by remember(content.stateKey) {
                derivedStateOf { state.getState<Any>(content.stateKey)?.toString() ?: "" }
            }
            stateValue
        }
        null -> ""
    }

    val color = style?.properties?.get("color")?.let { colorVal ->
        (colorVal as? AttributeValue.StringValue)?.value?.let { colorStr ->
            try {
                ColorParser.parse(colorStr)
            } catch (e: Exception) {
                null
            }
        }
    } ?: Color.Unspecified

    val fontSize = style?.properties?.get("fontSize")?.let { sizeVal ->
        (sizeVal as? AttributeValue.NumberValue)?.value?.toInt()?.sp
    } ?: 14.sp

    val fontWeight = style?.properties?.get("fontWeight")?.let { weightVal ->
        when ((weightVal as? AttributeValue.StringValue)?.value) {
            "bold" -> FontWeight.Bold
            else -> FontWeight.Normal
        }
    } ?: FontWeight.Normal

    Text(
        text = textContent,
        modifier = Modifier.applyReactNativeStyle(node.attributes),
        style = TextStyle(
            color = color,
            fontSize = fontSize,
            fontWeight = fontWeight
        )
    )
}
