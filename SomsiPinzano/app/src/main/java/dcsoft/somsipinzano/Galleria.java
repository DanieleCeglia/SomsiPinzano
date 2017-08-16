package dcsoft.somsipinzano;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Galleria extends AppCompatActivity {
    private final String TAG = getClass().getSimpleName();
    private ArrayList<ImmaginePdi> immaginiPdi;

    @BindView(R.id.pager) ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_galleria);
        ButterKnife.bind(this);

        immaginiPdi = getIntent().getParcelableArrayListExtra("immaginiPdi");
        int indiceImmagine = getIntent().getExtras().getInt("indiceImmagine");

        GalleriaPagerAdapter galleriaPagerAdapter = new GalleriaPagerAdapter(this);

        viewPager.setAdapter(galleriaPagerAdapter);
        viewPager.setOffscreenPageLimit(5);
        viewPager.setCurrentItem(indiceImmagine);
    }

    @Override
    public void onBackPressed() {
        SharedPreferences sharedPreferences = getSharedPreferences("Galleria", 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("indiceImmagine", "" + viewPager.getCurrentItem());
        editor.apply();

        super.onBackPressed();
    }

    class GalleriaPagerAdapter extends PagerAdapter {
        Context contesto;
        LayoutInflater layoutInflater;

        GalleriaPagerAdapter(Context context) {
            contesto = context;
            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return immaginiPdi.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            View itemView = layoutInflater.inflate(R.layout.pagine_galleria, container, false);
            container.addView(itemView);

            final ImageView immagineGalleria = (ImageView) itemView.findViewById(R.id.immagineGalleria);
            final SubsamplingScaleImageView immagineRidimensionabileGalleria = (SubsamplingScaleImageView) itemView.findViewById(R.id.immagineRidimensionabileGalleria);

            Glide.with(contesto)
                    .load(immaginiPdi.get(position).getUrl())
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            immagineRidimensionabileGalleria.setImage(ImageSource.bitmap(((BitmapDrawable)resource).getBitmap()));
                            immagineGalleria.setVisibility(View.GONE);

                            return false;
                        }
                    }).into(immagineGalleria);

            return itemView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((LinearLayout) object);
        }
    }
}
