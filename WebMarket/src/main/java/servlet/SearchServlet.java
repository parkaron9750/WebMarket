package servlet;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.ProductDAO;
import dto.ProductDTO;

@WebServlet("/Search")
public class SearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	ProductDAO instance = ProductDAO.getInstance();
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("UTF-8");
		String action = request.getParameter("action");
		
		try {
			switch(action) {
				case "search" :
					searchProduct(request, response);
					break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 데이터베이스에서 상품 검색 관련
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void searchProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String productTag = request.getParameter("productTag"); // 분류
		String searchText = request.getParameter("searchText"); // 텍스트에서 입력한 값 검색
		
		try {
			List<ProductDTO> searchList = instance.searchProduct(productTag, searchText);
			request.setAttribute("searchList", searchList); // 검색해서 나온 값을 저장
			request.getRequestDispatcher("ProductSearch.jsp").forward(request, response);
		} catch (SQLException e) {
			e.printStackTrace();
			request.setAttribute("message", "검색 제품이 존재하지 않습니다.");
			request.getRequestDispatcher("ProductList.jsp").forward(request, response);
		}
	}
}

