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

import GLISERV.GesImp.model.Pedido;
import GLISERV.GesImp.service.PedidoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@RestController
@RequestMapping("/api/v1/pedidos")
@Tag(name = "Pedido", description = "Ve, crea, edita o elimina los pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @GetMapping
    @Operation(summary = "Obtiene todos los pedidos", description = "Obtiene una lista con todos los pedidos")
    public ResponseEntity<List<Pedido>> getAll() {
        List<Pedido> pedidos = pedidoService.findAll();
        if (pedidos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(pedidos);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtiene los pedidos por ID", description = "Ingresa un ID para obtener un pedido")
    public ResponseEntity<Pedido> getById(@PathVariable Long id) {
        Pedido pedido = pedidoService.findById(id);
        if (pedido == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(pedido);
    }


    @GetMapping("/usuario")
    @Operation(summary = "Obtiene los pedidos por Usuario", description = "Ingresa un Usuario para obtener una lista de sus pedidos")
    public ResponseEntity<List<Pedido>> getByUsuario(@RequestParam("usuarioId") Long usuarioId) {
        List<Pedido> resultados = pedidoService.findByUsuario(usuarioId);
        if (resultados.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(resultados);
        }
    }

    @GetMapping("/buscar/q3")
    @Operation(summary = "Buscar pedidos por estado, producto y usuario", description = "Filtra los pedidos según el estado del pedido, el nombre del producto y el nombre del usuario")
    public ResponseEntity<List<Pedido>> buscarPedidosPorEstadoProductoYUsuario(
        @RequestParam String estado,
        @RequestParam String nombreProducto,
        @RequestParam String nombreUsuario) {

        List<Pedido> pedidos = pedidoService.buscarPedidosPorEstadoProductoYUsuario(estado, nombreProducto, nombreUsuario);
        if (pedidos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(pedidos);
    }

    @PostMapping
    @Operation(summary = "Crea un pedido", description = "Ingresa los parametros para crear un pedido")
    public ResponseEntity<Pedido> create(@RequestBody Pedido pedido) {
        Pedido nuevoPedido = pedidoService.save(pedido);
    return ResponseEntity.status(201).body(nuevoPedido);    
}

    @PutMapping("/{id}")
    @Operation(summary = "Actualiza un pedido", description = "Ingresa los parametros para actualizar un pedido (solo ingresa los cambios)")
    public ResponseEntity<Pedido> update(@PathVariable Long id, @RequestBody Pedido pedido) {
        Pedido actualizado = pedidoService.update(id, pedido);
        if (actualizado == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(actualizado);
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Actualiza un pedido", description = "Ingresa los parametros para actualizar un pedido")
    public ResponseEntity<Pedido> patch(@PathVariable Long id, @RequestBody Pedido pedido) {
        Pedido actualizado = pedidoService.patch(id, pedido);
        if (actualizado == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Borra los pedidos por ID", description = "Ingresa un ID para borrar un pedido")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        pedidoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
    
}
