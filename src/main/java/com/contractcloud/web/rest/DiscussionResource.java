package com.contractcloud.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.contractcloud.domain.Discussion;

import com.contractcloud.repository.DiscussionRepository;
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
 * REST controller for managing Discussion.
 */
@RestController
@RequestMapping("/api")
public class DiscussionResource {

    private final Logger log = LoggerFactory.getLogger(DiscussionResource.class);

    private static final String ENTITY_NAME = "discussion";

    private final DiscussionRepository discussionRepository;

    public DiscussionResource(DiscussionRepository discussionRepository) {
        this.discussionRepository = discussionRepository;
    }

    /**
     * POST  /discussions : Create a new discussion.
     *
     * @param discussion the discussion to create
     * @return the ResponseEntity with status 201 (Created) and with body the new discussion, or with status 400 (Bad Request) if the discussion has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/discussions")
    @Timed
    public ResponseEntity<Discussion> createDiscussion(@Valid @RequestBody Discussion discussion) throws URISyntaxException {
        log.debug("REST request to save Discussion : {}", discussion);
        if (discussion.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new discussion cannot already have an ID")).body(null);
        }
        Discussion result = discussionRepository.save(discussion);
        return ResponseEntity.created(new URI("/api/discussions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /discussions : Updates an existing discussion.
     *
     * @param discussion the discussion to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated discussion,
     * or with status 400 (Bad Request) if the discussion is not valid,
     * or with status 500 (Internal Server Error) if the discussion couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/discussions")
    @Timed
    public ResponseEntity<Discussion> updateDiscussion(@Valid @RequestBody Discussion discussion) throws URISyntaxException {
        log.debug("REST request to update Discussion : {}", discussion);
        if (discussion.getId() == null) {
            return createDiscussion(discussion);
        }
        Discussion result = discussionRepository.save(discussion);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, discussion.getId().toString()))
            .body(result);
    }

    /**
     * GET  /discussions : get all the discussions.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of discussions in body
     */
    @GetMapping("/discussions")
    @Timed
    public List<Discussion> getAllDiscussions() {
        log.debug("REST request to get all Discussions");
        return discussionRepository.findAll();
    }

    /**
     * GET  /discussions/:id : get the "id" discussion.
     *
     * @param id the id of the discussion to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the discussion, or with status 404 (Not Found)
     */
    @GetMapping("/discussions/{id}")
    @Timed
    public ResponseEntity<Discussion> getDiscussion(@PathVariable String id) {
        log.debug("REST request to get Discussion : {}", id);
        Discussion discussion = discussionRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(discussion));
    }

    /**
     * DELETE  /discussions/:id : delete the "id" discussion.
     *
     * @param id the id of the discussion to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/discussions/{id}")
    @Timed
    public ResponseEntity<Void> deleteDiscussion(@PathVariable String id) {
        log.debug("REST request to delete Discussion : {}", id);
        discussionRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }
}
