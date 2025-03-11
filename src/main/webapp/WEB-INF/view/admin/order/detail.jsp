<%@page contentType="text/html" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <!DOCTYPE html>
        <html lang="en">

        <head>
            <meta charset="utf-8" />
            <meta http-equiv="X-UA-Compatible" content="IE=edge" />
            <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
            <meta name="description" content="Hỏi Dân IT - Dự án laptopshop" />
            <meta name="author" content="Hỏi Dân IT" />
            <title>Dashboard - Hỏi Dân IT</title>
            <link href="https://cdn.jsdelivr.net/npm/simple-datatables@7.1.2/dist/style.min.css" rel="stylesheet" />
            <link href="/css/styles.css" rel="stylesheet" />
            <script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js" crossorigin="anonymous"></script>
        </head>

        <body class="sb-nav-fixed">
            <jsp:include page="../layout/header.jsp"></jsp:include>
            <div id="layoutSidenav">
                <jsp:include page="../layout/sidebar.jsp"></jsp:include>
                <div id="layoutSidenav_content">
                    <main>
                        <div class="container-fluid px-4">
                            <h1 class="mt-4">Dashboard</h1>
                            <ol class="breadcrumb mb-4">
                                <li class="breadcrumb-item "><a href="/admin">Dashboard</a></li>
                                <li class="breadcrumb-item active">Order Detail</li>
                            </ol>
                            <div class=" mt-5">
                                <div class="">
                                    <div class="col-12 mx-auto">
                                        <div class="d-flex justify-content-between">
                                            <H1>Order detail with id = ${order.id}</H1>
                                          
                                        </div>
                                        <hr>
                                        <table class="table table-bordered table-hover mt-5">
                                            <thead>
                                                <tr>
                                                    <th>Sản phẩm </th>
                                                    <th>Tên </th>
                                                    <th>Giá cả </th>
                                                    <th>Số lượng </th>
                                                    <th>Thành tiền </th>
                                                </tr>
                                            </thead>
                                            <tbody>


                                                <c:forEach var="orderDetail" items="${orderDetails}">
                                                    <tr>
                                                        <td><img src="/images/product/${orderDetail.product.image}"
                                                            class="img-fluid" width="50"></td>
                                                        <td><a href="/admin/orderDetail/${orderDetail.product.id}">${orderDetail.product.name}</a></td>
                                                        <td>${orderDetail.price}</td>
                                                        <td>${orderDetail.quantity}</td>
                                                        <td>${orderDetail.quantity * orderDetail.price}</td>

                                                        
                                                    </tr>
                                                </c:forEach>



                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </main>
                    <jsp:include page="../layout/footer.jsp"></jsp:include>
                </div>
            </div>
            <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"
                crossorigin="anonymous"></script>
            <script src="js/scripts.js"></script>

        </body>

        </html>