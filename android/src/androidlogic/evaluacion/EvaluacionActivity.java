package androidlogic.evaluacion;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
    private Button btn_play_evaluation;
    private TextView  answera, answerb, answerc, answerd;
    private LinearLayout linearLayout_options;
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
        btn_play_evaluation = findViewById(R.id.btn_play_evaluation);
        linearLayout_options = findViewById(R.id.linearlayout_options);

        answera.setOnClickListener(this);
        answerb.setOnClickListener(this);
        answerc.setOnClickListener(this);
        answerd.setOnClickListener(this);
        btn_play_evaluation.setOnClickListener(this);
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
        }, 2000);
    }

    /**
     * where value is the truth value of answer selected
     * 1: correct
     * 2: incorrect
     */

    public  void changeColorAnswerSelected(final TextView textView, final int value){
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(value == 1 && textView == answera){
                    textView.setBackgroundResource(R.drawable.answeracorrect);
                }else if(value == 2 && textView == answera){
                    textView.setBackgroundResource(R.drawable.answeraerror);
                }else if(value == 1 && textView == answerb){
                    textView.setBackgroundResource(R.drawable.answerbcorrect);
                }else if(value == 2 && textView == answerb){
                    textView.setBackgroundResource(R.drawable.answerberror);
                }else if(value == 1 && textView == answerc){
                    textView.setBackgroundResource(R.drawable.answerccorrect);
                }else if(value == 2 && textView == answerc){
                    textView.setBackgroundResource(R.drawable.answercerror);
                }else if(value == 1 && textView == answerd){
                    textView.setBackgroundResource(R.drawable.answerdcorrect);
                }else{
                    textView.setBackgroundResource(R.drawable.answerderror);
                }
                newQuestion();

            }
        }, 3000);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.textview_answer1:
                answera.setBackgroundResource(R.drawable.answeraselect);
                if(answera.getText().toString().equals(questionsList.get(randomQ-1))){
                    changeColorAnswerSelected(answera, 1 );
                }else{
                    changeColorAnswerSelected(answera, 2 );
                }

                break;
            case R.id.textview_answer2:
                answerb.setBackgroundResource(R.drawable.answerbselecte);
                if(answerb.getText().toString().equals(questionsList.get(randomQ-1))){
                    changeColorAnswerSelected(answerb, 1 );
                }else{
                    changeColorAnswerSelected(answerb, 2 );
                }
                break;
            case R.id.textview_answer3:
                answerc.setBackgroundResource(R.drawable.answercselect);
                if(answerc.getText().toString().equals(questionsList.get(randomQ-1))){
                    changeColorAnswerSelected(answerc, 1 );
                }else{
                    changeColorAnswerSelected(answerc, 2 );
                }
                break;
            case R.id.textview_answer4:
                answerd.setBackgroundResource(R.drawable.answerdselect);
                if(answerd.getText().toString().equals(questionsList.get(randomQ-1))){
                    changeColorAnswerSelected(answerd, 1 );
                }else{
                    changeColorAnswerSelected(answerd, 2 );
                }
                break;

            case R.id.btn_play_evaluation:
                question.setVisibility(View.VISIBLE);
                linearLayout_options.setVisibility(View.VISIBLE);
                break;
        }
    }
}
