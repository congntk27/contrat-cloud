package com.contractcloud.web.rest;

import com.contractcloud.ContractcloudApp;

import com.contractcloud.domain.Signature;
import com.contractcloud.repository.SignatureRepository;
import com.contractcloud.web.rest.errors.ExceptionTranslator;

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

import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static com.contractcloud.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the SignatureResource REST controller.
 *
 * @see SignatureResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ContractcloudApp.class)
public class SignatureResourceIntTest {

    private static final String DEFAULT_SIGNBY = "AAAAAAAAAA";
    private static final String UPDATED_SIGNBY = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_SIGNDATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_SIGNDATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private SignatureRepository signatureRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restSignatureMockMvc;

    private Signature signature;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        SignatureResource signatureResource = new SignatureResource(signatureRepository);
        this.restSignatureMockMvc = MockMvcBuilders.standaloneSetup(signatureResource)
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
    public static Signature createEntity() {
        Signature signature = new Signature()
            .signby(DEFAULT_SIGNBY)
            .signdate(DEFAULT_SIGNDATE);
        return signature;
    }

    @Before
    public void initTest() {
        signatureRepository.deleteAll();
        signature = createEntity();
    }

    @Test
    public void createSignature() throws Exception {
        int databaseSizeBeforeCreate = signatureRepository.findAll().size();

        // Create the Signature
        restSignatureMockMvc.perform(post("/api/signatures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(signature)))
            .andExpect(status().isCreated());

        // Validate the Signature in the database
        List<Signature> signatureList = signatureRepository.findAll();
        assertThat(signatureList).hasSize(databaseSizeBeforeCreate + 1);
        Signature testSignature = signatureList.get(signatureList.size() - 1);
        assertThat(testSignature.getSignby()).isEqualTo(DEFAULT_SIGNBY);
        assertThat(testSignature.getSigndate()).isEqualTo(DEFAULT_SIGNDATE);
    }

    @Test
    public void createSignatureWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = signatureRepository.findAll().size();

        // Create the Signature with an existing ID
        signature.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restSignatureMockMvc.perform(post("/api/signatures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(signature)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Signature> signatureList = signatureRepository.findAll();
        assertThat(signatureList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void checkSignbyIsRequired() throws Exception {
        int databaseSizeBeforeTest = signatureRepository.findAll().size();
        // set the field null
        signature.setSignby(null);

        // Create the Signature, which fails.

        restSignatureMockMvc.perform(post("/api/signatures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(signature)))
            .andExpect(status().isBadRequest());

        List<Signature> signatureList = signatureRepository.findAll();
        assertThat(signatureList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllSignatures() throws Exception {
        // Initialize the database
        signatureRepository.save(signature);

        // Get all the signatureList
        restSignatureMockMvc.perform(get("/api/signatures?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(signature.getId())))
            .andExpect(jsonPath("$.[*].signby").value(hasItem(DEFAULT_SIGNBY.toString())))
            .andExpect(jsonPath("$.[*].signdate").value(hasItem(sameInstant(DEFAULT_SIGNDATE))));
    }

    @Test
    public void getSignature() throws Exception {
        // Initialize the database
        signatureRepository.save(signature);

        // Get the signature
        restSignatureMockMvc.perform(get("/api/signatures/{id}", signature.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(signature.getId()))
            .andExpect(jsonPath("$.signby").value(DEFAULT_SIGNBY.toString()))
            .andExpect(jsonPath("$.signdate").value(sameInstant(DEFAULT_SIGNDATE)));
    }

    @Test
    public void getNonExistingSignature() throws Exception {
        // Get the signature
        restSignatureMockMvc.perform(get("/api/signatures/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateSignature() throws Exception {
        // Initialize the database
        signatureRepository.save(signature);
        int databaseSizeBeforeUpdate = signatureRepository.findAll().size();

        // Update the signature
        Signature updatedSignature = signatureRepository.findOne(signature.getId());
        updatedSignature
            .signby(UPDATED_SIGNBY)
            .signdate(UPDATED_SIGNDATE);

        restSignatureMockMvc.perform(put("/api/signatures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSignature)))
            .andExpect(status().isOk());

        // Validate the Signature in the database
        List<Signature> signatureList = signatureRepository.findAll();
        assertThat(signatureList).hasSize(databaseSizeBeforeUpdate);
        Signature testSignature = signatureList.get(signatureList.size() - 1);
        assertThat(testSignature.getSignby()).isEqualTo(UPDATED_SIGNBY);
        assertThat(testSignature.getSigndate()).isEqualTo(UPDATED_SIGNDATE);
    }

    @Test
    public void updateNonExistingSignature() throws Exception {
        int databaseSizeBeforeUpdate = signatureRepository.findAll().size();

        // Create the Signature

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSignatureMockMvc.perform(put("/api/signatures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(signature)))
            .andExpect(status().isCreated());

        // Validate the Signature in the database
        List<Signature> signatureList = signatureRepository.findAll();
        assertThat(signatureList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deleteSignature() throws Exception {
        // Initialize the database
        signatureRepository.save(signature);
        int databaseSizeBeforeDelete = signatureRepository.findAll().size();

        // Get the signature
        restSignatureMockMvc.perform(delete("/api/signatures/{id}", signature.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Signature> signatureList = signatureRepository.findAll();
        assertThat(signatureList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Signature.class);
        Signature signature1 = new Signature();
        signature1.setId("id1");
        Signature signature2 = new Signature();
        signature2.setId(signature1.getId());
        assertThat(signature1).isEqualTo(signature2);
        signature2.setId("id2");
        assertThat(signature1).isNotEqualTo(signature2);
        signature1.setId(null);
        assertThat(signature1).isNotEqualTo(signature2);
    }
}
