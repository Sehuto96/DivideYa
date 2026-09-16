package com.example.divideya.data.repository

import com.example.divideya.data.api.ApiService
import com.example.divideya.data.model.Participant

class ParticipantRepository(private val apiService: ApiService) {
    suspend fun getParticipants(groupId: String? = null) = apiService.getParticipants(groupId)
    suspend fun getParticipant(id: String) = apiService.getParticipant(id)
    suspend fun createParticipant(participant: Participant) = apiService.createParticipant(participant)
    suspend fun updateParticipant(id: String, participant: Participant) = apiService.updateParticipant(id, participant)
    suspend fun deleteParticipant(id: String) = apiService.deleteParticipant(id)
}