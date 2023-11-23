package hu.bme.aut.android.dailydiary.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [Entry::class],
    version = 2
)
@TypeConverters(value = [Entry.Mood::class])
abstract class EntryDatabase : RoomDatabase() {
    abstract fun entryDao(): EntryDao

    companion object {
        fun getDatabase(applicationContext: Context): EntryDatabase {
            return Room.databaseBuilder(
                applicationContext,
                EntryDatabase::class.java,
                "entry"
            ).build()
        }
    }
}