package org.mvnsearch

import io.ktor.application.install
import io.ktor.routing.routing
import io.ktor.server.cio.CIO
import io.ktor.server.engine.embeddedServer
import io.ktor.util.KtorExperimentalAPI
import io.ktor.websocket.WebSockets
import io.rsocket.kotlin.core.RSocketServerSupport
import io.rsocket.kotlin.core.rSocket

@OptIn(KtorExperimentalAPI::class)
fun main() {
    embeddedServer(CIO, port = 8080) {
        install(WebSockets)
        install(RSocketServerSupport)
        routing {
            rSocket("/rsocket", acceptor = pingPongAcceptor)
        }
    }.start(true)
}
