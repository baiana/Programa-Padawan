package padawana.recomendafilmes
import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_sample.view.*
import padawana.recomendafilmespackage.FilmResult


class SampleRecyclerViewAdapter(private val context: Context, private val filmesResultado: FilmResult) : RecyclerView.Adapter<SampleRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder =
            ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_sample, parent, false))

    override fun getItemCount(): Int = filmesResultado.result.count()

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {

        val filmeItem = filmesResultado.result[position]
        holder?.labelTXT?.text = filmeItem.titulo
        Picasso.get().load(filmeItem.posterPath).resize(200, 240).centerCrop().into(holder?.fundo)

        holder?.itemView?.setOnClickListener {
            val intent = Intent(context, SecondActivity::class.java)
            intent.putExtra(SecondActivity.const.MESSAGE, filmeItem.titulo)
            intent.putExtra(SecondActivity.const.SINOPSE, filmeItem.sinopse)
            intent.putExtra(SecondActivity.const.FUNDOURL,filmeItem.posterPath)
                 context.startActivity(intent)
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val labelTXT = itemView.labelTXT
        val fundo = itemView.fundo
    }
}