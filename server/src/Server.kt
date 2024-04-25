import java.io.*
import java.lang.Exception
import java.net.ServerSocket
import java.net.Socket

class Server(val port: Int = 8080) {

    val serverSocket = ServerSocket(port)

    fun start(){
        var clientSocket : Socket? = null
        try{
            clientSocket = serverSocket.accept()
            var bw = BufferedReader(InputStreamReader(clientSocket.getInputStream()))
            println(bw.readLine())
        }
        catch(e: Exception){
            println(e.message)
        }
        finally {
            println("finally")
            clientSocket?.close()
        }
    }
}