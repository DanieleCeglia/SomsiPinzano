package dcsoft.somsipinzano;

import android.os.Parcel;
import android.os.Parcelable;

public class Categoria implements Parcelable {
    private int idCategoria;
    private String nomeItaliano;
    private String nomeInglese;
    private String descrizioneItaliano;
    private String descrizioneInglese;
    private String fileImmagine;
    private String coloreEsadecimale;

    public Categoria(int idCategoria, String nomeItaliano, String nomeInglese, String descrizioneItaliano, String descrizioneInglese, String fileImmagine, String coloreEsadecimale) {
        super();

        this.idCategoria         = idCategoria;
        this.nomeItaliano        = nomeItaliano;
        this.nomeInglese         = nomeInglese;
        this.descrizioneItaliano = descrizioneItaliano;
        this.descrizioneInglese  = descrizioneInglese;
        this.fileImmagine        = fileImmagine;
        this.coloreEsadecimale   = coloreEsadecimale;
    }

    public Categoria(Parcel parcel) {
        super();

        this.idCategoria         = parcel.readInt();
        this.nomeItaliano        = parcel.readString();
        this.nomeInglese         = parcel.readString();
        this.descrizioneItaliano = parcel.readString();
        this.descrizioneInglese  = parcel.readString();
        this.fileImmagine        = parcel.readString();
        this.coloreEsadecimale   = parcel.readString();
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public String getNomeItaliano() {
        return nomeItaliano;
    }

    public String getNomeInglese() {
        return nomeInglese;
    }

    public String getDescrizioneItaliano() {
        return descrizioneItaliano;
    }

    public String getDescrizioneInglese() {
        return descrizioneInglese;
    }

    public String getFileImmagine() {
        return fileImmagine;
    }

    public String getColoreEsadecimale() {
        return coloreEsadecimale;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public void setNomeItaliano(String nomeItaliano) {
        this.nomeItaliano = nomeItaliano;
    }

    public void setNomeInglese(String nomeInglese) {
        this.nomeInglese = nomeInglese;
    }

    public void setFileImmagine(String fileImmagine) {
        this.fileImmagine = fileImmagine;
    }

    public void setColoreEsadecimale(String coloreEsadecimale) {
        this.coloreEsadecimale = coloreEsadecimale;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(idCategoria);
        dest.writeString(nomeItaliano);
        dest.writeString(nomeInglese);
        dest.writeString(descrizioneItaliano);
        dest.writeString(descrizioneInglese);
        dest.writeString(fileImmagine);
        dest.writeString(coloreEsadecimale);
    }

    public final static Parcelable.Creator CREATOR = new Parcelable.Creator() {
        @Override
        public Categoria createFromParcel(Parcel source) {
            return new Categoria(source);
        }

        @Override
        public Categoria[] newArray(int size) {
            return new Categoria[size];
        }
    };

    @Override
    public String toString() {
        return "\n" +
                "\nidCategoria: " + idCategoria +
                "\nnomeItaliano: " + nomeItaliano +
                "\nnomeInglese: " + nomeInglese +
                "\ndescrizioneItaliano: " + descrizioneItaliano +
                "\ndescrizioneInglese: " + descrizioneInglese +
                "\nfileImmagine: " + fileImmagine +
                "\ncoloreEsadecimale: " + coloreEsadecimale +
                "\n\n";
    }
}
