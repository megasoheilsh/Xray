package io.github.megasoheilsh.xray

import android.app.Application
import io.github.megasoheilsh.xray.database.XrayDatabase
import io.github.megasoheilsh.xray.repository.LinkRepository
import io.github.megasoheilsh.xray.repository.ProfileRepository

class Xray : Application() {

    private val xrayDatabase by lazy { XrayDatabase.ref(this) }
    val linkRepository by lazy { LinkRepository(xrayDatabase.linkDao()) }
    val profileRepository by lazy { ProfileRepository(xrayDatabase.profileDao()) }
}
