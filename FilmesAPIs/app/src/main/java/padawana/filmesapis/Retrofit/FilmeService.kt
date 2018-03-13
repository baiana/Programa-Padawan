package padawana.filmesapis.Retrofit
import padawana.filmesapis.Model.Filme
import retrofit2.Call
import retrofit2.http.GET
/**
 * Programado com amor por Ana Luísa Dias em 11/03/2018./
 */
interface FilmeService {
//TODO chamar apikey dos values
    @GET("269149?api_key=9d61623e84414389bee8063e589ae6f4&language=pt-BR")
    //TODO modificar link (retorno com os dados do filme zootopia)
    fun listarFilmes(): Call<List<Filme>>

}