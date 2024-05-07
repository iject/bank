fun main(){
    val serv = Server()
    while(true){
        serv.start()
        println("serv start")
        val msg_client = serv.get_client()
        println(msg_client)
//    serv.send_client(serv.bd_search(msg_client[0],msg_client[1]))
        serv.response_client(msg_client)
        serv.finish()
    }
}