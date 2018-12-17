package common;

public interface CallbackRetrofitListener<T> {

    /**
     * Accion a realizar cuando se obtuvo una respuesta del servidor
     *
     * @param resultado
     */
    void onResponse(T resultado);

    /**
     * Accion a realizar cuando ha ocurrido un error
     *
     * @param error
     */
    void onFailure(Throwable error);

}
