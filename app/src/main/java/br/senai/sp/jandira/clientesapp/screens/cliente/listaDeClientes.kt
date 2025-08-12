package br.senai.sp.jandira.clientesapp.screens.cliente

import android.content.res.Configuration
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import br.senai.sp.jandira.clientesapp.screens.cliente.componentes.barraDeTitulo
import br.senai.sp.jandira.clientesapp.screens.cliente.componentes.barraInferior
import br.senai.sp.jandira.clientesapp.screens.cliente.componentes.botaoFlutuante
import br.senai.sp.jandira.clientesapp.screens.cliente.componentes.conteudo
import br.senai.sp.jandira.clientesapp.ui.theme.ClientesAppTheme

@Composable
fun listaCLienteSceen (modifier: Modifier = Modifier){

    var controleNavegacao = rememberNavController()

    Scaffold(
        topBar = {
            barraDeTitulo()
        },
        bottomBar = {
            barraInferior(controleNavegacao)
        },
        floatingActionButton = {
            botaoFlutuante(controleNavegacao)
        },
        content = {padding ->
            NavHost(
                navController = controleNavegacao,
                startDestination = "Conteudo"
            ){
                composable(route = "Conteudo"){ conteudo(padding) }
                composable(route = "Cadastro"){ ClienteForm(padding, controleNavegacao) }
            }
        }
    )
}


@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun listaClientePreview(){
    ClientesAppTheme {
        listaCLienteSceen()
    }
}