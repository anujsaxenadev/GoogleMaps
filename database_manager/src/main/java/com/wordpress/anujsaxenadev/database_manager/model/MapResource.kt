package com.wordpress.anujsaxenadev.database_manager.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "map_resources",
    indices = [Index(value = ["resource_id"], unique = true)]
)
internal data class MapResource(
    @ColumnInfo(name = "resource_id")
    val resourceId: String,
    @ColumnInfo(name = "created_at")
    var createdAt: Long
){
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "index")
    var resourceIndex: Int = 0

    fun getResourceName(): String{
        return resourceIndex.toString()
    }
}