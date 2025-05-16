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


import java.util.List;

@RestController
@RequestMapping("/api/v1/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @GetMapping
    public ResponseEntity<List<Pedido>> listar() {
        List<Pedido> pedidos = pedidoService.findAll();
        if (pedidos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(pedidos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pedido> obtenerPorId(@PathVariable Long id) {
        Pedido pedido = pedidoService.findById(id);
        if (pedido == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(pedido);
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Pedido>> obtenerPorUsuario(@PathVariable Long usuarioId) {
        List<Pedido> pedidos = pedidoService.findByUsuario(usuarioId);
        if (pedidos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(pedidos);
    }

    @PostMapping
    public ResponseEntity<Pedido> crear(@RequestBody Pedido pedido) {
        Pedido nuevoPedido = pedidoService.save(pedido);
    return ResponseEntity.status(201).body(nuevoPedido);    
}

    @PutMapping("/{id}")
    public ResponseEntity<Pedido> actualizar(@PathVariable Long id, @RequestBody Pedido pedido) {
        Pedido actualizado = pedidoService.updatePedido(id, pedido);
        if (actualizado == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(actualizado);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Pedido> actualizarParcial(@PathVariable Long id, @RequestBody Pedido pedido) {
        Pedido actualizado = pedidoService.patchPedido(id, pedido);
        if (actualizado == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        pedidoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
