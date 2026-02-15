package io.github.megasoheilsh.xray.helper

import io.github.megasoheilsh.xray.BuildConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

class UpdateHelper {
    companion object {
        private const val GITHUB_RELEASES_URL = "https://api.github.com/repos/megasoheilsh/Xray/releases"

        suspend fun checkForUpdates(): UpdateInfo? {
            return withContext(Dispatchers.IO) {
                try {
                    val url = URL(GITHUB_RELEASES_URL)
                    val connection = url.openConnection() as HttpURLConnection
                    connection.requestMethod = "GET"
                    connection.setRequestProperty("Accept", "application/vnd.github.v3+json")
                    connection.connectTimeout = 5000
                    connection.readTimeout = 5000
                    connection.connect()

                    if (connection.responseCode == HttpURLConnection.HTTP_OK) {
                        val response = connection.inputStream.bufferedReader().use { it.readText() }
                        val releases = JSONArray(response)

                        // Iterate through releases to find the latest stable release
                        for (i in 0 until releases.length()) {
                            val release = releases.getJSONObject(i)
                            
                            // Skip pre-releases
                            if (release.getBoolean("prerelease")) {
                                continue
                            }

                            val tagName = release.getString("tag_name")
                            val releaseUrl = release.getString("html_url")
                            val latestVersion = tagName.replace("v", "").trim()
                            val currentVersion = BuildConfig.VERSION_NAME

                            if (isNewer(latestVersion, currentVersion)) {
                                return@withContext UpdateInfo(latestVersion, releaseUrl)
                            }

                            // If we found a stable release but it's not newer, no need to check older releases
                            break
                        }
                    }
                    null
                } catch (e: Exception) {
                    null
                }
            }
        }

        private fun isNewer(latestVersion: String, currentVersion: String): Boolean {
            try {
                val latest = latestVersion.split(".").map { it.toInt() }
                val current = currentVersion.split(".").map { it.toInt() }

                for (i in 0 until minOf(latest.size, current.size)) {
                    if (latest[i] > current[i]) return true
                    if (latest[i] < current[i]) return false
                }

                return latest.size > current.size
            } catch (e: Exception) {
                return false
            }
        }
    }

    data class UpdateInfo(val version: String, val downloadUrl: String)
} 