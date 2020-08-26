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
       	<script>
			var contextPath = "${pageContext.servletContext.contextPath}";
		</script>
       
    </head>
    <body class="page-login">
        <div class="page-content">
            <div class="page-inner">
                <div id="main-wrapper">
                    <div class="row">
                        <div class="col-md-3 center">
                            <div class="login-box">
                                <a href="index.html" class="logo-name text-lg text-center">Sistem Informasi Akademik</a>
                                <p class="text-center m-t-md">Pilih Hak Akses</p>
                                <c:if test="${not empty userRoleFail}">
	                                <div class="alert alert-danger alert-dismissible" role="alert">
	                                	<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">×</span></button>
	                                       ${userRoleFail.getMessage()}
	                                </div>
                                </c:if>
                                <form id="formPeran" class="m-t-md" method="post" action="${pageContext.servletContext.contextPath}/session/chooseUserRole/">
                                    <div class="form-group">
                                    	<select id="role-select" name="idPeran" class="form-control">
                                    		<option value="">-- Peran --</option>
                                    		<c:forEach items="${peranList}" var="peran">
                                    			<option value="${peran.getIdPeran()}">${peran.getNamaPeran()}</option>
                                    		</c:forEach>
                                    	</select>
                                    	<br/>
                                    	<select id="satman-select" name="idSatMan" class="form-control" style="display: none;">
                                    		<option value="">-- Satuan Manajemen --</option>
                                    	</select>
                                    </div>
                                    <button id="submit-btn" type="submit" class="btn btn-success btn-block disabled">Pilih</button>
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

		<script>
			$("#role-select").on("change", function() {
				var id = $(this).val();
				$.ajax({
					url: contextPath + "/session/getSatMan",
					type: "POST",
					data: {
						"idPeran": id,
						"idPengguna": ""
					},
					success: function(response) {
						$("#satman-select").css("display", "");
						$("#satman-select").empty();
						$("#satman-select").append("<option value=\"\">-- Satuan Manajemen --</option>");
						for(var i = 0 ; i < response.length; i++) {
							console.log(response[i].namaSatMan);
							$("#satman-select").append("<option value=" + response[i].idSatMan + ">" + response[i].namaSatMan + "</option>");
						}
					}
				});
			});
			
			$("#satman-select").on("change", function() {
				var id = $(this).val();
				if(id != null && id != "") {
					$("#submit-btn").removeClass("disabled");
				}
				else {
					$("#submit-btn").toggleClass("disabled");
				}
			});
		</script>

    </body>
    
</html>