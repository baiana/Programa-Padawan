package padawana.filmesapis

/**
 * Created by aninh_000 on 10/03/2018.
 */
import android.content.Context
import android.support.v7.widget.RecyclerView
import  android.support.v7.widget.RecyclerView.Adapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.main_cartoes.view.*

class CartoesFilmesAdapter(private val filmes: List<Filme>,private val context: Context): Adapter<CartoesFilmesAdapter.ViewHolder>()  {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titulo = itemView.titulo
        val sinopse = itemView.sinopse

       public fun bindView(filme:Filme) {
            val titulo = itemView.titulo
            val sinopse = itemView.sinopse
            titulo.text = filme.titulo
            sinopse.text = filme.sinopse
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.main_cartoes, parent, false)
        return ViewHolder(view)
    }
    override fun getItemCount(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        val filmes = filmes[position]
        //TODO usar ? safe call ou if
        //TODO usar o let se o holder não for null
        //bindView(filmes)


    }


}


