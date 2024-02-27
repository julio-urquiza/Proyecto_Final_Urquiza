package src.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import src.models.Producto;
import src.repository.*;

@Service
public class ServiceProducto 
{
    @Autowired
    private RepositoryProducto repoProducto;

    /**
     * Trae una lista con todos los objetos del tipo Producto presentes en la base de datos
     * @return Lista (List<Producto>) con objetos del tipo Producto o null en caso que ocurra una excepcion
     */
    public List<Producto> get()
    {
        try 
        {
            return repoProducto.findAll();
        } 
        catch (Exception e) 
        {
            return null;
        }
    }
    /**
     * Trae un objeto del tipo Producto presente en la base de datos que coincida con la id
     * @param id Id (Long) del objeto del tipo producto a buscar
     * @return Objeto (tipo Producto) que coincide con la id, o null en caso de que ocurra una excepcion o el id sea null 
     */
    public Producto getById(Long id)
    {
        try
        {
            if(id!=null)
            {
                return repoProducto.findById(id).get();
            }
            return null;
        } 
        catch (Exception e) 
        {
            return null;
        }
    }
    /**
     * Inserta objetos del tipo Producto en la base de datos 
     * @param producto Objeto (tipo Producto) a ser ingresado
     * @return Mensaje (String) que contiene el estado de la operacion
     */
    public String post(Producto producto)
    {
        try 
        {
            if(producto!=null)
            {
                repoProducto.save(producto);
                return "Producto guardado con exito";
            }
            return "No se pudo guardar el producto"; 
        }
        catch (Exception e) 
        {
            return "No se pudo guardar el producto"; 
        }
    }
    /**
     * Inserta objetos del tipo Producto en la base de datos desde una lista
     * @param productos Lista (List<Productos) con los objtos a ingresar
     * @return Mensaje (String) que contiene el estado de la operacion
     */
    public String postProductos(List<Producto> productos)
    {
        try 
        {
            for(int i=0;i<productos.size();i++)
            {
                this.post(productos.get(i));
            }
            return "Productos ingresados";
        } 
        catch (Exception e) 
        {
            return "No se puedieron ingresar los Productos";
        }
    }
    /**
     * Modifica un objeto del tipo Producto presente en la base de datos
     * @param producto Objeto (tipo Producto) a ingresar en la base de datos
     * @param id Id (Long) del objeto del tipo producto
     * @return Mensaje(String) que contiene el estado de la operacion
     */
    public String put(Producto producto,Long id)
    {
        try 
        {
            if(id!=null && producto!=null && ifExist(id))
            {
                Producto productoModificado= repoProducto.findById(id).get();
                productoModificado.setDescripcion(producto.getDescripcion());
                productoModificado.setPrecio(producto.getPrecio());
                productoModificado.setStock(producto.getStock());
                repoProducto.save(productoModificado);
                return "Producto modificado con exito";
            }
            return "El producto no se puso modidicar";
        }
        catch (Exception e) 
        {
            return "El producto no se puso modidicar";
        }
    }
    /**
     * Elimina un obejto del tipo Producto presente en la base de datos
     * @param id Id (Long) del objeto del tipo producto a eliminar
     * @return Mensaje(String) que contiene el estado de la operacion
     */
    public String delete(Long id)
    {
        try 
        {
            if(id!=null && ifExist(id))
            {
                repoProducto.deleteById(id);
                return "Producto eliminado con exito";
            }
            return "El id ingresado no es valido";
        } 
        catch (Exception e) 
        {
            return "No se pudo eliminar el producto";
        }
    }
    /**
     * Comprueba si un id esta presente en la base de datos
     * @param id Id (Long) a comprobar 
     * @return True si el id esta en la base de datos o false en caso contrario
     */
    private Boolean ifExist(Long id)
    {
        if(id!=null)
        {
            return repoProducto.existsById(id);
        }
        return false;
    }

}
