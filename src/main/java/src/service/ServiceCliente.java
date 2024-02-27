package src.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import src.models.Cliente;
import src.repository.*;

@Service
public class ServiceCliente 
{
    @Autowired
    private RepositoryCliente repoCliente;

    /**
     * Trae todos los objetos del tipo cliente presentes en la base de datos 
     * @return Lista (List<Cliente>) que contiene objetos del tipo Cliente o null en caso que se produzca una excepcion
     */
    public List<Cliente> get()
    {
        try
        {
            return repoCliente.findAll();
        }
        catch(Exception e)
        {
            return null;
        }
    }
    /**
     * Trae un objeto de tipo Cliente presente en la base de datos, correspondiente al id pasado 
     * @param id Id (Long) que corresponda al objeto del tipo Cliente 
     * @return Objeto (tipo Cliente) correspondiente al id o mensaje (String) que contiene un mensaje de error
     */
    public Object getById(Long id)
    {
        try
        {
            if(id!=null)
            {
                return repoCliente.findById(id).get();
            }
            return "El id no es valido";
        }
        catch(Exception e)
        {
            return "El id no es valido";
        }
    }
    /**
     * Inserta objetos del tipo Cliente en la base de datos a travez de una lista del tipo List<Cliente>
     * @param clientes Lista (List<Cliente>) que contiene los objetos del tipo Cliente
     * @return Mensaje (String) que contiene el estado de la operacion
     */
    public String postClientes(List<Cliente> clientes)
    {
        try 
        {
            for(int i=0;i<clientes.size();i++)
            {
                this.post(clientes.get(i));
            }
            return "Clientes guadado con exito";
        } 
        catch (Exception e) 
        {
            return "No se pudo guardar los clientes";
        }
    }
    /**
     * Inserta un objeto del tipo Cliente en la base de datos 
     * @param cliente Objeto (tipo Cliente) a ser ingresado
     * @return Mensaje (String) que contiene el estado de la operacion
     */
    public String post(Cliente cliente)
    {
        try
        {
            if(cliente!=null)
            {
                repoCliente.save(cliente);
                return "Cliente guardado con exito";
            }
            return "No se pudo guardar el cliente";
        }
        catch(Exception e)
        {
            return "No se pudo guardar el cliente";
        }
    }
    /**
     * Modifica un objeto del tipo Cliente presente en la base de datos 
     * @param cliente Objeto (tipo Cliente) editado 
     * @param id Id (Long) correspondiente al objeto del tipo Cliente a ser modificado
     * @return Mensaje(String) que contiene el estado de la operacion
     */
    public String put(Cliente cliente,Long id)
    {
        try
        {
            if(id!=null && ifExist(id))
            {
                Cliente clienteModificado= repoCliente.findById(id).get();
                clienteModificado.setNombre(cliente.getNombre());
                clienteModificado.setCorreo(cliente.getCorreo());
                repoCliente.save(clienteModificado);
                return "Cliente modificado con exito";
            }
            return "No se pudo moificar";
        }
        catch(Exception e)
        {
            return "No se pudo moificar el cliente";
        }
    }
    /**
     * Elimina un objeto del tipo Cliente presente en la base de datos
     * @param id Id (Long) del objeto del tipo Cliente a ser eliminado
     * @return Mensaje(String) que contiene estado de la operacion
     */
    public String delete(Long id)
    {
        try
        {
            if(id!=null && ifExist(id))
            {
                repoCliente.deleteById(id);
                return "Cliente eliminado con exito";
            }
            return "El id no es valido";
        }
        catch(Exception e)
        {
            return "El Cliente no se pudo eliminar";
        }
    }
    /**
     * Comprueba si un id esta dentro de la base de datos
     * @param id Id (Long) a comprobar 
     * @return True si la id esta dentro de la base de datos o false en caso contrario
     */
    public Boolean ifExist(Long id)
    {
        if(id!=null)
        {
            return repoCliente.existsById(id);
        }
        return false;
    }
}
