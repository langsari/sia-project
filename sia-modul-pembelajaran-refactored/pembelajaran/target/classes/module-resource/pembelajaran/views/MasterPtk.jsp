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
				<div class="col-md-12">
					<div class="panel panel-white">
						<div class="panel-heading clearfix">
							<h4 class="panel-title">Pendidik dan Tenaga Kependidikan</h4>
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
									<div class="form-group">
										<label>Status PTK</label> <select id="filter2" name="filter2"
											class="form-control">
											<option value="">Semua</option>
											<option value="true">Pendidik</option>
											<option value="false">Tenaga Kependidikan</option>
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
													<input class="checkbox-all" type="checkbox">
												</td>
												<td>NIP</td>
												<td>Nama</td>
												<td>Status</td>
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
							<div class="panel-heading clearfix">
								<h4 class="panel-title">Detil Pendidik dan Tenaga
									Kependidikan</h4>
							</div>
							<div class="panel-body">
								<form:form role="form" commandName="ptk" class="formdetail">
									<div class="form-group">
										<label>NIP</label>
										<form:input path="nipPtk" class="form-control"
											placeholder="Berisi nip Pendidik/Tenaga Kependidikan" />
										<form:hidden path="idPtk" class="form-control" />
									</div>
									<div class="form-group">
										<label>Nama</label>
										<form:input path="nmPtk" class="form-control"
											placeholder="Berisi nama Pendidik/Tenaga Kependidikan" />
									</div>
									<div class="form-group">
										<label>Status</label> <select name="statusPtk" id="statusPtk"
											class="form-control">
											<option value="true">Pendidik</option>
											<option value="false">Tenaga kependidikan</option>
										</select>
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
						$(document).ready(function() {
								$('#masterpage').masterPage({
											detailFocusId : '#idPtk',
											dataUrl : context_path+ 'modul/pembelajaran/ptk/json',
											detailUrl : context_path+ 'modul/pembelajaran/ptk/edit',
											addUrl : context_path+ 'modul/pembelajaran/ptk/simpan',
											editUrl : context_path+ 'modul/pembelajaran/ptk/simpan',
											deleteUrl : context_path+ 'modul/pembelajaran/ptk/deletemany',
											primaryKey : 'idPtk',
											order : [ [ 2,"asc" ] ],
											editOnClick : false,
											editOnClickRow : true,
											cols : [
													/* idPtk */
													{
														"bVisible" : true,
														bSortable : false,
														bSearchable : false,
														mRender : function(data,type,full) {
															return '<input type="checkbox" class="checkbox-data" name="idPtk[]" value="'+data+'"/>';
														}
													},
													/* nipPtk */
													{
														"bVisible" : true
													},
													/* nmPtk */
													{
														"bVisible" : true
													},
													/* statusPtk */
													{
														"bVisible" : true,
														mRender : function(data,type,full) {
															if (full[3] == 'false')
																return "Tenaga Kependidikan";
															else
																return "Pendidik";
														}
													},
													/* aPtkTerhapus */
													{
														"bVisible" : false
													},
													/* Aksi */
													{
														"bVisible" : true,
														bSortable : false,
														bSearchable : false,
														mRender : function(data,type,full) {
															var action = '<button type="button" class="btn btn-primary editrow">Edit</button>';
															if (full[4] == 'false')
																action += ' <button type="button" class="btn btn-danger deleterow">Hapus</button>'
															return action;
														}
													} ],
											validationRules : {idPtk : {required : false},nipPtk : {required : true},nmPtk : {required : true}},
											filters : [
													{
														id : '#filter',
														name : 'a_ptk_terhapus'
													},
													{
														id : '#filter2',
														name : 'status_ptk'
													} ]
										});
									});
					</script>
			<jsp:include page="Footer.jsp" />
		</content>
	</body>
</html>