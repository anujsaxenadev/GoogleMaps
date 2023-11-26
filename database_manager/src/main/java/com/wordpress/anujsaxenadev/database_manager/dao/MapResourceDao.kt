package com.wordpress.anujsaxenadev.database_manager.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.wordpress.anujsaxenadev.database_manager.model.MapResource

@Dao
internal interface MapResourceDao{
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(mapResource: MapResource)

    @Query(
        value = """
            SELECT * FROM MAP_RESOURCES
            WHERE resource_id = :resourceId
        """)
    suspend fun getResourceByResourceId(resourceId: String): MapResource?

    @Query(
        value = """
            SELECT * FROM MAP_RESOURCES
            WHERE created_at < :timestamp OR created_at == 0
        """)
    suspend fun getResourcesBefore(timestamp: Long): List<MapResource>

    @Query(
        value = """
            DELETE FROM MAP_RESOURCES
            WHERE created_at < :timestamp OR created_at == 0
        """)
    suspend fun removeResourcesBefore(timestamp: Long)
}