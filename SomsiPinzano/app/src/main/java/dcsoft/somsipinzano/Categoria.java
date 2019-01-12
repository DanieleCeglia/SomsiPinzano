package dcsoft.somsipinzano;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Categoria implements Parcelable {
    private Integer idCategoria;
    private Integer ordinamento;
    private String  nomeItaliano;
    private String  nomeInglese;
    private String  nomeTedesco;
    private String  nomeFrancese;
    private String  descrizioneItaliano;
    private String  descrizioneInglese;
    private String  descrizioneTedesco;
    private String  descrizioneFrancese;
    private String  fileImmagine;
    private String  fileImmagineCopertina;
    private String  filePin;

    public Categoria(Integer idCategoria, Integer ordinamento, String nomeItaliano, String nomeInglese, String nomeTedesco, String nomeFrancese, String descrizioneItaliano, String descrizioneInglese, String descrizioneTedesco, String descrizioneFrancese, String fileImmagine, String fileImmagineCopertina, String filePin) {
        super();

        this.idCategoria           = idCategoria;
        this.ordinamento           = ordinamento;
        this.nomeItaliano          = nomeItaliano;
        this.nomeInglese           = nomeInglese;
        this.nomeTedesco           = nomeTedesco;
        this.nomeFrancese          = nomeFrancese;
        this.descrizioneItaliano   = descrizioneItaliano;
        this.descrizioneInglese    = descrizioneInglese;
        this.descrizioneTedesco    = descrizioneTedesco;
        this.descrizioneFrancese   = descrizioneFrancese;
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
        this.nomeTedesco           = parcel.readString();
        this.nomeFrancese          = parcel.readString();
        this.descrizioneItaliano   = parcel.readString();
        this.descrizioneInglese    = parcel.readString();
        this.descrizioneTedesco    = parcel.readString();
        this.descrizioneFrancese   = parcel.readString();
        this.fileImmagine          = parcel.readString();
        this.fileImmagineCopertina = parcel.readString();
        this.filePin               = parcel.readString();
    }

    public Integer getIdCategoria() {
        return idCategoria;
    }

    public Integer getOrdinamento() {
        return ordinamento;
    }

    public String getNomeItaliano() {
        return nomeItaliano;
    }

    public String getNomeInglese() {
        return nomeInglese;
    }

    public String getNomeTedesco() {
        return nomeTedesco;
    }

    public String getNomeFrancese() {
        return nomeFrancese;
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

    public String getFileImmagine() {
        return fileImmagine;
    }

    public String getFileImmagineCopertina() {
        return fileImmagineCopertina;
    }

    public String getFilePin() {
        return filePin;
    }

    public void setIdCategoria(Integer idCategoria) {
        this.idCategoria = idCategoria;
    }

    public void setOrdinamento(Integer ordinamento) {
        this.ordinamento = ordinamento;
    }

    public void setNomeItaliano(String nomeItaliano) {
        this.nomeItaliano = nomeItaliano;
    }

    public void setNomeInglese(String nomeInglese) {
        this.nomeInglese = nomeInglese;
    }

    public void setNomeTedesco(String nomeTedesco) {
        this.nomeTedesco = nomeTedesco;
    }

    public void setNomeFrancese(String nomeFrancese) {
        this.nomeFrancese = nomeFrancese;
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
        dest.writeInt(idCategoria == null ? -1 : idCategoria);
        dest.writeInt(ordinamento == null ? -1 : ordinamento);
        dest.writeString(nomeItaliano);
        dest.writeString(nomeInglese);
        dest.writeString(nomeTedesco);
        dest.writeString(nomeFrancese);
        dest.writeString(descrizioneItaliano);
        dest.writeString(descrizioneInglese);
        dest.writeString(descrizioneTedesco);
        dest.writeString(descrizioneFrancese);
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

    @NonNull
    @Override
    public String toString() {
        return "\n" +
                "\nidCategoria: " + idCategoria +
                "\nordinamento: " + ordinamento +
                "\nnomeItaliano: " + nomeItaliano +
                "\nnomeInglese: " + nomeInglese +
                "\nnomeTedesco: " + nomeTedesco +
                "\nnomeFrancese: " + nomeFrancese +
                "\ndescrizioneItaliano: " + descrizioneItaliano +
                "\ndescrizioneInglese: " + descrizioneInglese +
                "\ndescrizioneTedesco: " + descrizioneTedesco +
                "\ndescrizioneFrancese: " + descrizioneFrancese +
                "\nfileImmagine: " + fileImmagine +
                "\nfileImmagineCopertina: " + fileImmagineCopertina +
                "\nfilePin: " + filePin +
                "\n\n";
    }
}
