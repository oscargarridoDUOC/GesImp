package GLISERV.GesImp.controller;

import GLISERV.GesImp.model.Producto;
import GLISERV.GesImp.service.ProductoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import GLISERV.GesImp.assemblers.ProductoModelAssembler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequestMapping("/api/v2/productos")
@Tag(name = "Producto", description = "Ve, crea, edita o elimina los productos")
public class ProductoControllerV2 {

    @Autowired
    private ProductoService productoService;

    @Autowired
    private ProductoModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Obtiene todos los productos", description = "Obtiene una lista con todos los productos")
    public CollectionModel<EntityModel<Producto>> getAllProductos() {
        List<EntityModel<Producto>> productos = productoService.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(productos,
                linkTo(methodOn(ProductoControllerV2.class).getAllProductos()).withSelfRel());
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Obtiene los productos por ID", description = "Ingresa un ID para obtener un producto")
    public ResponseEntity<EntityModel<Producto>> getProductoById(@PathVariable Long id) {
        Producto producto = productoService.findById(id);
        if (producto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(producto));
    }

    @GetMapping(value = "/nombre/{nombre}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Obtiene los productos por Nombre", description = "Ingresa un Nombre para obtener un producto")
    public ResponseEntity<List<EntityModel<Producto>>> getProductoByNombre(@PathVariable String nombre) {
        List<Producto> productos = productoService.findByNombre(nombre);
        if (productos == null || productos.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        
        List<EntityModel<Producto>> productosModel = new ArrayList<>();
        for (Producto producto : productos) {
            productosModel.add(EntityModel.of(producto));
        }
        
        return ResponseEntity.ok(productosModel);
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Crea un producto", description = "Ingresa los parametros para crear un producto")
    public ResponseEntity<EntityModel<Producto>> createProducto(@RequestBody Producto producto) {
        Producto nuevo = productoService.save(producto);
        return ResponseEntity
                .created(linkTo(methodOn(ProductoControllerV2.class).getProductoById(Long.valueOf(nuevo.getId()))).toUri())
                .body(assembler.toModel(nuevo));
    }

    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Actualiza un producto", description = "Ingresa los parametros para actualizar un producto (solo ingresa los cambios)")
    public ResponseEntity<EntityModel<Producto>> updateProducto(@PathVariable Integer id, @RequestBody Producto producto) {
        producto.setId(id);
        Producto actualizado = productoService.save(producto);
        if (actualizado == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(actualizado));
    }

    @PatchMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Actualiza un producto", description = "Ingresa los parametros para actualizar un producto")
    public ResponseEntity<EntityModel<Producto>> patchProducto(@PathVariable Long id, @RequestBody Producto producto) {
        Producto patched = productoService.patch(id, producto);
        if (patched == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(patched));
    }

    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Borra los productos por ID", description = "Ingresa un ID para borrar un producto")
    public ResponseEntity<Void> deleteProducto(@PathVariable Long id) {
        Producto existente = productoService.findById(id);
        if (existente == null) {
            return ResponseEntity.notFound().build();
        }
        productoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
