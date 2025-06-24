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
        return EntityModel.of(factura,
                linkTo(methodOn(FacturaControllerV2.class).getFacturaById(factura.getId().longValue())).withSelfRel(),
                linkTo(methodOn(FacturaControllerV2.class).getAllFacturas()).withRel("tipoPagos"),
                linkTo(methodOn(FacturaControllerV2.class).deleteFactura(factura.getId().longValue())).withRel("eliminar"),
                linkTo(methodOn(FacturaControllerV2.class).updateFactura(factura.getId().longValue(), factura)).withRel("actualizar")
        );
    }
}
