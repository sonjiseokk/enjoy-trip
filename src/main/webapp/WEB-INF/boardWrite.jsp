<%@page import="org.json.JSONObject" %>
<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page import="com.ssafy.enjoytrip.trip.SidoDto" %>
<%@ page import="com.ssafy.enjoytrip.trip.GugunDto" %>
<%@ page import="java.util.List" %>
<%@ page import="org.json.JSONObject" %>
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
    <link href="img/favicon.png" rel="icon"/>
    <link href="img/apple-touch-icon.png" rel="apple-touch-icon"/>

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

        .centered-content {
            display: flex;
            justify-content: center;
            align-items: center;
        }

    </style>


    <script type="text/javascript"
            src="//dapi.kakao.com/v2/maps/sdk.js?appkey=3c9544e401e78560632eaaf871996656"></script>
</head>


<body>
<jsp:include page="nav/nav.jsp"/>
<main id="main" class="main">


    <div class="container">
        <div class="row justify-content-center">
            <div class="col-lg-8 col-md-10 col-sm-12">
                <h2 class="my-3 py-3 shadow-sm bg-light text-center">
                    <mark class="sky">글쓰기</mark>
                </h2>
            </div>
            <div class="col-lg-8 col-md-10 col-sm-12">
                <form id="form-register" method="POST" action="${root}/board?action=register">
                    <input type="hidden" name="action" value="article">
                    <div class="mb-3">
                        <label for="subject" class="form-label">제목 : </label>
                        <input
                                type="text"
                                class="form-control"
                                id="subject"
                                name="subject"
                                placeholder="제목..."
                                required
                        />
                    </div>
                    <div class="mb-3">
                        <label for="content" class="form-label">내용 : </label>
                        <textarea class="form-control" id="content" name="content" rows="7" required></textarea>
                    </div>
                    <div class="col-auto text-center">
                        <button type="submit" id="btn-register" class="btn btn-outline-primary mb-3">
                            글작성
                        </button>
                        <button type="reset" class="btn btn-outline-danger mb-3">초기화</button>
                    </div>
                </form>
            </div>
        </div>
    </div>


</main>
<script
        src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL"
        crossorigin="anonymous"
></script>

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
<script src="js/main.js"></script>

<script>

</script>
</body>
</html>
