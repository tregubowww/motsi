package com.example.motsi.core.network.data

interface SessionManager {
    fun accessToken(): String?
    fun refreshToken(): String?
    fun saveAccessToken(token: String)
    fun saveRefreshToken(token: String)
    fun clear()
}