package io.github.deanalvero.renderer.rn.demoapp

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import io.github.deanalvero.rn.cmp.ReactNativeComposable
import io.github.deanalvero.rn.cmp.data.ReactNativeState

@Composable
fun CounterSample() {
    val state = remember {
        ReactNativeState(
            initialState = mapOf("counter" to 0),
            actionsMap = mapOf(
                "increment" to { state ->
                    val current = state.getState<Int>("counter") ?: 0
                    state.setState("counter", current + 1)
                }
            )
        )
    }

    val jsx = """
        <View style={{ padding: 20, backgroundColor: '#f5f5f5' }}>
            <Text style={{ fontSize: 24, fontWeight: 'bold' }}>
                Counter Sample
            </Text>
            <Text style={{ fontSize: 48, color: '#007AFF' }}>
                {counter}
            </Text>
            <Button title="Increment" onPress={increment} />
        </View>
    """.trimIndent()

    ReactNativeComposable(tags = jsx, state = state)
}
