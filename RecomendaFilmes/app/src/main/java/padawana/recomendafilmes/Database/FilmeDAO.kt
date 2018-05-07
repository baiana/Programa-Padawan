package padawana.recomendafilmes.Database

import android.arch.persistence.room.*
import padawana.recomendafilmes.retrofit.Filme

@Dao
interface FilmeDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFilme(filme: Array<Filme>)

    @Update
    fun updateFilme(filme: Filme)

    @Query("SELECT * FROM filme")
    fun loadFilmes(): List<Filme>

    @Query("SELECT * FROM filme WHERE titulo = :tituloPesquisa")
    fun loadPerTitle(tituloPesquisa: String): Array<Filme>



    @Query("SELECT * FROM filme WHERE favorito = 1")
    fun loadFavoritos(): List<Filme>

    @Query("UPDATE filme SET favorito = :valor WHERE idFilme=:id")
    fun favoritar(valor: Boolean, id: Int)

}
