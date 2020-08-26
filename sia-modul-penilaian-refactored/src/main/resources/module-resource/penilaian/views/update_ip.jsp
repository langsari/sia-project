<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>Update IP</title>
		
		<!-- Styles -->
		<link
			href="${pageContext.servletContext.contextPath}/resources/plugins/uniform/css/uniform.default.min.css"
			rel="stylesheet" />
		<link
			href="${pageContext.servletContext.contextPath}/resources/plugins/line-icons/simple-line-icons.css"
			rel="stylesheet" type="text/css" />
		<link
			href="${pageContext.servletContext.contextPath}/resources/plugins/switchery/switchery.min.css"
			rel="stylesheet" type="text/css" />
		<link
			href="${pageContext.servletContext.contextPath}/resources/plugins/3d-bold-navigation/css/style.css"
			rel="stylesheet" type="text/css" />
		<link
			href="${pageContext.servletContext.contextPath}/resources/plugins/slidepushmenus/css/component.css"
			rel="stylesheet" type="text/css" />
			<link href="${pageContext.servletContext.contextPath}/resources/plugins/bootstrap-datepicker/css/datepicker3.css" rel="stylesheet" type="text/css"/>
			
		<!-- Theme Styles -->
		<link
			href="${pageContext.servletContext.contextPath}/resources/css/custom.css"
			rel="stylesheet" type="text/css" />
			
		<script
			src="${pageContext.servletContext.contextPath}/resources/plugins/3d-bold-navigation/js/modernizr.js"></script>
		<script
			src="${pageContext.servletContext.contextPath}/resources/plugins/offcanvasmenueffects/js/snap.svg-min.js"></script>
	
	</head>
	<body>
		<!-- content -->
		<div class="container">
			<div class="row">
				<div class="col-md-6 col-md-offset-3">
					<div class="panel panel-white">
						<div class="panel-heading">
							<h4 class="panel-title">Daftar Perintah</h4>
						</div>
						<div class="panel-body">
							<button type="button" class="btn btn-primary" id="updateIPS" data-loading-text="Memperbaharui IPS...">Update IPS</button>
							<button type="button" class="btn btn-primary" id="updateIPK" data-loading-text="Memperbaharui IPK...">Update IPK</button>
							<button type="button" class="btn btn-primary" id="updateIPD" data-loading-text="Memperbaharui IPD...">Update IPD</button>
						</div>
					</div>
				</div>
			</div>
		</div>
		<!-- end of content -->
		
		<content tag="scripts">	
			<script>
				$(document).ready(function() {
					
					$("#updateIPS").click(function() {
						$("#updateIPS").button("loading");
						$.ajax({
							url : contextPath + "/modul/penilaian/update_ips/",
							type : "POST",
							success : function(data) {
								if(data.status=="ok") {
									toastr["success"](data.message, "Sukses");
								}
								$("#updateIPS").button("reset");
							}
						});
					});
					
					$("#updateIPK").click(function() {
						$("#updateIPK").button("loading");
						$.ajax({
							url : contextPath + "/modul/penilaian/update_ipk/",
							type : "POST",
							success : function(data) {
								if(data.status=="ok") {
									toastr["success"](data.message, "Sukses");
								}
								$("#updateIPK").button("reset");
							}
						});
					});
					
					$("#updateIPD").click(function() {
						$("#updateIPD").button("loading");
						$.ajax({
							url : contextPath + "/modul/penilaian/update_nilai_dosen/",
							type : "POST",
							success : function(data) {
								if(data.status=="ok") {
									toastr["success"](data.message, "Sukses");
								}
								$("#updateIPD").button("reset");
							}
						});
					});
				});
			</script>
			<%@include file="footer.jsp" %>
		</content>
	</body>
</html>