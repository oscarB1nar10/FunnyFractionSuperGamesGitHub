package androidlogic.practice;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import com.funnyfractions.game.R;
import androidlogic.games.archery_game.MainMenu;
import androidlogic.home.Home;
import bateria.EjecutableBateria;
import burbujas.MenuMultiplicacion;
import gotas.Menu;
import interfaces.PracticeAndFragments;

public class Practica extends AppCompatActivity implements
                                                MainMenu.OnFragmentInteractionListener,
                                                EjecutableBateria.OnFragmentInteractionListener,
                                                MenuMultiplicacion.OnFragmentInteractionListener,
                                                Menu.OnFragmentInteractionListener{

    //widgets
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;

    //vars
    PracticeAndFragments practiceAndFragments;
    private int numFragment;
    boolean showOperation;
    Bundle bundle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.practica);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        mViewPager = findViewById(R.id.container_vp);
        mViewPager.setAdapter(mSectionsPagerAdapter);


        if(getIntent() != null){
            numFragment = getIntent().getIntExtra("numFragment",0);
            if(numFragment == 4){
                showOperation = getIntent().getBooleanExtra("showOperation",false);
            }
            mViewPager.setCurrentItem(numFragment);
        }

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Practica.this, Home.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        overridePendingTransition(R.anim.zoom_back_in, R.anim.zoom_back_out);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }
        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            switch ((position+1)){
                case 1:
                    fragment = new PracticeCover();
                    break;
                case 2:
                    fragment = new MainMenu();
                    break;
                case 3:
                    fragment = new EjecutableBateria();
                    break;
                case 4:
                    fragment = new MenuMultiplicacion();
                    break;
                case 5:
                    bundle = new Bundle();
                    bundle.putBoolean("showOperation",showOperation);
                    fragment = new Menu();
                    fragment.setArguments(bundle);
                    break;
            }
            return fragment;
        }
        @Override
        public int getCount() {
            // Show 3 total pages.
            return 5;
        }
    }
}