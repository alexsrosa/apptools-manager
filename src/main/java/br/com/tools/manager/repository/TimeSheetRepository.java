package br.com.tools.manager.repository;

import br.com.tools.manager.domain.TimeSheet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.time.LocalDate;
import java.util.List;


/**
 * Spring Data JPA repository for the TimeSheet entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TimeSheetRepository extends JpaRepository<TimeSheet,Long>, JpaSpecificationExecutor {

    Page<TimeSheet> findByNome(Pageable pageable, String nome);

    Page<TimeSheet> findByDataBetween(Pageable pageable, LocalDate parse, LocalDate parse1);

    Page<TimeSheet> findByTarefa(Pageable pageable, String tarefa);
}
