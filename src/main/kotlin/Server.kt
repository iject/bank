import java.io.*
import java.lang.Exception
import java.net.ServerSocket
import java.net.Socket

class Server(port: Int = 8080) {
    val serverSocket = ServerSocket(port)
    var db: Database? = null
    var clientSocket : Socket? = null
    var bufw: BufferedWriter? = null
    var bufr: BufferedReader? = null

    // запуск серва и бд
    fun start(){
        try {
            println("servSock1") // test
            clientSocket = serverSocket.accept()
            println("servSock2") // test
            db = Database()
            bufw = BufferedWriter(OutputStreamWriter(clientSocket?.getOutputStream()))
            bufr = BufferedReader(InputStreamReader(clientSocket?.getInputStream()))
        }
        catch (e: Exception){
            println("Ошибка подключения к серверу или бд или буфер")
        }
    }

    // отправка сообщения клиенту
    fun send_client(text: String){
        bufw!!.write(text + "\n")
        bufw!!.flush()
    }

    // приём сообщения клиента
    fun get_client():MutableList<String>{
        // получаем на вход параметр обработки данных клента
        var processing_par = bufr!!.readLine()
        // определяем кол-во строк в зависимости от параметра
        var iter_count = when(processing_par){
            "login" -> 2
            "registration" -> 2
            else -> 2
        }
        var msg = mutableListOf<String>()
        msg.add(processing_par)
        for (i in 1..iter_count){
            msg.add(bufr!!.readLine())
        }
        println("Client message: $msg")
        return msg
    }

    fun response_client(msg: MutableList<String>){
        when(msg[0]){
            "login" -> {send_client(bd_search(msg[1],msg[2]))}
            "registration" -> {send_client(bd_create_acc(msg[1],msg[2]))}
            else -> {send_client("что-то пошло не так(")}
        }
    }

    // ищем по логину в бд
    fun bd_search(login: String, pass: String): String{
        var lst = db?.find_user(login)

        if (lst?.isEmpty() == true){
            return "Такого логина не существует"
        }
        if (lst?.get(2) != pass){
            return "Пароли не совпадают"
        }

        return "Вход выполнен, ваш баланс ${lst[3]}руб."
    }

    // создаём аккаунт в бд
    fun bd_create_acc(login: String, pass: String): String{
        var lst = db?.find_user(login)

        if (lst?.isEmpty() == false){
            return "Такой логин уже существует"
        }
        else{
            db?.insert(db!!.count_rows()+1, login, pass)
            return "Аккаунт добавлен"
        }


    }

    /*fun start(){
        try{
            clientSocket = serverSocket.accept()

            // Мой код
            var bufw = BufferedWriter(OutputStreamWriter(clientSocket?.getOutputStream()))
            var bufr = BufferedReader(InputStreamReader(clientSocket?.getInputStream()))

            *//* не получилось(
            // Считываю построчно буфер и формурую одну строку
            var msg = ""
            bufr.forEachLine {
                msg = msg + " " + it
                println("Client message: $it")
                println(msg)
            }
            println("exit")
            bufw.write("Hi from server! Received message: " + msg + "\n")
            bufw.flush()

            var msg = ""
            var line: String? = null
            while (bufr.ready()) {
                line = bufr.readLine()
                println(line)
                msg = msg + " " + line
            }

            var line: String?
            do {
                line = bufr.readLine()
                if (line == null)
                    break
                println(line)
            } while (true)

            val listOfLines = mutableListOf<String>()
            bufr.useLines{
                lines -> lines.forEach {
//                    var x = "> (" + it.length + ") " + it;
                    println(it)
                    listOfLines.add(it)
                }
            }
            listOfLines.forEach{ println(it) }

            var line: String
            try {
                while (bufr.readLine().also { line = it } != null) {
                    println(line)
                }
                bufr.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
            *//*

            // Запись нескольких строк в List
            var iter_count = bufr.readLine().toInt()
            var msg = mutableListOf<String>()
            for (i in 1..iter_count){
                msg.add(bufr.readLine())
            }
            println("Client message: $msg")
            bufw.write("From server. login:${msg[0]} password:${msg[1]}" + "\n")
            bufw.flush()


            bufw.close()
            bufr.close()
            // Конец моего кода
        }
        catch(e: Exception){
            println(e.message)
        }
        finally {
            clientSocket?.close()
            serverSocket?.close()
        }
    }*/

    fun finish(){
        clientSocket?.close()
        bufw?.close()
        bufr?.close()
//        serverSocket?.close() закоментил и сработало
    }
}