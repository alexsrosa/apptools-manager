package br.com.tools.manager.web.rest;

import com.codahale.metrics.annotation.Timed;
import br.com.tools.manager.domain.Parametro;

import br.com.tools.manager.repository.ParametroRepository;
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
 * REST controller for managing Parametro.
 */
@RestController
@RequestMapping("/api")
public class ParametroResource {

    private final Logger log = LoggerFactory.getLogger(ParametroResource.class);

    private static final String ENTITY_NAME = "parametro";

    private final ParametroRepository parametroRepository;

    public ParametroResource(ParametroRepository parametroRepository) {
        this.parametroRepository = parametroRepository;
    }

    /**
     * POST  /parametros : Create a new parametro.
     *
     * @param parametro the parametro to create
     * @return the ResponseEntity with status 201 (Created) and with body the new parametro, or with status 400 (Bad Request) if the parametro has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/parametros")
    @Timed
    public ResponseEntity<Parametro> createParametro(@Valid @RequestBody Parametro parametro) throws URISyntaxException {
        log.debug("REST request to save Parametro : {}", parametro);
        if (parametro.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new parametro cannot already have an ID")).body(null);
        }
        Parametro result = parametroRepository.save(parametro);
        return ResponseEntity.created(new URI("/api/parametros/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /parametros : Updates an existing parametro.
     *
     * @param parametro the parametro to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated parametro,
     * or with status 400 (Bad Request) if the parametro is not valid,
     * or with status 500 (Internal Server Error) if the parametro couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/parametros")
    @Timed
    public ResponseEntity<Parametro> updateParametro(@Valid @RequestBody Parametro parametro) throws URISyntaxException {
        log.debug("REST request to update Parametro : {}", parametro);
        if (parametro.getId() == null) {
            return createParametro(parametro);
        }
        Parametro result = parametroRepository.save(parametro);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, parametro.getId().toString()))
            .body(result);
    }

    /**
     * GET  /parametros : get all the parametros.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of parametros in body
     */
    @GetMapping("/parametros")
    @Timed
    public ResponseEntity<List<Parametro>> getAllParametros(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Parametros");
        Page<Parametro> page = parametroRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/parametros");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /parametros/:id : get the "id" parametro.
     *
     * @param id the id of the parametro to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the parametro, or with status 404 (Not Found)
     */
    @GetMapping("/parametros/{id}")
    @Timed
    public ResponseEntity<Parametro> getParametro(@PathVariable Long id) {
        log.debug("REST request to get Parametro : {}", id);
        Parametro parametro = parametroRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(parametro));
    }

    /**
     * DELETE  /parametros/:id : delete the "id" parametro.
     *
     * @param id the id of the parametro to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/parametros/{id}")
    @Timed
    public ResponseEntity<Void> deleteParametro(@PathVariable Long id) {
        log.debug("REST request to delete Parametro : {}", id);
        parametroRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
