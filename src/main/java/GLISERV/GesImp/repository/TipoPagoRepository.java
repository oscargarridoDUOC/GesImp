package GLISERV.GesImp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import GLISERV.GesImp.model.TipoPago;
import java.util.List;

@Repository
public interface TipoPagoRepository extends JpaRepository<TipoPago, Long> {

    List<TipoPago> findByNombre(String nombre);

    TipoPago findById(Integer id);
}
