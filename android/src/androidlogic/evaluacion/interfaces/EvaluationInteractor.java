package androidlogic.evaluacion.interfaces;
import androidlogic.retrofitClasses.EvaluationResponse;
import common.CallbackRetrofitListener;

public interface EvaluationInteractor {

    void getQuestions(CallbackRetrofitListener<EvaluationResponse> callbackRetrofitListener);
}
