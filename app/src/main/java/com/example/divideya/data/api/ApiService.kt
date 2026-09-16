package com.example.divideya.data.api

import com.example.divideya.data.model.Item
import com.example.divideya.data.model.User
import retrofit2.http.*
import com.example.divideya.data.model.Group
import com.example.divideya.data.model.Participant
interface ApiService {
    // Item CRUD
    @GET("item")
    suspend fun getItems(): List<Item>

    @GET("item/{id}")
    suspend fun getItem(@Path("id") id: String): Item

    @POST("item")
    suspend fun createItem(@Body item: Item): Item

    @PUT("item/{id}")
    suspend fun updateItem(@Path("id") id: String, @Body item: Item): Item

    @DELETE("item/{id}")
    suspend fun deleteItem(@Path("id") id: String)

    // User CRUD

    @GET("group")
    suspend fun getGroups(): List<Group>

    @GET("group/{id}")
    suspend fun getGroup(@Path("id") id: String): Group

    @POST("group")
    suspend fun createGroup(@Body group: Group): Group

    @PUT("group/{id}")
    suspend fun updateGroup(@Path("id") id: String, @Body group: Group): Group

    @DELETE("group/{id}")
    suspend fun deleteGroup(@Path("id") id: String)

    // Participant CRUD (filtrado por groupId)
    @GET("participant")
    suspend fun getParticipants(@Query("groupId") groupId: String? = null): List<Participant>

    @GET("participant/{id}")
    suspend fun getParticipant(@Path("id") id: String): Participant

    @POST("participant")
    suspend fun createParticipant(@Body participant: Participant): Participant

    @PUT("participant/{id}")
    suspend fun updateParticipant(@Path("id") id: String, @Body participant: Participant): Participant

    @DELETE("participant/{id}")
    suspend fun deleteParticipant(@Path("id") id: String)
    @GET("user")
    suspend fun getUsers(): List<User>

    @GET("user/{id}")
    suspend fun getUser(@Path("id") id: String): User

    @POST("user")
    suspend fun createUser(@Body user: User): User

    @PUT("user/{id}")
    suspend fun updateUser(@Path("id") id: String, @Body user: User): User

    @DELETE("user/{id}")
    suspend fun deleteUser(@Path("id") id: String)
}