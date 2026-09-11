package com.example.divideya.data.api

import com.example.divideya.data.model.Expense
import com.example.divideya.data.model.Item
import com.example.divideya.data.model.Split
import com.example.divideya.data.model.User
import retrofit2.http.*

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

    // Expense CRUD
    @GET("expense")
    suspend fun getExpensesByGroup(@Query("groupId") groupId: String): List<Expense>

    @GET("expense/{id}")
    suspend fun getExpense(@Path("id") id: String): Expense

    @POST("expense")
    suspend fun createExpense(@Body expense: Expense): Expense

    @PUT("expense/{id}")
    suspend fun updateExpense(@Path("id") id: String, @Body expense: Expense): Expense

    @DELETE("expense/{id}")
    suspend fun deleteExpense(@Path("id") id: String)

    // Split CRUD
    @GET("split")
    suspend fun getSplitsByExpense(@Query("expenseId") expenseId: String): List<Split>

    @POST("split")
    suspend fun createSplit(@Body split: Split): Split

    @PUT("split/{id}")
    suspend fun updateSplit(@Path("id") id: String, @Body split: Split): Split

    @DELETE("split/{id}")
    suspend fun deleteSplit(@Path("id") id: String)
}