package GLISERV.GesImp.controller;

import GLISERV.GesImp.assemblers.PedidoModelAssembler;
import GLISERV.GesImp.model.Pedido;
import GLISERV.GesImp.service.PedidoService;
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
@RequestMapping(value = "/api/v2/pedidos")
@Tag(name = "Pedido", description = "Ve, crea, edita o elimina los pedidos")
public class PedidoControllerV2 {

    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private PedidoModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Obtiene todos los pedidos", description = "Obtiene una lista con todos los pedidos")
    public CollectionModel<EntityModel<Pedido>> getAllPedidos() {
        List<EntityModel<Pedido>> pedidos = pedidoService.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(pedidos,
                linkTo(methodOn(PedidoControllerV2.class).getAllPedidos()).withSelfRel());
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Obtiene los pedidos por ID", description = "Ingresa un ID para obtener un pedido")
    public ResponseEntity<EntityModel<Pedido>> getPedidoById(@PathVariable Long id) {
        Pedido pedido = pedidoService.findById(id);
        if (pedido == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(pedido));
    }

    @GetMapping(value = "/buscar/q3", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Buscar pedidos por estado, producto y usuario", description = "Filtra los pedidos según el estado del pedido, el nombre del producto y el nombre del usuario"
    )
    public ResponseEntity<CollectionModel<EntityModel<Pedido>>> buscarPedidosPorEstadoProductoYUsuario(
            @RequestParam String estado,
            @RequestParam String nombreProducto,
            @RequestParam String nombreUsuario) {

        List<Pedido> pedidos = pedidoService.buscarPedidosPorEstadoProductoYUsuario(estado, nombreProducto, nombreUsuario);

        if (pedidos == null || pedidos.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        List<EntityModel<Pedido>> list = new ArrayList<>();
        for (Pedido p : pedidos) {
            list.add(assembler.toModel(p));
        }

        return ResponseEntity.ok(CollectionModel.of(list));
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Crea un pedido", description = "Ingresa los parametros para crear un pedido")
    public ResponseEntity<EntityModel<Pedido>> createPedido(@RequestBody Pedido pedido) {
        Pedido nuevo = pedidoService.save(pedido);
        return ResponseEntity
                .created(linkTo(methodOn(PedidoControllerV2.class).getPedidoById(Long.valueOf(nuevo.getId()))).toUri())
                .body(assembler.toModel(nuevo));
    }

    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Actualiza un pedido", description = "Ingresa los parametros para actualizar un pedido (solo ingresa los cambios)")
    public ResponseEntity<EntityModel<Pedido>> updatePedido(@PathVariable Long id, @RequestBody Pedido pedido) {
        pedido.setId(id.intValue());
        Pedido actualizado = pedidoService.update(id, pedido);
        if (actualizado == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(actualizado));
    }

    @PatchMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Actualiza un pedido", description = "Ingresa los parametros para actualizar un pedido")
    public ResponseEntity<EntityModel<Pedido>> patchPedido(@PathVariable Long id, @RequestBody Pedido pedido) {
        Pedido actualizado = pedidoService.patch(id, pedido);
        if (actualizado == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(actualizado));
    }

    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Borra los pedidos por ID", description = "Ingresa un ID para borrar un pedido")
    public ResponseEntity<Void> deletePedido(@PathVariable Long id) {
        Pedido existente = pedidoService.findById(id);
        if (existente == null) {
            return ResponseEntity.notFound().build();
        }
        pedidoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
