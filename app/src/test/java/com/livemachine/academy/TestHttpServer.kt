package com.livemachine.academy

import java.net.ServerSocket

/** Mini serveur HTTP sur socket (com.sun.net.httpserver n'est pas disponible dans les tests Android). */
fun <T> withHttpServer(status: Int, body: String, block: (String, () -> String) -> T): T {
    var received = ""
    val server = ServerSocket(0)
    val thread = Thread {
        runCatching {
            server.accept().use { socket ->
                val input = socket.getInputStream()
                val head = StringBuilder()
                while (!head.endsWith("\r\n\r\n")) {
                    val b = input.read()
                    if (b < 0) break
                    head.append(b.toChar())
                }
                val length = head.lines().firstOrNull { it.lowercase().startsWith("content-length:") }
                    ?.substringAfter(':')?.trim()?.toInt() ?: 0
                val bodyBytes = ByteArray(length)
                var read = 0
                while (read < length) {
                    val n = input.read(bodyBytes, read, length - read)
                    if (n < 0) break
                    read += n
                }
                received = String(bodyBytes, 0, read, Charsets.UTF_8)
                val bytes = body.toByteArray(Charsets.UTF_8)
                val out = socket.getOutputStream()
                out.write("HTTP/1.1 $status X\r\nContent-Type: application/json\r\nContent-Length: ${bytes.size}\r\nConnection: close\r\n\r\n".toByteArray())
                out.write(bytes)
                out.flush()
            }
        }
    }
    thread.start()
    try {
        return block("http://127.0.0.1:${server.localPort}/") { received }
    } finally {
        server.close()
        thread.join(2_000)
    }
}
