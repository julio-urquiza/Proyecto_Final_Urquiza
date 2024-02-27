package src.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import src.models.Producto;

@Repository
public interface RepositoryProducto extends JpaRepository<Producto,Long>
{

}
