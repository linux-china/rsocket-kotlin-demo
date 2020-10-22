package org.mvnsearch

import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.server.cio.*
import io.ktor.util.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@OptIn(KtorExperimentalAPI::class)
fun main(args: Array<String>) {
    GlobalScope.launch {
        runTcpServer(Dispatchers.IO, 42252)
    }
    EngineMain.main(args)
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