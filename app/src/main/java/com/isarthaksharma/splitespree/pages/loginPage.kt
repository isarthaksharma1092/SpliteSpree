package com.isarthaksharma.splitespree.pages

import android.content.Context
import android.content.Intent
import android.util.Patterns
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.facebook.CallbackManager
import com.isarthaksharma.splitespree.R
import com.isarthaksharma.splitespree.model.Authentication.AuthResponse
import com.isarthaksharma.splitespree.model.Authentication.AuthenticationManager
import com.isarthaksharma.splitespree.ui_components.HorizontalLine
import com.isarthaksharma.splitespree.ui_components.LoginOptionButton
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.util.regex.Pattern

@Composable
fun LoginPage(goToSignUpPage: () -> Unit) {
    // FOR INPUT
    var emailAddressState by remember { mutableStateOf("") }
    var passwordState by remember { mutableStateOf("") }

    // FOR ERROR HANDLING
    var emailAddressError by remember { mutableStateOf(false) }
    var passwordError by remember { mutableStateOf(false) }
    val showPassword = remember { mutableStateOf(false) }

    val context = LocalContext.current
    val authenticationManager = remember {
        AuthenticationManager(context)
    }
    val coroutineScope = rememberCoroutineScope()
    val callbackManager = remember { CallbackManager.Factory.create() }

// *********************************************************************************************************************************************************************
// UI
// *********************************************************************************************************************************************************************

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 30.dp, bottom = 60.dp, start = 10.dp, end = 10.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(modifier = Modifier.align(Alignment.Start)) {
            Column {
                Text(
                    text = "Log in to",
                    fontFamily = FontFamily(Font(R.font.doto, FontWeight.Bold)),
                    modifier = Modifier.padding(start = 10.dp, end = 40.dp, top = 40.dp),
                    color = MaterialTheme.colorScheme.onBackground,
                    fontSize = 28.sp
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = "SpliteSpree",
                    fontFamily = FontFamily(Font(R.font.doto, FontWeight.Bold)),
                    modifier = Modifier.padding(start = 10.dp, end = 40.dp),
                    color = MaterialTheme.colorScheme.onBackground,
                    fontSize = 28.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

// *********************************************************************************************************************************************************************
// INPUT SECTION
        Box(modifier = Modifier.padding(10.dp)) {
            Column {

                // EMAIL TEXT FIELD
                OutlinedTextField(value = emailAddressState,
                    label = { Text("Email Address") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    modifier = Modifier.fillMaxWidth(),
                    onValueChange = {
                        emailAddressState = it
                        emailAddressError =
                            !Patterns.EMAIL_ADDRESS.matcher(emailAddressState).matches()
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Email, contentDescription = "Email"
                        )
                    },
                    supportingText = {
                        if (emailAddressError) {
                            Text(
                                text = "Wrong Format \nExample: Demo@test.com",
                                color = Color(0xffF6D6D6)
                            )
                        }
                    })
                Spacer(modifier = Modifier.height(10.dp))

                // PASSWORD TEXT FIELD
                OutlinedTextField(value = passwordState,
                    label = { Text("Password") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    visualTransformation = if (showPassword.value) VisualTransformation.None else PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth(),
                    onValueChange = {
                        passwordState = it
                        passwordError =
                            !Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$")
                                .matcher(passwordState).matches()
                    },
                    trailingIcon = {
                        val (icon, iconColor) = if (showPassword.value) {
                            Pair(
                                R.drawable.visibility_icon,
                                MaterialTheme.colorScheme.onSecondaryContainer
                            )
                        } else {
                            Pair(
                                R.drawable.visibility_off_icon,
                                MaterialTheme.colorScheme.onSecondaryContainer
                            )
                        }

                        IconButton(onClick = { showPassword.value = !showPassword.value }) {
                            Image(
                                painter = painterResource(id = icon),
                                contentDescription = "Visibility",
                                colorFilter = androidx.compose.ui.graphics.ColorFilter.tint(
                                    iconColor
                                )
                            )
                        }
                    },
                    supportingText = {
                        if (passwordError) {
                            Text(
                                text = "• Must contain a Capital Letter (A-Z)\n" + "• Must contain a Small Letter (a-z)\n" + "• Must contain a Number (0-9)\n" + "• Must contain a Special character (@#$%^&+=)\n" + "• Must be greater than 8 characters",
                                color = Color(0xffF6D6D6)
                            )
                        }
                    })

                // SUBMIT BUTTON
                Button(
                    onClick = {
                        if (isEmailValid(emailAddressState, context) && isPasswordValid(
                                passwordState, context
                            )
                        ) {
                            authenticationManager.loginWithEmail(emailAddressState, passwordState)
                                .onEach {
                                    if (it is AuthResponse.Success) {

                                        Toast.makeText(context, "Welcome :)", Toast.LENGTH_SHORT).show()
                                        val intent = Intent(context, MainUiUserSection::class.java)
                                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                        context.startActivity(intent)
                                    }
                                }.launchIn(coroutineScope)
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 30.dp)

                ) {
                    Text(text = "Log In")
                }
            }
        }

        Box {
            HorizontalLine()
        }

// *********************************************************************************************************************************************************************
// Login Other Methods
        Box {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .clip(RoundedCornerShape(15.dp))
                    .align(Alignment.Center)
            ) {
                // Google Login Button
                LoginOptionButton(
                    icon = R.drawable.google_icon,
                    text = "Continue with Google",
                    onClick = {
                        authenticationManager.loginWithGoogle().onEach {
                            if (it is AuthResponse.Success) {
                                Toast.makeText(context, "Welcome Back", Toast.LENGTH_SHORT).show()
                                val intent = Intent(context, MainUiUserSection::class.java)
                                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                context.startActivity(intent)
                            } else {
                                if (it is AuthResponse.Error) {
                                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                                }
                            }
                        }.launchIn(coroutineScope)
                    },
                )
                Spacer(modifier = Modifier.height(10.dp))

                // Facebook Login Button
                LoginOptionButton(
                    icon = R.drawable.facebook_icon,
                    text = "Continue with Facebook",
                    onClick = {
                        coroutineScope.launch {
                            authenticationManager.loginWithFacebook(callbackManager)
                                .onEach {
                                    when (it) {
                                        is AuthResponse.Success -> {

                                            Toast.makeText(context, "Welcome :)", Toast.LENGTH_SHORT).show()
                                            val intent = Intent(context, MainUiUserSection::class.java)
                                            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                            context.startActivity(intent)


                                        }

                                        is AuthResponse.Error -> {
                                            Toast.makeText(context, it.message, Toast.LENGTH_SHORT)
                                                .show()
                                        }
                                    }
                                }
                                .launchIn(coroutineScope)
                        }
                    },
                )
                Spacer(modifier = Modifier.height(10.dp)) // Add spacing between buttons

                //Sign Up With Email
                LoginOptionButton(
                    icon = R.drawable.signup_icon,
                    text = "Continue with Email",
                    onClick = {
                        goToSignUpPage()
                    },
                )
            }
        }

    }
}

private fun isEmailValid(email: String, context: Context): Boolean {
    if (email.isBlank()) {
        Toast.makeText(context, "Email can't be empty", Toast.LENGTH_SHORT).show()
        return false
    } else if (Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
        return true
    } else {
        return false
    }
}

private fun isPasswordValid(password: String, context: Context): Boolean {
    if (password.isBlank()) {
        Toast.makeText(context, "Password can't be empty", Toast.LENGTH_SHORT).show()
        return false
    }
    val passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$"
    val pattern = Pattern.compile(passwordPattern)
    val matcher = pattern.matcher(password)

    if (matcher.matches()) {
        return true
    } else {
        Toast.makeText(context, "Password is not valid", Toast.LENGTH_SHORT).show()
        return false
    }
}