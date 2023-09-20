package med.voll.api.infra.errores;

//Throwable -> responde ante errores y exepciones
//RunTimeExcepton -> responde ante excepciones
public class ValidacionDeIntegridad extends RuntimeException {
    public ValidacionDeIntegridad(String s) {
        super(s);
    }
}
