package GLISERV.GesImp.model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table(name = "producto")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 50)
    private String nombre;
    
    @Column(nullable = false)
    private String tipo;
    
    @Column(nullable = false)
    private Integer precio;
}
