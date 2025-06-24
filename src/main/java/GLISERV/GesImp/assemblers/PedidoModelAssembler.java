package GLISERV.GesImp.assemblers;

import GLISERV.GesImp.controller.PedidoControllerV2;
import GLISERV.GesImp.model.Pedido;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class PedidoModelAssembler implements RepresentationModelAssembler<Pedido, EntityModel<Pedido>> {

    @SuppressWarnings("null")
    @Override
    public EntityModel<Pedido> toModel(Pedido pedido) {
        return EntityModel.of(pedido,
                linkTo(methodOn(PedidoControllerV2.class).getPedidoById(Long.valueOf(pedido.getId()))).withSelfRel(),
                linkTo(methodOn(PedidoControllerV2.class).deletePedido(Long.valueOf(pedido.getId()))).withRel("eliminar"),
                linkTo(methodOn(PedidoControllerV2.class).updatePedido(Long.valueOf(pedido.getId()), pedido)).withRel("actualizar")
        );
    }
}
