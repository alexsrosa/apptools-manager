package br.com.tools.manager.web.rest;

import com.codahale.metrics.annotation.Timed;
import br.com.tools.manager.domain.Consultor;

import br.com.tools.manager.repository.ConsultorRepository;
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
 * REST controller for managing Consultor.
 */
@RestController
@RequestMapping("/api")
public class ConsultorResource {

    private final Logger log = LoggerFactory.getLogger(ConsultorResource.class);

    private static final String ENTITY_NAME = "consultor";

    private final ConsultorRepository consultorRepository;

    public ConsultorResource(ConsultorRepository consultorRepository) {
        this.consultorRepository = consultorRepository;
    }

    /**
     * POST  /consultors : Create a new consultor.
     *
     * @param consultor the consultor to create
     * @return the ResponseEntity with status 201 (Created) and with body the new consultor, or with status 400 (Bad Request) if the consultor has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/consultors")
    @Timed
    public ResponseEntity<Consultor> createConsultor(@Valid @RequestBody Consultor consultor) throws URISyntaxException {
        log.debug("REST request to save Consultor : {}", consultor);
        if (consultor.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new consultor cannot already have an ID")).body(null);
        }
        Consultor result = consultorRepository.save(consultor);
        return ResponseEntity.created(new URI("/api/consultors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /consultors : Updates an existing consultor.
     *
     * @param consultor the consultor to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated consultor,
     * or with status 400 (Bad Request) if the consultor is not valid,
     * or with status 500 (Internal Server Error) if the consultor couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/consultors")
    @Timed
    public ResponseEntity<Consultor> updateConsultor(@Valid @RequestBody Consultor consultor) throws URISyntaxException {
        log.debug("REST request to update Consultor : {}", consultor);
        if (consultor.getId() == null) {
            return createConsultor(consultor);
        }
        Consultor result = consultorRepository.save(consultor);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, consultor.getId().toString()))
            .body(result);
    }

    /**
     * GET  /consultors : get all the consultors.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of consultors in body
     */
    @GetMapping("/consultors")
    @Timed
    public ResponseEntity<List<Consultor>> getAllConsultors(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Consultors");
        Page<Consultor> page = consultorRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/consultors");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /consultors/:id : get the "id" consultor.
     *
     * @param id the id of the consultor to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the consultor, or with status 404 (Not Found)
     */
    @GetMapping("/consultors/{id}")
    @Timed
    public ResponseEntity<Consultor> getConsultor(@PathVariable Long id) {
        log.debug("REST request to get Consultor : {}", id);
        Consultor consultor = consultorRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(consultor));
    }

    /**
     * DELETE  /consultors/:id : delete the "id" consultor.
     *
     * @param id the id of the consultor to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/consultors/{id}")
    @Timed
    public ResponseEntity<Void> deleteConsultor(@PathVariable Long id) {
        log.debug("REST request to delete Consultor : {}", id);
        consultorRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
