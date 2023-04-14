package com.makowski.messenger.repository;

import java.util.List;

import org.springframework.data.repository.ListCrudRepository;

import com.makowski.messenger.entity.Message;

public interface MessageRepository extends ListCrudRepository<Message, Long> {
    List<Message> findByPermament(boolean permament);
}
