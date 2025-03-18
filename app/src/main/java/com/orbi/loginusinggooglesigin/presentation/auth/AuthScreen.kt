package com.orbi.loginusinggooglesigin.presentation.auth

import android.os.Build
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.orbi.loginusinggooglesigin.domain.AuthState
import coil.compose.AsyncImage

@Composable
fun AuthScreen(
    authState: AuthState,
    onSignIn: () -> Unit,
    onSignOut: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF2F2F2))
    ) {
        when (authState) {
            is AuthState.NotSignedIn -> {
                GoogleSignInButton(onClick = onSignIn, modifier = Modifier.align(Alignment.Center))
            }
            is AuthState.SignedIn -> {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter)
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Bottom,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    UserAccountCard(
                        email = authState.email,
                        displayName = authState.displayName,
                        photoUrl = authState.photoUrl,
                        onSignOut = onSignOut
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    DeviceInfoCard()
                }
            }
        }
    }
}

@Composable
fun GoogleSignInButton(onClick: () -> Unit, modifier: Modifier = Modifier) {
    Button(onClick = onClick, modifier = modifier) {
        Text("Inicia sesión con Google")
    }
}

@Composable
fun UserAccountCard(email: String, displayName: String?, photoUrl: String?, onSignOut: () -> Unit) {
    Card(
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(8.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (photoUrl != null) {
                AsyncImage(
                    model = photoUrl,
                    contentDescription = "Foto de perfil",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape)
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
            Text("Email: $email")
            Text("Nombre: ${displayName ?: "N/D"}")
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = onSignOut) {
                Text("Cerrar sesión")
            }
        }
    }
}

@Composable
fun DeviceInfoCard() {
    Card(
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(8.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Datos del Dispositivo", color = Color.Black)
            Spacer(modifier = Modifier.height(8.dp))
            Text("Fabricante: ${Build.MANUFACTURER}")
            Text("Modelo: ${Build.MODEL}")
            Text("Versión Android: ${Build.VERSION.RELEASE}")
        }
    }
}
