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
        SumaTutorialInformation sumaTutorialInformation1 = new SumaTutorialInformation();

        sumaTutorialInformation1.setTitle(getString(R.string.txtQueson));
        sumaTutorialInformation1.setInformation(getString(R.string.txtdefinicion));
        sumaTutorialInformation1.setMainImage(R.drawable.fraccionejemplo);

        sumaTutorialInformationsList.add(sumaTutorialInformation1);

        SumaTutorialInformation sumaTutorialInformation2 = new SumaTutorialInformation();

        sumaTutorialInformation2.setTitle(getString(R.string.txtQueson));
        sumaTutorialInformation2.setInformation(getString(R.string.txtdefinicion));
        sumaTutorialInformation2.setMainImage(R.drawable.fraccionejemplo);

        sumaTutorialInformationsList.add(sumaTutorialInformation2);

        mRecyclerView = findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);

        //adapter to use
        mAdapter = new MyAdapter(sumaTutorialInformationsList);
        mRecyclerView.setAdapter(mAdapter);




    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.zoom_back_in, R.anim.zoom_back_out);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //hom.setImageDrawable(null);
        //img.setImageDrawable(null);
    }
}