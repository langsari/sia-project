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
		<link rel="stylesheet" href="${pageContext.servletContext.contextPath}/resources/plugins/jquery.datatables/extensions/TableTools/css/dataTables.tableTools.min.css">
		<link href="${pageContext.servletContext.contextPath}/resources/plugins/gritter/css/jquery.gritter.css" rel="stylesheet" type="text/css" />
	</head>
	<body>
	
	<div class="row" id="masterpage">
				<div class="col-md-12">
						<div class="panel panel-white">
							<div class="panel-heading">
								<h4 class="panel-title">Tahun Ajaran</h4>
							</div>
							<div class="panel-body">
					<div class="row">
						<div class="col-md-4">	
							<div class="form-group">
								<label>Tahun Ajaran</label>
	                            <select class="form-control" id="filter" name="filter">
	                            	<option value=""></option>
	                            	<c:forEach items="${listTglSmt}" var="tglSmt">
	                            		<option value="${tglSmt.idTglSmt}"
													<c:if test="${tglSmt.aTglSmtAktif == true}">selected="true"</c:if>>${tglSmt.thnAjaran.thnThnAjaran}
													${tglSmt.smt.nmSmt}</option>
	                            	</c:forEach>
	                            </select>
                            </div>
						</div>
						<div class="col-md-8 masteractions panel-body">
							<div class="btn-action pull-right">
							</div>
						</div>
					</div>
					<form class="tableform">
									<div class="table-responsive">
						<table class="table table-striped table-bordered table-hover table-checkable table-colvis datatable">
							<thead>
								<tr>
									<td>id</td>
									<td>Kode MK</td>
									<td>Matakuliah</td>
									<td>Pembelajaran</td>
									<td>Tahun ajaran</td>
									<td>Semester</td>
									<td>Kuota peserta didik</td>
									<td>Pertemuan dalam seminggu</td>
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
	
			<content tag="scripts">			
				<script type="text/javascript" src="${pageContext.servletContext.contextPath}/resources/plugins/jquery.datatables/media/js/jquery.dataTables.min.js"></script>
				<script type="text/javascript" src="${pageContext.servletContext.contextPath}/resources/plugins/jquery.datatables/extensions/TableTools/js/dataTables.tableTools.min.js"></script>
				<script type="text/javascript" src="${pageContext.servletContext.contextPath}/resources/plugins/jquery.datatables/extensions/ColVis/js/dataTables.colVis.min.js"></script>
				<script src="${pageContext.servletContext.contextPath}/resources/plugins/bootstrap-datepicker/js/bootstrap-datepicker.js" type="text/javascript"></script>
				<script src="${pageContext.servletContext.contextPath}/resources/plugins/gritter/js/jquery.gritter.js" rel="stylesheet" type="text/javascript"></script>
				<script src="${pageContext.servletContext.contextPath}/resources/plugins/jquery-validation/jquery.validate.min.js" rel="stylesheet" type="text/javascript"></script>
				<script	src="${pageContext.servletContext.contextPath}/resources/js/jquery.masterpage.sia.js" type="text/javascript"></script>
				<script type="text/javascript">
					$(document).ready(function(){
						$('#masterpage').masterPage(
						{
							detailFocusId: '#idPemb',
							dataUrl: context_path+'modul/pembelajaran/absensi/json',
							primaryKey: 'idPemb',
					        order: [[1,"asc"]],
							editOnClick: false,
							editOnClickRow: false,
							showAddButton: false,
							showDelButton: false,
							cols: [
								/* idPemb */
								{ 
									"bVisible":    false,
									bSortable: false,
									bSearchable: false,
									mRender: function(data,type,full){
										return '<input class="checkbox-data" type="checkbox" name="idPemb[]" value="'+data+'"/>';
									}
								},
								/* namaMK */
								{ "bVisible":    true },
								/* namaMK */
								{ "bVisible":    true },
								/* nmPemb */
								{ "bVisible":    true },
								/* thnAjaran */
								{ "bVisible":    true },
								/* smt */
								{ "bVisible":    true },
								/* kuota */
								{ "bVisible":    true },
								/* temuDalamSeminggu */
								{ "bVisible":    false },
								/* Aksi */
								{ 
									"bVisible":    true,
									bSortable: false,
									bSearchable: false,
									mRender: function(data,type,full){
										var action =' <a target="_blank" href="'+context_path+'modul/pembelajaran/absensi/pendidik/'+full[0]+'" class="btn btn-primary">Absensi Pendidik</a> ';
										action+=' <a target="_blank" href="'+context_path+'modul/pembelajaran/absensi/pesertadidik/'+full[0]+'" class="btn btn-success">Absensi Pesertadidik</a> ';
										return action;
									}
								}
							],
							validationRules: {idPemb:{required: false},namaMK:{required: true},idMK:{required: true},nmPemb:{required: true},temuDalamSeminggu:{required: true}},
							filters: [{id:'#filter', name:'id_tgl_smt'}]
						});
					});
				</script>
				<jsp:include page="Footer.jsp" />
		</content>
	</body>
</html>
