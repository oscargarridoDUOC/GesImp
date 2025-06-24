package GLISERV.GesImp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import GLISERV.GesImp.model.EstadoPedido;
import GLISERV.GesImp.model.Factura;
import GLISERV.GesImp.model.Pedido;
import GLISERV.GesImp.repository.EstadoPedidoRepository;
import GLISERV.GesImp.repository.FacturaRepository;
import GLISERV.GesImp.repository.PedidoRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class EstadoPedidoService {

    @Autowired
    private EstadoPedidoRepository estadoPedidoRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private FacturaRepository facturaRepository;

    public List<EstadoPedido> findAll() {
        return estadoPedidoRepository.findAll();
    }

    public EstadoPedido findById(Long id) {
        return estadoPedidoRepository.findById(id).orElse(null);
    }

    public List<EstadoPedido> findByNombre(String nombre) {
        return estadoPedidoRepository.findByNombre(nombre);
    }

    public EstadoPedido save(EstadoPedido estadoPedido) {
        return estadoPedidoRepository.save(estadoPedido);
    }

    public void deleteById(Long id) {
        EstadoPedido estado = estadoPedidoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("EstadoPedido no encontrado"));

        List<Pedido> pedidos = pedidoRepository.findByEstado(estado);

        for (Pedido pedido : pedidos) {
            List<Factura> facturas = facturaRepository.findByPedido(pedido);
            for (Factura factura : facturas) {
                facturaRepository.delete(factura);
            }

            pedidoRepository.delete(pedido);
        }

        estadoPedidoRepository.delete(estado);
    }

    public EstadoPedido update(Long id, EstadoPedido estadoPedido) {
        Optional<EstadoPedido> estadoPedidoOptional = estadoPedidoRepository.findById(id);
        if (estadoPedidoOptional.isPresent()) {
            EstadoPedido estadoPedidoToUpdate = estadoPedidoOptional.get();
            estadoPedidoToUpdate.setNombre(estadoPedido.getNombre());
            return estadoPedidoRepository.save(estadoPedidoToUpdate);
        } else {
            return null;
        }
    }

    public EstadoPedido patch(Long id, EstadoPedido estadoPedidoParcial) {
        Optional<EstadoPedido> estadoPedidoOptional = estadoPedidoRepository.findById(id);
        if (estadoPedidoOptional.isPresent()) {
            EstadoPedido estadoPedidoToUpdate = estadoPedidoOptional.get();

            if (estadoPedidoParcial.getNombre() != null) {
                estadoPedidoToUpdate.setNombre(estadoPedidoParcial.getNombre());
            }

            return estadoPedidoRepository.save(estadoPedidoToUpdate);
        } else {
            return null;
        }
    }
}
