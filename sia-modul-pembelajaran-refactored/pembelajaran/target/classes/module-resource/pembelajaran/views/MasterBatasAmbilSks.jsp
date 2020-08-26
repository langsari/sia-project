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
								<h4 class="panel-title">Batas Pengambilan SKS</h4>
							</div>
							<div class="panel-body">
					
					<div class="row">
						<div class="col-md-4">
							
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
									<td>Batas bawah IPS</td>
									<td>Batas pengambilan SKS</td>
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
								<h4 class="panel-title">Detil Batas Pengambilan SKS Kehadiran</h4>
							</div>
							<div class="panel-body">
					<form:form role="form" commandName="batasAmbilSks" class="formdetail">
						<div class="form-group">
							<label>Batas Bawah IPS</label>
							<form:input path="batasBawahIps" class="form-control" placeholder="Berisi batas bawah IPS semester sebelumnya" />
							<form:hidden path="idBatasAmbilSks" class="form-control" />
						</div>
						<div class="form-group">
							<label>Batas Pengambilan SKS</label>
							<form:input path="batasPengambilanSks" class="form-control" placeholder="Berisi batas pengambilan SKS" />
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
			<!-- Script Custom pada halaman. Kamu bisa memisah script pada file terpisah dengan menaruhnya di resource/js/namamodul/namafile.js -->
		<script>
			$(document).ready(function(){
				$('#masterpage').masterPage(
				{
					detailFocusId: '#idBatasAmbilSks',
					dataUrl: context_path+'modul/pembelajaran/batasambilsks/json',
					detailUrl: context_path+'modul/pembelajaran/batasambilsks/edit',
					addUrl: context_path+'modul/pembelajaran/batasambilsks/simpan',
					editUrl: context_path+'modul/pembelajaran/batasambilsks/simpan',
					deleteUrl: context_path+'modul/pembelajaran/batasambilsks/deletemany',
					primaryKey: 'idBatasAmbilSks',
			        order: [[1,"desc"]],
					editOnClick: false,
					editOnClickRow: true,
					cols: [
						/* idBatasAmbilSKS */
						{ 
							"bVisible":    true,
							bSortable: false,
							mRender: function(data,type,full){
								return '<input class="checkbox-data" type="checkbox" name="idStsKehadiran[]" value="'+data+'"/>';
							}
						},
						/* batasBawahIPS */
						{ "bVisible":    true },
						/* batasPengambilanSKS */
						{ "bVisible":    true },
						/* Aksi */
						{ 
							"bVisible":    true,
							bSortable: false,
							mRender: function(data,type,full){
								var action = '<button type="button" class="btn btn-primary editrow">Edit</button>';
								action += ' <button type="button" class="btn btn-danger deleterow">Hapus</button>'
								return action;
							}
						}
					],
					validationRules: {idBatasAmbilSks:{required: false},batasBawahIPS:{required: true},batasPengambilanSKS:{required: true, digits:true}}
				});
			});
		</script>
		<jsp:include page="Footer.jsp" />
		</content>
	</body>
</html>
