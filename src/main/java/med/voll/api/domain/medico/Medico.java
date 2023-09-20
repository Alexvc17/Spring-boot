package med.voll.api.domain.medico;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.voll.api.domain.direccion.Direccion;

//esta clas va a mapear la entidad medico
@Entity(name="Medico")
@Table(name="medicos")
@Getter
@NoArgsConstructor
@AllArgsConstructor
//lo que hace es usar el parametro de id para las comparaciones entre medicos
@EqualsAndHashCode(of="id")
public class Medico {

    @Id//con esto le especificamos que id es la llave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String email;
    private String telefono;
    private String documento;
    private Boolean activo;
    @Enumerated(EnumType.STRING)
    private Especialidad especialidad;
    @Embedded//Le colocamos embedded poque tanto el medico como los usuarios van a usar esta tabla
    private Direccion direccion;


    public Medico(DatosRegistroMedico datosRegistroMedico) {

        //aqui voy a hacer el mapeo de los datos que vienen desde DatosRegistroMedico
        this.activo = true;
        this.nombre = datosRegistroMedico.nombre();
        this.email = datosRegistroMedico.email();
        this.telefono = datosRegistroMedico.telefono();
        this.documento = datosRegistroMedico.documento();
        this.especialidad = datosRegistroMedico.especialidad();
        this.direccion = new Direccion(datosRegistroMedico.direccion());
    }

    public void actualizarDatos(DatosActualizarMedico datosActualizarMedico) {

        if(datosActualizarMedico.nombre() !=null){
            this.nombre = datosActualizarMedico.nombre();
        }

        if(datosActualizarMedico.documento() !=null){
            this.documento = datosActualizarMedico.documento();
        }

        if(datosActualizarMedico.direccion() !=null){
            this.direccion = direccion.actualizarDatos(datosActualizarMedico.direccion());
        }
    }


    public void desactivarMedico() {
        this.activo = false;
    }
}
