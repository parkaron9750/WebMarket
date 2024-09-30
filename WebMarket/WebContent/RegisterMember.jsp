<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="./resource/css/bootstrap.min.css">
<title>회원 가입</title>
</head>
<body>
	<div class="container">
		<div class="row justify-content-center">
			<div class="col-md-6">
				<div class="card mt-5">
					<div class="card-header text-center">
						<h3>회원 가입</h3>
					</div>
					<div class="card-body">
						<form action="Register" method="post">
							
							<div class="form-group">
								<label for="memberId">아이디</label>
								<input type="text" class="form-control" id="memberId" name="memberId" placeholder="영문과 숫자를 조합하여 아이디를 입력해주세요.">				
							</div>
							
							<div class="form-group">
								<label for="memberPw">비밀번호</label>
								<input type="password" class="form-control" id="memberPw" name="memberPw" placeholder="영문과 숫자를 조합하여 비밀번호를 입력해주세요.">
							</div>
							
							<div class="form-group">
								<label for="memberPw">비밀번호 확인</label>
								<input type="password" class="form-control" id="memberPw" name="memberPw" placeholder="비밀번호를 다시 입력해주세요.">
							</div>
							
							<div class="form-group">
								<label for="memberName">이름</label>
								<input type="text" class="form-control" id="memberName" name="memberName" placeholder="이름을 입력해주세요.">				
							</div>
							
							<div class="form-group">
								<label for="memberBirth">생년월일</label>
								<input type="text" class="form-control" id="memberBirth" name="memberBirth" placeholder="ex) 19950222">				
							</div>
							
							<div class="form-group">
								<label for="memberId">성별</label>
								<select class="form-control" id="memberGender" name="memberGender">
									<option value ="man">남성</option>
									<option value = "woman">여성</option>
								</select>				
							</div>
							
							<div class="form-group">
								<label for="memberEamil">이메일</label>
								<input type="email" class="form-control" id="memberEmail" name="memberEmail" placeholder="ex) 19950222">		
							</div>
							
							<button type="submit" class="btn btn-success btn-block">회원가입</button>
							<a href="MainPage.jsp" class="btn btn-danger btn-block" role="button">가입취소</a>
							
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>