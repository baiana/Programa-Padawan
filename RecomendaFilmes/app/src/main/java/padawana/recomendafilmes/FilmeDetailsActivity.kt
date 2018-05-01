package padawana.recomendafilmes

import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Layout.JUSTIFICATION_MODE_INTER_WORD
import android.view.MenuItem
import android.view.View
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.film_details.*
import kotlinx.android.synthetic.main.avaliacao.*
import kotlinx.android.synthetic.main.favorito.*

import kotlinx.android.synthetic.main.toolbar.*
import padawana.recomendafilmes.R.mipmap.*


class FilmeDetailsActivity : AppCompatActivity() {

    companion object {
        const val MESSAGE = "Titulo"
        const val SINOPSE = "Sinopse"
        const val FUNDOURL = "FUNDOURL"
        const val ESTRELAS = ""
        const val FAVORITOS = false


    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.film_details)

        val title = intent.getStringExtra(MESSAGE)
        val sinopse = intent.getStringExtra(SINOPSE)
        val fundo = intent.getStringExtra(FUNDOURL)
        val mvotos = intent.getStringExtra(ESTRELAS)
        var favorito:Boolean = intent.getBooleanExtra(FAVORITOS.toString(),false)

        favoritoButton.setOnClickListener({
            favorito = favorito.not()
            if(favorito){
                favoritoButton.setBackgroundResource(estrela)
            }else{
                favoritoButton.setBackgroundResource(emptystar)
            }

        })

        setMessage(title, sinopse, fundo, mvotos,favorito)
        initToolbar(title)
    }

    private fun initToolbar(title: String) {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        if (title.isBlank())
            supportActionBar?.title = getString(R.string.app_name)
        else
            supportActionBar?.title = title
    }

    private fun setMessage(stringSampleItem: String?, sinopse: String?, fundo: String?, votos: String?,favorito:Boolean) {
        labelTXT?.text = stringSampleItem
        sinopseTXT.text = sinopse
        if(favorito){
            favoritoButton.setBackgroundResource(estrela)
        }else{
            favoritoButton.setBackgroundResource(emptystar)
        }
        //TODO chamar função do FilmeDAO favoritar(valor:Boolean,id:Int) passando favoritar(efavorito,filmeItem.idFilme)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            sinopseTXT.justificationMode = JUSTIFICATION_MODE_INTER_WORD
        }

        Picasso.get().load("https://image.tmdb.org/t/p/w500$fundo").into(this.fundop)
        Picasso.get().load("https://image.tmdb.org/t/p/w500$fundo").into(this.Fundototal)
        nota.text = votos
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home)
            onBackPressed()
        return super.onOptionsItemSelected(item)
    }
}
