package GLISERV.GesImp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import GLISERV.GesImp.repository.FacturaRepository;
import GLISERV.GesImp.repository.PedidoRepository;
import GLISERV.GesImp.repository.UsuarioRepository;
import GLISERV.GesImp.model.Factura;
import GLISERV.GesImp.model.Pedido;
import GLISERV.GesImp.model.Usuario;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private FacturaRepository facturaRepository;


    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    public Usuario findById(Long id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    public Usuario save(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public void deleteById(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        List<Pedido> pedidos = pedidoRepository.findByUsuarioId(id);

        for (Pedido pedido : pedidos) {
            List<Factura> facturas = facturaRepository.findByPedido(pedido);
            for (Factura factura : facturas) {
                facturaRepository.delete(factura);
            }
            pedidoRepository.delete(pedido);
        }

        usuarioRepository.delete(usuario);
    }


    public Usuario update(Long id, Usuario usuario) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findById((long) id);
        if (usuarioOptional.isPresent()) {
            Usuario usuarioToUpdate = usuarioOptional.get();
            usuarioToUpdate.setNombre(usuario.getNombre());
            usuarioToUpdate.setEmail(usuario.getEmail());
            usuarioToUpdate.setRol(usuario.getRol());
            usuarioToUpdate.setPassword(usuario.getPassword());
            return usuarioRepository.save(usuarioToUpdate);
        } else {
            return null;
        }
    }

    public Usuario patch(Long id, Usuario usuarioParcial) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findById((long) id);
        if (usuarioOptional.isPresent()) {

            Usuario usuarioToUpdate = usuarioOptional.get();

            if (usuarioParcial.getNombre() != null) {
                usuarioToUpdate.setNombre(usuarioParcial.getNombre());
            }

            if (usuarioParcial.getEmail() != null) {
                usuarioToUpdate.setEmail(usuarioParcial.getEmail());
            }

            if (usuarioParcial.getRol() != null) {
                usuarioToUpdate.setRol(usuarioParcial.getRol());
            }

            if (usuarioParcial.getPassword() != null) {
                usuarioToUpdate.setPassword(usuarioParcial.getPassword());
            }

            return usuarioRepository.save(usuarioToUpdate);
        } else {
            return null;
        }
    }
}

