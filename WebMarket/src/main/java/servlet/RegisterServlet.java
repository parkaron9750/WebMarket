package servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.MemberDAO;
import dto.MemberDTO;

@WebServlet("/Register")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	MemberDAO instance = MemberDAO.getInstance();
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		
		String memberId = request.getParameter("memberId");
		String memberPw = request.getParameter("memberPw");
		String memberName = request.getParameter("memberName");
		String memberBirth = request.getParameter("memberBirth");
		String memberGender = request.getParameter("memberGender");
		String memberEmail = request.getParameter("memberEmail");
		
		MemberDTO member = new MemberDTO(memberId, memberPw, memberName, memberBirth, memberGender, memberEmail);
		
		try {
			instance.registerMember(member);
			request.setAttribute("message", "회원가입이 성공적으로 완료되었습니다.");
			request.getRequestDispatcher("ProductList.jsp").forward(request, response);
		} catch (SQLException e) {
			e.printStackTrace();
			request.setAttribute("message", "회원가입 중 오류가 발생하였습니다.");
			request.getRequestDispatcher("RegisterMember.jsp").forward(request, response);
		}
	}
}


