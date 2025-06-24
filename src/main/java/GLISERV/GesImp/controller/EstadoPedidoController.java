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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@RestController
@RequestMapping("/api/v1/estado-pedidos")
@Tag(name = "Estado Pedido", description = "Ve, crea, edita o elimina los estados de los pedidos")
public class EstadoPedidoController {

    @Autowired
    private EstadoPedidoService estadoPedidoService;

    @GetMapping
    @Operation(summary = "Obtiene todos los estados", description = "Obtiene una lista con todos los estados de los pedidos")
    public List<EstadoPedido> getAll() {
        return estadoPedidoService.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtiene los estados por ID", description = "Ingresa un ID para obtener un estado de pedido")
    public ResponseEntity<EstadoPedido> getById(@PathVariable Long id) {
        EstadoPedido estadoPedido = estadoPedidoService.findById(id);
        if (estadoPedido != null) {
            return ResponseEntity.ok(estadoPedido);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    
    @GetMapping("/nombre")
    @Operation(summary = "Obtiene los estados por Nombre", description = "Ingresa un Nombre para obtener un estado de pedido")
    public ResponseEntity<List<EstadoPedido>> getByNombre(@RequestParam("nombre") String nombre) {
        List<EstadoPedido> resultados = estadoPedidoService.findByNombre(nombre);
        if (resultados.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(resultados);
        }
    }


    @PostMapping
    @Operation(summary = "Crea un estado de pedido", description = "Ingresa los parametros para crear un estado de pedido")
    public EstadoPedido create(@RequestBody EstadoPedido estadoPedido) {
        return estadoPedidoService.save(estadoPedido);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualiza un estado de pedido", description = "Ingresa los parametros para actualizar un estado de pedido (solo ingresa los cambios)")
    public ResponseEntity<EstadoPedido> update(@PathVariable Long id, @RequestBody EstadoPedido estadoPedido) {
        EstadoPedido updated = estadoPedidoService.update(id, estadoPedido);
        if (updated != null) {
            return ResponseEntity.ok(updated);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Actualiza un estado de pedido", description = "Ingresa los parametros para actualizar un estado de pedido")
    public ResponseEntity<EstadoPedido> patch(@PathVariable Long id, @RequestBody EstadoPedido estadoPedidoParcial) {
        EstadoPedido patched = estadoPedidoService.patch(id, estadoPedidoParcial);
        if (patched != null) {
            return ResponseEntity.ok(patched);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Borra los estados por ID", description = "Ingresa un ID para borrar un estado de pedido")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        estadoPedidoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
