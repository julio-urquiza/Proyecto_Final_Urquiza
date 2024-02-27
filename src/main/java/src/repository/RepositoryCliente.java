package src.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import src.models.Cliente;

@Repository
public interface RepositoryCliente extends JpaRepository<Cliente,Long>
{

}
