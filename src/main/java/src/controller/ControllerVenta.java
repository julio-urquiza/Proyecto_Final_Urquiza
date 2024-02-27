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

import src.models.Venta;
import src.service.*;

@RestController
@RequestMapping("/venta")
public class ControllerVenta 
{
    @Autowired
    private ServiceVenta serviceVenta;
    @Autowired
    private ServiceVentaDetalle serviceVentaDetalle;

    @GetMapping
    public List<Venta> getVenta()
    {
        return serviceVenta.get();
    }

    @GetMapping("/cantProductosVendidos")
    public String cantidadProductosVendidos()
    {
        return serviceVentaDetalle.cantidadProdructoVendidos();
    }

    @GetMapping(path = "/{id}")
    public Object getVentaById(@PathVariable("id") Long id)
    {
        return serviceVenta.getById(id);
    }

    @PostMapping
    public String saveVenta(@RequestBody Venta venta)
    {
        return serviceVenta.post(venta);
    }

    @PutMapping(path = "/{id}")
    public String updateVenta(@RequestBody Venta venta,@PathVariable("id") Long id)
    {
        return serviceVenta.put(venta, id);
    }

    @DeleteMapping(path = "/{id}")
    public String deleteVenta(@PathVariable Long id)
    {
        return serviceVenta.delete(id);
    }
}
