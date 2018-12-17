package androidlogic.evaluacion;

import androidlogic.evaluacion.interfaces.EvaluationInteractor;
import androidlogic.evaluacion.interfaces.EvaluationView;
import androidlogic.retrofitClasses.EvaluationResponse;
import common.CallbackRetrofitListener;

public class EvaluationProvider implements EvaluationView {
    EvaluationView evaluationView;
    EvaluationInteractor evaluationInteractor;
    public EvaluationProvider(EvaluacionActivity evaluacionActivity, EvaluationInteractor evaluationInteractorImpl) {
        this.evaluationView = evaluacionActivity;
        this.evaluationInteractor = evaluationInteractorImpl;
    }


    public void getEvaluationQ(){

        evaluationInteractor.getQuestions(new CallbackRetrofitListener<EvaluationResponse>() {
            @Override
            public void onResponse(EvaluationResponse resultado) {

            }

            @Override
            public void onFailure(Throwable error) {

            }
        });


    }
}
