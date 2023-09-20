package med.voll.api.domain.medico;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;

//con esta interfaz seremos capaces de gestionar lo de bd | CRUD
//extendera de otra interfaz propia de spring la cual necesita dos parametros
// 1. el tipo de entidad con la cual voy a trabajar  |  2. tipo de objeto del id
public interface MedicoRepository extends JpaRepository<Medico,Long> {
    //me retorna una pagina de medicos
    Page<Medico> findAllByActivoTrue(Pageable paginacion);


    //con query opdemos hacer consultas en la BD
    // usamos id not in tiene que encontrarse disponible, entonces si el id tiene una fecha asignada
    //no va cumplir con la consulta porque el medico no esta disponible ya que ya tiene una fecha asignada para su cita
    @Query("""
       select m from Medico m
       where m.activo= 1
       and
       m.especialidad=:especialidad
       and
       m.id not in( 
           select c.medico.id from Consulta c
           where
           c.fecha=:fecha
       )
       order by rand()
       limit 1
       """)

    Medico seleccionarMedicoConEspecialidadEnFecha(Especialidad especialidad, LocalDateTime fecha);
}
