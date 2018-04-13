package co.simplon.chefDOeuvre.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.simplon.chefDOeuvre.model.AgentModel;

@Repository
public interface AgentRepository extends JpaRepository<AgentModel, Long> {

}
