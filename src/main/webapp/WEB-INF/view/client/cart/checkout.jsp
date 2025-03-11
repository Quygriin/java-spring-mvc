<%@page contentType="text/html" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
            <%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
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


                </head>

                <body>

                    <!-- Spinner Start -->
                    <div id="spinner"
                        class="show w-100 vh-100 bg-white position-fixed translate-middle top-50 start-50  d-flex align-items-center justify-content-center">
                        <div class="spinner-grow text-primary" role="status"></div>
                    </div>
                    <!-- Spinner End -->

                    <jsp:include page="../layout/header.jsp" />

                    <div class="container my-5">
                        <div class="row justify-content-center">
                            <div class="col-lg-10 col-md-12 bg-white p-4 rounded shadow">
                                <h4 class="mb-4 text-center">🛒 Chi Tiết Giỏ Hàng</h4>
                                <table class="table table-bordered text-center align-middle">
                                    <thead class="table-secondary">
                                        <tr>
                                            <th>Sản phẩm</th>
                                            <th>Tên sản phẩm </th>
                                            <th>Giá </th>
                                            <th>Số lượng </th>
                                            <th>Tổng tiền </th>

                                        </tr>

                                    </thead>
                                    <tbody>
                                        <c:if test="${sessionScope.sum == 0}">
                                            <tr>
                                                <td colspan="6" style="color: red; font-weight: bold;">
                                                    Không còn sản phẩm nào trong giỏ hàng!
                                                </td>
                                            </tr>
                                        </c:if>
                                        <c:forEach var="cartDetail" items="${cartDetails}">

                                            <tr>

                                                <td><img src="/images/product/${cartDetail.getProduct().image}"
                                                        class="img-fluid" width="50"></td>
                                                <td><a
                                                        href="/product/${cartDetail.product.id}">${cartDetail.getProduct().name}</a>
                                                </td>
                                                <td>${cartDetail.price}</td>
                                                <td>

                                                    <span class="mx-2" data-cart-detail-id="${cartDetail.id}"
                                                        data-cart-detail-price="${cartDetail.price}">${cartDetail.quantity}</span>

                                                </td>
                                                <td data-cart-detail-id="${cartDetail.id}">${cartDetail.price *
                                                    cartDetail.quantity}</td>
                                                <form method="post" action="/delete-cart-product/${cartDetail.id}">
                                                    <input type="hidden" name="${_csrf.parameterName}"
                                                        value="${_csrf.token}" />

                                                </form>
                                            </tr>
                                        </c:forEach>

                                    </tbody>
                                </table>
                                <c:if test="${not empty cartDetails}">
                                    <!-- Form đặt hàng, modelAttribute="cart" -->
                                    <form:form action="/place-order" method="post" modelAttribute="cart">
                                        <!-- CSRF token -->
                                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                                        <div class="row mt-5">
                                            <div class="col-md-6">
                                                <h4>Thông Tin Người Nhận</h4>
                                                
                                                    <div class="mb-3">
                                                        <label>Tên người nhận</label>
                                                        <input type="text" class="form-control" name="receiverName"
                                                            required>
                                                    </div>
                                                    <div class="mb-3">
                                                        <label>Địa chỉ</label>
                                                        <input type="text" class="form-control" name="receiverAddress"
                                                            required>
                                                    </div>
                                                    <div class="mb-3">
                                                        <label>Số điện thoại</label>
                                                        <input type="text" class="form-control" name="receiverPhone"
                                                            required>
                                                    </div>

                                              
                                            </div>
                                            <div class="col-md-6 checkout-info">
                                                <h4 class="text-primary">Thông Tin Thanh Toán</h4>
                                                <p>Phí vận chuyển: <strong>0 đ</strong></p>
                                                <p>Hình thức: <strong>Thanh toán khi nhận hàng (COD)</strong></p>
                                                <p data-cart-detail-price="${cartDetail.price}">Tổng tiền :
                                                    <strong>${totalPrice}</strong>
                                                </p>
                                                <button type="submit" class="btn btn-success w-100">Xác nhận đặt
                                                    hàng</button>
                                            </div>
                                        </div>
                                    </form:form>
                                </c:if>
                                <c:if test="${empty cartDetails}">
                                    <p>Giỏ hàng trống!</p>
                                </c:if>
                            </div>
                        </div>
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