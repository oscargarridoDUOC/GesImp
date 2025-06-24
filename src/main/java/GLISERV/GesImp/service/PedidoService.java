package GLISERV.GesImp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import GLISERV.GesImp.model.Factura;
import GLISERV.GesImp.model.Pedido;
import GLISERV.GesImp.repository.FacturaRepository;
import GLISERV.GesImp.repository.PedidoRepository;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private FacturaRepository facturaRepository;

    public List<Pedido> findAll() {
        return pedidoRepository.findAll();
    }

    public Pedido findById(Long id) {
        return pedidoRepository.findById(id).orElse(null);
    }
    
    public List<Pedido> findByUsuario(Long usuarioId) {
        return pedidoRepository.findByUsuarioId(usuarioId);
    }
    public Pedido save(Pedido pedido) {
        return pedidoRepository.save(pedido);
    }

    public void deleteById(Long id) {
        Pedido pedido = pedidoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));

        List<Factura> facturas = facturaRepository.findByPedido(pedido);

        for (Factura factura : facturas) {
            facturaRepository.delete(factura);
        }

        pedidoRepository.delete(pedido);
    }


    public Pedido update(Long id, Pedido pedido) {
        Optional<Pedido> pedidoOptional = pedidoRepository.findById(id);
        if (pedidoOptional.isPresent()) {
            Pedido pedidoToUpdate = pedidoOptional.get();
            pedidoToUpdate.setFecha(pedido.getFecha());
            pedidoToUpdate.setCantidad(pedido.getCantidad());
            pedidoToUpdate.setProducto(pedido.getProducto());
            pedidoToUpdate.setEstado(pedido.getEstado());
            pedidoToUpdate.setUsuario(pedido.getUsuario());
            return pedidoRepository.save(pedidoToUpdate);
        } else {
            return null;
        }
    }

    public Pedido patch(Long id, Pedido pedidoParcial) {
        Optional<Pedido> pedidoOptional = pedidoRepository.findById(id);
        if (pedidoOptional.isPresent()) {
            Pedido pedidoToUpdate = pedidoOptional.get();

            if (pedidoParcial.getFecha() != null) {
                pedidoToUpdate.setFecha(pedidoParcial.getFecha());
            }

            if (pedidoParcial.getCantidad() != null) {
                pedidoToUpdate.setCantidad(pedidoParcial.getCantidad());
            }

            if (pedidoParcial.getProducto() != null) {
                pedidoToUpdate.setProducto(pedidoParcial.getProducto());
            }

            if (pedidoParcial.getEstado() != null) {
                pedidoToUpdate.setEstado(pedidoParcial.getEstado());
            }

            if (pedidoParcial.getUsuario() != null) {
                pedidoToUpdate.setUsuario(pedidoParcial.getUsuario());
            }

            return pedidoRepository.save(pedidoToUpdate);
        } else {
            return null;
        }
    }

    public List<Pedido> buscarPedidosPorEstadoProductoYUsuario(String estado, String nombreProducto, String nombreUsuario){
        return pedidoRepository.buscarPedidosPorEstadoProductoYUsuario(estado, nombreProducto, nombreUsuario);
    }
}
