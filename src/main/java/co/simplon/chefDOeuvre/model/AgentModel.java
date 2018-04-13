package co.simplon.chefDOeuvre.model;

import java.util.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "agent")
public class AgentModel {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@NotNull
	private Long id_agent;
	private String photoAgent;
	// données encapsulées dans l'idRH de chaque agent.
	@NotNull
	private String nomAgent;
	private String prenomAgent;
	private String villeAffectationAgent;

	private String mailAgent;
	private String numeroTelephoneAgent;
	// chaque agent peut proposer un ou plusieurs logements,
	// il peut donc disposer d'une liste de logements
	private List<LogementModel> listeLogements;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "agent")
	private Set<LogementModel> logement = new HashSet<>();

	// TODO super ou pas super?
	public AgentModel() {
//		super();
	}
	
//	public AgentModel(Long)

	public Long getId_agent() {
		return id_agent;
	}

	public void setId_agent(Long id_agent) {
		this.id_agent = id_agent;
	}

	public String getPhotoAgent() {
		return photoAgent;
	}

	public void setPhotoAgent(String photoAgent) {
		this.photoAgent = photoAgent;
	}

	public String getNomAgent() {
		return nomAgent;
	}

	public void setNomAgent(String nomAgent) {
		this.nomAgent = nomAgent;
	}

	public String getPrenomAgent() {
		return prenomAgent;
	}

	public void setPrenomAgent(String prenomAgent) {
		this.prenomAgent = prenomAgent;
	}

	public String getVilleAffectationAgent() {
		return villeAffectationAgent;
	}

	public void setVilleAffectationAgent(String villeAffectationAgent) {
		this.villeAffectationAgent = villeAffectationAgent;
	}

	public String getMailAgent() {
		return mailAgent;
	}

	public void setMailAgent(String mailAgent) {
		this.mailAgent = mailAgent;
	}

	public String getNumeroTelephoneAgent() {
		return numeroTelephoneAgent;
	}

	public void setNumeroTelephoneAgent(String numeroTelephoneAgent) {
		this.numeroTelephoneAgent = numeroTelephoneAgent;
	}

	public List<LogementModel> getListeLogements() {
		return listeLogements;
	}

	public void setListeLogements(List<LogementModel> listeLogements) {
		this.listeLogements = listeLogements;
	}

}
