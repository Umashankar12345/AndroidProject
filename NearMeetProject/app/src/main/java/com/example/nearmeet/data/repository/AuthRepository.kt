package com.example.nearmeet.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepository @Inject constructor(
    private val auth: FirebaseAuth
) {
    private val _currentUser = MutableStateFlow(auth.currentUser)
    val currentUser: StateFlow<FirebaseUser?> = _currentUser

    init {
        auth.addAuthStateListener {
            _currentUser.value = it.currentUser
        }
    }

    fun loginWithEmail(email: String, pass: String, onComplete: (Boolean, String?) -> Unit) {
        auth.signInWithEmailAndPassword(email, pass)
            .addOnCompleteListener { task ->
                onComplete(task.isSuccessful, task.exception?.message)
            }
    }

    fun signUpWithEmail(email: String, pass: String, onComplete: (Boolean, String?) -> Unit) {
        auth.createUserWithEmailAndPassword(email, pass)
            .addOnCompleteListener { task ->
                onComplete(task.isSuccessful, task.exception?.message)
            }
    }

    fun logout() {
        auth.signOut()
    }

    val isUserLoggedIn: Boolean get() = auth.currentUser != null
}
