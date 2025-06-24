package GLISERV.GesImp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import GLISERV.GesImp.model.Inventario;
import GLISERV.GesImp.model.Producto;

@Repository
public interface InventarioRepository extends JpaRepository<Inventario, Long> {
    
    List<Inventario> findByMaterial(String material);

    Inventario findById(Integer id);

    List<Inventario> findByProducto(Producto producto);   

    @Query("""
        SELECT i FROM Inventario i
        JOIN i.producto p
        JOIN Pedido ped ON ped.producto = p
        JOIN ped.usuario u
        WHERE p.tipo = :tipoProducto
        AND u.rol = :rolUsuario
        AND i.cantidad > 0
    """)
    List<Inventario> buscarInventarioPorTipoYRolUsuario(
        @Param("tipoProducto") String tipoProducto,
        @Param("rolUsuario") String rolUsuario
    );

}
