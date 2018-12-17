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
    private List<EvaluationC> data;

    public EvaluationResponse() {

    }

    public Boolean getCode() {
        return code;
    }

    public void setCode(Boolean code) {
        this.code = code;
    }

    public List<EvaluationC> getData() {
        return data;
    }

    public void setData(List<EvaluationC> data) {
        this.data = data;
    }
}
