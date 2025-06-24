package GLISERV.GesImp.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import GLISERV.GesImp.model.Usuario;
import GLISERV.GesImp.repository.FacturaRepository;
import GLISERV.GesImp.repository.PedidoRepository;
import GLISERV.GesImp.repository.UsuarioRepository;

@SpringBootTest
public class UsuarioServiceTest {

    @Autowired
    private UsuarioService usuarioService;

    @MockBean
    private UsuarioRepository usuarioRepository;

    @MockBean
    private PedidoRepository pedidoRepository;

    @MockBean
    private FacturaRepository facturaRepository;


    private Usuario createUsuario() {
        return new Usuario(1, "Juan Pérez", "juan@example.com", "ADMIN", "1234");
    }

    @Test
    public void testFindAll() {
        when(usuarioRepository.findAll()).thenReturn(List.of(createUsuario()));
        List<Usuario> usuarios = usuarioService.findAll();
        assertNotNull(usuarios);
        assertEquals(1, usuarios.size());
    }

    @Test
    public void testFindById() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(createUsuario()));
        Usuario usuario = usuarioService.findById(1L);
        assertNotNull(usuario);
        assertEquals(1, usuario.getId());
    }

    @Test
    public void testSave() {
        Usuario usuario = createUsuario();
        when(usuarioRepository.save(usuario)).thenReturn(usuario);
        Usuario saved = usuarioService.save(usuario);
        assertNotNull(saved);
        assertEquals("juan@example.com", saved.getEmail());
    }

    @Test
    public void testUpdate() {
        Usuario existing = createUsuario();
        Usuario updatedData = new Usuario(null, "Pedro", "pedro@example.com", "USER", "abcd");

        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(existing);

        Usuario updated = usuarioService.update(1L, updatedData);
        assertNotNull(updated);
        assertEquals("Pedro", updated.getNombre());
        assertEquals("pedro@example.com", updated.getEmail());
    }

    @Test
    public void testPatch() {
        Usuario existing = createUsuario();
        Usuario patchData = new Usuario();
        patchData.setRol("GESTOR");

        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(existing);

        Usuario patched = usuarioService.patch(1L, patchData);
        assertNotNull(patched);
        assertEquals("GESTOR", patched.getRol());
    }

    @Test
    public void testDeleteById() {
        Usuario usuario = new Usuario();
        usuario.setId(1);

        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(pedidoRepository.findByUsuarioId(1L)).thenReturn(List.of());

        usuarioService.deleteById(1L);

        verify(usuarioRepository, times(1)).delete(usuario);
    }

}
