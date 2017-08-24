package br.com.tools.manager.web.rest;

import br.com.tools.manager.ManagerApp;

import br.com.tools.manager.domain.Consultores;
import br.com.tools.manager.repository.ConsultoresRepository;
import br.com.tools.manager.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ConsultoresResource REST controller.
 *
 * @see ConsultoresResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ManagerApp.class)
public class ConsultoresResourceIntTest {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATAPRIMEIROREGISTRO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATAPRIMEIROREGISTRO = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATAULTIMOREGISTRO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATAULTIMOREGISTRO = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private ConsultoresRepository consultoresRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restConsultoresMockMvc;

    private Consultores consultores;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ConsultoresResource consultoresResource = new ConsultoresResource(consultoresRepository);
        this.restConsultoresMockMvc = MockMvcBuilders.standaloneSetup(consultoresResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Consultores createEntity(EntityManager em) {
        Consultores consultores = new Consultores()
            .nome(DEFAULT_NOME)
            .dataprimeiroregistro(DEFAULT_DATAPRIMEIROREGISTRO)
            .dataultimoregistro(DEFAULT_DATAULTIMOREGISTRO);
        return consultores;
    }

    @Before
    public void initTest() {
        consultores = createEntity(em);
    }

    @Test
    @Transactional
    public void createConsultores() throws Exception {
        int databaseSizeBeforeCreate = consultoresRepository.findAll().size();

        // Create the Consultores
        restConsultoresMockMvc.perform(post("/api/consultores")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(consultores)))
            .andExpect(status().isCreated());

        // Validate the Consultores in the database
        List<Consultores> consultoresList = consultoresRepository.findAll();
        assertThat(consultoresList).hasSize(databaseSizeBeforeCreate + 1);
        Consultores testConsultores = consultoresList.get(consultoresList.size() - 1);
        assertThat(testConsultores.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testConsultores.getDataprimeiroregistro()).isEqualTo(DEFAULT_DATAPRIMEIROREGISTRO);
        assertThat(testConsultores.getDataultimoregistro()).isEqualTo(DEFAULT_DATAULTIMOREGISTRO);
    }

    @Test
    @Transactional
    public void createConsultoresWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = consultoresRepository.findAll().size();

        // Create the Consultores with an existing ID
        consultores.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restConsultoresMockMvc.perform(post("/api/consultores")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(consultores)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Consultores> consultoresList = consultoresRepository.findAll();
        assertThat(consultoresList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = consultoresRepository.findAll().size();
        // set the field null
        consultores.setNome(null);

        // Create the Consultores, which fails.

        restConsultoresMockMvc.perform(post("/api/consultores")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(consultores)))
            .andExpect(status().isBadRequest());

        List<Consultores> consultoresList = consultoresRepository.findAll();
        assertThat(consultoresList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDataprimeiroregistroIsRequired() throws Exception {
        int databaseSizeBeforeTest = consultoresRepository.findAll().size();
        // set the field null
        consultores.setDataprimeiroregistro(null);

        // Create the Consultores, which fails.

        restConsultoresMockMvc.perform(post("/api/consultores")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(consultores)))
            .andExpect(status().isBadRequest());

        List<Consultores> consultoresList = consultoresRepository.findAll();
        assertThat(consultoresList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDataultimoregistroIsRequired() throws Exception {
        int databaseSizeBeforeTest = consultoresRepository.findAll().size();
        // set the field null
        consultores.setDataultimoregistro(null);

        // Create the Consultores, which fails.

        restConsultoresMockMvc.perform(post("/api/consultores")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(consultores)))
            .andExpect(status().isBadRequest());

        List<Consultores> consultoresList = consultoresRepository.findAll();
        assertThat(consultoresList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllConsultores() throws Exception {
        // Initialize the database
        consultoresRepository.saveAndFlush(consultores);

        // Get all the consultoresList
        restConsultoresMockMvc.perform(get("/api/consultores?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(consultores.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())))
            .andExpect(jsonPath("$.[*].dataprimeiroregistro").value(hasItem(DEFAULT_DATAPRIMEIROREGISTRO.toString())))
            .andExpect(jsonPath("$.[*].dataultimoregistro").value(hasItem(DEFAULT_DATAULTIMOREGISTRO.toString())));
    }

    @Test
    @Transactional
    public void getConsultores() throws Exception {
        // Initialize the database
        consultoresRepository.saveAndFlush(consultores);

        // Get the consultores
        restConsultoresMockMvc.perform(get("/api/consultores/{id}", consultores.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(consultores.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME.toString()))
            .andExpect(jsonPath("$.dataprimeiroregistro").value(DEFAULT_DATAPRIMEIROREGISTRO.toString()))
            .andExpect(jsonPath("$.dataultimoregistro").value(DEFAULT_DATAULTIMOREGISTRO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingConsultores() throws Exception {
        // Get the consultores
        restConsultoresMockMvc.perform(get("/api/consultores/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateConsultores() throws Exception {
        // Initialize the database
        consultoresRepository.saveAndFlush(consultores);
        int databaseSizeBeforeUpdate = consultoresRepository.findAll().size();

        // Update the consultores
        Consultores updatedConsultores = consultoresRepository.findOne(consultores.getId());
        updatedConsultores
            .nome(UPDATED_NOME)
            .dataprimeiroregistro(UPDATED_DATAPRIMEIROREGISTRO)
            .dataultimoregistro(UPDATED_DATAULTIMOREGISTRO);

        restConsultoresMockMvc.perform(put("/api/consultores")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedConsultores)))
            .andExpect(status().isOk());

        // Validate the Consultores in the database
        List<Consultores> consultoresList = consultoresRepository.findAll();
        assertThat(consultoresList).hasSize(databaseSizeBeforeUpdate);
        Consultores testConsultores = consultoresList.get(consultoresList.size() - 1);
        assertThat(testConsultores.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testConsultores.getDataprimeiroregistro()).isEqualTo(UPDATED_DATAPRIMEIROREGISTRO);
        assertThat(testConsultores.getDataultimoregistro()).isEqualTo(UPDATED_DATAULTIMOREGISTRO);
    }

    @Test
    @Transactional
    public void updateNonExistingConsultores() throws Exception {
        int databaseSizeBeforeUpdate = consultoresRepository.findAll().size();

        // Create the Consultores

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restConsultoresMockMvc.perform(put("/api/consultores")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(consultores)))
            .andExpect(status().isCreated());

        // Validate the Consultores in the database
        List<Consultores> consultoresList = consultoresRepository.findAll();
        assertThat(consultoresList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteConsultores() throws Exception {
        // Initialize the database
        consultoresRepository.saveAndFlush(consultores);
        int databaseSizeBeforeDelete = consultoresRepository.findAll().size();

        // Get the consultores
        restConsultoresMockMvc.perform(delete("/api/consultores/{id}", consultores.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Consultores> consultoresList = consultoresRepository.findAll();
        assertThat(consultoresList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Consultores.class);
        Consultores consultores1 = new Consultores();
        consultores1.setId(1L);
        Consultores consultores2 = new Consultores();
        consultores2.setId(consultores1.getId());
        assertThat(consultores1).isEqualTo(consultores2);
        consultores2.setId(2L);
        assertThat(consultores1).isNotEqualTo(consultores2);
        consultores1.setId(null);
        assertThat(consultores1).isNotEqualTo(consultores2);
    }
}
