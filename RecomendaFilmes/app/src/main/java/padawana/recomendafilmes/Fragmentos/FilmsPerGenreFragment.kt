package padawana.recomendafilmes.Fragmentos

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.support.v7.widget.GridLayoutManager
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.mugen.Mugen
import com.mugen.MugenCallbacks
import com.mugen.attachers.BaseAttacher
import kotlinx.android.synthetic.main.alert.view.*
import kotlinx.android.synthetic.main.fragment_filmes.*
import kotlinx.android.synthetic.main.fragment_resultados.*
import padawana.recomendafilmes.R
import padawana.recomendafilmes.Retrofit.API
import padawana.recomendafilmes.SampleRecyclerViewAdapter
import padawana.recomendafilmespackage.FilmResult
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FilmsPerGenreFragment : Fragment() {

    enum class Genre {
        POPULAR,
        DRAMA,
        ADVENTURE,
        SCIFI,
        STARWARS
    }

    companion object {
        val genreKey = "KEY_GENRE"
        fun newInstance(genre: Genre): FilmsPerGenreFragment {
            val bundle = Bundle()
            bundle.putSerializable(genreKey, genre)
            val fragmentoFilme = FilmsPerGenreFragment()
            fragmentoFilme.arguments = bundle

            return fragmentoFilme
        }
    }

    var numeroPagina: Int = 0
    var carregaPagina: Int = 0
    var genero:Genre = Genre.POPULAR
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val genre: Genre = arguments?.getSerializable(genreKey) as Genre
        ChamaFilmes(genre)
        genero = genre
        return inflater!!.inflate(R.layout.fragment_filmes, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //val attacher = InfiniteScroll(genero)
        //attacher.start()

    }

    //TODO infinite scroll
    private fun InfiniteScroll(genre: Genre): BaseAttacher<*, *> {
        val toastTeste = Toast.makeText(context, "Infitite Scroll", Toast.LENGTH_LONG)
        toastTeste.setGravity(Gravity.CENTER, 0, 0)
        val attacher: BaseAttacher<*, *> = Mugen.with(recyclerViewFilme, object : MugenCallbacks {

            override fun onLoadMore() {

                if (carregaPagina == 0 || genre.equals(Genre.POPULAR) || genre.equals(Genre.STARWARS)) {
                    toastTeste.setText("Essa categoria não possui mais filmes")
                    toastTeste.show()
                } else {
                    LoadFilmes(genre)
                }
            }

            override fun isLoading(): Boolean {
                progressBarInfinite.visibility = View.VISIBLE
                return isLoading
            }

            override fun hasLoadedAllItems(): Boolean {

                toastTeste.setText("Essa categoria não possui mais filmes\nTente Fazer uma pesquisa")
                toastTeste.show()
                progressBar.visibility = View.GONE
                return false
            }
        })
        return attacher

    }


    fun ChamaFilmes(genre: Genre) {
        val call: Call<FilmResult> = SelecionaGenero(genre)
        call.enqueue(object : Callback<FilmResult?> {
            override fun onFailure(call: Call<FilmResult?>?, t: Throwable?) {
                Log.e("ERRO no ON FAILURE\n", t?.message)
                displayAlert("Erro no CallBack")
            }

            override fun onResponse(call: Call<FilmResult?>?, response: Response<FilmResult?>?) {
                if (response != null && response.isSuccessful) {
                    numeroPagina = response.body()!!.page
                    carregaPagina = response.body()!!.total_pages - numeroPagina
                    initRecycleView(response.body())
                } else {
                    displayAlert("Erro no OnResponse")
                }
            }
        })
    }

    fun LoadFilmes(genre: Genre) {
        numeroPagina++
        val call: Call<FilmResult> = API.moviesApi.LoadFilmes(pagina = numeroPagina.toString(), genero = NumeroGenero(genre))
        call.enqueue(object : Callback<FilmResult?> {
            override fun onFailure(call: Call<FilmResult?>?, t: Throwable?) {
                Log.e("ERRO no ON FAILURE\n", t?.message)
                displayAlert("Erro no CallBack")
            }

            override fun onResponse(call: Call<FilmResult?>?, response: Response<FilmResult?>?) {
                if (response != null && response.isSuccessful) {
                    carregaPagina = response.body()!!.total_pages - numeroPagina
                    initRecycleView(response.body())

                } else {
                    displayAlert("Erro no OnResponse")
                }
            }
        })
    }

    fun NumeroGenero(genre: Genre): String {
        when (genre) {
            Genre.POPULAR -> return ""
            Genre.DRAMA -> return "18"
            Genre.ADVENTURE -> return "12"
            Genre.SCIFI -> return "878"
            Genre.STARWARS -> return ""
        }
    }

    fun SelecionaGenero(genre: Genre): Call<FilmResult> {
        when (genre) {
            Genre.POPULAR -> return API.moviesApi.listaFilmes()
            Genre.DRAMA -> return API.moviesApi.listaDrama()
            Genre.ADVENTURE -> return API.moviesApi.listaAventura()
            Genre.SCIFI -> return API.moviesApi.listaSciFi()
            Genre.STARWARS -> return API.moviesApi.pesquisaFilme(filmeNome = "Star Wars")
        }
    }

    private fun initRecycleView(films: FilmResult?) {
        if (films?.results != null && films.results.isNotEmpty()) {
            recyclerViewFilme?.setHasFixedSize(true)
            recyclerViewFilme?.layoutManager = GridLayoutManager(context, 2)
            recyclerViewFilme?.adapter = SampleRecyclerViewAdapter(context!!, films)
        } else {
            displayAlert("Erro no initRecycleView")
        }

    }

    fun displayAlert(stringErro: String) {
        val alert = AlertDialog.Builder(context!!)
        val nullParent: ViewGroup? = null
        val editaAlerta = layoutInflater.inflate(R.layout.alert, nullParent)
        editaAlerta.textComplementoErro.text = stringErro
        with(alert) {
            setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
            }
        }
        val alertDialog: AlertDialog = alert.create()
        alertDialog.run {
            setView(editaAlerta)
            show()
        }
    }

}

