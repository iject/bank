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

@Composable
@Preview
fun App() {
    var login by remember { mutableStateOf("log") }
    var password by remember { mutableStateOf("pass") }
    var text by remember { mutableStateOf<String?>(null) }
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
                    client.send("2")
                    client.send(login)
                    client.send(password)
                    text = client.get()
                    client.finish()
                }) {
                    Text("Войти")
                }
                Button(onClick = {
                    print(2)
                }) {
                    Text("Регистрация")
                }
            }
            Row {
                if (text != null)
                    Text(text!!)
                else{
                    Text("empty text")
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
