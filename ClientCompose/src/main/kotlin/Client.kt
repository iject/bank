import java.io.*
import java.net.Socket

class Client(val host: String ="localhost", val port: Int = 8080) {
    var socket: Socket? = null //открываем соединение
    var pw: PrintWriter? = null
    var bw: BufferedReader? = null

    fun start(){
        try {
            socket = Socket(host, port)
        }
        catch (e: Exception){
            println(e.message)
        }
    }

    fun send(text: String){
        var pw = PrintWriter(socket?.getOutputStream())
        pw?.println(text)
        pw?.flush()
    }

    fun get():String?{
        var bw = socket?.getInputStream()?.bufferedReader()
        return bw?.readLine()
    }

    fun finish(){
        socket?.close()
        pw?.close()
        bw?.close()
    }
}