package co.simplon.chefDOeuvre.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "logement")
public class LogementModel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@NotNull
	private Long id_logement;
	private String photoLogement;
	
	// pour le moment, seule la ville du logement sera prise en compte
//	private int numeroLogement;
//	private String rueLogement;
//	private int codePostalLogement;
	@NotNull
	private String villeLogement;
	
	// proposer une chambre est obligatoire, le champ ne peut être vide mais il peut être supperieur à 1
//	@NotNull
	private int chambreLogement;
	
	// options à titre facultatif
	private boolean salleDeBainLogement = false;
	private boolean toilettesLogement = false;
	private boolean cuisineLogement = false;
	private boolean sejourLogement = false;
	
	private Date dateDispoDebutLogement;
	private Date dateDispoFinLogement;
	private int prixLogement;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "agent_id", nullable = false)
	private AgentModel agent;
	
	public LogementModel() {
//		super();
	}
	
	/*
	 * methodes pour changer les booleans
	 */
	public void seLaver() {
		salleDeBainLogement = !salleDeBainLogement;
	}
	public void allerAuxWC() {
		toilettesLogement = !toilettesLogement;
	}
	public void cuisiner() {
		cuisineLogement = !cuisineLogement;
	}
	public void seRelaxer() {
		sejourLogement = !sejourLogement;
	}

	/*
	 * getter, setter
	 */
	public Long getId_logement() {
		return id_logement;
	}

	public void setId_logement(Long id_logement) {
		this.id_logement = id_logement;
	}

	public String getPhotoLogement() {
		return photoLogement;
	}

	public void setPhotoLogement(String photoLogement) {
		this.photoLogement = photoLogement;
	}

	public String getVilleLogement() {
		return villeLogement;
	}

	public void setVilleLogement(String villeLogement) {
		this.villeLogement = villeLogement;
	}

	public int getChambreLogement() {
		return chambreLogement;
	}

	public void setChambreLogement(int chambreLogement) {
		this.chambreLogement = chambreLogement;
	}

	public boolean isSalleDeBainLogement() {
		return salleDeBainLogement;
	}

	public void setSalleDeBainLogement(boolean salleDeBainLogement) {
		this.salleDeBainLogement = salleDeBainLogement;
	}

	public boolean isToilettesLogement() {
		return toilettesLogement;
	}

	public void setToilettesLogement(boolean toilettesLogement) {
		this.toilettesLogement = toilettesLogement;
	}

	public boolean isCuisineLogement() {
		return cuisineLogement;
	}

	public void setCuisineLogement(boolean cuisineLogement) {
		this.cuisineLogement = cuisineLogement;
	}

	public boolean isSejourLogement() {
		return sejourLogement;
	}

	public void setSejourLogement(boolean sejourLogement) {
		this.sejourLogement = sejourLogement;
	}

	public Date getDateDispoDebutLogement() {
		return dateDispoDebutLogement;
	}

	public void setDateDispoDebutLogement(Date dateDispoDebutLogement) {
		this.dateDispoDebutLogement = dateDispoDebutLogement;
	}

	public Date getDateDispoFinLogement() {
		return dateDispoFinLogement;
	}

	public void setDateDispoFinLogement(Date dateDispoFinLogement) {
		this.dateDispoFinLogement = dateDispoFinLogement;
	}

	public int getPrixLogement() {
		return prixLogement;
	}

	public void setPrixLogement(int prixLogement) {
		this.prixLogement = prixLogement;
	}
	
}
