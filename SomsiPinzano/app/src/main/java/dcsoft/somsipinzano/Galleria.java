package dcsoft.somsipinzano;

import android.content.SharedPreferences;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.veinhorn.scrollgalleryview.HackyViewPager;
import com.veinhorn.scrollgalleryview.MediaInfo;
import com.veinhorn.scrollgalleryview.ScrollGalleryView;
import com.veinhorn.scrollgalleryview.loader.DefaultImageLoader;

import java.util.ArrayList;

public class Galleria extends AppCompatActivity {
    private final String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_galleria);

        ScrollGalleryView scrollGalleryView = (ScrollGalleryView) findViewById(R.id.scroll_gallery_view);
        scrollGalleryView
                .setThumbnailSize(100)
                .setZoom(true)
                .hideThumbnails(true)
                .setFragmentManager(getSupportFragmentManager());

        ArrayList<ImmaginePdi> immaginiPdi = getIntent().getParcelableArrayListExtra("immaginiPdi");
        int indiceImmagine = getIntent().getExtras().getInt("indiceImmagine");

        int dimensione = immaginiPdi.size();

        for (int i = 0; i < dimensione; i++) {
            ImmaginePdi immaginePdi = immaginiPdi.get(i);

            String nomeFileSenzaEstensione = immaginePdi.getFileImmagine().substring(0, immaginePdi.getFileImmagine().lastIndexOf('.'));
            String packageName = getApplicationContext().getPackageName();

            scrollGalleryView.addMedia(MediaInfo.mediaLoader(new DefaultImageLoader(getApplicationContext().getResources().getIdentifier(nomeFileSenzaEstensione, "drawable", packageName))));
        }

        scrollGalleryView.setCurrentItem(indiceImmagine);
    }

    @Override
    public void onBackPressed() {
        ViewPager viewPager = (HackyViewPager) findViewById(com.veinhorn.scrollgalleryview.R.id.viewPager);

        SharedPreferences sharedPreferences = getSharedPreferences("Galleria", 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("indiceImmagine", "" + viewPager.getCurrentItem());
        editor.apply();

        super.onBackPressed();
    }
}
