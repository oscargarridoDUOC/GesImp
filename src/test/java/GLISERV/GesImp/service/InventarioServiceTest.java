package GLISERV.GesImp.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import GLISERV.GesImp.model.Inventario;
import GLISERV.GesImp.model.Producto;
import GLISERV.GesImp.repository.InventarioRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class InventarioServiceTest {

    @Autowired
    private InventarioService inventarioService;

    @MockBean
    private InventarioRepository inventarioRepository;

    private Inventario createInventario() {
        Producto producto = new Producto();
        producto.setId(1);
        producto.setNombre("Producto Prueba");

        return new Inventario(
            1,
            "Papel",
            50,
            producto
        );
    }

    @Test
    public void testFindAll() {
        when(inventarioRepository.findAll()).thenReturn(List.of(createInventario()));
        List<Inventario> inventarios = inventarioService.findAll();
        assertNotNull(inventarios);
        assertEquals(1, inventarios.size());
    }

    @Test
    public void testFindById() {
        when(inventarioRepository.findById(1L)).thenReturn(Optional.of(createInventario()));
        Inventario inventario = inventarioService.findById(1L);
        assertNotNull(inventario);
        assertEquals(1, inventario.getId());
    }

    @Test
    public void testFindByMaterial() {
        when(inventarioRepository.findByMaterial("Papel")).thenReturn(List.of(createInventario()));
        List<Inventario> inventarios = inventarioService.findByMaterial("Papel");
        assertNotNull(inventarios);
        assertEquals(1, inventarios.size());
        assertEquals("Papel", inventarios.get(0).getMaterial());
    }

    @Test
    public void testSave() {
        Inventario inventario = createInventario();
        when(inventarioRepository.save(inventario)).thenReturn(inventario);
        Inventario saved = inventarioService.save(inventario);
        assertNotNull(saved);
        assertEquals(1, saved.getId());
    }

    @Test
    public void testUpdate() {
        Inventario original = createInventario();
        Inventario nuevo = createInventario();
        nuevo.setMaterial("Tinta");
        nuevo.setCantidad(100);

        when(inventarioRepository.findById(1L)).thenReturn(Optional.of(original));
        when(inventarioRepository.save(any(Inventario.class))).thenReturn(nuevo);

        Inventario actualizado = inventarioService.update(1L, nuevo);
        assertNotNull(actualizado);
        assertEquals("Tinta", actualizado.getMaterial());
        assertEquals(100, actualizado.getCantidad());
    }

    @Test
    public void testPatch() {
        Inventario original = createInventario();
        Inventario parcial = new Inventario();
        parcial.setCantidad(75);

        when(inventarioRepository.findById(1L)).thenReturn(Optional.of(original));
        when(inventarioRepository.save(any(Inventario.class))).thenReturn(original);

        Inventario actualizado = inventarioService.patch(1L, parcial);
        assertNotNull(actualizado);
        assertEquals(75, actualizado.getCantidad());
        assertEquals("Papel", actualizado.getMaterial());
    }

    @Test
    public void testDeleteById() {
        doNothing().when(inventarioRepository).deleteById(1L);
        inventarioService.deleteById(1L);
        verify(inventarioRepository, times(1)).deleteById(1L);
    }
    
    @Test
    public void testBuscarInventarioPorTipoYRolUsuario() {
        Inventario inventario = createInventario();

        when(inventarioRepository.buscarInventarioPorTipoYRolUsuario("Bicicleta", "cliente"))
            .thenReturn(List.of(inventario));

        List<Inventario> resultado = inventarioService.buscarInventarioPorTipoYRolUsuario("Bicicleta", "cliente");

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(inventario.getId(), resultado.get(0).getId());
    }

}
