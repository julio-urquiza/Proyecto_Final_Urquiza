package src.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import src.models.Venta;

@Repository
public interface RepositoryVenta extends JpaRepository<Venta,Long>
{

}
