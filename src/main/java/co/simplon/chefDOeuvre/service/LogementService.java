package co.simplon.chefDOeuvre.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.simplon.chefDOeuvre.model.LogementModel;
import co.simplon.chefDOeuvre.repository.LogementRepository;

@Service
public class LogementService {

	@Autowired
	private LogementRepository logementRepository;

	public Iterable<LogementModel> recupererLogements() throws Exception {
		return logementRepository.findAll();
	}

	public LogementModel recupererLogement(Long id) throws Exception {
		return logementRepository.findOne(id);
	}

	public void supprimerLogement(Long id) {
		logementRepository.delete(id);
	}

	public LogementModel ajouterLogement(LogementModel logement) throws Exception {
		return logementRepository.save(logement);
	}

	public LogementModel editerLogement(Long id, LogementModel logement) throws Exception {
		return logementRepository.save(logement);
	}

	public void supprimerLogements() {
		logementRepository.deleteAll();
	}

}
