package burbujas;
import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.funnyfractions.game.R;
import java.util.ArrayList;
import java.util.zip.Inflater;

import androidlogic.practice.Practica;
import tyrantgit.explosionfield.ExplosionField;

import static android.support.test.InstrumentationRegistry.getContext;

public class BubblesMain extends Activity implements View.OnClickListener, Animator.AnimatorListener {
    //cons
    private static final String TAG = "BubblesMain";


    //vars
    ArrayList<ObjectAnimator> objectAnimators = new ArrayList<>();
    MediaPlayer sonido,waterSound;
    ExplosionField explosionField;
    ObjectAnimator objectAnimator1, objectAnimator2, objectAnimator3, objectAnimator4;
    Button opcion1, opcion2, opcion3, opcion4, opcion11;
    ImageView imgoperacion, cor1, cor2, cor3, pausa;
    int numjuegos = 0, puntuacion, heightDp, alea, vidas;
    private long currentAnimation;
    private  ArrayList <Pregunta> Preguntas;
    private boolean correctAnswer, stopThread;
    private int random;
    Thread thread;
    boolean validateHeight = true;
    private View view;
    private ObjectAnimator objectAnimatorF;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        random = (int) (Math.random()*40);
        vidas = 3;
        puntuacion = 1000;
        imgoperacion = findViewById(R.id.imgopera);
        cor1 = findViewById(R.id.vida1);
        cor2 = findViewById(R.id.vida2);
        cor3 = findViewById(R.id.vida3);
        pausa = findViewById(R.id.imgpausa);
        opcion1 = findViewById(R.id.btn1);
        opcion2 = findViewById(R.id.btn2);
        opcion3 = findViewById(R.id.btn3);
        opcion4 = findViewById(R.id.btn4);
        opcion1.setOnClickListener(this);
        opcion2.setOnClickListener(this);
        opcion3.setOnClickListener(this);
        opcion4.setOnClickListener(this);
        explosionField = ExplosionField.attach2Window(this);
        sonido = MediaPlayer.create(this, R.raw.bubble);
        waterSound= MediaPlayer.create(this,R.raw.water);
        waterSound.setLooping(true);
        waterSound.setVolume(0,0.2f);
        waterSound.start();
        pausa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PauseAnimation();
                showMenu();
            }
        });
        IniciarJuego();
    }

    public ArrayList<Pregunta> ListaDePreguntas(){
        ArrayList <Pregunta> lista = new <Pregunta> ArrayList();
        Pregunta pregunta = new Pregunta(R.drawable.operacion, "20/3", "3/20", "4/15", "15/4", "3/20");
        Pregunta pregunta2 = new Pregunta(R.drawable.operacion2, "12/8", "4/24", "8/12", "24/4", "8/12");
        Pregunta pregunta3 = new Pregunta(R.drawable.operacion3, "9/16", "24/6", "16/9", "24/6", "9/16");
        Pregunta pregunta4 = new Pregunta(R.drawable.operacion4, "7/18", "9/14", "18/7", "14/9", "9/14");
        Pregunta pregunta5 = new Pregunta(R.drawable.operacion5, "8/12", "12/8", "4/24", "24/4", "4/24");
        Pregunta pregunta6 = new Pregunta(R.drawable.operacion6, "10/24", "20/12", "10/24", "12/20", "12/20");
        Pregunta pregunta7 = new Pregunta(R.drawable.operacion7, "45/10", "10/45", "18/25", "25/18", "45/10");
        Pregunta pregunta8 = new Pregunta(R.drawable.operacion8, "15/7", "7/15", "35/3", "3/35", "7/15");
        Pregunta pregunta9 = new Pregunta(R.drawable.operacion9, "18/32", "12/48", "48/12", "32/18", "48/12");
        Pregunta pregunta10 = new Pregunta(R.drawable.operacion10, "5/12", "30/2", "12/5", "2/30", "2/30");
        Pregunta pregunta11 = new Pregunta(R.drawable.operacion11, "21/42", "49/18", "42/21", "18/49", "21/42");
        Pregunta pregunta12 = new Pregunta(R.drawable.operacion12, "12/56", "32/21", "21/32", "56/12", "32/21");
        Pregunta pregunta13 = new Pregunta(R.drawable.operacion13, "45/2", "5/18", "2/45", "18/5", "2/45");
        Pregunta pregunta14 = new Pregunta(R.drawable.operacion14, "24/24", "14/46", "12/48", "48/12", "48/12");
        Pregunta pregunta15 = new Pregunta(R.drawable.operacion15, "5/12", "12/5", "20/3", "3/20", "5/12");
        Pregunta pregunta16 = new Pregunta(R.drawable.operacion16, "16/3", "2/24", "3/16", "24/2", "2/24");
        Pregunta pregunta17 = new Pregunta(R.drawable.operacion17, "8/14", "7/3", "14/8", "3/7", "14/8");
        Pregunta pregunta18 = new Pregunta(R.drawable.operacion18, "2/18", "12/3", "18/2", "3/12", "3/12");
        Pregunta pregunta19 = new Pregunta(R.drawable.operacion19, "8/25", "40/5", "25/8", "5/40", "8/25");
        Pregunta pregunta20 = new Pregunta(R.drawable.operacion20, "6/10", "5/12", "10/6", "12/5", "5/12");
        Pregunta pregunta21 = new Pregunta(R.drawable.operacion21, "2/36", "36/2", "8/9", "9/8", "2/36");
        Pregunta pregunta22 = new Pregunta(R.drawable.operacion22, "36/15", "30/18", "15/36", "18/30", "30/18");
        Pregunta pregunta23 = new Pregunta(R.drawable.operacion23, "7/3", "21/1", "1/21", "3/7", "1/21");
        Pregunta pregunta24 = new Pregunta(R.drawable.operacion24, "8/40", "10/32", "40/8", "32/10", "32/10");
        Pregunta pregunta25 = new Pregunta(R.drawable.operacion25, "12/18", "36/6", "18/12", "6/36", "12/18");
        Pregunta pregunta26 = new Pregunta(R.drawable.operacion26, "45/16", "18/40", "16/45", "40/18", "18/40");
        Pregunta pregunta27 = new Pregunta(R.drawable.operacion27, "9/10", "18/5", "5/18", "10/9", "5/18");
        Pregunta pregunta28 = new Pregunta(R.drawable.operacion28, "24/8", "8/24", "16/12", "12/16", "12/16");
        Pregunta pregunta29 = new Pregunta(R.drawable.operacion29, "2/15", "3/10", "15/2", "10/3", "2/15");
        Pregunta pregunta30 = new Pregunta(R.drawable.operacion30, "48/4", "12/16", "16/12", "4/48", "12/16");
        Pregunta pregunta31 = new Pregunta(R.drawable.operacion31, "1/20", "5/4", "4/5", "20/1", "1/20");
        Pregunta pregunta32 = new Pregunta(R.drawable.operacion32, "5/16", "4/20", "20/4", "16/5", "4/20");
        Pregunta pregunta33 = new Pregunta(R.drawable.operacion33, "21/4", "3/28", "4/21", "28/3", "4/21");
        Pregunta pregunta34 = new Pregunta(R.drawable.operacion34, "27/6", "6/27", "9/18", "18/9", "18/9");
        Pregunta pregunta35 = new Pregunta(R.drawable.operacion35, "6/54", "27/12", "54/6", "12/27", "6/54");
        Pregunta pregunta36 = new Pregunta(R.drawable.operacion36, "25/42", "30/35", "42/25", "35/30", "30/35");
        Pregunta pregunta37 = new Pregunta(R.drawable.operacion37, "40/42", "56/30", "30/56", "42/40", "30/56");
        Pregunta pregunta38 = new Pregunta(R.drawable.operacion38, "14/54", "10/63", "63/10", "45/14", "45/14");
        Pregunta pregunta39 = new Pregunta(R.drawable.operacion39, "12/12", "6/24", "24/6", "18/12", "24/6");
        Pregunta pregunta40 = new Pregunta(R.drawable.operacion40, "10/14", "12/16", "16/12", "14/10", "16/12");
        lista.add(pregunta);
        lista.add(pregunta2);
        lista.add(pregunta3);
        lista.add(pregunta4);
        lista.add(pregunta5);
        lista.add(pregunta6);
        lista.add(pregunta7);
        lista.add(pregunta8);
        lista.add(pregunta9);
        lista.add(pregunta10);
        lista.add(pregunta11);
        lista.add(pregunta12);
        lista.add(pregunta13);
        lista.add(pregunta14);
        lista.add(pregunta15);
        lista.add(pregunta16);
        lista.add(pregunta17);
        lista.add(pregunta18);
        lista.add(pregunta19);
        lista.add(pregunta20);
        lista.add(pregunta21);
        lista.add(pregunta22);
        lista.add(pregunta23);
        lista.add(pregunta24);
        lista.add(pregunta25);
        lista.add(pregunta26);
        lista.add(pregunta27);
        lista.add(pregunta28);
        lista.add(pregunta29);
        lista.add(pregunta30);
        lista.add(pregunta31);
        lista.add(pregunta32);
        lista.add(pregunta33);
        lista.add(pregunta34);
        lista.add(pregunta35);
        lista.add(pregunta36);
        lista.add(pregunta37);
        lista.add(pregunta38);
        lista.add(pregunta39);
        lista.add(pregunta40);
        return lista;
    }

    public void IniciarJuego(){
        Preguntas = ListaDePreguntas();//this function return a "question" list
        imgoperacion.setImageResource(Preguntas.get(random).operacion);//get the image correspondent to question
        opcion1.setText(Preguntas.get(random).opc1);//
        opcion2.setText(Preguntas.get(random).opc2);//Add the text correspondent to question
        opcion3.setText(Preguntas.get(random).opc3);//
        opcion4.setText(Preguntas.get(random).opc4);//

        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        display.getRealMetrics(displayMetrics);

        heightDp = (displayMetrics.heightPixels*77)/100;

        thread = new Thread(){
            @Override
            public void run() {
                //runOnUiThread(new Runnable() {
                  //  @Override
                  //  public void run() {
                        checkHeight();
                    //}
                }
            };


        objectAnimator1 = ObjectAnimator.ofFloat(opcion1,"translationY",0f, -heightDp);
        objectAnimator1.setDuration(11000);
        objectAnimator1.addListener(this);
        objectAnimator2 = ObjectAnimator.ofFloat(opcion2, "translationY", 0f,-heightDp);
        objectAnimator2.setDuration(11000);
        objectAnimator2.addListener(this);
        objectAnimator3 = ObjectAnimator.ofFloat(opcion3, "translationY", 0f, -heightDp);
        objectAnimator3.setDuration(11000);
        objectAnimator3.addListener(this);
        objectAnimator4 = ObjectAnimator.ofFloat(opcion4, "translationY", 0f, -heightDp);
        objectAnimator4.setDuration(11000);
        objectAnimator4.addListener(this);
        objectAnimators.add(objectAnimator1);
        objectAnimators.add(objectAnimator2);
        objectAnimators.add(objectAnimator3);
        objectAnimators.add(objectAnimator4);

        objectAnimator1.start();
        objectAnimator2.start();
        objectAnimator3.start();
        objectAnimator4.start();

        correctAnswer();
        //thread.start();
        ValidaJuego();

    }

    public void ValidaJuego() {

        if (puntuacion == 0 && numjuegos == 0) {
            puntuacion = 1000;
        } else if (numjuegos == 10) {
            validateHeight = false;
            showMenu();
        }

    }

    @Override
    public void onClick(View v) {
        ArrayList <Pregunta> Preguntas = ListaDePreguntas();
        Button boton = (Button) v;
        if(!boton.getText().toString().equals(Preguntas.get(random).rta)){
            switch (vidas) {
                case 3:
                    cor3.setImageResource(R.drawable.corazon2);
                    vidas--;
                    puntuacion = (puntuacion - 25);
                    explosionField.explode(boton);
                    sonido.start();
                    boton.setVisibility(View.INVISIBLE);
                    break;
                case 2:
                    cor2.setImageResource(R.drawable.corazon2);
                    vidas--;
                    puntuacion = (puntuacion - 25);
                    sonido.start();
                     boton.setVisibility(View.INVISIBLE);
                    explosionField.explode(boton);
                    break;
                case 1:
                    cor1.setImageResource(R.drawable.corazon2);
                    puntuacion = (puntuacion - 50);
                    numjuegos++;
                    vidas = 3;
                    explosionField.explode(boton);
                    sonido.start();
                    boton.setVisibility(View.INVISIBLE);
                    disableeButtons();
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            objectAnimatorF.setCurrentFraction(1);
                            onAnimationEnd(objectAnimator1);
                        }
                    }, 2000);

                    break;
            }
            /*for(int i = 0; i < objectAnimators.size(); i++){
                if(objectAnimators.get(i).getTarget().equals(boton)) {
                    sonido.start();
                    boton.setVisibility(View.INVISIBLE);
                    //boton.setVisibility(View.GONE);
                    explosionField.explode(boton);
                }
            }*/
        }
        else {
            boton.setBackgroundResource(R.drawable.burbujaverde);
            Intent intent = getIntent();

            numjuegos++;
            vidas = 3;
            correctAnswer = true;

            if (numjuegos == 10) {
                showMenu();
            } else {
                disableeButtons();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        objectAnimatorF.setCurrentFraction(1);
                        onAnimationEnd(objectAnimator1);
                    }
                }, 1000);
                //startActivity(intent);
            }
        }
    }

    private void  generateNewOperation() {
        random = (int) (Math.random() * 40);

        imgoperacion.setImageResource(Preguntas.get(random).operacion);//get the image correspondent to question

        opcion1.setText(Preguntas.get(random).opc1);
        opcion2.setText(Preguntas.get(random).opc2);//Add the text correspondent to question
        opcion3.setText(Preguntas.get(random).opc3);//
        opcion4.setText(Preguntas.get(random).opc4);

        correctAnswer();

    }

    private void disableeButtons(){
        opcion1.setEnabled(false);
        opcion2.setEnabled(false);
        opcion3.setEnabled(false);
        opcion4.setEnabled(false);
    }

    private void enableButtons(){
        opcion1.setEnabled(true);
        opcion2.setEnabled(true);
        opcion3.setEnabled(true);
        opcion4.setEnabled(true);
    }

    public void checkHeight(){
            Looper.prepare();

            ObjectAnimator objectAnimatorF = new ObjectAnimator();
            Button button1 = (Button) objectAnimator1.getTarget();
            Button button2 = (Button) objectAnimator2.getTarget();
            Button button3 = (Button) objectAnimator3.getTarget();
            Button button4 = (Button) objectAnimator4.getTarget();
            if(button1.getText().toString().equals(Preguntas.get(random).rta) ){
                objectAnimatorF = objectAnimator1;
            }else if(button2.getText().toString().equals(Preguntas.get(random).rta) ){
                objectAnimatorF = objectAnimator2;
            }else if(button3.getText().toString().equals(Preguntas.get(random).rta) ){
                objectAnimatorF = objectAnimator3;
            }else if(button4.getText().toString().equals(Preguntas.get(random).rta) ){
                objectAnimatorF = objectAnimator4;
            }
        try {
            while (validateHeight) {
                if(!stopThread) {
                    if (objectAnimatorF.getAnimatedFraction() == 1.0) {
                        break;
                    }
                }else {
                    return;
                }
            }
            if (validateHeight && !stopThread) {
                bubblesAgin();
                numjuegos++;
                stopThread = false;
                Looper.loop();
            }
        }catch (NullPointerException npe){
            Log.e(TAG, "checkHeight: "+ npe);
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

    private void  bubblesAgin(){

        /*thread = null;
        if(correctAnswer){
            opcion1.setBackgroundResource(R.drawable.burbuja);
            opcion2.setBackgroundResource(R.drawable.burbuja);
            opcion3.setBackgroundResource(R.drawable.burbuja);
            opcion4.setBackgroundResource(R.drawable.burbuja);

            opcion1.setVisibility(View.VISIBLE);
            opcion2.setVisibility(View.VISIBLE);
            opcion3.setVisibility(View.VISIBLE);
            opcion4.setVisibility(View.VISIBLE);
            correctAnswer = false;
        }*/

        /*reset( opcion1);
        reset(opcion2);
        reset(opcion3);
        reset(opcion4);
        reset((View)objectAnimator1.getTarget());
        reset((View)objectAnimator2.getTarget());
        reset((View)objectAnimator3.getTarget());
        reset((View)objectAnimator4.getTarget());*/


        objectAnimator1 = ObjectAnimator.ofFloat(opcion1,"translationY",0f, -heightDp);
        objectAnimator1.setDuration(11000);
        objectAnimator1.addListener(this);
        objectAnimator1.setRepeatCount(10);
        objectAnimator1.start();
        objectAnimator2 = ObjectAnimator.ofFloat(opcion2, "translationY", 0f,-heightDp);
        objectAnimator2.setDuration(11000);
        objectAnimator2.addListener(this);

        objectAnimator2.start();
        objectAnimator3 = ObjectAnimator.ofFloat(opcion3, "translationY", 0f, -heightDp);
        objectAnimator3.setDuration(11000);
        objectAnimator3.addListener(this);
        objectAnimator3.start();
        objectAnimator4 = ObjectAnimator.ofFloat(opcion4, "translationY", 0f, -heightDp);
        objectAnimator4.setDuration(11000);
        objectAnimator4.addListener(this);
        objectAnimator4.start();
        objectAnimators.add(objectAnimator1);
        objectAnimators.add(objectAnimator2);
        objectAnimators.add(objectAnimator3);
        objectAnimators.add(objectAnimator4);

        /*objectAnimators.add(objectAnimator1);
        objectAnimators.add(objectAnimator2);
        objectAnimators.add(objectAnimator3);
        objectAnimators.add(objectAnimator4);*/

        generateNewOperation();

        Thread thread2 = new Thread() {
            @Override
            public void run() {

                checkHeight();
            }
        };
        //thread2.start();
        ValidaJuego();
    }

    public void showMenu(){
        LayoutInflater layoutInflater = getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.game_over,null);

        final Button btn_restar = view.findViewById(R.id.btn_restart_b);
        Button btn_home = view.findViewById(R.id.btn_home_b);
        Button btn_mute = view.findViewById(R.id.btn_mute_b);
        TextView puntaje = view.findViewById(R.id.txtpuntuacionb);

        final AlertDialog.Builder menu = new AlertDialog.Builder(BubblesMain.this).setCancelable(false);
        menu.setView(view);
        final AlertDialog ad = menu.create();
        ad.show();

        if(numjuegos < 10){
            btn_restar.setText("Resumen");
            puntaje.setVisibility(View.INVISIBLE);
        }else {
            puntaje.setVisibility(View.VISIBLE);
            puntaje.setText(puntaje.getText()+" "+puntuacion+"/1000");
        }

        btn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Practica.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("numFragment",3);
                getApplicationContext().startActivity(intent);
            }
        });

        btn_restar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btn_restar.getText().equals("Resumen")){
                    ResumeAnimation();
                    ad.dismiss();
                }else{
                    numjuegos = 0;
                    puntuacion = 0;
                    Intent intent = getIntent();
                    intent.putExtra("llave", (numjuegos));
                    intent.putExtra("puntuacion",puntuacion);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    finish();
                    startActivity(intent);
                }
            }
        });
    }

    public void PauseAnimation(){
        currentAnimation = objectAnimator1.getCurrentPlayTime();
        for (int i = 0; i < objectAnimators.size(); i++ ){
            objectAnimators.get(i).cancel();
        }
    }

    public void ResumeAnimation(){
        for (int i = 0; i < objectAnimators.size(); i++ ){
            objectAnimators.get(i).setCurrentPlayTime(currentAnimation);
            objectAnimators.get(i).start();
        }
    }

    /**
     * This method allow us to change the buttons visivility to "VISIBLE"
     */
    private void changeButtonsVisibility(){
        opcion1.setVisibility(View.VISIBLE);
        opcion2.setVisibility(View.VISIBLE);
        opcion3.setVisibility(View.VISIBLE);
        opcion4.setVisibility(View.VISIBLE);

        cor1.setImageResource(R.drawable.corazon);
        cor2.setImageResource(R.drawable.corazon);
        cor3.setImageResource(R.drawable.corazon);

        opcion1.setBackgroundResource(R.drawable.burbuja);
        opcion2.setBackgroundResource(R.drawable.burbuja);
        opcion3.setBackgroundResource(R.drawable.burbuja);
        opcion4.setBackgroundResource(R.drawable.burbuja);

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), Practica.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        finish();
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        waterSound.stop();
    }

    @Override
    protected void onPause() {
        super.onPause();
        waterSound.pause();
        PauseAnimation();
    }

    @Override
    protected void onResume() {
        super.onResume();
        waterSound.start();
        ResumeAnimation();
    }

    private void correctAnswer(){
        objectAnimatorF = new ObjectAnimator();
        Button button1 = (Button) objectAnimator1.getTarget();
        Button button2 = (Button) objectAnimator2.getTarget();
        Button button3 = (Button) objectAnimator3.getTarget();
        Button button4 = (Button) objectAnimator4.getTarget();
        if(button1.getText().toString().equals(Preguntas.get(random).rta) ){
            objectAnimatorF = objectAnimator1;
        }else if(button2.getText().toString().equals(Preguntas.get(random).rta) ){
            objectAnimatorF = objectAnimator2;
        }else if(button3.getText().toString().equals(Preguntas.get(random).rta) ){
            objectAnimatorF = objectAnimator3;
        }else if(button4.getText().toString().equals(Preguntas.get(random).rta) ){
            objectAnimatorF = objectAnimator4;
        }
    }

    @Override
    public void onAnimationStart(Animator animator) {
        Toast.makeText(this, "an animation has started", Toast.LENGTH_SHORT).show();
        //correctAnswer();
    }

    @Override
    public void onAnimationEnd(Animator animator) {
        if(objectAnimatorF != null) {
            if (objectAnimatorF.getAnimatedFraction() == 1.0) {
                //objectAnimator1.setStartDelay(1000);
                //objectAnimator2.setStartDelay(1000);
                //objectAnimator3.setStartDelay(1000);
                //objectAnimator4.setStartDelay(1000);
                changeButtonsVisibility();
                enableButtons();
                reset((View) objectAnimator1.getTarget());
                reset((View) objectAnimator2.getTarget());
                reset((View) objectAnimator3.getTarget());
                reset((View) objectAnimator4.getTarget());

                objectAnimatorF = null;
                objectAnimator1.start();
                objectAnimator2.start();
                objectAnimator3.start();
                objectAnimator4.start();

                generateNewOperation();

            }
        }
    }

    @Override
    public void onAnimationCancel(Animator animator) {

    }

    @Override
    public void onAnimationRepeat(Animator animator) {

    }
}

