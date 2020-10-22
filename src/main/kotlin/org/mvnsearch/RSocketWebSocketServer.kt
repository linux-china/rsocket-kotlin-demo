package org.mvnsearch

import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.network.selector.*
import io.ktor.network.sockets.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.server.cio.*
import io.ktor.util.*
import io.ktor.websocket.*
import io.rsocket.kotlin.RSocketAcceptor
import io.rsocket.kotlin.RSocketRequestHandler
import io.rsocket.kotlin.core.RSocketServerSupport
import io.rsocket.kotlin.core.rSocket
import io.rsocket.kotlin.payload.Payload
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

@OptIn(KtorExperimentalAPI::class)
fun main(args: Array<String>) {
    GlobalScope.launch {
        runTcpServer(Dispatchers.IO, 42252)
    }
    EngineMain.main(args)
}

@OptIn(KtorExperimentalAPI::class, InternalAPI::class)
suspend fun runTcpServer(dispatcher: CoroutineContext, port: Int) {
    println("Start TCP server on 0.0.0.0:${port}")
    aSocket(SelectorManager(dispatcher))
        .tcp()
        .bind("0.0.0.0", port)
        .rSocket(acceptor = pingPongAcceptor)
}

val pingPongAcceptor: RSocketAcceptor = {
    RSocketRequestHandler {
        requestResponse = {
            Payload("Pong")
        }
    }
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