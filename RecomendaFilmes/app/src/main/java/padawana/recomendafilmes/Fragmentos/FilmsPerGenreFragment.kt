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
import kotlinx.android.synthetic.main.alert.view.*
import kotlinx.android.synthetic.main.fragment_filmes.*
import padawana.recomendafilmes.Database.AppDatabase
import padawana.recomendafilmes.R
import padawana.recomendafilmes.SampleRecyclerViewAdapter
import padawana.recomendafilmes.retrofit.API
import padawana.recomendafilmes.retrofit.Filme
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

    private lateinit var adapter: SampleRecyclerViewAdapter
    private var emChamada = false
    private var carregueiTudo = false
    var numeroPagina: Int = 0
    var carregaPagina: Int = 0
    var genero: Genre = Genre.POPULAR

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_filmes, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val genre: Genre = arguments?.getSerializable(genreKey) as Genre
        AppDatabase.getInstance(context!!)?.filmesDao()
        chamaFilmes(genre)
        genero = genre
    }

    private fun config() {
        val toastNotifica = Toast.makeText(context, null, Toast.LENGTH_LONG)
        toastNotifica.setGravity(Gravity.CENTER, 0, 0)
        val attacher = Mugen.with(recyclerViewFilme, object : MugenCallbacks {

            override fun onLoadMore() {
                if (carregaPagina == 0 || genero.equals(Genre.POPULAR) || genero.equals(Genre.STARWARS)) {
                    emChamada = false
                    toastNotifica.setText("Essa categoria não possui mais filmes!")
                    toastNotifica.show()
                } else {
                    loadFilmes(genero)
                }
            }

            override fun isLoading(): Boolean {
                progressBarInfinite.visibility = View.VISIBLE
                return emChamada
            }

            override fun hasLoadedAllItems(): Boolean {
                if (carregaPagina == 0 || genero.equals(Genre.POPULAR) || genero.equals(Genre.STARWARS))
                    toastNotifica.setText("Essa categoria não possui mais filmes\nTente Fazer uma pesquisa")
                toastNotifica.show()
                progressBarInfinite.visibility = View.GONE
                return carregueiTudo
            }
        })
        attacher.isLoadMoreEnabled = true
        attacher.loadMoreOffset = 6
        attacher.start()
    }

    fun chamaFilmes(genre: Genre) {
        val call: Call<FilmResult> = SelecionaGenero(genre)
        val local = AppDatabase.getInstance(context!!)?.filmesDao()!!.loadFilmes()
        call.enqueue(object : Callback<FilmResult?> {
            override fun onFailure(call: Call<FilmResult?>?, t: Throwable?) {
                Log.e("ERRO no ON FAILURE\n", t?.message)
                displayAlert("Ops! você está sem internet\n Carregando filmes do banco")
                local.let {
                    initRecycleView(local)
                }
            }

            override fun onResponse(call: Call<FilmResult?>?, response: Response<FilmResult?>?) {
                if (response != null && response.isSuccessful) {
                    AppDatabase.getInstance(context!!)?.filmesDao()!!.insertFilme(response.body()!!.results.toTypedArray())
                    numeroPagina = response.body()!!.page
                    carregaPagina = response.body()!!.total_pages - numeroPagina
                    initRecycleView(response.body()!!.results)
                } else {
                    displayAlert("Erro no OnResponse")
                    local.let {
                        initRecycleView(local)
                    }

                }
            }
        })
    }

    fun loadFilmes(genre: Genre) {
        emChamada = true
        numeroPagina++
        val call: Call<FilmResult> = API.moviesApi.LoadFilmes(pagina = numeroPagina.toString(), genero = NumeroGenero(genre))
        call.enqueue(object : Callback<FilmResult?> {
            override fun onFailure(call: Call<FilmResult?>?, t: Throwable?) {
                Log.e("ERRO no ON FAILURE\n", t?.message)
                displayAlert("Erro no CallBack\n Carregando filmes do banco")
                val local = AppDatabase.getInstance(context!!)?.filmesDao()!!.loadFilmes()
                local.let {
                    initRecycleView(local)
                }
            }

            override fun onResponse(call: Call<FilmResult?>?, response: Response<FilmResult?>?) {
                if (response != null && response.isSuccessful) {
                    carregaPagina = response.body()!!.total_pages - numeroPagina
                    adapter.adicionarFilmes(response.body()!!.results as ArrayList<Filme>)
                    AppDatabase.getInstance(context!!)?.filmesDao()!!.insertFilme(response.body()!!.results.toTypedArray())
                } else {
                    displayAlert("Erro no OnResponse")
                }
                emChamada = false
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


    private fun initRecycleView(films: List<Filme>) {
        if (films.isNotEmpty()) {
            recyclerViewFilme?.setHasFixedSize(true)
            recyclerViewFilme?.layoutManager = GridLayoutManager(context, 2)
            adapter = SampleRecyclerViewAdapter(context!!, films as ArrayList<Filme>)
            recyclerViewFilme?.adapter = adapter
            config()
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

