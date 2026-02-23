package com.theonova.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface RepositoryEngine<T, ID> extends JpaRepository<T,ID> {
    List<T> findAllByActive(Boolean estado);
    List<T> findAllByActive(Boolean estado, Sort sort);
    List<T> findAllByIdAndActive(ID id ,Boolean estado, Sort sort);
    Optional<T> findByIdAndActive(ID id , Boolean estado);
}
