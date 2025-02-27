package com.example.idftechnology.data.repository

import com.example.idftechnology.data.model.User
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UserRepository @Inject constructor(private val client: HttpClient) {

    suspend fun getUsers(): Flow<List<User>> = flow {
        try {
            val response: HttpResponse = client.get("https://jsonplaceholder.typicode.com/users")
            val users: List<User> = response.body()
            emit(users)
        } catch (e: Exception) {
            emit(emptyList())
        }
    }
}