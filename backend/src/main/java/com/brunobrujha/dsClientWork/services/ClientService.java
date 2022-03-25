package com.brunobrujha.dsClientWork.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.brunobrujha.dsClientWork.dto.ClientDTO;
import com.brunobrujha.dsClientWork.entities.Client;
import com.brunobrujha.dsClientWork.repositories.ClientRepository;
import com.brunobrujha.dsClientWork.services.exceptions.EntityNotFoundException;


@Service
public class ClientService {

	@Autowired
	private ClientRepository cliRepository;

	@Transactional(readOnly = true)
	public Page<ClientDTO> findAll(PageRequest pageRequest) {

		Page<Client> listPaged = cliRepository.findAll(pageRequest);

		return listPaged.map(x -> new ClientDTO(x));
	}

	@Transactional(readOnly = true)
	public ClientDTO findById(Long id) {
		Optional<Client> obj = cliRepository.findById(id);
		
		Client entity = obj.orElseThrow( () -> new EntityNotFoundException("Entity not found"));
		
		return new ClientDTO(entity);
	}
	
	@Transactional
	public ClientDTO insert(ClientDTO dto) {
		Client entity  = new Client();
		
		entity.setName(dto.getName());
		entity.setCpf(dto.getCpf());
		entity.setIncome(dto.getIncome());
		entity.setBirthDate(dto.getBirthDate());
		entity.setChildren(dto.getChildren());
		
		entity = cliRepository.save(entity);
		
		return new ClientDTO(entity);
				
	}
	
	@Transactional
	public ClientDTO update(Long id , ClientDTO dto) {
		try {
			Optional<Client> obj = cliRepository.findById(id);
			
			Client entity = obj.orElseThrow( () -> new EntityNotFoundException("Entity not found"));
			
			entity.setName(dto.getName());
			entity.setCpf(dto.getCpf());
			entity.setIncome(dto.getIncome());
			entity.setBirthDate(dto.getBirthDate());
			entity.setChildren(dto.getChildren());
			
			entity = cliRepository.save(entity);
			
			return new ClientDTO(entity);
		} catch (javax.persistence.EntityNotFoundException e) {
			throw new EntityNotFoundException("Id not found: " + id);
		}
	}
	
	public void delete(Long id) {
		try {
			cliRepository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new EntityNotFoundException("Id not found: " + id);
		}		
	}
	
}
