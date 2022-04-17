package org.mvnsearch

import io.ktor.server.application.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import io.rsocket.kotlin.ConnectionAcceptor
import io.rsocket.kotlin.RSocketRequestHandler
import io.rsocket.kotlin.core.RSocketServer
import io.rsocket.kotlin.ktor.server.RSocketSupport
import io.rsocket.kotlin.ktor.server.rSocket
import io.rsocket.kotlin.payload.buildPayload
import io.rsocket.kotlin.payload.data
import io.rsocket.kotlin.transport.ktor.tcp.TcpServerTransport
import kotlin.coroutines.CoroutineContext

suspend fun runTcpServer(dispatcher: CoroutineContext, port: Int) {
    println("Start TCP server on 0.0.0.0:${port}")
    val transport = TcpServerTransport("0.0.0.0", 42252)
    RSocketServer().bind(transport, pingPongAcceptor).handlerJob.join()
}

val pingPongAcceptor: ConnectionAcceptor = ConnectionAcceptor {
    RSocketRequestHandler {
        requestResponse {
            buildPayload { data("Pong") }
        }
    }
}

fun Application.rsocket() {
    install(WebSockets)
    install(RSocketSupport)
    routing {
        rSocket("/rsocket", acceptor = pingPongAcceptor)
    }
    println("RSocket enabled!")
}
