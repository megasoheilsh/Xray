package io.github.megasoheilsh.xray.dto

import io.github.megasoheilsh.xray.database.Profile

class ProfileList(
    var id: Long,
    var index: Int,
    var name: String,
) {
    companion object {
        fun fromProfile(value: Profile) = ProfileList(value.id, value.index, value.name)
    }
}
