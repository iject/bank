import java.io.*
import java.net.Socket

class Client(val host: String ="localhost", val port: Int = 8080) {
    fun start(){
        var socket :Socket? = null //открываем соединение

        try {
            socket = Socket(host, port)

            // Мой код
            var bufw = BufferedWriter(OutputStreamWriter(socket.getOutputStream()))
            var bufr = BufferedReader(InputStreamReader(socket.getInputStream()))

            println("Напишите сообщение")
            var msg = readln()
            bufw.write(msg + "\n")
            bufw.flush()

            var serverMsg = bufr.readLine()
            println(serverMsg)

            bufw.close()
            bufr.close()
            // Конец моего кода

            /* Makarov code
            var text: String = "Hi!"
            var pw = PrintWriter(socket.getOutputStream())
            pw.println(text)
            pw.flush()
            */
        }
        catch (e: Exception){
            println(e.message)
        }
        finally{
            socket?.close()
        }

    }
}