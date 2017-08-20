package br.com.tools.manager.web.rest;

import br.com.tools.manager.ManagerApp;

import br.com.tools.manager.domain.TimeSheet;
import br.com.tools.manager.repository.TimeSheetRepository;
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
 * Test class for the TimeSheetResource REST controller.
 *
 * @see TimeSheetResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ManagerApp.class)
public class TimeSheetResourceIntTest {

    private static final Integer DEFAULT_MATRICULA = 1;
    private static final Integer UPDATED_MATRICULA = 2;

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final Integer DEFAULT_TAREFA = 1;
    private static final Integer UPDATED_TAREFA = 2;

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final Integer DEFAULT_CODIGOFASE = 1;
    private static final Integer UPDATED_CODIGOFASE = 2;

    private static final String DEFAULT_DESCRICAOFASE = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAOFASE = "BBBBBBBBBB";

    private static final Integer DEFAULT_CODIGOATIVIDADE = 1;
    private static final Integer UPDATED_CODIGOATIVIDADE = 2;

    private static final String DEFAULT_DESCRICAOATIVIDADE = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAOATIVIDADE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_OBSERVACAO = "AAAAAAAAAA";
    private static final String UPDATED_OBSERVACAO = "BBBBBBBBBB";

    private static final String DEFAULT_HORAS = "AAAAAAAAAA";
    private static final String UPDATED_HORAS = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATAINCLUSAO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATAINCLUSAO = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATAULTIMAATUALIZACAO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATAULTIMAATUALIZACAO = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private TimeSheetRepository timeSheetRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTimeSheetMockMvc;

    private TimeSheet timeSheet;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TimeSheetResource timeSheetResource = new TimeSheetResource();
        this.restTimeSheetMockMvc = MockMvcBuilders.standaloneSetup(timeSheetResource)
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
    public static TimeSheet createEntity(EntityManager em) {
        TimeSheet timeSheet = new TimeSheet()
            .matricula(DEFAULT_MATRICULA)
            .nome(DEFAULT_NOME)
            .tarefa(DEFAULT_TAREFA)
            .descricao(DEFAULT_DESCRICAO)
            .codigofase(DEFAULT_CODIGOFASE)
            .descricaofase(DEFAULT_DESCRICAOFASE)
            .codigoatividade(DEFAULT_CODIGOATIVIDADE)
            .descricaoatividade(DEFAULT_DESCRICAOATIVIDADE)
            .data(DEFAULT_DATA)
            .observacao(DEFAULT_OBSERVACAO)
            .horas(DEFAULT_HORAS)
            .datainclusao(DEFAULT_DATAINCLUSAO)
            .dataultimaatualizacao(DEFAULT_DATAULTIMAATUALIZACAO);
        return timeSheet;
    }

    @Before
    public void initTest() {
        timeSheet = createEntity(em);
    }

    @Test
    @Transactional
    public void createTimeSheet() throws Exception {
        int databaseSizeBeforeCreate = timeSheetRepository.findAll().size();

        // Create the TimeSheet
        restTimeSheetMockMvc.perform(post("/api/time-sheets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(timeSheet)))
            .andExpect(status().isCreated());

        // Validate the TimeSheet in the database
        List<TimeSheet> timeSheetList = timeSheetRepository.findAll();
        assertThat(timeSheetList).hasSize(databaseSizeBeforeCreate + 1);
        TimeSheet testTimeSheet = timeSheetList.get(timeSheetList.size() - 1);
        assertThat(testTimeSheet.getMatricula()).isEqualTo(DEFAULT_MATRICULA);
        assertThat(testTimeSheet.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testTimeSheet.getTarefa()).isEqualTo(DEFAULT_TAREFA);
        assertThat(testTimeSheet.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testTimeSheet.getCodigofase()).isEqualTo(DEFAULT_CODIGOFASE);
        assertThat(testTimeSheet.getDescricaofase()).isEqualTo(DEFAULT_DESCRICAOFASE);
        assertThat(testTimeSheet.getCodigoatividade()).isEqualTo(DEFAULT_CODIGOATIVIDADE);
        assertThat(testTimeSheet.getDescricaoatividade()).isEqualTo(DEFAULT_DESCRICAOATIVIDADE);
        assertThat(testTimeSheet.getData()).isEqualTo(DEFAULT_DATA);
        assertThat(testTimeSheet.getObservacao()).isEqualTo(DEFAULT_OBSERVACAO);
        assertThat(testTimeSheet.getHoras()).isEqualTo(DEFAULT_HORAS);
        assertThat(testTimeSheet.getDatainclusao()).isEqualTo(DEFAULT_DATAINCLUSAO);
        assertThat(testTimeSheet.getDataultimaatualizacao()).isEqualTo(DEFAULT_DATAULTIMAATUALIZACAO);
    }

    @Test
    @Transactional
    public void createTimeSheetWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = timeSheetRepository.findAll().size();

        // Create the TimeSheet with an existing ID
        timeSheet.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTimeSheetMockMvc.perform(post("/api/time-sheets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(timeSheet)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<TimeSheet> timeSheetList = timeSheetRepository.findAll();
        assertThat(timeSheetList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkMatriculaIsRequired() throws Exception {
        int databaseSizeBeforeTest = timeSheetRepository.findAll().size();
        // set the field null
        timeSheet.setMatricula(null);

        // Create the TimeSheet, which fails.

        restTimeSheetMockMvc.perform(post("/api/time-sheets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(timeSheet)))
            .andExpect(status().isBadRequest());

        List<TimeSheet> timeSheetList = timeSheetRepository.findAll();
        assertThat(timeSheetList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = timeSheetRepository.findAll().size();
        // set the field null
        timeSheet.setNome(null);

        // Create the TimeSheet, which fails.

        restTimeSheetMockMvc.perform(post("/api/time-sheets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(timeSheet)))
            .andExpect(status().isBadRequest());

        List<TimeSheet> timeSheetList = timeSheetRepository.findAll();
        assertThat(timeSheetList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTarefaIsRequired() throws Exception {
        int databaseSizeBeforeTest = timeSheetRepository.findAll().size();
        // set the field null
        timeSheet.setTarefa(null);

        // Create the TimeSheet, which fails.

        restTimeSheetMockMvc.perform(post("/api/time-sheets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(timeSheet)))
            .andExpect(status().isBadRequest());

        List<TimeSheet> timeSheetList = timeSheetRepository.findAll();
        assertThat(timeSheetList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescricaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = timeSheetRepository.findAll().size();
        // set the field null
        timeSheet.setDescricao(null);

        // Create the TimeSheet, which fails.

        restTimeSheetMockMvc.perform(post("/api/time-sheets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(timeSheet)))
            .andExpect(status().isBadRequest());

        List<TimeSheet> timeSheetList = timeSheetRepository.findAll();
        assertThat(timeSheetList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCodigofaseIsRequired() throws Exception {
        int databaseSizeBeforeTest = timeSheetRepository.findAll().size();
        // set the field null
        timeSheet.setCodigofase(null);

        // Create the TimeSheet, which fails.

        restTimeSheetMockMvc.perform(post("/api/time-sheets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(timeSheet)))
            .andExpect(status().isBadRequest());

        List<TimeSheet> timeSheetList = timeSheetRepository.findAll();
        assertThat(timeSheetList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescricaofaseIsRequired() throws Exception {
        int databaseSizeBeforeTest = timeSheetRepository.findAll().size();
        // set the field null
        timeSheet.setDescricaofase(null);

        // Create the TimeSheet, which fails.

        restTimeSheetMockMvc.perform(post("/api/time-sheets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(timeSheet)))
            .andExpect(status().isBadRequest());

        List<TimeSheet> timeSheetList = timeSheetRepository.findAll();
        assertThat(timeSheetList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCodigoatividadeIsRequired() throws Exception {
        int databaseSizeBeforeTest = timeSheetRepository.findAll().size();
        // set the field null
        timeSheet.setCodigoatividade(null);

        // Create the TimeSheet, which fails.

        restTimeSheetMockMvc.perform(post("/api/time-sheets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(timeSheet)))
            .andExpect(status().isBadRequest());

        List<TimeSheet> timeSheetList = timeSheetRepository.findAll();
        assertThat(timeSheetList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescricaoatividadeIsRequired() throws Exception {
        int databaseSizeBeforeTest = timeSheetRepository.findAll().size();
        // set the field null
        timeSheet.setDescricaoatividade(null);

        // Create the TimeSheet, which fails.

        restTimeSheetMockMvc.perform(post("/api/time-sheets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(timeSheet)))
            .andExpect(status().isBadRequest());

        List<TimeSheet> timeSheetList = timeSheetRepository.findAll();
        assertThat(timeSheetList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDataIsRequired() throws Exception {
        int databaseSizeBeforeTest = timeSheetRepository.findAll().size();
        // set the field null
        timeSheet.setData(null);

        // Create the TimeSheet, which fails.

        restTimeSheetMockMvc.perform(post("/api/time-sheets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(timeSheet)))
            .andExpect(status().isBadRequest());

        List<TimeSheet> timeSheetList = timeSheetRepository.findAll();
        assertThat(timeSheetList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkHorasIsRequired() throws Exception {
        int databaseSizeBeforeTest = timeSheetRepository.findAll().size();
        // set the field null
        timeSheet.setHoras(null);

        // Create the TimeSheet, which fails.

        restTimeSheetMockMvc.perform(post("/api/time-sheets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(timeSheet)))
            .andExpect(status().isBadRequest());

        List<TimeSheet> timeSheetList = timeSheetRepository.findAll();
        assertThat(timeSheetList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDatainclusaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = timeSheetRepository.findAll().size();
        // set the field null
        timeSheet.setDatainclusao(null);

        // Create the TimeSheet, which fails.

        restTimeSheetMockMvc.perform(post("/api/time-sheets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(timeSheet)))
            .andExpect(status().isBadRequest());

        List<TimeSheet> timeSheetList = timeSheetRepository.findAll();
        assertThat(timeSheetList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDataultimaatualizacaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = timeSheetRepository.findAll().size();
        // set the field null
        timeSheet.setDataultimaatualizacao(null);

        // Create the TimeSheet, which fails.

        restTimeSheetMockMvc.perform(post("/api/time-sheets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(timeSheet)))
            .andExpect(status().isBadRequest());

        List<TimeSheet> timeSheetList = timeSheetRepository.findAll();
        assertThat(timeSheetList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTimeSheets() throws Exception {
        // Initialize the database
        timeSheetRepository.saveAndFlush(timeSheet);

        // Get all the timeSheetList
        restTimeSheetMockMvc.perform(get("/api/time-sheets?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(timeSheet.getId().intValue())))
            .andExpect(jsonPath("$.[*].matricula").value(hasItem(DEFAULT_MATRICULA)))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())))
            .andExpect(jsonPath("$.[*].tarefa").value(hasItem(DEFAULT_TAREFA)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())))
            .andExpect(jsonPath("$.[*].codigofase").value(hasItem(DEFAULT_CODIGOFASE)))
            .andExpect(jsonPath("$.[*].descricaofase").value(hasItem(DEFAULT_DESCRICAOFASE.toString())))
            .andExpect(jsonPath("$.[*].codigoatividade").value(hasItem(DEFAULT_CODIGOATIVIDADE)))
            .andExpect(jsonPath("$.[*].descricaoatividade").value(hasItem(DEFAULT_DESCRICAOATIVIDADE.toString())))
            .andExpect(jsonPath("$.[*].data").value(hasItem(DEFAULT_DATA.toString())))
            .andExpect(jsonPath("$.[*].observacao").value(hasItem(DEFAULT_OBSERVACAO.toString())))
            .andExpect(jsonPath("$.[*].horas").value(hasItem(DEFAULT_HORAS.toString())))
            .andExpect(jsonPath("$.[*].datainclusao").value(hasItem(DEFAULT_DATAINCLUSAO.toString())))
            .andExpect(jsonPath("$.[*].dataultimaatualizacao").value(hasItem(DEFAULT_DATAULTIMAATUALIZACAO.toString())));
    }

    @Test
    @Transactional
    public void getTimeSheet() throws Exception {
        // Initialize the database
        timeSheetRepository.saveAndFlush(timeSheet);

        // Get the timeSheet
        restTimeSheetMockMvc.perform(get("/api/time-sheets/{id}", timeSheet.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(timeSheet.getId().intValue()))
            .andExpect(jsonPath("$.matricula").value(DEFAULT_MATRICULA))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME.toString()))
            .andExpect(jsonPath("$.tarefa").value(DEFAULT_TAREFA))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO.toString()))
            .andExpect(jsonPath("$.codigofase").value(DEFAULT_CODIGOFASE))
            .andExpect(jsonPath("$.descricaofase").value(DEFAULT_DESCRICAOFASE.toString()))
            .andExpect(jsonPath("$.codigoatividade").value(DEFAULT_CODIGOATIVIDADE))
            .andExpect(jsonPath("$.descricaoatividade").value(DEFAULT_DESCRICAOATIVIDADE.toString()))
            .andExpect(jsonPath("$.data").value(DEFAULT_DATA.toString()))
            .andExpect(jsonPath("$.observacao").value(DEFAULT_OBSERVACAO.toString()))
            .andExpect(jsonPath("$.horas").value(DEFAULT_HORAS.toString()))
            .andExpect(jsonPath("$.datainclusao").value(DEFAULT_DATAINCLUSAO.toString()))
            .andExpect(jsonPath("$.dataultimaatualizacao").value(DEFAULT_DATAULTIMAATUALIZACAO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTimeSheet() throws Exception {
        // Get the timeSheet
        restTimeSheetMockMvc.perform(get("/api/time-sheets/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTimeSheet() throws Exception {
        // Initialize the database
        timeSheetRepository.saveAndFlush(timeSheet);
        int databaseSizeBeforeUpdate = timeSheetRepository.findAll().size();

        // Update the timeSheet
        TimeSheet updatedTimeSheet = timeSheetRepository.findOne(timeSheet.getId());
        updatedTimeSheet
            .matricula(UPDATED_MATRICULA)
            .nome(UPDATED_NOME)
            .tarefa(UPDATED_TAREFA)
            .descricao(UPDATED_DESCRICAO)
            .codigofase(UPDATED_CODIGOFASE)
            .descricaofase(UPDATED_DESCRICAOFASE)
            .codigoatividade(UPDATED_CODIGOATIVIDADE)
            .descricaoatividade(UPDATED_DESCRICAOATIVIDADE)
            .data(UPDATED_DATA)
            .observacao(UPDATED_OBSERVACAO)
            .horas(UPDATED_HORAS)
            .datainclusao(UPDATED_DATAINCLUSAO)
            .dataultimaatualizacao(UPDATED_DATAULTIMAATUALIZACAO);

        restTimeSheetMockMvc.perform(put("/api/time-sheets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTimeSheet)))
            .andExpect(status().isOk());

        // Validate the TimeSheet in the database
        List<TimeSheet> timeSheetList = timeSheetRepository.findAll();
        assertThat(timeSheetList).hasSize(databaseSizeBeforeUpdate);
        TimeSheet testTimeSheet = timeSheetList.get(timeSheetList.size() - 1);
        assertThat(testTimeSheet.getMatricula()).isEqualTo(UPDATED_MATRICULA);
        assertThat(testTimeSheet.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testTimeSheet.getTarefa()).isEqualTo(UPDATED_TAREFA);
        assertThat(testTimeSheet.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testTimeSheet.getCodigofase()).isEqualTo(UPDATED_CODIGOFASE);
        assertThat(testTimeSheet.getDescricaofase()).isEqualTo(UPDATED_DESCRICAOFASE);
        assertThat(testTimeSheet.getCodigoatividade()).isEqualTo(UPDATED_CODIGOATIVIDADE);
        assertThat(testTimeSheet.getDescricaoatividade()).isEqualTo(UPDATED_DESCRICAOATIVIDADE);
        assertThat(testTimeSheet.getData()).isEqualTo(UPDATED_DATA);
        assertThat(testTimeSheet.getObservacao()).isEqualTo(UPDATED_OBSERVACAO);
        assertThat(testTimeSheet.getHoras()).isEqualTo(UPDATED_HORAS);
        assertThat(testTimeSheet.getDatainclusao()).isEqualTo(UPDATED_DATAINCLUSAO);
        assertThat(testTimeSheet.getDataultimaatualizacao()).isEqualTo(UPDATED_DATAULTIMAATUALIZACAO);
    }

    @Test
    @Transactional
    public void updateNonExistingTimeSheet() throws Exception {
        int databaseSizeBeforeUpdate = timeSheetRepository.findAll().size();

        // Create the TimeSheet

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTimeSheetMockMvc.perform(put("/api/time-sheets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(timeSheet)))
            .andExpect(status().isCreated());

        // Validate the TimeSheet in the database
        List<TimeSheet> timeSheetList = timeSheetRepository.findAll();
        assertThat(timeSheetList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTimeSheet() throws Exception {
        // Initialize the database
        timeSheetRepository.saveAndFlush(timeSheet);
        int databaseSizeBeforeDelete = timeSheetRepository.findAll().size();

        // Get the timeSheet
        restTimeSheetMockMvc.perform(delete("/api/time-sheets/{id}", timeSheet.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TimeSheet> timeSheetList = timeSheetRepository.findAll();
        assertThat(timeSheetList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TimeSheet.class);
        TimeSheet timeSheet1 = new TimeSheet();
        timeSheet1.setId(1L);
        TimeSheet timeSheet2 = new TimeSheet();
        timeSheet2.setId(timeSheet1.getId());
        assertThat(timeSheet1).isEqualTo(timeSheet2);
        timeSheet2.setId(2L);
        assertThat(timeSheet1).isNotEqualTo(timeSheet2);
        timeSheet1.setId(null);
        assertThat(timeSheet1).isNotEqualTo(timeSheet2);
    }
}
