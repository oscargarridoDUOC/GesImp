package GLISERV.GesImp;

import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import GLISERV.GesImp.model.EstadoPedido;
import GLISERV.GesImp.model.Factura;
import GLISERV.GesImp.model.Inventario;
import GLISERV.GesImp.model.Pedido;
import GLISERV.GesImp.model.Producto;
import GLISERV.GesImp.model.TipoPago;
import GLISERV.GesImp.model.Usuario;
import GLISERV.GesImp.repository.EstadoPedidoRepository;
import GLISERV.GesImp.repository.FacturaRepository;
import GLISERV.GesImp.repository.InventarioRepository;
import GLISERV.GesImp.repository.PedidoRepository;
import GLISERV.GesImp.repository.ProductoRepository;
import GLISERV.GesImp.repository.TipoPagoRepository;
import GLISERV.GesImp.repository.UsuarioRepository;
import net.datafaker.Faker;

@Profile("dev")
@Component
public class DataLoader implements CommandLineRunner{

    
    @Autowired
    private EstadoPedidoRepository estadoPedidoRepository;
    
    @Autowired
    private FacturaRepository facturaRepository;

    @Autowired
    private InventarioRepository inventarioRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private TipoPagoRepository tipoPagoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public void run(String... args) throws Exception {
        
        Faker faker = new Faker();
        Random random = new Random();
        
        
        for (int i = 0; i < 10; i++) {
            Usuario usuario = new Usuario();
            usuario.setEmail(faker.internet().emailAddress());
            usuario.setNombre(faker.name().fullName());
            usuario.setRol(faker.options().option("ADMIN", "CLIENTE", "EMPLEADO"));
            usuario.setPassword(faker.internet().password(8, 12));
            
            usuarioRepository.save(usuario);
        }
        
        for (int i = 0; i < 5; i++) {
            EstadoPedido estado = new EstadoPedido();
            estado.setNombre(faker.options().option("Pendiente", "Procesando", "Enviado", "Entregado", "Cancelado"));
            
            estadoPedidoRepository.save(estado);
        }

        
        for (int i = 0; i < 15; i++) {
            Producto producto = new Producto();
            producto.setNombre(faker.commerce().productName());
            producto.setTipo(faker.options().option("Tinta", "Papel", "Cartucho", "Tóner", "Etiqueta", "Resma", "Pliego"));
            producto.setPrecio(faker.number().numberBetween(0, 5000)); 
            
            productoRepository.save(producto);
        }

        List<Producto> productos = productoRepository.findAll();
        List<Usuario> usuarios = usuarioRepository.findAll();
        List<EstadoPedido> estados = estadoPedidoRepository.findAll();
        
        for (int i = 0; i < 15; i++) {
            Pedido pedido = new Pedido();
            Date fechaGeneradaPedido = faker.date().past(30, TimeUnit.DAYS);
            pedido.setFecha(fechaGeneradaPedido);
            pedido.setDetalles(faker.lorem().sentence(5));
            pedido.setCantidad(faker.number().numberBetween(1, 20));
            pedido.setUsuario(usuarios.get(random.nextInt(usuarios.size())));
            pedido.setEstado(estados.get(random.nextInt(estados.size())));
            pedido.setProducto(productos.get(random.nextInt(productos.size())));
            
            pedidoRepository.save(pedido);
        }
        
        for (int i = 0; i < 5; i++) {
            TipoPago tipoPago = new TipoPago();
            tipoPago.setNombre(faker.options().option("Efectivo", "Tarjeta de crédito", "Tarjeta de débito", "Transferencia", "Pago móvil"));
            
            tipoPagoRepository.save(tipoPago);
        }
        
        List<Pedido> pedidos = pedidoRepository.findAll();
        List<TipoPago> tiposPago = tipoPagoRepository.findAll();

        for (int i = 0; i < 10; i++) {
            Factura factura = new Factura();
            Date fechaGeneradaFactura = faker.date().past(30, TimeUnit.DAYS);
            factura.setFecha(fechaGeneradaFactura);
            factura.setTotal(faker.number().numberBetween(1,9999999));
            factura.setPedido(pedidos.get(random.nextInt(pedidos.size())));
            factura.setTipoPago(tiposPago.get(random.nextInt(tiposPago.size())));
            
            facturaRepository.save(factura);
        }
        
        for (int i = 0; i < 10; i++) {
            Inventario inventario = new Inventario();
            inventario.setMaterial(faker.commerce().material());
            inventario.setCantidad(faker.number().numberBetween(1, 500));
            inventario.setProducto(productos.get(random.nextInt(productos.size())));
            
            inventarioRepository.save(inventario);
        }
        
    }

}
