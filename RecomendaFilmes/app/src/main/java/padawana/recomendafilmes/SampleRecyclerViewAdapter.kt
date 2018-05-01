package padawana.recomendafilmes

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.support.v7.widget.AppCompatImageButton
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.favorito.*
import kotlinx.android.synthetic.main.favorito.view.*
import kotlinx.android.synthetic.main.item_sample.view.*
import padawana.recomendafilmes.R.mipmap.*
import padawana.recomendafilmespackage.FilmResult


class SampleRecyclerViewAdapter(private val context: Context, private val filmesResultado: FilmResult) : RecyclerView.Adapter<SampleRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
            ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_sample, parent, false))

    override fun getItemCount(): Int = filmesResultado.results.count()

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val filmeItem = filmesResultado.results[position]
        holder.labelTXT?.text = filmeItem.titulo
        Picasso.get().load("https://image.tmdb.org/t/p/w500${filmeItem.posterPath}").resize(200, 240).centerCrop().into(holder?.fundo)

        holder.favorito.setOnClickListener{Favoritar(filmeItem,holder)}

        holder.itemView?.setOnClickListener {
            val intent = Intent(context, FilmeDetailsActivity::class.java)
            holder.itemView.favoritoButton.setOnClickListener { Favoritar(filmeItem,holder)}
            intent.putExtra(FilmeDetailsActivity.MESSAGE, filmeItem.titulo)
            intent.putExtra(FilmeDetailsActivity.SINOPSE, filmeItem.sinopse)
            intent.putExtra(FilmeDetailsActivity.ESTRELAS, filmeItem.voteAverage.toString())
            intent.putExtra(FilmeDetailsActivity.FUNDOURL, filmeItem.posterPath)
            intent.putExtra(FilmeDetailsActivity.FAVORITOS.toString(), filmeItem.favorito)
            context.startActivity(intent)
        }


    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val labelTXT = itemView.labelTXT
        val fundo = itemView.fundo
        var favorito = itemView.favoritoButton
    }

    fun Favoritar(filmeItem:Filme,holder:ViewHolder) {
        var efavorito:Boolean

        if (filmeItem.favorito) {
            efavorito = false
            holder.favorito.setBackgroundResource(emptystar)
            filmeItem.favorito = filmeItem.favorito.not()
        } else {
            efavorito = true
            holder.favorito.setBackgroundResource(estrela)
            filmeItem.favorito = filmeItem.favorito.not()
        }
        //TODO chamar função do FilmeDAO favoritar(valor:Boolean,id:Int) passando favoritar(efavorito,filmeItem.idFilme)


    }
}