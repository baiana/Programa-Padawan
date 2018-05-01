package padawana.recomendafilmes.Database

import android.app.Application

/**
 * Programado com amor por Ana Luísa Dias em 27/04/2018.
 */
class FilmsApplication():Application(){

    val database:AppDatabase? = AppDatabase.getInstance(applicationContext)


}