package padawana.projetofilmes

 class Filmes(var id_filme: Int) {
     var titulo: String? = null
    var titulo_original: String? = null
    var sinopse: String? = null
    var poster_patch: String? = null

    override fun toString(): String {
        return "Filmes(titulo=$titulo\n" +
                " titulo_original=$titulo_original\n" +
                "sinopse=$sinopse\n" +
                "poster_patch=$poster_patch)"
    }


}