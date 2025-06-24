package GLISERV.GesImp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import GLISERV.GesImp.model.Producto;
import GLISERV.GesImp.service.ProductoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@RestController
@RequestMapping("/api/v1/productos")
@Tag(name = "Producto", description = "Ve, crea, edita o elimina los productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @GetMapping
    @Operation(summary = "Obtiene todos los productos", description = "Obtiene una lista con todos los productos")
    public List<Producto> getAllProductos() {
        return productoService.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtiene los productos por ID", description = "Ingresa un ID para obtener un producto")
    public ResponseEntity<Producto> getProductoById(@PathVariable Long id) {
        Producto producto = productoService.findById(id);
        if (producto != null) {
            return ResponseEntity.ok(producto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/nombre")
    @Operation(summary = "Obtiene los productos por Nombre", description = "Ingresa un Nombre para obtener un producto")
    public List<Producto> getProductosByNombre(@RequestParam("nombre") String nombre) {
        return productoService.findByNombre(nombre);
    }

    @PostMapping
    @Operation(summary = "Crea un producto", description = "Ingresa los parametros para crear un producto")
    public Producto create(@RequestBody Producto producto) {
        return productoService.save(producto);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Borra los productos por ID", description = "Ingresa un ID para borrar un producto")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        productoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualiza un producto", description = "Ingresa los parametros para actualizar un producto (solo ingresa los cambios)")
    public ResponseEntity<Producto> update(@PathVariable Long id, @RequestBody Producto producto) {
        Producto updatedProducto = productoService.update(id, producto);
        if (updatedProducto != null) {
            return ResponseEntity.ok(updatedProducto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Actualiza un producto", description = "Ingresa los parametros para actualizar un producto")
    public ResponseEntity<Producto> patch(@PathVariable Long id, @RequestBody Producto productoParcial) {
        Producto updatedProducto = productoService.patch(id, productoParcial);
        if (updatedProducto != null) {
            return ResponseEntity.ok(updatedProducto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
