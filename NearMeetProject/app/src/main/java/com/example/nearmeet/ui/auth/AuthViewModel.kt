package com.example.nearmeet.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nearmeet.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    val isLoggedIn: StateFlow<Boolean> = authRepository.currentUser
        .map { it != null }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = authRepository.isUserLoggedIn
        )

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun login(email: String, pass: String) {
        if (!validate(email, pass)) return
        
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            val result = authRepository.loginWithEmail(email.trim(), pass.trim())
            _isLoading.value = false
            result.onFailure { _error.value = it.message }
        }
    }

    fun signUp(email: String, pass: String) {
        if (!validate(email, pass)) return
        
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            val result = authRepository.signUpWithEmail(email.trim(), pass.trim())
            _isLoading.value = false
            result.onFailure { _error.value = it.message }
        }
    }

    private fun validate(email: String, pass: String): Boolean {
        return when {
            email.isBlank() || pass.isBlank() -> {
                _error.value = "Fields cannot be empty"
                false
            }
            pass.length < 6 -> {
                _error.value = "Password must be at least 6 characters"
                false
            }
            else -> true
        }
    }

    fun clearError() {
        _error.value = null
    }
}
