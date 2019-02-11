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
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
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
    private Button btn_play_evaluation, btn_instrucc_evaluation, btn_settings_evaluation;
    private ImageButton btn_fifty_fifty, btn_call, btn_public , btn_salir, btn_salir_menu;
    private TextView  answera, answerb, answerc, answerd;
    private LinearLayout linearLayout_options, linearLayout_images;
    //variables
    private List<Questions> questionsList;
    int randomQ = 0, numberQuestion = 1;
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
        btn_instrucc_evaluation = findViewById(R.id.btn_instrucciones_evaluation);
        btn_settings_evaluation = findViewById(R.id.btn_settings_evaluation);
        linearLayout_options = findViewById(R.id.linearlayout_options);
        linearLayout_images = findViewById(R.id.linearLayout_images);
        btn_fifty_fifty = findViewById(R.id.btn_fifty_fifty);
        btn_call = findViewById(R.id.btn_call);
        btn_public = findViewById(R.id.btn_public);
        btn_salir = findViewById(R.id.btn_exit);
        btn_salir_menu = findViewById(R.id.btn_exit_menu_millionary);

        //region sonidos
        musicafondo = MediaPlayer.create(this, R.raw.musicafondo);
        musicafondo.setLooping(true);
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
        btn_instrucc_evaluation.setOnClickListener(this);
        btn_settings_evaluation.setOnClickListener(this);
        btn_fifty_fifty.setOnClickListener(this);
        btn_call.setOnClickListener(this);
        btn_salir.setOnClickListener(this);
        btn_salir_menu.setOnClickListener(this);
        btn_public.setOnClickListener(this);
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
                if(numberQuestion != 10) {
                    correctAnswerAlert();
                    questionsList.remove((randomQ - 1));
                    questionThrow(questionsList);
                    enableAnswerButton();
                }else{
                    wonGame();
                }
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
                    correctsound.start();
                    textView.setBackgroundResource(R.drawable.answeracorrect);
                    newQuestion();
                }else if(value == 2 && textView == answera){
                    errorsound.start();
                    textView.setBackgroundResource(R.drawable.answeraerror);
                    correctAnswer(questionsList.get(randomQ-1).getRespuesta());
                    loseGame();
                }else if(value == 1 && textView == answerb){
                    correctsound.start();
                    textView.setBackgroundResource(R.drawable.answerbcorrect);
                    newQuestion();
                }else if(value == 2 && textView == answerb){
                    errorsound.start();
                    textView.setBackgroundResource(R.drawable.answerberror);
                    correctAnswer(questionsList.get(randomQ-1).getRespuesta());
                    loseGame();
                }else if(value == 1 && textView == answerc){
                    correctsound.start();
                    textView.setBackgroundResource(R.drawable.answerccorrect);
                    newQuestion();
                }else if(value == 2 && textView == answerc){
                    errorsound.start();
                    textView.setBackgroundResource(R.drawable.answercerror);
                    correctAnswer(questionsList.get(randomQ-1).getRespuesta());
                    loseGame();
                }else if(value == 1 && textView == answerd){
                    correctsound.start();
                    textView.setBackgroundResource(R.drawable.answerdcorrect);
                    newQuestion();
                }else{
                    errorsound.start();
                    textView.setBackgroundResource(R.drawable.answerderror);
                    correctAnswer(questionsList.get(randomQ-1).getRespuesta());
                    loseGame();
                }
            }
        }, 3000);
    }

    public void instrucciones(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater layout = this.getLayoutInflater();
        View view = layout.inflate(R.layout.instrucciones, null);
        LinearLayout layoutdialog = view.findViewById(R.id.linearDialog);
        ArrayList<ImageView> imagen = new ArrayList<>();

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        ImageView imageView = new ImageView(this);
        ImageView imageView2 = new ImageView(this);
        ImageView imageView3 = new ImageView(this);
        ImageView imageView4 = new ImageView(this);
        ImageView imageView5 = new ImageView(this);
        ImageView imageView6 = new ImageView(this);
        ImageView imageView7 = new ImageView(this);

        imageView.setImageResource(R.drawable.millonario_ins1);
        imageView.setLayoutParams(params);
        imageView.setAdjustViewBounds(true);

        imageView2.setImageResource(R.drawable.millonario_ins2);
        imageView2.setLayoutParams(params);
        imageView2.setAdjustViewBounds(true);

        imageView3.setImageResource(R.drawable.millonario_ins3);
        imageView3.setLayoutParams(params);
        imageView3.setAdjustViewBounds(true);

        imageView4.setImageResource(R.drawable.millonario_ins4);
        imageView4.setLayoutParams(params);
        imageView4.setAdjustViewBounds(true);

        imageView5.setImageResource(R.drawable.millonario_ins5);
        imageView5.setLayoutParams(params);
        imageView5.setAdjustViewBounds(true);

        imageView6.setImageResource(R.drawable.millonario_ins6);
        imageView6.setLayoutParams(params);
        imageView6.setAdjustViewBounds(true);

        imageView7.setImageResource(R.drawable.millonario_ins7);
        imageView7.setLayoutParams(params);
        imageView7.setAdjustViewBounds(true);


        imagen.add(imageView);
        imagen.add(imageView2);
        imagen.add(imageView3);
        imagen.add(imageView4);
        imagen.add(imageView5);
        imagen.add(imageView6);
        imagen.add(imageView7);

        for(ImageView item : imagen) {
            layoutdialog.addView(item);
        }
        builder.setView(view);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.textview_answer1:
                answera.setBackgroundResource(R.drawable.answeraselect);
                musicafondo.pause();
                selectsound.start();
                if(answera.getText().toString().equals(questionsList.get(randomQ-1).getRespuesta())){
                    changeColorAnswerSelected(answera, 1 );
                }else{
                    changeColorAnswerSelected(answera, 2 );
                }
                disableAnswerButton();
                break;
            case R.id.textview_answer2:
                answerb.setBackgroundResource(R.drawable.answerbselecte);
                musicafondo.pause();
                selectsound.start();
                if(answerb.getText().toString().equals(questionsList.get(randomQ-1).getRespuesta())){
                    changeColorAnswerSelected(answerb, 1 );
                }else{
                    changeColorAnswerSelected(answerb, 2 );
                }
                disableAnswerButton();
                break;
            case R.id.textview_answer3:
                answerc.setBackgroundResource(R.drawable.answercselect);
                musicafondo.pause();
                selectsound.start();
                if(answerc.getText().toString().equals(questionsList.get(randomQ-1).getRespuesta())){
                    changeColorAnswerSelected(answerc, 1 );
                }else{
                    changeColorAnswerSelected(answerc, 2 );
                }
                disableAnswerButton();
                break;
            case R.id.textview_answer4:
                answerd.setBackgroundResource(R.drawable.answerdselect);
                musicafondo.pause();
                selectsound.start();
                if(answerd.getText().toString().equals(questionsList.get(randomQ-1).getRespuesta())){
                    changeColorAnswerSelected(answerd, 1 );
                }else{
                    changeColorAnswerSelected(answerd, 2 );
                }
                disableAnswerButton();
                break;

            case R.id.btn_play_evaluation:
                numberQuestion = 1;
                questionThrow(questionsList);
                enableAnswerButton();
                introsound.stop();
                musicafondo.start();
                linearLayout_options.setVisibility(View.VISIBLE);
                linearLayout_images.setVisibility(View.VISIBLE);
                break;

            case R.id.btn_instrucciones_evaluation:
                instrucciones();
                break;

            case R.id.btn_settings_evaluation:
                Toast.makeText(this, "Pendiente", Toast.LENGTH_LONG).show();
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

            case R.id.btn_exit_menu_millionary:
                exitGame();
                break;

            case R.id.btn_public:
                helpPublic();
                break;
        }
    }

    private void correctAnswerAlert(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.correct_answer_mellionary,null);
        TextView txtContinue = view.findViewById(R.id.txt_continue);
        TextView txtTakeMoney = view.findViewById(R.id.txt_take_money);
        TextView txtMoney = view.findViewById(R.id.txt_money);

        builder.setView(view);
        final AlertDialog dialog = builder.create();
        dialog.setCancelable(false);

        txtMoney.setText("$"+ (500*numberQuestion));

        txtContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                musicafondo.start();
                numberQuestion++;
                dialog.dismiss();
            }
        });
        txtTakeMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                linearLayout_images.setVisibility(View.GONE);
                linearLayout_options.setVisibility(View.GONE);
                enableHelps();
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void callToEintein(){
        btn_call.setImageResource(R.drawable.call_mellionary_selected);
        btn_call.setEnabled(false);

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

    private void helpPublic() {
        int max, random;
        btn_public.setImageResource(R.drawable.public_mellionary_selected);
        btn_public.setEnabled(false);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.public_mellionary,null);
        ImageView pregunta = view.findViewById(R.id.preguntapublico);
        ProgressBar posiblea = view.findViewById(R.id.aposible);
        ProgressBar posibleb = view.findViewById(R.id.bposible);
        ProgressBar posiblec = view.findViewById(R.id.cposible);
        ProgressBar posibled = view.findViewById(R.id.dposible);
        Button cerrar = view.findViewById(R.id.dismisspublic);
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
        Picasso.get().load(this.questionsList.get((randomQ-1)).getPregunta()).into(pregunta);
        random = (int) Math.floor(Math.random()*(100-51)+50);
        if (questionsList.get(randomQ-1).getRespuesta().equals(answera.getText().toString())) {
            posiblea.setProgress(random);
            max = 100-random;
            random = (int) (Math.random() * max + 1);
            posibleb.setProgress(random);
            max = max - random;
            random = (int) (Math.random() * max + 1);
            posiblec.setProgress(random);
            max = max - random;
            posibled.setProgress(max);
        } else if (questionsList.get(randomQ-1).getRespuesta().equals(answerb.getText().toString())) {
            posibleb.setProgress(random);
            max = 100-random;
            random = (int) (Math.random() * max + 1);
            posiblea.setProgress(random);
            max = max - random;
            random = (int) (Math.random() * max + 1);
            posiblec.setProgress(random);
            max = max - random;
            posibled.setProgress(max);
        } else if (questionsList.get(randomQ-1).getRespuesta().equals(answerc.getText().toString())) {
            posiblec.setProgress(random);
            max = 100-random;
            random = (int) (Math.random() * max + 1);
            posibleb.setProgress(random);
            max = max - random;
            random = (int) (Math.random() * max + 1);
            posiblea.setProgress(random);
            max = max - random;
            posibled.setProgress(max);
        } else {
            posibled.setProgress(random);
            max = 100-random;
            random = (int) (Math.random() * max + 1);
            posibleb.setProgress(random);
            max = max - random;
            random = (int) (Math.random() * max + 1);
            posiblea.setProgress(random);
            max = max - random;
            posiblec.setProgress(max);
        }
    }

    private void fiftyFifty(){
        btn_fifty_fifty.setImageResource(R.drawable.fifty_fiftymillionary_game_selected);
        btn_fifty_fifty.setEnabled(false);
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

    private void disableAnswerButton(){
        for(TextView  textView : answerOptions){
            textView.setEnabled(false);
        }
    }

    private void enableAnswerButton(){
        for(TextView  textView : answerOptions){
            textView.setEnabled(true);
        }
    }

    @Override
    public void onBackPressed() {

    }

    @Override
    protected void onPause() {
        musicafondo.pause();
        introsound.pause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        if(linearLayout_options.getVisibility() == View.VISIBLE){
            musicafondo.start();
        }
        else{
            introsound.start();
        }
        super.onResume();
    }

    private void wonGame(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.won_billionary_game,null);
        TextView txt_playAgain = view.findViewById(R.id.txt_play_again);
        TextView txt_exit = view.findViewById(R.id.txt_exit);
        TextView txt_money = view.findViewById(R.id.txt_money);
        builder.setView(view);
        final AlertDialog dialog = builder.create();
        dialog.setCancelable(false);
        txt_playAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numberQuestion = 1;
                questionThrow(questionsList);
                enableAnswerButton();
                dialog.dismiss();
                musicafondo.start();

            }
        });
        txt_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linearLayout_images.setVisibility(View.GONE);
                linearLayout_options.setVisibility(View.GONE);
                dialog.dismiss();
                enableHelps();
                musicafondo.stop();
            }
        });
        txt_money.setText("$ "+(500*numberQuestion));
        dialog.show();
    }

    private void loseGame(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.won_billionary_game,null);
        TextView txt_playAgain = view.findViewById(R.id.txt_play_again);
        TextView txt_exit = view.findViewById(R.id.txt_exit);
        TextView txt_money = view.findViewById(R.id.txt_money);
        ImageView imageView = view.findViewById(R.id.imageview_trophy);
        final MediaPlayer intro = MediaPlayer.create(this, R.raw.intro);

        builder.setView(view);
        final AlertDialog dialog = builder.create();
        dialog.setCancelable(false);

        imageView.setImageResource(R.drawable.panda_mellionary);

        txt_playAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numberQuestion = 1;
                questionThrow(questionsList);
                enableAnswerButton();
                enableHelps();
                dialog.dismiss();
                musicafondo.start();
            }
        });
        txt_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linearLayout_images.setVisibility(View.GONE);
                linearLayout_options.setVisibility(View.GONE);
                enableHelps();
                dialog.dismiss();
            }
        });
        txt_money.setText("$ "+((500*numberQuestion)-500));
        dialog.show();
    }

    public void enableHelps(){
        btn_call.setImageResource(R.drawable.call_mellionary);
        btn_call.setEnabled(true);
        btn_public.setImageResource(R.drawable.public_mellionary);
        btn_public.setEnabled(true);
        btn_fifty_fifty.setImageResource(R.drawable.fifty_fiftymillionary_game);
        btn_fifty_fifty.setEnabled(true);
    }
}
