package io.github.deanalvero.rn.cmp.composable.basic

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import io.github.deanalvero.rn.cmp.composable.CustomComposable
import io.github.deanalvero.rn.cmp.composable.NodeComposable
import io.github.deanalvero.rn.cmp.data.AttributeValue
import io.github.deanalvero.rn.cmp.data.ReactNativeState
import io.github.deanalvero.rn.cmp.data.UINode
import io.github.deanalvero.rn.cmp.modifier.applyReactNativeStyle

@Composable
fun TouchableOpacityComposable(
    node: UINode,
    state: ReactNativeState,
    customComponents: Map<String, CustomComposable>
) {
    val onPress = node.attributes["onPress"] as? AttributeValue.StateBinding
    val disabledBinding = node.attributes["disabled"] as? AttributeValue.StateBinding
    val activeOpacityAttr = node.attributes["activeOpacity"] as? AttributeValue.NumberValue

    val disabled = disabledBinding?.let {
        state.getState<Boolean>(it.key)
    } ?: false

    val activeOpacity = activeOpacityAttr?.value?.toFloat() ?: 0.2f

    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

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
        .clickable(
            enabled = !disabled,
            interactionSource = interactionSource,
            indication = null
        ) {
            onPress?.let { binding ->
                state.executeAction(binding.key)
            }
        }

    Box(modifier = modifier) {
        node.children.forEach { child ->
            NodeComposable(child, state, customComponents)
        }
    }
}
