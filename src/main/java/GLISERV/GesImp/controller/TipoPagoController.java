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

import java.util.List;

@RestController
@RequestMapping("/api/v1/tipo-pagos")
public class TipoPagoController {

    @Autowired
    private TipoPagoService tipoPagoService;

    @GetMapping
    public List<TipoPago> getAll() {
        return tipoPagoService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TipoPago> getById(@PathVariable long id) {
        TipoPago tipoPago= tipoPagoService.findById(id);
        if (tipoPago != null) {
            return ResponseEntity.ok(tipoPago);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/nombre")
    public ResponseEntity<List<TipoPago>> getByNombre(@RequestParam("nombre") String nombre) {
        List<TipoPago> resultados = tipoPagoService.findByNombre(nombre);
        if (resultados.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(resultados);
        }
    }
    @PostMapping
    public TipoPago create(@RequestBody TipoPago tipoPago) {
        return tipoPagoService.save(tipoPago);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TipoPago> update(@PathVariable Long id, @RequestBody TipoPago tipoPago) {
        TipoPago updated = tipoPagoService.update(id, tipoPago);
        if (updated != null) {
            return ResponseEntity.ok(updated);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<TipoPago> patch(@PathVariable Long id, @RequestBody TipoPago tipoPagoParcial) {
        TipoPago patched = tipoPagoService.patch(id, tipoPagoParcial);
        if (patched != null) {
            return ResponseEntity.ok(patched);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        tipoPagoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
