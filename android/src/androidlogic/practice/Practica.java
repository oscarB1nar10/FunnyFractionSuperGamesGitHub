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

        mViewPager = findViewById(R.id.container_vp);
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

    /*@SuppressLint("ValidFragment")
    public  class PlaceholderFragment extends Fragment {


        public  Fragment newInstance(int sectionNumber, PracticeAndFragments practiceAndFragments) {

            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

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

            // Replace whatever is in the fragment_container view with this fragment,
            // and add the transaction to the back stack
            fragmentTransaction.replace(R.id.container_vp, fragment);
            fragmentTransaction.addToBackStack(null);

            // Commit the transaction
            fragmentTransaction.commit();

            return fragment;
        }
    }*/

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }
        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            switch ((position+1)){
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
                    break;
            }


            return fragment;
        }
        @Override
        public int getCount() {
            // Show 3 total pages.
            return 4;
        }
    }
}