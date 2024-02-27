package src.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import src.models.Producto;
import src.models.VentaDetalle;
import src.repository.*;

@Service
public class ServiceVentaDetalle 
{
    @Autowired
    private RepositoryVentaDetalle repoVentaDetalle;
    @Autowired
    private RepositoryProducto repoProducto;
    /**
     * Trae los objetos del tipo VentaDetalle presentes en la base de datos
     * @return Lista (List<VentaDetalle>) que contiene los objetos del tipo VentaDetalle, o null en caso que produzca una excepcion
     */
    public List<VentaDetalle> get()
    {
        try
        {
            return repoVentaDetalle.findAll();
        }
        catch(Exception e)
        {
            return null;
        }
    }
    /**
     * Trae un objeto del tipo VentaDetalle presente en la base de datos correspondiente al id dado 
     * @param id Id (Long) que corresponda al objeto del tipo VentaDetalle
     * @return Objeto (tipo VentaDetalle) correspondiente al id, o un mensaje (String) si no encuentra el objeto o si el id es null 
     */
    public Object getById(Long id)
    {
        try
        {
            if(id!=null)
            {
                return repoVentaDetalle.findById(id).get();
            }
            return "El id no deve ser null";
        }
        catch(Exception e)
        {
            return "No se pudo obtener el detalle";
        }
    }
    /**
     * Inserta un objeto del tipo VentaDetalle en la base de datos
     * @param ventaDetalle Objeto (tipo VentaDetalle) a ingresar
     * @return Objeto (tipo VentaDetalle) que se ingreso en la base de datos, o un mensaje (String) si no se puso ingresar
     */
    public Object post(VentaDetalle ventaDetalle)
    {
        try
        {
            if(ventaDetalle!=null && reducirStockProducto(ventaDetalle))
            {
                ventaDetalle.setPrecioCantidad(caluclarPrecioCantidad(ventaDetalle));
                return repoVentaDetalle.save(ventaDetalle);
            }
            return "El datalle no es valido";
        }
        catch(Exception e)
        {
            return "No se pudo guardar el detalle";
        }
    }
    /**
     * Modifica un objeto del tipo VentaDetalle presente en la base de datos
     * @param ventaDetalle Objeto (tipo VentaDetalle) que contiene las modificaciones
     * @param id Id (Long) del objeto del tipo VentaDetalle que se quiera modificar
     * @return Mensaje (String) que contiene el estado de la operacion
     */
    public String put(VentaDetalle ventaDetalle,Long id)
    {
        try
        {
            if(id!=null && ifExist(id))
            {
                VentaDetalle ventaDetalleModificado= repoVentaDetalle.findById(id).get();
                ventaDetalleModificado.setCantidad(ventaDetalle.getCantidad());
                ventaDetalleModificado.setPrecioCantidad(ventaDetalle.getCantidad());
                ventaDetalleModificado.setProducto(ventaDetalle.getProducto());
                repoVentaDetalle.save(ventaDetalleModificado);
                return "Detalle modificado con exito";
            }
            return "El detalle no es valido ";
        }
        catch(Exception e)
        {
            return "El detalle no se pudo modificar";
        }
    }
    /**
     * Elimina un objeto del tipo VentaDetalle presente en la base de datos
     * @param id Id (Long) del objeto del tipo VentaDetalle que se quiera eliminar 
     * @return Mensaje (String) que contiene el estado de la operacion
     */
    public String delete(Long id)
    {
        try
        {
            if(id!=null && ifExist(id))
            {
                repoVentaDetalle.deleteById(id);
                return "Detalle eliminado con exito";
            }
            return "El id no es valido";
        }
        catch(Exception e)
        {
            return "No se pudo eliminar el detalle";
        }
    }
    /**
     * Comprueba si un id esta dentro de la base de datos
     * @param id Id (Long) a comprobar
     * @return True si encuentra el id dentro de la base de datos o false en caso contrario
     */
    private Boolean ifExist(Long id)
    {
        if(id!=null)
        {
            return repoVentaDetalle.existsById(id);
        }
        return false;
    }
    /**
     * Calcula el precio total de un objeto del tipo VentaDetalle
     * @param ventaDetalle Objeto (tipo VentaDetalle) del cual se quiera calcular 
     * @return Resultado (Float) de la operacion
     */
    private Float caluclarPrecioCantidad(VentaDetalle ventaDetalle)
    {
        try 
        {
            Float total=0f;
            Long id=ventaDetalle.getProducto().getId();
            if(id!=null)
            {
                Producto produto=repoProducto.findById(id).get();
                total=produto.getPrecio()*ventaDetalle.getCantidad();
            }
            return total;
        } 
        catch (Exception e) 
        {
            throw e;
        }
    }
    /**
     * Calcula la cantidad de producto vendidos en toda la base datos
     * @return Mensaje (String) que contiene la cantidad de producto vendidos o un mensaje de error 
     */
    public String cantidadProdructoVendidos()
    {
        List<VentaDetalle> lista = this.get();
        if(lista!=null)
        {
            int cantidad = 0;
            for(int i=0;i<lista.size();i++)
            {
                cantidad+=lista.get(i).getCantidad();
            }
            return "La cantidad de prodcto vendidos es de: "+ cantidad;
        }
        return "Error, la lista es null";
    }
    /**
     * Comprueba que el stock de un producto sea mayor o igual al pedido 
     * @param ventaDetalle Objeto (tipo VentaDetalle) que contiene la informacion para comprobar
     * @return True si el stock es mayor o igual al pedido, o false en caso contrario, o arroja una excepcion si sucede un error
     */
    private Boolean comprobarCantidadProducto(VentaDetalle ventaDetalle)
    {
        //Cantidad de productos solicitados sea menor o igual que el stock del producto
        try
        {
            Long id = ventaDetalle.getProducto().getId();
            int cantidad=ventaDetalle.getCantidad();
            if(id!=null && repoProducto.existsById(id))
            {
                Producto producto = repoProducto.findById(id).get();
                if(producto.getStock()>0 && cantidad<=producto.getStock())
                {
                    return true;
                }
    
            }
            return false;
        }
        catch(Exception e)
        {
            throw e;
        }
    }
    /**
     * Comprueba que se haya podido reducir el stock de un producto
     * @param ventaDetalle Objeto (tipo VentaDetalle) que contiene la informacion para comprobar
     * @return True si se pudo reducir el stock, o false en caso contrario, o arroja una excepcion si sucede un error
     */
    private Boolean reducirStockProducto(VentaDetalle ventaDetalle)
    {
        //Reducir el stock en la cantidad de unidades vendidas
        try
        {
            if(comprobarCantidadProducto(ventaDetalle))
            {
                int cantidad = ventaDetalle.getCantidad();
                Long id = ventaDetalle.getProducto().getId();
                if(id!=null)
                {
                    Producto producto= repoProducto.findById(id).get();
                    producto.setStock(producto.getStock()-cantidad);
                    repoProducto.save(producto);
                    return true;
                }
            }
            return false;
        }
        catch(Exception e)
        {
            throw e;
        }
    }
}
