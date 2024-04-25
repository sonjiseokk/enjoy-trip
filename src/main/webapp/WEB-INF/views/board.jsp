<%--<%@page import="org.json.JSONObject" %>--%>
<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%--<%@ page import="com.ssafy.enjoytrip.trip.SidoDto" %>--%>
<%--<%@ page import="com.ssafy.enjoytrip.trip.GugunDto" %>--%>
<%--<%@ page import="java.util.List" %>--%>
<%--<%@ page import="org.json.JSONObject" %>--%>
<c:set var="root" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8"/>
    <meta content="width=device-width, initial-scale=1.0" name="viewport"/>

    <title>Contact - Mentor Bootstrap Template</title>
    <meta content="" name="description"/>
    <meta content="" name="keywords"/>

    <!-- Favicons -->
    <link href="../../img/favicon.png" rel="icon"/>
    <link href="../../img/apple-touch-icon.png" rel="apple-touch-icon"/>

    <!-- Google Fonts -->
    <link
            href="https://fonts.googleapis.com/css?family=Open+Sans:300,300i,400,400i,600,600i,700,700i|Raleway:300,300i,400,400i,500,500i,600,600i,700,700i|Poppins:300,300i,400,400i,500,500i,600,600i,700,700i"
            rel="stylesheet"/>

    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js"
            integrity="sha384-I7E8VVD/ismYTF4hNIPjVp/Zjvgyol6VFvRkX/vR+Vc4jQkC+hVqc2pM8ODewa9r"
            crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.min.js"
            integrity="sha384-0pUGZvbkm6XF6gxjEnlmuGrJXVbNuzT9qBBavbLwCsOGabYfZo0T0to5eqruptLy"
            crossorigin="anonymous"></script>

    <!-- Vendor CSS Files -->
    <link href="../../vendor/animate.css/animate.min.css" rel="stylesheet"/>
    <link href="../../vendor/aos/aos.css" rel="stylesheet"/>
    <link href="../../vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet"/>
    <link href="../../vendor/bootstrap-icons/bootstrap-icons.css" rel="stylesheet"/>
    <link href="../../vendor/boxicons/css/boxicons.min.css" rel="stylesheet"/>
    <link href="../../vendor/remixicon/remixicon.css" rel="stylesheet"/>
    <link href="../../vendor/swiper/swiper-bundle.min.css" rel="stylesheet"/>

    <!-- Template Main CSS File -->
    <link href="../../css/style.css" rel="stylesheet"/>

    <!-- =======================================================
      * Template Name: Mentor
      * Updated: Mar 13 2024 with Bootstrap v5.3.3
      * Template URL: https://bootstrapmade.com/mentor-free-education-bootstrap-theme/
      * Author: BootstrapMade.com
      * License: https://bootstrapmade.com/license/
      ======================================================== -->

    <style>
        ::-webkit-scrollbar {
            width: 10px;
        }

        ::-webkit-scrollbar-thumb {
            background-color: #2f3542;
            border-radius: 10px;
        }

        ::-webkit-scrollbar-track {
            background-color: grey;
            border-radius: 10px;
            box-shadow: inset 0px 0px 5px white;
        }

        .table tr td:first-child {
            width: 1em;
        }

        .table tr td:nth-child(2), .table tr td:nth-child(3) {
            width: 2em;
        }

        .table tr td:last-child {
            width: 1em;
        }
    </style>


    <script type="text/javascript"
            src="//dapi.kakao.com/v2/maps/sdk.js?appkey=3c9544e401e78560632eaaf871996656"></script>
</head>


<jsp:include page="nav/nav.jsp"/>

<main id="main">
    <!-- ======= Breadcrumbs ======= -->
    <!-- End Breadcrumbs -->

    <!-- ======= Contact Section ======= -->
    <section id="contact" class="contact">
        <div class="container" data-aos="fade-up" style="margin-top: 80px">
            <div class="row mt-5">
                <div class="col-lg-12"></div>
            </div>
        </div>

        <div class="container mt-10">
            <div class="row " data-aos="fade-up">
                <div class="col-md-3">
                    <h1>유저 게시판</h1>
                </div>

                <div class="col-md-6">
                    <!-- 검색 폼 -->
                    <form action="${root}/board" method="get">
                        <div class="input-group">
                            <input type="hidden" name="action" value="search"/>
                            <input type="text" class="form-control" placeholder="검색어 입력"
                                   name="keyword">
                            <button class="btn btn-outline-secondary" type="submit">검색</button>
                        </div>
                    </form>


                </div>
                <div class="col-md-3" style="display: flex; justify-content: right">
                    <!-- 세션에 따라 보여야 할듯 -->
                    <c:if test="${not empty sessionScope.userinfo}">
                        <button class="btn btn-outline-success" id="mvregister"
                                value="글등록">글등록
                        </button>
                    </c:if>
                </div>
            </div>
            <div class="row" data-aos="fade-up">
                <div class="breadcrumbs col-md-12" data-aos="fade-up"
                     style="display: inline; margin-top: 0px; background-color: white">

                    <table class="table" style="text-align: center">
                        <thead>
                        <tr class="table-success">
                            <td>글번호
                                </th>
                            <td>제목
                                </th>
                            <td>내용
                                </th>
                        </tr>
                        </thead>

                        <tbody>
                        <c:if test="${not empty boardList}">
                            <c:forEach var="board" items="${boardList}">
                                <tr>
                                    <td>${board.boardId}</td>
                                    <td>${board.subject}</td>
                                    <td>${board.content}</td>
                                </tr>
                            </c:forEach>
                        </c:if>
                        <c:if test="${empty boardList}">
                            <tr>
                                <td colspan="3">아직 글이 없습니다.</td>
                            </tr>
                        </c:if>
                        </tbody>

                    </table>

                </div>
            </div>
        </div>

    </section>
    <!-- End Contact Section -->
</main>
<!-- End #main -->

<!-- ======= Footer ======= -->
<footer id="footer">
    <div class="container d-md-flex py-4">
        <div class="me-md-auto text-center text-md-start">
            <div class="copyright">
                &copy; Copyright <strong><span>Mentor</span></strong>. All Rights
                Reserved
            </div>
            <div class="credits">
                <!-- All the links in the footer should remain intact. -->
                <!-- You can delete the links only if you purchased the pro version. -->
                <!-- Licensing information: https://bootstrapmade.com/license/ -->
                <!-- Purchase the pro version with working PHP/AJAX contact form: https://bootstrapmade.com/mentor-free-education-bootstrap-theme/ -->
                Designed by <a href="https://bootstrapmade.com/">BootstrapMade</a>
            </div>
        </div>
        <div class="social-links text-center text-md-right pt-3 pt-md-0">
            <a href="#" class="twitter"><i class="bx bxl-twitter"></i></a> <a
                href="#" class="facebook"><i class="bx bxl-facebook"></i></a> <a
                href="#" class="instagram"><i class="bx bxl-instagram"></i></a> <a
                href="#" class="google-plus"><i class="bx bxl-skype"></i></a> <a
                href="#" class="linkedin"><i class="bx bxl-linkedin"></i></a>
        </div>
    </div>
</footer>
<!-- End Footer -->

<div id="preloader"></div>
<a href="#"
   class="back-to-top d-flex align-items-center justify-content-center"><i
        class="bi bi-arrow-up-short"></i></a>


<!-- Template Main JS File -->
<script src="../../js/main.js"></script>

<script>
    document.addEventListener('DOMContentLoaded', function () {
        document.getElementById('mvregister').addEventListener('click',
            function () {
                location.href = "${root}/boardDto?action=mvregister";
            });
    });
</script>
<!-- Vendor JS Files -->
<script src="../../vendor/purecounter/purecounter_vanilla.js"></script>
<script src="../../vendor/aos/aos.js"></script>
<script src="../../vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
<script src="../../vendor/swiper/swiper-bundle.min.js"></script>
<script src="../../vendor/php-email-form/validate.js"></script>

<!-- Template Main JS File -->
<script src="../../js/main.js"></script>
</body>
</html>
