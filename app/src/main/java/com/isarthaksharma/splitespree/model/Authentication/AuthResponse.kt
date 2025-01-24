package com.isarthaksharma.splitespree.model.Authentication

sealed interface AuthResponse {
    data object Success: AuthResponse
    data class Error(val message:String): AuthResponse
}