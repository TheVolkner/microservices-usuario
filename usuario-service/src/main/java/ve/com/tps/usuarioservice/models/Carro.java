package ve.com.tps.usuarioservice.models;

import lombok.Data;

//GENERAMOS UN MODELO DE CARRO CON EL QUE NOS COMUNICAREMOS USANDO EL REST TEMPLATE
//CON EL MICROSERVICIO DE CARRO-SERVICE
@Data
public class Carro {

    private Integer id;
    private String marca;
    private String modelo;
    private Integer usuario;
}
