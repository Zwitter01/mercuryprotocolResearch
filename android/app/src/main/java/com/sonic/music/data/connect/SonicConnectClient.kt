package com.sonic.music.data.connect

import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import java.util.concurrent.TimeUnit

class SonicConnectClient {

    private val gson = Gson()
    private val jsonMediaType = "application/json; charset=utf-8".toMediaType()

    private val client: OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(4, TimeUnit.SECONDS)
        .readTimeout(4, TimeUnit.SECONDS)
        .writeTimeout(4, TimeUnit.SECONDS)
        .retryOnConnectionFailure(false)
        .build()

    private fun buildUrl(host: String, port: Int, path: String): String {
        val cleanHost = host.trim().removePrefix("http://").removePrefix("https://").removeSuffix("/")
        return "http://$cleanHost:$port$path"
    }

    suspend fun registerDevice(host: String, port: Int, request: RegisterDeviceRequest): Boolean =
        withContext(Dispatchers.IO) {
            try {
                val json = gson.toJson(request)
                val body = json.toRequestBody(jsonMediaType)
                val url = buildUrl(host, port, "/api/connect/register")
                val req = Request.Builder().url(url).post(body).build()
                client.newCall(req).execute().use { response ->
                    response.isSuccessful
                }
            } catch (e: Exception) {
                false
            }
        }

    suspend fun pollCommands(host: String, port: Int, deviceId: String): ConnectCommandDto? =
        withContext(Dispatchers.IO) {
            try {
                val url = buildUrl(host, port, "/api/connect/poll?deviceId=$deviceId")
                val req = Request.Builder().url(url).get().build()
                client.newCall(req).execute().use { response ->
                    if (!response.isSuccessful) return@withContext null
                    val bodyString = response.body?.string() ?: return@withContext null
                    val pollResp = gson.fromJson(bodyString, PollResponse::class.java)
                    if (pollResp != null && pollResp.success) pollResp.command else null
                }
            } catch (e: Exception) {
                null
            }
        }

    suspend fun getDevices(host: String, port: Int): DevicesResponse? =
        withContext(Dispatchers.IO) {
            try {
                val url = buildUrl(host, port, "/api/connect/devices")
                val req = Request.Builder().url(url).get().build()
                client.newCall(req).execute().use { response ->
                    if (!response.isSuccessful) return@withContext null
                    val bodyString = response.body?.string() ?: return@withContext null
                    gson.fromJson(bodyString, DevicesResponse::class.java)
                }
            } catch (e: Exception) {
                null
            }
        }

    suspend fun sendCommand(host: String, port: Int, request: SendCommandRequest): Boolean =
        withContext(Dispatchers.IO) {
            try {
                val json = gson.toJson(request)
                val body = json.toRequestBody(jsonMediaType)
                val url = buildUrl(host, port, "/api/connect/command")
                val req = Request.Builder().url(url).post(body).build()
                client.newCall(req).execute().use { response ->
                    response.isSuccessful
                }
            } catch (e: Exception) {
                false
            }
        }
}
