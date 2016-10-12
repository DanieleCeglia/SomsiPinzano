package dcsoft.somsipinzano;

import java.io.Serializable;

class Pdi implements Serializable {
    int idPdi;
    int idPdi_idCategoria;
    String titolo;
    String descrizione;
    String citta;
    String via;
    int numeroCivico;
    String interno;
    int cap;
    double latitudine;
    double longitudine;
    String fileImmagine;

    @Override
    public String toString() {
        return "\n" +
                "\nidPdi: " + idPdi +
                "\nidPdi_idCategoria: " + idPdi_idCategoria +
                "\ntitolo: " + titolo +
                "\ndescrizione: " + descrizione +
                "\ncitta: " + citta +
                "\nvia: " + via +
                "\nnumeroCivico: " + numeroCivico +
                "\ninterno: " + interno +
                "\ncap: " + cap +
                "\nlatitudine: " + latitudine +
                "\nlongitudine: " + longitudine +
                "\nfileImmagine: " + fileImmagine +
                "\n\n";
    }
}
