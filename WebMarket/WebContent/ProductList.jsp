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
	  	<form class="form-inline ml-auto" action="Product">
	  		<select id="productTag" name="productTag" class="form-control">
                <option value="name">상품명</option>
                <option value="company">제조사</option>
                <option value="tag">분 류</option>
            </select>
	    	<input class="form-control mr-sm-2" type="search" name="searchText" placeholder="검색어를 입력해주세요." aria-label="Search">
	  		<input type= "hidden" name="action" value = "search">
	    	<button class="btn btn-outline-success my-2 my-sm-0" type="submit">검색</button>
	 	 </form>
	</nav>
	<br>
	<%
		String message = (String)session.getAttribute("message");
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
	 	ProductDAO instance = ProductDAO.getInstance();
		List<ProductDTO> productlist = instance.getAllProduct();
	%>
	<div class="container">	
        <div class="row" align="center">
        	<%

        		if(productlist != null && !productlist.isEmpty()){
        			for(ProductDTO item : productlist){
     
        	%>
		        		<div class="col-md-4">
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
        		session.setAttribute("productlist", productlist);
        	%>	
        </div>
	</div>

</body>
</html>
