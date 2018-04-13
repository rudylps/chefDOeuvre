package co.simplon.chefDOeuvre.DAO;

import java.sql.Date;
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
public class LogementDAO {

	private final Logger log = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private DataSource dataSource;

	@Autowired
	public LogementDAO(JdbcTemplate jdbcTemplate) {
		this.dataSource = jdbcTemplate.getDataSource();
	}

	/**
	 * Rechercher les logements avec un critère de recherche sur tous les champs
	 * 
	 * @param filtreLogements
	 * @return
	 * @throws Exception
	 */
	public List<LogementModel> recupererLogementsTries(String rechercheLogement) throws Exception {
		List<LogementModel> logementsTries = new ArrayList<LogementModel>();

		LogementModel logement;
		PreparedStatement pstmt = null;
		ResultSet rs;
		String sql;

		try {
			// Requete SQL
			sql = "SELECT * FROM logements"
					+ "WHERE villeLogement LIKE ?"
					+ "OR chambreLogement LIKE ?"
					+ "Or salleDeBainLogement LIKE ?"
					+ "OR toilettesLogement LIKE ?"
					+ "OR cuisineLogement LIKE ?"
					+ "OR sejourLogement LIKE ?"
					+ "OR dateDispoDebutLogement LIKE ?"
					+ "OR dateDispoFinLogement LIKE ?"
					+ "OR prixLogement LIKE ?";
			pstmt = dataSource.getConnection().prepareStatement(sql);
			pstmt.setString(1, "%" + rechercheLogement + "%");
			// TODO if ou faire requete speciale boolean, date et int
			pstmt.setInt(2, 2);
			pstmt.setBoolean(3, true);
			pstmt.setBoolean(4, true);
			pstmt.setBoolean(5, true);
			pstmt.setBoolean(6, true);
			Date date = null;
			pstmt.setDate(7, date);
			pstmt.setDate(8, date);
			pstmt.setInt(9, 9);

			// Log info
			logSQL(pstmt);
			// Lancement requete
			rs = pstmt.executeQuery();
			// resultat requete
			while (rs.next()) {
				logement = recupererLogementRS(rs);
				logementsTries.add(logement);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("SQL Error !:" + pstmt.toString(), e);
			throw e;
		} finally {
			pstmt.close();
		}

		return logementsTries;
	}

	/**
	 * Ajouter un logement à un agent
	 * 
	 * @param id_agent
	 * @param id_logement
	 * @throws Exception
	 */
	public void ajouterLogementAgent(long id_agent, long id_logement) throws Exception {
		PreparedStatement pstmt = null;
		String sql;
		try {
			// Requete SQL
			sql = "INSERT INTO agent (id_agent, id_logement)" + "VALUES (?, ?) ;";
			pstmt = dataSource.getConnection().prepareStatement(sql);
			pstmt.setLong(1, id_agent);
			pstmt.setLong(2, id_logement);
			// Log info
			logSQL(pstmt);
			// Lancement requete
			pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
			log.error("SQL Error !:" + pstmt.toString(), e);
			throw e;
		} finally {
			pstmt.close();
		}
	}

	/**
	 * Casser le lien d'un agent et d'un logement
	 * 
	 * @param id_agent
	 * @param id_logement
	 * @throws Exception
	 */
	public void enleverLogementAgent(Long id_agent, Long id_logement) throws Exception {

		PreparedStatement pstmt = null;
		String sql;

		try {
			sql = " DELETE FROM agent" + "WHERE `id_agent`=?" + "AND `id_logement`=? ";
			pstmt = dataSource.getConnection().prepareStatement(sql);
			pstmt.setLong(1, id_agent);
			pstmt.setLong(2, id_logement);
			int result = pstmt.executeUpdate();
			if (result != 1) {
				throw new Exception("Non trouvé dans la base de données !");
			} else {
				System.out.println("Logement supprimé!");
			}
			System.out.println("Result : " + result);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("SQL Error !:" + pstmt.toString(), e);
			throw e;
		} finally {
			pstmt.close();
		}

	}

	private void logSQL(PreparedStatement pstmt) {
		String sql;

		if (pstmt == null)
			return;

		sql = pstmt.toString().substring(pstmt.toString().indexOf(":") + 2);
		log.debug(sql);
	}

	/**
	 * Retrouver l'agent proprietaire d'un logement
	 * 
	 * @param id_agent
	 * @param id_logement
	 * @throws Exception
	 */
	public List<AgentModel> recupererAgentDeLogement(Long id) throws Exception {
		AgentModel agent;
		PreparedStatement pstmt = null;
		ResultSet rs;
		String sql;
		ArrayList<AgentModel> listeAgents = new ArrayList<AgentModel>();

		try {
			// Requete SQL
			sql = " SELECT agent.*"
					+ "FROM agent"
					+ "INNER JOIN agent_logement"
					+ "ON agent.id_agent = agent_logement.id_agent"
					+ "INNER JOIN logement"
					+ "ON agent_logement.logement_id = logement.id"
					+ "WHERE logement.id = ?;";

			pstmt = dataSource.getConnection().prepareStatement(sql);
			pstmt.setLong(1, id);
			// Log info
			logSQL(pstmt);
			// Lancement requete
			rs = pstmt.executeQuery();
			// resultat requete
			while (rs.next()) {
				agent = recupererAgentRS(rs);
				listeAgents.add(agent);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("SQL Error !:" + pstmt.toString(), e);
			throw e;
		} finally {
			pstmt.close();
		}

		return listeAgents;
	}
	
	/**
	 * 
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
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

	/**
	 * 
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
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