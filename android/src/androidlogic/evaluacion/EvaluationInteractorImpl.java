package androidlogic.evaluacion;

import com.funnyfractions.game.R;

import androidlogic.evaluacion.interfaces.EvaluationInteractor;
import androidlogic.retrofitClasses.EvaluationResponse;
import common.CallbackRetrofitListener;
import endPoints.Evaluation;
import okhttp3.OkHttpClient;
import okhttp3.internal.Util;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EvaluationInteractorImpl implements EvaluationInteractor {
    private Retrofit mRetrofit;
    private Evaluation evaluation;

    @Override
    public void getQuestions(final CallbackRetrofitListener<EvaluationResponse> callbackRetrofitListener) {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();

        mRetrofit = new Retrofit.Builder()
                .baseUrl("https://funnyfractios000.000webhostapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        evaluation = mRetrofit.create(Evaluation.class);

        Call<EvaluationResponse> call = evaluation.getQuestions();

        //Llamado asíncrono
        call.enqueue(new Callback<EvaluationResponse>() {
            @Override
            public void onResponse(Call<EvaluationResponse> call, Response<EvaluationResponse> response) {
                if(response.body() != null){
                    EvaluationResponse evaluationResponse = new EvaluationResponse();
                    evaluationResponse = response.body();
                    callbackRetrofitListener.onResponse(evaluationResponse);
                }else{
                    callbackRetrofitListener.onFailure(new Throwable("an error has occurred"));
                }
            }

            @Override
            public void onFailure(Call<EvaluationResponse> call, Throwable t) {

            }
        });

}

}
