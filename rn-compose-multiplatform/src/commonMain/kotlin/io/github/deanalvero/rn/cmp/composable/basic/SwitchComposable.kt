package io.github.deanalvero.rn.cmp.composable.basic

import androidx.compose.material3.Switch
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import io.github.deanalvero.rn.cmp.data.AttributeValue
import io.github.deanalvero.rn.cmp.data.ReactNativeState
import io.github.deanalvero.rn.cmp.data.UINode
import io.github.deanalvero.rn.cmp.data.getActionKey
import io.github.deanalvero.rn.cmp.modifier.applyReactNativeStyle

@Composable
fun SwitchComposable(node: UINode, state: ReactNativeState) {
    val valueBinding = node.attributes["value"] as? AttributeValue.StateBinding
    val onValueChangeBinding = node.attributes.getActionKey("onValueChange")
    val onChangeBinding = node.attributes.getActionKey("onChange")
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
            onValueChangeBinding?.let {
                state.executeValueChangeAction(it, newValue)
            }
            onChangeBinding?.let {
                state.executeAction(it)
            }
        },
        modifier = Modifier.applyReactNativeStyle(node.attributes),
        enabled = !isDisabled
    )
}
