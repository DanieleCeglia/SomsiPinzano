package dcsoft.somsipinzano;

import java.io.Serializable;

class Pdi implements Serializable {
    int idPdi;
    int idPdi_idCategoria;
    String titoloItaliano;
    String titoloInglese;
    String descrizioneItaliano;
    String descrizioneInglese;
    String citta;
    String via;
    int numeroCivico;
    String interno;
    int cap;
    double latitudine;
    double longitudine;
    String fileImmagine;
    String titoloLinkGenericoItaliano;
    String titoloLinkGenericoInglese;
    String linkGenerico;
    String linkVideo;

    @Override
    public String toString() {
        return "\n" +
                "\nidPdi: " + idPdi +
                "\nidPdi_idCategoria: " + idPdi_idCategoria +
                "\ntitoloItaliano: " + titoloItaliano +
                "\ntitoloInglese: " + titoloInglese +
                "\ndescrizioneItaliano: " + descrizioneItaliano +
                "\ndescrizioneInglese: " + descrizioneInglese +
                "\ncitta: " + citta +
                "\nvia: " + via +
                "\nnumeroCivico: " + numeroCivico +
                "\ninterno: " + interno +
                "\ncap: " + cap +
                "\nlatitudine: " + latitudine +
                "\nlongitudine: " + longitudine +
                "\nfileImmagine: " + fileImmagine +
                "\ntitoloLinkGenericoItaliano: " + titoloLinkGenericoItaliano +
                "\ntitoloLinkGenericoInglese: " + titoloLinkGenericoInglese +
                "\nlinkGenerico: " + linkGenerico +
                "\nlinkVideo: " + linkVideo +
                "\n\n";
    }
}
