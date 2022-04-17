//KOTLIN 1.6.20
//DEPS io.rsocket.kotlin:rsocket-core-jvm:0.15.4
//DEPS io.rsocket.kotlin:rsocket-transport-ktor-tcp-jvm:0.15.4

import io.rsocket.kotlin.RSocketRequestHandler
import io.rsocket.kotlin.core.RSocketServer
import io.rsocket.kotlin.payload.Payload
import io.rsocket.kotlin.payload.buildPayload
import io.rsocket.kotlin.payload.data
import io.rsocket.kotlin.transport.ktor.tcp.TcpServer
import io.rsocket.kotlin.transport.ktor.tcp.TcpServerTransport
import kotlinx.coroutines.runBlocking

fun main() {
    val transport = TcpServerTransport("0.0.0.0", 42252)
    val server: TcpServer = RSocketServer().bind(transport) {
        RSocketRequestHandler {
            //handler for request/response
            requestResponse { request: Payload ->
                println(request.data.readText()) //print request payload data
                buildPayload {
                    //language=json
                    data("""{ "data": "Server response" }""")
                }
            }
        }
    }
    runBlocking {
        println("RSocket Server started on 0.0.0.0:42262")
        server.handlerJob.join()
    }
}