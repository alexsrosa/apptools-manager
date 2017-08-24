package br.com.tools.manager.repository;

import br.com.tools.manager.domain.Consultores;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Consultores entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ConsultoresRepository extends JpaRepository<Consultores,Long> {
    
}
