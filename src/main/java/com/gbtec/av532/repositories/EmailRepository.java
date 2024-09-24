package com.gbtec.av532.repositories;

import com.gbtec.av532.model.EmailModel;
import org.springframework.data.repository.ListCrudRepository;

import java.util.UUID;

public interface EmailRepository extends ListCrudRepository<EmailModel, UUID> {
}
