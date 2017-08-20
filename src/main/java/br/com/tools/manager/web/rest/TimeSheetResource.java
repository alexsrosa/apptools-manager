package br.com.tools.manager.web.rest;

import br.com.tools.manager.domain.TimeSheet;
import br.com.tools.manager.service.TimeSheetService;
import br.com.tools.manager.web.rest.util.HeaderUtil;
import br.com.tools.manager.web.rest.util.PaginationUtil;
import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.web.util.ResponseUtil;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.Map;
import java.util.Optional;

/**
 * REST controller for managing TimeSheet.
 */
@RestController
@RequestMapping("/api")
public class TimeSheetResource {

    private final Logger log = LoggerFactory.getLogger(TimeSheetResource.class);

    private static final String ENTITY_NAME = "timeSheet";

    @Autowired
    private TimeSheetService service;

    /**
     * PUT  /time-sheets : Updates an existing timeSheet.
     *
     * @param timeSheet the timeSheet to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated timeSheet,
     * or with status 400 (Bad Request) if the timeSheet is not valid,
     * or with status 500 (Internal Server Error) if the timeSheet couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/time-sheets")
    @Timed
    public ResponseEntity<TimeSheet> updateTimeSheet(@Valid @RequestBody TimeSheet timeSheet) throws URISyntaxException {
        log.debug("REST request to update TimeSheet : {}", timeSheet);

        TimeSheet result = service.save(timeSheet);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, timeSheet.getId().toString()))
            .body(result);
    }

    /**
     * GET  /time-sheets : get all the timeSheets.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of timeSheets in body
     */
    @GetMapping("/time-sheets")
    @Timed
    public ResponseEntity<List<TimeSheet>> getAllTimeSheets(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of TimeSheets");
        Page<TimeSheet> page = service.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/time-sheets");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /time-sheets/:id : get the "id" timeSheet.
     *
     * @param id the id of the timeSheet to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the timeSheet, or with status 404 (Not Found)
     */
    @GetMapping("/time-sheets/{id}")
    @Timed
    public ResponseEntity<TimeSheet> getTimeSheet(@PathVariable Long id) {
        log.debug("REST request to get TimeSheet : {}", id);
        TimeSheet timeSheet = service.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(timeSheet));
    }

    /**
     * GET  /time-sheets/search : get select the timeSheets.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of timeSheets in body
     */
    @GetMapping("/time-sheets/search")
    @Timed
    public ResponseEntity<List<TimeSheet>> search(@RequestParam Map<String,String> allRequestParams, @ApiParam Pageable pageable) {
        log.debug("REST request to get a page of TimeSheets search {}", allRequestParams.toString());

        Page<TimeSheet> page = service.findAll(allRequestParams, pageable);

        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/time-sheets/search");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
