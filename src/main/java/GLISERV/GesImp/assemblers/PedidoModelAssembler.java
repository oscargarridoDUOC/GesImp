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
                linkTo(methodOn(PedidoControllerV2.class).getPedidoById(pedido.getId().longValue())).withSelfRel(),
                linkTo(methodOn(PedidoControllerV2.class).getAllPedidos()).withRel("listar-todos"),
                linkTo(methodOn(PedidoControllerV2.class).updatePedido(pedido.getId().longValue(), pedido)).withRel("actualizar"),
                linkTo(methodOn(PedidoControllerV2.class).patchPedido(pedido.getId().longValue(), pedido)).withRel("actualización-parcial"),
                linkTo(methodOn(PedidoControllerV2.class).deletePedido(pedido.getId().longValue())).withRel("eliminar")        
        );
    }
}
