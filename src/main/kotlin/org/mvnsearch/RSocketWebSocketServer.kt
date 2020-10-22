package org.mvnsearch

import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.server.cio.*
import io.ktor.util.*
import io.ktor.websocket.*
import io.rsocket.kotlin.core.RSocketServerSupport
import io.rsocket.kotlin.core.rSocket

@OptIn(KtorExperimentalAPI::class)
fun main(args: Array<String>) {
    EngineMain.main(args)
}

fun Application.rsocket() {
    install(WebSockets)
    install(RSocketServerSupport)
    routing {
        rSocket("/rsocket", acceptor = pingPongAcceptor)
    }
    println("RSocket enabled!")
}

fun Application.hello() {
    install(DefaultHeaders)
    install(CallLogging)
    routing {
        get("/") {
            call.respondText("Hello World!", ContentType.Text.Plain)
        }
    }
    println("Http enabled!")
}