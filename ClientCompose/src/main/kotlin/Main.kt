import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import kotlin.system.exitProcess

@Composable
@Preview
fun App() {
    var login by remember { mutableStateOf("login") }
    var password by remember { mutableStateOf("password") }
    var response_serv by remember { mutableStateOf<String?>(null) }
    var windowFlag by remember { mutableStateOf(false) }
    var client = Client_my()

    MaterialTheme {
        Column(modifier = Modifier.fillMaxSize())
        {
            Row{
                Text("Логин")
                TextField(value = login, onValueChange = {login = it})
            }
            Row{
                Text("Пароль")
                TextField(value = password, onValueChange = {password = it},
                    visualTransformation = PasswordVisualTransformation())
            }
            Row{
                Button(onClick = {
                    client.start()
                    client.send("login")
                    client.send(login)
                    client.send(password)
                    response_serv = client.get()
                    client.finish()
                }) {
                    Text("Войти")
                }
                Button(onClick = {
                    client.start()
                    client.send("registration")
                    client.send(login)
                    client.send(password)
                    response_serv = client.get()
                    client.finish()
                }) {
                    Text("Регистрация")
                }
            }
            Row {
                if (response_serv != null){
                    if (response_serv!!.contains("Вход выполнен")){
                        windowFlag = true
                    }
                    else Text(response_serv!!)
                }

                else{
                    Text("empty text")
                }
            }
        }
        if (windowFlag) {
            Window(onCloseRequest = {
                windowFlag = false
                response_serv = null
            }) {
                Row {
                    Text(response_serv!!)
                    Button(onClick = {
                        windowFlag = false
                        response_serv = null
                    }){
                        Text("Exit")
                    }
                }
            }
        }
    }
}

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        App()
    }
}
