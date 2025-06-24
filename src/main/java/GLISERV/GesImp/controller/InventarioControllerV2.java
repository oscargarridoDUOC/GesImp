package GLISERV.GesImp.controller;

import GLISERV.GesImp.assemblers.InventarioModelAssembler;
import GLISERV.GesImp.model.Inventario;
import GLISERV.GesImp.service.InventarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequestMapping(value = "/api/v2/inventarios")
@Tag(name = "Inventario", description = "Ve, crea, edita o elimina los inventario")
public class InventarioControllerV2 {

    @Autowired
    private InventarioService inventarioService;

    @Autowired
    private InventarioModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Obtiene todos los inventarios", description = "Obtiene una lista con todos los inventarios")
    public CollectionModel<EntityModel<Inventario>> getAllInventarios() {
        List<EntityModel<Inventario>> inventarios = inventarioService.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(inventarios,
                linkTo(methodOn(InventarioControllerV2.class).getAllInventarios()).withSelfRel());
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Obtiene los inventarios por ID", description = "Ingresa un ID para obtener un inventario")
    public ResponseEntity<EntityModel<Inventario>> getInventarioById(@PathVariable Long id) {
        Inventario inventario = inventarioService.findById(id);
        if (inventario == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(inventario));
    }

    @GetMapping(value = "/material/{material}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Obtiene los inventarios por Material", description = "Ingresa un Material para obtener un inventario")
    public ResponseEntity<CollectionModel<EntityModel<Inventario>>> getInventarioByMaterial(@PathVariable String material) {
        List<Inventario> resultados = inventarioService.findByMaterial(material);
        if (resultados == null || resultados.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        List<EntityModel<Inventario>> inventariosModel = new ArrayList<>();
        for (Inventario inv : resultados) {
            inventariosModel.add(EntityModel.of(inv));
        }

        CollectionModel<EntityModel<Inventario>> collectionModel = CollectionModel.of(inventariosModel);
        return ResponseEntity.ok(collectionModel);
    }

    @GetMapping(value = "/buscar/q4", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Buscar inventario disponible por tipo de producto y rol de usuario",description = "Retorna materiales con stock, filtrando por tipo de producto y rol de usuario en pedidos relacionados")
    public ResponseEntity<CollectionModel<EntityModel<Inventario>>> buscarInventarioPorTipoYRolUsuario(
            @RequestParam String tipoProducto,
            @RequestParam String rolUsuario) {

        List<Inventario> inventarios = inventarioService.buscarInventarioPorTipoYRolUsuario(tipoProducto, rolUsuario);

        if (inventarios == null || inventarios.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        List<EntityModel<Inventario>> list = new ArrayList<>();
        for (Inventario i : inventarios) {
            list.add(assembler.toModel(i));
        }

        return ResponseEntity.ok(CollectionModel.of(list));
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Crea un inventario", description = "Ingresa los parametros para crear un inventario")
    public ResponseEntity<EntityModel<Inventario>> createInventario(@RequestBody Inventario inventario) {
        Inventario nuevo = inventarioService.save(inventario);
        return ResponseEntity
                .created(linkTo(methodOn(InventarioControllerV2.class).getInventarioById(Long.valueOf(nuevo.getId()))).toUri())
                .body(assembler.toModel(nuevo));
    }

    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Actualiza un inventario", description = "Ingresa los parametros para actualizar un inventario (solo ingresa los cambios)")
    public ResponseEntity<EntityModel<Inventario>> updateInventario(@PathVariable Long id, @RequestBody Inventario inventario) {
        inventario.setId(id.intValue());
        Inventario actualizado = inventarioService.save(inventario);
        if (actualizado == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(actualizado));
    }

    @PatchMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Actualiza un inventario", description = "Ingresa los parametros para actualizar un inventario")
    public ResponseEntity<EntityModel<Inventario>> patchInventario(@PathVariable Long id, @RequestBody Inventario inventario) {
        Inventario actualizado = inventarioService.patch(id, inventario);
        if (actualizado == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(actualizado));
    }

    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Borra los inventarios por ID", description = "Ingresa un ID para borrar un inventario")
    public ResponseEntity<Void> deleteInventario(@PathVariable Long id) {
        Inventario existente = inventarioService.findById(id);
        if (existente == null) {
            return ResponseEntity.notFound().build();
        }
        inventarioService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
