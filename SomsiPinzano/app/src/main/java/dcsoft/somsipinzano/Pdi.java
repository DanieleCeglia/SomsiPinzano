package dcsoft.somsipinzano;

/**
 * Created by daniele on 03/10/16.
 */

public class Pdi {
    public int idPdi;
    public int idPdi_idCategoria;
    public String titolo;
    public String descrizione;
    public String citta;
    public String via;
    public int numeroCivico;
    public String interno;
    public int cap;
    public double latitudine;
    public double longitudine;

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
                "\n\n";
    }
}
