package co.simplon.chefDOeuvre.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.simplon.chefDOeuvre.model.AgentModel;
import co.simplon.chefDOeuvre.repository.IAgentRepository;

@Service
public class AgentService {

	@Autowired
	private IAgentRepository agentRepository;

	public Iterable<AgentModel> recupererAgents() throws Exception {
		return agentRepository.findAll();
	}

	public AgentModel recupererAgent(Long id) throws Exception {
		//return agentRepository.findOne(id);
		return agentRepository.findOne(id);
	}

	public void supprimerAgent(Long id) {
		agentRepository.delete(id);
	}

	public AgentModel ajouterAgent(AgentModel agent) throws Exception {
		return agentRepository.save(agent);
	}

	public AgentModel editerAgent(Long id, AgentModel agent) throws Exception {
		return agentRepository.save(agent);
	}

	public void supprimerAgents() {
		agentRepository.deleteAll();
	}
}
