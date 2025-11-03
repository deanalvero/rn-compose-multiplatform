package io.github.deanalvero.rn.cmp.data

import androidx.compose.runtime.mutableStateMapOf

class ReactNativeState(
    initialState: Map<String, Any>,
    private val actionsMap: Map<String, (ReactNativeState) -> Unit> = emptyMap(),
    private val valueChangeActionsMap: Map<String, (ReactNativeState, Any) -> Unit> = emptyMap()
) {
    private val _state = mutableStateMapOf<String, Any>().apply { putAll(initialState) }
    val state: Map<String, Any> get() = _state.toMap()
    val actions: Map<String, (ReactNativeState) -> Unit> get() = actionsMap

    fun <T : Any> setState(key: String, value: T) {
        _state[key] = value
    }

    fun <T : Any> getState(key: String, default: T? = null): T? {
        @Suppress("UNCHECKED_CAST")
        val result = (_state[key] as? T) ?: default
        return result
    }

    fun executeAction(actionName: String) {
        actionsMap[actionName]?.invoke(this)
    }

    fun executeValueChangeAction(actionName: String, value: Any) {
        valueChangeActionsMap[actionName]?.invoke(this, value)
    }
}