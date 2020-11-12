package org.mvnsearch

import io.ktor.application.*
import io.ktor.network.selector.*
import io.ktor.network.sockets.*
import io.ktor.routing.*
import io.ktor.util.*
import io.ktor.utils.io.core.*
import io.ktor.websocket.*
import io.rsocket.kotlin.ConnectionAcceptor
import io.rsocket.kotlin.RSocketRequestHandler
import io.rsocket.kotlin.core.RSocketServer
import io.rsocket.kotlin.payload.Payload
import io.rsocket.kotlin.transport.ktor.server.RSocketSupport
import io.rsocket.kotlin.transport.ktor.server.rSocket
import io.rsocket.kotlin.transport.ktor.serverTransport
import kotlin.coroutines.CoroutineContext
import kotlin.text.toByteArray

@OptIn(KtorExperimentalAPI::class, InternalAPI::class)
suspend fun runTcpServer(dispatcher: CoroutineContext, port: Int) {
    println("Start TCP server on 0.0.0.0:${port}")
    val transport = aSocket(SelectorManager(dispatcher)).tcp().serverTransport("0.0.0.0", port)
    RSocketServer().bind(transport, pingPongAcceptor).join()
}

val pingPongAcceptor: ConnectionAcceptor = ConnectionAcceptor {
    RSocketRequestHandler {
        requestResponse {
            Payload(ByteReadPacket("Pong".toByteArray()))
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
