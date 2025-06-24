package GLISERV.GesImp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import GLISERV.GesImp.model.Producto;
import java.util.List;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {

    List<Producto> findByNombre(String nombre);

    Producto findById(Integer id);

    @Query("SELECT p FROM Producto p WHERE p.tipo = :tipo AND p.precio < :precioMax")
    List<Producto> buscarPorTipoYPrecioMaximo(@Param("tipo") String tipo, @Param("precioMax") Integer precioMax);

}
