package androidlogic.practice;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import com.funnyfractions.game.R;
import androidlogic.games.archery_game.MainMenu;
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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.practica);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        mViewPager = findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        if(getIntent() != null){
            numFragment = getIntent().getIntExtra("numFragment",0);
            mViewPager.setCurrentItem(numFragment);
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.zoom_back_in, R.anim.zoom_back_out);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public static class PlaceholderFragment extends Fragment {
        public static Fragment newInstance(int sectionNumber, PracticeAndFragments practiceAndFragments) {
            Fragment fragment = null;
            Bundle bundle;
            switch (sectionNumber){
                case 1:
                    fragment = new MainMenu();
                    break;
                case 2:
                    fragment = new EjecutableBateria();
                    break;
                case 3:
                    fragment = new MenuMultiplicacion();
                    break;
                case 4:
                    fragment = new Menu();
            }

            return fragment;
        }
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }
        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance((position + 1), practiceAndFragments);
        }
        @Override
        public int getCount() {
            // Show 3 total pages.
            return 4;
        }
    }
}