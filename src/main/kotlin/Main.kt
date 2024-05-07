fun main() {
    val db = Database()
    db.create_data()
    db.insert(3, "login", "password", 100)
    db.select(1).forEach { print("$it ") }
    println()
    db.find_user("login").forEach { print("$it ") }
    println()
    println("кол-во строк в бд: ${db.count_rows()}")

    println(db.select(6).isEmpty())
//    println(db.select(2))

}