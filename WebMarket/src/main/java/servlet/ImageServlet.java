package servlet;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import dao.ProductDAO;
import dto.ProductDTO;

@WebServlet("/Image")
public class ImageServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	ProductDAO instance = ProductDAO.getInstance();
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		

		String uploadPath = request.getServletContext().getRealPath("/resource/image");
		int imageSize = 10 * 1024 * 1024;
		String encType = "UTF-8";

		File mkdir = new File(uploadPath);
		if(!mkdir.exists()) mkdir.mkdirs();
		
		MultipartRequest multi = new MultipartRequest(request, uploadPath, imageSize, encType, new DefaultFileRenamePolicy()); 

		String action = multi.getParameter("action");
		
		try {
			switch(action) {
				case "create" :
					createProduct(multi, request, response);
					break;
					
				case "update" :
					updateProduct(multi, request, response);
					break;
			}
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 상품 이미지 및 상품 등록 관련 코드
	 * @param multi
	 * @param request
	 * @param response
	 * @throws SQLException
	 * @throws IOException
	 * @throws ServletException
	 */
	private void createProduct(MultipartRequest multi, HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
		HttpSession session = request.getSession();
		
		String productId = multi.getParameter("productId");
		String productName = multi.getParameter("productName");
		int productPrice = Integer.parseInt(multi.getParameter("productPrice"));
		String productInfo = multi.getParameter("productInfo");
		String productCompany = multi.getParameter("productCompany");
		String productTag = multi.getParameter("productTag");
		int productStock = Integer.parseInt(multi.getParameter("productStock"));
		
		Enumeration files = multi.getFileNames(); //모든 파일의 필드의 이름을 반환
		String fileName = (String)files.nextElement(); //파일 이름을 반환
		String productImage = multi.getFilesystemName(fileName); //저장 된 이미지 파일 이름을 가져온다.
		
		ProductDTO product = new ProductDTO(productId, productName, productPrice, productInfo, productCompany, productTag, productStock, productImage);
		
		try {			
			if(product != null) {
				instance.createProduct(product);
				session.setAttribute("message", "상품이 등록되었습니다.");
			}
		}catch (SQLException e) {
			e.printStackTrace();
			session.setAttribute("message", "상품을 등록하는데 실패하였습니다.");
			if(!response.isCommitted())
				request.getRequestDispatcher("ProductList.jsp").forward(request, response);
		}
		if(!response.isCommitted())
			request.getRequestDispatcher("ProductList.jsp").forward(request, response);
	}
	/**
	 * 상품 수정 관련 코드
	 * @param multi
	 * @param request
	 * @param response
	 * @throws SQLException
	 * @throws IOException
	 * @throws ServletException
	 */
	private void updateProduct(MultipartRequest multi, HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
		HttpSession session = request.getSession();
		
		String productId = multi.getParameter("productId");
		String productName = multi.getParameter("productName");
		int productPrice = Integer.parseInt(multi.getParameter("productPrice"));
		String productInfo = multi.getParameter("productInfo");
		String productCompany = multi.getParameter("productCompany");
		String productTag = multi.getParameter("productTag");
		int productStock = Integer.parseInt(multi.getParameter("productStock"));
		
		Enumeration files = multi.getFileNames(); //모든 파일 이름을 가져온다.
		String fileName = (String)files.nextElement();// 파일 이름을 반환한다.
		String productImage = multi.getFilesystemName(fileName);//저장된 이미지 파일을 가져온다.
		
		try {
			ProductDTO product = new ProductDTO(productId, productName, productPrice, productInfo, productCompany, productTag, productStock, productImage);
			if(product != null) {
				instance.updateProduct(product);
				request.setAttribute("product", product);
				session.setAttribute("message", "상품이 수정되었습니다.");
			}		
		}catch (SQLException e) {
			e.printStackTrace();
			session.setAttribute("message", "상품이 존재하지 않습니다.");
			request.getRequestDispatcher("ProductList.jsp").forward(request, response);
		}
	
		request.getRequestDispatcher("ProductList.jsp").forward(request, response);
	}
}

