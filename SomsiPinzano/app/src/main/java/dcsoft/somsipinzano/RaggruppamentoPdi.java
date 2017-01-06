package dcsoft.somsipinzano;

import android.os.Parcel;
import android.os.Parcelable;

public class RaggruppamentoPdi implements Parcelable {
    private int idRaggruppamentoPdi;
    private int ordinamento;
    private String nomeRaggruppamentoItaliano;
    private String nomeRaggruppamentoInglese;

    public RaggruppamentoPdi(int idRaggruppamentoPdi, int ordinamento, String nomeRaggruppamentoItaliano, String nomeRaggruppamentoInglese) {
        super();

        this.idRaggruppamentoPdi        = idRaggruppamentoPdi;
        this.ordinamento                = ordinamento;
        this.nomeRaggruppamentoItaliano = nomeRaggruppamentoItaliano;
        this.nomeRaggruppamentoInglese  = nomeRaggruppamentoInglese;
    }

    public RaggruppamentoPdi(Parcel parcel) {
        super();

        this.idRaggruppamentoPdi        = parcel.readInt();
        this.ordinamento                = parcel.readInt();
        this.nomeRaggruppamentoItaliano = parcel.readString();
        this.nomeRaggruppamentoInglese  = parcel.readString();
    }

    public int getIdRaggruppamentoPdi() {
        return idRaggruppamentoPdi;
    }

    public int getOrdinamento() {
        return ordinamento;
    }

    public String getNomeRaggruppamentoItaliano() {
        return nomeRaggruppamentoItaliano;
    }

    public String getNomeRaggruppamentoInglese() {
        return nomeRaggruppamentoInglese;
    }

    public void setIdRaggruppamentoPdi(int idRaggruppamentoPdi) {
        this.idRaggruppamentoPdi = idRaggruppamentoPdi;
    }
    public void setOrdinamento(int ordinamento) {
        this.ordinamento = ordinamento;
    }

    public void setNomeRaggruppamentoItaliano(String nomeRaggruppamentoItaliano) {
        this.nomeRaggruppamentoItaliano = nomeRaggruppamentoItaliano;
    }

    public void setNomeRaggruppamentoInglese(String nomeRaggruppamentoInglese) {
        this.nomeRaggruppamentoInglese = nomeRaggruppamentoInglese;
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

    @Override
    public String toString() {
        return "\n" +
                "\nidRaggruppamentoPdi: " + idRaggruppamentoPdi +
                "\nordinamento: " + ordinamento +
                "\nnomeRaggruppamentoItaliano: " + nomeRaggruppamentoItaliano +
                "\nnomeRaggruppamentoInglese: " + nomeRaggruppamentoInglese +
                "\n\n";
    }
}
