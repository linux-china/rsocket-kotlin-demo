package org.mvnsearch

import io.rsocket.kotlin.RSocketAcceptor
import io.rsocket.kotlin.RSocketRequestHandler
import io.rsocket.kotlin.payload.Payload

val pingPongAcceptor: RSocketAcceptor = {
    RSocketRequestHandler {
        requestResponse = {
            Payload("Pong")
        }
    }
}