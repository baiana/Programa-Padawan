package padawana.recomendafilmes.Database

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import padawana.recomendafilmes.Filme


/**
 * Programado com amor por Ana Luísa Dias em 21/04/2018.
 */
@Database(entities = [(Filme::class)], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun FilmesDao(): FilmeDAO

    companion object {
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase? {
            if (INSTANCE == null) {
                synchronized(AppDatabase::class) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase::class.java, "weather.db")
                            .build()

                }
            }
            return INSTANCE
        }
    }
}
