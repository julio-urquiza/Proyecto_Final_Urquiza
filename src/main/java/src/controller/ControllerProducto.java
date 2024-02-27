package src.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import src.models.Producto;
import src.service.ServiceProducto;

@RestController
@RequestMapping("/producto")
public class ControllerProducto 
{
    @Autowired
    private ServiceProducto serviceProducto;

    @GetMapping
    public List<Producto> getProductos()
    {
        return serviceProducto.get();
    }

    @GetMapping(path = "/{id}")
    public Producto getProductoById(@PathVariable("id") Long id)
    {
        return serviceProducto.getById(id);
    }

    @PostMapping
    public String saveProducto(@RequestBody Producto producto)
    {
        return serviceProducto.post(producto);
    }

    @PostMapping("/ingresar_productos")
    public String saveProductos(@RequestBody List<Producto> productos)
    {
        return serviceProducto.postProductos(productos);
    }

    @PutMapping(path = "/{id}")
    public String updateProducto(@RequestBody Producto producto,@PathVariable("id") Long id)
    {
        return serviceProducto.put(producto, id);
    }

    @DeleteMapping(path = "/{id}")
    public String deleteProducto(@PathVariable Long id)
    {
        return serviceProducto.delete(id);
    }

}