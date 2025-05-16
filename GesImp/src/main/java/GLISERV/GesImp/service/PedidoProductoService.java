package GLISERV.GesImp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import GLISERV.GesImp.model.Inventario;
import GLISERV.GesImp.model.PedidoProducto;
import GLISERV.GesImp.repository.PedidoProductoRepository;

import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PedidoProductoService {

    @Autowired
    private PedidoProductoRepository pedidoProductoRepository;
    
    public List<PedidoProducto> findAll() {
        return pedidoProductoRepository.findAll();
    }
    public PedidoProducto findById(Long id) {
        return pedidoProductoRepository.findById(id);
    }

    public PedidoProducto save(PedidoProducto pedidoProducto) {
        return pedidoProductoRepository.save(pedidoProducto);
    }

    public void deleteById(Integer id) {
        pedidoProductoRepository.deleteById(id);
    }

    public PedidoProducto updatePedidoProducto(Long id, PedidoProducto pedidoProducto) {
        PedidoProducto pedidoProductoToUpdate = pedidoProductoRepository.findById(id);
        if (pedidoProductoToUpdate != null) {
            pedidoProductoToUpdate.setPedido(pedidoProducto.getPedido());
            pedidoProductoToUpdate.setProducto(pedidoProducto.getProducto());
            return pedidoProductoRepository.save(pedidoProductoToUpdate);
        } else {
            return null;
        }
    }

    public PedidoProducto patchPedidoProducto(Long id, PedidoProducto pedidoProductoParcial) {
        PedidoProducto pedidoProductoToUpdate = pedidoProductoRepository.findById(id);
        if (pedidoProductoToUpdate != null) {
            if (pedidoProductoParcial.getPedido() != null) {
                pedidoProductoToUpdate.setPedido(pedidoProductoParcial.getPedido());
            }
            if (pedidoProductoParcial.getProducto() != null) {
                pedidoProductoToUpdate.setProducto(pedidoProductoParcial.getProducto());
            }
            return pedidoProductoRepository.save(pedidoProductoToUpdate);
        } else {
            return null;
        }
    }
}
