package GLISERV.GesImp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import GLISERV.GesImp.model.EstadoPedido;
import java.util.List;

@Repository
public interface EstadoPedidoRepository extends JpaRepository<EstadoPedido, Long> {
    
    List<EstadoPedido> findByNombre(String nombre);

    EstadoPedido findById(Integer id);
}

