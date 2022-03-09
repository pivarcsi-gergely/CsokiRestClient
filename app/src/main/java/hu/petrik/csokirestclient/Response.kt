package hu.petrik.csokirestclient

import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader

class Response {
    var code: Int
    var content: String

    constructor(code: Int, content: String) {
        this.code = code
        this.content = content
    }

    constructor(code: Int, content: InputStream?) {
        this.code = code
        val bufferedReader = BufferedReader(InputStreamReader(content))
        val boby = StringBuilder()
        var line = bufferedReader.readLine()
        while (line != null) {
            boby.append(line)
            line = bufferedReader.readLine()
        }
        bufferedReader.close()
        this.content = boby.toString()
    }
}