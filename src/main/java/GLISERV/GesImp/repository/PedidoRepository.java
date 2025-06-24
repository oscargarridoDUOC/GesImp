package GLISERV.GesImp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import GLISERV.GesImp.model.EstadoPedido;
import GLISERV.GesImp.model.Pedido;
import GLISERV.GesImp.model.Producto;

import java.util.List;


@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    Pedido findById(Integer id);

    List<Pedido> findByUsuarioId(Long usuarioId); 

    List<Pedido> findByEstado(EstadoPedido estado);

    List<Pedido> findByProducto(Producto producto);

    @Query("""
        SELECT p FROM Pedido p
        JOIN p.estado e
        JOIN p.producto prod
        JOIN p.usuario u
        WHERE e.nombre = :estado
        AND prod.nombre = :nombreProducto
        AND u.nombre = :nombreUsuario
    """)
    List<Pedido> buscarPedidosPorEstadoProductoYUsuario(
        @Param("estado") String estado,
        @Param("nombreProducto") String nombreProducto,
        @Param("nombreUsuario") String nombreUsuario
    );

}
