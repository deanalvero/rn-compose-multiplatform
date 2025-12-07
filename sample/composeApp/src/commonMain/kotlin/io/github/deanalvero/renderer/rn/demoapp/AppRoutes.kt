package io.github.deanalvero.renderer.rn.demoapp

import kotlinx.serialization.Serializable

@Serializable
object ListRoute

@Serializable
data class DetailRoute(val componentName: String)