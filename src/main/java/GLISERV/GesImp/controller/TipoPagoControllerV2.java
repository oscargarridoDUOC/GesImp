package GLISERV.GesImp.controller;

import GLISERV.GesImp.assemblers.TipoPagoModelAssembler;
import GLISERV.GesImp.model.TipoPago;
import GLISERV.GesImp.service.TipoPagoService;
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
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequestMapping(value = "/api/v2/tipo-pagos")
@Tag(name = "Tipo Pago", description = "Ve, crea, edita o elimina los estados de los tipos de pago")
public class TipoPagoControllerV2 {

    @Autowired
    private TipoPagoService tipoPagoService;

    @Autowired
    private TipoPagoModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Obtiene todos los tipos de pago", description = "Obtiene una lista con todos los tipos de pago")
    public CollectionModel<EntityModel<TipoPago>> getAllTipoPagos() {
        List<EntityModel<TipoPago>> tipoPagos = tipoPagoService.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(tipoPagos,
                linkTo(methodOn(TipoPagoControllerV2.class).getAllTipoPagos()).withSelfRel());
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Obtiene los tipos de pago por ID", description = "Ingresa un ID para obtener un tipo de pago")
    public ResponseEntity<EntityModel<TipoPago>> getTipoPagoById(@PathVariable Long id) {
        TipoPago tipoPago = tipoPagoService.findById(id);
        if (tipoPago == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(tipoPago));
    }

    @GetMapping(value = "/nombre/{nombre}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Obtiene los tipos de pago por Nombre", description = "Ingresa un Nombre para obtener un tipo de pago")
    public ResponseEntity<CollectionModel<EntityModel<TipoPago>>> getTipoPagoByNombre(@PathVariable String nombre) {
        List<TipoPago> tipoPagos = tipoPagoService.findByNombre(nombre);
        if (tipoPagos == null || tipoPagos.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        List<EntityModel<TipoPago>> tipoPagosModel = tipoPagos.stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return ResponseEntity.ok(
                CollectionModel.of(
                        tipoPagosModel,
                        linkTo(methodOn(TipoPagoControllerV2.class).getTipoPagoByNombre(nombre)).withSelfRel()
                )
        );
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Crea un tipo de pago", description = "Ingresa los parametros para crear un tipo de pago")
    public ResponseEntity<EntityModel<TipoPago>> createTipoPago(@RequestBody TipoPago tipoPago) {
        TipoPago nuevo = tipoPagoService.save(tipoPago);
        return ResponseEntity
                .created(linkTo(methodOn(TipoPagoControllerV2.class).getTipoPagoById(Long.valueOf(nuevo.getId()))).toUri())
                .body(assembler.toModel(nuevo));
    }

    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Actualiza un tipo de pago", description = "Ingresa los parametros para actualizar un tipo de pago (solo ingresa los cambios)")
    public ResponseEntity<EntityModel<TipoPago>> updateTipoPago(@PathVariable Long id, @RequestBody TipoPago tipoPago) {
        tipoPago.setId(id.intValue());
        TipoPago updated = tipoPagoService.save(tipoPago);
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(updated));
    }

    @PatchMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Actualiza un tipo de pago", description = "Ingresa los parametros para actualizar un tipo de pago")
    public ResponseEntity<EntityModel<TipoPago>> patchTipoPago(@PathVariable Long id, @RequestBody TipoPago tipoPago) {
        TipoPago patched = tipoPagoService.patch(id, tipoPago);
        if (patched == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(patched));
    }

    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Borra los tipos de pago por ID", description = "Ingresa un ID para borrar un tipo de pago")
    public ResponseEntity<Void> deleteTipoPago(@PathVariable Long id) {
        TipoPago existing = tipoPagoService.findById(id);
        if (existing == null) {
            return ResponseEntity.notFound().build();
        }
        tipoPagoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
