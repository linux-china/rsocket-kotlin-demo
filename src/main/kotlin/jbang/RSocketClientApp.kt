//KOTLIN 1.6.20
//DEPS io.rsocket.kotlin:rsocket-core-jvm:0.15.4
//DEPS io.rsocket.kotlin:rsocket-transport-ktor-tcp-jvm:0.15.4

import io.rsocket.kotlin.RSocket
import io.rsocket.kotlin.core.RSocketConnector
import io.rsocket.kotlin.payload.buildPayload
import io.rsocket.kotlin.payload.data
import io.rsocket.kotlin.transport.ktor.tcp.TcpClientTransport
import kotlinx.coroutines.runBlocking

fun main() {
    val transport = TcpClientTransport("127.0.0.1", 42252)
    runBlocking {
        val rsocket: RSocket = RSocketConnector().connect(transport)
        //use rsocket to do request
        val response = rsocket.requestResponse(buildPayload { data("""{ "data": "hello world" }""") })
        println(response.data.readText())
    }
}