package io.github.deanalvero.rn.cmp.modifier

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import io.github.deanalvero.rn.cmp.data.AttributeValue
import io.github.deanalvero.rn.cmp.parser.ColorParser
import kotlin.collections.component1
import kotlin.collections.component2

fun Modifier.applyReactNativeStyle(attributes: Map<String, AttributeValue>): Modifier {
    var modifier = this
    val style = attributes["style"] as? AttributeValue.StyleObject ?: return this

    style.properties.forEach { (key, value) ->
        modifier = when (key) {
            "backgroundColor" -> {
                (value as? AttributeValue.StringValue)?.value?.let { colorStr ->
                    try {
                        ColorParser.parse(colorStr)?.let { modifier.background(it) } ?: modifier
                    } catch (e: Exception) {
                        modifier
                    }
                } ?: modifier
            }
            "borderWidth" -> {
                val width = (value as? AttributeValue.NumberValue)?.value?.toInt()?.dp
                val borderColor = style.properties["borderColor"]?.let { colorVal ->
                    (colorVal as? AttributeValue.StringValue)?.value?.let { colorStr ->
                        try {
                            ColorParser.parse(colorStr)
                        } catch (e: Exception) {
                            null
                        }
                    }
                } ?: Color.Black

                if (width != null) modifier.border(width, borderColor) else modifier
            }
            "width" -> {
                (value as? AttributeValue.NumberValue)?.value?.toInt()?.dp?.let {
                    modifier.width(it)
                } ?: modifier
            }
            "height" -> {
                (value as? AttributeValue.NumberValue)?.value?.toInt()?.dp?.let {
                    modifier.height(it)
                } ?: modifier
            }
            "padding" -> {
                (value as? AttributeValue.NumberValue)?.value?.toInt()?.dp?.let {
                    modifier.padding(it)
                } ?: modifier
            }
            else -> modifier
        }
    }
    return modifier
}
