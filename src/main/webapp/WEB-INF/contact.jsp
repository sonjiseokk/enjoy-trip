<%@page import="org.json.JSONObject" %>
<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<%@ page import="com.ssafy.enjoytrip.trip.SidoDto" %>
<%@ page import="com.ssafy.enjoytrip.trip.GugunDto" %>
<%@ page import="java.util.List" %>
<%@ page import="org.json.JSONObject" %>
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
            rel="stylesheet"/>

    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js"
            integrity="sha384-I7E8VVD/ismYTF4hNIPjVp/Zjvgyol6VFvRkX/vR+Vc4jQkC+hVqc2pM8ODewa9r"
            crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.min.js"
            integrity="sha384-0pUGZvbkm6XF6gxjEnlmuGrJXVbNuzT9qBBavbLwCsOGabYfZo0T0to5eqruptLy"
            crossorigin="anonymous"></script

    <!- =======================================================
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

        #exampleModal .modal-dialog {
            max-width: 800px;
        }
    </style>


    <script type="module" src="./js/map.js"></script>
    <script type="text/javascript"
            src="//dapi.kakao.com/v2/maps/sdk.js?appkey=3c9544e401e78560632eaaf871996656"></script>
</head>

<%
    JSONObject sido = (JSONObject) request.getAttribute("sido");
%>

<jsp:include page="nav/nav.jsp"/>

<main id="main">
    <!-- ======= Breadcrumbs ======= -->
    <!-- End Breadcrumbs -->

    <!-- ======= Contact Section ======= -->
    <section id="contact" class="contact">
        <div class="container" data-aos="fade-up" style="margin-top: 80px">
            <div class="row mt-5">
                <div class="col-lg-12">
                    <form action="/enjoyTrip/trip?action=trip" class="php-email-form">
                        <div class="row">
                            <div class="col-md-3 form-group">
                                <!-- 지역 코드 -->
                                <select name="sido" id="index-area"
                                        onchange="javascript:chageLangSelect()" class="form-select"></select>
                            </div>
                            <div class="col-md-3 form-group">
                                <!-- 지역 (시군도) 코드 -->
                                <select name="gugun" id="index-sigundo-area" class="form-select"></select>
                            </div>
                            <div class="col-md-3 form-group">
                                <!-- 지역 (시군도) 코드 -->
                                <!-- 관광타입(12:관광지, 14:문화시설, 15:축제공연행사, 25:여행코스, 28:레포츠, 32:숙박, 38:쇼핑, 39:음식점) -->
                                <select class="form-select" name="form-select" id="category">
                                    <option value="12">관광지</option>
                                    <option value="14">문화시설</option>
                                    <option value="15">축제공연행사</option>
                                    <option value="25">여행코스</option>
                                    <option value="28">레포츠</option>
                                    <option value="32">숙박</option>
                                    <option value="38">쇼핑</option>
                                    <option value="39">음식점</option>
                                </select>
                            </div>
                            <div class="col-md-3 text-center">
                                <button class="btn btn-outline-success" type="button"
                                        style="padding-left: 50px; padding-right: 50px"
                                        onclick="javascript:chageArea()">검색
                                </button>
                            </div>
                        </div>

                        <div class="my-3">
                            <div class="loading">Loading</div>
                            <div class="error-message"></div>
                            <div class="sent-message">Your message has been sent. Thank
                                you!
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>

        <div class="container">
            <div class="row" data-aos="fade-up">
                <div class="breadcrumbs2 col-md-3" data-aos="fade-in">
                    <div class="container">
                        <h2>추천관광지</h2>

                        <!-- 관광지 목록 생성 -->
                        <div class="recommend" style="overflow-y: scroll; height: 550px"></div>
                    </div>
                </div>
                <div class="col-md-9" data-aos="fade-up"
                     style="display: inline; margin-top: 10px">
                    <div id="map" style="width: 100%; height: 700px"></div>
                </div>
            </div>
        </div>

        <!-- 모달 창 -->
        <div id="exampleModal" class="modal" tabindex="-1" role="dialog">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title-detail">모달 제목</h5>
                        <button type="button" class="btn-close" data-dismiss="modal"
                                aria-label="Close"></button>
                    </div>
                    <div class="modal-body-detail">
                        <p>모달 내용</p>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary"
                                data-dismiss="modal">닫기
                        </button>
                        <!-- <button type="button" class="btn btn-primary">저장</button> -->
                    </div>
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

<!-- Vendor JS Files -->
<script src="./vendor/purecounter/purecounter_vanilla.js"></script>
<script src="./vendor/aos/aos.js"></script>
<script src="./vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
<script src="./vendor/swiper/swiper-bundle.min.js"></script>
<script src="./vendor/php-email-form/validate.js"></script>

<!-- Template Main JS File -->
<script src="js/main.js"></script>

<script>
    let data = <%=sido%>;
    makeOption(data);

    function makeOption(data) {
        let areas = data.body;
        console.log(areas);
        let sel = document.getElementById("index-area");
        areas.forEach((area) => {
            let opt = document.createElement("option");
            opt.setAttribute("value", area.sido_code);
            opt.appendChild(document.createTextNode(area.sido_name));
            sel.appendChild(opt);
        });
    }

    function chageLangSelect() {
        var langSelect = document.getElementById('index-area');
        var selectValue = langSelect.options[langSelect.selectedIndex].value;

        console.log(selectValue);
        // js에서 넘겨주기 선태된 시도 코드 (selectValue)
        gundoUrl = "/enjoyTrip/trip?action=gugun&sido=" + selectValue;

        fetch(gundoUrl)
            .then(response => {
                if (!response.ok) {
                    throw new Error('서버 응답이 실패하였습니다.');
                }
                return response.json();
            })
            .then(data => {
                makedetailOption(data);
            })
            .catch(error => {
                console.error('데이터를 가져오는 중에 오류가 발생하였습니다:', error);
            });


        function makedetailOption(gugundata) {
            let areas = gugundata.body;
            console.log(areas);
            let sel = document.getElementById('index-sigundo-area');
            sel.options.length = 0;
            areas.forEach((area) => {
                let opt = document.createElement('option');
                opt.setAttribute('value', area.gugun_code);
                opt.appendChild(document.createTextNode(area.gugun_name));

                sel.appendChild(opt);
            });
        }
    }

    //contentTypeId=12
    function chageArea() {
        let seletdArea = document.getElementById('index-area');
        let seletedSigundodArea = document.getElementById('index-sigundo-area');
        let category = document.getElementById('category');

        console.log(seletdArea.value, seletedSigundodArea.value, category.value);

        let initArea = [
            [37.564214, 127.001699], // 서울
            [37.4562557, 126.7052062], // 인천
            [36.3504119, 127.3845475], // 대전
            [35.8714354, 128.601445], // 대구
            [35.1, 126.8], // 광주
            [35.17944, 129.07556], // 부산
            [35.53889, 129.31667], // 울산
            [36.4875, 127.28167], // 세종
        ];
        console.log(seletdArea.value);

        recomend(seletdArea.value, seletedSigundodArea.value, category.value);
    }


    window.onload = function () {
        recomend(1, 1, 12);

        // 초기 select option값 받아오기

        // 초기 지도
        var mapContainer = document.getElementById('map'), // 지도를 표시할 div
            mapOption = {
                center: new kakao.maps.LatLng(37.564214, 127.001699), // 지도의 중심좌표
                level: 9, // 지도의 확대 레벨
            };

        var map = new kakao.maps.Map(mapContainer, mapOption); // 지도를 생성합니다

        // 일반 지도와 스카이뷰로 지도 타입을 전환할 수 있는 지도타입 컨트롤을 생성합니다
        var mapTypeControl = new kakao.maps.MapTypeControl();

        // 지도에 컨트롤을 추가해야 지도위에 표시됩니다
        // kakao.maps.ControlPosition은 컨트롤이 표시될 위치를 정의하는데 TOPRIGHT는 오른쪽 위를 의미합니다
        map.addControl(mapTypeControl, kakao.maps.ControlPosition.TOPRIGHT);

        // 지도 확대 축소를 제어할 수 있는  줌 컨트롤을 생성합니다
        var zoomControl = new kakao.maps.ZoomControl();
        map.addControl(zoomControl, kakao.maps.ControlPosition.RIGHT);

    };

    function recomend(area, sigundo, category) {
        console.log(area, sigundo);
        let Url = "/enjoyTrip/trip?action=search&contentTypeId="
            + category + "&sidoCode="
            + area + "&gugunCode="
            + sigundo;

        fetch(Url)
            .then((response) => response.json())
            .then((data) => makeList(data));
        //.then((data) => console.log(data));
    }

    function makeList(data) {
        let recommend = document.querySelector('.recommend');
        let recommendAreas = data.body;

        console.log(recommendAreas);
        // recommend 요소의 내용을 초기화
        recommend.innerHTML = '';

        // area 해당 지역 관강 정보 들어있음
        // 폼태그 만들어서 request 보내야 할듯
        // jsp 페이지에서 세션 정보 저장해서 js에서 사용
        let idx = 0;
        // 이렇게 해놓고 변수로 사용
        recommendAreas.forEach((area) => {

            console.log(area.title);

            let img = area.first_image;

            let cardElement = document.createElement('div');
            cardElement.className = 'row';
            cardElement.style.margin = '5px';

            let cardHtml = `<div class="card mb-3" style="max-width: 540px" >
                              <div class="row g-0">
                                <div class="col-md-4">
                                  <img src=` + img + ` class="img-fluid rounded-start" alt="..." />
                                </div>
                                <div class="col-md-8">
                                  <div class="card-body">
                                    <h5 class="card-title" onclick="panTo()">` + area.title + `</h5>
                                    <p class="card-text">
                                      ` + area.addr1 + `
                                    </p>
                                  </div>
                                </div>
                              </div>
                            </div>`;

            cardElement.innerHTML = cardHtml;

            let button = document.createElement('button');
            button.className = 'btn btn-outline-primary';
            button.textContent = '찜하기';
            button.setAttribute('style', 'display=inline-block');

            let button2 = document.createElement('button');
            button2.className = 'btn btn-outline-primary';
            button2.textContent = '상세보기';
            button2.setAttribute('style', 'display=inline-block');

            /* 찜하기 버튼 위치 => 백엔드로*/
            button.addEventListener('click', () => {
                console.log('찜하기 버튼이 클릭되었습니다.');
                alert('찜완료');

                window.location.href = "${root}/tour?action=join&userId=${userinfo.userId}&contentId=" + area.content_id;
            });

            /* 상세보기 버튼 */
            button2.addEventListener('click', () => {
                console.log('상세보기 버튼이 클릭되었습니다.');

                fetch('/enjoyTrip/trip?action=view&contentId=' + area.content_id)
                    .then(response => {
                        if (!response.ok) {
                            throw new Error('서버 응답이 실패하였습니다.');
                        }
                        return response.json();
                    })
                    .then(data => {
                        //console.log(data);
                        displayModal(data);
                    })
                    .catch(error => {
                        console.error('데이터를 가져오는 중에 오류가 발생하였습니다:', error);
                    });

                //var modal = document.getElementById('exampleModal');
                //modal.style.display = 'block';
            });

            function displayModal(data) {
                var modalTitle = document.querySelector('.modal-title-detail');
                var modalBody = document.querySelector('.modal-body-detail');

                console.log(modalTitle);
                console.log(data.body.title);

                // 모달의 제목과 내용을 서버에서 받아온 데이터로 업데이트
                modalTitle.textContent = data.body.title;
                modalBody.innerHTML = `
      		    	<img src=` + data.body.first_image + `>
      		        <p><strong>주소:</strong> ` + data.body.addr1 + `</p>
      		        <p><strong>개요:</strong> ` + data.body.overview + `</p>
      		    `;

                var modal = document.getElementById('exampleModal');
                modal.style.display = 'block';
            }

            // 모달 닫기 버튼 눌렀을 때 처리
            var modalCloseButtons = document.querySelectorAll('[data-dismiss="modal"]');
            modalCloseButtons.forEach(function (btn) {
                btn.addEventListener('click', function () {
                    var modal = this.closest('.modal');
                    modal.style.display = 'none';

                });
            });

            // 모달 바깥 영역 클릭 시 모달 닫기
            window.addEventListener('click', function (event) {
                var modals = document.querySelectorAll('.modal');
                modals.forEach(function (modal) {
                    if (event.target === modal) {
                        modal.style.display = 'none';
                    }
                });
            });


            let cardBody = cardElement.querySelector('.card-body');
            cardBody.appendChild(button);
            cardBody.appendChild(button2);

            recommend.appendChild(cardElement);
        });

        var mapContainer = document.getElementById('map'), // 지도를 표시할 div
            mapOption = {
                center: new kakao.maps.LatLng(recommendAreas[0].latitude, recommendAreas[0].longitude), // 지도의 중심좌표
                level: 6, // 지도의 확대 레벨
            };

        var map = new kakao.maps.Map(mapContainer, mapOption); // 지도를 생성합니다

        for (let i = 0; i < recommendAreas.length; i++) {
            // 마커가 표시될 위치입니다
            var markerPosition = new kakao.maps.LatLng(recommendAreas[i].latitude, recommendAreas[i].longitude);

            // 마커를 생성합니다
            var marker = new kakao.maps.Marker({
                position: markerPosition,
            });
            // 마커가 지도 위에 표시되도록 설정합니다
            marker.setMap(map);

            var iwContent = `<div style="padding:5px;">` + recommendAreas[i].title + `<br><a href="https://map.kakao.com/link/map/Hello World!,"+recommendAreas[i].mapy+","+recommendAreas[i].mapx" style="color:blue" target="_blank">큰지도보기</a></div>`, // 인포윈도우에 표출될 내용으로 HTML 문자열이나 document element가 가능합니다
                iwPosition = new kakao.maps.LatLng(33.450701, 126.570667); //인포윈도우 표시 위치입니다

            // 인포윈도우를 생성합니다
            var infowindow = new kakao.maps.InfoWindow({
                position: iwPosition,
                content: iwContent,
            });
            infowindow.open(map, marker);
        }

        // 일반 지도와 스카이뷰로 지도 타입을 전환할 수 있는 지도타입 컨트롤을 생성합니다
        var mapTypeControl = new kakao.maps.MapTypeControl();

        // 지도에 컨트롤을 추가해야 지도위에 표시됩니다
        // kakao.maps.ControlPosition은 컨트롤이 표시될 위치를 정의하는데 TOPRIGHT는 오른쪽 위를 의미합니다
        map.addControl(mapTypeControl, kakao.maps.ControlPosition.TOPRIGHT);

        // 지도 확대 축소를 제어할 수 있는  줌 컨트롤을 생성합니다
        var zoomControl = new kakao.maps.ZoomControl();
        map.addControl(zoomControl, kakao.maps.ControlPosition.RIGHT);
    }

    function panTo() {
        // 이동할 위도 경도 위치를 생성합니다
        var moveLatLon = new kakao.maps.LatLng(33.45058, 126.574942);

        // 지도 중심을 부드럽게 이동시킵니다
        // 만약 이동할 거리가 지도 화면보다 크면 부드러운 효과 없이 이동합니다
        map.panTo(moveLatLon);
    }


</script>
</body>
</html>
