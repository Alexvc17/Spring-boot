package med.voll.api.domain.consulta;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//con esta interfaz seremos capaces de gestionar lo de bd | CRUD
//extendera de otra interfaz propia de spring la cual necesita dos parametros
// 1. el tipo de entidad con la cual voy a trabajar  |  2. tipo de objeto del id
@Repository
public interface ConsultaRepository extends JpaRepository<Consulta,Long> {




}
