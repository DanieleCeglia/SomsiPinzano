package dcsoft.somsipinzano;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.veinhorn.scrollgalleryview.MediaInfo;
import com.veinhorn.scrollgalleryview.ScrollGalleryView;
import com.veinhorn.scrollgalleryview.loader.DefaultImageLoader;

import java.util.ArrayList;

public class Galleria extends AppCompatActivity {
    private final String TAG = getClass().getSimpleName();
    private ScrollGalleryView scrollGalleryView;
    private ArrayList<ImmaginePdi> immaginiPdi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_galleria);

        scrollGalleryView = (ScrollGalleryView) findViewById(R.id.scroll_gallery_view);
        scrollGalleryView
                .setThumbnailSize(100)
                .setZoom(true)
                .setFragmentManager(getSupportFragmentManager());

        immaginiPdi = getIntent().getParcelableArrayListExtra("immaginiPdi");
        int dimensione = immaginiPdi.size();

        for (int i = 0; i < dimensione; i++) {
            ImmaginePdi immaginePdi = immaginiPdi.get(i);

            String nomeFileSenzaEstensione = immaginePdi.getFileImmagine().substring(0, immaginePdi.getFileImmagine().lastIndexOf('.'));
            String packageName = getApplicationContext().getPackageName();

            scrollGalleryView.addMedia(MediaInfo.mediaLoader(new DefaultImageLoader(getApplicationContext().getResources().getIdentifier(nomeFileSenzaEstensione, "drawable", packageName))));
        }
    }

    private Bitmap toBitmap(int image) {
        return ((BitmapDrawable) getResources().getDrawable(image)).getBitmap();
    }
}
