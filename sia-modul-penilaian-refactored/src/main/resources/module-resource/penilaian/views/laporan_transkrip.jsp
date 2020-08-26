<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>Transkrip</title>
		
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
				<div class="col-md-8 col-md-offset-2">
					<div class="panel panel-white">
						<div class="panel-heading">
							<h4 class="panel-title">Transkrip Nilai</h4>
						</div>
						<div class="panel-body">
							<p>Nama : <c:out value="${pd.getNmPd()}"></c:out></p>
							<p>NIM : <c:out value="${pd.getNimPd()}"></c:out></p>
							<p>IPK : <fmt:formatNumber value="${ipk.getNilaiIpk()}" maxFractionDigits="2"></fmt:formatNumber></p>
							
							<table class="table">
								<thead>
									<tr>
										<th>Kode MK</th>
										<th>Mata Kuliah</th>
										<th>SKS</th>
										<th>Nilai</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="krs" items="${daftarKrs}">
										<tr>
											<td><c:out value="${krs.getPemb().getMk().getKodeMK()}"></c:out></td>
											<td><c:out value="${krs.getPemb().getMk().getNamaMK()}"></c:out></td>
											<td><c:out value="${krs.getPemb().getMk().getJumlahSKS()}"></c:out></td>
											<c:choose>
												<c:when test="${krs.getKonversiNilai() != null}">
													<td><c:out value="${krs.getKonversiNilai().getHuruf()}"></c:out></td>
												</c:when>
												<c:otherwise>
													<td>-</td>
												</c:otherwise>
											</c:choose>
										</tr>
									</c:forEach>
								</tbody>
							</table>
							
							<form action="${pageContext.servletContext.contextPath}/cetak_transkrip/" method="post" target="_blank">
								<input type="hidden" value="${pd.getIdPd()}" name="idPd" />
								<button type="submit" class="btn btn-primary center"><span class="glyphicon glyphicon-print"></span> Versi Cetak</button>
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
		<!-- end of content -->
		
		<content tag="scripts">	
			<%@include file="footer.jsp" %>
		</content>
	</body>
</html>