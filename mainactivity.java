
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.google.android.ads.nativetemplates.NativeTemplateStyle;
import com.google.android.ads.nativetemplates.TemplateView;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
public class catago extends AppCompatActivity {
    private InterstitialAd mInterstitialAd;
    AdView adView;
    RelativeLayout banner;
    DatabaseReference databaseads;
    private static final String AD_UNIT_ID = "ca-app-pub-3940256099942544/2247696110";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catago);

        MobileAds.initialize(this);

        banner = findViewById(R.id.banner);
        databaseads= FirebaseDatabase.getInstance().getReference().child("ADS");
        databaseads.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String BannerID = dataSnapshot.child("banner_id").getValue().toString();
                String InterstitialID = dataSnapshot.child("interstitial_id").getValue().toString();
                        loadbanner(BannerID);


                mInterstitialAd = new InterstitialAd(catago.this);
                mInterstitialAd.setAdUnitId(InterstitialID);
                mInterstitialAd.loadAd(new AdRequest.Builder().build());


            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        LoadNativeAds();
    }


    public void loadinter(){

        if (mInterstitialAd.isLoaded()){
            mInterstitialAd.show();
        }
    }


    public void loadbanner(String BannerID){

        adView = new AdView(this);
        adView.setAdSize(AdSize.BANNER);
        adView.setAdUnitId(BannerID);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
        adView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
                banner.addView(adView);
            }

            @Override
            public void onAdFailedToLoad(LoadAdError adError) {
                // Code to be executed when an ad request fails.
            }



        });

    }
        public void pdf(View view) {
            switch (view.getId()) {
                case R.id.book1:
                    Intent intent = new Intent(catago.this, listbook1.class);
                    startActivity(intent);
                    loadinter();
        }
        }

    public void pdf2(View view) {
        switch (view.getId()) {
            case R.id.book2:
                Intent intent = new Intent(catago.this, listbook2.class);
                startActivity(intent);
                loadinter();
        }


    }

    public void pdf3(View view) {
        switch (view.getId()) {
            case R.id.book3:
                Intent intent = new Intent(catago.this, listbook3.class);
                startActivity(intent);
                loadinter();
        }
    }

    public void more(View view) {
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://search?q=pub%3AAtlas%20&c=apps")));
        } catch (ActivityNotFoundException e)
        {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/search?q=pub%3AAtlas%20&c=apps")));
        }
    }

    void   LoadNativeAds(){
        final    ColorDrawable background = new ColorDrawable(0xFFFFFFFF);

        AdLoader adLoader = new AdLoader.Builder(this, AD_UNIT_ID)
                .forUnifiedNativeAd(new UnifiedNativeAd.OnUnifiedNativeAdLoadedListener() {
                    @Override
                    public void onUnifiedNativeAdLoaded(UnifiedNativeAd unifiedNativeAd) {
                        NativeTemplateStyle styles = new
                                NativeTemplateStyle.Builder().withMainBackgroundColor(background).build();
                        TemplateView template = findViewById(R.id.my_template);
                        template.setStyles(styles);
                        template.setNativeAd(unifiedNativeAd);
                    }
                })
                .build();

        adLoader.loadAd(new AdRequest.Builder().build());
    }


