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

import GLISERV.GesImp.model.EstadoPedido;
import GLISERV.GesImp.service.EstadoPedidoService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/estado-pedidos")
public class EstadoPedidoController {

    @Autowired
    private EstadoPedidoService estadoPedidoService;

    @GetMapping
    public List<EstadoPedido> getAll() {
        return estadoPedidoService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<EstadoPedido> getById(@PathVariable Long id) {
        EstadoPedido estadoPedido = estadoPedidoService.findById(id);
        if (estadoPedido != null) {
            return ResponseEntity.ok(estadoPedido);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    
    @GetMapping("/nombre")
    public ResponseEntity<List<EstadoPedido>> getByNombre(@RequestParam("nombre") String nombre) {
        List<EstadoPedido> resultados = estadoPedidoService.findByNombre(nombre);
        if (resultados.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(resultados);
        }
    }


    @PostMapping
    public EstadoPedido create(@RequestBody EstadoPedido estadoPedido) {
        return estadoPedidoService.save(estadoPedido);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EstadoPedido> update(@PathVariable Long id, @RequestBody EstadoPedido estadoPedido) {
        EstadoPedido updated = estadoPedidoService.update(id, estadoPedido);
        if (updated != null) {
            return ResponseEntity.ok(updated);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<EstadoPedido> patch(@PathVariable Long id, @RequestBody EstadoPedido estadoPedidoParcial) {
        EstadoPedido patched = estadoPedidoService.patch(id, estadoPedidoParcial);
        if (patched != null) {
            return ResponseEntity.ok(patched);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        estadoPedidoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
