package androidlogic.retrofitClasses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class EvaluationResponse {

    @SerializedName("code")
    @Expose
    private Boolean code;
    @SerializedName("data")
    @Expose
    private QuestionBase data;

    public EvaluationResponse(){ }


    public Boolean getCode() {
        return code;
    }

    public void setCode(Boolean code) {
        this.code = code;
    }

    public QuestionBase getData() {
        return data;
    }

    public void setData(QuestionBase data) {
        this.data = data;
    }
}
