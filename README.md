# LoginUsingGoogleSignIn

Este proyecto es una aplicación Android desarrollada en Kotlin con Jetpack Compose que utiliza el flujo **One Tap Sign-In** de Google para autenticación. Se implementa siguiendo el patrón **MVVM** y algunos principios de **Clean Architecture**, separando la lógica de autenticación (ViewModel y modelo de dominio) de la interfaz de usuario (composables).

## Características

- **One Tap Sign-In de Google:**  
  Permite iniciar sesión con Google mediante una interfaz emergente (bottom sheet) para seleccionar la cuenta.

- **Información del Usuario:**  
  Una vez autenticado, se muestra la información básica del usuario (email, nombre, foto de perfil) en una tarjeta.

- **Datos del Dispositivo:**  
  Se muestran datos del dispositivo (fabricante, modelo y versión de Android) en la interfaz.

- **MVVM y Clean Architecture:**  
  La lógica de autenticación se encuentra en el ViewModel y el modelo de dominio, separada de la UI.

## Requisitos y Dependencias

### Google Cloud & Credenciales

- **Proyecto en Google Cloud Console:**  
  Debes crear un proyecto en [Google Cloud Console](https://console.cloud.google.com/) y habilitar la API de Google Sign-In (One Tap).

- **Credenciales OAuth:**  
  Crea unas credenciales OAuth de tipo **Web Application** y obtén el **Web Client ID**.  
  **Nota:** El `client secret` debe mantenerse privado y no se usa directamente en la aplicación Android.

- **Configuración en el proyecto:**  
  En el archivo `strings.xml` agrega el Web Client ID:
  ```xml
  <resources>
      <string name="app_name">LoginUsingGoogleSignIn</string>
      <string name="web_client_id">497880819643-ertinhnqoo3v72iqpjtcvgflekijkhsn.apps.googleusercontent.com</string>
  </resources>
  ```

**Dependencias Gradle**

Asegúrate de incluir estas dependencias en tu archivo build.gradle (módulo app):

```scss
  <resources>
      <string name="app_name">LoginUsingGoogleSignIn</string>
      <string name="web_client_id">497880819643-ertinhnqoo3v72iqpjtcvgflekijkhsn.apps.googleusercontent.com</string>
  </resources>
```

**Estructura del Proyecto**
```scss
com.orbi.loginusinggooglesigin
├── MainActivity.kt
├── domain
│   └── AuthState.kt
└── presentation
    └── auth
        ├── AuthViewModel.kt
        └── AuthScreen.kt
```

- **MainActivity.kt:**  
  Punto de entrada de la aplicación. Inyecta el ViewModel y configura la UI.

- **domain/AuthState.kt:**  
  Define los posibles estados de autenticación (no autenticado o autenticado con datos).

- **presentation/auth/AuthViewModel.kt:**  
  Contiene la lógica de autenticación con One Tap (inicialización, sign in, procesamiento del resultado y sign out).
Expone un estado observable que la UI utiliza para actualizarse.

- **presentation/auth/AuthScreen.kt:**  
  Contiene los composables de la pantalla de autenticación, mostrando el botón de sign in, la tarjeta del usuario y los datos del dispositivo según el estado.

