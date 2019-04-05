package kr.ac.hansung.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import kr.ac.hansung.model.Participation;

@Repository
public class ParticipationDao {

	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public void setDataSource(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	public boolean addParticipation(Participation participation) {
		String comment = participation.getComment();

		String sqlStatement = "insert into participation (comment)"
				            + "values (?)";

		return (jdbcTemplate.update(sqlStatement, new Object[] { comment }) == 1);
	}
	
	public List<Participation> getParticipationlist() {
		String sqlStatement = "select * from participation";

		return jdbcTemplate.query(sqlStatement, new RowMapper<Participation>() { // record 형태로 넘어오는 데이터를 object 형태로 매핑

			@Override
			public Participation mapRow(ResultSet rs, int rowNum) throws SQLException {

				Participation participation = new Participation();

				participation.setId(rs.getInt("id"));
				participation.setComment(rs.getString("comment"));

				return participation;
			}

		});
	}
	
}
