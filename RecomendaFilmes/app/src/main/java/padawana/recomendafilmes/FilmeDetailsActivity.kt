package padawana.recomendafilmes

import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.text.Layout.JUSTIFICATION_MODE_INTER_WORD
import android.view.MenuItem
import android.view.View
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.film_details.*
import kotlinx.android.synthetic.main.avaliacao.*
import kotlinx.android.synthetic.main.favorito.*

import kotlinx.android.synthetic.main.toolbar.*
import padawana.recomendafilmes.Database.AppDatabase
import padawana.recomendafilmes.R.mipmap.*


class FilmeDetailsActivity : AppCompatActivity() {

    companion object {
        const val MESSAGE = "Titulo"
        const val SINOPSE = "Sinopse"
        const val FUNDOURL = "FUNDOURL"
        const val ESTRELAS = ""
        const val FAVORITOS = false
        const val ID = 0


    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.film_details)
        val id:Int = intent.getIntExtra(ID.toString(),0)
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
            AppDatabase.getInstance(baseContext)!!.filmesDao().favoritar(favorito,id)
        })

        setMessage(title, sinopse, fundo, mvotos,favorito,id)
        initToolbar(title)
    }

    private fun initToolbar(title: String) {
        setSupportActionBar(toolbarDetails as Toolbar?)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        if (title.isBlank())
            supportActionBar?.title = getString(R.string.app_name)
        else
            supportActionBar?.title = title
    }

    private fun setMessage(stringSampleItem: String?, sinopse: String?, fundo: String?, votos: String?,favorito:Boolean,id:Int) {
        labelTXT?.text = stringSampleItem
        sinopseTXT.text = sinopse
        AppDatabase.getInstance(baseContext)!!.filmesDao().favoritar(favorito,id)
        if(favorito){
            favoritoButton.setBackgroundResource(estrela)

        }else{
            favoritoButton.setBackgroundResource(emptystar)
        }

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
