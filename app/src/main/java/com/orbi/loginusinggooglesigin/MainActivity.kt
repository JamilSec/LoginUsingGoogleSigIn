package com.orbi.loginusinggooglesigin

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import com.orbi.loginusinggooglesigin.presentation.auth.AuthScreen
import com.orbi.loginusinggooglesigin.presentation.auth.AuthViewModel
import com.orbi.loginusinggooglesigin.ui.theme.LoginUsingGoogleSigInTheme

class MainActivity : ComponentActivity() {

    private val authViewModel: AuthViewModel by viewModels()

    // Registro para procesar el resultado del flujo One Tap
    private val signInLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            authViewModel.handleSignInResult(result.data)
        } else {
            Log.e("MainActivity", "Sign in cancelado o fallido")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Inicializa el ViewModel con el Web Client ID (definido en strings.xml)
        authViewModel.initialize(getString(R.string.web_client_id), this)

        setContent {
            LoginUsingGoogleSigInTheme {
                AuthScreen(
                    authState = authViewModel.authState.value,
                    onSignIn = { authViewModel.signIn(this) { error -> Log.e("MainActivity", error) } },
                    onSignOut = { authViewModel.signOut(this) }
                )
            }
        }
    }

    // Redirige el resultado al ViewModel a través del ActivityResultLauncher
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == AuthViewModel.REQ_ONE_TAP) {
            if (resultCode == RESULT_OK) {
                authViewModel.handleSignInResult(data)
            } else {
                Log.e("MainActivity", "Sign in cancelado o fallido")
            }
        }
    }
}
