<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
    <%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
    <%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>Kuisioner Per Periode</title>
		
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
		
		<!-- css dan js spesifik -->
		<link
			href="${pageContext.servletContext.contextPath}/resources/plugins/jquery.datatables/media/css/jquery.dataTables.min.css"
			rel="stylesheet" type="text/css" />
	</head>
	<body>
		<!-- content -->
		<div class="container">
			<div class="row">
				<div class="col-md-6">
					<div class="panel panel-white">
						<div class="panel-heading">
							<h4 class="panel-title">Laporan Kuisioner Per Periode</h4>
						</div>
						<div class="panel-body">
							<form method="post" action="">
								<div class="form-group">
									<select class="form-control" name="idTglSmt">
										<c:forEach var="tglSmt" items="${daftarTglSmt}">
											<option value="${tglSmt.getIdTglSmt()}"><c:out value="${tglSmt.getSmt().getNmSmt()} ${tglSmt.getThnAjaran().getThnThnAjaran()}"></c:out></option>
										</c:forEach>
									</select>
								</div>
								<button type="submit" class="btn btn-primary pull-right">Buka</button>
							</form>
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-md-12">
					<div class="panel panel-white">
						<div class="panel-heading">
							<h4 class="panel-title">Laporan Kuisioner</h4>
						</div>
						<div class="panel-body">
							<table class="table">
								<thead>
									<tr>
										<th rowspan="2">Nama Kelas</th>
										<th rowspan="2">Nama Dosen</th>
										<th rowspan="2">Nilai IPD</th>
										<th colspan="${fn:length(daftarKuisioner)}">Kuisioner</th>									
									</tr>
									<tr>
										<c:forEach var="kuisioner" items="${daftarKuisioner}">
											<th><c:out value="${kuisioner.getNmKuisioner()}"></c:out></th>
										</c:forEach>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="pemb" items="${daftarPemb}">
										<tr id="${pemb.getIdPemb()}">
											<td><c:out value="${pemb.getMk().getNamaMK()} ${pemb.getNmPemb()}"></c:out></td>
											<c:set var="dosenFound" value="false" scope="page"></c:set>
											<c:forEach var="dosen" items="${daftarKetuaPendidik}">
												<c:if test="${dosen.getPemb().getIdPemb() == pemb.getIdPemb()}">
													<c:set var="dosenFound" value="true" scope="page"></c:set>
													<td><c:out value="${dosen.getPtk().getNmPtk()}"></c:out></td>
													<td><fmt:formatNumber value="${dosen.getNilaiIpd()}" maxFractionDigits="2"></fmt:formatNumber></td>
												</c:if>
											</c:forEach>
											<c:if test="${dosenFound == false}">
												<td>-</td>
												<td>-</td>
											</c:if>
											<c:forEach var="kuisioner" items="${daftarKuisioner}">
												<c:forEach var="nilai" items="${daftarNilai}">
													<c:if test="${nilai.getIdPemb() == pemb.getIdPemb() && nilai.getIdKuisioner() == kuisioner.getIdKuisioner()}">
														<td><fmt:formatNumber value="${nilai.getNilai()}" maxFractionDigits="2"></fmt:formatNumber></td>
													</c:if>
												</c:forEach>
											</c:forEach>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
					</div>
				</div>
			</div>
		</div>
		
		<content tag="scripts">
		
			<script
				src="${pageContext.servletContext.contextPath}/resources/plugins/jquery.datatables/media/js/jquery.dataTables.min.js"></script>
		
			<script>
				$(document).ready(function() {
					$("table").DataTable();
				});
			</script>
			
			<%@include file="footer.jsp" %>
		</content>
	</body>
</html>