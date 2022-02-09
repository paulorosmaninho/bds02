package com.devsuperior.bds02.services;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.bds02.dto.EventDTO;
import com.devsuperior.bds02.entities.City;
import com.devsuperior.bds02.entities.Event;
import com.devsuperior.bds02.repositories.CityRepository;
import com.devsuperior.bds02.repositories.EventRepository;
import com.devsuperior.bds02.services.exceptions.ResourceNotFoundException;

@Service
public class EventService {

	@Autowired
	private EventRepository eventRepository;
	
	@Autowired
	private CityRepository cityRepository;

	@Transactional
	public EventDTO update(Long id, EventDTO eventDTO) {

		try {

			Event entity = eventRepository.getOne(id);
			City city = cityRepository.getOne(eventDTO.getCityId());

			entity.setName(eventDTO.getName());
			entity.setDate(eventDTO.getDate());
			entity.setUrl(eventDTO.getUrl());
			entity.setCity(city);
			
			entity = eventRepository.save(entity);

			return new EventDTO(entity);

		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Evento " + id + " não encontrado");
		}


	}

}
