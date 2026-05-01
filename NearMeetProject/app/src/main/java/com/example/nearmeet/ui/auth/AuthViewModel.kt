package com.example.nearmeet.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nearmeet.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _isLoggedIn = MutableStateFlow(authRepository.isUserLoggedIn)
    val isLoggedIn: StateFlow<Boolean> = _isLoggedIn

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun login(email: String, pass: String) {
        authRepository.loginWithEmail(email, pass) { success, msg ->
            if (success) _isLoggedIn.value = true
            else _error.value = msg
        }
    }

    fun signUp(email: String, pass: String) {
        authRepository.signUpWithEmail(email, pass) { success, msg ->
            if (success) _isLoggedIn.value = true
            else _error.value = msg
        }
    }

    fun clearError() {
        _error.value = null
    }

    fun logout() {
        authRepository.logout()
        _isLoggedIn.value = false
    }
}
