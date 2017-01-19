package dcsoft.somsipinzano;

import android.os.Parcel;
import android.os.Parcelable;

public class Pdi implements Parcelable {
    private int idPdi;
    private int idPdi_idCategoria;
    private int idPdi_idRaggruppamento;
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
    private String telefono;
    private String fax;
    private String cellulare;
    private String email;
    private String titoloLinkGenerico1Italiano;
    private String titoloLinkGenerico1Inglese;
    private String linkGenerico1;
    private String titoloLinkGenerico2Italiano;
    private String titoloLinkGenerico2Inglese;
    private String linkGenerico2;
    private String titoloLinkGenerico3Italiano;
    private String titoloLinkGenerico3Inglese;
    private String linkGenerico3;
    private String titoloLinkGenerico4Italiano;
    private String titoloLinkGenerico4Inglese;
    private String linkGenerico4;
    private String fileTracciaGps;

    public Pdi(int idPdi,
               int idPdi_idCategoria,
               int idPdi_idRaggruppamento,
               int ordinamento,
               String titoloItaliano,
               String titoloInglese,
               String descrizioneItaliano,
               String descrizioneInglese,
               String citta,
               String via,
               int numeroCivico,
               String interno,
               int cap,
               double latitudine,
               double longitudine,
               String telefono,
               String fax,
               String cellulare,
               String email,
               String titoloLinkGenerico1Italiano,
               String titoloLinkGenerico1Inglese,
               String linkGenerico1,
               String titoloLinkGenerico2Italiano,
               String titoloLinkGenerico2Inglese,
               String linkGenerico2,
               String titoloLinkGenerico3Italiano,
               String titoloLinkGenerico3Inglese,
               String linkGenerico3,
               String titoloLinkGenerico4Italiano,
               String titoloLinkGenerico4Inglese,
               String linkGenerico4,
               String fileTracciaGps) {
        super();

        this.idPdi                       = idPdi;
        this.idPdi_idCategoria           = idPdi_idCategoria;
        this.idPdi_idRaggruppamento      = idPdi_idRaggruppamento;
        this.ordinamento                 = ordinamento;
        this.titoloItaliano              = titoloItaliano;
        this.titoloInglese               = titoloInglese;
        this.descrizioneItaliano         = descrizioneItaliano;
        this.descrizioneInglese          = descrizioneInglese;
        this.citta                       = citta;
        this.via                         = via;
        this.numeroCivico                = numeroCivico;
        this.interno                     = interno;
        this.cap                         = cap;
        this.latitudine                  = latitudine;
        this.longitudine                 = longitudine;
        this.telefono                    = telefono;
        this.fax                         = fax;
        this.cellulare                   = cellulare;
        this.email                       = email;
        this.titoloLinkGenerico1Italiano = titoloLinkGenerico1Italiano;
        this.titoloLinkGenerico1Inglese  = titoloLinkGenerico1Inglese;
        this.linkGenerico1               = linkGenerico1;
        this.titoloLinkGenerico2Italiano = titoloLinkGenerico2Italiano;
        this.titoloLinkGenerico2Inglese  = titoloLinkGenerico2Inglese;
        this.linkGenerico2               = linkGenerico2;
        this.titoloLinkGenerico3Italiano = titoloLinkGenerico3Italiano;
        this.titoloLinkGenerico3Inglese  = titoloLinkGenerico3Inglese;
        this.linkGenerico3               = linkGenerico3;
        this.titoloLinkGenerico4Italiano = titoloLinkGenerico4Italiano;
        this.titoloLinkGenerico4Inglese  = titoloLinkGenerico4Inglese;
        this.linkGenerico4               = linkGenerico4;
        this.fileTracciaGps              = fileTracciaGps;
    }

    public Pdi(Parcel parcel) {
        super();

        this.idPdi                       = parcel.readInt();
        this.idPdi_idCategoria           = parcel.readInt();
        this.idPdi_idRaggruppamento      = parcel.readInt();
        this.ordinamento                 = parcel.readInt();
        this.titoloItaliano              = parcel.readString();
        this.titoloInglese               = parcel.readString();
        this.descrizioneItaliano         = parcel.readString();
        this.descrizioneInglese          = parcel.readString();
        this.citta                       = parcel.readString();
        this.via                         = parcel.readString();
        this.numeroCivico                = parcel.readInt();
        this.interno                     = parcel.readString();
        this.cap                         = parcel.readInt();
        this.latitudine                  = parcel.readDouble();
        this.longitudine                 = parcel.readDouble();
        this.telefono                    = parcel.readString();
        this.fax                         = parcel.readString();
        this.cellulare                   = parcel.readString();
        this.email                       = parcel.readString();
        this.titoloLinkGenerico1Italiano = parcel.readString();
        this.titoloLinkGenerico1Inglese  = parcel.readString();
        this.linkGenerico1               = parcel.readString();
        this.titoloLinkGenerico2Italiano = parcel.readString();
        this.titoloLinkGenerico2Inglese  = parcel.readString();
        this.linkGenerico2               = parcel.readString();
        this.titoloLinkGenerico3Italiano = parcel.readString();
        this.titoloLinkGenerico3Inglese  = parcel.readString();
        this.linkGenerico3               = parcel.readString();
        this.titoloLinkGenerico4Italiano = parcel.readString();
        this.titoloLinkGenerico4Inglese  = parcel.readString();
        this.linkGenerico4               = parcel.readString();
        this.fileTracciaGps              = parcel.readString();
    }

    public int getIdPdi() {
        return idPdi;
    }

    public int getIdPdi_idCategoria() {
        return idPdi_idCategoria;
    }

    public int getIdPdi_idRaggruppamento() {
        return idPdi_idRaggruppamento;
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

    public String getTelefono() {
        return telefono;
    }

    public String getFax() {
        return fax;
    }

    public String getCellulare() {
        return cellulare;
    }

    public String getEmail() {
        return email;
    }

    public String getTitoloLinkGenerico1Italiano() {
        return titoloLinkGenerico1Italiano;
    }

    public String getTitoloLinkGenerico1Inglese() {
        return titoloLinkGenerico1Inglese;
    }

    public String getLinkGenerico1() {
        return linkGenerico1;
    }

    public String getTitoloLinkGenerico2Italiano() {
        return titoloLinkGenerico2Italiano;
    }

    public String getTitoloLinkGenerico2Inglese() {
        return titoloLinkGenerico2Inglese;
    }

    public String getLinkGenerico2() {
        return linkGenerico2;
    }

    public String getTitoloLinkGenerico3Italiano() {
        return titoloLinkGenerico3Italiano;
    }

    public String getTitoloLinkGenerico3Inglese() {
        return titoloLinkGenerico3Inglese;
    }

    public String getLinkGenerico3() {
        return linkGenerico3;
    }

    public String getTitoloLinkGenerico4Italiano() {
        return titoloLinkGenerico4Italiano;
    }

    public String getTitoloLinkGenerico4Inglese() {
        return titoloLinkGenerico4Inglese;
    }

    public String getLinkGenerico4() {
        return linkGenerico4;
    }

    public String getFileTracciaGps() {
        return fileTracciaGps;
    }

    public void setIdPdi(int idPdi) {
        this.idPdi = idPdi;
    }

    public void setIdPdi_idCategoria(int idPdi_idCategoria) {
        this.idPdi_idCategoria = idPdi_idCategoria;
    }

    public void setIdPdi_idRaggruppamento(int idPdi_idRaggruppamento) {
        this.idPdi_idRaggruppamento = idPdi_idRaggruppamento;
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

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public void setCellulare(String cellulare) {
        this.cellulare = cellulare;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setTitoloLinkGenerico1Italiano(String titoloLinkGenerico1Italiano) {
        this.titoloLinkGenerico1Italiano = titoloLinkGenerico1Italiano;
    }

    public void setTitoloLinkGenerico1Inglese(String titoloLinkGenerico1Inglese) {
        this.titoloLinkGenerico1Inglese = titoloLinkGenerico1Inglese;
    }

    public void setLinkGenerico1(String linkGenerico1) {
        this.linkGenerico1 = linkGenerico1;
    }

    public void setTitoloLinkGenerico2Italiano(String titoloLinkGenerico2Italiano) {
        this.titoloLinkGenerico2Italiano = titoloLinkGenerico2Italiano;
    }

    public void setTitoloLinkGenerico2Inglese(String titoloLinkGenerico2Inglese) {
        this.titoloLinkGenerico2Inglese = titoloLinkGenerico2Inglese;
    }

    public void setLinkGenerico2(String linkGenerico2) {
        this.linkGenerico1 = linkGenerico2;
    }

    public void setTitoloLinkGenerico3Italiano(String titoloLinkGenerico3Italiano) {
        this.titoloLinkGenerico3Italiano = titoloLinkGenerico3Italiano;
    }

    public void setTitoloLinkGenerico3Inglese(String titoloLinkGenerico3Inglese) {
        this.titoloLinkGenerico3Inglese = titoloLinkGenerico3Inglese;
    }

    public void setLinkGenerico3(String linkGenerico3) {
        this.linkGenerico3 = linkGenerico3;
    }

    public void setTitoloLinkGenerico4Italiano(String titoloLinkGenerico4Italiano) {
        this.titoloLinkGenerico4Italiano = titoloLinkGenerico4Italiano;
    }

    public void setTitoloLinkGenerico4Inglese(String titoloLinkGenerico4Inglese) {
        this.titoloLinkGenerico4Inglese = titoloLinkGenerico4Inglese;
    }

    public void setLinkGenerico4(String linkGenerico4) {
        this.linkGenerico4 = linkGenerico4;
    }

    public void setFileTracciaGps(String fileTracciaGps) {
        this.fileTracciaGps = fileTracciaGps;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(idPdi);
        dest.writeInt(idPdi_idCategoria);
        dest.writeInt(idPdi_idRaggruppamento);
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
        dest.writeString(telefono);
        dest.writeString(fax);
        dest.writeString(cellulare);
        dest.writeString(email);
        dest.writeString(titoloLinkGenerico1Italiano);
        dest.writeString(titoloLinkGenerico1Inglese);
        dest.writeString(linkGenerico1);
        dest.writeString(titoloLinkGenerico2Italiano);
        dest.writeString(titoloLinkGenerico2Inglese);
        dest.writeString(linkGenerico2);
        dest.writeString(titoloLinkGenerico3Italiano);
        dest.writeString(titoloLinkGenerico3Inglese);
        dest.writeString(linkGenerico3);
        dest.writeString(titoloLinkGenerico4Italiano);
        dest.writeString(titoloLinkGenerico4Inglese);
        dest.writeString(linkGenerico4);
        dest.writeString(fileTracciaGps);
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
                "\nidPdi_idRaggruppamento: " + idPdi_idRaggruppamento +
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
                "\ntelefono: " + telefono +
                "\nfax: " + fax +
                "\ncellulare: " + cellulare +
                "\nemail: " + email +
                "\ntitoloLinkGenerico1Italiano: " + titoloLinkGenerico1Italiano +
                "\ntitoloLinkGenerico1Inglese: " + titoloLinkGenerico1Inglese +
                "\nlinkGenerico1: " + linkGenerico1 +
                "\ntitoloLinkGenerico2Italiano: " + titoloLinkGenerico2Italiano +
                "\ntitoloLinkGenerico2Inglese: " + titoloLinkGenerico2Inglese +
                "\nlinkGenerico2: " + linkGenerico2 +
                "\ntitoloLinkGenerico3Italiano: " + titoloLinkGenerico3Italiano +
                "\ntitoloLinkGenerico3Inglese: " + titoloLinkGenerico3Inglese +
                "\nlinkGenerico3: " + linkGenerico3 +
                "\ntitoloLinkGenerico4Italiano: " + titoloLinkGenerico4Italiano +
                "\ntitoloLinkGenerico4Inglese: " + titoloLinkGenerico4Inglese +
                "\nlinkGenerico4: " + linkGenerico4 +
                "\nfileTracciaGps: " + fileTracciaGps +
                "\n\n";
    }
}
