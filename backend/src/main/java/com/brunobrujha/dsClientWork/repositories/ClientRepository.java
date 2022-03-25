package com.brunobrujha.dsClientWork.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.brunobrujha.dsClientWork.entities.Client;

public interface ClientRepository extends JpaRepository<Client, Long> {

}
