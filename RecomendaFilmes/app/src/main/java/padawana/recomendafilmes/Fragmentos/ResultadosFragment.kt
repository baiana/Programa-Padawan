package padawana.recomendafilmes.Fragmentos

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.support.v7.widget.GridLayoutManager
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import kotlinx.android.synthetic.main.alert.view.*
import kotlinx.android.synthetic.main.fragment_resultados.*
import kotlinx.android.synthetic.main.loading.*
import padawana.recomendafilmes.R
import padawana.recomendafilmes.Retrofit.API
import padawana.recomendafilmes.SampleRecyclerViewAdapter
import padawana.recomendafilmespackage.FilmResult
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


@SuppressLint("ValidFragment")

class ResultadosFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_resultados, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun initRecycleView(films: FilmResult?) {
        var loading:ProgressBar? = loadingBar.findViewById(progressBar.id)
        loading?.visibility = View.VISIBLE

        val toast = Toast.makeText(context, "Ops! Filme não encontrado\nConfira sua pesquisa tente novamente", Toast.LENGTH_LONG)
        toast.setGravity(Gravity.CENTER, 0, 0)

        if  (films?.results != null && films.results.isNotEmpty()) {
            carinhaTriste.visibility = View.GONE
            recyclerViewPesquisa?.setHasFixedSize(true)
            recyclerViewPesquisa?.layoutManager = GridLayoutManager(context,2)
            recyclerViewPesquisa?.adapter = SampleRecyclerViewAdapter(context, films)

        } else {
            toast.show().let {
                loading?.visibility = View.GONE
                carinhaTriste.visibility = View.VISIBLE
            }

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

    fun pesquisarFilmes(stringFilme:String){
        val call: Call<FilmResult> = API.moviesApi.pesquisaFilme("9d61623e84414389bee8063e589ae6f4", "pt-BR",stringFilme)
        call.enqueue(object: Callback<FilmResult?> {
            override fun onFailure(call: Call<FilmResult?>?, t: Throwable?) {
                Log.e("ERRO no ON FAILURE\n", t?.message)
                displayAlert("Erro no CallBack")
            }

            @SuppressLint("NewApi")
            override fun onResponse(call: Call<FilmResult?>?, response: Response<FilmResult?>?) {

                if (response != null && response.isSuccessful) {
                    initRecycleView(response.body())
                } else {
                    displayAlert("Erro no OnResponse")
                }
            }
        })
    }
}
