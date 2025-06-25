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
        Long id = estadoPedido.getId().longValue();
        return EntityModel.of(estadoPedido,
            linkTo(methodOn(EstadoPedidoControllerV2.class).getEstadoPedidoById(id)).withSelfRel(),
            linkTo(methodOn(EstadoPedidoControllerV2.class).getAllEstadoPedidos()).withRel("listar-todos"),
            linkTo(methodOn(EstadoPedidoControllerV2.class).getByNombre(estadoPedido.getNombre())).withRel("buscar-por-nombre"),
            linkTo(methodOn(EstadoPedidoControllerV2.class).createEstadoPedido(estadoPedido)).withRel("crear"),
            linkTo(methodOn(EstadoPedidoControllerV2.class).updateEstadoPedido(id, estadoPedido)).withRel("actualizar"),
            linkTo(methodOn(EstadoPedidoControllerV2.class).patchEstadoPedido(id, estadoPedido)).withRel("patch"),
            linkTo(methodOn(EstadoPedidoControllerV2.class).deleteEstadoPedido(id)).withRel("eliminar")
        );
    }
}
