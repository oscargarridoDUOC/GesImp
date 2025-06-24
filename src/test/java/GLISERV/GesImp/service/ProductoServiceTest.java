package GLISERV.GesImp.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import GLISERV.GesImp.model.Producto;
import GLISERV.GesImp.repository.ProductoRepository;

@SpringBootTest
public class ProductoServiceTest {

    @Autowired
    private ProductoService productoService;

    @MockBean
    private ProductoRepository productoRepository;

    private Producto createProducto() {
        return new Producto(1, "Tarjeta presentacion", "Impresión", 100);
    }

    @Test
    public void testFindAll() {
        when(productoRepository.findAll()).thenReturn(List.of(createProducto()));
        List<Producto> productos = productoService.findAll();
        assertNotNull(productos);
        assertEquals(1, productos.size());
    }

    @Test
    public void testFindById() {
        when(productoRepository.findById(1L)).thenReturn(Optional.of(createProducto()));
        Producto producto = productoService.findById(1L);
        assertNotNull(producto);
        assertEquals(1, producto.getId());
    }

    @Test
    public void testSave() {
        Producto producto = createProducto();
        when(productoRepository.save(producto)).thenReturn(producto);
        Producto saved = productoService.save(producto);
        assertNotNull(saved);
        assertEquals("Tarjeta presentacion", saved.getNombre());
    }

    @Test
    public void testUpdate() {
        Producto existing = createProducto();
        Producto updatedData = new Producto(null, "Cuaderno", "Papelería", 500);

        when(productoRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(productoRepository.save(any(Producto.class))).thenReturn(existing);

        Producto updated = productoService.update(1L, updatedData);
        assertNotNull(updated);
        assertEquals("Cuaderno", updated.getNombre());
        assertEquals("Papelería", updated.getTipo());
        assertEquals(500, updated.getPrecio());
    }

    @Test
    public void testPatch() {
        Producto existing = createProducto();
        Producto patchData = new Producto();
        patchData.setPrecio(250);

        when(productoRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(productoRepository.save(any(Producto.class))).thenReturn(existing);

        Producto patched = productoService.patch(1L, patchData);
        assertNotNull(patched);
        assertEquals(250, patched.getPrecio());
    }

    @Test
    public void testDelete() {
        doNothing().when(productoRepository).deleteById(1L);
        productoService.deleteById(1L);
        verify(productoRepository, times(1)).deleteById(1L);
    }
}
