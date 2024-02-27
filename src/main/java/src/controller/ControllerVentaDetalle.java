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

import src.models.VentaDetalle;
import src.service.ServiceVentaDetalle;

@RestController
@RequestMapping("/ventaDetalles")
public class ControllerVentaDetalle 
{
    @Autowired
    private ServiceVentaDetalle serviceVentaDetalle;

    @GetMapping
    public List<VentaDetalle> getVenta_Detalles()
    {
        return serviceVentaDetalle.get();
    }

    @GetMapping(path = "/{id}")
    public Object getVenta_DetallesById(@PathVariable("id") Long id)
    {
        return serviceVentaDetalle.getById(id);
    }

    @PostMapping
    public Object saveVenta_Detalles(@RequestBody VentaDetalle ventaDetalle)
    {
        return serviceVentaDetalle.post(ventaDetalle);
    }

    @PutMapping(path = "/{id}")
    public String updateVenta_Detalles(@RequestBody VentaDetalle ventaDetalle,@PathVariable("id") Long id)
    {
        return serviceVentaDetalle.put(ventaDetalle, id);
    }

    @DeleteMapping(path = "/{id}")
    public String deleteVenta_Detalles(@PathVariable Long id)
    {
        return serviceVentaDetalle.delete(id);
    }
}
