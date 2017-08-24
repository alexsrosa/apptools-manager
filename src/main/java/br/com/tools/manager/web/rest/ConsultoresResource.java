package br.com.tools.manager.web.rest;

import com.codahale.metrics.annotation.Timed;
import br.com.tools.manager.domain.Consultores;

import br.com.tools.manager.repository.ConsultoresRepository;
import br.com.tools.manager.web.rest.util.HeaderUtil;
import br.com.tools.manager.web.rest.util.PaginationUtil;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Consultores.
 */
@RestController
@RequestMapping("/api")
public class ConsultoresResource {

    private final Logger log = LoggerFactory.getLogger(ConsultoresResource.class);

    private static final String ENTITY_NAME = "consultores";

    private final ConsultoresRepository consultoresRepository;

    public ConsultoresResource(ConsultoresRepository consultoresRepository) {
        this.consultoresRepository = consultoresRepository;
    }

    /**
     * POST  /consultores : Create a new consultores.
     *
     * @param consultores the consultores to create
     * @return the ResponseEntity with status 201 (Created) and with body the new consultores, or with status 400 (Bad Request) if the consultores has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/consultores")
    @Timed
    public ResponseEntity<Consultores> createConsultores(@Valid @RequestBody Consultores consultores) throws URISyntaxException {
        log.debug("REST request to save Consultores : {}", consultores);
        if (consultores.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new consultores cannot already have an ID")).body(null);
        }
        Consultores result = consultoresRepository.save(consultores);
        return ResponseEntity.created(new URI("/api/consultores/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /consultores : Updates an existing consultores.
     *
     * @param consultores the consultores to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated consultores,
     * or with status 400 (Bad Request) if the consultores is not valid,
     * or with status 500 (Internal Server Error) if the consultores couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/consultores")
    @Timed
    public ResponseEntity<Consultores> updateConsultores(@Valid @RequestBody Consultores consultores) throws URISyntaxException {
        log.debug("REST request to update Consultores : {}", consultores);
        if (consultores.getId() == null) {
            return createConsultores(consultores);
        }
        Consultores result = consultoresRepository.save(consultores);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, consultores.getId().toString()))
            .body(result);
    }

    /**
     * GET  /consultores : get all the consultores.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of consultores in body
     */
    @GetMapping("/consultores")
    @Timed
    public ResponseEntity<List<Consultores>> getAllConsultores(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Consultores");
        Page<Consultores> page = consultoresRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/consultores");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /consultores/:id : get the "id" consultores.
     *
     * @param id the id of the consultores to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the consultores, or with status 404 (Not Found)
     */
    @GetMapping("/consultores/{id}")
    @Timed
    public ResponseEntity<Consultores> getConsultores(@PathVariable Long id) {
        log.debug("REST request to get Consultores : {}", id);
        Consultores consultores = consultoresRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(consultores));
    }

    /**
     * DELETE  /consultores/:id : delete the "id" consultores.
     *
     * @param id the id of the consultores to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/consultores/{id}")
    @Timed
    public ResponseEntity<Void> deleteConsultores(@PathVariable Long id) {
        log.debug("REST request to delete Consultores : {}", id);
        consultoresRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
