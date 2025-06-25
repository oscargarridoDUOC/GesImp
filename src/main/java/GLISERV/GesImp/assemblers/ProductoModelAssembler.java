package GLISERV.GesImp.assemblers;

import GLISERV.GesImp.controller.ProductoControllerV2;
import GLISERV.GesImp.model.Producto;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Component
public class ProductoModelAssembler implements RepresentationModelAssembler<Producto, EntityModel<Producto>> {

    @SuppressWarnings("null")
    @Override
    public EntityModel<Producto> toModel(Producto producto) {
        return EntityModel.of(producto,
                linkTo(methodOn(ProductoControllerV2.class).getProductoById(producto.getId().longValue())).withSelfRel(),
                linkTo(methodOn(ProductoControllerV2.class).getAllProductos()).withRel("listar-todos"),
                linkTo(methodOn(ProductoControllerV2.class).updateProducto(producto.getId(), producto)).withRel("actualizar"),
                linkTo(methodOn(ProductoControllerV2.class).patchProducto(producto.getId().longValue(), producto)).withRel("actualización-parcial"),
                linkTo(methodOn(ProductoControllerV2.class).deleteProducto(producto.getId().longValue())).withRel("eliminar"),
                linkTo(methodOn(ProductoControllerV2.class).getProductoByNombre(producto.getNombre())).withRel("buscar-nombre")
        );
    }
}
