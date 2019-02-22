package androidlogic.tutorials;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.funnyfractions.game.R;

import java.util.ArrayList;

import Util.SumaTutorialInformation;
import adapters.MyAdapter;

public class TutorialQueSon extends AppCompatActivity {
    //const
    private static final String TAG = "Tutorial";
    private ArrayList<SumaTutorialInformation> sumaTutorialInformationsList;

    //widget
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tutoriales_queson);

        sumaTutorialInformationsList = new ArrayList<>();

        fillTheCards();

        mRecyclerView = findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);

        //adapter to use
        mAdapter = new MyAdapter(sumaTutorialInformationsList, this);
        mRecyclerView.setAdapter(mAdapter);
    }

    /*
        Here we add the information necessary to fill the cards
     */

    private void fillTheCards(){

        SumaTutorialInformation sumaTutorialInformation0 = new SumaTutorialInformation();
        sumaTutorialInformationsList.add(sumaTutorialInformation0);

        SumaTutorialInformation sumaTutorialInformation1 = new SumaTutorialInformation();

        sumaTutorialInformation1.setTitle(getString(R.string.txtQueson));
        sumaTutorialInformation1.setInformation(getString(R.string.txtdefinicion));
        sumaTutorialInformation1.setMainImage(R.drawable.interrogacion);
        sumaTutorialInformation1.setOptionalImg1(R.drawable.def_fractions);

        sumaTutorialInformationsList.add(sumaTutorialInformation1);

        SumaTutorialInformation sumaTutorialInformation2 = new SumaTutorialInformation();

        sumaTutorialInformation2.setTitle(getString(R.string.txtsumhomo));
        sumaTutorialInformation2.setInformation(getString(R.string.txteoriasumhomo));
        sumaTutorialInformation2.setMainImage(R.drawable.suma);
        sumaTutorialInformation2.setOptionalImg1(R.drawable.sum_homo_fraction);

        sumaTutorialInformationsList.add(sumaTutorialInformation2);

        SumaTutorialInformation sumaTutorialInformation3 = new SumaTutorialInformation();

        sumaTutorialInformation3.setTitle(getString(R.string.txtsumhete));
        sumaTutorialInformation3.setInformation(getString(R.string.txteoriasumhete));
        sumaTutorialInformation3.setMainImage(R.drawable.suma);
        sumaTutorialInformation3.setOptionalImg1(R.drawable.sum_hete_fractions);

        sumaTutorialInformationsList.add(sumaTutorialInformation3);

        SumaTutorialInformation sumaTutorialInformation4 = new SumaTutorialInformation();

        sumaTutorialInformation4.setTitle(getString(R.string.txtreshomo));
        sumaTutorialInformation4.setInformation(getString(R.string.txteoriareshomo));
        sumaTutorialInformation4.setMainImage(R.drawable.resta);
        sumaTutorialInformation4.setOptionalImg1(R.drawable.res_homo_fraction);

        sumaTutorialInformationsList.add(sumaTutorialInformation4);

        SumaTutorialInformation sumaTutorialInformation5 = new SumaTutorialInformation();

        sumaTutorialInformation5.setTitle(getString(R.string.txtreshete));
        sumaTutorialInformation5.setInformation(getString(R.string.txteoriareshete));
        sumaTutorialInformation5.setMainImage(R.drawable.resta);
        sumaTutorialInformation5.setOptionalImg1(R.drawable.res_hete_fractions);

        sumaTutorialInformationsList.add(sumaTutorialInformation5);

        SumaTutorialInformation sumaTutorialInformation6 = new SumaTutorialInformation();

        sumaTutorialInformation6.setTitle(getString(R.string.txtmul));
        sumaTutorialInformation6.setInformation(getString(R.string.txteoriamul));
        sumaTutorialInformation6.setMainImage(R.drawable.multiplicacion);
        sumaTutorialInformation6.setOptionalImg1(R.drawable.mul_fractions);

        sumaTutorialInformationsList.add(sumaTutorialInformation6);

        SumaTutorialInformation sumaTutorialInformation7 = new SumaTutorialInformation();

        sumaTutorialInformation7.setTitle(getString(R.string.txtdiv));
        sumaTutorialInformation7.setInformation(getString(R.string.txteoriadiv));
        sumaTutorialInformation7.setMainImage(R.drawable.division);
        sumaTutorialInformation7.setOptionalImg1(R.drawable.div_fractions);

        sumaTutorialInformationsList.add(sumaTutorialInformation7);
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
}