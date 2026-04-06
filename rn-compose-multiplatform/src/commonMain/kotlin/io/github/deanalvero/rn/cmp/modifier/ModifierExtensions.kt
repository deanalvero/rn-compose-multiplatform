package io.github.deanalvero.rn.cmp.modifier

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import io.github.deanalvero.rn.cmp.data.AttributeValue
import io.github.deanalvero.rn.cmp.parser.ColorParser

fun Modifier.applyReactNativeStyle(attributes: Map<String, AttributeValue>): Modifier {
    var modifier = this
    val style = (attributes["style"] as? AttributeValue.StyleObject)?.properties ?: return this

    fun getDp(key: String): Dp? =
        (style[key] as? AttributeValue.NumberValue)?.value?.toFloat()?.dp

    fun getDp(specific: String, axis: String, general: String): Dp {
        return getDp(specific)
            ?: getDp(axis)
            ?: getDp(general)
            ?: 0.dp
    }

    val marginTop = getDp("marginTop", "marginVertical", "margin")
    val marginBottom = getDp("marginBottom", "marginVertical", "margin")
    val marginLeft = getDp("marginLeft", "marginHorizontal", "margin")
    val marginRight = getDp("marginRight", "marginHorizontal", "margin")

    modifier = modifier.padding(
        start = marginLeft,
        top = marginTop,
        end = marginRight,
        bottom = marginBottom
    )

    getDp("width")?.let { modifier = modifier.width(it) }
    getDp("height")?.let { modifier = modifier.height(it) }

    val borderRadius = getDp("borderRadius") ?: 0.dp
    val shape = if (borderRadius > 0.dp) RoundedCornerShape(borderRadius) else RectangleShape

    val backgroundColor = (style["backgroundColor"] as? AttributeValue.StringValue)?.value
        ?.let { ColorParser.parse(it) }

    if (borderRadius > 0.dp) {
        modifier = modifier.clip(shape)
    }

    if (backgroundColor != null) {
        modifier = modifier.background(backgroundColor, shape)
    }

    val borderWidth = getDp("borderWidth")
    val borderColor = (style["borderColor"] as? AttributeValue.StringValue)?.value
        ?.let { ColorParser.parse(it) } ?: Color.Black

    if (borderWidth != null && borderWidth > 0.dp) {
        modifier = modifier.border(borderWidth, borderColor, shape)
    }

    val paddingTop = getDp("paddingTop", "paddingVertical", "padding")
    val paddingBottom = getDp("paddingBottom", "paddingVertical", "padding")
    val paddingLeft = getDp("paddingLeft", "paddingHorizontal", "padding")
    val paddingRight = getDp("paddingRight", "paddingHorizontal", "padding")

    modifier = modifier.padding(
        start = paddingLeft,
        top = paddingTop,
        end = paddingRight,
        bottom = paddingBottom
    )
    return modifier
}
