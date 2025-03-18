package com.orbi.loginusinggooglesigin.domain

sealed class AuthState {
    object NotSignedIn : AuthState()
    data class SignedIn(
        val idToken: String?,
        val email: String,
        val displayName: String?,
        val photoUrl: String?
    ) : AuthState()
}
