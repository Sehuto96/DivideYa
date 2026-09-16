package com.example.divideya.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.divideya.data.api.RetrofitClient
import com.example.divideya.data.model.Group
import com.example.divideya.data.model.Participant
import com.example.divideya.data.repository.GroupRepository
import com.example.divideya.data.repository.ParticipantRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class GroupViewModel : ViewModel() {
    private val groupRepository = GroupRepository(RetrofitClient.apiService)
    private val participantRepository = ParticipantRepository(RetrofitClient.apiService)

    private val _groups = MutableStateFlow<List<Group>>(emptyList())
    val groups: StateFlow<List<Group>> = _groups

    private val _participants = MutableStateFlow<List<Participant>>(emptyList())
    val participants: StateFlow<List<Participant>> = _participants

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    init {
        fetchGroups()
    }

    // ---- Groups ----

    fun fetchGroups() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                _groups.value = groupRepository.getGroups()
                _error.value = null
            } catch (e: Exception) {
                e.printStackTrace()
                _error.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun addGroup(name: String, description: String) {
        viewModelScope.launch {
            try {
                groupRepository.createGroup(Group(name = name, description = description))
                fetchGroups()
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }

    fun updateGroup(id: String, name: String, description: String) {
        viewModelScope.launch {
            try {
                groupRepository.updateGroup(id, Group(id = id, name = name, description = description))
                fetchGroups()
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }

    fun deleteGroup(id: String) {
        viewModelScope.launch {
            try {
                groupRepository.deleteGroup(id)
                fetchGroups()
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }

    // ---- Participants ----

    fun fetchParticipants(groupId: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                _participants.value = participantRepository.getParticipants(groupId)
                _error.value = null
            } catch (e: Exception) {
                e.printStackTrace()
                _error.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun addParticipant(groupId: String, name: String, email: String) {
        viewModelScope.launch {
            try {
                participantRepository.createParticipant(
                    Participant(groupId = groupId, name = name, email = email)
                )
                fetchParticipants(groupId)
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }

    fun removeParticipant(groupId: String, participantId: String) {
        viewModelScope.launch {
            try {
                participantRepository.deleteParticipant(participantId)
                fetchParticipants(groupId)
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }
}