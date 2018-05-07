package padawana.recomendafilmes

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso.get
import kotlinx.android.synthetic.main.favorito.view.*
import kotlinx.android.synthetic.main.item_sample.view.*
import padawana.recomendafilmes.Database.AppDatabase
import padawana.recomendafilmes.R.mipmap.emptystar
import padawana.recomendafilmes.R.mipmap.estrela
import padawana.recomendafilmes.retrofit.Filme


class SampleRecyclerViewAdapter(private val context: Context, private val filmes: ArrayList<Filme>) : RecyclerView.Adapter<SampleRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
            ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_sample, parent, false))

    override fun getItemCount(): Int = filmes.count()

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val filmeItem = filmes[position]
        holder.labelTXT?.text = filmeItem.titulo
        if (filmeItem.favorito){ holder.favorito.setBackgroundResource(estrela) }
        else { holder.favorito.setBackgroundResource(emptystar)}

        get().load("https://image.tmdb.org/t/p/w500${filmeItem.posterPath}").resize(200, 240).centerCrop().into(holder?.fundo)

        holder.favorito.setOnClickListener { favoritar(filmeItem, holder) }

        holder.itemView?.setOnClickListener {
            val intent = Intent(context, FilmeDetailsActivity::class.java)
            holder.itemView.favoritoButton.setOnClickListener { favoritar(filmeItem, holder) }
            intent.putExtra(FilmeDetailsActivity.MESSAGE, filmeItem.titulo)
            intent.putExtra(FilmeDetailsActivity.SINOPSE, filmeItem.sinopse)
            intent.putExtra(FilmeDetailsActivity.ESTRELAS, filmeItem.voteAverage.toString())
            intent.putExtra(FilmeDetailsActivity.FUNDOURL, filmeItem.posterPath)
            intent.putExtra(FilmeDetailsActivity.FAVORITOS.toString(), filmeItem.favorito)
            intent.putExtra(FilmeDetailsActivity.ID.toString(),filmeItem.idFilme)
            context.startActivity(intent)
        }
    }

    fun adicionarFilmes(filmes: ArrayList<Filme>) {
        this.filmes.addAll(filmes)
        this.notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val labelTXT = itemView.labelTXT
        val fundo = itemView.fundo
        var favorito = itemView.favoritoButton
    }

    fun favoritar(filmeItem: Filme, holder: ViewHolder) {
        val efavorito: Boolean

        if (filmeItem.favorito) {
            efavorito = false
            holder.favorito.setBackgroundResource(emptystar)
            filmeItem.favorito = filmeItem.favorito.not()
        } else {
            efavorito = true
            holder.favorito.setBackgroundResource(estrela)
            filmeItem.favorito = filmeItem.favorito.not()
        }
        AppDatabase.getInstance(context)?.filmesDao()!!.favoritar(efavorito, filmeItem.idFilme!!)
    }
}