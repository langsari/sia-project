<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>Kelola Nilai Kelas</title>
		
		<!-- Styles -->
		<link href="${pageContext.servletContext.contextPath}/resources/plugins/uniform/css/uniform.default.min.css" rel="stylesheet" />
		<link href="${pageContext.servletContext.contextPath}/resources/plugins/line-icons/simple-line-icons.css" rel="stylesheet" type="text/css" />
		<link href="${pageContext.servletContext.contextPath}/resources/plugins/switchery/switchery.min.css" rel="stylesheet" type="text/css" />
		<link href="${pageContext.servletContext.contextPath}/resources/plugins/3d-bold-navigation/css/style.css" rel="stylesheet" type="text/css" />
		<link href="${pageContext.servletContext.contextPath}/resources/plugins/slidepushmenus/css/component.css" rel="stylesheet" type="text/css" />
		<link href="${pageContext.servletContext.contextPath}/resources/plugins/bootstrap-datepicker/css/datepicker3.css" rel="stylesheet" type="text/css"/>
			
		<!-- Theme Styles -->
		<link href="${pageContext.servletContext.contextPath}/resources/css/custom.css" rel="stylesheet" type="text/css" />
			
		<script src="${pageContext.servletContext.contextPath}/resources/plugins/3d-bold-navigation/js/modernizr.js"></script>
		<script src="${pageContext.servletContext.contextPath}/resources/plugins/offcanvasmenueffects/js/snap.svg-min.js"></script>
		
	</head>
	<body>
		
		<!-- content -->
		<div class="container">
			<div class="row">
				<div class="col-md-6 col-md-offset-3" class="content">
					<div class="panel panel-white">
						<div class="panel-heading">
							<h4 class="panel-title">Daftar Kelas</h4>
						</div>
						<div class="panel-body">
							<c:if test="${pesan != null}">
								<div class="alert alert-danger" role="alert">
									<c:out value="${pesan}"></c:out>
								</div>
							</c:if>
							<form method="post" action="">
								<div class="form-group">
									<select class="form-control" name="idPemb">
										<c:forEach var="kelas" items="${listKelas}">
											<option value="${kelas.getIdPemb()}"><c:out value="${kelas.getMk().getNamaMK()} ${kelas.getNmPemb()}"></c:out></option>
										</c:forEach>
									</select>
								</div>
								<button type="submit" class="btn btn-primary pull-right">Buka</button>
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
		<!-- end of content -->
		<content tag="scripts">
			<%@include file="footer.jsp" %>
			
			<script>
				$(document).ready(function() {
					$("form").submit(function(e) {
						if($("#pilihanTglSmt option:selected").val() == "") {
							e.preventDefault();
						}
					});
				});
			</script>
		</content>
	</body>
</html>