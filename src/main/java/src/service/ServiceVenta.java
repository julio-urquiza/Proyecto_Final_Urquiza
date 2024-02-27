package src.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import src.models.Venta;
import src.models.VentaDetalle;
import src.models.WorldClock;
import src.repository.*;

@Service
public class ServiceVenta 
{
    @Autowired
    private RepositoryVenta repoVenta;
    @Autowired
    private ServiceVentaDetalle serviceVentaDetalle;
    @Autowired
    private ServiceCliente serviceCliente;
    @Autowired
    private TimeRestApi timeRestApi;

    /**
     * Trae un lista con todos los objetos del tipo Venta presente es la base de datos 
     * @return Lista (List<Venta>) con objetos del tipo Venta o null en caso que ocurra una excepcion   
     */
    public List<Venta> get()
    {
        try 
        {
            return repoVenta.findAll();
        } 
        catch (Exception e) 
        {
            return null;
        }
    }
    /**
     * Trae un objeto del tipo Venta presenta en la base de datos que coincida en el id
     * @param id Id (Long) del objeto del tipo Venta a buscar
     * @return Objeto (tipo Venta) correspoendiente al id o null en caso de se produzca una excepcion
     */
    public Object getById(Long id)
    {
        try 
        {
            if(id!=null)
            {
                return repoVenta.findById(id).get();
            }
            return "El id no deve ser null";
        } 
        catch (Exception e) 
        {
            return "No se pudo obtener la venta";
        }
    }
    /**
     * Inserta objeto del tipo Venta en la base de datos
     * @param venta Objeto (tipo Venta) a ser ingresado
     * @return Mensaje(String) que contiene el estado de la operacion
     */
    public String post(Venta venta)
    {
        try 
        {
            if(venta!=null && serviceCliente.ifExist(venta.getCliente().getId()))
            {
                venta.setVentaDetalles(crearVentaDetalle(venta));
                venta.setPrecioTotal(calcularPrecioTotal(venta));
                venta.setFecha(calcularFecha());
                repoVenta.save(venta);
                return "Venta guardada con exito";
            }
            return "La venta no es valida";
        } 
        catch (Exception e) 
        {
            return "La venta no se pudo guardar";
        }
    }
    /**
     * Modifica un objeto del tipo Venta presente en la base de datos
     * @param venta Objeto (tipo Venta) que contiene las modificaciones
     * @param id Id(Long) del obejto del tipo Venta a modificar
     * @return Mensaje (String) que contiene el estado de la operacion
     */
    public String put(Venta venta,Long id)
    {
        try 
        {
            if(id!=null && ifExist(id))
            {
                Venta ventaModificado= repoVenta.findById(id).get();
                ventaModificado.setCliente(venta.getCliente());
                ventaModificado.setFecha(venta.getFecha());
                ventaModificado.setVentaDetalles(venta.getVentaDetalles());
                repoVenta.save(ventaModificado);
                return "Venta modificado con exito";
            }
            return "No se pudo realizar la modificacion";
        } 
        catch (Exception e) 
        {
            return "No se pudo modificar la venta";
        }
    }
    /**
     * Elimina un objeto del tipo Venta presente en la base de datos
     * @param id Id (Long) del objeto del tipo Venta a eliminar 
     * @return Mensaje (String) que contiene el estado de la operacion
     */
    public String delete(Long id)
    {
        try
        {
            if(id!=null && ifExist(id))
            {
                repoVenta.deleteById(id);
                return "Venta eliminada con exito";
            }
            return "El id ingresado no es valido";
        } 
        catch (Exception e) 
        {
            return "No se pudo eliminar la venta";
        }
    }
    /**
     * Comprueba si existe un id en la base de datos
     * @param id Id (Long) a comprobar
     * @return True si el Id esta dentro de la base de datos o false en caso contrario 
     */
    private Boolean ifExist(Long id)
    {
        if(id!=null)
        {
            return repoVenta.existsById(id);
        }
        return false;
    }
    /**
     * cCalcuala el precio de un objeto del tipo Venta
     * @param venta Objeto (tipo Venta) del cual queremos calcular el precio
     * @return Resultado (Float) de la operacion
     */
    private Float calcularPrecioTotal(Venta venta)
    {
        try 
        {
            Float total = 0f;
            Long id;
            for(int i=0;i<venta.getVentaDetalles().size();i++)
            {
                id=venta.getVentaDetalles().get(i).getId();
                if(id!=null)
                {
                    VentaDetalle ventaDetalle =(VentaDetalle)serviceVentaDetalle.getById(id);
                    total+=ventaDetalle.getPrecioCantidad();
                }
            }
            return total;            
        } 
        catch (Exception e) 
        {
            throw e;
        }
    }
    /**
     * Calcuala la fecha actual
     * @return Objeto (tipo LocalDate) referente al la fecha actual
     */
    public LocalDate calcularFecha()
    {
        try
        {
            WorldClock time = timeRestApi.getTime();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate fecha= LocalDate.parse(time.getCurrentDateTime(),formatter);
            return fecha;
        }
        catch(Exception e)
        {
            return LocalDate.now();
        }
    }
    /**
     * Crea una lista de objetos del tipo VentaDetalle con los datos de la venta y los ingresa en la base de datos 
     * @param venta Objeto (tipo Venta) que contiene los datos necesarios para crear la lista
     * @return Lista (List<VentaDetalle>) que contiene todos los objetos del tipo VentaDetalle ingresados en la base de datos
     */
    private List<VentaDetalle> crearVentaDetalle(Venta venta)
    {
        try 
        {
            List<VentaDetalle> listaRetorno=new ArrayList<VentaDetalle>();
            for(int i=0;i<venta.getVentaDetalles().size();i++)
            {
                VentaDetalle ventaDetalle=venta.getVentaDetalles().get(i);
                if(ventaDetalle!=null)
                {
                    listaRetorno.add((VentaDetalle)serviceVentaDetalle.post(ventaDetalle));
                }
            }
            return listaRetorno;    
        } 
        catch (Exception e) 
        {
            throw e;
        }
    }
}
