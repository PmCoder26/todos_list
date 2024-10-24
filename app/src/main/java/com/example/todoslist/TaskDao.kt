package com.example.todoslist

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow


@Dao
interface TaskDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun addTask(task: Task)

    @Delete
    suspend fun removeTask(task: Task)

    @Query("select * from tasks")
    fun getAllTasks(): Flow<List<Task>>

}