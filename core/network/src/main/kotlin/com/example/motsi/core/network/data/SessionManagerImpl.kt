package com.example.motsi.core.network.data

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import javax.inject.Inject
import androidx.datastore.preferences.preferencesDataStore
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking


internal class SessionManagerImpl @Inject constructor(
     private val application: Application
) : SessionManager {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "session")

    private val ACCESS_TOKEN = stringPreferencesKey("access_token")
    private val REFRESH_TOKEN = stringPreferencesKey("refresh_token")

    private val store get() = application.dataStore

    override fun accessToken(): String? = runBlocking {
        store.data.first()[ACCESS_TOKEN]
    }

    override fun refreshToken(): String? = runBlocking {
        store.data.first()[REFRESH_TOKEN]
    }

    override fun saveAccessToken(token: String) {
        runBlocking {
            store.edit { it[ACCESS_TOKEN] = token }
        }
    }

    override fun saveRefreshToken(token: String) {
        runBlocking {
            store.edit { it[REFRESH_TOKEN] = token }
        }
    }

    override fun clear() {
        runBlocking {
            store.edit { it.clear() }
        }
    }
}

//  [-5, 2, 3, 4, 6] leftIndex = 0 rightIndex = 4 positionIndex = 4
// [0, 0, 0, 0, 6 leftIndex = 0 rightIndex = 3 positionIndex = 3
// [0, 0, 0, 5 , 6]  leftIndex = 1 rightIndex = 3 positionIndex = 2
// [0, 0, 0, 0, 0]  leftIndex = 0 rightIndex = 1 positionIndex = 1
// [0, 0, 0, 0, 0]  leftIndex = 1 rightIndex = 2 positionIndex = 1
//
//
private fun sqr(list: List<Int>): List<Int>{
    val resultList = MutableList<Int>(size = list.size){
        0
    }
    var leftIndex = 0
    var rightIndex = resultList.lastIndex
    var positionIndex = resultList.lastIndex

    while (leftIndex <=  rightIndex){
        val leftValue = list[leftIndex]
        val rightValue = list[rightIndex]
        if (kotlin.math.abs(leftValue)  > kotlin.math.abs(rightValue)){
            resultList[positionIndex] = leftValue * leftValue
            leftIndex++
        } else {
            resultList[positionIndex] = rightValue * rightValue
            rightIndex--
        }
        positionIndex--
    }
    return resultList
}