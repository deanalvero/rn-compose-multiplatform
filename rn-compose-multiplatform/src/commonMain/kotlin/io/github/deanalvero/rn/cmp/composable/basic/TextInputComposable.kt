package io.github.deanalvero.rn.cmp.composable.basic

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import io.github.deanalvero.rn.cmp.data.AttributeValue
import io.github.deanalvero.rn.cmp.data.ReactNativeState
import io.github.deanalvero.rn.cmp.data.UINode
import io.github.deanalvero.rn.cmp.modifier.applyReactNativeStyle

@Composable
fun TextInputComposable(node: UINode, state: ReactNativeState) {
    val placeholder = (node.attributes["placeholder"] as? AttributeValue.StringValue)?.value ?: ""
    val valueBinding = node.attributes["value"] as? AttributeValue.StateBinding
    val onChangeTextBinding = node.attributes["onChangeText"] as? AttributeValue.StateBinding

    var textValue by remember(valueBinding?.key) {
        mutableStateOf(valueBinding?.let { state.getState<String>(it.key) } ?: "")
    }

    LaunchedEffect(valueBinding?.key, state.state) {
        valueBinding?.let { binding ->
            state.getState<String>(binding.key)?.let { newValue ->
                if (textValue != newValue) {
                    textValue = newValue
                }
            }
        }
    }

    TextField(
        value = textValue,
        onValueChange = { newValue ->
            textValue = newValue
            onChangeTextBinding?.let { binding ->
                state.executeValueChangeAction(binding.key, newValue)
            }
        },
        placeholder = { Text(placeholder) },
        modifier = Modifier
            .fillMaxWidth()
            .applyReactNativeStyle(node.attributes)
    )
}
