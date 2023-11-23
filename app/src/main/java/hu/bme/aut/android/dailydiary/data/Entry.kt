package hu.bme.aut.android.dailydiary.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Entity(tableName = "entry")
data class Entry(
    @ColumnInfo(name = "id") @PrimaryKey(autoGenerate = true) var id: Long? = null,
    @ColumnInfo(name = "title") var title: String,
    @ColumnInfo(name = "content") var content: String,
    @ColumnInfo(name = "mood") var mood: Mood?,
    @ColumnInfo(name = "date") var date: String = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd")).toString()
) {
    enum class Mood {
        Horrible, Bad, Neutral, Good, Amazing;
        companion object {
            @JvmStatic
            @TypeConverter
            fun getByOrdinal(ordinal: Int): Mood? {
                var ret: Mood? = null
                for (cat in values()) {
                    if (cat.ordinal == ordinal) {
                        ret = cat
                        break
                    }
                }
                return ret
            }

            @JvmStatic
            @TypeConverter
            fun toInt(mood: Mood): Int {
                return mood.ordinal
            }
        }
    }
}