package GLISERV.GesImp.controller;

import GLISERV.GesImp.model.Usuario;
import GLISERV.GesImp.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import GLISERV.GesImp.assemblers.UsuarioModelAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequestMapping("/api/v2/usuarios")
@Tag(name = "Usuario", description = "Ve, crea, edita o elimina los usuarios")

public class UsuarioControllerV2 {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Obtiene todos los usuarios", description = "Obtiene una lista con todos los usuarios")
    public CollectionModel<EntityModel<Usuario>> getAllUsuarios() {
        List<EntityModel<Usuario>> usuarios = usuarioService.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(usuarios,
                linkTo(methodOn(UsuarioControllerV2.class).getAllUsuarios()).withSelfRel());
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Obtiene los usuarios por ID", description = "Ingresa un ID para obtener un usuario")    
    public ResponseEntity<EntityModel<Usuario>> getUsuarioById(@PathVariable Long id) {
        Usuario usuario = usuarioService.findById(id);
        if (usuario == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(usuario));
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Crea un usuario", description = "Ingresa los parametros para crear un usuario")
    public ResponseEntity<EntityModel<Usuario>> createUsuario(@RequestBody Usuario usuario) {
        Usuario nuevoUsuario = usuarioService.save(usuario);
        return ResponseEntity
                .created(linkTo(methodOn(UsuarioControllerV2.class).getUsuarioById(nuevoUsuario.getId().longValue())).toUri())
                .body(assembler.toModel(nuevoUsuario));
    }

    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Actualiza un usuario", description = "Ingresa los parametros para actualizar un usuario (solo ingresa los cambios)")
    public ResponseEntity<EntityModel<Usuario>> updateUsuario(@PathVariable Long id, @RequestBody Usuario usuario) {
        usuario.setId(id.intValue());
        Usuario updated = usuarioService.save(usuario);
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(updated));
    }

    @PatchMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Actualiza un usuario", description = "Ingresa los parametros para actualizar un usuario")
    public ResponseEntity<EntityModel<Usuario>> patchUsuario(@PathVariable Long id, @RequestBody Usuario usuario) {
        Usuario patched = usuarioService.patch(id, usuario);
        if (patched == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(patched));
    }

    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Borra los usuarios por ID", description = "Ingresa un ID para borrar un usuario")
    public ResponseEntity<Void> deleteUsuario(@PathVariable Long id) {
        Usuario existing = usuarioService.findById(id);
        if (existing == null) {
            return ResponseEntity.notFound().build();
        }
        usuarioService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
} 
