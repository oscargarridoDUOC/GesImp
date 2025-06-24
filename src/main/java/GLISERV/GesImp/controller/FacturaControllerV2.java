package GLISERV.GesImp.controller;

import GLISERV.GesImp.assemblers.FacturaModelAssembler;
import GLISERV.GesImp.model.Factura;
import GLISERV.GesImp.service.FacturaService;
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
@RequestMapping(value = "/api/v2/facturas")
@Tag(name = "Factura", description = "Ve, crea, edita o elimina las facturas de la imprenta")
public class FacturaControllerV2 {

    @Autowired
    private FacturaService facturaService;

    @Autowired
    private FacturaModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Obtiene todos las facturas", description = "Obtiene una lista con todas las facturas existentes")
    public CollectionModel<EntityModel<Factura>> getAllFacturas() {
        List<EntityModel<Factura>> facturas = facturaService.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(facturas,
                linkTo(methodOn(FacturaControllerV2.class).getAllFacturas()).withSelfRel());
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Obtiene las facuras por ID", description = "Ingresa un ID para obtener una factura")
    public ResponseEntity<EntityModel<Factura>> getFacturaById(@PathVariable Long id) {
        Factura factura = facturaService.findById(id);
        if (factura == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(factura));
    }
    @GetMapping(value = "/buscar/q1", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Busca facturas por tipo de pago y total mínimo", description = "Ingresa un tipo de pago y un monto mínimo para obtener las facturas que coincidan")
    public ResponseEntity<CollectionModel<EntityModel<Factura>>> buscarPorTipoPagoYTotalMinimo(
            @RequestParam String tipoPago,
            @RequestParam Integer montoMinimo) {

        List<Factura> facturas = facturaService.buscarPorTipoPagoYTotalMinimo(tipoPago, montoMinimo);

        if (facturas == null || facturas.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        List<EntityModel<Factura>> list = new ArrayList<>();
        for (Factura f : facturas) {
            list.add(assembler.toModel(f));
        }

        return ResponseEntity.ok(CollectionModel.of(list));
    }

    @GetMapping(value = "/buscar/q2", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Buscar facturas por usuario, tipo de producto y tipo de pago", description = "Retorna facturas filtradas por nombre de usuario, tipo de producto y nombre del método de pago")
    public ResponseEntity<CollectionModel<EntityModel<Factura>>> buscarFacturasPorUsuarioProductoYTipoPago(
            @RequestParam String nombreUsuario,
            @RequestParam String tipoProducto,
            @RequestParam String nombreTipoPago) {

        List<Factura> facturas = facturaService.buscarFacturasPorUsuarioProductoYTipoPago(nombreUsuario, tipoProducto, nombreTipoPago);

        if (facturas == null || facturas.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        List<EntityModel<Factura>> list = new ArrayList<>();
        for (Factura f : facturas) {
            list.add(assembler.toModel(f));
        }

        return ResponseEntity.ok(CollectionModel.of(list));
    }

    @GetMapping(value = "/buscar/q5", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Buscar facturas nombre de usuario y nombre del producto", description = "Filtra las facturas según el nombre del usuario que las solicitó y el nombre del producto facturado")
    public ResponseEntity<CollectionModel<EntityModel<Factura>>> buscarFacturasPorUsuarioYProducto(
            @RequestParam String nombreUsuario,
            @RequestParam String nombreProducto) {

        List<Factura> facturas = facturaService.buscarFacturasPorUsuarioYProducto(nombreUsuario, nombreProducto);

        if (facturas == null || facturas.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        List<EntityModel<Factura>> list = new ArrayList<>();
        for (Factura f : facturas) {
            list.add(assembler.toModel(f));
        }

        return ResponseEntity.ok(CollectionModel.of(list));
    }


    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Crea una factura", description = "Ingresa los parametros para crear una factura")
    public ResponseEntity<EntityModel<Factura>> createFactura(@RequestBody Factura factura) {
        Factura nuevaFactura = facturaService.save(factura);
        return ResponseEntity
                .created(linkTo(methodOn(FacturaControllerV2.class).getFacturaById(Long.valueOf(nuevaFactura.getId()))).toUri())
                .body(assembler.toModel(nuevaFactura));
    }

    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Actualiza una factura", description = "Ingresa los parametros para actualizar una factura (solo ingresa los cambios)")
    public ResponseEntity<EntityModel<Factura>> updateFactura(@PathVariable Long id, @RequestBody Factura factura) {
        factura.setId(id.intValue());
        Factura updated = facturaService.save(factura);
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(updated));
    }

    @PatchMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Actualiza una factura", description = "Ingresa los parametros para actualizar una factura")
    public ResponseEntity<EntityModel<Factura>> patchFactura(@PathVariable Integer id, @RequestBody Factura factura) {
        Factura patched = facturaService.patch(id, factura);
        if (patched == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(patched));
    }

    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Borra las facuras por ID", description = "Ingresa un ID para borrar una factura")
    public ResponseEntity<Void> deleteFactura(@PathVariable Long id) {
        Factura existing = facturaService.findById(id);
        if (existing == null) {
            return ResponseEntity.notFound().build();
        }
        facturaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
