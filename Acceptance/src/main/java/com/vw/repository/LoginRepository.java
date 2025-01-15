package com.vw.repository;

import com.vw.model.SignupEmployee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LoginRepository extends JpaRepository<SignupEmployee, String> {
    Optional<SignupEmployee> findByEmailIdAndPassword(String emailId, String password);
}
