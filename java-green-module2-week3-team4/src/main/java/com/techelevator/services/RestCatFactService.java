package com.techelevator.services;

import com.techelevator.model.CatPic;
import org.springframework.stereotype.Component;

import com.techelevator.model.CatFact;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

@Component
public class RestCatFactService implements CatFactService {
	private final RestTemplate restTemplate = new RestTemplate();
	@Override
	public CatFact getFact() {
		// TODO Auto-generated method stub
		CatFact catFact = null;
		try{
			catFact = restTemplate.getForObject("https://cat-data.netlify.app/api/facts/random", CatFact.class);
		}catch (RestClientResponseException e) {
			e.getStatusText();
		} catch (ResourceAccessException e) {
			e.getStackTrace();
		}
		return catFact;
	}

}
