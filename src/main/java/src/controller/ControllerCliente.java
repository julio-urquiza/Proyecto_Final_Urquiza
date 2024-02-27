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

import src.models.Cliente;
import src.service.ServiceCliente;

@RestController
@RequestMapping("/cliente")
public class ControllerCliente 
{
    @Autowired
    private ServiceCliente serviceCliente;

    @GetMapping
    public List<Cliente> getClientes()
    {
        return serviceCliente.get();
    }
    
    @PostMapping
    public String saveCliente(@RequestBody Cliente cliente)
    {
        return serviceCliente.post(cliente);
    }

    @PostMapping("/ingresar_clientes")
    public String saveClientes(@RequestBody List<Cliente>clientes)
    {
        return serviceCliente.postClientes(clientes);
    }

    @GetMapping(path = "/{id}")
    public Object getClienteById(@PathVariable("id") Long id)
    {
        return serviceCliente.getById(id);
    }

    @PutMapping(path = "/{id}")
    public String updateCliente(@PathVariable("id") Long id,@RequestBody Cliente cliente)
    {
        return serviceCliente.put(cliente, id);
    }

    @DeleteMapping(path = "/{id}")
    public String deleteCliente(@PathVariable("id") Long id)
    {
        return serviceCliente.delete(id);
    }

}