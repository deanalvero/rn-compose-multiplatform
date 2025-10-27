package io.github.deanalvero.renderer.rn.demoapp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform