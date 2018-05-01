package padawana.recomendafilmes.Database

import android.arch.persistence.room.*
import padawana.recomendafilmes.Filme

@Dao
interface FilmeDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFilme(filme: Array<Filme>)

    @Update
    fun updateFilme(filme: Filme)

    @Query("SELECT * FROM Filmes")
    fun loadFilmes(): Array<Filme>

    @Query("SELECT * FROM Filmes WHERE titulo = :tituloPesquisa")
    fun loadPerTitle(tituloPesquisa:String):Array<Filme>

    @Query("SELECT * FROM Filmes WHERE favorito = :favorito")
    fun loadFavoritos(favorito:Boolean = true):Array<Filme>

    @Query("UPDATE Filmes SET favorito = :valor WHERE idFilme=:id")
    fun favoritar(valor:Boolean,id:Int)

}
