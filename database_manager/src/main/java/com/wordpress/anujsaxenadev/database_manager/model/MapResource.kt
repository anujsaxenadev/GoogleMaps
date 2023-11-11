package com.wordpress.anujsaxenadev.database_manager.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "map_resources",
    indices = [Index(value = ["resource_id"], unique = true)]
)
data class MapResource(
    @ColumnInfo(name = "resource_id")
    val resourceId: String
){
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "index")
    var resourceIndex: Int = 0
}