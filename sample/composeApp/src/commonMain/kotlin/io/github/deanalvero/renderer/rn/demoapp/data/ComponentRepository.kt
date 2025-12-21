package io.github.deanalvero.renderer.rn.demoapp.data

import androidx.compose.runtime.remember
import io.github.deanalvero.rn.cmp.ReactNativeComposable
import io.github.deanalvero.rn.cmp.data.ReactNativeState

object ComponentRepository {
    val all = listOf(
        ComponentData(
            name = "Hello World!",
            codeSnippet = """
                val state = remember {
                    ReactNativeState(initialState = emptyMap())
                }

                val jsx = ""${'"'}
                    <Text>Hello World!</Text>
                ""${'"'}.trimIndent()

                ReactNativeComposable(tags = jsx, state = state)
            """.trimIndent(),
            content = {
                val state = remember {
                    ReactNativeState(initialState = emptyMap())
                }

                val jsx = """
                    <Text>Hello World!</Text>
                """.trimIndent()

                ReactNativeComposable(tags = jsx, state = state)
            }
        ),
        ComponentData(
            name = "Counter",
            codeSnippet = """
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

                val jsx = ""${'"'}
                    <View style={{ padding: 20, backgroundColor: '#f5f5f5' }}>
                        <Text style={{ fontSize: 24, fontWeight: 'bold' }}>
                            Counter Sample
                        </Text>
                        <Text style={{ fontSize: 48, color: '#007AFF' }}>
                            {counter}
                        </Text>
                        <Button title="Increment" onPress={increment} />
                    </View>
                ""${'"'}.trimIndent()

                ReactNativeComposable(tags = jsx, state = state)
            """.trimIndent(),
            content = {
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
        ),
        ComponentData(
            name = "Settings",
            codeSnippet = """
                val state = remember {
                    ReactNativeState(
                        initialState = mapOf("notificationsEnabled" to false),
                        valueChangeActionsMap = mapOf(
                            "toggleNotifications" to { state, newValue ->
                                state.setState("notificationsEnabled", newValue)
                            }
                        )
                    )
                }

                val jsx = ""${'"'}
                    <View style={{ flex: 1, backgroundColor: '#ffffff' }}>
                        <Text>Notifications</Text>
                        <Switch 
                            value={notificationsEnabled} 
                            onValueChange={toggleNotifications} 
                        />
                    </View>
                ""${'"'}.trimIndent()

                ReactNativeComposable(tags = jsx, state = state)
            """.trimIndent(),
            content = {
                val state = remember {
                    ReactNativeState(
                        initialState = mapOf("notificationsEnabled" to false),
                        valueChangeActionsMap = mapOf(
                            "toggleNotifications" to { state, newValue ->
                                state.setState("notificationsEnabled", newValue)
                            }
                        )
                    )
                }

                val jsx = """
                    <View style={{ flex: 1, backgroundColor: '#ffffff' }}>
                        <Text>Notifications</Text>
                        <Switch 
                            value={notificationsEnabled} 
                            onValueChange={toggleNotifications} 
                        />
                    </View>
                """.trimIndent()

                ReactNativeComposable(tags = jsx, state = state)
            }
        ),
    )

    fun getByName(name: String): ComponentData? = all.find { it.name == name }
}
