package com.example.routes

import io.ktor.server.routing.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import com.google.gson.*
import java.io.BufferedInputStream
import java.io.ByteArrayOutputStream
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL

val gson = Gson()

data class TargetPayload(val payload: Any)

const val clientCode = "valtechsaemeapartner"
const val sessionId = "11111111111111111111111111111111111"
const val url = "https://$clientCode.tt.omtrdc.net/rest/v1/delivery?client=$clientCode&sessionId=$sessionId"
val payload = object {
    val context = object {
        val channel = "web"
        val browser = object {
            val host = "demo"
        }
        val address = object {
            val url = "https://demo.test.com"
        }
        val screen = object {
            val width = 0
            val height = 0
        }
    }
    val execute = object {
        val mboxes = arrayListOf(
            object {
                val name = "local"
                val index = 1
                val parameters = object {
                    val testParam = "testParamValue"
                }
            })
    }
}

fun Route.rest() {
    get("/rest") {
        val connection = URL(url).openConnection() as HttpURLConnection
        connection.requestMethod = "POST"
        connection.setRequestProperty("Content-Type", "application/json")
        connection.setRequestProperty("cache-control", "no-cache")
        connection.useCaches = false
        connection.doOutput = true
        val osw = OutputStreamWriter(connection.outputStream, "UTF-8")
        osw.write(gson.toJson(TargetPayload(payload).payload))
        osw.flush()
        osw.close()
        connection.outputStream.close()
        connection.connect()

        val buffer = ByteArrayOutputStream()
        val bis = BufferedInputStream(connection.inputStream)

        var chunk = bis.read()
        while (chunk != -1) {
            buffer.write(chunk)
            chunk = bis.read()
        }

        call.respondText(buffer.toString())
    }
}