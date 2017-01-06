package dcsoft.somsipinzano;

import android.os.Parcel;
import android.os.Parcelable;

public class RagruppamentoPdi implements Parcelable {
    private int idRaggruppamentoPdi;
    private int ordinamento;
    private String nomeRagruppamentoItaliano;
    private String nomeRagruppamentoInglese;

    public RagruppamentoPdi(int idRaggruppamentoPdi, int ordinamento, String nomeRagruppamentoItaliano, String nomeRagruppamentoInglese) {
        super();

        this.idRaggruppamentoPdi       = idRaggruppamentoPdi;
        this.ordinamento               = ordinamento;
        this.nomeRagruppamentoItaliano = nomeRagruppamentoItaliano;
        this.nomeRagruppamentoInglese  = nomeRagruppamentoInglese;
    }

    public RagruppamentoPdi(Parcel parcel) {
        super();

        this.idRaggruppamentoPdi       = parcel.readInt();
        this.ordinamento               = parcel.readInt();
        this.nomeRagruppamentoItaliano = parcel.readString();
        this.nomeRagruppamentoInglese  = parcel.readString();
    }

    public int getIdRaggruppamentoPdi() {
        return idRaggruppamentoPdi;
    }

    public int getOrdinamento() {
        return ordinamento;
    }

    public String getNomeRagruppamentoItaliano() {
        return nomeRagruppamentoItaliano;
    }

    public String getNomeRagruppamentoInglese() {
        return nomeRagruppamentoInglese;
    }

    public void setIdRaggruppamentoPdi(int idRaggruppamentoPdi) {
        this.idRaggruppamentoPdi = idRaggruppamentoPdi;
    }
    public void setOrdinamento(int ordinamento) {
        this.ordinamento = ordinamento;
    }

    public void setNomeRagruppamentoItaliano(String nomeRagruppamentoItaliano) {
        this.nomeRagruppamentoItaliano = nomeRagruppamentoItaliano;
    }

    public void setNomeRagruppamentoInglese(String nomeRagruppamentoInglese) {
        this.nomeRagruppamentoInglese = nomeRagruppamentoInglese;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(idRaggruppamentoPdi);
        dest.writeInt(ordinamento);
        dest.writeString(nomeRagruppamentoItaliano);
        dest.writeString(nomeRagruppamentoInglese);
    }

    public final static Parcelable.Creator CREATOR = new Parcelable.Creator() {
        @Override
        public RagruppamentoPdi createFromParcel(Parcel source) {
            return new RagruppamentoPdi(source);
        }

        @Override
        public RagruppamentoPdi[] newArray(int size) {
            return new RagruppamentoPdi[size];
        }
    };

    @Override
    public String toString() {
        return "\n" +
                "\nidRaggruppamentoPdi: " + idRaggruppamentoPdi +
                "\nordinamento: " + ordinamento +
                "\nnomeRagruppamentoItaliano: " + nomeRagruppamentoItaliano +
                "\nnomeRagruppamentoInglese: " + nomeRagruppamentoInglese +
                "\n\n";
    }
}
