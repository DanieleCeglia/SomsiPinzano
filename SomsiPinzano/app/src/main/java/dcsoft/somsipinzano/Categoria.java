package dcsoft.somsipinzano;

import android.os.Parcel;
import android.os.Parcelable;

public class Categoria implements Parcelable {
    private int idCategoria;
    private int ordinamento;
    private String nomeItaliano;
    private String nomeInglese;
    private String descrizioneItaliano;
    private String descrizioneInglese;
    private String fileImmagine;
    private String fileImmagineCopertina;
    private String filePin;

    public Categoria(int idCategoria, int ordinamento, String nomeItaliano, String nomeInglese, String descrizioneItaliano, String descrizioneInglese, String fileImmagine, String fileImmagineCopertina, String filePin) {
        super();

        this.idCategoria           = idCategoria;
        this.ordinamento           = ordinamento;
        this.nomeItaliano          = nomeItaliano;
        this.nomeInglese           = nomeInglese;
        this.descrizioneItaliano   = descrizioneItaliano;
        this.descrizioneInglese    = descrizioneInglese;
        this.fileImmagine          = fileImmagine;
        this.fileImmagineCopertina = fileImmagineCopertina;
        this.filePin               = filePin;
    }

    public Categoria(Parcel parcel) {
        super();

        this.idCategoria           = parcel.readInt();
        this.ordinamento           = parcel.readInt();
        this.nomeItaliano          = parcel.readString();
        this.nomeInglese           = parcel.readString();
        this.descrizioneItaliano   = parcel.readString();
        this.descrizioneInglese    = parcel.readString();
        this.fileImmagine          = parcel.readString();
        this.fileImmagineCopertina = parcel.readString();
        this.filePin               = parcel.readString();
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public int getOrdinamento() {
        return ordinamento;
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

    public String getFileImmagineCopertina() {
        return fileImmagineCopertina;
    }

    public String getFilePin() {
        return filePin;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public void setOrdinamento(int ordinamento) {
        this.ordinamento = ordinamento;
    }

    public void setNomeItaliano(String nomeItaliano) {
        this.nomeItaliano = nomeItaliano;
    }

    public void setNomeInglese(String nomeInglese) {
        this.nomeInglese = nomeInglese;
    }

    public void setDescrizioneItaliano(String nomeItaliano) {
        this.descrizioneItaliano = descrizioneItaliano;
    }

    public void setDescrizioneInglese(String nomeInglese) {
        this.descrizioneInglese = descrizioneInglese;
    }

    public void setFileImmagine(String fileImmagine) {
        this.fileImmagine = fileImmagine;
    }

    public void setFileImmagineCopertina(String fileImmagineCopertina) {
        this.fileImmagineCopertina = fileImmagineCopertina;
    }

    public void setFilePin(String filePin) {
        this.filePin = filePin;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(idCategoria);
        dest.writeInt(ordinamento);
        dest.writeString(nomeItaliano);
        dest.writeString(nomeInglese);
        dest.writeString(descrizioneItaliano);
        dest.writeString(descrizioneInglese);
        dest.writeString(fileImmagine);
        dest.writeString(fileImmagineCopertina);
        dest.writeString(filePin);
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
                "\nordinamento: " + ordinamento +
                "\nnomeItaliano: " + nomeItaliano +
                "\nnomeInglese: " + nomeInglese +
                "\ndescrizioneItaliano: " + descrizioneItaliano +
                "\ndescrizioneInglese: " + descrizioneInglese +
                "\nfileImmagine: " + fileImmagine +
                "\nfileImmagineCopertina: " + fileImmagineCopertina +
                "\nfilePin: " + filePin +
                "\n\n";
    }
}
