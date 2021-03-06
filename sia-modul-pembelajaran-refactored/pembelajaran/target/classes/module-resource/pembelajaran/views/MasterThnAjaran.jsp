<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page session="false"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<link rel="stylesheet" href="${pageContext.servletContext.contextPath}/resources/plugins/jquery.datatables/media/css/jquery.dataTables.min.css">
		<link rel="stylesheet" href="${pageContext.servletContext.contextPath}/resources/plugins/jquery.datatables/extensions/ColVis/css/dataTables.colVis.min.css">
		<link href="${pageContext.servletContext.contextPath}/resources/plugins/gritter/css/jquery.gritter.css" rel="stylesheet" type="text/css" />
	</head>
	<body>
	<div class="row" id="masterpage">
					<div class="col-md-12" style="margin-bottom: 10px;">
						<div class="panel panel-white">
							<div class="panel-heading">
								<h4 class="panel-title">Tahun Ajaran</h4>
							</div>
							<div class="panel-body">

								<div class="row">
									<div class="col-md-4">
										<div class="form-group">
											<label>Status Aktif</label> <select id="filter" name="filter">
												<option value="false">Aktif</option>
												<option value="">Semua</option>
											</select>
										</div>
									</div>
									<div class="col-md-8 masteractions panel-body">
										<div class="btn-action pull-right"></div>
									</div>
								</div>
								<form class="tableform">
									<div class="table-responsive">
									<table
										class="table table-striped table-bordered table-hover table-checkable table-colvis datatable">
										<thead>
											<tr>
												<td>
													<input class="checkbox-all" type="checkbox"
															id="flat-checkbox-1">
												</td>
												<td>Tahun</td>
												<td>Minimal Kehadiran Peserta Didik</td>
												<td>Minimal Pertemuan Pembelajaran</td>
												<td>Status hapus</td>
												<td>Aksi</td>
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
			</div>

			<div class="row" id="master-detail" style="display: none;">
					<div class="col-md-6 col-md-offset-3">
						
						<div class="panel panel-white">
							<div class="panel-heading">
								<h4 class="panel-title">Detil Tahun Ajaran</h4>
							</div>
							<div class="panel-body">
								<form:form role="form" commandName="thnAjaran"
									class="formdetail">
									<div class="form-group">
										<label>Tahun</label>
										<form:input path="thnThnAjaran" class="form-control"
											placeholder="Berisi tahun ajaran" required="true"
											digits="true" />
										<form:hidden path="idThnAjaran" class="form-control" />
									</div>
									<div class="form-group">
										<label>Minimal Pertemuan Pembelajaran</label>
										<form:input path="persenMinimPertemuan" class="form-control"
											placeholder="Berisi minimal pertemuan pembelajaran dalam persen"
											required="true" />
									</div>
									<div class="form-group">
										<label>Minimal Kehadiran Peserta Didik</label>
										<form:input path="persenHadirMinimPd" class="form-control"
											placeholder="Berisi minimal kehadiran peserta didik dalam persen"
											required="true" />
									</div>
									<div class="form-group detailcontrol"></div>
								</form:form>

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
			<script>
				$(document)
						.ready(
								function() {
									$('#masterpage')
											.masterPage(
													{
														detailFocusId : '#idThnAjaran',
														dataUrl : context_path
																+ 'modul/pembelajaran/tahunajaran/json',
														detailUrl : context_path
																+ 'modul/pembelajaran/tahunajaran/edit',
														addUrl : context_path
																+ 'modul/pembelajaran/tahunajaran/simpan',
														editUrl : context_path
																+ 'modul/pembelajaran/tahunajaran/simpan',
														deleteUrl : context_path
																+ 'modul/pembelajaran/tahunajaran/deletemany',
														primaryKey : 'idThnAjaran',
														order : [ [ 1, "desc" ] ],
														editOnClick : false,
														editOnClickRow : true,
														cols : [
																/* id */
																{
																	"bVisible" : true,
																	bSortable : false,
																	mRender : function(
																			data,
																			type,
																			full) {
																		return '<input class="checkbox-data" type="checkbox" name="idThnAjaran[]" value="'+data+'"/>';
																	}
																},
																/* Tahun */
																{
																	"bVisible" : true
																},
																/* Minimal Kehadiran Peserta Didik */
																{
																	"bVisible" : true
																},
																/* Minimal Kehadiran Peserta Didik */
																{
																	"bVisible" : true
																},
																/* Status Aktif */
																{
																	"bVisible" : false,
																	mRender : function(
																			data,
																			type,
																			full) {
																		if (full[4] == "false")
																			return "Aktif";
																		else
																			return "Terhapus";
																	}
																},
																/* Aksi */
																{
																	"bVisible" : true,
																	bSortable : false,
																	mRender : function(
																			data,
																			type,
																			full) {
																		var action = '<button type="button" class="btn btn-primary editrow">Edit</button>';
																		if (full[4] == 'false')
																			action += ' <button type="button" class="btn btn-danger deleterow">Hapus</button>'
																		return action;
																	}
																} ],
														validationRules : {
															idThnAjaran : {
																required : false
															},
															thnThnAjaran : {
																required : true,
																digits : true
															},
															persenMinimPertemuan : {
																required : true
															},
															persenHadirMinimPd : {
																required : true
															}
														},
														filters : [ {
															id : '#filter',
															name : 'a_thn_ajaran_terhapus'
														} ]
													});
								});
			</script>
			<jsp:include page="Footer.jsp" />
		</content>
	</body>
</html>
