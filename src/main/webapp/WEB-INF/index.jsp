<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8"/>
    <meta content="width=device-width, initial-scale=1.0" name="viewport"/>

    <title>Mentor Bootstrap Template - Index</title>
    <meta content="" name="description"/>
    <meta content="" name="keywords"/>

    <!-- Favicons -->
    <link href="img/favicon.png" rel="icon"/>
    <link href="img/apple-touch-icon.png" rel="apple-touch-icon"/>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js"
            integrity="sha384-I7E8VVD/ismYTF4hNIPjVp/Zjvgyol6VFvRkX/vR+Vc4jQkC+hVqc2pM8ODewa9r"
            crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.min.js"
            integrity="sha384-0pUGZvbkm6XF6gxjEnlmuGrJXVbNuzT9qBBavbLwCsOGabYfZo0T0to5eqruptLy"
            crossorigin="anonymous"></script>
    <!-- Google Fonts -->
    <link
            href="https://fonts.googleapis.com/css?family=Open+Sans:300,300i,400,400i,600,600i,700,700i|Raleway:300,300i,400,400i,500,500i,600,600i,700,700i|Poppins:300,300i,400,400i,500,500i,600,600i,700,700i"
            rel="stylesheet"
    />

</head>

<jsp:include page="nav/nav.jsp"/>

<!-- ======= Hero Section ======= -->
<section id="hero" class="d-flex justify-content-center align-items-center">
    <div class="container position-relative" data-aos="zoom-in" data-aos-delay="100">
        <div class="position-absolute bottom-50 start-30 p-5"
             style="z-index: 1">
            <h1>EnjoyTrip</h1>
            <h2>다양한 여행지에 대해 찾아보고 나만의 여행 계획을 만들어보세요!!</h2>
        </div>
        <div class="container-fluid p-0" id="hero-bg">
            <div class="position-relative">
                <div id="carouselExampleAutoplaying" class="carousel slide" data-bs-ride="carousel">
                    <div class="carousel-inner">
                        <div class="carousel-item active">
                            <img src="img/index/hero1.jpg" class="d-block w-100 bg-image" alt="..."/>
                        </div>
                        <div class="carousel-item">
                            <img src="img/index/hero2.jpg" class="d-block w-100" alt="..."/>
                        </div>
                        <div class="carousel-item">
                            <img src="img/index/hero3.jpg" class="d-block w-100" alt="..."/>
                        </div>
                    </div>
                    <button
                            class="carousel-control-prev"
                            type="button"
                            data-bs-target="#carouselExampleAutoplaying"
                            data-bs-slide="prev"
                    >
                        <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                        <span class="visually-hidden">Previous</span>
                    </button>
                    <button
                            class="carousel-control-next"
                            type="button"
                            data-bs-target="#carouselExampleAutoplaying"
                            data-bs-slide="next"
                    >
                        <span class="carousel-control-next-icon" aria-hidden="true"></span>
                        <span class="visually-hidden">Next</span>
                    </button>
                </div>
                <div
                        class="position-absolute bottom-50 end-50 translate-middle p-5 bg-transparent"
                        style="z-index: 1"
                >
                </div>
            </div>
        </div>
    </div>
</section>
<!-- End Hero -->

<body id="main">
    <!-- ======= Why Us Section ======= -->
    <section id="why-us" class="why-us">
        <div class="container" data-aos="fade-up">
            <div class="row">
                <div class="col-lg-4 d-flex align-items-stretch">
                    <div class="content" style="width: 350px;">
                        <h3>EnjoyTrip</h3>
                        <div>
                            <h5>
                                <div class="p-3">Community</div>
                                <div class="p-3">Search</div>
                                <div class="p-3">Tour Course</div>
                            </h5>
                        </div>
                    </div>
                </div>
                <div
                        class="col-lg-8 d-flex align-items-stretch"
                        data-aos="zoom-in"
                        data-aos-delay="100"
                >
                    <div class="icon-boxes d-flex flex-column justify-content-center">
                        <div class="row">
                            <div class="col-xl-4 d-flex align-items-stretch">
                                <div class="icon-box mt-4 mt-xl-0">
                                    <i class="bi bi-chat-right-text"></i>
                                    <h4 class="mt-5">Community</h4>
                                    <p>
                                        커뮤니티를 통해 사람들과 여행지에 대해 이야기를 나누고 경험을 공유할 수 있습니다.
                                    </p>
                                </div>
                            </div>
                            <div class="col-xl-4 d-flex align-items-stretch">
                                <div class="icon-box mt-4 mt-xl-0">
                                    <i class="bi bi-search"></i>
                                    <h4 class="mt-5">Search</h4>
                                    <p>
                                        다양한 관광지를 지역별로, 서비스 별로 검색해서 찾아볼 수 있습니다.
                                    </p>
                                </div>
                            </div>
                            <div class="col-xl-4 d-flex align-items-stretch">
                                <div class="icon-box mt-4 mt-xl-0">
                                    <i class="bi bi-map p-3"></i>
                                    <h4 class="mt-5">Tour Course</h4>
                                    <p>
                                        지도를 통해 여행 코스를 계획하고 저장할 수 있습니다.
                                    </p>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!-- End .content-->
                </div>
            </div>
        </div>
    </section>
    <!-- End Why Us Section -->

    <!-- ======= Features Section ======= -->
    <section id="features" class="features">
        <div class="container" data-aos="fade-up">
            <div class="row" data-aos="zoom-in" data-aos-delay="100">
                <div class="col-lg-3 col-md-4">
                    <div class="icon-box">
                        <i class="ri-store-line" style="color: #ffbb2c"></i>
                        <h3><a href="">음식점</a></h3>
                    </div>
                </div>
                <div class="col-lg-3 col-md-4 mt-4 mt-md-0">
                    <div class="icon-box">
                        <i class="bi bi-tree" style="color: #5578ff"></i>
                        <h3><a href="">관광지</a></h3>
                    </div>
                </div>
                <div class="col-lg-3 col-md-4 mt-4 mt-md-0">
                    <div class="icon-box">
                        <i class="bi bi-building" style="color: #e80368"></i>
                        <h3><a href="">숙박</a></h3>
                    </div>
                </div>
                <div class="col-lg-3 col-md-4 mt-4 mt-lg-0">
                    <div class="icon-box">
                        <i class="bi bi-cake" style="color: #e361ff"></i>
                        <h3><a href="">축제</a></h3>
                    </div>
                </div>
                <div class="col-lg-3 col-md-4 mt-4">
                    <div class="icon-box">
                        <i class="bi bi-person-walking" style="color: #47aeff"></i>
                        <h3><a href="">레저, 스포츠</a></h3>
                    </div>
                </div>
                <div class="col-lg-3 col-md-4 mt-4">
                    <div class="icon-box">
                        <i class="bi bi-house-heart" style="color: #ffa76e"></i>
                        <h3><a href="">문화시설</a></h3>
                    </div>
                </div>
                <div class="col-lg-3 col-md-4 mt-4">
                    <div class="icon-box">
                        <i class="bi bi-cart" style="color: #11dbcf"></i>
                        <h3><a href="">쇼핑</a></h3>
                    </div>
                </div>
                <div class="col-lg-3 col-md-4 mt-4">
                    <div class="icon-box">
                        <i class="bi bi-signpost-fill" style="color: #4233ff"></i>
                        <h3><a href="">여행코스</a></h3>
                    </div>
                </div>
            </div>
        </div>
    </section>
    <!-- End Features Section -->

    <!-- ======= Popular Courses Section ======= -->
    <section id="popular-courses" class="courses">
        <div class="container" data-aos="fade-up">
            <div class="section-title">
                <h2>Courses</h2>
                <p>Popular Courses</p>
            </div>

            <div class="row" data-aos="zoom-in" data-aos-delay="100">
                <div class="col-lg-4 col-md-6 d-flex align-items-stretch">
                    <div class="course-item" style="max-width: 534px">
                        <img src="img/index/seongsimdang.jpg" class="img-fluid" alt="..."/>
                        <div class="course-content">
                            <div class="d-flex justify-content-between align-items-center mb-3">
                                <h4>음식점</h4>
                            </div>

                            <h3><a href="course-details.html">대전 성심당</a></h3>
                            <p>
                                2011 프랑스 미슐랭 가이드 선정 한국에서 꼭 가봐야 할 빵집의 솜씨가 어떤지 한 번 확인해 보세요.
                            </p>
                        </div>
                    </div>
                </div>
                <!-- End Course Item-->

                <div class="col-lg-4 col-md-6 d-flex align-items-stretch mt-4 mt-md-0">
                    <div class="course-item">
                        <img src="img/index/jeju.avif" class="img-fluid" alt="..."/>
                        <div class="course-content">
                            <div class="d-flex justify-content-between align-items-center mb-3">
                                <h4>관광지</h4>
                            </div>

                            <h3><a href="course-details.html">제주 애월 지역</a></h3>
                            <p>
                                제주 애월 지역에서 한담해안로를 따라 거닐며 구석구석 숨겨진 명소를 찾아보세요.
                            </p>
                        </div>
                    </div>
                </div>
                <!-- End Course Item-->

                <div class="col-lg-4 col-md-6 d-flex align-items-stretch mt-4 mt-lg-0">
                    <div class="course-item">
                        <img src="img/index/kyungju.jpg" class="img-fluid" alt="..."/>
                        <div class="course-content">
                            <div class="d-flex justify-content-between align-items-center mb-3">
                                <h4>관광지</h4>
                            </div>

                            <h3><a href="course-details.html">경북 경주</a></h3>
                            <p>
                                신라 왕궁의 별궁터에서 야경을 바라보는 것은 어떤가요?
                            </p>
                        </div>
                    </div>
                </div>
                <!-- End Course Item-->
            </div>
        </div>
    </section>
    <!-- End Popular Courses Section -->

    <!-- ======= Footer ======= -->
    <footer id="footer">
        <div class="container d-md-flex py-4">
            <div class="me-md-auto text-center text-md-start">
                <div class="copyright">
                    <strong><span>SSAFY</span></strong>
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
    <!-- <script src="./js/index.js"></script> -->
    <script src="js/main.js"></script>
    </body>
</html>
