package io.github.deanalvero.rn.cmp.data

fun Map<String, AttributeValue>.getActionKey(attrName: String): String? =
    when (val v = this[attrName]) {
        is AttributeValue.ActionBinding -> v.key
        is AttributeValue.StateBinding  -> v.key
        else -> null
    }
