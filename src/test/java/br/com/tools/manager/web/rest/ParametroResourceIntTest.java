package br.com.tools.manager.web.rest;

import br.com.tools.manager.ManagerApp;

import br.com.tools.manager.domain.Parametro;
import br.com.tools.manager.repository.ParametroRepository;
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
 * Test class for the ParametroResource REST controller.
 *
 * @see ParametroResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ManagerApp.class)
public class ParametroResourceIntTest {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_VALOR = "AAAAAAAAAA";
    private static final String UPDATED_VALOR = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATAINCLUSAO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATAINCLUSAO = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATAULTIMAATUALIZACAO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATAULTIMAATUALIZACAO = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private ParametroRepository parametroRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restParametroMockMvc;

    private Parametro parametro;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ParametroResource parametroResource = new ParametroResource(parametroRepository);
        this.restParametroMockMvc = MockMvcBuilders.standaloneSetup(parametroResource)
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
    public static Parametro createEntity(EntityManager em) {
        Parametro parametro = new Parametro()
            .nome(DEFAULT_NOME)
            .valor(DEFAULT_VALOR)
            .datainclusao(DEFAULT_DATAINCLUSAO)
            .dataultimaatualizacao(DEFAULT_DATAULTIMAATUALIZACAO);
        return parametro;
    }

    @Before
    public void initTest() {
        parametro = createEntity(em);
    }

    @Test
    @Transactional
    public void createParametro() throws Exception {
        int databaseSizeBeforeCreate = parametroRepository.findAll().size();

        // Create the Parametro
        restParametroMockMvc.perform(post("/api/parametros")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(parametro)))
            .andExpect(status().isCreated());

        // Validate the Parametro in the database
        List<Parametro> parametroList = parametroRepository.findAll();
        assertThat(parametroList).hasSize(databaseSizeBeforeCreate + 1);
        Parametro testParametro = parametroList.get(parametroList.size() - 1);
        assertThat(testParametro.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testParametro.getValor()).isEqualTo(DEFAULT_VALOR);
        assertThat(testParametro.getDatainclusao()).isEqualTo(DEFAULT_DATAINCLUSAO);
        assertThat(testParametro.getDataultimaatualizacao()).isEqualTo(DEFAULT_DATAULTIMAATUALIZACAO);
    }

    @Test
    @Transactional
    public void createParametroWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = parametroRepository.findAll().size();

        // Create the Parametro with an existing ID
        parametro.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restParametroMockMvc.perform(post("/api/parametros")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(parametro)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Parametro> parametroList = parametroRepository.findAll();
        assertThat(parametroList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = parametroRepository.findAll().size();
        // set the field null
        parametro.setNome(null);

        // Create the Parametro, which fails.

        restParametroMockMvc.perform(post("/api/parametros")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(parametro)))
            .andExpect(status().isBadRequest());

        List<Parametro> parametroList = parametroRepository.findAll();
        assertThat(parametroList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkValorIsRequired() throws Exception {
        int databaseSizeBeforeTest = parametroRepository.findAll().size();
        // set the field null
        parametro.setValor(null);

        // Create the Parametro, which fails.

        restParametroMockMvc.perform(post("/api/parametros")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(parametro)))
            .andExpect(status().isBadRequest());

        List<Parametro> parametroList = parametroRepository.findAll();
        assertThat(parametroList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDatainclusaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = parametroRepository.findAll().size();
        // set the field null
        parametro.setDatainclusao(null);

        // Create the Parametro, which fails.

        restParametroMockMvc.perform(post("/api/parametros")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(parametro)))
            .andExpect(status().isBadRequest());

        List<Parametro> parametroList = parametroRepository.findAll();
        assertThat(parametroList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDataultimaatualizacaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = parametroRepository.findAll().size();
        // set the field null
        parametro.setDataultimaatualizacao(null);

        // Create the Parametro, which fails.

        restParametroMockMvc.perform(post("/api/parametros")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(parametro)))
            .andExpect(status().isBadRequest());

        List<Parametro> parametroList = parametroRepository.findAll();
        assertThat(parametroList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllParametros() throws Exception {
        // Initialize the database
        parametroRepository.saveAndFlush(parametro);

        // Get all the parametroList
        restParametroMockMvc.perform(get("/api/parametros?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(parametro.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())))
            .andExpect(jsonPath("$.[*].valor").value(hasItem(DEFAULT_VALOR.toString())))
            .andExpect(jsonPath("$.[*].datainclusao").value(hasItem(DEFAULT_DATAINCLUSAO.toString())))
            .andExpect(jsonPath("$.[*].dataultimaatualizacao").value(hasItem(DEFAULT_DATAULTIMAATUALIZACAO.toString())));
    }

    @Test
    @Transactional
    public void getParametro() throws Exception {
        // Initialize the database
        parametroRepository.saveAndFlush(parametro);

        // Get the parametro
        restParametroMockMvc.perform(get("/api/parametros/{id}", parametro.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(parametro.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME.toString()))
            .andExpect(jsonPath("$.valor").value(DEFAULT_VALOR.toString()))
            .andExpect(jsonPath("$.datainclusao").value(DEFAULT_DATAINCLUSAO.toString()))
            .andExpect(jsonPath("$.dataultimaatualizacao").value(DEFAULT_DATAULTIMAATUALIZACAO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingParametro() throws Exception {
        // Get the parametro
        restParametroMockMvc.perform(get("/api/parametros/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateParametro() throws Exception {
        // Initialize the database
        parametroRepository.saveAndFlush(parametro);
        int databaseSizeBeforeUpdate = parametroRepository.findAll().size();

        // Update the parametro
        Parametro updatedParametro = parametroRepository.findOne(parametro.getId());
        updatedParametro
            .nome(UPDATED_NOME)
            .valor(UPDATED_VALOR)
            .datainclusao(UPDATED_DATAINCLUSAO)
            .dataultimaatualizacao(UPDATED_DATAULTIMAATUALIZACAO);

        restParametroMockMvc.perform(put("/api/parametros")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedParametro)))
            .andExpect(status().isOk());

        // Validate the Parametro in the database
        List<Parametro> parametroList = parametroRepository.findAll();
        assertThat(parametroList).hasSize(databaseSizeBeforeUpdate);
        Parametro testParametro = parametroList.get(parametroList.size() - 1);
        assertThat(testParametro.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testParametro.getValor()).isEqualTo(UPDATED_VALOR);
        assertThat(testParametro.getDatainclusao()).isEqualTo(UPDATED_DATAINCLUSAO);
        assertThat(testParametro.getDataultimaatualizacao()).isEqualTo(UPDATED_DATAULTIMAATUALIZACAO);
    }

    @Test
    @Transactional
    public void updateNonExistingParametro() throws Exception {
        int databaseSizeBeforeUpdate = parametroRepository.findAll().size();

        // Create the Parametro

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restParametroMockMvc.perform(put("/api/parametros")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(parametro)))
            .andExpect(status().isCreated());

        // Validate the Parametro in the database
        List<Parametro> parametroList = parametroRepository.findAll();
        assertThat(parametroList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteParametro() throws Exception {
        // Initialize the database
        parametroRepository.saveAndFlush(parametro);
        int databaseSizeBeforeDelete = parametroRepository.findAll().size();

        // Get the parametro
        restParametroMockMvc.perform(delete("/api/parametros/{id}", parametro.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Parametro> parametroList = parametroRepository.findAll();
        assertThat(parametroList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Parametro.class);
        Parametro parametro1 = new Parametro();
        parametro1.setId(1L);
        Parametro parametro2 = new Parametro();
        parametro2.setId(parametro1.getId());
        assertThat(parametro1).isEqualTo(parametro2);
        parametro2.setId(2L);
        assertThat(parametro1).isNotEqualTo(parametro2);
        parametro1.setId(null);
        assertThat(parametro1).isNotEqualTo(parametro2);
    }
}
