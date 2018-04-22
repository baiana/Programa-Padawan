package padawana.recomendafilmes.Database

import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.Database
import padawana.recomendafilmes.Filme


/**
 * Programado com amor por Ana Luísa Dias em 21/04/2018.
 */
@Database(entities = arrayOf(Filme::class), version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun FilmesDao():FilmeDAO


}