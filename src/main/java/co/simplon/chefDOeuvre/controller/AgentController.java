package co.simplon.chefDOeuvre.controller;

import java.util.List;

import javax.validation.Valid;

import org.aspectj.weaver.loadtime.Agent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import co.simplon.chefDOeuvre.DAO.AgentDAO;
import co.simplon.chefDOeuvre.model.AgentModel;
import co.simplon.chefDOeuvre.model.LogementModel;
import co.simplon.chefDOeuvre.service.AgentService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class AgentController {

	@Autowired
	private AgentService agentService;
	@Autowired
	private AgentDAO agentDAO;

	// SELECT * FROM agents;
	// afficher tous les agents
	@GetMapping(path = "/agents")
	public @ResponseBody Iterable<AgentModel> recupererAgents() throws Exception {
		return agentService.recupererAgents();
	}

	// SELECT * FROM agent WHERE `id_agent`=id;
	// afficher un seul agent selon son id
	@GetMapping(path = "/agent/{id}")
	public ResponseEntity<AgentModel> recupererAgent(@PathVariable(value = "id") long id) throws Exception {
		AgentModel agent = agentService.recupererAgent(id);
		if (agent == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		return ResponseEntity.ok().body(agent);
	}

	// DELETE FROM agent WHERE `id_agent`=id;
	// supprimer un agent selon son id
	@DeleteMapping(path = "/agent/{id}")
	public ResponseEntity<AgentModel> supprimerAgent(@PathVariable(value = "id") long id) throws Exception {
		AgentModel agent = agentService.recupererAgent(id);
		if (agent == null) {
			return ResponseEntity.notFound().build();
		}

		agentService.supprimerAgent(id);
		return ResponseEntity.ok().build();
	}

	// INSERT INTO `agent` (`photoAgent`, `nomAgent`, `prenomAgent`,
	// `villeAffectationAgent`, `mailAgent`, `numeroTelephoneAgent`) VALUES
	// (?,?,?,?,?,?);
	// créer un agent
	@PostMapping(path = "/agents")
	public AgentModel ajouterAgent(@Valid @RequestBody AgentModel agent) throws Exception {
		return agentService.ajouterAgent(agent);
	}

	// UPDATE `agent` (`photoAgent`, `nomAgent`, `prenomAgent`,
	// `villeAffectationAgent`, `mailAgent`, `numeroTelephoneAgent`) FROM agent
	// WHERE `id_agent`=id;
	// met à jour un agent
	@PutMapping(path = "/agent/{id}")
	public ResponseEntity<AgentModel> editerAgent(@PathVariable(value = "id") long id, @Valid @RequestBody AgentModel agent)
			throws Exception {
		AgentModel agentAModifier = agentService.recupererAgent(id);
		if (agentAModifier == null)
			return ResponseEntity.notFound().build();
	
	// Mise à jour des attributs obligatoires
	agentAModifier.setId_agent(agent.getId_agent());
	agentAModifier.setNomAgent(agent.getNomAgent());
	
	// Mise à jour des attributs facultatifs lors de l'édition
	if (agent.getPhotoAgent() != null)
		agentAModifier.setPhotoAgent(agent.getPhotoAgent());
	if (agent.getPrenomAgent() != null)
		agentAModifier.setPrenomAgent(agent.getPrenomAgent());
	if (agent.getVilleAffectationAgent() != null)
		agentAModifier.setVilleAffectationAgent(agent.getVilleAffectationAgent());
	if (agent.getMailAgent() != null)
		agentAModifier.setMailAgent(agent.getMailAgent());
	// TODO int!
	if (agent.getNumeroTelephoneAgent() != null)
		agentAModifier.setNumeroTelephoneAgent(agent.getNumeroTelephoneAgent());

	AgentModel agentModifie = agentService.editerAgent(id, agentAModifier);
	return ResponseEntity.ok(agentModifie);
	
	}
	
	// recuperer les logements d'un agent
	@GetMapping(path = "/agent/{id}/logements")
	public ResponseEntity<?> recupererLogementsLiesAgent(@PathVariable(value = "id") long id) throws Exception {
		List<LogementModel> logements = null;
		AgentModel agent = agentService.recupererAgent(id);
		try {
		logements = agentDAO.recupererLogementsLiesAgent(id);
		}
		catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
			
		}
		if (agent == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.status(HttpStatus.OK).body(logements);

	}

}
