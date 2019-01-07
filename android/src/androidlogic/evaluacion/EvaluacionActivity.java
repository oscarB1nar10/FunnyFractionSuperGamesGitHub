package androidlogic.evaluacion;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.funnyfractions.game.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import androidlogic.evaluacion.interfaces.EvaluationInteractor;
import androidlogic.evaluacion.interfaces.EvaluationView;
import androidlogic.home.Home;
import androidlogic.retrofitClasses.Questions;

public class EvaluacionActivity extends AppCompatActivity implements EvaluationView, View.OnClickListener {

    //widgest
    private EvaluationProvider evaluationProvider;
    private MediaPlayer musicafondo, selectsound, correctsound, errorsound, introsound;
    private ImageView question;
    private Button btn_play_evaluation;
    private ImageButton btn_fifty_fifty, btn_call, btn_salir;
    private TextView  answera, answerb, answerc, answerd;
    private LinearLayout linearLayout_options, linearLayout_images;
    //variables
    private List<Questions> questionsList;
    int randomQ = 0;
    private ArrayList<TextView> answerOptions;
    private int answersToDelete;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.evaluacion);
        init();
        initView();
        introsound.start();
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
        linearLayout_images = findViewById(R.id.linearLayout_images);
        btn_fifty_fifty = findViewById(R.id.btn_fifty_fifty);
        btn_call = findViewById(R.id.btn_call);
        btn_salir = findViewById(R.id.btn_exit);
        //region sonidos
        musicafondo = MediaPlayer.create(this, R.raw.musicafondo);
        introsound = MediaPlayer.create(this, R.raw.intro);
        selectsound = MediaPlayer.create(this, R.raw.preguntaselecionada);
        correctsound = MediaPlayer.create(this, R.raw.preguntacorrecta);
        errorsound = MediaPlayer.create(this, R.raw.preguntaincorrecta);
        //endregion

        answerOptions = new ArrayList<>();
        answerOptions.add(answera);
        answerOptions.add(answerb);
        answerOptions.add(answerc);
        answerOptions.add(answerd);

        answera.setOnClickListener(this);
        answerb.setOnClickListener(this);
        answerc.setOnClickListener(this);
        answerd.setOnClickListener(this);
        btn_play_evaluation.setOnClickListener(this);
        btn_fifty_fifty.setOnClickListener(this);
        btn_call.setOnClickListener(this);
        btn_salir.setOnClickListener(this);
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

        answera.setBackgroundResource(R.drawable.textview_how_want_to_be_mellionary);
        answerb.setBackgroundResource(R.drawable.textview_how_want_to_be_mellionary2);
        answerc.setBackgroundResource(R.drawable.textview_how_want_to_be_mellionary3);
        answerd.setBackgroundResource(R.drawable.textview_how_want_to_be_mellionary4);

        Picasso.get().load(lista.get((randomQ-1)).getPregunta()).into(question);
        answera.setText(lista.get((randomQ-1)).getOpcion1());
        answerb.setText(lista.get((randomQ-1)).getOpcion2());
        answerc.setText(lista.get((randomQ-1)).getOpcion3());
        answerd.setText(lista.get((randomQ-1)).getOpcion4());
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

    private void fiftyFifty(){
        int random = (int) (Math.random() * answerOptions.size() + 1);


        while(answersToDelete != 2){
            if(!answerOptions.get((random-1)).getText().toString().equals(questionsList.get(randomQ-1).getRespuesta())
                    && !answerOptions.get((random-1)).getText().toString().equals("")){
                answerOptions.get((random-1)).setText("");
                answersToDelete ++ ;
                random = (int) (Math.random() * answerOptions.size() + 1);
            }else{
                random = (int) (Math.random() * answerOptions.size() + 1);
            }
        }

    }

    private void  callToEintein(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.call_mellinonary,null);
        TextView respuesta = view.findViewById(R.id.textview_respuesta);
        Button cerrar = view.findViewById(R.id.dismiss);
        builder.setView(view);
        final AlertDialog dialog = builder.create();
        dialog.setCancelable(false);
        dialog.show();
        cerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        respuesta.setText("Parece que la respuesta es: "+questionsList.get(randomQ-1).getRespuesta());
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
                    correctsound.start();
                    textView.setBackgroundResource(R.drawable.answeracorrect);
                }else if(value == 2 && textView == answera){
                    errorsound.start();
                    textView.setBackgroundResource(R.drawable.answeraerror);
                    correctAnswer(questionsList.get(randomQ-1).getRespuesta());
                }else if(value == 1 && textView == answerb){
                    correctsound.start();
                    textView.setBackgroundResource(R.drawable.answerbcorrect);
                }else if(value == 2 && textView == answerb){
                    errorsound.start();
                    textView.setBackgroundResource(R.drawable.answerberror);
                    correctAnswer(questionsList.get(randomQ-1).getRespuesta());
                }else if(value == 1 && textView == answerc){
                    correctsound.start();
                    textView.setBackgroundResource(R.drawable.answerccorrect);
                }else if(value == 2 && textView == answerc){
                    errorsound.start();
                    textView.setBackgroundResource(R.drawable.answercerror);
                    correctAnswer(questionsList.get(randomQ-1).getRespuesta());
                }else if(value == 1 && textView == answerd){
                    correctsound.start();
                    textView.setBackgroundResource(R.drawable.answerdcorrect);
                }else{
                    errorsound.start();
                    textView.setBackgroundResource(R.drawable.answerderror);
                    correctAnswer(questionsList.get(randomQ-1).getRespuesta());
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
                musicafondo.stop();
                selectsound.start();
                if(answera.getText().toString().equals(questionsList.get(randomQ-1).getRespuesta())){
                    changeColorAnswerSelected(answera, 1 );
                }else{
                    changeColorAnswerSelected(answera, 2 );
                }
                break;
            case R.id.textview_answer2:
                answerb.setBackgroundResource(R.drawable.answerbselecte);
                musicafondo.stop();
                selectsound.start();
                if(answerb.getText().toString().equals(questionsList.get(randomQ-1).getRespuesta())){
                    changeColorAnswerSelected(answerb, 1 );
                }else{
                    changeColorAnswerSelected(answerb, 2 );
                }
                break;
            case R.id.textview_answer3:
                answerc.setBackgroundResource(R.drawable.answercselect);
                musicafondo.stop();
                selectsound.start();
                if(answerc.getText().toString().equals(questionsList.get(randomQ-1).getRespuesta())){
                    changeColorAnswerSelected(answerc, 1 );
                }else{
                    changeColorAnswerSelected(answerc, 2 );
                }
                break;
            case R.id.textview_answer4:
                answerd.setBackgroundResource(R.drawable.answerdselect);
                musicafondo.stop();
                selectsound.start();
                if(answerd.getText().toString().equals(questionsList.get(randomQ-1).getRespuesta())){
                    changeColorAnswerSelected(answerd, 1 );
                }else{
                    changeColorAnswerSelected(answerd, 2 );
                }
                break;

            case R.id.btn_play_evaluation:
                introsound.stop();
                musicafondo.start();
                linearLayout_options.setVisibility(View.VISIBLE);
                linearLayout_images.setVisibility(View.VISIBLE);
                break;

            case R.id.btn_fifty_fifty:
                fiftyFifty();
                break;

            case R.id.btn_call:
                callToEintein();
                break;

            case R.id.btn_exit:
                exitGame();
                break;
        }
    }

    private void exitGame() {
        musicafondo.stop();
        selectsound.stop();
        correctsound.stop();
        errorsound.stop();
        SharedPreferences sharedP;
        Intent intent = new Intent(getApplicationContext(), Home.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        sharedP = getSharedPreferences("SHARED_PREFERENCES", Context.MODE_PRIVATE);
        intent.putExtra("usuario", sharedP.getString("usuariologueado", "usuario"));
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {

    }
}
