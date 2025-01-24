package com.isarthaksharma.splitespree.service

import android.app.Activity
import android.content.Context
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import com.google.firebase.Firebase
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
import com.isarthaksharma.splitespree.R
import com.isarthaksharma.splitespree.model.Authentication.AuthResponse
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import java.security.MessageDigest
import java.util.UUID
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.isarthaksharma.splitespree.model.Authentication.SessionManager


class AuthenticationManager(private val context: Context) {

    private val auth = Firebase.auth

    // creating account using email
    fun createAccountWithEmail(email: String, password: String): Flow<AuthResponse> = callbackFlow {

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    trySend(AuthResponse.Success)
                } else {
                    trySend(
                        AuthResponse.Error(
                            message = task.exception?.message
                                ?: "Something Went Wrong With Registration"
                        )
                    )
                }
            }
        awaitClose()
    }

    // Login in With Email & Password
    fun loginWithEmail(email: String, password: String): Flow<AuthResponse> = callbackFlow {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    val userId = user?.uid ?: ""
                    val userName = user?.displayName ?: ""
                    SessionManager(context).saveUserSession(userId, userName)

                    trySend(AuthResponse.Success)
                } else {
                    trySend(
                        AuthResponse.Error(
                            message = task.exception?.message ?: "Something Went Wrong With Login"
                        )
                    )
                }
            }
        awaitClose()
    }

    // LOGIN USING FACEBOOK
    fun loginWithFacebook(callbackManager: CallbackManager) = callbackFlow {
        val loginManager = LoginManager.getInstance()

        val callback = object : FacebookCallback<LoginResult> {
            override fun onSuccess(loginResult: LoginResult) {
                val credential = FacebookAuthProvider.getCredential(loginResult.accessToken.token)
                FirebaseAuth.getInstance().signInWithCredential(credential)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val user = auth.currentUser
                            val userId = user?.uid ?: ""
                            val userName = user?.displayName ?: ""
                            SessionManager(context).saveUserSession(userId, userName)
                            trySend(AuthResponse.Success)

                        } else {
                            trySend(AuthResponse.Error(task.exception?.message ?: "Unknown error"))
                        }
                    }
            }

            override fun onCancel() {
                trySend(AuthResponse.Error("Facebook login canceled"))
            }

            override fun onError(error: FacebookException) {
                trySend(AuthResponse.Error(error.message ?: "Unknown error"))
            }
        }

        loginManager.registerCallback(callbackManager, callback)
        loginManager.logInWithReadPermissions(context as Activity, listOf("email", "public_profile"))

        awaitClose {
            loginManager.unregisterCallback(callbackManager)
        }
    }

    // Login using Google
    fun loginWithGoogle(): Flow<AuthResponse> = callbackFlow {
        val googleIdOption = GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(false)
            .setServerClientId(context.getString(R.string.WebClientId))
            .setAutoSelectEnabled(false)
            .setNonce(creatingNonce())
            .build()

        val request = GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption)
            .build()

        try {
            val credentialManager = CredentialManager.create(context)
            val result = credentialManager.getCredential(request = request, context = context)
            val credential = result.credential
            if (credential is CustomCredential) {
                if (credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                    try {
                        val googleIdTokenCredential = GoogleIdTokenCredential
                            .createFrom(credential.data)
                        val firebaseCredential = GoogleAuthProvider
                            .getCredential(
                                googleIdTokenCredential.idToken,
                                null
                            )
                        auth.signInWithCredential(firebaseCredential)
                            .addOnCompleteListener {
                                if (it.isSuccessful) {
                                    val user = auth.currentUser
                                    val userId = user?.uid ?: ""
                                    val userName = user?.displayName ?: ""
                                    SessionManager(context).saveUserSession(userId = "user_id", userName = "user_name")

                                    trySend(AuthResponse.Success)
                                } else {
                                    trySend(
                                        AuthResponse.Error(
                                            message = it.exception?.message ?: ""
                                        )
                                    )
                                }
                            }
                    } catch (e: GoogleIdTokenParsingException) {
                        trySend(AuthResponse.Error(message = e.message ?: ""))
                    }
                    awaitClose()
                }
            }

        } catch (e: GoogleIdTokenParsingException) {
            trySend(AuthResponse.Error(message = e.message ?: ""))
        }

        awaitClose()
    }

    // creating Nonce (for Encryption)
    private fun creatingNonce(): String {
        val rawNonce = UUID.randomUUID().toString()
        val byteConvert = rawNonce.toByteArray()
        val msgDigest = MessageDigest.getInstance("SHA-256")
        val digest = msgDigest.digest(byteConvert)

        return digest.fold("") { str, it ->
            str + "%02x".format(it)
        }
    }
}

