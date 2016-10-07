package dcsoft.somsipinzano;

class Categoria {
    int idCategoria;
    String nome;
    String fileImmagine;

    @Override
    public String toString() {
        return "\n" +
                "\nidCategoria: " + idCategoria +
                "\nnome: " + nome +
                "\nfileImmagine: " + fileImmagine +
                "\n\n";
    }
}
