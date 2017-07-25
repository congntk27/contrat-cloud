package com.contractcloud.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;
import com.contractcloud.repository.DiscussionRepository;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

/**
 * REST controller for managing Discussion.
 */
@RestController
@RequestMapping("/api")
public class CategoryResource
{

	private final Logger				log			= LoggerFactory.getLogger(CategoryResource.class);

	private static final String			ENTITY_NAME	= "discussion";

	private final DiscussionRepository	discussionRepository;

	public CategoryResource(DiscussionRepository discussionRepository)
	{
		this.discussionRepository = discussionRepository;
	}

	@GetMapping(path = "/category", params = "description")
	@Timed
	public String getCategory(@RequestParam(name = "description", required = true) String description) throws UnirestException
	{
		HttpResponse<String> response = Unirest.post("http://api.meaningcloud.com/class-1.1").header("content-type", "application/x-www-form-urlencoded").body("key=13cba2eadf0a801c2bbfc64f32280d7e&txt=" +
				description + "&of=json&model=IPTC_en").asString();

		return response.getBody();
	}

}
