package com.makowski.messenger.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.ListCrudRepository;

import com.makowski.messenger.entity.Chat;

public interface ChatRepository extends ListCrudRepository<Chat, Long>{
    Optional<List<Chat>> findByChatUsersId(Long id);
}
