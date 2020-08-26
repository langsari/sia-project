<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>Indeks Prestasi Kumulatif</title>
		
		<link href="${pageContext.servletContext.contextPath}/resources/plugins/uniform/css/uniform.default.min.css" rel="stylesheet" />
		<link href="${pageContext.servletContext.contextPath}/resources/plugins/line-icons/simple-line-icons.css" rel="stylesheet" type="text/css" />
		<link href="${pageContext.servletContext.contextPath}/resources/plugins/switchery/switchery.min.css" rel="stylesheet" type="text/css" />
		<link href="${pageContext.servletContext.contextPath}/resources/plugins/3d-bold-navigation/css/style.css" rel="stylesheet" type="text/css" />
		<link href="${pageContext.servletContext.contextPath}/resources/plugins/slidepushmenus/css/component.css" rel="stylesheet" type="text/css" />
		<link href="${pageContext.servletContext.contextPath}/resources/plugins/bootstrap-datepicker/css/datepicker3.css" rel="stylesheet" type="text/css"/>
			
		<link href="${pageContext.servletContext.contextPath}/resources/css/custom.css" rel="stylesheet" type="text/css" />
		<script src="${pageContext.servletContext.contextPath}/resources/plugins/3d-bold-navigation/js/modernizr.js"></script>
		<script src="${pageContext.servletContext.contextPath}/resources/plugins/offcanvasmenueffects/js/snap.svg-min.js"></script>
		
		<link href="${pageContext.servletContext.contextPath}/resources/plugins/jquery.datatables/media/css/jquery.dataTables.min.css" rel="stylesheet" type="text/css" />
	</head>

	<body>
	
		<div class="container">
			<div class="row">
				<div class="col-md-6 col-md-offset-3">
					<div class="panel panel-white">
						<div class="panel-heading">
							<h4 class="panel-title">Ranking IPK</h4>
						</div>
						<div class="panel-body">
							<div class="row">
								<div class="col-md-12">
									<div class="form-group pull-right">
										<div class="form-inline">
											<label for="filterAngkatan">Angkatan</label>
											<select class="form-control" id="filterAngkatan">
												<option value=""></option>
											</select>
										</div>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-md-12">
									<table class="table" id="tabel_ipk">
										<thead>
											<tr>
												<th>NRP</th>
												<th>Nama</th>
												<th>IPK</th>
												<th>Angkatan</th>
											</tr>
										</thead>
										<tbody>
											<c:forEach var="ipk" items="${listIpk}">
											<tr>
												<td><c:out value="${ipk.getPd().getNimPd()}"></c:out></td>
												<td><c:out value="${ipk.getPd().getNmPd()}"></c:out></td>
												<td><fmt:formatNumber value="${ipk.getNilaiIpk()}" maxFractionDigits="2"></fmt:formatNumber></td>
												<td><c:out value="${ipk.getPd().getAngkatanPd()}"></c:out></td>
											</tr>
											</c:forEach>
										</tbody>
									</table>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<!-- end of content -->
		
		<!-- script custom -->
		<content tag="scripts">	
			<%@include file="footer.jsp" %>
			
			<script src="${pageContext.servletContext.contextPath}/resources/plugins/jquery.datatables/media/js/jquery.dataTables.min.js"></script>
	        <script>
		        $(document).ready(function() {
					var tabel = $("#tabel_ipk").DataTable({
						"columnDefs" : [{"targets" : [3], "visible" : false}]
					});
					
					tabel.column(3).cache("search").sort().unique().each(function(d) {
						$("#filterAngkatan").append($('<option value="'+d+'">'+d+'</option>'));
					});
					
					$("#filterAngkatan").on("change", function() {
						tabel.column(3).search($(this).val()).draw();
					});
		        });
			</script>
		</content>
	</body>
</html>