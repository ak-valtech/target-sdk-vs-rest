package com.example.plugins

import com.example.routes.rest
import com.example.routes.sdk
import io.ktor.server.routing.*
import io.ktor.server.application.*

fun Application.configureRouting() {
    routing {
        rest()
        sdk()
    }
}
