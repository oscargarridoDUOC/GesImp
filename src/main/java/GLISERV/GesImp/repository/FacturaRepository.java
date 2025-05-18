package GLISERV.GesImp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import GLISERV.GesImp.model.Factura;

@Repository
public interface FacturaRepository extends JpaRepository<Factura, Long> {

    Factura findById(Integer id);
}
