package GLISERV.GesImp.controller;

import java.util.List;

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

import GLISERV.GesImp.model.Inventario;
import GLISERV.GesImp.service.InventarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/inventarios")
@Tag(name = "Inventario", description = "Ve, crea, edita o elimina los inventario")
public class InventarioController {

    @Autowired
    private InventarioService inventarioService;

    @GetMapping
    @Operation(summary = "Obtiene todos los inventarios", description = "Obtiene una lista con todos los inventarios")
    public ResponseEntity<List<Inventario>> getAll() {
        List<Inventario> inventarios = inventarioService.findAll();
        if (inventarios.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(inventarios);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtiene los inventarios por ID", description = "Ingresa un ID para obtener un inventario")
    public ResponseEntity<Inventario> getById(@PathVariable Long id) {
        Inventario inventario = inventarioService.findById(id);
        if (inventario == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(inventario);
    }

    @GetMapping("/material")
    @Operation(summary = "Obtiene los inventarios por Material", description = "Ingresa un Material para obtener un inventario")
    public ResponseEntity<List<Inventario>> getByMaterial(@RequestParam("material") String material) {
        List<Inventario> resultados = inventarioService.findByMaterial(material);
        if (resultados.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(resultados);
        }
    }

    @GetMapping("/buscar/q4")
    @Operation(summary = "Buscar inventario disponible por tipo de producto y rol de usuario",description = "Retorna materiales con stock, filtrando por tipo de producto y rol de usuario en pedidos relacionados")
    public ResponseEntity<List<Inventario>> buscarInventarioPorTipoYRolUsuario(@RequestParam String tipoProducto,@RequestParam String rolUsuario) {
        List<Inventario> inventarios = inventarioService.buscarInventarioPorTipoYRolUsuario(tipoProducto, rolUsuario);
        if (inventarios.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(inventarios);
    }

    @PostMapping
    @Operation(summary = "Crea un inventario", description = "Ingresa los parametros para crear un inventario")
    public ResponseEntity<Inventario> create(@RequestBody Inventario inventario) {
        Inventario nuevoInventario = inventarioService.save(inventario);
        return ResponseEntity.status(201).body(nuevoInventario);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualiza un inventario", description = "Ingresa los parametros para actualizar un inventario (solo ingresa los cambios)")
    public ResponseEntity<Inventario> update(@PathVariable Long id, @RequestBody Inventario inventario) {
        Inventario actualizado = inventarioService.update(id, inventario);
        if (actualizado == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(actualizado);
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Actualiza un inventario", description = "Ingresa los parametros para actualizar un inventario")
    public ResponseEntity<Inventario> patch(@PathVariable Long id, @RequestBody Inventario inventarioParcial) {
        Inventario actualizado = inventarioService.patch(id, inventarioParcial);
        if (actualizado == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Borra los inventarios por ID", description = "Ingresa un ID para borrar un inventario")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            inventarioService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
