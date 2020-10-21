tcp-server:
  rsocket-cli -i "pong" --server --debug tcp://localhost:42252

tcp-client:
  rsocket-cli --request -i "ping" --debug tcp://localhost:42252

ws-server:
  rsocket-cli -i "pong" --server --debug ws://localhost:8080/rsocket

ws-client:
   rsocket-cli --request -i "ping" --debug ws://localhost:8080/rsocket