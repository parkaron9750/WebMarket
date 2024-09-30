package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import db.DB;
import dto.MemberDTO;

public class MemberDAO {
	private MemberDAO() {}
	
	private static MemberDAO M_instance = new MemberDAO();
	
	public static MemberDAO getInstance() {
		return M_instance;
	}
	
	public void registerMember(MemberDTO member) throws SQLException {
		String registerSql = "{call registerMember(?, ?, ?, ?, ?, ?)}";
		
		try(Connection connection = DB.getConnection();
			PreparedStatement statement = connection.prepareStatement(registerSql)){
			
			statement.setString(1, member.getMemberId());
			statement.setString(2, member.getMemberPw());
			statement.setString(3, member.getMemberName());
			statement.setString(4, member.getMemberBirth());
			statement.setString(5, member.getMemberGender());
			statement.setString(6, member.getMemberEmail());
			
			statement.execute();
		}
	}
}
