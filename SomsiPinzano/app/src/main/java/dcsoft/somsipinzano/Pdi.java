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
    private String titoloTedesco;
    private String titoloFrancese;
    private String descrizioneItaliano;
    private String descrizioneInglese;
    private String descrizioneTedesco;
    private String descrizioneFrancese;
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
    private String titoloLink1GenericoItaliano;
    private String titoloLink1GenericoInglese;
    private String titoloLink1GenericoTedesco;
    private String titoloLink1GenericoFrancese;
    private String linkGenerico1;
    private String titoloLink2GenericoItaliano;
    private String titoloLink2GenericoInglese;
    private String titoloLink2GenericoTedesco;
    private String titoloLink2GenericoFrancese;
    private String linkGenerico2;
    private String titoloLink3GenericoItaliano;
    private String titoloLink3GenericoInglese;
    private String titoloLink3GenericoTedesco;
    private String titoloLink3GenericoFrancese;
    private String linkGenerico3;
    private String titoloLink4GenericoItaliano;
    private String titoloLink4GenericoInglese;
    private String titoloLink4GenericoTedesco;
    private String titoloLink4GenericoFrancese;
    private String linkGenerico4;
    private String fileTracciaGps;

    public Pdi(int idPdi,
               int idPdi_idCategoria,
               int idPdi_idRaggruppamento,
               int ordinamento,
               String titoloItaliano,
               String titoloInglese,
               String titoloTedesco,
               String titoloFrancese,
               String descrizioneItaliano,
               String descrizioneInglese,
               String descrizioneTedesco,
               String descrizioneFrancese,
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
               String titoloLink1GenericoItaliano,
               String titoloLink1GenericoInglese,
               String titoloLink1GenericoTedesco,
               String titoloLink1GenericoFrancese,
               String linkGenerico1,
               String titoloLink2GenericoItaliano,
               String titoloLink2GenericoInglese,
               String titoloLink2GenericoTedesco,
               String titoloLink2GenericoFrancese,
               String linkGenerico2,
               String titoloLink3GenericoItaliano,
               String titoloLink3GenericoInglese,
               String titoloLink3GenericoTedesco,
               String titoloLink3GenericoFrancese,
               String linkGenerico3,
               String titoloLink4GenericoItaliano,
               String titoloLink4GenericoInglese,
               String titoloLink4GenericoTedesco,
               String titoloLink4GenericoFrancese,
               String linkGenerico4,
               String fileTracciaGps) {
        super();

        this.idPdi                       = idPdi;
        this.idPdi_idCategoria           = idPdi_idCategoria;
        this.idPdi_idRaggruppamento      = idPdi_idRaggruppamento;
        this.ordinamento                 = ordinamento;
        this.titoloItaliano              = titoloItaliano;
        this.titoloInglese               = titoloInglese;
        this.titoloTedesco               = titoloTedesco;
        this.titoloFrancese              = titoloFrancese;
        this.descrizioneItaliano         = descrizioneItaliano;
        this.descrizioneInglese          = descrizioneInglese;
        this.descrizioneTedesco          = descrizioneTedesco;
        this.descrizioneFrancese         = descrizioneFrancese;
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
        this.titoloLink1GenericoItaliano = titoloLink1GenericoItaliano;
        this.titoloLink1GenericoInglese  = titoloLink1GenericoInglese;
        this.titoloLink1GenericoTedesco  = titoloLink1GenericoTedesco;
        this.titoloLink1GenericoFrancese = titoloLink1GenericoFrancese;
        this.linkGenerico1               = linkGenerico1;
        this.titoloLink2GenericoItaliano = titoloLink2GenericoItaliano;
        this.titoloLink2GenericoInglese  = titoloLink2GenericoInglese;
        this.titoloLink2GenericoTedesco  = titoloLink2GenericoTedesco;
        this.titoloLink2GenericoFrancese = titoloLink2GenericoFrancese;
        this.linkGenerico2               = linkGenerico2;
        this.titoloLink3GenericoItaliano = titoloLink3GenericoItaliano;
        this.titoloLink3GenericoInglese  = titoloLink3GenericoInglese;
        this.titoloLink3GenericoTedesco  = titoloLink3GenericoTedesco;
        this.titoloLink3GenericoFrancese = titoloLink3GenericoFrancese;
        this.linkGenerico3               = linkGenerico3;
        this.titoloLink4GenericoItaliano = titoloLink4GenericoItaliano;
        this.titoloLink4GenericoInglese  = titoloLink4GenericoInglese;
        this.titoloLink4GenericoTedesco  = titoloLink4GenericoTedesco;
        this.titoloLink4GenericoFrancese = titoloLink4GenericoFrancese;
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
        this.titoloTedesco               = parcel.readString();
        this.titoloFrancese              = parcel.readString();
        this.descrizioneItaliano         = parcel.readString();
        this.descrizioneInglese          = parcel.readString();
        this.descrizioneTedesco          = parcel.readString();
        this.descrizioneFrancese         = parcel.readString();
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
        this.titoloLink1GenericoItaliano = parcel.readString();
        this.titoloLink1GenericoInglese  = parcel.readString();
        this.titoloLink1GenericoTedesco  = parcel.readString();
        this.titoloLink1GenericoFrancese = parcel.readString();
        this.linkGenerico1               = parcel.readString();
        this.titoloLink2GenericoItaliano = parcel.readString();
        this.titoloLink2GenericoInglese  = parcel.readString();
        this.titoloLink2GenericoTedesco  = parcel.readString();
        this.titoloLink2GenericoFrancese = parcel.readString();
        this.linkGenerico2               = parcel.readString();
        this.titoloLink3GenericoItaliano = parcel.readString();
        this.titoloLink3GenericoInglese  = parcel.readString();
        this.titoloLink3GenericoTedesco  = parcel.readString();
        this.titoloLink3GenericoFrancese = parcel.readString();
        this.linkGenerico3               = parcel.readString();
        this.titoloLink4GenericoItaliano = parcel.readString();
        this.titoloLink4GenericoInglese  = parcel.readString();
        this.titoloLink4GenericoTedesco  = parcel.readString();
        this.titoloLink4GenericoFrancese = parcel.readString();
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

    public String getTitoloTedesco() {
        return titoloTedesco;
    }

    public String getTitoloFrancese() {
        return titoloFrancese;
    }

    public String getDescrizioneItaliano() {
        return descrizioneItaliano;
    }

    public String getDescrizioneInglese() {
        return descrizioneInglese;
    }

    public String getDescrizioneTedesco() {
        return descrizioneTedesco;
    }

    public String getDescrizioneFrancese() {
        return descrizioneFrancese;
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

    public String getTitoloLink1GenericoItaliano() {
        return titoloLink1GenericoItaliano;
    }

    public String getTitoloLink1GenericoInglese() {
        return titoloLink1GenericoInglese;
    }

    public String getTitoloLink1GenericoTedesco() {
        return titoloLink1GenericoTedesco;
    }

    public String getTitoloLink1GenericoFrancese() {
        return titoloLink1GenericoFrancese;
    }

    public String getLinkGenerico1() {
        return linkGenerico1;
    }

    public String getTitoloLink2GenericoItaliano() {
        return titoloLink2GenericoItaliano;
    }

    public String getTitoloLink2GenericoInglese() {
        return titoloLink2GenericoInglese;
    }

    public String getTitoloLink2GenericoTedesco() {
        return titoloLink2GenericoTedesco;
    }

    public String getTitoloLink2GenericoFrancese() {
        return titoloLink2GenericoFrancese;
    }

    public String getLinkGenerico2() {
        return linkGenerico2;
    }

    public String getTitoloLink3GenericoItaliano() {
        return titoloLink3GenericoItaliano;
    }

    public String getTitoloLink3GenericoInglese() {
        return titoloLink3GenericoInglese;
    }

    public String getTitoloLink3GenericoTedesco() {
        return titoloLink3GenericoTedesco;
    }

    public String getTitoloLink3GenericoFrancese() {
        return titoloLink3GenericoFrancese;
    }

    public String getLinkGenerico3() {
        return linkGenerico3;
    }

    public String getTitoloLink4GenericoItaliano() {
        return titoloLink4GenericoItaliano;
    }

    public String getTitoloLink4GenericoInglese() {
        return titoloLink4GenericoInglese;
    }

    public String getTitoloLink4GenericoTedesco() {
        return titoloLink4GenericoTedesco;
    }

    public String getTitoloLink4GenericoFrancese() {
        return titoloLink4GenericoFrancese;
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

    public void setTitoloTedesco(String titoloTedesco) {
        this.titoloTedesco = titoloTedesco;
    }

    public void setTitoloFrancese(String titoloFrancese) {
        this.titoloFrancese = titoloFrancese;
    }

    public void setDescrizioneItaliano(String descrizioneItaliano) {
        this.descrizioneItaliano = descrizioneItaliano;
    }

    public void setDescrizioneInglese(String descrizioneInglese) {
        this.descrizioneInglese = descrizioneInglese;
    }

    public void setDescrizioneTedesco(String descrizioneTedesco) {
        this.descrizioneTedesco = descrizioneTedesco;
    }

    public void setDescrizioneFrancese(String descrizioneFrancese) {
        this.descrizioneFrancese = descrizioneFrancese;
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

    public void setTitoloLink1GenericoItaliano(String titoloLink1GenericoItaliano) {
        this.titoloLink1GenericoItaliano = titoloLink1GenericoItaliano;
    }

    public void setTitoloLink1GenericoInglese(String titoloLink1GenericoInglese) {
        this.titoloLink1GenericoInglese = titoloLink1GenericoInglese;
    }

    public void setTitoloLink1GenericoTedesco(String titoloLink1GenericoTedesco) {
        this.titoloLink1GenericoTedesco = titoloLink1GenericoTedesco;
    }

    public void setTitoloLink1GenericoFrancese(String titoloLink1GenericoFrancese) {
        this.titoloLink1GenericoFrancese = titoloLink1GenericoFrancese;
    }

    public void setLinkGenerico1(String linkGenerico1) {
        this.linkGenerico1 = linkGenerico1;
    }

    public void setTitoloLink2GenericoItaliano(String titoloLink2GenericoItaliano) {
        this.titoloLink2GenericoItaliano = titoloLink2GenericoItaliano;
    }

    public void setTitoloLink2GenericoInglese(String titoloLink2GenericoInglese) {
        this.titoloLink2GenericoInglese = titoloLink2GenericoInglese;
    }

    public void setTitoloLink2GenericoTedesco(String titoloLink2GenericoTedesco) {
        this.titoloLink2GenericoTedesco = titoloLink2GenericoTedesco;
    }

    public void setTitoloLink2GenericoFrancese(String titoloLink2GenericoFrancese) {
        this.titoloLink2GenericoFrancese = titoloLink2GenericoFrancese;
    }

    public void setLinkGenerico2(String linkGenerico2) {
        this.linkGenerico1 = linkGenerico2;
    }

    public void setTitoloLink3GenericoItaliano(String titoloLink3GenericoItaliano) {
        this.titoloLink3GenericoItaliano = titoloLink3GenericoItaliano;
    }

    public void setTitoloLink3GenericoInglese(String titoloLink3GenericoInglese) {
        this.titoloLink3GenericoInglese = titoloLink3GenericoInglese;
    }

    public void setTitoloLink3GenericoTedesco(String titoloLink3GenericoTedesco) {
        this.titoloLink3GenericoTedesco = titoloLink3GenericoTedesco;
    }

    public void setTitoloLink3GenericoFrancese(String titoloLink3GenericoFrancese) {
        this.titoloLink3GenericoFrancese = titoloLink3GenericoFrancese;
    }

    public void setLinkGenerico3(String linkGenerico3) {
        this.linkGenerico3 = linkGenerico3;
    }

    public void setTitoloLink4GenericoItaliano(String titoloLink4GenericoItaliano) {
        this.titoloLink4GenericoItaliano = titoloLink4GenericoItaliano;
    }

    public void setTitoloLink4GenericoInglese(String titoloLink4GenericoInglese) {
        this.titoloLink4GenericoInglese = titoloLink4GenericoInglese;
    }

    public void setTitoloLink4GenericoTedesco(String titoloLink4GenericoTedesco) {
        this.titoloLink4GenericoTedesco = titoloLink4GenericoTedesco;
    }

    public void setTitoloLink4GenericoFrancese(String titoloLink4GenericoFrancese) {
        this.titoloLink4GenericoFrancese = titoloLink4GenericoFrancese;
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
        dest.writeString(titoloTedesco);
        dest.writeString(titoloFrancese);
        dest.writeString(descrizioneItaliano);
        dest.writeString(descrizioneInglese);
        dest.writeString(descrizioneTedesco);
        dest.writeString(descrizioneFrancese);
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
        dest.writeString(titoloLink1GenericoItaliano);
        dest.writeString(titoloLink1GenericoInglese);
        dest.writeString(titoloLink1GenericoTedesco);
        dest.writeString(titoloLink1GenericoFrancese);
        dest.writeString(linkGenerico1);
        dest.writeString(titoloLink2GenericoItaliano);
        dest.writeString(titoloLink2GenericoInglese);
        dest.writeString(titoloLink2GenericoTedesco);
        dest.writeString(titoloLink2GenericoFrancese);
        dest.writeString(linkGenerico2);
        dest.writeString(titoloLink3GenericoItaliano);
        dest.writeString(titoloLink3GenericoInglese);
        dest.writeString(titoloLink3GenericoTedesco);
        dest.writeString(titoloLink3GenericoFrancese);
        dest.writeString(linkGenerico3);
        dest.writeString(titoloLink4GenericoItaliano);
        dest.writeString(titoloLink4GenericoInglese);
        dest.writeString(titoloLink4GenericoTedesco);
        dest.writeString(titoloLink4GenericoFrancese);
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
                "\ntitoloTedesco: " + titoloTedesco +
                "\ntitoloFrancese: " + titoloFrancese +
                "\ndescrizioneItaliano: " + descrizioneItaliano +
                "\ndescrizioneInglese: " + descrizioneInglese +
                "\ndescrizioneTedesco: " + descrizioneTedesco +
                "\ndescrizioneFrancese: " + descrizioneFrancese +
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
                "\ntitoloLink1GenericoItaliano: " + titoloLink1GenericoItaliano +
                "\ntitoloLink1GenericoInglese: " + titoloLink1GenericoInglese +
                "\ntitoloLink1GenericoTedesco: " + titoloLink1GenericoTedesco +
                "\ntitoloLink1GenericoFrancese: " + titoloLink1GenericoFrancese +
                "\nlinkGenerico1: " + linkGenerico1 +
                "\ntitoloLink2GenericoItaliano: " + titoloLink2GenericoItaliano +
                "\ntitoloLink2GenericoInglese: " + titoloLink2GenericoInglese +
                "\ntitoloLink2GenericoTedesco: " + titoloLink2GenericoTedesco +
                "\ntitoloLink2GenericoFrancese: " + titoloLink2GenericoFrancese +
                "\nlinkGenerico2: " + linkGenerico2 +
                "\ntitoloLink3GenericoItaliano: " + titoloLink3GenericoItaliano +
                "\ntitoloLink3GenericoInglese: " + titoloLink3GenericoInglese +
                "\ntitoloLink3GenericoTedesco: " + titoloLink3GenericoTedesco +
                "\ntitoloLink3GenericoFrancese: " + titoloLink3GenericoFrancese +
                "\nlinkGenerico3: " + linkGenerico3 +
                "\ntitoloLink4GenericoItaliano: " + titoloLink4GenericoItaliano +
                "\ntitoloLink4GenericoInglese: " + titoloLink4GenericoInglese +
                "\ntitoloLink4GenericoTedesco: " + titoloLink4GenericoTedesco +
                "\ntitoloLink4GenericoFrancese: " + titoloLink4GenericoFrancese +
                "\nlinkGenerico4: " + linkGenerico4 +
                "\nfileTracciaGps: " + fileTracciaGps +
                "\n\n";
    }
}
