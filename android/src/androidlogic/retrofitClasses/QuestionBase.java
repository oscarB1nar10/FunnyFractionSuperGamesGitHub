package androidlogic.retrofitClasses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class QuestionBase {

    @SerializedName("Preguntas")
    @Expose
    private List<Questions> preguntas = null;

    public QuestionBase(){ }

    public List<Questions> getPreguntas() {
        return preguntas;
    }

    public void setPreguntas(List<Questions> preguntas) {
        this.preguntas = preguntas;
    }
}
