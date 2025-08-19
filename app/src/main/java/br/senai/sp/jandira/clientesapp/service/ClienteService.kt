package br.senai.sp.jandira.clientesapp.service

import br.senai.sp.jandira.clientesapp.model.Cliente
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ClienteService {

    @POST("clientes")
    fun cadastrarCliente(@Body cliente: Cliente): Call<Cliente>

    @GET("clientes")
    fun listarTodos(): Call<List<Cliente>>

    @GET("clientes/{id}")
    fun buscarPorId(@Path("id") id: Long): Call<List<Cliente>>

    @PUT("clientes")
    fun atualizarCliente(@Body cliente: Cliente): Call<Cliente>

    @HTTP(method = "DELETE", path = "clientes", hasBody = true)
    fun deletarCliente(@Body cliente: Cliente): Unit
}