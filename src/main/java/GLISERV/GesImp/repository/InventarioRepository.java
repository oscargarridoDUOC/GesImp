package GLISERV.GesImp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import GLISERV.GesImp.model.Inventario;

@Repository
public interface InventarioRepository extends JpaRepository<Inventario, Long> {
    
    List<Inventario> findByMaterial(String material);
    Inventario findById(Integer id);
    
}
