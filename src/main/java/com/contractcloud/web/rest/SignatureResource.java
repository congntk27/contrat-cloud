package com.contractcloud.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.contractcloud.domain.Signature;

import com.contractcloud.repository.SignatureRepository;
import com.contractcloud.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Signature.
 */
@RestController
@RequestMapping("/api")
public class SignatureResource {

    private final Logger log = LoggerFactory.getLogger(SignatureResource.class);

    private static final String ENTITY_NAME = "signature";

    private final SignatureRepository signatureRepository;

    public SignatureResource(SignatureRepository signatureRepository) {
        this.signatureRepository = signatureRepository;
    }

    /**
     * POST  /signatures : Create a new signature.
     *
     * @param signature the signature to create
     * @return the ResponseEntity with status 201 (Created) and with body the new signature, or with status 400 (Bad Request) if the signature has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/signatures")
    @Timed
    public ResponseEntity<Signature> createSignature(@Valid @RequestBody Signature signature) throws URISyntaxException {
        log.debug("REST request to save Signature : {}", signature);
        if (signature.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new signature cannot already have an ID")).body(null);
        }
        Signature result = signatureRepository.save(signature);
        return ResponseEntity.created(new URI("/api/signatures/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /signatures : Updates an existing signature.
     *
     * @param signature the signature to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated signature,
     * or with status 400 (Bad Request) if the signature is not valid,
     * or with status 500 (Internal Server Error) if the signature couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/signatures")
    @Timed
    public ResponseEntity<Signature> updateSignature(@Valid @RequestBody Signature signature) throws URISyntaxException {
        log.debug("REST request to update Signature : {}", signature);
        if (signature.getId() == null) {
            return createSignature(signature);
        }
        Signature result = signatureRepository.save(signature);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, signature.getId().toString()))
            .body(result);
    }

    /**
     * GET  /signatures : get all the signatures.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of signatures in body
     */
    @GetMapping("/signatures")
    @Timed
    public List<Signature> getAllSignatures() {
        log.debug("REST request to get all Signatures");
        return signatureRepository.findAll();
    }

    /**
     * GET  /signatures/:id : get the "id" signature.
     *
     * @param id the id of the signature to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the signature, or with status 404 (Not Found)
     */
    @GetMapping("/signatures/{id}")
    @Timed
    public ResponseEntity<Signature> getSignature(@PathVariable String id) {
        log.debug("REST request to get Signature : {}", id);
        Signature signature = signatureRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(signature));
    }

    /**
     * DELETE  /signatures/:id : delete the "id" signature.
     *
     * @param id the id of the signature to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/signatures/{id}")
    @Timed
    public ResponseEntity<Void> deleteSignature(@PathVariable String id) {
        log.debug("REST request to delete Signature : {}", id);
        signatureRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }
}
