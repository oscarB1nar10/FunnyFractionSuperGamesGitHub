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
    TextView enunciado;
    ImageView operacion;
    TextView pregunta;
    TextView respuesta1,respuesta2,respuesta3,respuesta4;
    LottieAnimationView animacionBateria;
    int repeticiones=0;
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
        pregunta=findViewById(R.id.pregunta);
        preguntas= new ArrayList<Pregunta>();
        //_----------------------------------
        respuesta1=findViewById(R.id.valor2);
        respuesta1.setOnClickListener(this);
        respuesta2=findViewById(R.id.valor3);
        respuesta2.setOnClickListener(this);
        respuesta3=findViewById(R.id.valor4);
        respuesta3.setOnClickListener(this);
        respuesta4=findViewById(R.id.valor5);
        respuesta4.setOnClickListener(this);
        //__-------------------------------
        añadirEnunciados();
        extraerPreguntas();
    }
    public void extraerPreguntas(){
        //generar valor aleatorio-> añadir valores a la vista
        enunciado.setText(preguntas.get(0).enunciado);
        operacion.setImageResource(preguntas.get(0).operacion);
        pregunta.setText(preguntas.get(0).pregunta);

    }

    public void añadirEnunciados(){
        preguntas.add(new Pregunta("La bateria ha estado cargando durante un tiempo igual a la siguiente operacion", R.drawable.op2, "¿cuanto nivel de carga tiene la bateria?",2));

    }

    public int extraerFrameParaAnimar(int respuesta){
      return VFRAME[(respuesta-1)];
    }

    @Override
    public void onClick(View view) {
        //El atributo "respuesta" pEr a pregunta se obtiene a partir del valor aleatorio generado en la funcion "extraer preguntas"
        switch (view.getId()){
            case R.id.valor2:
                    if(Integer.parseInt((String) respuesta1.getText())==preguntas.get(0).respuesta){
                        animacionBateria.setMaxFrame(3);
                        animacionBateria.playAnimation();
                        //Toast.makeText(this, "condicional1",Toast.LENGTH_LONG).show();
                    }
                break;
            case R.id.valor3:
                    if(Integer.parseInt((String) respuesta2.getText())==preguntas.get(0).respuesta){
                        animacionBateria.playAnimation();
                        //
                    }

                break;

            case R.id.valor4:
                    if(Integer.parseInt((String) respuesta3.getText())==preguntas.get(0).respuesta){

                        animacionBateria.addAnimatorUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                            @Override
                            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                               if(animacionBateria.getProgress()==1.0f){
                                   repeticiones++;
                                   if(repeticiones==3){
                                       //aca se debe definir el frame hasta el cual animar para la repeticion numero 5.
                                       animacionBateria.setMaxFrame(extraerFrameParaAnimar(preguntas.get(0).respuesta));

                                   }
                               }

                            }
                        });
                        animacionBateria.setSpeed(0.92f);
                        animacionBateria.playAnimation();
                        animacionBateria.setRepeatCount(3);
                    }

                break;

            case R.id.valor5:
                    if(Integer.parseInt((String) respuesta4.getText())==preguntas.get(0).respuesta){
                        animacionBateria.setMaxFrame(3);
                        animacionBateria.playAnimation();
                        //Toast.makeText(this, "condicional1",Toast.LENGTH_LONG).show();
                    }

                break;
        }
    }

    public class Pregunta {

        public String enunciado;
        public int operacion;
        public String pregunta;
        public int respuesta;

        public Pregunta(String enunciado, int operacion, String pregunta, int respuesta){
            this.enunciado=enunciado;
            this.operacion=operacion;
            this.pregunta=pregunta;
            this.respuesta=respuesta;

        }


    }
}
