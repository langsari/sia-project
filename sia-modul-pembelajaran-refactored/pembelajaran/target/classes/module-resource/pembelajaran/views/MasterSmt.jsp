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
								<h4 class="panel-title">Semester</h4>
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
						<div class="col-md-8 masteractions">
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
										<input class="checkbox-all" type="checkbox">
									</td>
									<td>Nama Semester</td>
									<td>Jumlah minggu</td>
									<td>Jenis Semester</td>
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
								<h4 class="panel-title">Detil Semester</h4>
							</div>
							<div class="panel-body">
					<form:form role="form" commandName="smt" class="formdetail">
						<div class="form-group">
							<label>Nama Semester</label>
							<form:input path="nmSmt" class="form-control" placeholder="Berisi nama semester" />
							<form:hidden path="idSmt" class="form-control" />
						</div>
						<div class="form-group">
							<label>Jumlah minggu</label>
							<form:input path="jmlPertemuan" class="form-control" placeholder="Berisi jumlah pertemuan" />
						</div>
						<div class="form-group">
							<label>Jenis Semester</label>
							<select class="form-control" id="jenisSmt" name="jenisSmt">
                           		<option value="0">Ganjil</option>
                           		<option value="1">Genap</option>
                           		<option value="2">Pendek</option>
                            </select>
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
					detailFocusId: '#idSmt',
					dataUrl: context_path+'modul/pembelajaran/semester/json',
					detailUrl: context_path+'modul/pembelajaran/semester/edit',
					addUrl: context_path+'modul/pembelajaran/semester/simpan',
					editUrl: context_path+'modul/pembelajaran/semester/simpan',
					deleteUrl: context_path+'modul/pembelajaran/semester/deletemany',
					primaryKey: 'idSmt',
			        order: [[3,"asc"]],
					editOnClick: false,
					editOnClickRow: true,
					cols: [
						/* idSmt */
						{ 
							"bVisible":    true,
							bSortable: false,
							bSearchable: false,
							mRender: function(data,type,full){
								return '<input class="checkbox-data" type="checkbox" name="idSmt[]" value="'+data+'">';
							}
						},
						/* nmSmt */
						{ "bVisible":    true },
						/* jmlPertemuan */
						{ "bVisible":    true },
						/* jenisSmt */
						{ 
							"bVisible":    true,
							bSortable: true,
							bSearchable: false,
							mRender: function(data,type,full){
								if(data == 0 ) return "Ganjil";
								if(data == 1 ) return "Genap";
								if(data == 2 ) return "Pendek";
								else return "";
							} 
						},
						/* aSmthapus */
						{ "bVisible":    false },
						/* Aksi */
						{ 
							"bVisible":    true,
							bSortable: false,
							bSearchable: false,
							mRender: function(data,type,full){
								var action = '<button type="button" class="btn btn-primary editrow">Edit</button>';
								if(full[4]=='false') action += ' <button type="button" class="btn btn-danger deleterow">Hapus</button>'
								return action;
							}
						}
					],
					validationRules: {idSmt:{required: false},nmSmt:{required: true},jmlPertemuan:{required: true,digits:true},smtTerhitung:{required: false}},
					filters: [{id:'#filter', name:'a_smt_hapus'}],
					callOnFillForm: function(data, options){
						
					},
					callOnAddAfterReset:function(options){
						
					}
				});
			});
		</script>
			<jsp:include page="Footer.jsp" />
		</content>
	</body>
</html>