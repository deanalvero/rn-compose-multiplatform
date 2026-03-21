package io.github.deanalvero.rn.cmp

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import io.github.deanalvero.parser.jsx.JsxParser
import io.github.deanalvero.parser.jsx.attributevalue.JsxAttributeValue
import io.github.deanalvero.parser.jsx.attributevalue.JsxExpressionContainer
import io.github.deanalvero.parser.jsx.attributevalue.JsxStringLiteral
import io.github.deanalvero.parser.jsx.node.JsxElement
import io.github.deanalvero.parser.jsx.node.JsxExpression
import io.github.deanalvero.parser.jsx.node.JsxNode
import io.github.deanalvero.parser.jsx.node.JsxText
import io.github.deanalvero.parser.jsx.node.expression.JsxExpressionNode
import io.github.deanalvero.rn.cmp.composable.CustomComposable
import io.github.deanalvero.rn.cmp.composable.NodeComposable
import io.github.deanalvero.rn.cmp.data.AttributeValue
import io.github.deanalvero.rn.cmp.data.ReactNativeState
import io.github.deanalvero.rn.cmp.data.TextContent
import io.github.deanalvero.rn.cmp.data.UINode
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun ReactNativeComposable(
    tags: String,
    state: ReactNativeState,
    customComponents: Map<String, CustomComposable> = emptyMap()
) {
    var rootNode by remember {
        mutableStateOf<UINode?>(null)
    }
    LaunchedEffect(tags, state) {
        val parsedNode = withContext(Dispatchers.Default) {
            parseJsx(tags)
        }
        rootNode = parsedNode
    }
    rootNode?.let {
        NodeComposable(
            node = it,
            state = state,
            customComponents = customComponents
        )
    }
}

private fun parseJsx(jsx: String): UINode? {
    return try {
        val ast = JsxParser.parse(jsx)
        ast.getOrThrow().firstOrNull()?.let { convertNodeToUINode(it) }
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

private fun convertNodeToUINode(node: JsxNode): UINode? {
    return when (node) {
        is JsxElement -> {
            val attributes = node.attributes.associate { attr ->
                attr.key to convertAttrValue(attr.key, attr.value)
            }

            val textContent = if (node.name.equals("Text", ignoreCase = true)) {
                node.children.firstNotNullOfOrNull { child ->
                    when (child) {
                        is JsxText -> {
                            val text = child.text.trim()
                            if (text.isNotEmpty()) TextContent.Literal(text) else null
                        }
                        is JsxExpression -> {
                            when (val exprNode = child.node) {
                                is JsxExpressionNode.Identifier ->
                                    TextContent.Binding(exprNode.name)
                                is JsxExpressionNode.MemberExpression ->
                                    TextContent.Binding(exprNode.parts.joinToString("."))
                                else ->
                                    TextContent.Binding(child.expression.trim())
                            }
                        }
                        else -> null
                    }
                }
            } else null

            val children = if (node.name.equals("Text", ignoreCase = true)) {
                emptyList()
            } else {
                node.children.mapNotNull { convertNodeToUINode(it) }
            }

            UINode(
                tagName = node.name,
                attributes = attributes,
                children = children,
                textContent = textContent
            )
        }
        is JsxText -> {
            val text = node.text.trim()
            if (text.isNotEmpty()) {
                UINode(
                    tagName = "text",
                    attributes = emptyMap(),
                    children = emptyList(),
                    textContent = TextContent.Literal(text)
                )
            } else null
        }
        is JsxExpression -> {
            UINode(
                tagName = "text",
                attributes = emptyMap(),
                children = emptyList(),
                textContent = TextContent.Binding(node.expression.trim())
            )
        }
        else -> null
    }
}

private fun convertAttrValue(key: String, value: JsxAttributeValue?): AttributeValue {
    val isEventHandler = key.length > 2
            && key.startsWith("on")
            && key[2].isUpperCase()

    return when (value) {
        is JsxStringLiteral -> AttributeValue.StringValue(value.value)
        is JsxExpressionContainer -> convertExpressionNode(value.node, isEventHandler)
        null -> AttributeValue.BooleanValue(true)
    }
}

private fun convertExpressionNode(node: JsxExpressionNode, isEventHandler: Boolean? = null): AttributeValue {
    return when (node) {
        is JsxExpressionNode.StringLiteral -> AttributeValue.StringValue(node.value)
        is JsxExpressionNode.NumberLiteral -> AttributeValue.NumberValue(node.value)
        is JsxExpressionNode.ObjectLiteral ->
            AttributeValue.StyleObject(convertStyleObject(node))
        is JsxExpressionNode.Identifier -> {
            if (isEventHandler == true) {
                AttributeValue.ActionBinding(node.name)
            } else {
                AttributeValue.StateBinding(node.name)
            }
        }
        is JsxExpressionNode.MemberExpression ->
            AttributeValue.StateBinding(node.parts.joinToString("."))
        is JsxExpressionNode.CallExpression -> AttributeValue.ActionBinding(node.callee)
        is JsxExpressionNode.Unknown -> AttributeValue.StateBinding(node.source)
    }
}

private fun convertStyleObject(objectLiteral: JsxExpressionNode.ObjectLiteral): Map<String, AttributeValue> {
    return objectLiteral.properties.mapValues { (_, value) ->
        convertExpressionNode(value, null)
    }
}
