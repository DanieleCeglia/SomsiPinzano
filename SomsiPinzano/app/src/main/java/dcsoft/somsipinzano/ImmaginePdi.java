package dcsoft.somsipinzano;

import android.os.Parcel;
import android.os.Parcelable;

public class ImmaginePdi implements Parcelable {
    private int idImmaginePdi;
    private int idImmaginePdi_idPdi;
    private int ordinamento;
    private String url;

    public ImmaginePdi(int idImmaginePdi, int idImmaginePdi_idPdi, int ordinamento, String url) {
        super();

        this.idImmaginePdi       = idImmaginePdi;
        this.idImmaginePdi_idPdi = idImmaginePdi_idPdi;
        this.ordinamento         = ordinamento;
        this.url                 = url;
    }

    public ImmaginePdi(Parcel parcel) {
        super();

        this.idImmaginePdi       = parcel.readInt();
        this.idImmaginePdi_idPdi = parcel.readInt();
        this.ordinamento         = parcel.readInt();
        this.url                 = parcel.readString();
    }

    public int getIdImmaginePdi() {
        return idImmaginePdi;
    }

    public int getIdImmaginePdi_idPdi() {
        return idImmaginePdi_idPdi;
    }

    public int getOrdinamento() {
        return ordinamento;
    }

    public String getUrl() {
        return url;
    }

    public void setIdImmaginePdi(int idImmaginePdi) {
        this.idImmaginePdi = idImmaginePdi;
    }

    public void setIdImmaginePdi_idPdi(int idImmaginePdi_idPdi) {
        this.idImmaginePdi_idPdi = idImmaginePdi_idPdi;
    }

    public void setOrdinamento(int ordinamento) {
        this.ordinamento = ordinamento;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(idImmaginePdi);
        dest.writeInt(idImmaginePdi_idPdi);
        dest.writeInt(ordinamento);
        dest.writeString(url);
    }

    public final static Parcelable.Creator CREATOR = new Parcelable.Creator() {
        @Override
        public ImmaginePdi createFromParcel(Parcel source) {
            return new ImmaginePdi(source);
        }

        @Override
        public ImmaginePdi[] newArray(int size) {
            return new ImmaginePdi[size];
        }
    };

    @Override
    public String toString() {
        return "\n" +
                "\nidImmaginePdi: " + idImmaginePdi +
                "\nidImmaginePdi: " + idImmaginePdi +
                "\nidImmaginePdi_idPdi: " + idImmaginePdi_idPdi +
                "\nordinamento: " + ordinamento +
                "\nurl: " + url +
                "\n\n";
    }
}
