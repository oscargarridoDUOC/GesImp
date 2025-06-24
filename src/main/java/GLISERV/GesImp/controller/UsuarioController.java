package GLISERV.GesImp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import GLISERV.GesImp.model.Usuario;
import GLISERV.GesImp.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/usuarios")
@Tag(name = "Usuario", description = "Ve, crea, edita o elimina los usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    @Operation(summary = "Obtiene todos los usuarios", description = "Obtiene una lista con todos los usuarios")
    public List<Usuario> getAll() {
        return usuarioService.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtiene los usuarios por ID", description = "Ingresa un ID para obtener un usuario")
    public ResponseEntity<Usuario> getById(@PathVariable long id) {
        Usuario usuario= usuarioService.findById(id);
        if (usuario != null) {
            return ResponseEntity.ok(usuario);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @Operation(summary = "Crea un usuario", description = "Ingresa los parametros para crear un usuario")
    public ResponseEntity<Usuario> create(@RequestBody Usuario usuario) {
        Usuario nuevoUsuario = usuarioService.save(usuario);
        return ResponseEntity.status(201).body(nuevoUsuario);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualiza un usuario", description = "Ingresa los parametros para actualizar un usuario (solo ingresa los cambios)")
    public ResponseEntity<Usuario> update(@PathVariable Long id, @RequestBody Usuario usuario) {
        Usuario actualizado = usuarioService.update(id, usuario);
        if (actualizado == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(actualizado);
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Actualiza un usuario", description = "Ingresa los parametros para actualizar un usuario")
    public ResponseEntity<Usuario> patch(@PathVariable Long id, @RequestBody Usuario usuarioParcial) {
        Usuario actualizado = usuarioService.patch(id, usuarioParcial);
        if (actualizado == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Borra los usuarios por ID", description = "Ingresa un ID para borrar un usuario")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            usuarioService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
