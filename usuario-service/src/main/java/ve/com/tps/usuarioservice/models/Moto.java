package ve.com.tps.usuarioservice.models;

import lombok.Data;

//GENERAMOS UN MODELO DE MOTO CON EL QUE NOS COMUNICAREMOS USANDO EL REST TEMPLATE
//CON EL MICROSERVICIO DE MOTO-SERVICE
@Data
public class Moto {

    private Integer id;
    private String marca;
    private String modelo;

    private Integer usuario;
}
