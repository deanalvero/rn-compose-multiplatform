package io.github.deanalvero.rn.cmp.composable.basic

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import io.github.deanalvero.rn.cmp.data.AttributeValue
import io.github.deanalvero.rn.cmp.data.ReactNativeState
import io.github.deanalvero.rn.cmp.data.UINode
import io.github.deanalvero.rn.cmp.data.getActionKey
import io.github.deanalvero.rn.cmp.modifier.applyReactNativeStyle

@Composable
fun ButtonComposable(node: UINode, state: ReactNativeState) {
    val title = (node.attributes["title"] as? AttributeValue.StringValue)?.value ?: "Button"
    val onPress = node.attributes.getActionKey("onPress")

    Button(
        onClick = {
            onPress?.let { state.executeAction(it) }
        },
        modifier = Modifier.applyReactNativeStyle(node.attributes)
    ) {
        Text(title)
    }
}
