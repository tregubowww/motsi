package com.example.motsi.core.network.data

import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Route
import javax.inject.Inject


class TokenAuthenticator @Inject constructor(
    private val sessionManager: SessionManager,
    private val authApiSync: AuthApiSync
) : Authenticator {

    override fun authenticate(route: Route?, response: okhttp3.Response): Request? {
        val refreshToken = sessionManager.refreshToken() ?: return null

        val tokenResponse = try {
            val call = authApiSync.refresh(RefreshTokenRequest(refreshToken))
            val response = call.execute()
            if (response.isSuccessful) response.body() else null
        } catch (e: Exception) {
            null
        }

        tokenResponse ?: return null

        sessionManager.saveAccessToken(tokenResponse.accessToken)
        sessionManager.saveRefreshToken(tokenResponse.refreshToken)

        return response.request.newBuilder()
            .header("Authorization", "Bearer ${tokenResponse.accessToken}")
            .build()
    }
}



data class RefreshTokenRequest(val refreshToken: String)
data class RefreshTokenResponse(val accessToken: String, val refreshToken: String)