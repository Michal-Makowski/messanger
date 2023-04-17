package com.makowski.messenger.repository;

import java.util.Optional;

import org.springframework.data.repository.ListCrudRepository;

import com.makowski.messenger.entity.ContactList;

public interface ContactListRepository extends ListCrudRepository<ContactList, Long>{
    Optional<ContactList> findByUserId(Long id);
}
