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

import GLISERV.GesImp.model.PedidoProducto;
import GLISERV.GesImp.service.PedidoProductoService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/pedido-productos")
public class PedidoProductoController {

    @Autowired
    private PedidoProductoService pedidoProductoService;

    @GetMapping
    public List<PedidoProducto> getAllPedidoProductos() {
        return pedidoProductoService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoProducto> getPedidoProductoById(@PathVariable Long id) {
        PedidoProducto pedidoProducto = pedidoProductoService.findById(id);
        if (pedidoProducto != null) {
            return ResponseEntity.ok(pedidoProducto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public PedidoProducto createPedidoProducto(@RequestBody PedidoProducto pedidoProducto) {
        return pedidoProductoService.save(pedidoProducto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePedidoProducto(@PathVariable Integer id) {
        pedidoProductoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<PedidoProducto> updatePedidoProducto(@PathVariable Long id, @RequestBody PedidoProducto pedidoProducto) {
        PedidoProducto updatedPedidoProducto = pedidoProductoService.updatePedidoProducto(id, pedidoProducto);
        if (updatedPedidoProducto != null) {
            return ResponseEntity.ok(updatedPedidoProducto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<PedidoProducto> patchPedidoProducto(@PathVariable Long id, @RequestBody PedidoProducto pedidoProductoParcial) {
        PedidoProducto updatedPedidoProducto = pedidoProductoService.patchPedidoProducto(id, pedidoProductoParcial);
        if (updatedPedidoProducto != null) {
            return ResponseEntity.ok(updatedPedidoProducto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
