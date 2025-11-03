# React Native Compose Multiplatform
A declarative UI rendering engine that enables React Native-style JSX syntax for Jetpack Compose applications, providing developers with a familiar API while leveraging Compose's powerful rendering capabilities.

## Overview
This library bridges the gap between React Native's declarative JSX syntax and Jetpack Compose's modern UI toolkit. It provides a comprehensive rendering system that parses JSX strings and converts them into native Compose components, enabling code reuse, rapid prototyping, and a gentler learning curve for developers transitioning between frameworks.

## Dependency
TODO

## Usage
```
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
```

## Notes
Enjoy!
