package dcsoft.somsipinzano;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class RaggruppamentoPdi implements Parcelable {
    private Integer idRaggruppamentoPdi;
    private Integer ordinamento;
    private String  nomeRaggruppamentoItaliano;
    private String  nomeRaggruppamentoInglese;
    private String  nomeRaggruppamentoTedesco;
    private String  nomeRaggruppamentoFrancese;

    public RaggruppamentoPdi(Integer idRaggruppamentoPdi, Integer ordinamento, String nomeRaggruppamentoItaliano, String nomeRaggruppamentoInglese, String nomeRaggruppamentoTedesco, String nomeRaggruppamentoFrancese) {
        super();

        this.idRaggruppamentoPdi        = idRaggruppamentoPdi;
        this.ordinamento                = ordinamento;
        this.nomeRaggruppamentoItaliano = nomeRaggruppamentoItaliano;
        this.nomeRaggruppamentoInglese  = nomeRaggruppamentoInglese;
        this.nomeRaggruppamentoTedesco  = nomeRaggruppamentoTedesco;
        this.nomeRaggruppamentoFrancese = nomeRaggruppamentoFrancese;
    }

    public RaggruppamentoPdi(Parcel parcel) {
        super();

        this.idRaggruppamentoPdi        = parcel.readInt();
        this.ordinamento                = parcel.readInt();
        this.nomeRaggruppamentoItaliano = parcel.readString();
        this.nomeRaggruppamentoInglese  = parcel.readString();
        this.nomeRaggruppamentoTedesco  = parcel.readString();
        this.nomeRaggruppamentoFrancese = parcel.readString();
    }

    public Integer getIdRaggruppamentoPdi() {
        return idRaggruppamentoPdi;
    }

    public Integer getOrdinamento() {
        return ordinamento;
    }

    public String getNomeRaggruppamentoItaliano() {
        return nomeRaggruppamentoItaliano;
    }

    public String getNomeRaggruppamentoInglese() {
        return nomeRaggruppamentoInglese;
    }

    public String getNomeRaggruppamentoTedesco() {
        return nomeRaggruppamentoTedesco;
    }

    public String getNomeRaggruppamentoFrancese() {
        return nomeRaggruppamentoFrancese;
    }

    public void setIdRaggruppamentoPdi(Integer idRaggruppamentoPdi) {
        this.idRaggruppamentoPdi = idRaggruppamentoPdi;
    }
    public void setOrdinamento(Integer ordinamento) {
        this.ordinamento = ordinamento;
    }

    public void setNomeRaggruppamentoItaliano(String nomeRaggruppamentoItaliano) {
        this.nomeRaggruppamentoItaliano = nomeRaggruppamentoItaliano;
    }

    public void setNomeRaggruppamentoInglese(String nomeRaggruppamentoInglese) {
        this.nomeRaggruppamentoInglese = nomeRaggruppamentoInglese;
    }

    public void setNomeRaggruppamentoTedesco(String nomeRaggruppamentoTedesco) {
        this.nomeRaggruppamentoTedesco = nomeRaggruppamentoTedesco;
    }

    public void setNomeRaggruppamentoFrancese(String nomeRaggruppamentoFrancese) {
        this.nomeRaggruppamentoFrancese = nomeRaggruppamentoFrancese;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(idRaggruppamentoPdi);
        dest.writeInt(ordinamento);
        dest.writeString(nomeRaggruppamentoItaliano);
        dest.writeString(nomeRaggruppamentoInglese);
        dest.writeString(nomeRaggruppamentoTedesco);
        dest.writeString(nomeRaggruppamentoFrancese);
    }

    public final static Parcelable.Creator CREATOR = new Parcelable.Creator() {
        @Override
        public RaggruppamentoPdi createFromParcel(Parcel source) {
            return new RaggruppamentoPdi(source);
        }

        @Override
        public RaggruppamentoPdi[] newArray(int size) {
            return new RaggruppamentoPdi[size];
        }
    };

    @NonNull
    @Override
    public String toString() {
        return "\n" +
                "\nidRaggruppamentoPdi: " + idRaggruppamentoPdi +
                "\nordinamento: " + ordinamento +
                "\nnomeRaggruppamentoItaliano: " + nomeRaggruppamentoItaliano +
                "\nnomeRaggruppamentoInglese: " + nomeRaggruppamentoInglese +
                "\nnomeRaggruppamentoTedesco: " + nomeRaggruppamentoTedesco +
                "\nnomeRaggruppamentoFrancese: " + nomeRaggruppamentoFrancese +
                "\n\n";
    }
}
