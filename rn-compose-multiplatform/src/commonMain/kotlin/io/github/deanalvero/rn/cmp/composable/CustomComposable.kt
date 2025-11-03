package io.github.deanalvero.rn.cmp.composable

import androidx.compose.runtime.Composable
import io.github.deanalvero.rn.cmp.data.ReactNativeState
import io.github.deanalvero.rn.cmp.data.UINode

interface CustomComposable {
    @Composable
    fun Render(
        node: UINode,
        state: ReactNativeState,
        customComposable: Map<String, CustomComposable>,
        renderChildren: @Composable () -> Unit
    )
}