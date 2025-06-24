package GLISERV.GesImp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import GLISERV.GesImp.model.Factura;
import GLISERV.GesImp.model.Pedido;
import GLISERV.GesImp.model.Producto;
import GLISERV.GesImp.repository.FacturaRepository;
import GLISERV.GesImp.repository.PedidoRepository;
import GLISERV.GesImp.repository.ProductoRepository;
import jakarta.transaction.Transactional;

import java.util.List;

@Service
@Transactional
public class FacturaService {

    @Autowired
    private FacturaRepository facturaRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ProductoRepository productoRepository;

    private int calcularTotal(Pedido pedido) {
        Producto producto = productoRepository.findById(pedido.getProducto().getId());
        return pedido.getCantidad() * producto.getPrecio();
    }

    public List<Factura> findAll() {
        return facturaRepository.findAll();
    }

    public Factura findById(Long id) {
        return facturaRepository.findById(id).orElse(null);
    }

    public Factura save(Factura factura) {
        Pedido pedido = pedidoRepository.findById(factura.getPedido().getId());
        factura.setTotal(calcularTotal(pedido));
        return facturaRepository.save(factura);
    }

    public Factura update(Integer id, Factura factura) {
        Factura existente = facturaRepository.findById(id);
        if (existente != null) {
            Pedido pedido = pedidoRepository.findById(factura.getPedido().getId());
            existente.setTotal(calcularTotal(pedido));
            existente.setPedido(factura.getPedido());
            existente.setTipoPago(factura.getTipoPago());
            return facturaRepository.save(existente);
        } else {
            return null;
        }
    }

    public void deleteById(Long id) {
        facturaRepository.deleteById(id);
    }

    public Factura patch(Integer id, Factura facturaParcial) {
        Factura existente = facturaRepository.findById(id);
        if (existente != null) {
            if (facturaParcial.getFecha() != null) {
                existente.setFecha(facturaParcial.getFecha());
            }
            if (facturaParcial.getPedido() != null) {
                Pedido pedido = pedidoRepository.findById(facturaParcial.getPedido().getId());
                existente.setTotal(calcularTotal(pedido));
                existente.setPedido(facturaParcial.getPedido());
            }
            if (facturaParcial.getTipoPago() != null) {
                existente.setTipoPago(facturaParcial.getTipoPago());
            }
            return facturaRepository.save(existente);
        } else {
            return null;
        }
    }

    public List<Factura> buscarPorTipoPagoYTotalMinimo(String tipoPago, Integer montoMinimo){
        return facturaRepository.buscarPorTipoPagoYTotalMinimo(tipoPago, montoMinimo);
    }

    public List<Factura> buscarFacturasPorUsuarioProductoYTipoPago(String nombreUsuario, String tipoProducto, String nombreTipoPago){
        return facturaRepository.buscarFacturasPorUsuarioProductoYTipoPago(nombreUsuario, tipoProducto, nombreTipoPago);
    }

    public List<Factura> buscarFacturasPorUsuarioYProducto(String nombreUsuario, String nombreProducto){
        return facturaRepository.buscarFacturasPorUsuarioYProducto(nombreUsuario, nombreProducto);
    }
}
