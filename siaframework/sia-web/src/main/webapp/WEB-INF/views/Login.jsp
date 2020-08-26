<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
    
<head>
        <!-- Title -->
        <link rel="shotcut icon" href="${pageContext.servletContext.contextPath}/resources/icon.ico">
        <title>Masuk | Sistem Informasi Akademik</title>
        
        <meta content="width=device-width, initial-scale=1" name="viewport"/>
        <meta charset="UTF-8">
        <meta name="description" content="Admin Dashboard Template" />
        <meta name="keywords" content="admin,dashboard" />
        <meta name="author" content="Steelcoders" />
        
        <!-- Styles -->
        <link href='http://fonts.googleapis.com/css?family=Open+Sans:400,300,600' rel='stylesheet' type='text/css'>
        <link href="${pageContext.servletContext.contextPath}/resources/plugins/pace-master/themes/blue/pace-theme-flash.css" rel="stylesheet"/>
        <link href="${pageContext.servletContext.contextPath}/resources/plugins/uniform/css/uniform.default.min.css" rel="stylesheet"/>
        <link href="${pageContext.servletContext.contextPath}/resources/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
        <link href="${pageContext.servletContext.contextPath}/resources/plugins/fontawesome/css/font-awesome.css" rel="stylesheet" type="text/css"/>
        <link href="${pageContext.servletContext.contextPath}/resources/plugins/line-icons/simple-line-icons.css" rel="stylesheet" type="text/css"/>	
        <link href="${pageContext.servletContext.contextPath}/resources/plugins/offcanvasmenueffects/css/menu_cornerbox.css" rel="stylesheet" type="text/css"/>	
        <link href="${pageContext.servletContext.contextPath}/resources/plugins/waves/waves.min.css" rel="stylesheet" type="text/css"/>	
        <link href="${pageContext.servletContext.contextPath}/resources/plugins/switchery/switchery.min.css" rel="stylesheet" type="text/css"/>
        <link href="${pageContext.servletContext.contextPath}/resources/plugins/3d-bold-navigation/css/style.css" rel="stylesheet" type="text/css"/>	
        
        <!-- Theme Styles -->
        <link href="${pageContext.servletContext.contextPath}/resources/css/modern.min.css" rel="stylesheet" type="text/css"/>
        <link href="${pageContext.servletContext.contextPath}/resources/css/themes/white.css" class="theme-color" rel="stylesheet" type="text/css"/>
        <link href="${pageContext.servletContext.contextPath}/resources/css/custom.css" rel="stylesheet" type="text/css"/>
        
        <script src="${pageContext.servletContext.contextPath}/resources/plugins/3d-bold-navigation/js/modernizr.js"></script>
        <script src="${pageContext.servletContext.contextPath}/resources/plugins/offcanvasmenueffects/js/snap.svg-min.js"></script>
       
    </head>
    <body class="page-login">
        <div class="page-content">
            <div class="page-inner">
                <div id="main-wrapper">
                    <div class="row">
                        <div class="col-md-3 center">
                            <div class="login-box">
                                <a href="index.html" class="logo-name text-lg text-center">Sistem Informasi Akademik</a>
                                <p class="text-center m-t-md">Mohon masuk dengan akun anda.</p>
                                <c:if test="${not empty errorMessage}">
	                                <div class="alert alert-danger alert-dismissible" role="alert">
	                                	<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">×</span></button>
	                                       ${errorMessage}
	                                </div>
                                </c:if>
                                <form class="m-t-md" action="${pageContext.servletContext.contextPath}/j_spring_security_check" method="post">
                                    <div class="form-group">
                                        <input type="text" class="form-control" placeholder="Email" required name="username">
                                    </div>
                                    <div class="form-group">
                                        <input type="password" class="form-control" placeholder="Password" required name="password">
                                    </div>
                                    <button type="submit" class="btn btn-success btn-block">Login</button>
                                </form>
                                <p class="text-center m-t-xs text-sm">2015 &copy; LBE RPL Teknik Informatika ITS.</p>
                            </div>
                        </div>
                    </div><!-- Row -->
                </div><!-- Main Wrapper -->
            </div><!-- Page Inner -->
        </div><!-- Page Content -->

        <!-- Javascripts -->
        <script src="${pageContext.servletContext.contextPath}/resources/plugins/jquery/jquery-2.1.3.min.js"></script>
        <script src="${pageContext.servletContext.contextPath}/resources/plugins/jquery-ui/jquery-ui.min.js"></script>
        <script src="${pageContext.servletContext.contextPath}/resources/plugins/pace-master/pace.min.js"></script>
        <script src="${pageContext.servletContext.contextPath}/resources/plugins/jquery-blockui/jquery.blockui.js"></script>
        <script src="${pageContext.servletContext.contextPath}/resources/plugins/bootstrap/js/bootstrap.min.js"></script>
        <script src="${pageContext.servletContext.contextPath}/resources/plugins/jquery-slimscroll/jquery.slimscroll.min.js"></script>
        <script src="${pageContext.servletContext.contextPath}/resources/plugins/switchery/switchery.min.js"></script>
        <script src="${pageContext.servletContext.contextPath}/resources/plugins/uniform/jquery.uniform.min.js"></script>
        <script src="${pageContext.servletContext.contextPath}/resources/plugins/offcanvasmenueffects/js/classie.js"></script>
        <script src="${pageContext.servletContext.contextPath}/resources/plugins/offcanvasmenueffects/js/main.js"></script>
        <script src="${pageContext.servletContext.contextPath}/resources/plugins/waves/waves.min.js"></script>
        <script src="${pageContext.servletContext.contextPath}/resources/plugins/3d-bold-navigation/js/main.js"></script>
        <script src="${pageContext.servletContext.contextPath}/resources/js/modern.min.js"></script>
        
    </body>
    
</html>