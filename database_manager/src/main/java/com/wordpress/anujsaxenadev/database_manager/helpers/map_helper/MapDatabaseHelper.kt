package com.wordpress.anujsaxenadev.database_manager.helpers.map_helper

interface MapDatabaseHelper{
    /**
     * Check if Resource Entry is there in Database
     * @param resourceId Id of the resource to check
     *
     * @return [Result] of `true` -  if resource exists, `false` -  if resource does not exist, [Throwable] - if there is some error happened during check.
     */
    suspend fun checkResourceExist(resourceId: String): Result<Boolean>

    /**
     * Get the Resource Name of the given `resourceId`
     *
     * @param resourceId Id of the resource
     *
     * @return [Result] of Name of the resource saved. [Throwable] - if there is some error happened during get call.
     */
    suspend fun getResourceName(resourceId: String): Result<String>

    /**
     * Save the Resource with given resource id and generate the resource name
     *
     * @param resourceId Id of the resource
     *
     * @return [Result] of name of the resource saved. [Throwable] - if there is some error happened during get call.
     */
    suspend fun storeResourceAndGetFileName(resourceId: String): Result<String>

    /**
     * Removing the Resource with `created_at` < given `timestamp`
     *
     * @param timestamp given timestamp.
     *
     * @return [Result] of Unit if success [Throwable] - if there is some error happened during removing items.
     */
    suspend fun removeResourceBefore(timestamp: Long): Result<Unit>

    /**
     * Get all the resource before `timestamp`
     *
     * @param timestamp given timestamp.
     *
     * @return [Result] of Unit if success [Throwable] - if there is some error happened during getting list.
     */
    suspend fun getResourcesBefore(timestamp: Long): Result<List<String>>
}