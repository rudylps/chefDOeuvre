package co.simplon.chefDOeuvre.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.simplon.chefDOeuvre.model.LogementModel;

@Repository
public interface LogementRepository extends JpaRepository<LogementModel, Long> {

}
