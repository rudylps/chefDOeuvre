package co.simplon.chefDOeuvre.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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

import co.simplon.chefDOeuvre.DAO.LogementDAO;
import co.simplon.chefDOeuvre.model.LogementModel;
import co.simplon.chefDOeuvre.service.LogementService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class LogementController {

	@Autowired
	private LogementService logementService;
	@Autowired
	private LogementDAO logementDAO;

	// SELECT * FROM logements;
	// afficher tous les logements
	@GetMapping(path = "/logements")
	public @ResponseBody Iterable<LogementModel> recupererLogements() throws Exception {
		return logementService.recupererLogements();
	}

	// SELECT * FROM logement WHERE `id_logement`=id;
	// afficher un seul logement selon son id
	@GetMapping(path = "/logement/{id}")
	ResponseEntity<LogementModel> recupererLogement(@PathVariable(value = "id") long id) throws Exception {
		LogementModel logement = logementService.recupererLogement(id);
		if (logement == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().body(logement);
	}

	// DELETE FROM logement WHERE `id_logement`=id;
	// supprimer un logement selon son id
	@DeleteMapping(path = "/logement/{id}")
	ResponseEntity<LogementModel> supprimerLogement(@PathVariable(value = "id") long id) throws Exception {
		LogementModel logement = logementService.recupererLogement(id);
		if (logement == null)
			return ResponseEntity.notFound().build();

		logementService.supprimerLogement(id);
		return ResponseEntity.ok().build();
	}

	// INSERT INTO `logement` (`photoLogement`, `nomLogement`, `prenomLogement`,
	// `villeAffectationLogement`, `mailLogement`, `numeroTelephoneLogement`) VALUES
	// (?,?,?,?,?,?);
	// créer un logement
	@PostMapping(path = "/logements")
	LogementModel ajouterLogement(@Valid @RequestBody LogementModel logement) throws Exception {
		return logementService.ajouterLogement(logement);
	}

	// UPDATE `logement` (`photoLogement`, `nomLogement`, `prenomLogement`,
	// `villeAffectationLogement`, `mailLogement`, `numeroTelephoneLogement`) FROM
	// logement
	// WHERE `id_logement`=id;
	// met à jour un logement
	@PutMapping(path = "/logement/{id}")
	ResponseEntity<LogementModel> editerLogement(@PathVariable(value = "id") long id, @Valid @RequestBody LogementModel logement)
			throws Exception {
		LogementModel logementAModifier = logementService.recupererLogement(id);
		if (logementAModifier == null)
			return ResponseEntity.notFound().build();

		// Mise à jour des attributs obligatoires
		// Tous les champs sauf la photo doivent être renseignés
		logementAModifier.setId_logement(logement.getId_logement());
		logementAModifier.setVilleLogement(logement.getVilleLogement());
		// int
		logementAModifier.setChambreLogement(logement.getChambreLogement());
		logementAModifier.setPrixLogement(logement.getPrixLogement());
		// booleans
		logementAModifier.setSalleDeBainLogement(logement.isSalleDeBainLogement());
		logementAModifier.setToilettesLogement(logement.isToilettesLogement());
		logementAModifier.setCuisineLogement(logement.isCuisineLogement());
		logementAModifier.setSejourLogement(logement.isSejourLogement());
		// dates
		logementAModifier.setDateDispoDebutLogement(logement.getDateDispoDebutLogement());
		logementAModifier.setDateDispoFinLogement(logement.getDateDispoFinLogement());

		// Mise à jour des attributs facultatifs lors de l'édition
		if (logement.getPhotoLogement() != null)
			logementAModifier.setPhotoLogement(logement.getPhotoLogement());

		LogementModel logementModifie = logementService.editerLogement(id, logementAModifier);
		return ResponseEntity.ok(logementModifie);

	}

}
