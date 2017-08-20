package br.com.tools.manager.service;

import br.com.tools.manager.domain.TimeSheet;
import br.com.tools.manager.repository.TimeSheetRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class TimeSheetService {

    private final Logger log = LoggerFactory.getLogger(TimeSheetService.class);

    @Autowired
    private TimeSheetRepository repository;

    /**
     *
     * @return
     */
    public List<TimeSheet> search(){

        return null;
    }

    /**
     *
     * @param timeSheet
     * @return
     */
    public TimeSheet save(TimeSheet timeSheet) {
        return repository.save(timeSheet);
    }

    /**
     *
     * @param pageable
     * @return
     */
    public Page<TimeSheet> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    /**
     *
     * @param id
     * @return
     */
    public TimeSheet findOne(Long id) {
        return repository.findOne(id);
    }

    /**
     *
     * @param id
     */
    public void delete(Long id) {
        repository.delete(id);
    }

    public Page<TimeSheet> findAll(Map<String, String> allRequestParams, Pageable pageable) {

        String nome = allRequestParams.get("nome");
        String tarefa = allRequestParams.get("tarefa");
        String dataInicio = allRequestParams.get("dataInicio");
        String dataFim = allRequestParams.get("dataFim");

        Specification<TimeSheet> specification = new Specification<TimeSheet>() {
            public Predicate toPredicate(Root<TimeSheet> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                List<Predicate> predicates = new ArrayList<Predicate>();

                if(nome != null) {
                    predicates.add(builder.like(root.get("nome"),
                        "%" + nome.toString().trim().toLowerCase() + "%"));
                }
                if(tarefa != null)
                    predicates.add(builder.like(root.get("tarefa").as(String.class), "%"+tarefa.toString().trim()+"%"));
                if(dataInicio != null && dataFim != null)
                    predicates.add(builder.between(root.get("data"), LocalDate.parse(dataInicio), LocalDate.parse(dataFim)));

                return builder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };

        return repository.findAll(specification, pageable);
    }
}
