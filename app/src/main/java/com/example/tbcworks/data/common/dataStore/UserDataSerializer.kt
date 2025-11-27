package com.example.tbcworks.data.common.dataStore

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.example.tbcworks.data.common.dataStore.UserData
import java.io.InputStream
import java.io.OutputStream

object UserDataSerializer : Serializer<UserData> {
    override val defaultValue: UserData = UserData.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): UserData {
        try {
            return UserData.parseFrom(input)
        } catch (exception: Exception) {
            throw CorruptionException("Cannot read proto.", exception)
        }
    }

    override suspend fun writeTo(t: UserData, output: OutputStream) = t.writeTo(output)
}
