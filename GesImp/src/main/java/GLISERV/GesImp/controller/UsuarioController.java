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

@RestController
@RequestMapping("/api/v1/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<List<Usuario>> getAll() {
        List<Usuario> usuarios = usuarioService.findAll();
        if (usuarios.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> getById(@PathVariable Long id) {
        try {
            usuarioService.findById(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Usuario> create(@RequestBody Usuario usuario) {
        Usuario nuevoUsuario = usuarioService.save(usuario);
        return ResponseEntity.status(201).body(nuevoUsuario);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> update(@PathVariable Long id, @RequestBody Usuario usuario) {
        Usuario actualizado = usuarioService.updateUsuario(id, usuario);
        if (actualizado == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(actualizado);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Usuario> patch(@PathVariable Long id, @RequestBody Usuario usuarioParcial) {
        Usuario actualizado = usuarioService.patchUsuario(id, usuarioParcial);
        if (actualizado == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            usuarioService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
