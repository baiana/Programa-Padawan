package padawana.filmesapis.Retrofit

import padawana.filmesapis.Model.Filme

/**
 * Programado com amor por Ana Luísa Dias em 13/03/2018.
 */
 interface FilmeResposta{
    fun sucesso(filmes: List<Filme>)
}