package com.example.divideya.data.repository

import com.example.divideya.data.api.ApiService
import com.example.divideya.data.model.Group

class GroupRepository(private val apiService: ApiService) {
    suspend fun getGroups() = apiService.getGroups()
    suspend fun getGroup(id: String) = apiService.getGroup(id)
    suspend fun createGroup(group: Group) = apiService.createGroup(group)
    suspend fun updateGroup(id: String, group: Group) = apiService.updateGroup(id, group)
    suspend fun deleteGroup(id: String) = apiService.deleteGroup(id)
}