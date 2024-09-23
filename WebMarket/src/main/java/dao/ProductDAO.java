package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import db.DB;
import dto.ProductDTO;

public class ProductDAO {
	private ProductDAO() {}
	private static ProductDAO instance = new ProductDAO();
	
	public static ProductDAO getInstance() {
		return instance;
	}
	
	
	/**
	 * 데이터베이스에서 상품 목록 가져오기
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<ProductDTO> getAllProduct() throws SQLException{
		String listSql = "select * from productList";
		ArrayList<ProductDTO> productlist = new ArrayList<ProductDTO>();

		try(Connection connection = DB.getConnection();
			PreparedStatement statement = connection.prepareStatement(listSql);	
			ResultSet resultSet = statement.executeQuery()){
			
			while(resultSet.next()) {
				String productId = resultSet.getString("productid");
				String productName = resultSet.getString("productname");
				int productPrice = resultSet.getInt("productprice");
				String productInfo = resultSet.getString("productinfo");
				String productCompany = resultSet.getString("productcompany");
				String productTag = resultSet.getString("producttag");
				int productStock = resultSet.getInt("productstock");
				String productImage = resultSet.getString("productimage");
				
				ProductDTO item = new ProductDTO(productId, productName, productPrice, productInfo, productCompany, productTag, productStock, productImage);
				productlist.add(item);
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return productlist;
	}
	/**
	 * 데이터베이스에 상품 추가 하는 코드
	 * @param product
	 * @throws SQLException
	 */
	public void createProduct(ProductDTO product) throws SQLException {
		String createSql = "insert into productList (productid, productname, productprice, productinfo, productcompany, producttag, productstock, productimage) values (?, ?, ?, ?, ?, ?, ?, ?)";
	
		try(Connection connection = DB.getConnection();
			PreparedStatement statement = connection.prepareStatement(createSql)){
				statement.setString(1, product.getProductId());
				statement.setString(2, product.getProductName());
				statement.setInt(3, product.getProductPrice());
				statement.setString(4, product.getProductInfo());
				statement.setString(5, product.getProductCompany());
				statement.setString(6, product.getProductTag());
				statement.setInt(7, product.getProductStock());
				statement.setString(8, product.getProductImage());
				
				statement.executeUpdate();
				
		}catch (SQLException e) {
			e.printStackTrace();
			
		}
	}
	/**
	 * 상품 특정해서 가져오는 코드
	 * @param productId
	 * @return
	 * @throws SQLException
	 */
	public ProductDTO readProduct(String productId) throws SQLException {
		String readSql = "select * from productList where productid = ?";
		ProductDTO product = null;
		
		try(Connection connection = DB.getConnection();
			PreparedStatement statement = connection.prepareStatement(readSql)){
			
			statement.setString(1, productId);
			try(ResultSet resultSet = statement.executeQuery()){
				if(resultSet.next()) {
					String productName = resultSet.getString("productname");
					int productPrice = resultSet.getInt("productprice");
					String productInfo = resultSet.getString("productinfo");
					String productCompany = resultSet.getString("productcompany");
					String productTag = resultSet.getString("producttag");
					int productStock = resultSet.getInt("productstock");
					String productImage = resultSet.getString("productimage");
					
					product = new ProductDTO(productId, productName, productPrice, productInfo, productCompany, productTag, productStock, productImage);
				}
			}catch (SQLException e) {
				e.printStackTrace();
			}
		}catch (SQLException e1) {
			e1.printStackTrace();
		}
		return product;
	}
	
	/**
	 * 상품 정보를 수정하는 코드
	 * @param product
	 * @throws SQLException
	 */
	public void updateProduct(ProductDTO product) throws SQLException {
		String updateSql = "update productList set productname = ?, productprice = ?, productinfo = ?, productcompany = ?, producttag = ?, productstock = ? where productid = ?";
	
		try(Connection connection = DB.getConnection();
			PreparedStatement statement = connection.prepareStatement(updateSql)) {
			
			statement.setString(1, product.getProductName());
			statement.setInt(2, product.getProductPrice());
			statement.setString(3, product.getProductInfo());
			statement.setString(4, product.getProductCompany());
			statement.setString(5, product.getProductTag());
			statement.setInt(6, product.getProductStock());
			statement.setString(7, product.getProductImage());
			statement.setString(8, product.getProductId());
			
			statement.executeUpdate();
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 특정 상품을 데이터베이스에서 삭제하는 코드
	 * @param productId
	 * @throws SQLException
	 */
	public void removeProduct(String productId) throws SQLException {
		String deleteSql = "delete from productList where productid = ?";
		
		try(Connection connection = DB.getConnection();
			PreparedStatement statement = connection.prepareStatement(deleteSql)){
				
				statement.setString(1, productId);
				statement.executeUpdate();
				
			
		}catch (SQLException e) {
			e.printStackTrace();
			
		}
	}
	/**
	 * 결제한 후 재고수 변화 관련 코드 
	 * @param ProductId
	 * @param Quantity
	 * @throws SQLException
	 */
	public void updateStock(String ProductId, int Quantity) throws SQLException {
		String stockSql = "update productList set productstock = productstock - ? where productid = ?";
		
		try(Connection connection = DB.getConnection();
			PreparedStatement statement = connection.prepareStatement(stockSql)){
				
				statement.setInt(1, Quantity);
				statement.setString(2, ProductId);
				
				statement.executeUpdate();
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public List<ProductDTO> searchProduct(String productTag, String searchText) throws SQLException{
		List<ProductDTO> searchList = new ArrayList<ProductDTO>();		
		switch(productTag) {
			case "name" :
				productTag = "productname";
				break;
			
			case "company" :
				productTag = "productcompany";
				break;
			
			case "tag" :
				productTag = "producttag";
				break;
		}
		
		String searchSql = "select * from productList where " + productTag + " like ?";
		try(Connection connection = DB.getConnection();
			PreparedStatement statement = connection.prepareStatement(searchSql)){
				statement.setString(1, "%" + searchText + "%");
			
			try(ResultSet resultSet = statement.executeQuery()){
				while(resultSet.next()){
					ProductDTO item = new ProductDTO();
					item.setProductId(resultSet.getString("productid"));
					item.setProductName(resultSet.getString("productname"));
					item.setProductPrice(resultSet.getInt("productprice"));
					item.setProductInfo(resultSet.getString("productinfo"));
					item.setProductCompany(resultSet.getString("productcompany"));
					item.setProductTag(resultSet.getString("producttag"));
					item.setProductStock(resultSet.getInt("productstock"));
					item.setProductImage(resultSet.getString("productimage"));
					searchList.add(item);
				} 
			}catch (SQLException e) {
				e.printStackTrace();
			}
		}catch (SQLException e1) {
			e1.printStackTrace();
		}
		return searchList;
	}
	/**
	 * 검색 된 제품의 목록들을 페이징 해주는 코드
	 * @param start
	 * @param end
	 * @return
	 * @throws SQLException
	 */
	public List<ProductDTO> getPagIng(int start, int end) throws SQLException{
		List<ProductDTO> products = new ArrayList<ProductDTO>();
		String pagIngSql ="select * from productList limit ? offset ?";
		
		try(Connection connection = DB.getConnection();
			PreparedStatement statement = connection.prepareStatement(pagIngSql)){
				statement.setInt(1, end); // 보여줄 제품의 갯수 끝
				statement.setInt(2, start); // 보여줄 제품의 갯수의 시작
				
			try(ResultSet resultSet = statement.executeQuery()){
				while(resultSet.next()) {
					ProductDTO item = new ProductDTO();
					item.setProductId(resultSet.getString("productid"));
					item.setProductName(resultSet.getString("productname"));
					item.setProductPrice(resultSet.getInt("productprice"));
					item.setProductInfo(resultSet.getString("productinfo"));
					item.setProductCompany(resultSet.getString("productcompany"));
					item.setProductTag(resultSet.getString("producttag"));
					item.setProductStock(resultSet.getInt("productstock"));
					item.setProductImage(resultSet.getString("productimage"));
					products.add(item);
				}
			}catch (SQLException e) {
				e.printStackTrace();
			}
		}catch (SQLException e1) {
			e1.printStackTrace();
		}
		return products;
	}
	
	/**
	 * 데이터베이스에서 총 제품 갯수를 구하는 코드
	 * @return
	 * @throws SQLException
	 */
	public int getTotalProduct() throws SQLException {
		String totalSql = "select count(*) from productList"; 
		int total = 0;
		
		try(Connection connection = DB.getConnection();
			PreparedStatement statement = connection.prepareStatement(totalSql);
			ResultSet resultSet = statement.executeQuery()){
			
			if(resultSet.next()) {
				total = resultSet.getInt(1);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return total;
	}
}

