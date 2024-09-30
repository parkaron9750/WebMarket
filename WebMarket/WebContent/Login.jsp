<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인</title>
<link rel="stylesheet" href="./resource/css/bootstrap.min.css">
</head>
<body class="d-flex justify-content-center align-items-center min-vh-100">
	<div class="container">
		<div class="row justify-content-center">
			<div class="col-md-4">
				<h2 class="text-center">로그인</h2>
				<form action="Login" method="post">
					<div class="form-group">
						<label for="memberId">아이디</label>
						<input type="text" class="form-control" id="memberId" name="memberId" placeholder="아이디를 입력해주세요." required="required">
					</div>
					
					<div class="form-group">
						<label for="memberPw">비밀번호</label>
						<input type="password" class="form-control" id="memberPw" name="memberPw" placeholder="비밀번호를 입력해주세요." required="required">
					</div>
					
					<button type="submit" class="btn btn-success">로그인</button>
					<a href="RegisterMember.jsp" class="btn btn-primary">회원 가입</a>
				</form>
			</div>
		</div>
	</div>
</body>
</html>