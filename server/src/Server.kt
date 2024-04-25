import java.io.*
import java.lang.Exception
import java.net.ServerSocket
import java.net.Socket

class Server(val port: Int = 8080) {
    val serverSocket = ServerSocket(port)
    var db = Database()
    var clientSocket : Socket? = null
    var bufw: BufferedWriter? = null
    var bufr: BufferedReader? = null

    fun start(){
        try {
            clientSocket = serverSocket.accept()
        }
        catch (e: Exception){
            println(e.message)
        }
    }

    fun send_client(text: String){
        bufw = BufferedWriter(OutputStreamWriter(clientSocket?.getOutputStream()))
        bufw!!.write(text + "\n")
        bufw!!.flush()
    }

    fun get_client():MutableList<String>{
        bufr = BufferedReader(InputStreamReader(clientSocket?.getInputStream()))

        var iter_count = bufr!!.readLine().toInt()
        var msg = mutableListOf<String>()
        for (i in 1..iter_count){
            msg.add(bufr!!.readLine())
        }
        println("Client message: $msg")
        return msg
    }

    fun start(){
        try{
            clientSocket = serverSocket.accept()

            // Мой код
            var bufw = BufferedWriter(OutputStreamWriter(clientSocket?.getOutputStream()))
            var bufr = BufferedReader(InputStreamReader(clientSocket?.getInputStream()))

            /* не получилось(
            // Считываю построчно буфер и формурую одну строку
            /*var msg = ""
            bufr.forEachLine {
                msg = msg + " " + it
                println("Client message: $it")
                println(msg)
            }
            println("exit")
            bufw.write("Hi from server! Received message: " + msg + "\n")
            bufw.flush()*/

            /*var msg = ""
            var line: String? = null
            while (bufr.ready()) {
                line = bufr.readLine()
                println(line)
                msg = msg + " " + line
            }*/

            // Попробовать это
            /*var line: String?
            do {
                line = bufr.readLine()
                if (line == null)
                    break
                println(line)
            } while (true)*/

            /*val listOfLines = mutableListOf<String>()
            bufr.useLines{
                lines -> lines.forEach {
//                    var x = "> (" + it.length + ") " + it;
                    println(it)
                    listOfLines.add(it)
                }
            }
            listOfLines.forEach{ println(it) }*/

            /*var line: String
            try {
                while (bufr.readLine().also { line = it } != null) {
                    println(line)
                }
                bufr.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }*/
            */

            // Запись нескольких строк в List
            var iter_count = bufr.readLine().toInt()
            var msg = mutableListOf<String>()
            for (i in 1..iter_count){
                msg.add(bufr.readLine())
            }
            println("Client message: $msg")
            bufw.write("From server. login:${msg[0]} password:${msg[1]}" + "\n")
            bufw.flush()


            // Чтение одной строки и вывод в консоль и буфер
            /*var msg = bufr.readLine()
            println("Client message: $msg")
            bufw.write("Hi from server! Received message: $msg" + "\n")
            bufw.flush()*/

            bufw.close()
            bufr.close()
            // Конец моего кода

            /* Makarov code
            var bw = clientSocket.getInputStream().bufferedReader()
            println(bw.readLine())
            var resp = "Hello from server"
            var pw = PrintWriter(clientSocket.getOutputStream())
            pw.println(resp)
            pw.flush()
            bw.close()
            pw.close()
            */
        }
        catch(e: Exception){
            println(e.message)
        }
        finally {
            clientSocket?.close()
            serverSocket?.close()
        }
    }

    fun finish(){
        clientSocket?.close()
        serverSocket?.close()
        bufw?.close()
        bufr?.close()
    }
}