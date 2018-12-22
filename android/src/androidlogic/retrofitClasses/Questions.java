package androidlogic.retrofitClasses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Questions {

    @SerializedName("Pregunta")
    @Expose
    private String pregunta;
    @SerializedName("Opcion1")
    @Expose
    private String opcion1;
    @SerializedName("Opcion2")
    @Expose
    private String opcion2;
    @SerializedName("Opcion3")
    @Expose
    private String opcion3;
    @SerializedName("Opcion4")
    @Expose
    private String opcion4;
    @SerializedName("Respuesta")
    @Expose
    private String respuesta;

    public Questions(){ }

    public String getPregunta() {
        return pregunta;
    }

    public void setPregunta(String pregunta) {
        this.pregunta = pregunta;
    }

    public String getOpcion1() {
        return opcion1;
    }

    public void setOpcion1(String opcion1) {
        this.opcion1 = opcion1;
    }

    public String getOpcion2() {
        return opcion2;
    }

    public void setOpcion2(String opcion2) {
        this.opcion2 = opcion2;
    }

    public String getOpcion3() {
        return opcion3;
    }

    public void setOpcion3(String opcion3) {
        this.opcion3 = opcion3;
    }

    public String getOpcion4() {
        return opcion4;
    }

    public void setOpcion4(String opcion4) {
        this.opcion4 = opcion4;
    }

    public String getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }
}
