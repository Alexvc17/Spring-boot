package med.voll.api.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.domain.direccion.DatosDireccion;
import med.voll.api.domain.medico.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;



@RestController
@RequestMapping("/medicos")
public class MedicoController {

    @Autowired//forma para inyectar el repositorio
    private MedicoRepository medicoRepository;

    //si quiero mapear un request tipo post hago lo siguiente:
    //response entity es un Wrapper que encapsula la respuesta que se envia al servidor
    @PostMapping
    public ResponseEntity<DatosRespuestaMedico> registrarMedico(@RequestBody @Valid DatosRegistroMedico datosRegistroMedico, UriComponentsBuilder uriComponentsBuilder){

        //retornar 204 y la URL donde se puede encontrar a ese medico | ejm: localhost:8080/medico/4
        //aqui guardado el cuerpo del medico que he guardado y hay que crear un constructor para que me acepte parametro
        // de tipo datosregistromedico
        Medico medico = medicoRepository.save(new Medico(datosRegistroMedico));
        DatosRespuestaMedico datosRespuestaMedico = new DatosRespuestaMedico(medico.getId(), medico.getNombre(), medico.getEmail(),
                medico.getTelefono(), medico.getEspecialidad().toString(),
                new DatosDireccion(medico.getDireccion().getCalle(), medico.getDireccion().getDistrito(),
                        medico.getDireccion().getCiudad(), medico.getDireccion().getNumero(),
                        medico.getDireccion().getComplemento()));

        URI url = uriComponentsBuilder.path("/medicos/{id}").buildAndExpand(medico.getId()).toUri();
        //URI url = UriComponentsBuilder.path("/medicos/{id}").buildAndExpand(medico.getId()).toUri();
        return ResponseEntity.created(url).body(datosRespuestaMedico);



    }

    @GetMapping
    public ResponseEntity<Page<DatosListadoMedico>> listadoMedicos(@PageableDefault(size = 2) Pageable paginacion){
        //En resumen, esta línea de código toma una lista de objetos Medico, los convierte en un stream,
        // mapea cada objeto Medico a un objeto DatosListadoMedico utilizando el constructor definido en esa
        // clase, y luego recopila todos estos objetos mapeados en una nueva lista. Esta lista resultante es
        // lo que se devuelve como resultado de la función listadoMedicos().//crea un nuevo regitro por cada medico que llega de la lista
        return ResponseEntity.ok().body(medicoRepository.findAllByActivoTrue(paginacion).map(DatosListadoMedico::new));
    }


    @PutMapping
    @Transactional//con esto se le dice que cuando termine el metodo se va a hacer un commit de los datos a nivel de BD
    public ResponseEntity actualizarMedico(@RequestBody @Valid DatosActualizarMedico datosActualizarMedico){
        //con esto envio el id y este lo busca en la bd y nos trae el medico con ese ID
        Medico medico = medicoRepository.getReferenceById(datosActualizarMedico.id());
        medico.actualizarDatos(datosActualizarMedico);

        //no es bueno retornar directamente la entidad, por lo que es necesario crear un DTO
        //entonces ahora retorno los datos de mi DTO de mi medico
        return ResponseEntity.ok(new DatosRespuestaMedico(medico.getId(), medico.getNombre(), medico.getEmail(),
                medico.getTelefono(), medico.getEspecialidad().toString(),
                new DatosDireccion(medico.getDireccion().getCalle(), medico.getDireccion().getDistrito(),
                        medico.getDireccion().getCiudad(), medico.getDireccion().getNumero(),
                        medico.getDireccion().getComplemento())));
    }

    //DELETE LOGICO
    @DeleteMapping("/{id}")
    @Transactional
    //le decimos que viene de un path variable osea de medicos/3
    public ResponseEntity eliminarMedico(@PathVariable Long id){
        Medico medico = medicoRepository.getReferenceById(id);
        medico.desactivarMedico();

        //si quiero retornar un codigo 204
        return ResponseEntity.noContent().build();
    }


    //retornar un medico con un ID en especifico
    @GetMapping("/{id}")
    //le decimos que viene de un path variable osea de medicos/3
    public ResponseEntity<DatosRespuestaMedico> retornaDatosMedicos(@PathVariable Long id){
        Medico medico = medicoRepository.getReferenceById(id);
        var datosMedicos = new DatosRespuestaMedico(medico.getId(), medico.getNombre(), medico.getEmail(),
                medico.getTelefono(), medico.getEspecialidad().toString(),
                new DatosDireccion(medico.getDireccion().getCalle(), medico.getDireccion().getDistrito(),
                        medico.getDireccion().getCiudad(), medico.getDireccion().getNumero(),
                        medico.getDireccion().getComplemento()));
        return ResponseEntity.ok(datosMedicos);
    }




}
