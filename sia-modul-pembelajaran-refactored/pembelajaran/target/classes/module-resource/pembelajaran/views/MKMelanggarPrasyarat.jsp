<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page session="false" %>

		
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<link rel="stylesheet" href="${pageContext.servletContext.contextPath}/resources/plugins/jquery.datatables/media/css/jquery.dataTables.min.css">
		<link rel="stylesheet" href="${pageContext.servletContext.contextPath}/resources/plugins/jquery.datatables/extensions/ColVis/css/dataTables.colVis.min.css">
		<link href="${pageContext.servletContext.contextPath}/resources/plugins/gritter/css/jquery.gritter.css" rel="stylesheet" type="text/css" />
	</head>
	<body>
	<div class="row" id="masterpage">
				<div class="col-md-12">
				<div class="panel panel-white">
							<div class="panel-heading">
								<h4 class="panel-title">${rombel.nmRombel}</h4>
							</div>
							<div class="panel-body">
					<div class="table-responsive">
						<table id="tableKrs" class="table table-striped table-bordered table-hover">
							<thead>
								<tr>
									<td>Kode MK</td>
									<td>Matakuliah</td>
									<td>Kode MK Prasyarat</td>
									<td>MK Prasyarat</td>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${listMK}" var="mk" varStatus="status">
									<tr>
										<td>${mk.kodeMK}</td>
										<td>${mk.namaMK}</td>
										<td>${listMKPrasyarat[status.index].kodeMK}</td>
										<td>${listMKPrasyarat[status.index].namaMK}</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</div>
			</div>
		</div>
		<content tag="scripts">			
			<script type="text/javascript" src="${pageContext.servletContext.contextPath}/resources/plugins/jquery.datatables/media/js/jquery.dataTables.min.js"></script>
			<script type="text/javascript" src="${pageContext.servletContext.contextPath}/resources/plugins/jquery.datatables/extensions/ColVis/js/dataTables.colVis.min.js"></script>
			<script src="${pageContext.servletContext.contextPath}/resources/plugins/bootstrap-datepicker/js/bootstrap-datepicker.js" type="text/javascript"></script>
			<script src="${pageContext.servletContext.contextPath}/resources/plugins/gritter/js/jquery.gritter.js" rel="stylesheet" type="text/javascript"></script>
			<script src="${pageContext.servletContext.contextPath}/resources/plugins/jquery-validation/jquery.validate.min.js" rel="stylesheet" type="text/javascript"></script>
			<script	src="${pageContext.servletContext.contextPath}/resources/js/jquery.masterpage.sia.js" type="text/javascript"></script>
			
			<jsp:include page="Footer.jsp" />
		</content>
	</body>
</html>