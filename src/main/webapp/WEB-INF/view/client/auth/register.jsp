<%@page contentType="text/html" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
            <html lang="en">

            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>Document</title>
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
                    body {
                        background-color: #4A6EF5;
                        display: flex;
                        justify-content: center;
                        align-items: center;
                        height: 100vh;
                    }

                    .card {
                        width: 400px;
                        padding: 20px;
                        border-radius: 10px;
                        box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
                    }
                </style>
            </head>

            <body>
                <div class="card">
                    <h3 class="text-center">Create Account</h3>
                    <form:form method="post" action="/register" modelAttribute="registerUser">
                        <div class="mb-3">
                            <div class="row">

                                <div class="col">
                                    <c:set var="errorFirstName">
                                        <form:errors path="firstName" cssClass="invalid-feedback" />
                                    </c:set>
                                    <form:input type="text" class="form-control ${not empty errorFirstName ? 'is-invalid' : ''}"
                                    placeholder="First name"
                                        path="firstName" />
                                        ${errorFirstName}
                                </div>
                                <div class="col">
                                    <form:input type="text" class="form-control" placeholder="Last name"
                                        path="lastName" />
                                </div>
                            </div>
                        </div>
                        <div class="mb-3">
                            <c:set var="errorEmail">
                                <form:errors path="email" cssClass="invalid-feedback" />
                            </c:set>
                            <form:input type="email" class="form-control ${not empty errorEmail ? 'is-invalid' : ''}"
                                placeholder="Email address" path="email" />
                            ${errorEmail}
                        </div>
                        <div class="mb-3">
                            <div class="row">
                                <div class="col">
                                    
                                    <form:input type="password" class="form-control" placeholder="Password"
                                        path="password" />
                                </div>
                                <div class="col">
                                    <c:set var="errorConfirmPassword">
                                        <form:errors path="confirmPassword" cssClass="invalid-feedback" />
                                    </c:set>
                                    <form:input type="password" class="form-control ${not empty errorConfirmPassword ? 'is-invalid' : ''}"
                                     placeholder="Confirm Password"
                                        path="confirmPassword" />
                                        ${errorConfirmPassword}
                                </div>
                            </div>
                        </div>
                        <div class="d-grid">
                            <button type="submit" class="btn btn-primary">Create Account</button>
                        </div>
                    </form:form>
                    <div class="text-center mt-3">
                        <small><a href="#">Have an account? Go to login</a></small>
                    </div>
                </div>

            </body>

            </html>