package com.wordpress.anujsaxenadev.database_manager.databases

import androidx.room.Database
import androidx.room.RoomDatabase
import com.wordpress.anujsaxenadev.database_manager.dao.MapResourceDao
import com.wordpress.anujsaxenadev.database_manager.model.MapResource

@Database(entities = [MapResource::class], version = 1)
internal abstract class MapDatabase: RoomDatabase(){
    internal abstract fun mapResourcesDao(): MapResourceDao
}