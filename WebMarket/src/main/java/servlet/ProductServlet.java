package servlet;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.ProductDAO;
import dto.CartDTO;
import dto.ProductDTO;

@WebServlet("/Product")
public class ProductServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	ProductDAO instance = ProductDAO.getInstance();
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
		request.setCharacterEncoding("utf-8");
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
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		String action = request.getParameter("action");
		
		try {			
			switch(action) {
				//데이터베이스에 추가
				case "create" :
					createProduct(request, response);
					break;
				//데이터베이스 수정
				case "update" :
					updateProduct(request, response);
					break;
				//데이터베이스에서 삭제
				case "remove" :
					removeProduct(request, response);
					break;
				//장바구니에 상품 추가
				case "order" :
					orderProduct(request, response);
					break;
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 상품 등록하는 코드
	 * @param request
	 * @param response
	 * @throws SQLException
	 * @throws IOException
	 * @throws ServletException 
	 */
	private void createProduct(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
		HttpSession session = request.getSession();
		
		String productId = request.getParameter("productId");
		String productName = request.getParameter("productName");
		int productPrice = Integer.parseInt(request.getParameter("productPrice"));
		String productInfo = request.getParameter("productInfo");
		String productCompany = request.getParameter("productCompany");
		String productTag = request.getParameter("productTag");
		int productStock = Integer.parseInt(request.getParameter("productStock"));
		ProductDTO product = new ProductDTO(productId, productName, productPrice, productInfo, productCompany, productTag, productStock);
		
		try {			
			if(product != null) {
				instance.createProduct(product);
				session.setAttribute("message", "상품이 등록되었습니다.");
			}
		}catch (SQLException e) {
			e.printStackTrace();
			session.setAttribute("message", "상품을 등록하는데 실패하였습니다.");
			request.getRequestDispatcher("ProductList.jsp").forward(request, response);
		}
		request.getRequestDispatcher("ProductList.jsp").forward(request, response);
	}
	/**
	 * 데이터베이스 상품 수정하는 코드
	 * @param request
	 * @param response
	 * @throws SQLException
	 * @throws IOException
	 * @throws ServletException 
	 */
	private void updateProduct(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
		HttpSession session = request.getSession();
		
		String productId = request.getParameter("productId");
		String productName = request.getParameter("productName");
		int productPrice = Integer.parseInt(request.getParameter("productPrice"));
		String productInfo = request.getParameter("productInfo");
		String productCompany = request.getParameter("productCompany");
		String productTag = request.getParameter("productTag");
		int productStock = Integer.parseInt(request.getParameter("productStock"));
		
		try {
			ProductDTO product = new ProductDTO(productId, productName, productPrice, productInfo, productCompany, productTag, productStock);
			if(product != null) {
				session.setAttribute("message", "상품이 수정되었습니다.");
				instance.updateProduct(product);
			}		
		}catch (SQLException e) {
			e.printStackTrace();
			session.setAttribute("message", "상품이 존재하지 않습니다.");
			request.getRequestDispatcher("ProductList.jsp").forward(request, response);
		}
		request.getRequestDispatcher("ProductList.jsp").forward(request, response);
	}
	/**
	 * 데이터베이스에서 상품 삭제하는 코드
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws ServletException 
	 */
	private void removeProduct(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		HttpSession session = request.getSession();
		String productId = request.getParameter("productId");
		
		try {
			instance.removeProduct(productId);
			session.setAttribute("message", "상품이 삭제되었습니다.");
			request.getRequestDispatcher("ProductList.jsp").forward(request, response);
			
		}catch (SQLException e) {
			e.printStackTrace();
			session.setAttribute("message", "상품을 삭제하는데 오류가 발생하였습니다.");
			request.getRequestDispatcher("ProductList.jsp").forward(request, response);
		}
	}
	
	/**
	 * 상품을 장바구니에 추가하는 코드
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws ServletException 
	 */
	private void orderProduct(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		HttpSession session = request.getSession();
		ArrayList<CartDTO> cart = (ArrayList<CartDTO>)session.getAttribute("cart");
		String productId = request.getParameter("productId");
		int quantity = Integer.parseInt(request.getParameter("Quantity"));
	
		try {
			ProductDTO product = instance.readProduct(productId);
			if(cart == null) {
				cart = new ArrayList<CartDTO>();
			}
	
			if(product != null) {	
				boolean productExists = false;
				for(CartDTO item : cart) {
					if(item.getProductId().equals(productId)) {
						item.setCartQuantity(item.getCartQuantity() + quantity);
						productExists = true;
						break;
					}
				}
				
				if(!productExists) {					
					CartDTO cartItem = new CartDTO(productId, product.getProductName(), product.getProductPrice(), quantity);
					cart.add(cartItem);
				}
				session.setAttribute("cart", cart);
				session.setAttribute("message", "장바구니에 상품이 추가되었습니다.");
				request.getRequestDispatcher("ProductCart.jsp").forward(request, response);
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
