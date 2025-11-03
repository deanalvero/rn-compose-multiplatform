package io.github.deanalvero.rn.cmp.data

data class UINode(
    val tagName: String,
    val attributes: Map<String, AttributeValue>,
    val children: List<UINode>,
    val textContent: TextContent? = null
)