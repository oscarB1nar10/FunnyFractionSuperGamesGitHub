package bateria;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.funnyfractions.game.R;

import java.util.ArrayList;

import androidlogic.practice.Practica;
import de.hdodenhof.circleimageview.CircleImageView;
import tyrantgit.explosionfield.ExplosionField;

public class EjecutableBateria extends AppCompatActivity implements View.OnClickListener {
    ArrayList<Pregunta> preguntas;
    ArrayList<ObjectAnimator> hearts2;
    ExplosionField explosionField;
    TextView enunciado, pregunta, respuesta1, respuesta2, respuesta3, respuesta4;
    ArrayList<ImageView> hearts;
    ArrayList<TextView> txtvList = new ArrayList<>();
    ImageView operacion, selector;
    ImageButton imb_pause;
    Button btn_jugar;
    LinearLayout main_linearLayout;
    LottieAnimationView animacionBateria, animacionBateria1;
    MediaPlayer ring, heartExplosion;
    //vars
    int random = 0;
    final static int[] VFRAME = new int[]{6, 12, 18, 24, 30, 36, 40, 50, 54, 60, 68, 74};
    private int numberQuestion = 0, score = 1000, attemps = 0;
    private SharedPreferences mSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ejecutable_bateria);
        animacionBateria = findViewById(R.id.animacionBateria);
        animacionBateria.useHardwareAcceleration(true);
        startBattery();
        hearts2 = new ArrayList<>();

        hearts = new ArrayList<>();
        hearts.add((ImageView) findViewById(R.id.heart1));
        hearts.add((ImageView) findViewById(R.id.heart2));
        hearts.add((ImageView) findViewById(R.id.heart3));

        ObjectAnimator heart1 = ObjectAnimator.ofFloat(hearts.get(2), "translationY", 0, 0);
        ObjectAnimator heart2 = ObjectAnimator.ofFloat(hearts.get(1), "translationY", 0, 0);
        ObjectAnimator heart3 = ObjectAnimator.ofFloat(hearts.get(0), "translationY", 0, 0);

        hearts2.add(heart1);
        hearts2.add(heart2);
        hearts2.add(heart3);

        imb_pause = findViewById(R.id.imb_pause);
        imb_pause.setOnClickListener(this);

        ring = MediaPlayer.create(EjecutableBateria.this,R.raw.spatial_sound);
        heartExplosion = MediaPlayer.create(EjecutableBateria.this, R.raw.bubble);
        heartExplosion.setVolume(1f,0.5f);
        ring.setLooping(true);

        explosionField = ExplosionField.attach2Window(this);
        animacionBateria1 = findViewById(R.id.animacionBateria_initial);

        mSharedPreferences = getSharedPreferences("SHARED_PREFERENCES", Context.MODE_PRIVATE);
        main_linearLayout = findViewById(R.id.main_layout_game_battery);
        btn_jugar = findViewById(R.id.btn_jugar_battery);
        btn_jugar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                main_linearLayout.setVisibility(View.VISIBLE);
            }
        });

        // animacionBateria.setRepeatCount(5);
        enunciado = findViewById(R.id.enunciado);
        operacion = findViewById(R.id.operacion);
        selector = findViewById(R.id.selector);
        pregunta = findViewById(R.id.pregunta);
        preguntas = new ArrayList<>();
        //_----------------------------------
        respuesta1 = findViewById(R.id.valor2);
        txtvList.add(respuesta1);
        respuesta1.setOnClickListener(this);
        respuesta2 = findViewById(R.id.valor3);
        txtvList.add(respuesta2);
        respuesta2.setOnClickListener(this);
        respuesta3 = findViewById(R.id.valor4);
        txtvList.add(respuesta3);
        respuesta3.setOnClickListener(this);
        respuesta4 = findViewById(R.id.valor5);
        txtvList.add(respuesta4);
        respuesta4.setOnClickListener(this);
        //__-------------------------------
        anadirEnunciados();
        extraerPreguntas();
        asignarValores();
    }

    private void asignarValores() {
        int randomN = (int) (Math.random() * 8 + 1);
        int randomA = (int) (Math.random() * 4 + 1);

        txtvList.get(0).setText("" + (randomN) + "/" + (randomN * randomA));
        txtvList.get(1).setText("" + (randomN + 1) + "/" + ((randomN * 2) * randomA));
        txtvList.get(2).setText("" + (randomN + 2) + "/" + ((randomN * 3) * randomA));
        txtvList.get(3).setText("" + (randomN + 3) + "/" + (randomN * randomA));


        txtvList.get(randomA - 1).setText("" + preguntas.get((random - 1)).answerToCompare);

    }

    public void extraerPreguntas() {
        random = (int) (Math.random() * 19 + 1);
        //generar valor aleatorio-> añadir valores a la vista
        enunciado.setText(preguntas.get(random - 1).enunciado);
        operacion.setImageResource(preguntas.get(random - 1).operacion);
        pregunta.setText(preguntas.get(random - 1).pregunta);
        numberQuestion++;
    }

    public void anadirEnunciados() {
        preguntas.add(new Pregunta("La bateria ha estado cargando durante un tiempo igual a la siguiente operacion", R.drawable.op2, "¿cuanto nivel de carga tiene la bateria?", 4 / 2, "4/2"));
        preguntas.add(new Pregunta("La bateria ha estado cargando durante un tiempo igual a la siguiente operacion", R.drawable.op1, "¿cuanto nivel de carga tiene la bateria?", 3 / 3, "3/3"));
        preguntas.add(new Pregunta("La bateria ha estado cargando durante un tiempo igual a la siguiente operacion", R.drawable.op3, "¿cuanto nivel de carga tiene la bateria?", 9 / 3, "9/3"));
        preguntas.add(new Pregunta("La bateria ha estado cargando durante un tiempo igual a la siguiente operacion", R.drawable.op4, "¿cuanto nivel de carga tiene la bateria?", 8 / 4, "8/4"));
        preguntas.add(new Pregunta("La bateria ha estado cargando durante un tiempo igual a la siguiente operacion", R.drawable.op5, "¿cuanto nivel de carga tiene la bateria?", 8 / 2, "8/2"));
        preguntas.add(new Pregunta("La bateria ha estado cargando durante un tiempo igual a la siguiente operacion", R.drawable.op6, "¿cuanto nivel de carga tiene la bateria?", 25 / 5, "25/5"));
        preguntas.add(new Pregunta("La bateria ha estado cargando durante un tiempo igual a la siguiente operacion", R.drawable.op7, "¿cuanto nivel de carga tiene la bateria?", 36 / 6, "36/6"));
        preguntas.add(new Pregunta("La bateria ha estado cargando durante un tiempo igual a la siguiente operacion", R.drawable.op8, "¿cuanto nivel de carga tiene la bateria?", 49 / 7, "49/7"));
        preguntas.add(new Pregunta("La bateria ha estado cargando durante un tiempo igual a la siguiente operacion", R.drawable.op9, "¿cuanto nivel de carga tiene la bateria?", 64 / 8, "64/8"));
        preguntas.add(new Pregunta("La bateria ha estado cargando durante un tiempo igual a la siguiente operacion", R.drawable.op10, "¿cuanto nivel de carga tiene la bateria?", 81 / 9, "81/9"));
        preguntas.add(new Pregunta("La bateria ha estado cargando durante un tiempo igual a la siguiente operacion", R.drawable.op11, "¿cuanto nivel de carga tiene la bateria?", 6 / 6, "6/6"));
        preguntas.add(new Pregunta("La bateria ha estado cargando durante un tiempo igual a la siguiente operacion", R.drawable.op12, "¿cuanto nivel de carga tiene la bateria?", 12 / 6, "12/6"));
        preguntas.add(new Pregunta("La bateria ha estado cargando durante un tiempo igual a la siguiente operacion", R.drawable.op13, "¿cuanto nivel de carga tiene la bateria?", 18 / 6, "18/6"));
        preguntas.add(new Pregunta("La bateria ha estado cargando durante un tiempo igual a la siguiente operacion", R.drawable.op14, "¿cuanto nivel de carga tiene la bateria?", 32 / 8, "32/8"));
        preguntas.add(new Pregunta("La bateria ha estado cargando durante un tiempo igual a la siguiente operacion", R.drawable.op15, "¿cuanto nivel de carga tiene la bateria?", 30 / 6, "30/6"));
        preguntas.add(new Pregunta("La bateria ha estado cargando durante un tiempo igual a la siguiente operacion", R.drawable.op16, "¿cuanto nivel de carga tiene la bateria?", 90 / 15, "90/15"));
        preguntas.add(new Pregunta("La bateria ha estado cargando durante un tiempo igual a la siguiente operacion", R.drawable.op17, "¿cuanto nivel de carga tiene la bateria?", 70 / 10, "70/10"));
        preguntas.add(new Pregunta("La bateria ha estado cargando durante un tiempo igual a la siguiente operacion", R.drawable.op18, "¿cuanto nivel de carga tiene la bateria?", 128 / 16, "128/16"));
        preguntas.add(new Pregunta("La bateria ha estado cargando durante un tiempo igual a la siguiente operacion", R.drawable.op19, "¿cuanto nivel de carga tiene la bateria?", 108 / 12, "108/12"));
    }

    public int extraerFrameParaAnimar(int respuesta) {
        return VFRAME[(respuesta - 1)];
    }

    @Override
    public void onClick(View view) {

        //El atributo "respuesta" pEr a pregunta se obtiene a partir del valor aleatorio generado en la funcion "extraer preguntas"
        switch (view.getId()) {
            case R.id.valor2:
                selector.setImageResource(R.drawable.s3);
                if (respuesta1.getText().equals(preguntas.get((random - 1)).answerToCompare)) {
                    disableTextView();
                    animacionBateria.setMaxFrame(extraerFrameParaAnimar(preguntas.get((random - 1)).respuesta));
                    animacionBateria.playAnimation();

                } else {
                    explosionField.explode((View) hearts2.get(hearts2.size() - 1).getTarget());
                    heartExplosion.start();
                    //View vieww = (View) hearts2.get(hearts2.size() - 1).getTarget();
                    //reset(view);
                    hearts2.remove(hearts2.size() - 1);
                    attemps++;
                    decreaseScore();
                }
                break;
            case R.id.valor3:
                selector.setImageResource(R.drawable.s4);
                if (respuesta2.getText().equals(preguntas.get((random - 1)).answerToCompare)) {
                    disableTextView();
                    animacionBateria.setMaxFrame(extraerFrameParaAnimar(preguntas.get((random - 1)).respuesta));
                    animacionBateria.playAnimation();


                } else {
                    explosionField.explode((View) hearts2.get(hearts2.size() - 1).getTarget());
                    heartExplosion.start();
                   // View vieww = (View) hearts2.get(hearts2.size() - 1).getTarget();
                    //explosionField.clear();
                    //reset(view);
                    hearts2.remove(hearts2.size() - 1);
                    attemps++;
                    decreaseScore();
                }
                break;
            case R.id.valor4:
                selector.setImageResource(R.drawable.s2);
                if (respuesta3.getText().equals(preguntas.get((random - 1)).answerToCompare)) {
                    disableTextView();
                    animacionBateria.setMaxFrame(extraerFrameParaAnimar(preguntas.get((random - 1)).respuesta));
                    animacionBateria.playAnimation();

                } else {
                    explosionField.explode((View) hearts2.get(hearts2.size() - 1).getTarget());
                    heartExplosion.start();
                    //View vieww = (View) hearts2.get(hearts2.size() - 1).getTarget();
                    //explosionField.clear();
                    //reset(view);
                    hearts2.remove(hearts2.size() - 1);
                    attemps++;
                    decreaseScore();
                }
                break;
            case R.id.valor5:
                selector.setImageResource(R.drawable.s1);
                if (respuesta4.getText().equals(preguntas.get((random - 1)).answerToCompare)) {
                    disableTextView();
                    animacionBateria.setMaxFrame(extraerFrameParaAnimar(preguntas.get((random - 1)).respuesta));
                    animacionBateria.playAnimation();

                } else {
                    explosionField.explode((View) hearts2.get(hearts2.size() - 1).getTarget());
                    //View vieww = (View) hearts2.get(hearts2.size() - 1).getTarget();
                    //explosionField.clear();
                    //reset(view);
                    heartExplosion.start();
                    hearts2.remove(hearts2.size() - 1);
                    attemps++;
                    decreaseScore();
                }
                break;

            case R.id.imb_pause:
                launchMenu();

                break;
        }

    }

    private  void startBattery(){
        animacionBateria.setSpeed(0.92f);
        animacionBateria.addAnimatorUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                if (animacionBateria.getProgress() == 1.0f) {
                    //aca se debe definir el frame hasta el cual animar para la repeticion numero 5.
                    // animacionBateria.setMaxFrame(extraerFrameParaAnimar(preguntas.get((random - 1)).respuesta));
                    nextQuestion();
                }

            }
        });

        animacionBateria.addAnimatorListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
              Handler handler = new Handler();
              handler.postDelayed(new Runnable() {
                  @Override
                  public void run() {
                      nextQuestion();
                  }
              }, 1000);


            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    private void launchMenu() {
        final SharedPreferences.Editor editor = mSharedPreferences.edit();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // Get the layout inflater
        LayoutInflater inflater = this.getLayoutInflater();

        View view = inflater.inflate(R.layout.menu_drop_game, null);

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(view);

        TextView txv_score = view.findViewById(R.id.final_score);
        TextView txv_title = view.findViewById(R.id.titulo);
        LinearLayout ll_score = view.findViewById(R.id.ll_score);
        CircleImageView cim_continue = view.findViewById(R.id.btn_play_drops);
        CircleImageView cim_home = view.findViewById(R.id.btn_home_drops);
        final CircleImageView cim_sound = view.findViewById(R.id.btn_sound_drops);


        final AlertDialog alertDialog = builder.create();
        alertDialog.setCanceledOnTouchOutside(false);
        if(numberQuestion == 10){
            txv_title.setText("Fin del juego");
            cim_continue.setImageResource(R.drawable.devolver);
        }

        if(mSharedPreferences.getBoolean("battery_sound", true)){
            cim_sound.setImageResource(R.drawable.sonido);
        }else{
            cim_sound.setImageResource(R.drawable.mute);
        }


        cim_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(numberQuestion == 10){
                    numberQuestion = 0;
                    score = 1000;
                    nextQuestion();
                }
                alertDialog.dismiss();


            }
        });

        cim_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Practica.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        cim_sound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mSharedPreferences.getBoolean("battery_sound", true)){
                    cim_sound.setImageResource(R.drawable.mute);
                    ring.pause();
                    editor.putBoolean("battery_sound", false);
                    editor.commit();
                }else{
                    cim_sound.setImageResource(R.drawable.sonido);
                    ring.start();
                    editor.putBoolean("battery_sound", true);
                    editor.commit();
                }
            }
        });

        ll_score.setVisibility(View.VISIBLE);
        txv_score.setText(""+score+"/"+1000);
        alertDialog.show();
    }


    private void nextQuestion(){
        if(numberQuestion < 10) {
            selector.setImageResource(R.drawable.selector);
            hearts = null;
            hearts = new ArrayList<>();

            hearts.add((ImageView) findViewById(R.id.heart1));
            hearts.add((ImageView) findViewById(R.id.heart2));
            hearts.add((ImageView) findViewById(R.id.heart3));

            ObjectAnimator heart1 = ObjectAnimator.ofFloat(hearts.get(2), "translationY", 0, 0);
            ObjectAnimator heart2 = ObjectAnimator.ofFloat(hearts.get(1), "translationY", 0, 0);
            ObjectAnimator heart3 = ObjectAnimator.ofFloat(hearts.get(0), "translationY", 0, 0);

            hearts2.add(heart1);
            hearts2.add(heart2);
            hearts2.add(heart3);

            animacionBateria.setProgress(0);
            reset((ImageView) findViewById(R.id.heart1));
            reset((ImageView) findViewById(R.id.heart2));
            reset((ImageView) findViewById(R.id.heart3));
            attemps = 0;
            enableTextView();
            extraerPreguntas();
            asignarValores();
        }else{
            launchMenu();

        }
    }

    private void disableTextView(){
        for(TextView textView: txtvList){
            textView.setEnabled(false);
        }
    }

    private void enableTextView(){
        for(TextView textView: txtvList){
            textView.setEnabled(true);
        }
    }
    
    private void decreaseScore(){
        if(attemps == 1){
            score = score - 25;
        }else if(attemps == 2){
            score = score - 50;
        }else{
            score = score - 100;
            disableTextView();
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    nextQuestion();

                }
            }, 1000);
            attemps = 0;

        }
    }

    private void reset(View root) {
        if (root instanceof ViewGroup) {
            ViewGroup parent = (ViewGroup) root;
            for (int i = 0; i < parent.getChildCount(); i++) {
                reset(parent.getChildAt(i));
            }
        } else {
            root.setScaleX(1);
            root.setScaleY(1);
            root.setAlpha(1);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        ring.pause();
    }

    @Override
    protected void onStart() {
        super.onStart();
        ring.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        ring.stop();
    }

    public class Pregunta {

        public String enunciado, pregunta, answerToCompare;
        int operacion, respuesta;

        Pregunta(String enunciado, int operacion, String pregunta, int respuesta, String answerToCompare){
            this.enunciado=enunciado;
            this.operacion=operacion;
            this.pregunta=pregunta;
            this.respuesta=respuesta;
            this.answerToCompare = answerToCompare;
        }
    }
}
