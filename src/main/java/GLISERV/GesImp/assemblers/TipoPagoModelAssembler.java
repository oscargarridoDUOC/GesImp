package GLISERV.GesImp.assemblers;

import GLISERV.GesImp.controller.TipoPagoControllerV2;
import GLISERV.GesImp.model.TipoPago;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Component
public class TipoPagoModelAssembler implements RepresentationModelAssembler<TipoPago, EntityModel<TipoPago>> {

    @SuppressWarnings("null")
    @Override
    public EntityModel<TipoPago> toModel(TipoPago tipoPago) {
        return EntityModel.of(tipoPago,
                linkTo(methodOn(TipoPagoControllerV2.class).getTipoPagoById(tipoPago.getId().longValue())).withSelfRel(),
                linkTo(methodOn(TipoPagoControllerV2.class).getAllTipoPagos()).withRel("listar-todos"),
                linkTo(methodOn(TipoPagoControllerV2.class).updateTipoPago(tipoPago.getId().longValue(), tipoPago)).withRel("actualizar"),
                linkTo(methodOn(TipoPagoControllerV2.class).patchTipoPago(tipoPago.getId().longValue(), tipoPago)).withRel("actualización-parcial"),
                linkTo(methodOn(TipoPagoControllerV2.class).deleteTipoPago(tipoPago.getId().longValue())).withRel("eliminar"),
                linkTo(methodOn(TipoPagoControllerV2.class).getTipoPagoByNombre(tipoPago.getNombre())).withRel("buscar-nombre")
        );
    }
}