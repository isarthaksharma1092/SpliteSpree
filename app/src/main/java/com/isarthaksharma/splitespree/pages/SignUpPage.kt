package com.isarthaksharma.splitespree.pages

import android.content.Context
import android.content.Intent
import android.util.Patterns
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.isarthaksharma.splitespree.ui_components.HorizontalLine
import com.isarthaksharma.splitespree.ui_components.LoginOptionButton
import com.isarthaksharma.splitespree.R
import com.isarthaksharma.splitespree.model.Authentication.AuthResponse
import com.isarthaksharma.splitespree.model.Authentication.AuthenticationManager
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.util.regex.Pattern


@Composable
fun SignUpPage(
    goToLoginPage: () -> Unit,
    onGoBack: () -> Unit
) {
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

// *********************************************************************************************************************************************************************
// UI
// *********************************************************************************************************************************************************************

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 30.dp, horizontal = 10.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // Back Button
        Box(
            modifier = Modifier
                .padding(top = 10.dp)
                .align(Alignment.Start)
                .clip(RoundedCornerShape(50))
                .background(MaterialTheme.colorScheme.onSecondaryContainer)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                tint = MaterialTheme.colorScheme.background,
                contentDescription = "Back to Login Button",
                modifier = Modifier
                    .padding(10.dp)
                    .clickable {
                        onGoBack()
                    }
            )
        }
// *********************************************************************************************************************************************************************
// SignUp Text
        Box(modifier = Modifier.align(Alignment.Start)) {
            Column {
                Text(
                    text = "Get Your Free Account",
                    fontFamily = FontFamily(Font(R.font.doto, FontWeight.Bold)),
                    color = MaterialTheme.colorScheme.onBackground,
                    fontSize = 28.sp
                )
            }
        }

        // Login Using Google & Facebook
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
                        authenticationManager.loginWithGoogle()
                            .onEach {
                                if (it is AuthResponse.Success) {
                                    Toast.makeText(context, "Welcome :)", Toast.LENGTH_SHORT).show()
                                    val intent = Intent(context, MainUiUserSection::class.java)
                                    intent.flags =
                                        Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                    context.startActivity(intent)
                                } else {
                                    if (it is AuthResponse.Error) {
                                        Toast.makeText(context, it.message, Toast.LENGTH_SHORT)
                                            .show()
                                    }
                                }
                            }
                            .launchIn(coroutineScope)
                    },
                )
                Spacer(modifier = Modifier.height(10.dp))

                // Facebook Login Button
                LoginOptionButton(
                    icon = R.drawable.facebook_icon,
                    text = "Continue with Facebook",
                    onClick = {
                        /* *** Handle Facebook login  Missing Fix it *** */

                        Toast.makeText(context, "Welcome Back", Toast.LENGTH_SHORT).show()
                        val intent = Intent(context, MainUiUserSection::class.java)
                        intent.flags =
                            Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        context.startActivity(intent)
                    },
                )
                Spacer(modifier = Modifier.height(10.dp))
            }
        }

        Box {
            HorizontalLine()
        }
// *********************************************************************************************************************************************************************
// INPUT TEXT FIELD
        Box(modifier = Modifier.padding(10.dp)) {
            Column {

                // EMAIL TEXT FIELD
                OutlinedTextField(
                    value = emailAddressState,
                    label = { Text("Email Address") },
                    placeholder = { Text(text = "Enter your email address") },
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
                            imageVector = Icons.Default.Email,
                            contentDescription = "Email"
                        )
                    },
                    supportingText = {
                        if (emailAddressError) {
                            Text(
                                text = "Wrong Format \nExample: Demo@test.com",
                                color = Color(0xffF6D6D6)
                            )
                        }
                    }
                )
                Spacer(modifier = Modifier.height(10.dp))


                // PASSWORD TEXT FIELD
                OutlinedTextField(
                    value = passwordState,
                    label = { Text("Password") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    visualTransformation = if (showPassword.value) VisualTransformation.None else PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth(),
                    onValueChange = {
                        passwordState = it
                        passwordError = !Pattern
                            .compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$")
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
                                text = "• Must contain a Capital Letter (A-Z)\n" +
                                        "• Must contain a Small Letter (a-z)\n" +
                                        "• Must contain a Number (0-9)\n" +
                                        "• Must contain a Special character (@#$%^&+=)\n" +
                                        "• Must be greater than 8 characters",
                                color = Color(0xffF6D6D6)
                            )
                        }
                    }
                )

                // SUBMIT BUTTON
                Button(
                    onClick = {
                        if (isEmailValid(emailAddressState, context) && isPasswordValid(
                                passwordState,
                                context
                            )
                        ) {
                            authenticationManager.createAccountWithEmail(
                                emailAddressState,
                                passwordState
                            )
                                .onEach {
                                    if (it is AuthResponse.Success) {
                                        Toast.makeText(context, "Welcome :)", Toast.LENGTH_SHORT)
                                            .show()
                                        val intent = Intent(context, MainUiUserSection::class.java)
                                        intent.flags =
                                            Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                        context.startActivity(intent)
                                    }
                                }
                                .launchIn(coroutineScope)
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 30.dp)

                ) {
                    Text(text = "Sign Up")
                }
            }
        }

        Box {
            Text(
                text = "Already Have an Account ?",
                fontFamily = FontFamily(Font(R.font.doto, FontWeight.Thin)),
                textDecoration = TextDecoration.Underline,
                modifier = Modifier.clickable {
                    goToLoginPage()
                }
            )
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