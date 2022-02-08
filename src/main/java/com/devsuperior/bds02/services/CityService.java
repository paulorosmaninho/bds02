package com.devsuperior.bds02.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.bds02.dto.CityDTO;
import com.devsuperior.bds02.entities.City;
import com.devsuperior.bds02.repositories.CityRepository;
import com.devsuperior.bds02.services.exceptions.DatabaseException;
import com.devsuperior.bds02.services.exceptions.ResourceNotFoundException;

@Service
public class CityService {

	@Autowired
	private CityRepository cityRepository;

	@Transactional(readOnly = true)
	public List<CityDTO> findAll() {

		List<City> listEntityCities = cityRepository.findAll(Sort.by("name"));

		List<CityDTO> listDTOCities = new ArrayList<>();

		listEntityCities.forEach(entityCity -> listDTOCities.add(new CityDTO(entityCity)));

		return listDTOCities;

	}

	@Transactional
	public CityDTO insert(CityDTO cityDTO) {

		City entity = new City();

		entity.setName(cityDTO.getName());

		entity = cityRepository.save(entity);

		return new CityDTO(entity);

	}

	// Anotação @Transactional comentada para entrar no catch DataIntegrityViolationException
	// Ver com professor Nélio como usar @Transactional com catchs
	// @Transactional
	public void delete(Long id) {

		try {
			cityRepository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("Cidade " + id + " não encontrada");
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException(e.getMessage());
		}

	}

}
