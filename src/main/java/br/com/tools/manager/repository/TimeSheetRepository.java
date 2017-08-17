package br.com.tools.manager.repository;

import br.com.tools.manager.domain.TimeSheet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.time.LocalDate;


/**
 * Spring Data JPA repository for the TimeSheet entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TimeSheetRepository extends JpaRepository<TimeSheet,Long> {

    Page<TimeSheet> findByNomeAndTarefaAndDataBetween(Pageable pageable, String nome, String tarefa, LocalDate data1
            , LocalDate data2);
}
