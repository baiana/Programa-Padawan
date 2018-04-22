package padawana.recomendafilmes.Database

import android.arch.persistence.room.*
import padawana.recomendafilmes.Filme

@Dao
interface FilmeDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFilmes(vararg filmes: Filme)

    @Update
    fun updateFilme(filmes: Filme)

    @Delete
    fun deleteFilmes(filmes: Filme)

    @Query("SELECT * FROM Filmes")
    fun loadAllUsers(): Array<Filme>

    @Query("SELECT * FROM Filmes WHERE titulo = tituloPesquisa")
    fun loadPerTitle(tituloPesquisa:String):ArrayList<Filme>
}
