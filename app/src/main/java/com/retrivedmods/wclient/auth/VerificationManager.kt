package com.retrivedmods.wclient.auth

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.browser.customtabs.CustomTabsIntent
import kotlinx.coroutines.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.net.URLEncoder
import java.util.*

object VerificationManager {
    private const val TAG = "VerifyNet"
    private const val PREFS = "wclient_prefs"
    private const val KEY_WCLIENT_ID = "wclient_id"

    private const val BASE_VERIFY_URL = "https://retrivedmods.online/task/verify.php"

    private val client: OkHttpClient = OkHttpClient.Builder()
        .followRedirects(true)
        .followSslRedirects(true)
        .addInterceptor { chain ->
            val req = chain.request()
            Log.d(TAG, "REQ: ${req.method} ${req.url}")
            val resp = chain.proceed(req)
            val loc = resp.header("Location")
            Log.d(TAG, "RESP: ${resp.code} ${resp.message} -> ${resp.request.url}")
            if (loc != null) Log.d(TAG, "Redirect Location: $loc")
            resp
        }
        .build()

    private val jsonMedia = "application/json; charset=utf-8".toMediaType()
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    private fun prefs(ctx: Context) = ctx.getSharedPreferences(PREFS, Context.MODE_PRIVATE)

    fun getWClientId(ctx: Context): String {
        val p = prefs(ctx)
        var id = p.getString(KEY_WCLIENT_ID, null)
        if (id.isNullOrBlank()) {
            id = "WC-" + UUID.randomUUID().toString().uppercase().replace("-", "").substring(0, 16)
            p.edit().putString(KEY_WCLIENT_ID, id).apply()
        }
        return id
    }

    // VERIFIKASI DIHILANGKAN: Selalu return true (tidak ada pengecekan whitelist)
    suspend fun isWhitelisted(ctx: Context, wclientId: String): Boolean =
        withContext(Dispatchers.IO) {
            return@withContext true
        }

    // VERIFIKASI DIHILANGKAN: Selalu return true (tidak ada pengecekan verifikasi)
    suspend fun isVerified(ctx: Context, wclientId: String): Boolean =
        withContext(Dispatchers.IO) {
            return@withContext true
        }

    // VERIFIKASI DIHILANGKAN: Tidak diperlukan lagi
    suspend fun requestVerification(ctx: Context, wclientId: String): String =
        withContext(Dispatchers.IO) {
            return@withContext ""
        }

    // VERIFIKASI DIHILANGKAN: Tidak diperlukan lagi
    fun openInAppBrowser(activity: Activity, verifyUrl: String) {
        Log.d(TAG, "openInAppBrowser: Verification disabled - skipping browser")
    }

    // VERIFIKASI DIHILANGKAN: Tidak diperlukan lagi
    fun openInExternalBrowser(activity: Activity, url: String) {
        Log.d(TAG, "openInExternalBrowser: Verification disabled - skipping browser")
    }

    // VERIFIKASI DIHILANGKAN: Tidak diperlukan lagi
    fun pollVerificationStatus(ctx: Context, wclientId: String, onComplete: (Boolean, String?) -> Unit) {
        Log.d(TAG, "pollVerificationStatus: Verification disabled - skipping poll")
    }

    fun cancelAll() {
        scope.cancel()
    }
}
