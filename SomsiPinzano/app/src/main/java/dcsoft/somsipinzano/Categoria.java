package dcsoft.somsipinzano;

import java.io.Serializable;

class Categoria implements Serializable {
    int idCategoria;
    String nomeItaliano;
    String nomeInglese;
    String fileImmagine;
    String coloreEsadecimale;

    @Override
    public String toString() {
        return "\n" +
                "\nidCategoria: " + idCategoria +
                "\nnomeItaliano: " + nomeItaliano +
                "\nnomeInglese: " + nomeInglese +
                "\nfileImmagine: " + fileImmagine +
                "\ncoloreEsadecimale: " + coloreEsadecimale +
                "\n\n";
    }
}
