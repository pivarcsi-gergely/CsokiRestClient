package hu.petrik.csokirestclient

import android.util.Log
import java.io.*
import java.net.HttpURLConnection
import java.net.URL
import java.util.*

    class RetoolConnection(urlExtension: String, method: String) {
        private val conn: HttpURLConnection
        val responseContent: InputStream
            get() = try {
                conn.inputStream
            } catch (e: IOException) {
                conn.errorStream
            }

        @Throws(IOException::class)
        fun putJSON(json: String?) {
            val os = conn.outputStream
            val bw = BufferedWriter(OutputStreamWriter(os))
            bw.write(json)
            bw.flush()
        }

        @Throws(IOException::class)
        fun getCall(): Response{
            return  Response(conn.responseCode, responseContent)
        }

        @Throws(IOException::class)
        fun deleteCall(): Response {
            return Response(conn.responseCode, responseContent)
        }

        @Throws(IOException::class)
        fun postCall(jsonContent: String?): Response {
            putJSON(jsonContent)
            return Response(conn.responseCode, responseContent)
        }

        @Throws(IOException::class)
        fun patchCall(jsonContent: String?): Response {
            putJSON(jsonContent)
            return Response(conn.responseCode, responseContent)
        }

        @get:Throws(IOException::class)
        val contentString: String
            get() {
                val bufferedReader = BufferedReader(
                    InputStreamReader(
                        responseContent
                    )
                )
                val boby = StringBuilder()
                var line = bufferedReader.readLine()
                while (line != null) {
                    boby.append(line)
                    line = bufferedReader.readLine()
                }
                return boby.toString()
            }

        fun getrequestMethod(): String {
            return conn.requestMethod
        }

        init {
            val methodTemp = method.uppercase(Locale.ROOT)
            val finalUrl = "https://retoolapi.dev/Q304E6$urlExtension"
            Log.d("Új kapcsolat / $methodTemp", finalUrl)
            conn = URL(finalUrl).openConnection() as HttpURLConnection
            conn.requestMethod = methodTemp
            conn.setRequestProperty("Accept", "application/json")
            if (methodTemp === "POST" || methodTemp === "PATCH") {
                conn.doOutput = true
                conn.setRequestProperty("Content-Type", "application/json")
            }
        }
    }
}