package org.mvnsearch

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.features.websocket.WebSockets
import io.ktor.network.selector.SelectorManager
import io.ktor.network.sockets.aSocket
import io.ktor.util.InternalAPI
import io.ktor.util.KtorExperimentalAPI
import io.rsocket.kotlin.RSocket
import io.rsocket.kotlin.connection.connectClient
import io.rsocket.kotlin.connection.connection
import io.rsocket.kotlin.core.RSocketClientSupport
import io.rsocket.kotlin.core.rSocket
import io.rsocket.kotlin.payload.Payload
import io.rsocket.kotlin.payload.PayloadMimeType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import kotlin.coroutines.CoroutineContext

@KtorExperimentalAPI
class RSocketClientTest {

    @Test
    fun testRSocketTcpSocketClient(): Unit = runBlocking {
        val rsocket = buildTcpClient(Dispatchers.IO, "127.0.0.1", 8080)
        val result = rsocket.requestResponse(Payload("Ping", null))
        println(result.data.readText())
    }

    @OptIn(KtorExperimentalAPI::class, InternalAPI::class)
    suspend fun buildTcpClient(dispatcher: CoroutineContext, host: String, port: Int): RSocket {
        return aSocket(SelectorManager(dispatcher))
            .tcp()
            .connect(host, port)
            .connection
            .connectClient()
    }

    @Test
    fun testRSocketWebSocketClient(): Unit = runBlocking {
        val rsocket = buildHttpClient(
            "ws://localhost:8080/rsocket",
            "text/plain",
            "message/x.rsocket.composite-metadata.v0"
        )
        val result = rsocket.requestResponse(Payload("Ping", null))
        println(result.data.readText())
    }

    private suspend fun buildHttpClient(uri: String, dataFormat: String, metadataFormat: String): RSocket {
        val client = HttpClient(CIO) {
            install(WebSockets)
            install(RSocketClientSupport) {
                payloadMimeType = PayloadMimeType(dataFormat, metadataFormat)
            }
        }
        return client.rSocket(uri, uri.startsWith("wss"))
    }
}




