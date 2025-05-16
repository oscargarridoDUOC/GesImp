package GLISERV.GesImp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import GLISERV.GesImp.model.PedidoProducto;

@Repository
public interface PedidoProductoRepository extends JpaRepository<PedidoProducto, Integer> {
    PedidoProducto findById(long id);
}