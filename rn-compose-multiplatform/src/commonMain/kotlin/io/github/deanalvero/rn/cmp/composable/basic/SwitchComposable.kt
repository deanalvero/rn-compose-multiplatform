package io.github.deanalvero.rn.cmp.composable.basic

import androidx.compose.material3.Switch
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import io.github.deanalvero.rn.cmp.data.AttributeValue
import io.github.deanalvero.rn.cmp.data.ReactNativeState
import io.github.deanalvero.rn.cmp.data.UINode
import io.github.deanalvero.rn.cmp.modifier.applyReactNativeStyle

@Composable
fun SwitchComposable(node: UINode, state: ReactNativeState) {
    val valueBinding = node.attributes["value"] as? AttributeValue.StateBinding
    val onValueChangeBinding = node.attributes["onValueChange"] as? AttributeValue.StateBinding
    val onChangeBinding = node.attributes["onChange"] as? AttributeValue.StateBinding
    val disabledBinding = (node.attributes["disabled"] as? AttributeValue.StateBinding)

    val isDisabled = disabledBinding?.let {
        state.getState<Boolean>(it.key)
    } ?: false

    val isChecked = valueBinding?.let {
        state.getState<Boolean>(it.key)
    } ?: false

    Switch(
        checked = isChecked,
        onCheckedChange = { newValue ->
            onValueChangeBinding?.let { binding ->
                state.executeValueChangeAction(binding.key, newValue)
            }
            onChangeBinding?.let { binding ->
                state.executeAction(binding.key)
            }
        },
        modifier = Modifier.applyReactNativeStyle(node.attributes),
        enabled = !isDisabled
    )
}
