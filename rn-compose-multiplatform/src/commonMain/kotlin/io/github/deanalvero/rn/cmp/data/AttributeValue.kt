package io.github.deanalvero.rn.cmp.data

sealed class AttributeValue {
    data class StringValue(val value: String) : AttributeValue()
    data class NumberValue(val value: Number) : AttributeValue()
    data class BooleanValue(val value: Boolean) : AttributeValue()
    data class StyleObject(val properties: Map<String, AttributeValue>) : AttributeValue()
    data class StateBinding(val key: String) : AttributeValue()
    data class ActionBinding(val key: String) : AttributeValue()
}