<%@page import="org.apache.catalina.ssi.SSICommand"%>
<%@page import="dao.ProductDAO"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="dto.ProductDTO"%>
<%@page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%	request.setCharacterEncoding("utf-8"); %>    
<!DOCTYPE html>
<html>
<head><link rel="stylesheet" href="./resource/css/bootstrap.min.css">
<meta charset="UTF-8">
<title>상품 목록</title>
</head>
<body>
	<jsp:include page="Nav.jsp"/>
	<div class="jumbotron">
		<h1>상품 목록</h1>
	</div>
	<nav class="navbar">
	  	<form class="form-inline ml-auto" action="Search" method="get">
	  		<input type= "hidden" name="action" value = "search">
	  		<select id="productTag" name="productTag" class="form-control">
                <option value="name">상품명</option>
                <option value="company">제조사</option>
                <option value="tag">분 류</option>
            </select>
	    	<input class="form-control mr-sm-2" type="search" name="searchText" placeholder="검색어를 입력해주세요." aria-label="Search">
	    	<button class="btn btn-outline-success my-2 my-sm-0" type="submit">검색</button>
	 	 </form>
	</nav>
	<br>
	<%
		ProductDAO instance = ProductDAO.getInstance();
		String message = (String)request.getAttribute("message");
		if(message != null){
	%>
		<script type="text/javascript">
			alert('<%=message %>');
		</script>
	<%	
		session.removeAttribute("message");
		}
	%>
	<%
		int pageSize = 9; //한페이지에 상품 갯수
		int pageCount = 1; //현재 페이지
		String pagIng = request.getParameter("page");
		
		if(pagIng != null){
			pageCount = Integer.parseInt(pagIng);
		}
		
		int offset = (pageCount - 1) * pageSize;
		List<ProductDTO> pageList = instance.getPagIng(offset, pageSize);
		
		int totalProduct = instance.getTotalProduct();
		int totalPage = (int)Math.ceil((double) totalProduct / pageSize);
	%>
	<div class="container">	
        <div class="row" align="center">
        	<%

        		if(pageList != null && !pageList.isEmpty()){
        			for(ProductDTO item : pageList){
     
        	%>
		        		<div class="col-md-4">
		        			<img src="./resource/image/<%=item.getProductImage() %>" style="width:50%" >
		        			<h3><%=item.getProductName() %></h3>
		        			<p><%=item.getProductInfo() %>    	
		        			<p><%=item.getProductPrice() %>원</p>
		        			
		        			<p><a href="ProductInfo.jsp?productId=<%=item.getProductId() %>" class="btn btn-secondary">상세 정보&raquo;</a></p>
		        		</div>
        	<%		
        			}
        		} else {
        	%>
        		<p>상품이 존재하지 않습니다.<p>
        		<a href="ProductADD.jsp" class="btn btn-secondary">상품 추가&raquo;</a>	
        	<%
        		}
        		
        	%>	
        </div>
	</div>
	
	<nav aria-label="Page navigation">
	    <ul class="pagination justify-content-center">
	        <li class="page-item <%= (pageCount == 1) ? "disabled" : "" %>">
	            <a class="page-link" href="?page=<%= pageCount - 1 %>" aria-label="Previous">
	                <span aria-hidden="true">&laquo;</span>
	                <span class="sr-only">Previous</span>
	            </a>
	        </li>
	        <% for (int i = 1; i <= totalPage; i++) { %>
	            <li class="page-item <%= (i == pageCount) ? "active" : "" %>">
	                <a class="page-link" href="?page=<%= i %>"><%= i %></a>
	            </li>
	        <% } %>
	        <li class="page-item <%= (pageCount == totalPage) ? "disabled" : "" %>">
	            <a class="page-link" href="?page=<%= pageCount + 1 %>" aria-label="Next">
	                <span aria-hidden="true">&raquo;</span>
	                <span class="sr-only">Next</span>
	            </a>
	        </li>
	    </ul>
	</nav>

</body>
</html>
 