package GLISERV.GesImp.assemblers;

import GLISERV.GesImp.controller.UsuarioControllerV2;
import GLISERV.GesImp.model.Usuario;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Component
public class UsuarioModelAssembler implements RepresentationModelAssembler<Usuario, EntityModel<Usuario>> {

    @SuppressWarnings("null")
    @Override
    public EntityModel<Usuario> toModel(Usuario usuario) {
        return EntityModel.of(usuario,
                linkTo(methodOn(UsuarioControllerV2.class).getUsuarioById(usuario.getId().longValue())).withSelfRel(),
                linkTo(methodOn(UsuarioControllerV2.class).getAllUsuarios()).withRel("listar-todos"),
                linkTo(methodOn(UsuarioControllerV2.class).updateUsuario(usuario.getId().longValue(), usuario)).withRel("actualizar"),
                linkTo(methodOn(UsuarioControllerV2.class).patchUsuario(usuario.getId().longValue(), usuario)).withRel("actualización-parcial"),
                linkTo(methodOn(UsuarioControllerV2.class).deleteUsuario(usuario.getId().longValue())).withRel("eliminar")
        );
    }
}
