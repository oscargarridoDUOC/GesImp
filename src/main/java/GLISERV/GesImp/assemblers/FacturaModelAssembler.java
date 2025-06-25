package GLISERV.GesImp.assemblers;
import GLISERV.GesImp.controller.FacturaControllerV2;
import GLISERV.GesImp.model.Factura;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Component
public class FacturaModelAssembler implements RepresentationModelAssembler<Factura, EntityModel<Factura>>{
    
    @SuppressWarnings("null")
    @Override
    public EntityModel<Factura> toModel(Factura factura) {
        Long id = factura.getId().longValue();
        return EntityModel.of(factura,
            linkTo(methodOn(FacturaControllerV2.class).getFacturaById(id)).withSelfRel(),
            linkTo(methodOn(FacturaControllerV2.class).getAllFacturas()).withRel("listar-todas"),
            linkTo(methodOn(FacturaControllerV2.class).buscarPorTipoPagoYTotalMinimo("Efectivo", 1000)).withRel("buscar-tipoPago-y-minimo"),
            linkTo(methodOn(FacturaControllerV2.class).buscarFacturasPorUsuarioProductoYTipoPago("usuario", "tipoProducto", "pago")).withRel("buscar-usuario-producto-pago"),
            linkTo(methodOn(FacturaControllerV2.class).buscarFacturasPorUsuarioYProducto("usuario", "producto")).withRel("buscar-usuario-producto"),
            linkTo(methodOn(FacturaControllerV2.class).createFactura(factura)).withRel("crear"),
            linkTo(methodOn(FacturaControllerV2.class).updateFactura(id, factura)).withRel("actualizar"),
            linkTo(methodOn(FacturaControllerV2.class).patchFactura(factura.getId(), factura)).withRel("patch"),
            linkTo(methodOn(FacturaControllerV2.class).deleteFactura(id)).withRel("eliminar")        );
    }
}
