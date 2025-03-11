<%@page contentType="text/html" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
            <%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>


                <!DOCTYPE html>
                <html lang="en">

                <head>
                    <meta charset="utf-8">
                    <title>Fruitables - Vegetable Website Template</title>
                    <meta content="width=device-width, initial-scale=1.0" name="viewport">
                    <meta content="" name="keywords">
                    <meta content="" name="description">

                    <!-- Google Web Fonts -->
                    <link rel="preconnect" href="https://fonts.googleapis.com">
                    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
                    <link
                        href="https://fonts.googleapis.com/css2?family=Open+Sans:wght@400;600&family=Raleway:wght@600;800&display=swap"
                        rel="stylesheet">

                    <!-- Icon Font Stylesheet -->
                    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.15.4/css/all.css" />
                    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.4.1/font/bootstrap-icons.css"
                        rel="stylesheet">

                    <!-- Libraries Stylesheet -->
                    <link href="/client/lib/lightbox/css/lightbox.min.css" rel="stylesheet">
                    <link href="/client/lib/owlcarousel/assets/owl.carousel.min.css" rel="stylesheet">


                    <!-- Customized Bootstrap Stylesheet -->
                    <link href="/client/css/bootstrap.min.css" rel="stylesheet">

                    <!-- Template Stylesheet -->
                    <link href="/client/css/style.css" rel="stylesheet">

                    <style>
                        th,
                        td {
                            padding: 15px !important;
                            /* Tăng khoảng cách bên trong ô */
                            vertical-align: middle !important;
                            /* Căn giữa nội dung theo chiều dọc */
                        }

                        img {
                            width: 60px;
                            /* Tăng kích thước ảnh */
                            height: 60px;
                            object-fit: cover;
                            /* Đảm bảo ảnh không bị méo */
                        }
                    </style>
                </head>

                <body style="margin-top: 150px;">

                    <!-- Spinner Start -->
                    <div id="spinner"
                        class="show w-100 vh-100 bg-white position-fixed translate-middle top-50 start-50  d-flex align-items-center justify-content-center">
                        <div class="spinner-grow text-primary" role="status"></div>
                    </div>
                    <!-- Spinner End -->

                    <jsp:include page="../layout/header.jsp" />

                    <div class="container mt-4">
                        <nav class="breadcrumb">
                            <a class="breadcrumb-item text-success" href="#">Home</a>
                            <span class="breadcrumb-item active">Lịch sử mua hàng</span>
                        </nav>

                        <table class="table table-bordered">
                            <thead class="table-light">
                                <tr>
                                    <th>Sản phẩm</th>
                                    <th>Tên</th>
                                    <th>Giá cả</th>
                                    <th>Số lượng</th>
                                    <th>Thành tiền</th>
                                    <th>Trạng thái</th>
                                </tr>
                            </thead>
                            <tbody>
                                <!-- Order 3 -->
                                <c:if test="${empty orders}">
                                    <tr>
                                        <td colspan="6" class="fw-bold bg-light">Không có đơn hàng nào </td>
                                    </tr>
                                </c:if>
                            <c:if test="${not empty orders}">
                                <c:forEach var="order" items="${orders}">
                                    <tr>
                                        <td colspan="2" >Order Id = ${order.id}</td>
                                        <td colspan="1" >${order.totalPrice}</td>
                                        <td colspan="2"></td>
                                        <td colspan="1" >${order.totalPrice}</td>
                                    </tr>   
                                    <c:forEach var="orderDetail" items="${order.orderDetails}">
                                        <tr>
                                            <td><img src="/images/product/${orderDetail.product.image}" class="rounded"
                                                    alt="Laptop"></td>
                                            <td class="text-success">${orderDetail.product.name}</td>
                                            <td>${orderDetail.price}</td>
                                            <td>${orderDetail.quantity}</td>
                                            <td colspan="2">${orderDetail.quantity *orderDetail.price}</td>

                                        </tr>
                                    </c:forEach>

                                </c:forEach>
                            </c:if>

                            </tbody>
                        </table>
                    </div>
                    <jsp:include page="../layout/footer.jsp" />


                    <!-- Back to Top -->
                    <a href="#" class="btn btn-primary border-3 border-primary rounded-circle back-to-top"><i
                            class="fa fa-arrow-up"></i></a>


                    <!-- JavaScript Libraries -->
                    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.4/jquery.min.js"></script>
                    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/js/bootstrap.bundle.min.js"></script>
                    <script src="/client/lib/easing/easing.min.js"></script>
                    <script src="/client/lib/waypoints/waypoints.min.js"></script>
                    <script src="/client/lib/lightbox/js/lightbox.min.js"></script>
                    <script src="/client/lib/owlcarousel/owl.carousel.min.js"></script>

                    <!-- Template Javascript -->
                    <script src="/client/js/main.js"></script>
                </body>

                </html>