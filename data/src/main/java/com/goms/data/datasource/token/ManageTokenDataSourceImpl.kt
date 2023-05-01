package com.goms.data.datasource.token

import android.content.Context
import android.content.Context.MODE_PRIVATE
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class ManageTokenDataSourceImpl @Inject constructor(
    @ApplicationContext private val context: Context
): ManageTokenDataSource {
    companion object {
        const val NAME = "token"
        const val ACCESS_TOKEN = "accessToken"
        const val REFRESH_TOKEN = "refreshToken"
        const val ACCESS_TOKEN_EXP = "accessTokenExp"
        const val REFRESH_TOKEN_EXP = "refreshTokenExp"
    }

    override fun getAccessToken(): String =
        getSharedPreferences().getString(ACCESS_TOKEN, "") ?: ""

    override fun getRefreshToken(): String =
        getSharedPreferences().getString(REFRESH_TOKEN, "") ?: ""

    override fun getAccessTokenExp(): String =
        getSharedPreferences().getString(ACCESS_TOKEN_EXP, "") ?: ""
    override fun getRefreshTokenExp(): String =
        getSharedPreferences().getString(REFRESH_TOKEN_EXP, "") ?: ""


    override fun setToken(
        accessToken: String,
        refreshToken: String,
        accessTokenExp: String,
        refreshTokenExp: String
    ) {
        setData(ACCESS_TOKEN, accessToken)
        setData(REFRESH_TOKEN, refreshToken)
        setData(ACCESS_TOKEN_EXP, accessTokenExp)
        setData(REFRESH_TOKEN_EXP, refreshTokenExp)
    }

    private fun setData(id: String, data: String?) =
        getSharedPreferences().edit().let {
            it.putString(id, data)
            it.apply()
        }

    private fun getSharedPreferences() = context.getSharedPreferences(NAME, MODE_PRIVATE)
}