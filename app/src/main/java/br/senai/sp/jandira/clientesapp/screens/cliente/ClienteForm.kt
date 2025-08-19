package br.senai.sp.jandira.clientesapp.screens.cliente

import android.content.res.Configuration
import android.util.Patterns
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import br.senai.sp.jandira.clientesapp.model.Cliente
import br.senai.sp.jandira.clientesapp.service.Conexao
import br.senai.sp.jandira.clientesapp.ui.theme.ClientesAppTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.intellij.lang.annotations.Pattern
import retrofit2.await

@Composable
fun ClienteForm(padding: PaddingValues, controleNavegacao: NavHostController?) {

    var nomeCliente by remember { mutableStateOf(value = "") }
    var emailCliente by remember { mutableStateOf(value = "") }

    var isNomeError by remember { mutableStateOf(false) }
    var isEmailError by remember { mutableStateOf(false) }

    // Variavel determmina se a mensagem deve aparecer
    var mostrarMensagemSucess by remember { mutableStateOf(false) }

    fun validar(): Boolean{
        isNomeError = nomeCliente.length < 3
        isEmailError = !Patterns.EMAIL_ADDRESS.matcher(emailCliente).matches()

        return !isNomeError && !isEmailError
    }

    val clienteApi = Conexao().getClienteService()


    Surface (
        modifier = Modifier
            .padding(padding)
            .fillMaxSize()
    ){
        Column (
            modifier = Modifier
                .fillMaxSize()
        ){
            Row (
                modifier = Modifier.padding(16.dp).fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ){
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = ""
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = "Novo Cliente",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = nomeCliente,
                onValueChange = {nomeCliente = it},
                modifier = Modifier.padding(16.dp).fillMaxWidth(),
                label = {
                    Text(text = "Nome do Cliente")
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    capitalization = KeyboardCapitalization.Words,
                    imeAction = ImeAction.Next
                ),
                isError = isNomeError,
                supportingText = {
                    if (isNomeError){
                        Text(text = "O nome é obrigatório")
                    }
                },
                trailingIcon = {
                    if (isNomeError){
                        Icon(imageVector = Icons.Default.Info, contentDescription = "")
                    }
                }
            )
            Spacer(modifier = Modifier.height(4.dp))
            OutlinedTextField(
                value = emailCliente,
                onValueChange = {emailCliente = it},
                modifier = Modifier.padding(16.dp).fillMaxWidth(),
                label = {
                    Text(text = "Email do Cliente")
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Done
                ),
                isError = isEmailError,
                supportingText = {
                    if (isEmailError){
                        Text(text = "O email é obrigatório")
                    }
                },
                trailingIcon = {
                    if (isEmailError){
                        Icon(imageVector = Icons.Default.Info, contentDescription = "")
                    }
                }
            )
            Button(
                onClick = {
                    if (validar()){
                        val cliente = Cliente(
                            nome = nomeCliente,
                            email = emailCliente
                        )

                        GlobalScope.launch (Dispatchers.IO){
                            val clienteNovo = clienteApi.cadastrarCliente(cliente).await()
                            mostrarMensagemSucess = true
                        }
                    }else {
                        println("Dados incorretos")
                    }
                },
                modifier = Modifier.padding(16.dp).fillMaxWidth()
            ) {
                Text(text = "Gravar Cliente")
            }
        }
        if (mostrarMensagemSucess){
                AlertDialog(
                    onDismissRequest = {
                        mostrarMensagemSucess = false
                        nomeCliente = ""
                        emailCliente = ""
                    },
                    title = {
                        Text(text = "Sucesso")
                    },
                    text = {
                        Text(text = "Cliente $nomeCliente gravado com sucesso!\nDeseja cadastrar um novo cliente?")
                    },
                    confirmButton = {
                        Button(
                            onClick =  {
                                nomeCliente = ""
                                emailCliente = ""
                                mostrarMensagemSucess = false
                            }
                        ) {
                            Text(text = "Sim")
                        }
                    },
                    dismissButton = {
                        TextButton(
                            onClick =  {
                                controleNavegacao!!.navigate("home")
                            }
                        ) {
                            Text(text = "Nao")
                        }
                    }
                )
        }
    }

}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun ClienteFormPreview(){
    ClientesAppTheme {
        ClienteForm(PaddingValues(0.dp), null)
    }
}