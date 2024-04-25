import java.sql.DriverManager
import java.sql.PreparedStatement


class Database(){
    val user = "root"
    val password = "root"
    val url = "jdbc:mysql://localhost:3306/bank"
    val connection = DriverManager.getConnection(url, user, password)
    val statement = connection.createStatement()
    fun create_data(){
        try{
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS User" +
                    "(" +
                    "id INT PRIMARY KEY," +
                    "login VARCHAR(30) NOT NULL," +
                    "password VARCHAR(30) NOT NULL," +
                    "balance INT DEFAULT(100) NOT NULL" +
                    ");")
        }
        catch(e:Exception){
            println("Ошибка создания таблицы!")
        }
    }

    fun select(id: Int):MutableList<String> {
        try {
            var result = mutableListOf<String>()
            val preparedStatement = connection.prepareStatement("SELECT * FROM user WHERE id = ?;")
            preparedStatement.setInt(1, id)
            val resultSet = preparedStatement.executeQuery()

            while (resultSet.next()) {
                result.addAll(listOf(resultSet.getInt(1).toString(),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getInt(4).toString()))
            }
            return result
        }
        catch (e: Exception){
            println("Ошибка запроса!")
            return mutableListOf<String>()
        }
    }

    fun find_user(login: String):MutableList<String> {
        try {
            var result = mutableListOf<String>()
            val preparedStatement = connection.prepareStatement("SELECT * FROM user WHERE login = ?;")
            preparedStatement.setString(1, login)
            val resultSet = preparedStatement.executeQuery()

            while (resultSet.next()) {
                result.addAll(listOf(resultSet.getInt(1).toString(),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getInt(4).toString()))
            }
            return result
        }
        catch (e: Exception){
            println("Ошибка запроса!")
            return mutableListOf<String>()
        }
    }

    fun insert(id: Int, login: String, password: String, balance: Int){
        try{
            /*statement.executeQuery("INSERT INTO bank (id, login, password, balance)" +
                    " VALUES (" + id + "," + login + "," + password + "," + balance + ");")*/
            /*statement.executeUpdate("INSERT INTO user (id, login, password, balance)" +
                    " VALUES (1, 'test', 'test', 100);")*/
            var sql = """
                INSERT INTO user (id, login, password, balance)
                VALUES (?, ?, ?, ?);
                """.trimIndent()
            var preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, login);
            preparedStatement.setString(3, password);
            preparedStatement.setInt(4, balance);

            preparedStatement.executeUpdate();
        }
        catch(e: Exception){
            println("Ошибка записи")
        }
    }
}