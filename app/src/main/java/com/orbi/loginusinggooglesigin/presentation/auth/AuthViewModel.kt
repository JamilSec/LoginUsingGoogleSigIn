package com.orbi.loginusinggooglesigin.presentation.auth

import android.content.Intent
import android.content.IntentSender
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.common.api.ApiException
import com.orbi.loginusinggooglesigin.domain.AuthState

class AuthViewModel : ViewModel() {

    private lateinit var oneTapClient: SignInClient
    private lateinit var signInRequest: BeginSignInRequest

    private val _authState = mutableStateOf<AuthState>(AuthState.NotSignedIn)
    val authState: State<AuthState> get() = _authState

    fun initialize(clientId: String, activity: ComponentActivity) {
        oneTapClient = Identity.getSignInClient(activity)
        signInRequest = BeginSignInRequest.builder()
            .setGoogleIdTokenRequestOptions(
                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    .setServerClientId(clientId)
                    .setFilterByAuthorizedAccounts(false) // Muestra TODAS las cuentas
                    .build()
            )
            .build()
    }

    fun signIn(activity: ComponentActivity, onFailure: (String) -> Unit) {
        oneTapClient.beginSignIn(signInRequest)
            .addOnSuccessListener(activity) { result ->
                try {
                    activity.startIntentSenderForResult(
                        result.pendingIntent.intentSender,
                        REQ_ONE_TAP,
                        null, 0, 0, 0
                    )
                } catch (e: IntentSender.SendIntentException) {
                    onFailure("Error iniciando el flujo: ${e.localizedMessage}")
                }
            }
            .addOnFailureListener(activity) { e ->
                onFailure("One Tap sign in falló: ${e.localizedMessage}")
            }
    }

    fun handleSignInResult(data: Intent?) {
        try {
            val credential = oneTapClient.getSignInCredentialFromIntent(data)
            val idToken = credential.googleIdToken
            val email = credential.id
            val displayName = credential.displayName
            val photoUrl = credential.profilePictureUri?.toString()

            if (email != null) {
                _authState.value = AuthState.SignedIn(idToken, email, displayName, photoUrl)
            }
        } catch (e: ApiException) {
            Log.e("AuthViewModel", "Error al procesar sign in: ${e.localizedMessage}")
        }
    }

    fun signOut(activity: ComponentActivity) {
        oneTapClient.signOut().addOnCompleteListener {
            _authState.value = AuthState.NotSignedIn
        }
    }

    companion object {
        const val REQ_ONE_TAP = 100
    }
}
