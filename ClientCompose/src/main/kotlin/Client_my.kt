import java.io.*
import java.net.Socket

class Client_my(val host: String ="localhost", val port: Int = 8080) {
    var socket :Socket? = null
    var bufw: BufferedWriter? = null
    var bufr: BufferedReader? = null

    fun start(){
        try {
            socket = Socket(host, port)
        }
        catch (e: Exception){
            println(e.message)
        }
    }

    fun send(text: String){
        bufw = BufferedWriter(OutputStreamWriter(socket?.getOutputStream()))
        bufw!!.write(text + "\n")
        bufw!!.flush()
        /*var pw = PrintWriter(socket?.getOutputStream())
        pw?.println(text)
        pw?.flush()*/
    }

    fun get():String?{
        bufr = BufferedReader(InputStreamReader(socket?.getInputStream()))
        return bufr!!.readLine()
        /*var bw = socket?.getInputStream()?.bufferedReader()
        return bw?.readLine()*/
    }

    fun finish(){
        socket?.close()
        bufw?.close()
        bufr?.close()
    }
}