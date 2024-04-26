<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="root" value="${pageContext.request.contextPath}" />
<c:if test="${cookie.ssafy_id.value ne null}">
	<c:set var="idck" value=" checked"/>
	<c:set var="saveid" value="${cookie.ssafy_id.value}"/>
</c:if>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>Nav</title>
</head>
<div class="modal fade" id="join-modal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<h1 class="modal-title fs-5" id="exampleModalLabel">Join EnjoyTrip</h1>
				<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
			</div>
			<div class="modal-body">
				<form id="form-join" method="POST" action="">
					<div class="mb-3">
						<label for="join-name" class="col-form-label">이름:</label>
						<input type="text" class="form-control" id="join-name" name="userName" required />
					</div>
					<div class="mb-3">
						<label for="join-id" class="col-form-label">ID:</label>
						<input type="text" class="form-control" id="join-id" name="userId" required />
					</div>
					<div class="mb-3">
						<label for="join-pw" class="col-form-label">비밀번호:</label>
						<input type="password" class="form-control" id="join-pw" name="userPassword" required />
					</div>
					<div class="mb-3">
						<label for="join-pw-confirm" class="col-form-label">비밀번호 확인:</label>
						<input type="password" class="form-control" id="join-pw-confirm" required />
					</div>
					<div class="mb-3">
						<label for="join-email" class="col-form-label">이메일:</label>
						<input type="text" class="form-control" id="join-email" name="userEmail" required />
					</div>
					<div class="modal-body ms-0 text-danger text-end d-none" id="join-msg">message</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-secondary" data-bs-dismiss="modal">닫기</button>
						<button type="button" class="btn btn-primary" id="join-commit">가입</button>
					</div>
				</form>
			</div>
		</div>
	</div>
</div>
<div class="modal fade" id="login-modal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<h1 class="modal-title fs-5" id="exampleModalLabel">LogIn</h1>
				<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
			</div>
			<div class="modal-body">
				<form id="form-login" method="POST" action="">
					<div class="form-check mb-3 float-end">
		              <input
		                class="form-check-input"
		                type="checkbox"
		                value="ok"
		                id="saveid"
		                name="saveid"
		                ${idck}
		              />
		              <label class="form-check-label" for="saveid"> 아이디저장 </label>
		            </div>
					<div class="mb-3">
						<label for="loginId" class="col-form-label">ID:</label>
						<input type="text" class="form-control" id="loginId" name="loginId" value = "${saveid}" required />
					</div>
					<div class="mb-3">
						<label for="loginPw" class="col-form-label">비밀번호:</label>
						<input type="password" class="form-control" id="loginPw" name="loginPw" required />
					</div>
				</form>
			</div>
			<div class="modal-body ms-0 text-danger text-end d-none" id="login-msg">message</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-secondary" data-bs-dismiss="modal">닫기</button>
				<button type="button" class="btn btn-success" id="find-pw" data-bs-toggle="modal"
					data-bs-target="#find-pw-modal">비밀번호 찾기</button>
				<button type="button" class="btn btn-primary" id="login-commit">로그인</button>
			</div>
		</div>
	</div>
</div>
<div class="modal fade" id="find-pw-modal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<h1 class="modal-title fs-5" id="exampleModalLabel">비밀번호 찾기</h1>
				<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
			</div>
			<div class="modal-body">
				<form id="form-findpw" method="post" action="">
					<div class="mb-3">
						<label for="join-id" class="col-form-label">ID:</label>
						<input type="text" class="form-control" id="find-pw-id" name="userId" required />
					</div>
					<div class="mb-3">
						<label for="join-pw" class="col-form-label">이름:</label>
						<input type="text" class="form-control" id="find-pw-name" name="userName" required />
					</div>
				</form>
			</div>
			<div class="modal-body ms-0 text-danger text-end d-none" id="find-pw-msg">message</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-secondary" data-bs-dismiss="modal">닫기</button>
				<button type="button" class="btn btn-success" id="find-pw-commit">찾기</button>
			</div>
		</div>
	</div>
</div>
<div class="modal fade" id="mypage-modal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<h1 class="modal-title fs-5" id="exampleModalLabel">내 정보</h1>
				<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
			</div>
			<div class="modal-body">
				<form id="form-mypage" method="POST" action="">
					<div class="mb-3">
						<label for="join-id" class="col-form-label">ID:</label>
						<input type="text" class="form-control" id="mypage-id" name="mypage-id" value = "${userinfo.userId}" readonly />
					</div>
					<div class="mb-3">
						<label for="join-name" class="col-form-label">이름:</label>
						<input type="text" class="form-control" id="mypage-name" name="mypage-name" value ="${userinfo.userName}" required />
					</div>
					<div class="mb-3">
						<label for="join-email" class="col-form-label">이메일:</label>
						<input type="text" class="form-control" id="mypage-email" name="mypage-email"  value ="${userinfo.userEmail}" required />
					</div>
				</form>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-warning ms-0" data-bs-toggle="modal"
					data-bs-target="#pw-change-modal">비밀번호 변경</button>
				<button type="button" class="btn btn-secondary" data-bs-dismiss="modal">닫기</button>
				<button type="button" class="btn btn-primary" id="mypage-commit">저장</button>
			</div>
		</div>
	</div>
</div>
<div class="modal fade" id="pw-change-modal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<h1 class="modal-title fs-5" id="exampleModalLabel">비밀번호 변경</h1>
				<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
			</div>
			<div class="modal-body">
				<form id="form-pwchange" method="POST" action="">
					<div class="mb-3">
						<label for="join-id" class="col-form-label">현재 비밀번호:</label>
						<input type="password" class="form-control" id="pw-change-cur-pw" name="orgPw" required />
					</div>
					<div class="mb-3">
						<label for="join-pw" class="col-form-label">새 비밀번호:</label>
						<input type="password" class="form-control" id="pw-change-new-pw" name="newPw" required />
					</div>
					<input type="hidden" class="form-control" id="pw-change-new-id" name="pw-change-id" value = "${userinfo.userId}" required />
				</form>
			</div>
			<div class="modal-body ms-0 text-danger text-end d-none" id="pw-change-msg">message</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-secondary" data-bs-dismiss="modal">닫기</button>
				<button type="button" class="btn btn-primary" id="pw-change-commit">저장</button>
			</div>
		</div>
	</div>
</div>
<div class="modal fade" id="signout-modal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<h1 class="modal-title fs-5" id="exampleModalLabel">SignOut</h1>
				<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
			</div>
			<div class="modal-body">
				<form id="form-signout" method="POST" action="">
					<div class="modal-body ms-0 text-danger text-start p-1" id="login-msg">회원탈퇴를 위해서 비밀번호를 입력하세요</div>
					<div class="mb-3">
						<label for="join-pw" class="col-form-label">비밀번호:</label>
						<input type="password" class="form-control" id="signout-pw" name="signout-pw" required />
					</div>
				</form>
			</div>
			<div class="modal-body ms-0 text-danger text-end d-none" id="signout-msg">message</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-secondary" data-bs-dismiss="modal">닫기</button>
				<button type="button" class="btn btn-primary" id="signout-commit">탈퇴</button>
			</div>
		</div>
	</div>
</div>

<body>
	<!-- ======= Header ======= -->
	<header id="header" class="fixed-top">
		<div class="container d-flex align-items-center">
			<h1 class="logo me-auto"><a href="${root}/">EnjoyTrip</a></h1>
			<!-- <a href="index.html" class="logo me-auto"><img src="./img/logo.png" alt="" class="img-fluid"></a>-->

			<nav id="navbar" class="navbar order-last order-lg-0">
				<ul>
					<li><a href="${root}/board/list">게시판</a></li>

					<li class="dropdown"><a href="#"><span>회원</span> <i class="bi bi-chevron-down"></i></a>
						<ul>
							<c:if test="${empty userinfo }">
								<li>
									<a class="dropdown-item" id="dropdown-join" data-bs-toggle="modal"
										data-bs-target="#join-modal" href="#">회원가입</a>
								</li>
								<li>
									<hr class="dropdown-divider" />
								</li>
								<li>
									<a class="dropdown-item" id="dropdown-login" href="#" data-bs-toggle="modal"
										data-bs-target="#login-modal">로그인</a>
								</li>
							</c:if>
							<c:if test = "${!empty userinfo}">							
								<li>
									<a class="dropdown-item" id="dropdown-mypage" href="#" data-bs-toggle="modal"
										data-bs-target="#mypage-modal">내 정보</a>
								</li>
								<li>
									<a class="dropdown-item" id="dropdown-logout" href="#">로그아웃</a>
								</li>
								<li>
									<a class="dropdown-item" id="dropdown-signout" href="#" data-bs-toggle="modal"
										data-bs-target="#signout-modal">회원탈퇴</a>
								</li>
							</c:if>
						</ul>
					</li>
					<li><a class="active" href="${root}/trip?action=sido">관광 정보 보기</a></li>
					<li><a class="active" href="${root}/tour?action=searchAll">나의 여행지</a></li>
				</ul>
				<i class="bi bi-list mobile-nav-toggle"></i>
			</nav><!-- .navbar -->
			<!-- .navbar -->
		</div>
	</header>
	<script>
	// 회원가입 비밀번호 동일 여부 체크 스크립트
	  let joinPwConfirmElement = document.querySelector("#join-pw-confirm");
	  let joinPwElement = document.querySelector("#join-pw");

	  // 비밀번호 동일 여부 체크
	  const pwCheck = function () {
	    var inputPw = document.querySelector("#join-pw").value;
	    var inputPwConfirm = document.querySelector("#join-pw-confirm").value;

	    let joinMsgElement = document.querySelector("#join-msg");
	    if (inputPw !== inputPwConfirm) {
	      joinMsgElement.innerHTML = "비밀번호가 일치하지 않습니다.";
	      joinMsgElement.classList.remove("d-none");

	      let joinCommitElement = document.querySelector("#join-commit");
	      joinCommitElement.disabled = true;
	    } else {
	      joinMsgElement.classList.add("d-none");

	      let joinCommitElement = document.querySelector("#join-commit");
	      joinCommitElement.disabled = false;
	    }
	  };

	  if(joinPwConfirmElement != null && joinPwElement != null){
	  joinPwConfirmElement.addEventListener("keyup", pwCheck);
	  joinPwElement.addEventListener("keyup", pwCheck);			  
	  }
	  // 비밀번호 동일 여부 체크
	  
	  // 회원가입 모달 스크립트
	  let joinCommitElement = document.querySelector("#join-commit");
	  if(joinCommitElement != null){			  
	  joinCommitElement.addEventListener("click", function () {
        let form = document.querySelector("#form-join");
        form.setAttribute("action", "${root}/member/join");
   	  if(form.checkValidity()){	        	  
        	form.submit();
        }
        else{
      	  alert("빈 칸을 입력하세요");
        }
	  });
	  }
	  // 회원가입 모달 스크립트

	  // 로그인 모달 스크립트
	  let loginCommitElement = document.querySelector("#login-commit");
	  if(loginCommitElement != null){			  
	  loginCommitElement.addEventListener("click", function () {
		  let form = document.querySelector("#form-login");
        form.setAttribute("action", "${root}/member/login");
        if(form.checkValidity()){	        	  
        	form.submit();
        }
        else{
      	  alert("빈 칸을 입력하세요");
        }
	  });
	  }
	  // 로그인 모달 스크립트

	  // 로그아웃 스크립트
	  let logoutCommitElement = document.querySelector("#dropdown-logout");
	  if(logoutCommitElement != null){
	  logoutCommitElement.addEventListener("click", function () {
	    if (window.confirm("로그아웃하시겠습니까?")) {
	    	location.href = "${root}/member/logout";
	    }
	  });
	  }
	  
	  // 로그아웃 모달 스크립트

	  // 내 정보 수정 스크립트
	  let mypageCommitElement = document.querySelector("#mypage-commit");
	  if(mypageCommitElement != null){
	  mypageCommitElement.addEventListener("click", function () {
		  let form = document.querySelector("#form-mypage");
        form.setAttribute("action", "${root}/member/update/info");
        if(form.checkValidity()){	        	  
        	form.submit();
        }
        else{
      	  alert("빈 칸을 입력하세요");
        }
	  });			  
	  }
	  // 내 정보 수정 스크립트

	  // 비밀번호 변경 모달 스크립트
	  let pwChangeCommitElement = document.querySelector("#pw-change-commit");
	  if(pwChangeCommitElement != null){			  
	  pwChangeCommitElement.addEventListener("click", function () {
		  let form = document.querySelector("#form-pwchange");
        form.setAttribute("action", "${root}/member/update/password");
        if(form.checkValidity()){	        	  
        	form.submit();
        }
        else{
      	  alert("빈 칸을 입력하세요");
        }
	  });
	  }
	  // 비밀번호 변경 모달 스크립트
		
	  // 비밀번호 찾기 스크립트
	  let findPwCommitElement = document.querySelector("#find-pw-commit");
	  findPwCommitElement.addEventListener("click", function () {
		  let form = document.querySelector("#form-findpw");
        form.setAttribute("action", "${root}/member/update/password/find");
        if(form.checkValidity()){	        	  
        	form.submit();
        }
        else{
      	  alert("빈 칸을 입력하세요");
        }
	  });
	  // 비밀번호 찾기 스크립트
	  
	  // 회원 탈퇴 모달 스크립트
	  let signoutCommitElement = document.querySelector("#signout-commit");
	  signoutCommitElement.addEventListener("click", function () {
		  let form = document.querySelector("#form-signout");
        form.setAttribute("action", "${root}/member/exit");
        if(form.checkValidity()){	        	  
        	form.submit();
        }
        else{
      	  alert("빈 칸을 입력하세요");
        }
	  });
	  // 회원 탈퇴 모달 스크립트
	</script>
</body>