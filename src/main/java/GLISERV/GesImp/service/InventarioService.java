package GLISERV.GesImp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import GLISERV.GesImp.model.Inventario;
import GLISERV.GesImp.repository.InventarioRepository;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class InventarioService {

    @Autowired
    private InventarioRepository inventarioRepository;

    public List<Inventario> findAll() {
        return inventarioRepository.findAll();
    }

    public Inventario findById(Long id) {
        return inventarioRepository.findById(id).orElse(null);
    }

    public List<Inventario> findByMaterial(String material) {
        return inventarioRepository.findByMaterial(material);
    }
    public Inventario save(Inventario inventario) {
        return inventarioRepository.save(inventario);
    }

    public void deleteById(Long id) {
        inventarioRepository.deleteById((long) id);
    }

    public Inventario update(Long id, Inventario inventario) {
        Optional<Inventario> inventarioOptional = inventarioRepository.findById(id);
        if (inventarioOptional.isPresent()) {
            Inventario inventarioToUpdate = inventarioOptional.get();
            inventarioToUpdate.setMaterial(inventario.getMaterial());
            inventarioToUpdate.setCantidad(inventario.getCantidad());
            inventarioToUpdate.setProducto(inventario.getProducto());
            return inventarioRepository.save(inventarioToUpdate);
        } else {
            return null;
        }
    }

    public Inventario patch(Long id, Inventario inventarioParcial) {
        Optional<Inventario> inventarioOptional = inventarioRepository.findById(id);
        if (inventarioOptional.isPresent()) {
            Inventario inventarioToUpdate = inventarioOptional.get();

            if (inventarioParcial.getMaterial() != null) {
                inventarioToUpdate.setMaterial(inventarioParcial.getMaterial());
            }

            if (inventarioParcial.getCantidad() != null) {
                inventarioToUpdate.setCantidad(inventarioParcial.getCantidad());
            }

            if (inventarioParcial.getProducto() != null) {
                inventarioToUpdate.setProducto(inventarioParcial.getProducto());
            }

            return inventarioRepository.save(inventarioToUpdate);
        } else {
            return null;
        }
    }

}
