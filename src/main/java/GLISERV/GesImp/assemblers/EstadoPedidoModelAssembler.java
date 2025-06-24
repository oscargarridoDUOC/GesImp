package GLISERV.GesImp.assemblers;

import GLISERV.GesImp.controller.EstadoPedidoControllerV2;
import GLISERV.GesImp.model.EstadoPedido;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
@Component
public class EstadoPedidoModelAssembler implements RepresentationModelAssembler<EstadoPedido, EntityModel<EstadoPedido>> {
    
    @SuppressWarnings("null")
    @Override
    public EntityModel<EstadoPedido> toModel(EstadoPedido estadoPedido) {
        return EntityModel.of(estadoPedido,
                linkTo(methodOn(EstadoPedidoControllerV2.class).getEstadoPedidoById(estadoPedido.getId().longValue())).withSelfRel(),
                linkTo(methodOn(EstadoPedidoControllerV2.class).getAllEstadoPedidos()).withRel("tipoPagos"),
                linkTo(methodOn(EstadoPedidoControllerV2.class).deleteEstadoPedido(estadoPedido.getId().longValue())).withRel("eliminar"),
                linkTo(methodOn(EstadoPedidoControllerV2.class).updateEstadoPedido(estadoPedido.getId().longValue(), estadoPedido)).withRel("actualizar")
        );
    }
}
