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
                                            <th>Xóa </th>
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
                                        <c:forEach var="cartDetail" items="${cartDetails}" varStatus="status">

                                            <tr>

                                                <td><img src="/images/product/${cartDetail.getProduct().image}"
                                                        class="img-fluid" width="50"></td>
                                                <td><a
                                                        href="/product/${cartDetail.product.id}">${cartDetail.getProduct().name}</a>
                                                </td>
                                                <td>${cartDetail.price}</td>
                                                <td>
                                                    <button class="btn btn-outline-secondary btn-sm">-</button>
                                                    <span class="mx-2" data-cart-detail-id="${cartDetail.id}"
                                                        data-cart-detail-price="${cartDetail.price}"
                                                        data-cart-detail-price="${status.index}">${cartDetail.quantity}</span>
                                                    <button class="btn btn-outline-secondary btn-sm">+</button>
                                                </td>
                                                <td data-cart-detail-id="${cartDetail.id}">${cartDetail.price *
                                                    cartDetail.quantity}</td>
                                                <form method="post" action="/delete-cart-product/${cartDetail.id}">
                                                    <input type="hidden" name="${_csrf.parameterName}"
                                                        value="${_csrf.token}" />
                                                    <td><button type="submit"
                                                            class="btn btn-outline-danger btn-sm btn-delete">&times;</button>
                                                    </td>
                                                </form>
                                            </tr>
                                        </c:forEach>

                                    </tbody>
                                </table>

                                <div class="p-3 bg-light rounded ">
                                    <h5 class="text-center">Thông tin đơn hàng </h5>
                                    <div class="text-center">
                                        <!-- <fmt:formatNumber type="number" value="${totalPrice}"/> fomat-->
                                        <p data-cart-detail-price="${cartDetail.price}">Tạm tính:
                                            <strong>${totalPrice}</strong>
                                        </p>
                                        <p>Phí vận chuyển $3.00 </p>
                                        <p data-cart-detail-price="${cartDetail.price}">Tổng tiền : <strong>${totalPrice
                                                +3
                                                }</strong></p>
                                    </div>
                                    
                                     <form:form action="/confirm-checkout" method="post" modelAttribute="cart">
                                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                                        <div style="display: none;"> 
                                            <c:forEach var="cartDetail" items="${cart.cartDetails}" varStatus="status">
                                                <div class="mb-3">
                                                    <div class="form-group">
                                                        <label>Id :</label>
                                                        <form:input class="form-control" type="text"
                                                            value="${cartDetail.id}"
                                                            path="cartDetails[${status.index}].id" readonly="true" />
                                                    </div>
                                                    <div class="form-group">
                                                        <label>Quantity :</label>
                                                        <form:input class="form-control cart-quantity-input" type="text"
                                                            value="${cartDetail.quantity}"
                                                            path="cartDetails[${status.index}].quantity"
                                                            data-cart-detail-id="${cartDetail.id}" />
                                                    </div>
                                                </div>
                                            </c:forEach>
                                        </div>
                                            <div class="text-center mt-4">
                                                <c:choose>
                                                    <c:when test="${sessionScope.sum == 0}">
                                                        <p class="text-danger">Không có hàng trong giỏ!</p>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <button type="submit" class="btn btn-success w-50">Xác nhận đơn hàng</button>
                                                    </c:otherwise>
                                                </c:choose>
                                                
                                            </div>
                                       
                                        </form:form>
                                     
                                    </div>
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