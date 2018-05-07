package padawana.recomendafilmes.Database

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import padawana.recomendafilmes.retrofit.Filme



/**
 * Programado com amor por Ana Luísa Dias em 21/04/2018.
 */
@Database(entities = [Filme::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun filmesDao(): FilmeDAO

    companion object {
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase? {
            if (INSTANCE == null) {
                synchronized(AppDatabase::class) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                            AppDatabase::class.java, "weather.db")
                            .allowMainThreadQueries()
                            .build()
                }
            }
            return INSTANCE
        }
    }
}
