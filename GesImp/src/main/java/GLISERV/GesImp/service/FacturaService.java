package GLISERV.GesImp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import GLISERV.GesImp.model.Factura;
import GLISERV.GesImp.repository.FacturaRepository;
import java.util.List;

@Service
public class FacturaService {

    @Autowired
    private FacturaRepository facturaRepository;

    public List<Factura> findAll() {
        return facturaRepository.findAll();
    }

    public Factura findById(Long id) {
        return facturaRepository.findById(id).orElse(null);
    }

    public Factura save(Factura factura) {
        return facturaRepository.save(factura);
    }

    public void deleteById(Long id) {
        facturaRepository.deleteById(id);
    }

    public Factura update(Integer id, Factura factura) {
        Factura existente = facturaRepository.findById(id);
        if (existente != null) {
            existente.setFecha(factura.getFecha());
            existente.setTotal(factura.getTotal());
            existente.setPedido(factura.getPedido());
            existente.setTipoPago(factura.getTipoPago());
            return facturaRepository.save(existente);
        } else {
            return null;
        }
    }

    public Factura patch(Integer id, Factura facturaParcial) {
        Factura existente = facturaRepository.findById(id);
        if (existente != null) {
            if (facturaParcial.getFecha() != null) {
                existente.setFecha(facturaParcial.getFecha());
            }
            if (facturaParcial.getTotal() != null) {
                existente.setTotal(facturaParcial.getTotal());
            }
            if (facturaParcial.getPedido() != null) {
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
}