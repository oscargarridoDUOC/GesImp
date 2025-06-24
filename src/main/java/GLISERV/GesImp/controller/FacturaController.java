package GLISERV.GesImp.controller;

import java.util.List;

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

import GLISERV.GesImp.model.Factura;
import GLISERV.GesImp.service.FacturaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/facturas")
@Tag(name = "Factura", description = "Ve, crea, edita o elimina las facturas de la imprenta")
public class FacturaController {

    @Autowired
    private FacturaService facturaService;

    @GetMapping
    @Operation(summary = "Obtiene todos las facturas", description = "Obtiene una lista con todas las facturas existentes")
    public ResponseEntity<List<Factura>> getAll() {
        List<Factura> facturas = facturaService.findAll();
        if (facturas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(facturas);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtiene las facuras por ID", description = "Ingresa un ID para obtener una factura")
    public ResponseEntity<Factura> getById(@PathVariable long id) {
        Factura factura= facturaService.findById(id);
        if (factura != null) {
            return ResponseEntity.ok(factura);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/buscar/q1")
    @Operation(summary = "Busca facturas por tipo de pago y total mínimo", description = "Ingresa un tipo de pago y un monto mínimo para obtener las facturas que coincidan")
    public ResponseEntity<List<Factura>> buscarPorTipoPagoYTotalMinimo( @RequestParam String tipoPago, @RequestParam Integer montoMinimo) {

        List<Factura> facturas = facturaService.buscarPorTipoPagoYTotalMinimo(tipoPago, montoMinimo);
        if (facturas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(facturas);
    }
    
    @GetMapping("/buscar/q2")
    @Operation(summary = "Buscar facturas por usuario, tipo de producto y tipo de pago", description = "Retorna facturas filtradas por nombre de usuario, tipo de producto y nombre del método de pago")
    public ResponseEntity<List<Factura>> buscarFacturasPorUsuarioProductoYTipoPago(@RequestParam String nombreUsuario,@RequestParam String tipoProducto,@RequestParam String nombreTipoPago) {
        List<Factura> facturas = facturaService.buscarFacturasPorUsuarioProductoYTipoPago(nombreUsuario, tipoProducto, nombreTipoPago);
        if (facturas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(facturas);
    }

    @GetMapping("/buscar/q5")
    @Operation(summary = "Buscar facturas nombre de usuario y nombre del producto", description = "Filtra las facturas según el nombre del usuario que las solicitó y el nombre del producto facturado")
    public ResponseEntity<List<Factura>> buscarFacturasPorUsuarioYProducto(@RequestParam String nombreUsuario,@RequestParam String nombreProducto) {
        List<Factura> facturas = facturaService.buscarFacturasPorUsuarioYProducto(nombreUsuario, nombreProducto);
        if (facturas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(facturas);
    }

    @PostMapping
    @Operation(summary = "Crea una factura", description = "Ingresa los parametros para crear una factura")
    public ResponseEntity<Factura> create(@RequestBody Factura factura) {
        Factura nuevaFactura = facturaService.save(factura);
        return ResponseEntity.status(201).body(nuevaFactura);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualiza una factura", description = "Ingresa los parametros para actualizar una factura (solo ingresa los cambios)")
    public ResponseEntity<Factura> update(@PathVariable Integer id, @RequestBody Factura factura) {
        Factura actualizada = facturaService.update(id, factura);
        if (actualizada == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(actualizada);
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Actualiza una factura", description = "Ingresa los parametros para actualizar una factura")
    public ResponseEntity<Factura> patch(@PathVariable Integer id, @RequestBody Factura facturaParcial) {
        Factura actualizada = facturaService.patch(id, facturaParcial);
        if (actualizada == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(actualizada);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Borra las facuras por ID", description = "Ingresa un ID para borrar una factura")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        Factura factura = facturaService.findById(id);
        if (factura == null) {
            return ResponseEntity.notFound().build();
        }
        facturaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
