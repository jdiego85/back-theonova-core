package com.theonova.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface RepositoryEngine<T, E> extends JpaRepository<T,E> {
    List<T> findAllByEstado(Boolean estado);
    List<T> findAllByEstado(Boolean estado, Sort sort);
    List<T> findAllByIdAndEstado(Long id ,Boolean estado, Sort sort);
    Optional<T> findByIdAndEstado(Long id , Boolean estado);
}
