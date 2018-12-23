package androidlogic.evaluacion;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.funnyfractions.game.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidlogic.evaluacion.interfaces.EvaluationInteractor;
import androidlogic.evaluacion.interfaces.EvaluationView;
import androidlogic.retrofitClasses.Questions;

public class EvaluacionActivity extends AppCompatActivity implements EvaluationView, View.OnClickListener {

    //widgest
    private EvaluationProvider evaluationProvider;
    private ImageView question;
    private TextView  answera, answerb, answerc, answerd;
    //variables
    private List<Questions> questionsList;
    int randomQ = 0;
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

        answera.setOnClickListener(this);
        answerb.setOnClickListener(this);
        answerc.setOnClickListener(this);
        answerd.setOnClickListener(this);
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
        randomQ = (int) (Math.random() * lista.size() + 1);
        Picasso.get().load(lista.get((randomQ-1)).getPregunta()).into(question);
        answera.setText(lista.get((randomQ-1)).getOpcion1());
        answerb.setText(lista.get((randomQ-1)).getOpcion2());
        answerc.setText(lista.get((randomQ-1)).getOpcion3());
        answerd.setText(lista.get((randomQ-1)).getOpcion4());


    }

    public boolean evaluateSelection(String answer){
        if(answer.equals(questionsList.get(randomQ-1).getRespuesta())){
            Toast.makeText(this,"Respuesta correcta", Toast.LENGTH_LONG).show();
            return true;
        }else{
            correctAnswer(questionsList.get(randomQ-1).getRespuesta());
            Toast.makeText(this,"Respuesta incorrecta", Toast.LENGTH_LONG).show();
            return  false;
        }
    }

    public void correctAnswer(String answer) {
        if (answer.equals(answera.getText().toString())) {
            answera.setBackgroundResource(R.drawable.answeracorrect);
        } else if (answer.equals(answerb.getText().toString())) {
            answerb.setBackgroundResource(R.drawable.answerbcorrect);
        } else if (answer.equals(answerc.getText().toString())) {
            answerc.setBackgroundResource(R.drawable.answerccorrect);
        } else {
            answerd.setBackgroundResource(R.drawable.answerdcorrect);

        }

    }

    public void newQuestion(){
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                questionsList.remove((randomQ-1));
                questionThrow(questionsList);
            }
        }, 3000);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.textview_answer1:
                if(evaluateSelection(answera.getText().toString())){
                    answera.setBackgroundResource(R.drawable.answeracorrect);
                    newQuestion();
                }else{
                    answera.setBackgroundResource(R.drawable.answeraerror);
                    newQuestion();
                }
                break;
            case R.id.textview_answer2:
                if(evaluateSelection(answerb.getText().toString())){
                    answerb.setBackgroundResource(R.drawable.answerbcorrect);
                    newQuestion();
                }else{
                    answerb.setBackgroundResource(R.drawable.answerberror);
                    newQuestion();
                }
                break;
            case R.id.textview_answer3:
                if(evaluateSelection(answerc.getText().toString())){
                    answerc.setBackgroundResource(R.drawable.answerccorrect);
                    newQuestion();
                }else{
                    answerc.setBackgroundResource(R.drawable.answercerror);
                    newQuestion();
                }
                break;
            case R.id.textview_answer4:
                if(evaluateSelection(answerd.getText().toString())){
                    answerd.setBackgroundResource(R.drawable.answerdcorrect);
                    newQuestion();
                }else{
                    answerd.setBackgroundResource(R.drawable.answerderror);
                    newQuestion();
                }
                break;
        }
    }
}
