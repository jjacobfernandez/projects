package com.techelevator.services;

import org.springframework.stereotype.Component;

import com.techelevator.model.CatPic;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

@Component
public class RestCatPicService implements CatPicService {
	private final RestTemplate restTemplate = new RestTemplate();

	@Override
	public CatPic getPic() {
		CatPic catPic = null;
		try{
			catPic = restTemplate.getForObject("https://cat-data.netlify.app/api/pictures/random", CatPic.class);
		}catch (RestClientResponseException e) {
			e.getStatusText();
		} catch (ResourceAccessException e) {
			e.getStackTrace();
		}
		return catPic;
	}

}	
