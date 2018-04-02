package padawana.recomendafilmes.Fragmentos


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.support.v7.widget.GridLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.alert.view.*
import kotlinx.android.synthetic.main.fragment_fragmento_drama.*
import kotlinx.android.synthetic.main.fragment_fragmento_scifi.*
import padawana.recomendafilmes.Retrofit.API

import padawana.recomendafilmes.R
import padawana.recomendafilmes.SampleRecyclerViewAdapter
import padawana.recomendafilmespackage.FilmResult
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


/**
 * A simple [Fragment] subclass.
 */
class FragmentoScifi : Fragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val call: Call<FilmResult> = API.moviesApi.listaSciFi("9d61623e84414389bee8063e589ae6f4", "pt-BR")
        call.enqueue(object: Callback<FilmResult?> {
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
        return inflater!!.inflate(R.layout.fragment_fragmento_scifi, container, false)
    }

    private fun initRecycleView(films: FilmResult?) {
        if  (films?.results != null && films.results.isNotEmpty()) {
            recyclerViewScifi?.setHasFixedSize(true)
            recyclerViewScifi?.layoutManager = GridLayoutManager(context,2)
            recyclerViewScifi?.adapter = SampleRecyclerViewAdapter(context, films)
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

   }// Required empty public constructor
