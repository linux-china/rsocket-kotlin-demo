package org.mvnsearch

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.cio.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@OptIn(DelicateCoroutinesApi::class)
fun main(args: Array<String>) {
    GlobalScope.launch {
        runTcpServer(Dispatchers.IO, 42252)
    }
    EngineMain.main(args)
}

fun Application.hello() {
    routing {
        get("/") {
            call.respondText("Hello World!", ContentType.Text.Plain)
        }
    }
    println("Http enabled!")
}