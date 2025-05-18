package GLISERV.GesImp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import GLISERV.GesImp.model.Producto;
import GLISERV.GesImp.repository.ProductoRepository;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    public List<Producto> findAll() {
        return productoRepository.findAll();
    }

    public Producto findById(Long id) {
        return productoRepository.findById(id).orElse(null);
    }

    public List<Producto> findByNombre(String nombre) {
        return productoRepository.findByNombre(nombre);
    }

    public Producto save(Producto producto) {
        return productoRepository.save(producto);
    }

    public void deleteById(Long id) {
        productoRepository.deleteById(id);
    }

    public Producto update(Long id, Producto producto) {
        Optional<Producto> productoOptional = productoRepository.findById(id);
        if (productoOptional.isPresent()) {
            Producto productoToUpdate = productoOptional.get();
            productoToUpdate.setNombre(producto.getNombre());
            productoToUpdate.setTipo(producto.getTipo());
            productoToUpdate.setPrecio(producto.getPrecio());
            return productoRepository.save(productoToUpdate);
        } else {
            return null;
        }
    }

    public Producto patch(Long id, Producto productoParcial) {
        Optional<Producto> productoOptional = productoRepository.findById(id);
        if (productoOptional.isPresent()) {
            Producto productoToUpdate = productoOptional.get();

            if (productoParcial.getNombre() != null) {
                productoToUpdate.setNombre(productoParcial.getNombre());
            }

            if (productoParcial.getTipo() != null) {
                productoToUpdate.setTipo(productoParcial.getTipo());
            }

            if (productoParcial.getPrecio() != null) {
                productoToUpdate.setPrecio(productoParcial.getPrecio());
            }

            return productoRepository.save(productoToUpdate);
        } else {
            return null;
        }
    }
}
