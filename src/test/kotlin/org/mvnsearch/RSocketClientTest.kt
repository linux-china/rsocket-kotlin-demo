package org.mvnsearch

import io.ktor.client.engine.cio.CIO
import io.ktor.utils.io.core.ByteReadPacket
import io.rsocket.kotlin.RSocket
import io.rsocket.kotlin.core.RSocketConnector
import io.rsocket.kotlin.payload.Payload
import io.rsocket.kotlin.transport.ktor.tcp.TcpClientTransport
import io.rsocket.kotlin.transport.ktor.websocket.client.WebSocketClientTransport
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import java.net.URI
import kotlin.coroutines.CoroutineContext

class RSocketClientTest {

    @Test
    fun testRSocketTcpSocketClient(): Unit = runBlocking {
        val rsocket = buildTcpClient(Dispatchers.IO, "127.0.0.1", 8080)
        val result = rsocket.requestResponse(Payload(ByteReadPacket("Ping".toByteArray()), null))
        println(result.data.readText())
    }

    suspend fun buildTcpClient(dispatcher: CoroutineContext, host: String, port: Int): RSocket {
        val connector = RSocketConnector {
            connectionConfig { }
        }
        return connector.connect(TcpClientTransport(host, port))
    }

    @Test
    fun testRSocketWebSocketClient(): Unit = runBlocking {
        val rsocket = buildHttpClient(
            URI("ws://localhost:8080/rsocket"),
            "text/plain",
            "message/x.rsocket.composite-metadata.v0"
        )
        val result = rsocket.requestResponse(Payload(ByteReadPacket("Ping".toByteArray()), null))
        println(result.data.readText())
    }

    private suspend fun buildHttpClient(uri: URI, dataFormat: String, metadataFormat: String): RSocket {
        val connector = RSocketConnector {
            connectionConfig { }
        }
        return connector.connect(WebSocketClientTransport(CIO, uri.host, uri.port,uri.path))
    }
}




