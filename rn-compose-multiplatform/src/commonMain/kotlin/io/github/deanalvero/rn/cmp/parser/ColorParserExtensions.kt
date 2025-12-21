package io.github.deanalvero.rn.cmp.parser

import androidx.compose.ui.graphics.Color
import io.github.deanalvero.rn.cmp.data.AttributeValue

fun AttributeValue?.parseColor(): Color? {
    return (this as? AttributeValue.StringValue)?.value?.let { ColorParser.parse(it) }
}
