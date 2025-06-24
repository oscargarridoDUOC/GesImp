package GLISERV.GesImp.service;

import static org.junit.jupiter.api.Assertions.*;
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
import GLISERV.GesImp.model.TipoPago;
import GLISERV.GesImp.repository.FacturaRepository;
import GLISERV.GesImp.repository.TipoPagoRepository;

@SpringBootTest
public class TipoPagoServiceTest {

    @Autowired
    private TipoPagoService tipoPagoService;

    @MockBean
    private TipoPagoRepository tipoPagoRepository;

    @MockBean
    private FacturaRepository facturaRepository;
    
    private TipoPago createTipoPago() {
        return new TipoPago(1, "Débito");
    }

    @Test
    public void testFindAll() {
        when(tipoPagoRepository.findAll()).thenReturn(List.of(createTipoPago()));
        List<TipoPago> tipos = tipoPagoService.findAll();
        assertNotNull(tipos);
        assertEquals(1, tipos.size());
    }

    @Test
    public void testFindById() {
        when(tipoPagoRepository.findById(1L)).thenReturn(Optional.of(createTipoPago()));
        TipoPago tipoPago = tipoPagoService.findById(1L);
        assertNotNull(tipoPago);
        assertEquals(1, tipoPago.getId());
    }

    @Test
    public void testSave() {
        TipoPago tipoPago = createTipoPago();
        when(tipoPagoRepository.save(tipoPago)).thenReturn(tipoPago);
        TipoPago saved = tipoPagoService.save(tipoPago);
        assertNotNull(saved);
        assertEquals(1, saved.getId());
    }

    @Test
    public void testUpdate() {
        TipoPago existing = createTipoPago();
        TipoPago updateData = new TipoPago(null, "Crédito");

        when(tipoPagoRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(tipoPagoRepository.save(any(TipoPago.class))).thenReturn(existing);

        TipoPago updated = tipoPagoService.update(1L, updateData);
        assertNotNull(updated);
        assertEquals("Crédito", updated.getNombre());
    }

    @Test
    public void testPatch() {
        TipoPago existing = createTipoPago();
        TipoPago patchData = new TipoPago();
        patchData.setNombre("Transferencia");

        when(tipoPagoRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(tipoPagoRepository.save(any(TipoPago.class))).thenReturn(existing);

        TipoPago patched = tipoPagoService.patch(1L, patchData);
        assertNotNull(patched);
        assertEquals("Transferencia", patched.getNombre());
    }

    @Test
    public void testDeleteById() {
        TipoPago tipoPago = new TipoPago(1, "Transferencia");

        Factura factura = new Factura();
        factura.setId(1);
        factura.setFecha(new Date());
        factura.setTotal(1000);
        factura.setPedido(new Pedido());
        factura.setTipoPago(tipoPago);

        when(tipoPagoRepository.findById(1L)).thenReturn(java.util.Optional.of(tipoPago));
        when(facturaRepository.findByTipoPago(tipoPago)).thenReturn(List.of(factura));
        doNothing().when(facturaRepository).delete(factura);
        doNothing().when(tipoPagoRepository).delete(tipoPago);

        tipoPagoService.deleteById(1L);

        verify(facturaRepository, times(1)).delete(factura);
        verify(tipoPagoRepository, times(1)).delete(tipoPago);
    }
}
