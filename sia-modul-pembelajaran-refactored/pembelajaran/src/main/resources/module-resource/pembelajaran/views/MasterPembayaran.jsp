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
							<div class="panel-heading clearfix">
								<h4 class="panel-title">Pembayaran</h4>
							</div>
							<div class="panel-body">
						<div class="row">
						<div class="col-md-6 form-horizontal">
							<div class="form-group">
								<label class="col-sm-4 control-label">Prodi</label>
								<div class="col-sm-8">
									<select class="form-control" id="filter3">
									<c:forEach items="${listSatMan}" var="satMan">
										<option value="${satMan.idSatMan}">${satMan.nmSatMan}</option>
									</c:forEach>
									</select>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-4 control-label">Tahun Ajaran</label>
								<div class="col-sm-8">
									<select class="form-control" id="filter">
										<c:forEach items="${listThnAjaran}" var="thnAjaran">
											<option 
												<c:if test="${thnAjaran.idThnAjaran == smtAktif.thnAjaran.idThnAjaran}"> selected="true"</c:if>
												value="${thnAjaran.idThnAjaran}">${thnAjaran.thnThnAjaran}</option>
										</c:forEach>
									</select>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-4 control-label">Semester</label>
								<div class="col-sm-8">
									<select class="form-control" id="filter2">
										<c:forEach items="${listSmt}" var="smt">
											<option
												<c:if test="${smt.idSmt == smtAktif.smt.idSmt}"> selected="true"</c:if> 
												value="${smt.idSmt}">${smt.nmSmt}</option>
										</c:forEach>
									</select>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-4 control-label">Angkatan</label>
								<div class="col-sm-8">
									<select class="form-control" id="filter4">
										<option value="">Semua</option>
										<c:forEach items="${listAngkatan}" var="angkatan">
											<option value="${angkatan}">${angkatan}</option>
										</c:forEach>
									</select>
								</div>
							</div>
						</div>
						<div class="col-md-6 masteractions panel-body">
							<div class="btn-action pull-right">
							</div>
						</div>
					</div>
					<form class="tableform">
						<div class="table-responsive">
						<table class="table table-striped table-bordered table-hover table-checkable table-colvis datatable">
							<thead>
								<tr>
									<td>Tahun Ajaran</td>
									<td>Semester</td>
									<td>NIM</td>
									<td>Nama</td>
									<td>Angkatan</td>
									<td>Tanggal Pembayaran</td>
								</tr>
							</thead>
							<tbody>
							</tbody>
						</table>
						</div>
					</form>
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
			<script>
			$(document).ready(function(){
				$('#masterpage').masterPage(
				{
					detailFocusId: '#idKrs',
					dataUrl: context_path+'modul/pembelajaran/pembayaran/json',
					primaryKey: 'idKrs',
			        order: [[2,"desc"]],
					editOnClick: false,
					editOnClickRow: false,
					showAddButton: false,
					showDelButton: false,
					cols: [
						/* nimPd */
						{ "bVisible":    false },
						/* nmPd */
						{ "bVisible":    false },
						/* angkatanPd */
						{ "bVisible":    true },
						/* namaMK */
						{ "bVisible":    true },
						/* nmPemb */
						{ "bVisible":    true },
						/* jumlahSKS */
						{ "bVisible":    true }
					],
					filters: [{id:'#filter2', name:'idSmt'},{id:'#filter', name:'idThnAjaran'},{id:'#filter3', name:'idSatMan'},{id:'#filter4', name:'angkatan'}]
				});
			});
		</script>
			<jsp:include page="Footer.jsp" />
		</content>
	</body>
</html>