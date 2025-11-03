package io.github.deanalvero.rn.cmp.composable.basic

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import io.github.deanalvero.rn.cmp.data.AttributeValue
import io.github.deanalvero.rn.cmp.data.ReactNativeState
import io.github.deanalvero.rn.cmp.data.UINode
import io.github.deanalvero.rn.cmp.modifier.applyReactNativeStyle

@Composable
fun ButtonComposable(node: UINode, state: ReactNativeState) {
    val title = (node.attributes["title"] as? AttributeValue.StringValue)?.value ?: "Button"
    val onPress = node.attributes["onPress"] as? AttributeValue.StateBinding

    Button(
        onClick = {
            onPress?.let { binding ->
                state.executeAction(binding.key)
            }
        },
        modifier = Modifier.applyReactNativeStyle(node.attributes)
    ) {
        Text(title)
    }
}
