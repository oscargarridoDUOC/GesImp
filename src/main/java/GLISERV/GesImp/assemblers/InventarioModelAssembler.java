package GLISERV.GesImp.assemblers;
import GLISERV.GesImp.controller.InventarioControllerV2;
import GLISERV.GesImp.model.Inventario;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Component
public class InventarioModelAssembler implements RepresentationModelAssembler<Inventario, EntityModel<Inventario>>{
    
    @SuppressWarnings("null")
    @Override
    public EntityModel<Inventario> toModel(Inventario inventario) {
        return EntityModel.of(inventario,
            linkTo(methodOn(InventarioControllerV2.class).getInventarioById(inventario.getId().longValue())).withSelfRel(),
            linkTo(methodOn(InventarioControllerV2.class).getAllInventarios()).withRel("listar-todos"),
            linkTo(methodOn(InventarioControllerV2.class).updateInventario(inventario.getId().longValue(), inventario)).withRel("actualizar"),
            linkTo(methodOn(InventarioControllerV2.class).patchInventario(inventario.getId().longValue(), inventario)).withRel("actualización-parcial"),
            linkTo(methodOn(InventarioControllerV2.class).deleteInventario(inventario.getId().longValue())).withRel("eliminar"),
            linkTo(methodOn(InventarioControllerV2.class).getInventarioByMaterial(inventario.getMaterial())).withRel("buscar-por-material")        
            );
    }
}