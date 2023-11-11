package com.wordpress.anujsaxenadev.database_manager.dao.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.wordpress.anujsaxenadev.database_manager.model.MapResource

@Dao
interface MapResourceDao{

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(mapResource: MapResource): Long

    @Query(
        value = """
            SELECT * FROM MAP_RESOURCES
            WHERE resource_id = :resourceId
        """)
    suspend fun getResourceByResourceId(resourceId: String): MapResource?
}