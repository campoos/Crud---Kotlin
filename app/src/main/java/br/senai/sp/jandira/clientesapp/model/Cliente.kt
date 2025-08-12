package br.senai.sp.jandira.clientesapp.model

data class Cliente (
    val id: Long? = 0,
    val nome: String = "",
    var email: String = ""
)