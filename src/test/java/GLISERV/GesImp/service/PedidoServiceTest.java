package GLISERV.GesImp.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import GLISERV.GesImp.model.EstadoPedido;
import GLISERV.GesImp.model.Factura;
import GLISERV.GesImp.model.Pedido;
import GLISERV.GesImp.model.Producto;
import GLISERV.GesImp.model.Usuario;
import GLISERV.GesImp.repository.FacturaRepository;
import GLISERV.GesImp.repository.PedidoRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class PedidoServiceTest {

    @Autowired
    private PedidoService pedidoService;

    @MockBean
    private PedidoRepository pedidoRepository;

    @MockBean
    private FacturaRepository facturaRepository;

    private Pedido createPedido() {
        Usuario usuario = new Usuario();
        usuario.setId(1);
        usuario.setNombre("pedido1");

        EstadoPedido estado = new EstadoPedido();
        estado.setId(1);
        estado.setNombre("Pendiente");

        Producto producto = new Producto();
        producto.setId(1);
        producto.setNombre("Producto Prueba");

        return new Pedido(
            1,
            new Date(),
            "Detalles de prueba",
            5,
            usuario,
            estado,
            producto
        );
    }

    @Test
    public void testFindAll() {
        when(pedidoRepository.findAll()).thenReturn(List.of(createPedido()));
        List<Pedido> pedidos = pedidoService.findAll();
        assertNotNull(pedidos);
        assertEquals(1, pedidos.size());
    }

    @Test
    public void testFindById() {
        when(pedidoRepository.findById(1L)).thenReturn(Optional.of(createPedido()));
        Pedido pedido = pedidoService.findById(1L);
        assertNotNull(pedido);
        assertEquals(1, pedido.getId());
    }

    @Test
    public void testFindByUsuario() {
        when(pedidoRepository.findByUsuarioId(1L)).thenReturn(List.of(createPedido()));
        List<Pedido> pedidos = pedidoService.findByUsuario(1L);
        assertNotNull(pedidos);
        assertEquals(1, pedidos.size());
        assertEquals("pedido1", pedidos.get(0).getUsuario().getNombre());
    }

    @Test
    public void testSave() {
        Pedido pedido = createPedido();
        when(pedidoRepository.save(pedido)).thenReturn(pedido);
        Pedido saved = pedidoService.save(pedido);
        assertNotNull(saved);
        assertEquals(1, saved.getId());
    }

    @Test
    public void testUpdate() {
        Pedido original = createPedido();
        Pedido nuevo = createPedido();
        nuevo.setCantidad(10);
        nuevo.setDetalles("Detalles actualizados");

        when(pedidoRepository.findById(1L)).thenReturn(Optional.of(original));
        when(pedidoRepository.save(any(Pedido.class))).thenReturn(nuevo);

        Pedido actualizado = pedidoService.update(1L, nuevo);
        assertNotNull(actualizado);
        assertEquals(10, actualizado.getCantidad());
        assertEquals("Detalles actualizados", actualizado.getDetalles());
    }

    @Test
    public void testPatch() {
        Pedido original = createPedido();
        Pedido parcial = new Pedido();
        parcial.setCantidad(20);

        when(pedidoRepository.findById(1L)).thenReturn(Optional.of(original));
        when(pedidoRepository.save(any(Pedido.class))).thenReturn(original);

        Pedido actualizado = pedidoService.patch(1L, parcial);
        assertNotNull(actualizado);
        assertEquals(20, actualizado.getCantidad());
        assertEquals("Detalles de prueba", actualizado.getDetalles());
    }

    @Test
    public void testDeleteById() {
        Long pedidoId = 1L;

        Pedido pedido = new Pedido();
        pedido.setId(1);
        pedido.setUsuario(new Usuario());
        pedido.setEstado(new EstadoPedido());
        pedido.setProducto(new Producto());
        pedido.setCantidad(1);
        pedido.setFecha(new Date());

        Factura factura = new Factura();
        factura.setId(1);
        factura.setPedido(pedido);

        when(pedidoRepository.findById(pedidoId)).thenReturn(Optional.of(pedido));
        when(facturaRepository.findByPedido(pedido)).thenReturn(List.of(factura));
        doNothing().when(facturaRepository).delete(factura);
        doNothing().when(pedidoRepository).delete(pedido);

        pedidoService.deleteById(pedidoId);

        verify(facturaRepository, times(1)).delete(factura);
        verify(pedidoRepository, times(1)).delete(pedido);
    }

    @Test
    public void testBuscarPedidosPorEstadoProductoYUsuario() {
        Pedido pedido = new Pedido();
        pedido.setId(1);

        when(pedidoRepository.buscarPedidosPorEstadoProductoYUsuario("Entregado", "Papel", "Carlos"))
            .thenReturn(List.of(pedido));

        List<Pedido> resultado = pedidoService.buscarPedidosPorEstadoProductoYUsuario("Entregado", "Papel", "Carlos");

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(1, resultado.get(0).getId());
    }


}
