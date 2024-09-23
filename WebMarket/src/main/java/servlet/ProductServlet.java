package servlet;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import dao.ProductDAO;
import dto.CartDTO;
import dto.ProductDTO;

@WebServlet("/Product")
public class ProductServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	ProductDAO instance = ProductDAO.getInstance();
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		
		String action = request.getParameter("action");
		
		
		try {			
			switch(action) {
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
