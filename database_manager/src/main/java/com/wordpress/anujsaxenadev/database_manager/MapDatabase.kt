package com.wordpress.anujsaxenadev.database_manager

import androidx.room.Database
import androidx.room.RoomDatabase
import com.wordpress.anujsaxenadev.database_manager.dao.daos.MapResourceDao
import com.wordpress.anujsaxenadev.database_manager.model.MapResource

@Database(entities = [MapResource::class], version = 1)
abstract class MapDatabase: RoomDatabase(){
    abstract fun mapResourcesDao(): MapResourceDao
}