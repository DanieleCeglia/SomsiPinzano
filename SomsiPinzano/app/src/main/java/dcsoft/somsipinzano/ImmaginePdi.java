package dcsoft.somsipinzano;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class ImmaginePdi implements Parcelable {
    private Integer idImmaginePdi;
    private Integer idImmaginePdi_idPdi;
    private Integer ordinamento;
    private String  url;

    public ImmaginePdi(Integer idImmaginePdi, Integer idImmaginePdi_idPdi, Integer ordinamento, String url) {
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

    public Integer getIdImmaginePdi() {
        return idImmaginePdi;
    }

    public Integer getIdImmaginePdi_idPdi() {
        return idImmaginePdi_idPdi;
    }

    public Integer getOrdinamento() {
        return ordinamento;
    }

    public String getUrl() {
        return url;
    }

    public void setIdImmaginePdi(Integer idImmaginePdi) {
        this.idImmaginePdi = idImmaginePdi;
    }

    public void setIdImmaginePdi_idPdi(Integer idImmaginePdi_idPdi) {
        this.idImmaginePdi_idPdi = idImmaginePdi_idPdi;
    }

    public void setOrdinamento(Integer ordinamento) {
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
        dest.writeInt(idImmaginePdi       == null ? -1 : idImmaginePdi);
        dest.writeInt(idImmaginePdi_idPdi == null ? -1 : idImmaginePdi_idPdi);
        dest.writeInt(ordinamento         == null ? -1 : ordinamento);
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

    @NonNull
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
