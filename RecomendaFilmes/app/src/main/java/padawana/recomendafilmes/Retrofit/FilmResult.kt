package padawana.recomendafilmespackage

/**
 * Programado com amor por Ana Luísa Dias em 14/03/2018.
 */
import android.arch.persistence.room.Embedded
import padawana.recomendafilmes.Filme

data class FilmResult(
        val page: Int,
        val total_results: Int,
        val total_pages: Int,
        val results: List<Filme>)
