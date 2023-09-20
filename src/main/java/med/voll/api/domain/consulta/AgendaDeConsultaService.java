package med.voll.api.domain.consulta;


import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.medico.MedicoRepository;
import med.voll.api.domain.paciente.Paciente;
import med.voll.api.domain.paciente.PacienteRepository;
import med.voll.api.infra.errores.ValidacionDeIntegridad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//esta clase que ez un servicio hara las validaciones
@Service
public class AgendaDeConsultaService {
    //para que spring sepa donde inyecetar el valor usamos autowired
    //inyectamos nuestras clases de repositorio dentro de nuestro servicio
    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private ConsultaRepository consultaRepository;
    //va a recibir el recor datos agendaconsulta
    public void agendar(DatosAgendarConsulta datos) throws ValidacionDeIntegridad {

        if(pacienteRepository.findById(datos.idPaciente()).isPresent()){
            throw new ValidacionDeIntegridad("Este id para el paciente no fue encontrado");
        }

        if(datos.idMedico()!=null && medicoRepository.existsById(datos.idMedico())){

            throw new ValidacionDeIntegridad("Este id para el medico     no fue encontrado");

        }

        //hacemos una consulta a la base de datosa traves de nuestros parametros
        //y estamos recibiendo los datos con el id del paciente de medico y fecha de consulta
        var paciente  = pacienteRepository.findById(datos.idPaciente()).get();

        var medico = seleccionarMedico(datos);

        var consulta = new Consulta(null, medico,paciente,datos.fecha());
        System.out.println(consulta);
        consultaRepository.save(consulta);

    }


    //aqui construimos el metodo que nos permite seleccionar un medico de forma aleatorio en la BD
    private Medico seleccionarMedico(DatosAgendarConsulta datos) {
        if(datos.idMedico()!=null){
            return medicoRepository.getReferenceById(datos.idMedico());
        }
        if(datos.especialidad()==null){
            throw new ValidacionDeIntegridad("debe seleccionarse una especialidad para el medico");
        }

        //en caso de que la especialidad no sea nula
        //creamos una interface y le enviamos dos datos esoecialidad y fecha
        return medicoRepository.seleccionarMedicoConEspecialidadEnFecha(datos.especialidad(),datos.fecha());
    }
}
