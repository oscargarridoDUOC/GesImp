package GLISERV.GesImp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import GLISERV.GesImp.model.TipoPago;
import GLISERV.GesImp.repository.TipoPagoRepository;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TipoPagoService {

    @Autowired
    private TipoPagoRepository tipoPagoRepository;

    public List<TipoPago> findAll() {
        return tipoPagoRepository.findAll();
    }

    public TipoPago findById(Long id) {
        return tipoPagoRepository.findById(id).orElse(null);
    }

    public List<TipoPago> findByNombre(String nombre) {
        return tipoPagoRepository.findByNombre(nombre);
    }

    public TipoPago save(TipoPago tipoPago) {
        return tipoPagoRepository.save(tipoPago);
    }

    public void deleteById(Long id) {
        tipoPagoRepository.deleteById(id);
    }

    public TipoPago update(Long id, TipoPago tipoPago) {
        Optional<TipoPago> tipoPagoOptional = tipoPagoRepository.findById(id);
        if (tipoPagoOptional.isPresent()) {
            TipoPago tipoPagoToUpdate = tipoPagoOptional.get();
            tipoPagoToUpdate.setNombre(tipoPago.getNombre());
            return tipoPagoRepository.save(tipoPagoToUpdate);
        } else {
            return null;
        }
    }

    public TipoPago patch(Long id, TipoPago tipoPagoParcial) {
        Optional<TipoPago> tipoPagoOptional = tipoPagoRepository.findById(id);
        if (tipoPagoOptional.isPresent()) {
            TipoPago tipoPagoToUpdate = tipoPagoOptional.get();

            if (tipoPagoParcial.getNombre() != null) {
                tipoPagoToUpdate.setNombre(tipoPagoParcial.getNombre());
            }

            return tipoPagoRepository.save(tipoPagoToUpdate);
        } else {
            return null;
        }
    }
}
