package io.github.deanalvero.rn.cmp.composable.basic

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.input.pointer.pointerInput
import io.github.deanalvero.rn.cmp.composable.CustomComposable
import io.github.deanalvero.rn.cmp.composable.NodeComposable
import io.github.deanalvero.rn.cmp.data.AttributeValue
import io.github.deanalvero.rn.cmp.data.ReactNativeState
import io.github.deanalvero.rn.cmp.data.UINode
import io.github.deanalvero.rn.cmp.data.getActionKey
import io.github.deanalvero.rn.cmp.modifier.applyReactNativeStyle

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PressableComposable(
    node: UINode,
    state: ReactNativeState,
    customComponents: Map<String, CustomComposable>
) {

    val onPress = node.attributes.getActionKey("onPress")
    val onLongPress = node.attributes.getActionKey("onLongPress")
    val onPressIn = node.attributes.getActionKey("onPressIn")
    val onPressOut = node.attributes.getActionKey("onPressOut")
    val activeOpacityAttr = node.attributes["activeOpacity"] as? AttributeValue.NumberValue
    val activeOpacity = activeOpacityAttr?.value?.toFloat() ?: 0.3f

    var isPressed by remember { mutableStateOf(false) }

    val animatedAlpha by animateFloatAsState(
        targetValue = if (isPressed) {
            activeOpacity
        } else {
            1f
        }
    )

    val modifier = Modifier
        .applyReactNativeStyle(node.attributes)
        .alpha(animatedAlpha)
        .pointerInput(Unit) {
            detectTapGestures(
                onPress = {
                    isPressed = true
                    onPressIn?.let { state.executeAction(it) }

                    val success = tryAwaitRelease()

                    isPressed = false
                    onPressOut?.let { state.executeAction(it) }

                    if (success) {
                        onPress?.let { state.executeAction(it) }
                    }
                },
                onLongPress = {
                    onLongPress?.let { state.executeAction(it) }
                }
            )
        }

    Box(modifier = modifier) {
        node.children.forEach { child ->
            NodeComposable(child, state, customComponents)
        }
    }
}
