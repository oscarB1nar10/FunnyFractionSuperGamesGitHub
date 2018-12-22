package androidlogic.evaluacion;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.funnyfractions.game.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidlogic.evaluacion.interfaces.EvaluationInteractor;
import androidlogic.evaluacion.interfaces.EvaluationView;
import androidlogic.retrofitClasses.Questions;

public class EvaluacionActivity extends AppCompatActivity implements EvaluationView {

    //widgest
    private EvaluationProvider evaluationProvider;
    private ImageView question;
    private TextView  answera, answerb, answerc, answerd;
    //variables
    private List<Questions> questionsList;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.evaluacion);
        init();
        initView();
        consumeServices();
    }

    private void init(){
        EvaluationInteractor evaluationInteractor = new EvaluationInteractorImpl();
        evaluationProvider = new EvaluationProvider(EvaluacionActivity.this, evaluationInteractor);
    }


    private void initView() {
        question =  findViewById(R.id.imageview_question);
        answera = findViewById(R.id.textview_answer1);
        answerb = findViewById(R.id.textview_answer2);
        answerc = findViewById(R.id.textview_answer3);
        answerd = findViewById(R.id.textview_answer4);
    }


    private void consumeServices(){
        evaluationProvider.getEvaluationQ();
    }


    @Override
    public void questionList(List<Questions> questionsList) {
        this.questionsList = questionsList;
        questionThrow(this.questionsList);
    }

    public void questionThrow(List<Questions> lista){
        //Here we assign a question whit the respective answer options
        int randomQ = (int) (Math.random() * lista.size() + 1);
        Picasso.get().load(lista.get((randomQ-1)).getPregunta()).into(question);
        answera.setText(lista.get((randomQ-1)).getOpcion1());
        answerb.setText(lista.get((randomQ-1)).getOpcion2());
        answerc.setText(lista.get((randomQ-1)).getOpcion3());
        answerd.setText(lista.get((randomQ-1)).getOpcion4());
        lista.remove(lista.get(randomQ-1));

    }
}
