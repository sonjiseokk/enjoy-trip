<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
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
            rel="stylesheet"
    />

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

    <script
            type="text/javascript"
            src="//dapi.kakao.com/v2/maps/sdk.js?appkey=3c9544e401e78560632eaaf871996656"
    ></script>
</head>

<jsp:include page="nav/nav.jsp"/>

<main id="main">
    <!-- ======= Breadcrumbs ======= -->
    <!-- End Breadcrumbs -->

    <!-- ======= Contact Section ======= -->
    <section id="contact" class="contact">

        <c:if test="${!empty userinfo }">
            <div class='row mb-3' style='margin-top: 83px;'>
                <h1 style='margin-left: 120px;'>${userinfo.userName} 님이 찜하신 장소</h1>
            </div>
        </c:if>
        <c:if test="${empty userinfo }">
            <div class='row mb-3' style='margin-top: 83px;'>
                <h1 style='margin-left: 120px;'>로그인 후 이용하세요</h1>
            </div>
        </c:if>
        <div class="container">
            <div class="row" data-aos="fade-up">
                <div class="breadcrumbs2 col-md-3" data-aos="fade-in">
                    <div class="container">
                        <h2>찜목록</h2>
                        <c:if test="${not empty tripList}">
                            <c:forEach var="item" items="${tripList}">
                                <div class="card mb-3" style="max-width: 540px">
                                    <div class="row g-0">
                                        <div class="col-md-4">
                                            <img src="${item.firstImage}" class="img-fluid rounded-start" alt="..."/>
                                        </div>
                                        <div class="col-md-8">
                                            <div class="card-body">
                                                <h5 class="card-title"
                                                    onclick="panTo(${item.latitude}, ${item.longitude})">${item.title}</h5>
                                                <p class="card-text">
                                                        ${item.addr1}
                                                </p>
                                            </div>
                                            <button class="btn btn-outline-danger">삭제</button>
                                        </div>
                                    </div>
                                </div>
                            </c:forEach>
                        </c:if>
                        <!-- 관광지 목록 생성 -->
                        <div class="recommend" style="overflow: scroll; height: 550px"></div>
                    </div>
                </div>
                <div class="col-md-9" data-aos="fade-up" style="display: inline; margin-top: 10px">
                    <div id="map" style="width: 100%; height: 700px"></div>
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
                &copy; Copyright <strong><span>Mentor</span></strong
            >. All Rights Reserved
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
            <a href="#" class="twitter"><i class="bx bxl-twitter"></i></a>
            <a href="#" class="facebook"><i class="bx bxl-facebook"></i></a>
            <a href="#" class="instagram"><i class="bx bxl-instagram"></i></a>
            <a href="#" class="google-plus"><i class="bx bxl-skype"></i></a>
            <a href="#" class="linkedin"><i class="bx bxl-linkedin"></i></a>
        </div>
    </div>
</footer>
<!-- End Footer -->

<div id="preloader"></div>
<a href="#" class="back-to-top d-flex align-items-center justify-content-center"
><i class="bi bi-arrow-up-short"></i
></a>

<!-- Template Main JS File -->
<script src="js/main.js"></script>
<script>
    var userLikes = [];

    window.onload = function () {
        var mapContainer = document.getElementById("map"); // 지도를 표시할 div
        var mapOption = {
            center: new kakao.maps.LatLng(36, 127.5), // 지도의 중심좌표 (기본값 설정)
            level: 13, // 지도의 확대 레벨 (기본값 설정)
        };

        var map = new kakao.maps.Map(mapContainer, mapOption); // 지도를 생성합니다

        var tripList = ${jsonList};

        for (var key in tripList) {
            if (tripList.hasOwnProperty(key)) {
                var trip = tripList[key];
                // 마커가 표시될 위치입니다
                var markerPosition = new kakao.maps.LatLng(trip.latitude, trip.longitude);

                // 마커를 생성합니다
                var marker = new kakao.maps.Marker({
                    position: markerPosition
                });

                (function (trip) {
                    kakao.maps.event.addListener(marker, 'click', function () {
                        // 마커 위에 인포윈도우를 표시합니다
                        window.location.href = "${root}/tour?action=dist&contentId=" + trip.content_id;
                    });
                })(trip);

                // 마커가 지도 위에 표시되도록 설정합니다
                marker.setMap(map);
            }

        }

        var path = '${orderedTrip}';

        if (path != null) {
            console.log(path);

            // 정규표현식을 사용하여 숫자와 소수점을 추출
            var matches = path.match(/-?\d+\.\d+/g);

            // 추출된 숫자와 소수점을 출력
            console.log(matches);

            var first_polyline = [];
            for (var i = 0; i < matches.length; i += 2) {
                first_polyline.push(new kakao.maps.LatLng(matches[i], matches[i + 1]));
            }

            console.log(first_polyline);

            // 지도에 표시할 선을 생성합니다

            var first_linePath = new kakao.maps.Polyline({
                path: first_polyline, // 선을 구성하는 좌표배열 입니다
                strokeWeight: 3,      // 선의 두께 입니다
                strokeColor: 'blue', // 선의 색깔입니다
                strokeOpacity: 0.7,   // 선의 불투명도 입니다 1에서 0 사이의 값이며 0에 가까울수록 투명합니다
                strokeStyle: 'solid'  // 선의 스타일입니다
            });

            first_linePath.setMap(map);
        }
    };


</script>
</body>
</html>
