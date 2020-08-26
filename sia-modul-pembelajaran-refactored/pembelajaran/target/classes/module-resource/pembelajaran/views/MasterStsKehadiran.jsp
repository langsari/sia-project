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
								<h4 class="panel-title">Status Kehadiran</h4>
							</div>
							<div class="panel-body">
					
					<div class="row">
						<div class="col-md-4">
							<div class="form-group">
								<label>Status Aktif</label>
								<select id="filter" name="filter">
									<option value="false">Aktif</option>
									<option value="">Semua</option>
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
									<td>
										<input class="checkbox-all" type="checkbox" id="flat-checkbox-1"/>
									</td>
									<td>Kode Status Kehadiran</td>
									<td>Nama Status Kehadiran</td>
									<td>Status Awal</td>
									<td>Terhitung Absen</td>
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
		<div class="row" id="master-detail" style="display:none;">
				<div class="col-md-6 col-md-offset-3">
				<div class="panel panel-white">
							<div class="panel-heading clearfix">
								<h4 class="panel-title">Detil Status Kehadiran</h4>
							</div>
							<div class="panel-body">
					<form:form role="form" commandName="stsKehadiran" class="formdetail">
						<div class="form-group">
							<label>Kode Status Kehaidran</label>
							<form:input path="kodeStsKehadiran" class="form-control" placeholder="Berisi kode status kehadiran" />
							<form:hidden path="idStsKehadiran" class="form-control" />
						</div>
						<div class="form-group">
							<label>Nama Status kehadiran</label>
							<form:input path="nmStsKehadiran" class="form-control" placeholder="Berisi nama status kehadiran" />
						</div>
						<div class="checkbox">
							<label>
								<form:checkbox path="aKehadiranAwal" class="aKehadiranAwal" />
								Jadikan Kehadiran Awal
							</label>
						</div>
						<div class="checkbox">
							<label>
								<form:checkbox path="aAbsen" class="aAbsen" />
								Terhitung absen
							</label>
						</div>
						<div class="form-group detailcontrol">
						</div>
			        </form:form>
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
					detailFocusId: '#idStsKehadiran',
					dataUrl: context_path+'modul/pembelajaran/stskehadiran/json',
					detailUrl: context_path+'modul/pembelajaran/stskehadiran/edit',
					addUrl: context_path+'modul/pembelajaran/stskehadiran/simpan',
					editUrl: context_path+'modul/pembelajaran/stskehadiran/simpan',
					deleteUrl: context_path+'modul/pembelajaran/stskehadiran/deletemany',
					primaryKey: 'idStsKehadiran',
			        order: [[1,"desc"]],
					editOnClick: false,
					editOnClickRow: true,
					cols: [
						/* idStsKehadiran */
						{ 
							"bVisible":    true,
							bSortable: false,
							mRender: function(data,type,full){
								return '<input class="checkbox-data" type="checkbox" name="idStsKehadiran[]" value="'+data+'"/>';
							}
						},
						/* kodeStsKehadiran */
						{ "bVisible":    true },
						/* nmStsKehadiran */
						{ "bVisible":    true },
						/* aAbsen */
						{ 
							"bVisible":    true,
							mRender: function(data,type,full){
								if(data == "false") return "Tidak";
								else return "Ya";
							}
						},
						/* aAbsen */
						{ "bVisible":    true },
						/* Status Aktif */
						{ 
							"bVisible":    false, 
							mRender: function(data,type,full){
								if(data == "false") return "Aktif";
								else return "Terhapus";
							}
						},
						/* Aksi */
						{ 
							"bVisible":    true,
							bSortable: false,
							mRender: function(data,type,full){
								var action = '<button type="button" class="btn btn-primary editrow">Edit</button>';
								if(full[5]=='false') action += ' <button type="button" class="btn btn-danger deleterow">Hapus</button>'
								return action;
							}
						}
					],
					validationRules: {idStsKehadiran:{required: false},kodeStsKehadiran:{required: true},nmStsKehadiran:{required: true}},
					filters: [{id:'#filter', name:'a_kehadiran_terhapus'}],
					callOnFillForm: function(data, options){
						$('.formdetail').find("input[type=checkbox]:not(.switchery), input[type=radio]:not(.no-uniform)").each(function(){
							$(this).uniform();
						});
					},
					callOnAddAfterReset:function(options){
						$('.formdetail').find("input[type=checkbox]:not(.switchery), input[type=radio]:not(.no-uniform)").each(function(){
							$(this).uniform();
						});
					}
				});
			});
		</script>
			<jsp:include page="Footer.jsp" />
		</content>
	</body>
</html>
