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

import GLISERV.GesImp.model.TipoPago;
import GLISERV.GesImp.service.TipoPagoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tipo-pagos")
@Tag(name = "Tipo Pago", description = "Ve, crea, edita o elimina los estados de los tipos de pago")
public class TipoPagoController {

    @Autowired
    private TipoPagoService tipoPagoService;

    @GetMapping
    @Operation(summary = "Obtiene todos los tipos de pago", description = "Obtiene una lista con todos los tipos de pago")
    public List<TipoPago> getAll() {
        return tipoPagoService.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtiene los tipos de pago por ID", description = "Ingresa un ID para obtener un tipo de pago")
    public ResponseEntity<TipoPago> getById(@PathVariable long id) {
        TipoPago tipoPago= tipoPagoService.findById(id);
        if (tipoPago != null) {
            return ResponseEntity.ok(tipoPago);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/nombre")
    @Operation(summary = "Obtiene los tipos de pago por Nombre", description = "Ingresa un Nombre para obtener un tipo de pago")
    public ResponseEntity<List<TipoPago>> getByNombre(@RequestParam("nombre") String nombre) {
        List<TipoPago> resultados = tipoPagoService.findByNombre(nombre);
        if (resultados.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(resultados);
        }
    }

    @PostMapping
    @Operation(summary = "Crea un tipo de pago", description = "Ingresa los parametros para crear un tipo de pago")
    public TipoPago create(@RequestBody TipoPago tipoPago) {
        return tipoPagoService.save(tipoPago);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualiza un tipo de pago", description = "Ingresa los parametros para actualizar un tipo de pago (solo ingresa los cambios)")
    public ResponseEntity<TipoPago> update(@PathVariable Long id, @RequestBody TipoPago tipoPago) {
        TipoPago updated = tipoPagoService.update(id, tipoPago);
        if (updated != null) {
            return ResponseEntity.ok(updated);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Actualiza un tipo de pago", description = "Ingresa los parametros para actualizar un tipo de pago")
    public ResponseEntity<TipoPago> patch(@PathVariable Long id, @RequestBody TipoPago tipoPagoParcial) {
        TipoPago patched = tipoPagoService.patch(id, tipoPagoParcial);
        if (patched != null) {
            return ResponseEntity.ok(patched);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Borra los tipos de pago por ID", description = "Ingresa un ID para borrar un tipo de pago")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        tipoPagoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
