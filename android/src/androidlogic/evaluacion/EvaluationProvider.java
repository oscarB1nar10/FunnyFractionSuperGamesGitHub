package androidlogic.evaluacion;

import java.util.List;

import androidlogic.evaluacion.interfaces.EvaluationInteractor;
import androidlogic.evaluacion.interfaces.EvaluationView;
import androidlogic.retrofitClasses.EvaluationResponse;
import androidlogic.retrofitClasses.Questions;
import common.CallbackRetrofitListener;

public class EvaluationProvider  {

    EvaluationView evaluationView;
    EvaluationInteractor evaluationInteractor;

    public EvaluationProvider(EvaluacionActivity evaluacionActivity, EvaluationInteractor evaluationInteractorImpl) {
        this.evaluationView = evaluacionActivity;
        this.evaluationInteractor = evaluationInteractorImpl;
    }


    public void getEvaluationQ(){
        evaluationView.showPGB();
        evaluationInteractor.getQuestions(new CallbackRetrofitListener<EvaluationResponse>() {
            @Override
            public void onResponse(EvaluationResponse resultado) {
                evaluationView.hidePGB();
                evaluationView.questionList(resultado.getData().getPreguntas());
            }

            @Override
            public void onFailure(Throwable error) {
                evaluationView.hidePGB();
            }
        });


    }


}
