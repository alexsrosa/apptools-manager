package br.com.tools.manager.repository;

import br.com.tools.manager.domain.Consultor;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Consultor entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ConsultorRepository extends JpaRepository<Consultor,Long> {
    
}
