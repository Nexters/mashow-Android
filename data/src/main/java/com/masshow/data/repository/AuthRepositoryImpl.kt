package com.masshow.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.masshow.data.Constants
import com.masshow.data.model.BaseState
import com.masshow.data.model.request.LoginRequest
import com.masshow.data.model.request.SignupRequest
import com.masshow.data.model.response.LoginResponse
import com.masshow.data.model.runRemote
import com.masshow.data.remote.AuthApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val api: AuthApi,
    private val dataStore: DataStore<Preferences>,
) : AuthRepository {

    companion object {
        private val ACCESS_TOKEN_KEY = stringPreferencesKey(Constants.ACCESS_TOKEN)
        private val REFRESH_TOKEN_KEY = stringPreferencesKey(Constants.REFRESH_TOKEN)
        private val USER_ID = longPreferencesKey(Constants.USER_ID)
        private val NICK = stringPreferencesKey(Constants.NICK)
    }

    override suspend fun login(body: LoginRequest): BaseState<LoginResponse?> = runRemote {
        api.login(body)
    }

    override suspend fun signup(body: SignupRequest): BaseState<LoginResponse?> = runRemote {
        api.signup(body)
    }

    override suspend fun getAccessToken(): String? {
        return dataStore.data.map { prefs ->
            prefs[ACCESS_TOKEN_KEY]
        }.first()
    }

    override suspend fun getRefreshToken(): String? {
        return dataStore.data.map { prefs ->
            prefs[REFRESH_TOKEN_KEY]
        }.first()
    }

    override suspend fun getUserId(): Long? {
        return dataStore.data.map { prefs ->
            prefs[USER_ID]
        }.first()
    }

    override suspend fun getNick(): String? {
        return dataStore.data.map { prefs ->
            prefs[NICK]
        }.first()
    }

    override suspend fun putAccessToken(token: String) {
        dataStore.edit { prefs ->
            prefs[ACCESS_TOKEN_KEY] = token
        }
    }

    override suspend fun putRefreshToken(token: String) {
        dataStore.edit { prefs ->
            prefs[REFRESH_TOKEN_KEY] = token
        }
    }

    override suspend fun putUserId(id: Long) {
        dataStore.edit { prefs ->
            prefs[USER_ID] = id
        }
    }

    override suspend fun putNick(nick: String) {
        dataStore.edit { prefs ->
            prefs[NICK] = nick
        }
    }

    override suspend fun deleteAccessToken() {
        dataStore.edit { prefs ->
            prefs.remove(ACCESS_TOKEN_KEY)
        }
    }

    override suspend fun deleteRefreshToken() {
        dataStore.edit { prefs ->
            prefs.remove(REFRESH_TOKEN_KEY)
        }
    }

    override suspend fun deleteUserId() {
        dataStore.edit { prefs ->
            prefs.remove(USER_ID)
        }
    }

    override suspend fun deleteNick() {
        dataStore.edit { prefs ->
            prefs.remove(NICK)
        }
    }
}