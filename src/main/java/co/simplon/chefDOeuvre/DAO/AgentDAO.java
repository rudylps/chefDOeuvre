package co.simplon.chefDOeuvre.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import co.simplon.chefDOeuvre.model.AgentModel;
import co.simplon.chefDOeuvre.model.LogementModel;

@Repository
public class AgentDAO {

	private final Logger log = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private DataSource dataSource;

	@Autowired
	public AgentDAO(JdbcTemplate jdbcTemplate) {
		this.dataSource = jdbcTemplate.getDataSource();
	}

	/**
	 * Recherche des logements liés à un agent
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public List<LogementModel> recupererLogementsLiesAgent(Long id) throws Exception {
		LogementModel logement;
		PreparedStatement pstmt = null;
		ResultSet rs;
		String sql;
		ArrayList<LogementModel> listeLogements = new ArrayList<LogementModel>();

		try {
			// Requete SQL
			sql = " SELECT * FROM logements"
					+ "INNER JOIN agent_logement"
					+ "ON logement.id = agent_logement.id_logement"
					+ "INNER JOIN agent"
					+ "ON agent_logement.id_agent = agent.id_agent"
					+ "WHERE agent.id_agent = ?;";

			pstmt = dataSource.getConnection().prepareStatement(sql);
			pstmt.setLong(1, id);
			// Log info
			logSQL(pstmt);
			// Lancement requete
			rs = pstmt.executeQuery();
			// resultat requete
			while (rs.next()) {
				logement = recupererLogementRS(rs);
				listeLogements.add(logement);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("SQL Error !:" + pstmt.toString(), e);
			throw e;
		} finally {
			pstmt.close();
		}

		return listeLogements;
	}

	private LogementModel recupererLogementRS(ResultSet rs) throws SQLException {
		LogementModel logement = new LogementModel();
		logement.setId_logement(rs.getLong("id"));
		logement.setPhotoLogement(rs.getString("photo"));
		logement.setVilleLogement(rs.getString("ville"));
		logement.setChambreLogement(rs.getInt("chambre"));
		logement.setSalleDeBainLogement(rs.getBoolean("salle_de_bain"));
		logement.setToilettesLogement(rs.getBoolean("toilettes"));
		logement.setCuisineLogement(rs.getBoolean("cuisine"));
		logement.setSejourLogement(rs.getBoolean("sejour"));
		logement.setDateDispoDebutLogement(rs.getDate("date_debut"));
		logement.setDateDispoFinLogement(rs.getDate("date_fin"));
		logement.setPrixLogement(rs.getInt("prix"));

		return logement;
	}

	private void logSQL(PreparedStatement pstmt) {
		String sql;

		if (pstmt == null)
			return;

		sql = pstmt.toString().substring(pstmt.toString().indexOf(":") + 2);
		log.debug(sql);
	}

	/**
	 * Rechercher les agents avec un critère de recherche sur 3 champs
	 * 
	 * @param filtreAgent
	 * @return
	 * @throws Exception
	 */
	public List<AgentModel> recupererAgentsTries(String rechercheAgent) throws Exception {
		List<AgentModel> agentsTries = new ArrayList<AgentModel>();

		AgentModel agent;
		PreparedStatement pstmt = null;
		ResultSet rs;
		String sql;

		try {
			// Requete SQL
			sql = "SELECT * FROM agents"
					+ "WHERE nom LIKE ?"
					+ "OR prenom LIKE ?"
					+ "OR ville_affectation LIKE ?";
			pstmt = dataSource.getConnection().prepareStatement(sql);
			pstmt.setString(1, "%" + rechercheAgent + "%");
			pstmt.setString(2, "%" + rechercheAgent + "%");
			pstmt.setString(3, "%" + rechercheAgent + "%");

			// Log info
			logSQL(pstmt);
			// Lancement requete
			rs = pstmt.executeQuery();
			// resultat requete
			while (rs.next()) {
				agent = recupererAgentRS(rs);
				agentsTries.add(agent);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("SQL Error !:" + pstmt.toString(), e);
			throw e;
		} finally {
			pstmt.close();
		}
		
		return agentsTries;
	}
	
	private AgentModel recupererAgentRS(ResultSet rs) throws SQLException {
		AgentModel agent = new AgentModel();
		agent.setId_agent(rs.getLong("id"));
		agent.setPhotoAgent(rs.getString("photo"));
		agent.setNomAgent(rs.getString("nom"));
		agent.setPrenomAgent(rs.getString("prenom"));
		agent.setVilleAffectationAgent(rs.getString("ville"));
		agent.setMailAgent(rs.getString("mail"));
		agent.setNumeroTelephoneAgent(rs.getString("telephone"));

		return agent;
	}

}
