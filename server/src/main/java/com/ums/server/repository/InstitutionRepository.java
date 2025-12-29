package com.ums.server.repository;

import com.ums.server.models.Institution;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InstitutionRepository extends JpaRepository<Institution,String> {
}
