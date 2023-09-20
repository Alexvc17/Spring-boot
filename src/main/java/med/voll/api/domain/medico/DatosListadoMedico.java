package med.voll.api.domain.medico;

public record DatosListadoMedico(Long id, String nombre, String especialidad, String documento, String email ) {

    //creamos un constructor que reciba los datos del medico como entidad
    public DatosListadoMedico(Medico medico){

        this(medico.getId(), medico.getNombre(), medico.getEspecialidad().toString(), medico.getDocumento(), medico.getEmail());
    }


}

