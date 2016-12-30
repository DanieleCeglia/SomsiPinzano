package dcsoft.somsipinzano;

import android.os.Parcel;
import android.os.Parcelable;

public class Pdi implements Parcelable {
    private int idPdi;
    private int idPdi_idCategoria;
    private int ordinamento;
    private String titoloItaliano;
    private String titoloInglese;
    private String descrizioneItaliano;
    private String descrizioneInglese;
    private String citta;
    private String via;
    private int numeroCivico;
    private String interno;
    private int cap;
    private double latitudine;
    private double longitudine;
    private String titoloLinkGenericoItaliano;
    private String titoloLinkGenericoInglese;
    private String linkGenerico;
    private String linkVideo;

    public Pdi(int idPdi, int idPdi_idCategoria, int ordinamento, String titoloItaliano, String titoloInglese, String descrizioneItaliano, String descrizioneInglese, String citta, String via, int numeroCivico, String interno, int cap, double latitudine, double longitudine, String titoloLinkGenericoItaliano, String titoloLinkGenericoInglese, String linkGenerico, String linkVideo) {
        super();

        this.idPdi                      = idPdi;
        this.idPdi_idCategoria          = idPdi_idCategoria;
        this.ordinamento                = ordinamento;
        this.titoloItaliano             = titoloItaliano;
        this.titoloInglese              = titoloInglese;
        this.descrizioneItaliano        = descrizioneItaliano;
        this.descrizioneInglese         = descrizioneInglese;
        this.citta                      = citta;
        this.via                        = via;
        this.numeroCivico               = numeroCivico;
        this.interno                    = interno;
        this.cap                        = cap;
        this.latitudine                 = latitudine;
        this.longitudine                = longitudine;
        this.titoloLinkGenericoItaliano = titoloLinkGenericoItaliano;
        this.titoloLinkGenericoInglese  = titoloLinkGenericoInglese;
        this.linkGenerico               = linkGenerico;
        this.linkVideo                  = linkVideo;
    }

    public Pdi(Parcel parcel) {
        super();

        this.idPdi                      = parcel.readInt();
        this.idPdi_idCategoria          = parcel.readInt();
        this.ordinamento                = parcel.readInt();
        this.titoloItaliano             = parcel.readString();
        this.titoloInglese              = parcel.readString();
        this.descrizioneItaliano        = parcel.readString();
        this.descrizioneInglese         = parcel.readString();
        this.citta                      = parcel.readString();
        this.via                        = parcel.readString();
        this.numeroCivico               = parcel.readInt();
        this.interno                    = parcel.readString();
        this.cap                        = parcel.readInt();
        this.latitudine                 = parcel.readDouble();
        this.longitudine                = parcel.readDouble();
        this.titoloLinkGenericoItaliano = parcel.readString();
        this.titoloLinkGenericoInglese  = parcel.readString();
        this.linkGenerico               = parcel.readString();
        this.linkVideo                  = parcel.readString();
    }

    public int getIdPdi() {
        return idPdi;
    }

    public int getIdPdi_idCategoria() {
        return idPdi_idCategoria;
    }

    public int getOrdinamento() {
        return ordinamento;
    }

    public String getTitoloItaliano() {
        return titoloItaliano;
    }

    public String getTitoloInglese() {
        return titoloInglese;
    }

    public String getDescrizioneItaliano() {
        return descrizioneItaliano;
    }

    public String getDescrizioneInglese() {
        return descrizioneInglese;
    }

    public String getCitta() {
        return citta;
    }

    public String getVia() {
        return via;
    }

    public int getNumeroCivico() {
        return numeroCivico;
    }

    public String getInterno() {
        return interno;
    }

    public int getCap() {
        return cap;
    }

    public double getLatitudine() {
        return latitudine;
    }

    public double getLongitudine() {
        return longitudine;
    }

    public String getTitoloLinkGenericoItaliano() {
        return titoloLinkGenericoItaliano;
    }

    public String getTitoloLinkGenericoInglese() {
        return titoloLinkGenericoInglese;
    }

    public String getLinkGenerico() {
        return linkGenerico;
    }

    public String getLinkVideo() {
        return linkVideo;
    }

    public void setIdPdi(int idPdi) {
        this.idPdi = idPdi;
    }

    public void setIdPdi_idCategoria(int idPdi_idCategoria) {
        this.idPdi_idCategoria = idPdi_idCategoria;
    }

    public void setOrdinamento(int ordinamento) {
        this.ordinamento = ordinamento;
    }

    public void setTitoloItaliano(String titoloItaliano) {
        this.titoloItaliano = titoloItaliano;
    }

    public void setTitoloInglese(String titoloInglese) {
        this.titoloInglese = titoloInglese;
    }

    public void setDescrizioneItaliano(String descrizioneItaliano) {
        this.descrizioneItaliano = descrizioneItaliano;
    }

    public void setDescrizioneInglese(String descrizioneInglese) {
        this.descrizioneInglese = descrizioneInglese;
    }

    public void setCitta(String citta) {
        this.citta = citta;
    }

    public void setVia(String via) {
        this.via = via;
    }

    public void setNumeroCivico(int numeroCivico) {
        this.numeroCivico = numeroCivico;
    }

    public void setInterno(String interno) {
        this.interno = interno;
    }

    public void setCap(int cap) {
        this.cap = cap;
    }

    public void setLatitudine(double latitudine) {
        this.latitudine = latitudine;
    }

    public void setLongitudine(double longitudine) {
        this.longitudine = longitudine;
    }

    public void setTitoloLinkGenericoItaliano(String titoloLinkGenericoItaliano) {
        this.titoloLinkGenericoItaliano = titoloLinkGenericoItaliano;
    }

    public void setTitoloLinkGenericoInglese(String titoloLinkGenericoInglese) {
        this.titoloLinkGenericoInglese = titoloLinkGenericoInglese;
    }

    public void setLinkGenerico(String linkGenerico) {
        this.linkGenerico = linkGenerico;
    }

    public void setLinkVideo(String linkVideo) {
        this.linkVideo = linkVideo;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(idPdi);
        dest.writeInt(idPdi_idCategoria);
        dest.writeInt(ordinamento);
        dest.writeString(titoloItaliano);
        dest.writeString(titoloInglese);
        dest.writeString(descrizioneItaliano);
        dest.writeString(descrizioneInglese);
        dest.writeString(citta);
        dest.writeString(via);
        dest.writeInt(numeroCivico);
        dest.writeString(interno);
        dest.writeInt(cap);
        dest.writeDouble(latitudine);
        dest.writeDouble(longitudine);
        dest.writeString(titoloLinkGenericoItaliano);
        dest.writeString(titoloLinkGenericoInglese);
        dest.writeString(linkGenerico);
        dest.writeString(linkVideo);
    }

    public final static Parcelable.Creator CREATOR = new Parcelable.Creator() {
        @Override
        public Pdi createFromParcel(Parcel source) {
            return new Pdi(source);
        }

        @Override
        public Pdi[] newArray(int size) {
            return new Pdi[size];
        }
    };

    @Override
    public String toString() {
        return "\n" +
                "\nidPdi: " + idPdi +
                "\nidPdi_idCategoria: " + idPdi_idCategoria +
                "\nordinamento: " + ordinamento +
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
                "\ntitoloLinkGenericoItaliano: " + titoloLinkGenericoItaliano +
                "\ntitoloLinkGenericoInglese: " + titoloLinkGenericoInglese +
                "\nlinkGenerico: " + linkGenerico +
                "\nlinkVideo: " + linkVideo +
                "\n\n";
    }
}
