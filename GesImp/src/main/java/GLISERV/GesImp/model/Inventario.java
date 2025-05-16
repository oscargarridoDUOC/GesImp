package GLISERV.GesImp.model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;


@Entity
@Table(name = "inventario")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Inventario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 100)
    private String material;

    @Column(nullable = false, length = 100)
    private Integer cantidad;

    @ManyToOne
    @JoinColumn(name = "producto_id", nullable = false)
    private Producto producto;
}
