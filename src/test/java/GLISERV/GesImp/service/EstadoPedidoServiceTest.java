package GLISERV.GesImp.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import GLISERV.GesImp.model.EstadoPedido;
import GLISERV.GesImp.model.Pedido;
import GLISERV.GesImp.repository.EstadoPedidoRepository;
import GLISERV.GesImp.repository.PedidoRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class EstadoPedidoServiceTest {

    @Autowired
    private EstadoPedidoService estadoPedidoService;

    @MockBean
    private EstadoPedidoRepository estadoPedidoRepository;

    @MockBean
    private PedidoRepository pedidoRepository;

    private EstadoPedido createEstadoPedido() {
        return new EstadoPedido(1, "Pendiente");
    }

    @Test
    public void testFindAll() {
        when(estadoPedidoRepository.findAll()).thenReturn(List.of(createEstadoPedido()));
        List<EstadoPedido> estados = estadoPedidoService.findAll();
        assertNotNull(estados);
        assertEquals(1, estados.size());
        assertEquals("Pendiente", estados.get(0).getNombre());
    }

    @Test
    public void testFindById() {
        when(estadoPedidoRepository.findById(1L)).thenReturn(Optional.of(createEstadoPedido()));
        EstadoPedido estado = estadoPedidoService.findById(1L);
        assertNotNull(estado);
        assertEquals(1, estado.getId());
        assertEquals("Pendiente", estado.getNombre());
    }

    @Test
    public void testFindByNombre() {
        when(estadoPedidoRepository.findByNombre("Pendiente")).thenReturn(List.of(createEstadoPedido()));
        List<EstadoPedido> estados = estadoPedidoService.findByNombre("Pendiente");
        assertNotNull(estados);
        assertFalse(estados.isEmpty());
        assertEquals("Pendiente", estados.get(0).getNombre());
    }

    @Test
    public void testSave() {
        EstadoPedido estado = createEstadoPedido();
        when(estadoPedidoRepository.save(estado)).thenReturn(estado);
        EstadoPedido savedEstado = estadoPedidoService.save(estado);
        assertNotNull(savedEstado);
        assertEquals("Pendiente", savedEstado.getNombre());
    }

    @Test
    public void testDeleteById() {
        EstadoPedido estado = new EstadoPedido(1, "En Proceso");
        Pedido pedido = new Pedido();
        pedido.setId(1);
        pedido.setEstado(estado);

        when(estadoPedidoRepository.findById(1L)).thenReturn(Optional.of(estado));
        when(pedidoRepository.findByEstado(estado)).thenReturn(List.of(pedido));

        doNothing().when(pedidoRepository).delete(pedido);
        doNothing().when(estadoPedidoRepository).delete(estado);

        estadoPedidoService.deleteById(1L);

        verify(pedidoRepository, times(1)).delete(pedido);
        verify(estadoPedidoRepository, times(1)).delete(estado);
    }

    @Test
    public void testUpdate() {
        EstadoPedido estadoExistente = createEstadoPedido();
        EstadoPedido estadoNuevo = new EstadoPedido(null, "En proceso");

        when(estadoPedidoRepository.findById(1L)).thenReturn(Optional.of(estadoExistente));
        when(estadoPedidoRepository.save(any(EstadoPedido.class))).thenReturn(estadoExistente);

        EstadoPedido actualizado = estadoPedidoService.update(1L, estadoNuevo);
        assertNotNull(actualizado);
        assertEquals("En proceso", actualizado.getNombre());
    }

    @Test
    public void testPatch() {
        EstadoPedido estadoExistente = createEstadoPedido();
        EstadoPedido patch = new EstadoPedido();
        patch.setNombre("Completado");

        when(estadoPedidoRepository.findById(1L)).thenReturn(Optional.of(estadoExistente));
        when(estadoPedidoRepository.save(any(EstadoPedido.class))).thenReturn(estadoExistente);

        EstadoPedido actualizado = estadoPedidoService.patch(1L, patch);
        assertNotNull(actualizado);
        assertEquals("Completado", actualizado.getNombre());
    }

}
