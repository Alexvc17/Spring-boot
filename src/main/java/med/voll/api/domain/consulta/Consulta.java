package med.voll.api.domain.consulta;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.paciente.Paciente;

import java.time.LocalDateTime;

//esta clas va a mapear la entidad medico
@Entity(name="Consulta")
@Table(name="consultas")
@Getter
@NoArgsConstructor
@AllArgsConstructor
//lo que hace es usar el parametro de id para las comparaciones entre medicos
@EqualsAndHashCode(of="id")
public class Consulta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="medico_id")
    private Medico medico;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="peciente_id")
    private Paciente paciente;

    private LocalDateTime data;


}
