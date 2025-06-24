package GLISERV.GesImp.controller;

import GLISERV.GesImp.assemblers.EstadoPedidoModelAssembler;
import GLISERV.GesImp.model.EstadoPedido;
import GLISERV.GesImp.service.EstadoPedidoService;
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
@RequestMapping(value = "/api/v2/estado-pedidos")
@Tag(name = "Estado Pedido", description = "Ve, crea, edita o elimina los estados de los pedidos")
public class EstadoPedidoControllerV2 {

    @Autowired
    private EstadoPedidoService estadoPedidoService;

    @Autowired
    private EstadoPedidoModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Obtiene todos los estados", description = "Obtiene una lista con todos los estados de los pedidos")
    public CollectionModel<EntityModel<EstadoPedido>> getAllEstadoPedidos() {
        List<EntityModel<EstadoPedido>> estadoPedidos = estadoPedidoService.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(estadoPedidos,
                linkTo(methodOn(EstadoPedidoControllerV2.class).getAllEstadoPedidos()).withSelfRel());
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Obtiene los estados por ID", description = "Ingresa un ID para obtener un estado de pedido")
    public ResponseEntity<EntityModel<EstadoPedido>> getEstadoPedidoById(@PathVariable Long id) {
        EstadoPedido estadoPedido = estadoPedidoService.findById(id);
        if (estadoPedido == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(estadoPedido));
    }

    @GetMapping("/nombre")
    @Operation(summary = "Obtiene los estados por Nombre", description = "Ingresa un Nombre para obtener un estado de pedido")
    public ResponseEntity<CollectionModel<EntityModel<EstadoPedido>>> getByNombre(@RequestParam("nombre") String nombre) {
        List<EstadoPedido> resultados = estadoPedidoService.findByNombre(nombre);
        if (resultados.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        List<EntityModel<EstadoPedido>> list = new ArrayList<>();
        for (EstadoPedido e : resultados) {
            list.add(assembler.toModel(e));
        }

        return ResponseEntity.ok(CollectionModel.of(list,
            linkTo(methodOn(EstadoPedidoControllerV2.class).getByNombre(nombre)).withSelfRel()));
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Crea un estado de pedido", description = "Ingresa los parametros para crear un estado de pedido")
    public ResponseEntity<EntityModel<EstadoPedido>> createEstadoPedido(@RequestBody EstadoPedido estadoPedido) {
        EstadoPedido nuevo = estadoPedidoService.save(estadoPedido);
        return ResponseEntity
                .created(linkTo(methodOn(EstadoPedidoControllerV2.class).getEstadoPedidoById(Long.valueOf(nuevo.getId()))).toUri())
                .body(assembler.toModel(nuevo));
    }

    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Actualiza un estado de pedido", description = "Ingresa los parametros para actualizar un estado de pedido (solo ingresa los cambios)")
    public ResponseEntity<EntityModel<EstadoPedido>> updateEstadoPedido(@PathVariable Long id, @RequestBody EstadoPedido estadoPedido) {
        estadoPedido.setId(id.intValue());
        EstadoPedido updated = estadoPedidoService.save(estadoPedido);
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(updated));
    }

    @PatchMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Actualiza un estado de pedido", description = "Ingresa los parametros para actualizar un estado de pedido")
    public ResponseEntity<EntityModel<EstadoPedido>> patchEstadoPedido(@PathVariable Long id, @RequestBody EstadoPedido estadoPedido) {
        EstadoPedido patched = estadoPedidoService.patch(id, estadoPedido);
        if (patched == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(patched));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Borra los estados por ID", description = "Ingresa un ID para borrar un estado de pedido")
    public ResponseEntity<Void> deleteEstadoPedido(@PathVariable Long id) {
        EstadoPedido existing = estadoPedidoService.findById(id);
        if (existing == null) {
            return ResponseEntity.notFound().build();
        }
        estadoPedidoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}