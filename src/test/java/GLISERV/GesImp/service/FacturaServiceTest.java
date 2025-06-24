package GLISERV.GesImp.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import GLISERV.GesImp.model.Factura;
import GLISERV.GesImp.model.Pedido;
import GLISERV.GesImp.model.Producto;
import GLISERV.GesImp.model.TipoPago;
import GLISERV.GesImp.repository.FacturaRepository;
import GLISERV.GesImp.repository.PedidoRepository;
import GLISERV.GesImp.repository.ProductoRepository;

@SpringBootTest
public class FacturaServiceTest {

    @Autowired
    private FacturaService facturaService;

    @MockBean
    private FacturaRepository facturaRepository;

    @MockBean
    private PedidoRepository pedidoRepository;

    @MockBean
    private ProductoRepository productoRepository;

    private Factura createFactura() {
        Pedido pedido = new Pedido();
        pedido.setId(1);

        Producto producto = new Producto();
        producto.setId(1);
        producto.setPrecio(500);

        pedido.setProducto(producto);
        pedido.setCantidad(2);

        TipoPago tipoPago = new TipoPago();

        Factura factura = new Factura();
        factura.setId(1);
        factura.setFecha(new Date());
        factura.setPedido(pedido);
        factura.setTipoPago(tipoPago);

        return factura;
    }

    @Test
    public void testFindAll() {
        when(facturaRepository.findAll()).thenReturn(List.of(createFactura()));
        List<Factura> facturas = facturaService.findAll();
        assertNotNull(facturas);
        assertEquals(1, facturas.size());
    }

    @Test
    public void testFindById() {
        when(facturaRepository.findById(1L)).thenReturn(Optional.of(createFactura()));
        Factura factura = facturaService.findById(1L);
        assertNotNull(factura);
        assertEquals(1, factura.getId());
    }

    @Test
    public void testSave() {
        Factura factura = createFactura();
        Pedido pedido = factura.getPedido();
        Producto producto = pedido.getProducto();

        when(pedidoRepository.findById(pedido.getId())).thenReturn(pedido);
        when(productoRepository.findById(producto.getId())).thenReturn(producto);
        when(facturaRepository.save(any(Factura.class))).thenReturn(factura);

        Factura savedFactura = facturaService.save(factura);
        assertNotNull(savedFactura);
        assertEquals(1, savedFactura.getId());
    }

    @Test
    public void testUpdate() {
        Factura factura = createFactura();
        Pedido pedido = factura.getPedido();
        Producto producto = pedido.getProducto();

        when(facturaRepository.findById(1)).thenReturn(factura);
        when(pedidoRepository.findById(pedido.getId())).thenReturn(pedido);
        when(productoRepository.findById(producto.getId())).thenReturn(producto);
        when(facturaRepository.save(any(Factura.class))).thenReturn(factura);

        Factura updated = facturaService.update(1, factura);
        assertNotNull(updated);
        assertEquals(1, updated.getId());
    }

    @Test
    public void testPatch() {
        Factura existente = createFactura();
        Factura patchData = new Factura();
        patchData.setFecha(new Date());

        Pedido pedido = existente.getPedido();
        Producto producto = pedido.getProducto();

        when(facturaRepository.findById(1)).thenReturn(existente);
        when(pedidoRepository.findById(pedido.getId())).thenReturn(pedido);
        when(productoRepository.findById(producto.getId())).thenReturn(producto);
        when(facturaRepository.save(any(Factura.class))).thenReturn(existente);

        Factura patched = facturaService.patch(1, patchData);
        assertNotNull(patched);
        assertEquals(existente.getId(), patched.getId());
    }

    @Test
    public void testDeleteById() {
        doNothing().when(facturaRepository).deleteById(1L);
        facturaService.deleteById(1L);
        verify(facturaRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testBuscarPorTipoPagoYTotalMinimo() {
        Factura factura = createFactura();

        when(facturaRepository.buscarPorTipoPagoYTotalMinimo("Transferencia", 30000))
            .thenReturn(List.of(factura));

        List<Factura> resultado = facturaService.buscarPorTipoPagoYTotalMinimo("Transferencia", 30000);

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(factura.getId(), resultado.get(0).getId());
    }

    @Test
    public void testBuscarFacturasPorUsuarioProductoYTipoPago() {
        Factura factura = createFactura();

        when(facturaRepository.buscarFacturasPorUsuarioProductoYTipoPago("Juan", "Papel", "Transferencia"))
            .thenReturn(List.of(factura));

        List<Factura> resultado = facturaService.buscarFacturasPorUsuarioProductoYTipoPago("Juan", "Papel", "Transferencia");

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(factura.getId(), resultado.get(0).getId());
    }

@Test
public void testBuscarFacturasPorUsuarioYProducto() {
    Factura factura = createFactura();

    when(facturaRepository.buscarFacturasPorUsuarioYProducto("Juan", "Papel"))
        .thenReturn(List.of(factura));

    List<Factura> resultado = facturaService.buscarFacturasPorUsuarioYProducto("Juan", "Papel");

    assertNotNull(resultado);
    assertEquals(1, resultado.size());
    assertEquals(factura.getId(), resultado.get(0).getId());
}

}
