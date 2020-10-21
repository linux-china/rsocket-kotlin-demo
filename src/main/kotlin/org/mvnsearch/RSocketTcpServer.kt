package org.mvnsearch

import io.ktor.network.selector.SelectorManager
import io.ktor.network.sockets.aSocket
import io.ktor.util.InternalAPI
import io.ktor.util.KtorExperimentalAPI
import io.rsocket.kotlin.core.rSocket
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlin.coroutines.CoroutineContext

fun main(args: Array<String>) = runBlocking {
    println("Start TCP server on 0.0.0.0:42252")
    runTcpServer(Dispatchers.IO, 42252)
}

@OptIn(KtorExperimentalAPI::class, InternalAPI::class)
suspend fun runTcpServer(dispatcher: CoroutineContext, port: Int) {
    aSocket(SelectorManager(dispatcher))
        .tcp()
        .bind("0.0.0.0", port)
        .rSocket(acceptor = pingPongAcceptor)
}