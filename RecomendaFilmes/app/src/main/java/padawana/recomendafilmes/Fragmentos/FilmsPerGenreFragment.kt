package padawana.recomendafilmes.Fragmentos

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AlertDialog
import android.support.v7.widget.GridLayoutManager
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
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

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val genre: Genre = arguments.getSerializable(genreKey) as Genre
        ChamaFilmes(genre)
        val attacher = InfiniteScroll(genre)
        attacher.start()

        return inflater!!.inflate(R.layout.fragment_filmes, container, false)
    }

    private fun InfiniteScroll(genre: Genre): BaseAttacher<*, *> {
        val toastTeste = Toast.makeText(context, "Infitite Scroll", Toast.LENGTH_LONG)
        toastTeste.setGravity(Gravity.CENTER, 0, 0)
        val attacher: BaseAttacher<*, *> = Mugen.with(recyclerViewFilme, object : MugenCallbacks {

            override fun onLoadMore() {
                toastTeste.setText("ONLOADMORE")
                toastTeste.show()
                ChamaFilmes(genre)
                TODO("fazer uma nova call")
            }

            override fun isLoading(): Boolean {
                progressBarInfinite.visibility = View.VISIBLE
                /* toastTeste.setText("IS LOADING")
                 toastTeste.show()*/
                TODO("tirar toast de teste")
                return isLoading
            }

            override fun hasLoadedAllItems(): Boolean {
                /* toastTeste.setText("HAS LOADED ALL ITENS")
                 toastTeste.show()*/
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
                    initRecycleView(response.body())
                } else {
                    displayAlert("Erro no OnResponse")
                }
            }
        })
    }

    fun SelecionaGenero(genre: Genre): Call<FilmResult> {
        when (genre) {
            Genre.POPULAR -> return API.moviesApi.listaFilmes()
            Genre.DRAMA -> return API.moviesApi.listaDrama()
            Genre.ADVENTURE -> return API.moviesApi.listaAventura()
            Genre.SCIFI -> return API.moviesApi.listaSciFi()
            Genre.STARWARS -> return API.moviesApi.pesquisaFilme("9d61623e84414389bee8063e589ae6f4", "pt-BR", "Star Wars")
        }
    }

    private fun initRecycleView(films: FilmResult?) {
        if (films?.results != null && films.results.isNotEmpty()) {
            recyclerViewFilme?.setHasFixedSize(true)
            recyclerViewFilme?.layoutManager = GridLayoutManager(context, 2)
            recyclerViewFilme?.adapter = SampleRecyclerViewAdapter(context, films)
        } else {
            displayAlert("Erro no initRecycleView")
        }

    }

    fun displayAlert(stringErro: String) {
        val alert = AlertDialog.Builder(context)
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