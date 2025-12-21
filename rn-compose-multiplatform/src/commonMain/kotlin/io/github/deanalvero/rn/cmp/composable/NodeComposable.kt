package io.github.deanalvero.rn.cmp.composable

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import io.github.deanalvero.rn.cmp.composable.basic.ButtonComposable
import io.github.deanalvero.rn.cmp.composable.basic.SwitchComposable
import io.github.deanalvero.rn.cmp.composable.basic.TextComposable
import io.github.deanalvero.rn.cmp.composable.basic.TextInputComposable
import io.github.deanalvero.rn.cmp.composable.basic.ViewComposable
import io.github.deanalvero.rn.cmp.data.ReactNativeState
import io.github.deanalvero.rn.cmp.data.UINode

@Composable
fun NodeComposable(
    node: UINode,
    state: ReactNativeState,
    customComponents: Map<String, CustomComposable>
) {
    val tagName = node.tagName.lowercase()
    when {
        customComponents.containsKey(tagName) -> {
            customComponents[tagName]?.Render(node, state, customComponents) {
                node.children.forEach { child ->
                    NodeComposable(child, state, customComponents)
                }
            }
        }
        tagName == "view" -> ViewComposable(node, state, customComponents)
        tagName == "text" -> TextComposable(node, state)
        tagName == "textinput" -> TextInputComposable(node, state)
        tagName == "button" -> ButtonComposable(node, state)
        tagName == "switch" -> SwitchComposable(node, state)
        else -> Text("Unsupported: $tagName", color = Color.Red)
    }
}
