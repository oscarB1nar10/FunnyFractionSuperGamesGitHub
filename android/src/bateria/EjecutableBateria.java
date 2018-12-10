package bateria;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.funnyfractions.game.R;

import java.util.ArrayList;

public class EjecutableBateria extends AppCompatActivity implements View.OnClickListener{
    ArrayList<Pregunta> preguntas;
    TextView enunciado, pregunta,respuesta1,respuesta2,respuesta3,respuesta4;
    ArrayList<TextView> txtvList = new ArrayList<>();
    ImageView operacion, selector;
    LottieAnimationView animacionBateria;
    int random = 0;
    final static int[] VFRAME= new int[]{6, 12, 18, 24, 30, 36, 40, 50, 54, 60, 68, 74};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ejecutable_bateria);
        animacionBateria=findViewById(R.id.animacionBateria);
        animacionBateria.useHardwareAcceleration(true);
        // animacionBateria.setRepeatCount(5);
        enunciado=findViewById(R.id.enunciado);
        operacion=findViewById(R.id.operacion);
        selector = findViewById(R.id.selector);
        pregunta=findViewById(R.id.pregunta);
        preguntas= new ArrayList<>();
        //_----------------------------------
        respuesta1=findViewById(R.id.valor2);
        txtvList.add(respuesta1);
        respuesta1.setOnClickListener(this);
        respuesta2=findViewById(R.id.valor3);
        txtvList.add(respuesta2);
        respuesta2.setOnClickListener(this);
        respuesta3=findViewById(R.id.valor4);
        txtvList.add(respuesta3);
        respuesta3.setOnClickListener(this);
        respuesta4=findViewById(R.id.valor5);
        txtvList.add(respuesta4);
        respuesta4.setOnClickListener(this);
        //__-------------------------------
        anadirEnunciados();
        extraerPreguntas();
        asignarValores();
    }

    private void asignarValores() {
        int randomN = (int) (Math.random()*8+1);
        int randomA = (int)(Math.random()*4+1);

        txtvList.get(0).setText(""+(randomN));
        txtvList.get(1).setText(""+(randomN+1));
        txtvList.get(2).setText(""+(randomN+2));
        txtvList.get(3).setText(""+(randomN+3));

        txtvList.get(randomA-1).setText(""+preguntas.get((random-1)).respuesta);

    }

    public void extraerPreguntas(){
        random =(int)(Math.random()*10+1);
        //generar valor aleatorio-> añadir valores a la vista
        enunciado.setText(preguntas.get(random-1).enunciado);
        operacion.setImageResource(preguntas.get(random-1).operacion);
        pregunta.setText(preguntas.get(random-1).pregunta);
    }

    public void anadirEnunciados(){
        preguntas.add(new Pregunta("La bateria ha estado cargando durante un tiempo igual a la siguiente operacion", R.drawable.op2, "¿cuanto nivel de carga tiene la bateria?",2));
        preguntas.add(new Pregunta("La bateria ha estado cargando durante un tiempo igual a la siguiente operacion", R.drawable.op1, "¿cuanto nivel de carga tiene la bateria?",1));
        preguntas.add(new Pregunta("La bateria ha estado cargando durante un tiempo igual a la siguiente operacion", R.drawable.op3, "¿cuanto nivel de carga tiene la bateria?",3));
        preguntas.add(new Pregunta("La bateria ha estado cargando durante un tiempo igual a la siguiente operacion", R.drawable.op4, "¿cuanto nivel de carga tiene la bateria?",2));
        preguntas.add(new Pregunta("La bateria ha estado cargando durante un tiempo igual a la siguiente operacion", R.drawable.op5, "¿cuanto nivel de carga tiene la bateria?",4));
        preguntas.add(new Pregunta("La bateria ha estado cargando durante un tiempo igual a la siguiente operacion", R.drawable.op6, "¿cuanto nivel de carga tiene la bateria?",5));
        preguntas.add(new Pregunta("La bateria ha estado cargando durante un tiempo igual a la siguiente operacion", R.drawable.op7, "¿cuanto nivel de carga tiene la bateria?",6));
        preguntas.add(new Pregunta("La bateria ha estado cargando durante un tiempo igual a la siguiente operacion", R.drawable.op8, "¿cuanto nivel de carga tiene la bateria?",7));
        preguntas.add(new Pregunta("La bateria ha estado cargando durante un tiempo igual a la siguiente operacion", R.drawable.op9, "¿cuanto nivel de carga tiene la bateria?",8));
        preguntas.add(new Pregunta("La bateria ha estado cargando durante un tiempo igual a la siguiente operacion", R.drawable.op10, "¿cuanto nivel de carga tiene la bateria?",9));
        preguntas.add(new Pregunta("La bateria ha estado cargando durante un tiempo igual a la siguiente operacion", R.drawable.op11, "¿cuanto nivel de carga tiene la bateria?",1));
        preguntas.add(new Pregunta("La bateria ha estado cargando durante un tiempo igual a la siguiente operacion", R.drawable.op12, "¿cuanto nivel de carga tiene la bateria?",2));
        preguntas.add(new Pregunta("La bateria ha estado cargando durante un tiempo igual a la siguiente operacion", R.drawable.op13, "¿cuanto nivel de carga tiene la bateria?",3));
        preguntas.add(new Pregunta("La bateria ha estado cargando durante un tiempo igual a la siguiente operacion", R.drawable.op14, "¿cuanto nivel de carga tiene la bateria?",4));
        preguntas.add(new Pregunta("La bateria ha estado cargando durante un tiempo igual a la siguiente operacion", R.drawable.op15, "¿cuanto nivel de carga tiene la bateria?",5));
        preguntas.add(new Pregunta("La bateria ha estado cargando durante un tiempo igual a la siguiente operacion", R.drawable.op16, "¿cuanto nivel de carga tiene la bateria?",6));
        preguntas.add(new Pregunta("La bateria ha estado cargando durante un tiempo igual a la siguiente operacion", R.drawable.op17, "¿cuanto nivel de carga tiene la bateria?",7));
        preguntas.add(new Pregunta("La bateria ha estado cargando durante un tiempo igual a la siguiente operacion", R.drawable.op18, "¿cuanto nivel de carga tiene la bateria?",8));
        preguntas.add(new Pregunta("La bateria ha estado cargando durante un tiempo igual a la siguiente operacion", R.drawable.op19, "¿cuanto nivel de carga tiene la bateria?",9));


    }

    public int extraerFrameParaAnimar(int respuesta){
        return VFRAME[(respuesta-1)];
    }
    @Override
    public void onClick(View view) {

        //El atributo "respuesta" pEr a pregunta se obtiene a partir del valor aleatorio generado en la funcion "extraer preguntas"
        switch (view.getId()){
            case R.id.valor2:
                selector.setImageResource(R.drawable.s3);
                if(Integer.parseInt((String) respuesta1.getText()) == preguntas.get((random-1)).respuesta){
                    animacionBateria.addAnimatorUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator valueAnimator) {
                            if(animacionBateria.getProgress()==1.0f){
                                //aca se debe definir el frame hasta el cual animar para la repeticion numero 5.
                                animacionBateria.setMaxFrame(extraerFrameParaAnimar(preguntas.get((random-1)).respuesta));
                            }

                        }
                    });
                    animacionBateria.setSpeed(0.92f);
                    animacionBateria.playAnimation();
                    animacionBateria.setRepeatCount(1);
                }
                break;
            case R.id.valor3:
                selector.setImageResource(R.drawable.s4);
                if(Integer.parseInt((String) respuesta2.getText()) == preguntas.get((random-1)).respuesta){
                    animacionBateria.addAnimatorUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator valueAnimator) {
                            if(animacionBateria.getProgress()==1.0f){
                                //aca se debe definir el frame hasta el cual animar para la repeticion numero 5.
                                animacionBateria.setMaxFrame(extraerFrameParaAnimar(preguntas.get((random-1)).respuesta));
                            }

                        }
                    });
                    animacionBateria.setSpeed(0.92f);
                    animacionBateria.playAnimation();
                    animacionBateria.setRepeatCount(1);
                }
                break;
            case R.id.valor4:
                selector.setImageResource(R.drawable.s2);
                if(Integer.parseInt((String) respuesta3.getText()) == preguntas.get((random-1)).respuesta){
                    animacionBateria.addAnimatorUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator valueAnimator) {
                            if(animacionBateria.getProgress()==1.0f){
                                //aca se debe definir el frame hasta el cual animar para la repeticion numero 5.
                                animacionBateria.setMaxFrame(extraerFrameParaAnimar(preguntas.get((random-1)).respuesta));
                            }

                        }
                    });
                    animacionBateria.setSpeed(0.92f);
                    animacionBateria.playAnimation();
                    animacionBateria.setRepeatCount(1);
                }
                break;
            case R.id.valor5:
                selector.setImageResource(R.drawable.s1);
                if(Integer.parseInt((String) respuesta4.getText()) == preguntas.get((random-1)).respuesta){
                    animacionBateria.addAnimatorUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator valueAnimator) {
                            if(animacionBateria.getProgress()==1.0f){
                                //aca se debe definir el frame hasta el cual animar para la repeticion numero 5.
                                animacionBateria.setMaxFrame(extraerFrameParaAnimar(preguntas.get((random-1)).respuesta));
                            }

                        }
                    });
                    animacionBateria.setSpeed(0.92f);
                    animacionBateria.playAnimation();
                    animacionBateria.setRepeatCount(1);
                }
                break;
        }
    }

    public class Pregunta {

        public String enunciado, pregunta;
        int operacion, respuesta;

        Pregunta(String enunciado, int operacion, String pregunta, int respuesta){
            this.enunciado=enunciado;
            this.operacion=operacion;
            this.pregunta=pregunta;
            this.respuesta=respuesta;
        }
    }
}
