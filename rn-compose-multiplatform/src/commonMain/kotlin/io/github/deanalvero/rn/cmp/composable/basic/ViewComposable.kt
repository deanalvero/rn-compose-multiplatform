package io.github.deanalvero.rn.cmp.composable.basic

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import io.github.deanalvero.rn.cmp.composable.CustomComposable
import io.github.deanalvero.rn.cmp.composable.NodeComposable
import io.github.deanalvero.rn.cmp.data.ReactNativeState
import io.github.deanalvero.rn.cmp.data.UINode
import io.github.deanalvero.rn.cmp.modifier.applyReactNativeStyle

@Composable
fun ViewComposable(
    node: UINode,
    state: ReactNativeState,
    customComponents: Map<String, CustomComposable>
) {
    Box(modifier = Modifier.applyReactNativeStyle(node.attributes)) {
        Column {
            node.children.forEach { child ->
                NodeComposable(child, state, customComponents)
            }
        }
    }
}
