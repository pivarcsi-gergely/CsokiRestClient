package hu.petrik.csokirestclient

import android.provider.ContactsContract.CommonDataKinds.Website.URL
import java.io.*
import java.net.HttpURLConnection
import java.net.URL
import java.nio.charset.StandardCharsets

class RequestHandler {
    private fun RequestHandler() {}
    companion object {
        @Throws(IOException::class)
        fun get(url: String): Response? {
            val httpConn: HttpURLConnection = setupConnection(url)
            return getResponse(httpConn)
        }

        @Throws(IOException::class)
        fun post(url: String, body: String): Response? {
            val httpConn: HttpURLConnection = setupConnection(url)
            httpConn.setRequestMethod("POST")
            addRequestBody(httpConn, body)
            return getResponse(httpConn)
        }

        @Throws(IOException::class)
        fun put(url: String, body: String): Response? {
            val httpConn: HttpURLConnection = setupConnection(url)
            httpConn.setRequestMethod("PUT")
            addRequestBody(httpConn, body)
            return getResponse(httpConn)
        }

        @Throws(IOException::class)
        fun delete(url: String): Response? {
            val httpConn: HttpURLConnection = setupConnection(url)
            httpConn.setRequestMethod("DELETE")
            return getResponse(httpConn)
        }


        @Throws(IOException::class)
        private fun setupConnection(url: String): HttpURLConnection {
            val urlObject = URL(url)
            val httpConn: HttpURLConnection = urlObject.openConnection() as HttpURLConnection
            httpConn.setRequestProperty("Accept", "application/json")
            httpConn.setConnectTimeout(15000)
            httpConn.setReadTimeout(15000)
            return httpConn
        }

        @Throws(IOException::class)
        private fun addRequestBody(httpConn: HttpURLConnection, body: String) {
            httpConn.setRequestProperty("Content-Type", "application/json")
            httpConn.setDoOutput(true)
            val os: OutputStream = httpConn.getOutputStream()
            val bw = BufferedWriter(OutputStreamWriter(os, StandardCharsets.UTF_8))
            bw.write(body)
            bw.flush()
            bw.close()
        }

        @Throws(IOException::class)
        private fun getResponse(httpConn: HttpURLConnection): Response? {
            val `is`: InputStream
            val responseCode: Int = httpConn.getResponseCode()
            `is` = if (responseCode < 400) {
                httpConn.getInputStream()
            } else {
                httpConn.getErrorStream()
            }
            val sBuilder = StringBuilder()
            val br = BufferedReader(InputStreamReader(`is`))
            var sor: String = br.readLine()
            while (sor != null) {
                sBuilder.append(sor)
                sor = br.readLine()
            }
            br.close()
            `is`.close()
            httpConn.disconnect()
            return Response(responseCode, sBuilder.toString())
        }
    }
}