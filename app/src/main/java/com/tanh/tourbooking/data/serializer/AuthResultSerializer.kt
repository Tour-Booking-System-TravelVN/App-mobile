package com.tanh.tourbooking.data.serializer

import androidx.datastore.core.Serializer
import com.tanh.tourbooking.data.model.dto.auth.AuthResult
import com.tanh.tourbooking.util.Crypto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import java.io.InputStream
import java.io.OutputStream
import java.util.Base64

object AuthResultSerializer : Serializer<AuthResult> {
    override val defaultValue: AuthResult
        get() = AuthResult()

    //read -> base64 -> decrypted -> bytearray -> json -> obj
    override suspend fun readFrom(input: InputStream): AuthResult {
        val encryptedBytes = withContext(Dispatchers.IO) {
            input.use {
                it.readBytes()
            }
        }
        val decryptedBase64 = Base64.getDecoder().decode(encryptedBytes)
        val decryptedBytes = Crypto.decrypted(decryptedBase64)
        val decodedJsonString = decryptedBytes.decodeToString()
        return Json.decodeFromString(decodedJsonString)
    }

    //string json -> bytearray -> encrypted -> base64 -> write
    override suspend fun writeTo(t: AuthResult, output: OutputStream) {
        val json = Json.encodeToString(t)
        val byteArray = json.toByteArray()
        val encryptedByte = Crypto.encrypt(byteArray)
        val encryptedBase64 = Base64.getEncoder().encode(encryptedByte)
        return withContext(Dispatchers.IO) {
            output.use {    //use to close flow
                it.write(encryptedBase64)
            }
        }
    }
}