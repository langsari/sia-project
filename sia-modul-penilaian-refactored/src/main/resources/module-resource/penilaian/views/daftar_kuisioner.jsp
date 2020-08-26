<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>Daftar Kuisioner</title>
		
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
							<h4 class="panel-title">Daftar Kuisioner</h4>
						</div>
						<div class="panel-body">
							<form id="formKuisioner" method="post" action="">
								<div class="form-group">
									<select class="form-control" name="idKuisioner">
										<c:set var="count" value="0" scope="page"/>
										<option value=""></option>
										<c:forEach var="krs" varStatus="status" items="${daftarKrs}">
											<optgroup name="${krs.getIdKrs()}" label="${krs.getPemb().getMk().getNamaMK()} ${krs.getPemb().getNmPemb()}">
												<c:forEach var="kuisioner" items="${daftarKuisioner}">
												
													<c:choose>
														<c:when test="${daftarStatus[count]}">
															<c:set var="disabled" value="disabled" scope="page"/>
															<c:set var="keterangan" value="-Sudah Terisi-" scope="page"/>
														</c:when>
														<c:otherwise>
															<c:set var="disabled" value="" scope="page"/>
															<c:set var="keterangan" value="" scope="page"/>
														</c:otherwise>
													</c:choose>
													
													<option value="${kuisioner.getIdKuisioner()}" ${empty disabled ? '' : 'disabled'}><c:out value="${kuisioner.getNmKuisioner()} ${keterangan}"></c:out></option>
													<c:set var="count" value="${count + 1}" scope="page"/>
												</c:forEach>
											</optgroup>
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
			<script>
				$(document).ready(function() {
					$("#formKuisioner").submit(function(e) {
						if($("select option:selected").val() == "") {
							e.preventDefault();
						}
						else {
							var idKrs = $("select option:selected").closest('optgroup').attr('name');
							$("#formKuisioner").append('<input type="hidden" name="idKrs" value="' + idKrs + '" />');
						}
					});
				});
			</script>
			
			<%@include file="footer.jsp" %>
		</content>
	</body>
</html>