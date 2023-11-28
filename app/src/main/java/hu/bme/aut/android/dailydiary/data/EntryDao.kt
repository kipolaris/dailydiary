package hu.bme.aut.android.dailydiary.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface EntryDao {
    @Query("SELECT * FROM entry")
    fun getAll(): List<Entry>

    @Insert
    fun insert(entry: Entry): Long

    @Update
    fun update(entry: Entry)

    @Delete
    fun deleteEntry(entry: Entry)
}