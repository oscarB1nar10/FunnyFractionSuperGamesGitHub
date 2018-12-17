package endPoints;

import androidlogic.retrofitClasses.EvaluationResponse;
import retrofit2.Call;
import retrofit2.http.GET;

public interface Evaluation {

    @GET(/*"api_devel/*/"evaluation.php")
    Call<EvaluationResponse> getQuestions(
    );

}
