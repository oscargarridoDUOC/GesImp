package GLISERV.GesImp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import GLISERV.GesImp.model.Factura;
import GLISERV.GesImp.model.Pedido;
import GLISERV.GesImp.model.TipoPago;

@Repository
public interface FacturaRepository extends JpaRepository<Factura, Long> {

    Factura findById(Integer id);

    List<Factura> findByTipoPago(TipoPago tipoPago);

    List<Factura> findByPedido(Pedido pedido);

    @Query("SELECT f FROM Factura f WHERE f.tipoPago.nombre = :tipoPago AND f.total >= :montoMinimo")
    List<Factura> buscarPorTipoPagoYTotalMinimo(@Param("tipoPago") String tipoPago, @Param("montoMinimo") Integer montoMinimo);

    @Query("""
        SELECT f FROM Factura f
        JOIN f.pedido p
        JOIN p.usuario u
        JOIN p.producto prod
        JOIN f.tipoPago tp
        WHERE u.nombre = :nombreUsuario
        AND prod.tipo = :tipoProducto
        AND tp.nombre = :nombreTipoPago
    """)
    List<Factura> buscarFacturasPorUsuarioProductoYTipoPago(
        @Param("nombreUsuario") String nombreUsuario,
        @Param("tipoProducto") String tipoProducto,
        @Param("nombreTipoPago") String nombreTipoPago
    );
    
    @Query("""
        SELECT f FROM Factura f
        JOIN f.pedido p
        JOIN p.usuario u
        JOIN p.producto prod
        WHERE u.nombre = :nombreUsuario
        AND prod.nombre = :nombreProducto
    """)
    List<Factura> buscarFacturasPorUsuarioYProducto(
        @Param("nombreUsuario") String nombreUsuario,
        @Param("nombreProducto") String nombreProducto
    );


}
