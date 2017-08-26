package br.com.tools.manager.web.rest;

import br.com.tools.manager.ManagerApp;

import br.com.tools.manager.domain.Consultor;
import br.com.tools.manager.repository.ConsultorRepository;
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

import br.com.tools.manager.domain.enumeration.ConsultorStatus;
/**
 * Test class for the ConsultorResource REST controller.
 *
 * @see ConsultorResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ManagerApp.class)
public class ConsultorResourceIntTest {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATAPRIMEIROREGISTRO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATAPRIMEIROREGISTRO = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATAULTIMOREGISTRO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATAULTIMOREGISTRO = LocalDate.now(ZoneId.systemDefault());

    private static final ConsultorStatus DEFAULT_FLATIVO = ConsultorStatus.ATIVADO;
    private static final ConsultorStatus UPDATED_FLATIVO = ConsultorStatus.DESATIVADO;

    @Autowired
    private ConsultorRepository consultorRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restConsultorMockMvc;

    private Consultor consultor;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ConsultorResource consultorResource = new ConsultorResource(consultorRepository);
        this.restConsultorMockMvc = MockMvcBuilders.standaloneSetup(consultorResource)
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
    public static Consultor createEntity(EntityManager em) {
        Consultor consultor = new Consultor()
            .nome(DEFAULT_NOME)
            .dataprimeiroregistro(DEFAULT_DATAPRIMEIROREGISTRO)
            .dataultimoregistro(DEFAULT_DATAULTIMOREGISTRO)
            .flativo(DEFAULT_FLATIVO);
        return consultor;
    }

    @Before
    public void initTest() {
        consultor = createEntity(em);
    }

    @Test
    @Transactional
    public void createConsultor() throws Exception {
        int databaseSizeBeforeCreate = consultorRepository.findAll().size();

        // Create the Consultor
        restConsultorMockMvc.perform(post("/api/consultors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(consultor)))
            .andExpect(status().isCreated());

        // Validate the Consultor in the database
        List<Consultor> consultorList = consultorRepository.findAll();
        assertThat(consultorList).hasSize(databaseSizeBeforeCreate + 1);
        Consultor testConsultor = consultorList.get(consultorList.size() - 1);
        assertThat(testConsultor.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testConsultor.getDataprimeiroregistro()).isEqualTo(DEFAULT_DATAPRIMEIROREGISTRO);
        assertThat(testConsultor.getDataultimoregistro()).isEqualTo(DEFAULT_DATAULTIMOREGISTRO);
        assertThat(testConsultor.getFlativo()).isEqualTo(DEFAULT_FLATIVO);
    }

    @Test
    @Transactional
    public void createConsultorWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = consultorRepository.findAll().size();

        // Create the Consultor with an existing ID
        consultor.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restConsultorMockMvc.perform(post("/api/consultors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(consultor)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Consultor> consultorList = consultorRepository.findAll();
        assertThat(consultorList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = consultorRepository.findAll().size();
        // set the field null
        consultor.setNome(null);

        // Create the Consultor, which fails.

        restConsultorMockMvc.perform(post("/api/consultors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(consultor)))
            .andExpect(status().isBadRequest());

        List<Consultor> consultorList = consultorRepository.findAll();
        assertThat(consultorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDataprimeiroregistroIsRequired() throws Exception {
        int databaseSizeBeforeTest = consultorRepository.findAll().size();
        // set the field null
        consultor.setDataprimeiroregistro(null);

        // Create the Consultor, which fails.

        restConsultorMockMvc.perform(post("/api/consultors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(consultor)))
            .andExpect(status().isBadRequest());

        List<Consultor> consultorList = consultorRepository.findAll();
        assertThat(consultorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDataultimoregistroIsRequired() throws Exception {
        int databaseSizeBeforeTest = consultorRepository.findAll().size();
        // set the field null
        consultor.setDataultimoregistro(null);

        // Create the Consultor, which fails.

        restConsultorMockMvc.perform(post("/api/consultors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(consultor)))
            .andExpect(status().isBadRequest());

        List<Consultor> consultorList = consultorRepository.findAll();
        assertThat(consultorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFlativoIsRequired() throws Exception {
        int databaseSizeBeforeTest = consultorRepository.findAll().size();
        // set the field null
        consultor.setFlativo(null);

        // Create the Consultor, which fails.

        restConsultorMockMvc.perform(post("/api/consultors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(consultor)))
            .andExpect(status().isBadRequest());

        List<Consultor> consultorList = consultorRepository.findAll();
        assertThat(consultorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllConsultors() throws Exception {
        // Initialize the database
        consultorRepository.saveAndFlush(consultor);

        // Get all the consultorList
        restConsultorMockMvc.perform(get("/api/consultors?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(consultor.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())))
            .andExpect(jsonPath("$.[*].dataprimeiroregistro").value(hasItem(DEFAULT_DATAPRIMEIROREGISTRO.toString())))
            .andExpect(jsonPath("$.[*].dataultimoregistro").value(hasItem(DEFAULT_DATAULTIMOREGISTRO.toString())))
            .andExpect(jsonPath("$.[*].flativo").value(hasItem(DEFAULT_FLATIVO.toString())));
    }

    @Test
    @Transactional
    public void getConsultor() throws Exception {
        // Initialize the database
        consultorRepository.saveAndFlush(consultor);

        // Get the consultor
        restConsultorMockMvc.perform(get("/api/consultors/{id}", consultor.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(consultor.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME.toString()))
            .andExpect(jsonPath("$.dataprimeiroregistro").value(DEFAULT_DATAPRIMEIROREGISTRO.toString()))
            .andExpect(jsonPath("$.dataultimoregistro").value(DEFAULT_DATAULTIMOREGISTRO.toString()))
            .andExpect(jsonPath("$.flativo").value(DEFAULT_FLATIVO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingConsultor() throws Exception {
        // Get the consultor
        restConsultorMockMvc.perform(get("/api/consultors/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateConsultor() throws Exception {
        // Initialize the database
        consultorRepository.saveAndFlush(consultor);
        int databaseSizeBeforeUpdate = consultorRepository.findAll().size();

        // Update the consultor
        Consultor updatedConsultor = consultorRepository.findOne(consultor.getId());
        updatedConsultor
            .nome(UPDATED_NOME)
            .dataprimeiroregistro(UPDATED_DATAPRIMEIROREGISTRO)
            .dataultimoregistro(UPDATED_DATAULTIMOREGISTRO)
            .flativo(UPDATED_FLATIVO);

        restConsultorMockMvc.perform(put("/api/consultors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedConsultor)))
            .andExpect(status().isOk());

        // Validate the Consultor in the database
        List<Consultor> consultorList = consultorRepository.findAll();
        assertThat(consultorList).hasSize(databaseSizeBeforeUpdate);
        Consultor testConsultor = consultorList.get(consultorList.size() - 1);
        assertThat(testConsultor.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testConsultor.getDataprimeiroregistro()).isEqualTo(UPDATED_DATAPRIMEIROREGISTRO);
        assertThat(testConsultor.getDataultimoregistro()).isEqualTo(UPDATED_DATAULTIMOREGISTRO);
        assertThat(testConsultor.getFlativo()).isEqualTo(UPDATED_FLATIVO);
    }

    @Test
    @Transactional
    public void updateNonExistingConsultor() throws Exception {
        int databaseSizeBeforeUpdate = consultorRepository.findAll().size();

        // Create the Consultor

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restConsultorMockMvc.perform(put("/api/consultors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(consultor)))
            .andExpect(status().isCreated());

        // Validate the Consultor in the database
        List<Consultor> consultorList = consultorRepository.findAll();
        assertThat(consultorList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteConsultor() throws Exception {
        // Initialize the database
        consultorRepository.saveAndFlush(consultor);
        int databaseSizeBeforeDelete = consultorRepository.findAll().size();

        // Get the consultor
        restConsultorMockMvc.perform(delete("/api/consultors/{id}", consultor.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Consultor> consultorList = consultorRepository.findAll();
        assertThat(consultorList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Consultor.class);
        Consultor consultor1 = new Consultor();
        consultor1.setId(1L);
        Consultor consultor2 = new Consultor();
        consultor2.setId(consultor1.getId());
        assertThat(consultor1).isEqualTo(consultor2);
        consultor2.setId(2L);
        assertThat(consultor1).isNotEqualTo(consultor2);
        consultor1.setId(null);
        assertThat(consultor1).isNotEqualTo(consultor2);
    }
}
