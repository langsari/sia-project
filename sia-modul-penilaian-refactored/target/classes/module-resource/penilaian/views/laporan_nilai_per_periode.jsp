<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>Lihat Nilai Per Periode</title>
		
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
				<div class="col-md-12">
					<div class="panel panel-white">
						<div class="panel-heading">
							<h4 class="panel-title">Daftar Nilai</h4>
						</div>
						<div class="panel-body">
							<p>Nama : <c:out value="${pd.getNmPd()}"></c:out></p>
							<p>NIM : <c:out value="${pd.getNimPd()}"></c:out></p>
							
							<c:forEach var="ips" items="${daftarIps}">
								<div class="row">
									<div class="col-md-8 col-md-offset-2">
										<table class="table">
											<thead>
												<tr>
													<th colspan="4">
														Periode : <c:out value="${ips.getTglSmt().getSmt().getNmSmt()} ${ips.getTglSmt().getThnAjaran().getThnThnAjaran()}"></c:out><br />
														IPS : <fmt:formatNumber value="${ips.getNilaiIps()}" maxFractionDigits="2"></fmt:formatNumber>
													</th>
												</tr>
												<tr>
													<th>Kode MK</th>
													<th>Mata Kuliah</th>
													<th>SKS</th>
													<th>Nilai</th>
												</tr>
											</thead>
											<tbody>
												<c:set var="jumlahSks" value="0" scope="page"></c:set>
												<c:forEach var="krs" items="${daftarKrs}">
													<c:if test="${krs.getPemb().getTglSmt().getIdTglSmt() == ips.getTglSmt().getIdTglSmt()}">
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
													<c:set var="jumlahSks" value="${jumlahSks + krs.getPemb().getMk().getJumlahSKS()}" scope="page"></c:set>
													</c:if>
												</c:forEach>
											</tbody>
											<tfoot>
												<tr>
													<th></th>
													<th>Jumlah SKS</th>
													<th><c:out value="${jumlahSks}"></c:out></th>
													<th></th>
												</tr>
											</tfoot>
										</table>
									</div>
								</div>
							</c:forEach>
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