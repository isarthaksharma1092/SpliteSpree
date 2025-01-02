package com.isarthaksharma.splitespree.model

import android.content.Context
import android.content.SharedPreferences

class SessionManager(context: Context){
    private val sharedPreferences:SharedPreferences= context.getSharedPreferences("UserSession",Context.MODE_PRIVATE)

    companion object {
        const val KEY_IS_LOGGED_IN = "is_logged_in"
        const val KEY_USER_ID = "user_id"
        const val KEY_USER_NAME = "user_name"
    }

    fun saveUserSession(userId: String, userName: String) {
        val editor = sharedPreferences.edit()
        editor.putBoolean(KEY_IS_LOGGED_IN, true)
        editor.putString(KEY_USER_ID, userId)
        editor.putString(KEY_USER_NAME, userName)
        editor.apply()
    }

    fun isLoggedIn(): Boolean {
        return sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false)
    }

    fun getUserDetails(): Map<String, String?> {
        return mapOf(
            KEY_USER_ID to sharedPreferences.getString(KEY_USER_ID, null),
            KEY_USER_NAME to sharedPreferences.getString(KEY_USER_NAME, null)
        )
    }

    fun logout() {
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }
}