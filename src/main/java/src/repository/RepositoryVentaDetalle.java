package src.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import src.models.VentaDetalle;

@Repository
public interface RepositoryVentaDetalle extends JpaRepository<VentaDetalle,Long>
{

}
