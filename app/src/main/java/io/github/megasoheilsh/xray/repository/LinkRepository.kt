package io.github.megasoheilsh.xray.repository

import io.github.megasoheilsh.xray.database.Link
import io.github.megasoheilsh.xray.database.LinkDao

class LinkRepository(private val linkDao: LinkDao) {

    val all = linkDao.all()
    val tabs = linkDao.tabs()

    suspend fun activeLinks(): List<Link> {
        return linkDao.activeLinks()
    }

    suspend fun insert(link: Link) {
        linkDao.insert(link)
    }

    suspend fun update(link: Link) {
        linkDao.update(link)
    }

    suspend fun delete(link: Link) {
        linkDao.delete(link)
    }
}
