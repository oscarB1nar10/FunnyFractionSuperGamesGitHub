package androidlogic.evaluacion;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.funnyfractions.game.R;
import com.squareup.picasso.Picasso;

import androidlogic.evaluacion.interfaces.EvaluationInteractor;
import androidlogic.evaluacion.interfaces.EvaluationView;

public class EvaluacionActivity extends AppCompatActivity implements EvaluationView {
    private ImageView layout;
    private EvaluationProvider evaluationProvider;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.evaluacion);
        layout = findViewById(R.id.layoutEvaluacion);
        Picasso.get().load("https://funnyfractios000.000webhostapp.com/images/millonariofondo.png").into(layout);

        init();
        consumeServices();
    }

    private void init(){
        EvaluationInteractor evaluationInteractor = new EvaluationInteractorImpl();
        evaluationProvider = new EvaluationProvider(EvaluacionActivity.this, evaluationInteractor);
    }

    private void consumeServices(){
        evaluationProvider.getEvaluationQ();
    }



}
