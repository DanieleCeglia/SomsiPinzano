package dcsoft.somsipinzano;

import java.io.Serializable;

class Categoria implements Serializable {
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
