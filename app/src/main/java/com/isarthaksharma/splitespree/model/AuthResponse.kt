package com.isarthaksharma.splitespree.model

sealed interface AuthResponse {
    data object Success: AuthResponse
    data class Error(val message:String): AuthResponse
}