<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page session="false" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<!-- Styles -->
		<link
			href="${pageContext.servletContext.contextPath}/resources/plugins/uniform/css/uniform.default.min.css"
			rel="stylesheet" />
		<link
			href="${pageContext.servletContext.contextPath}/resources/plugins/line-icons/simple-line-icons.css"
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
		
		<link rel="stylesheet" href="${pageContext.servletContext.contextPath}/resources/plugins/select2/css/select2.min.css" />
	
		<link rel="stylesheet"
				href="${pageContext.servletContext.contextPath}/resources/plugins/jquery.datatables/media/css/jquery.dataTables.min.css">
		
		<link rel="stylesheet"
			href="${pageContext.servletContext.contextPath}/resources/plugins/jquery.datatables/extensions/TableTools/css/dataTables.tableTools.min.css">
		<!-- optional -->
		<link rel="stylesheet"
			href="${pageContext.servletContext.contextPath}/resources/plugins/jquery.datatables/extensions/ColVis/css/dataTables.colVis.min.css">
		<!-- optional -->
	</head>
	<body>
		<div class="row" id="masterpage"> 
			<div class="col-md-12" style="margin-bottom:10px;"> 
				<div class="panel panel-white">
					<div class="panel-heading clearfix">
						<h4 class="panel-title">Bentuk Penilaian</h4>
					</div>
					<div class="panel-body">  
						<p>Tabel menampilkan bentuk penilaian</p>
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
									<div class="btn-action pull-right"> </div>
								</div> 
							</div>
							<form class="tableform">
								<div class="table-responsive">
									<table class="table table-striped table-bordered table-hover table-checkable table-colvis datatable">
										<thead>
											<tr>
												<td> 
														<input class="checkbox-all" type="checkbox" id="flat-checkbox-1"> 
												</td> 
												<td>Nama Bentuk Penilaian</td> 
												<td>Deskripsi Bentuk Penilaian</td>
												<td>Status Aktif</td>
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
								<h4 id="title">Bentuk Penilaian</h4>
						</div>
						<div class="panel-body">
							<h4 id="title">Kelola bentuk penilaian</h4>
							<form:form role="form" action="login" commandName="bentukPenilaian" class="formdetail">
								<div class="form-group">
									<label>Nama Bentuk Penilaian</label>
									<form:input path="namaBentuk" class="form-control" placeholder="Berisi nama bentuk penilaian" required="true" />
									<form:hidden path="idBentuk" class="form-control" />
								</div>
								<div class="form-group">
									<label>Deskripsi Bentuk Penilaian</label>
									<form:input path="deskripsiBentuk" class="form-control" placeholder="Berisi deskripsi bentuk penilaian" /> 
								</div> 
								<div class="form-group detailcontrol">
								</div>
					        </form:form>
						</div>
					</div>
			</div>
		</div> 
		
		
		<content tag="scripts">
			<%@include file="footer.jsp" %>
			
			<script>
				var context_path = "${pageContext.servletContext.contextPath}/";
			</script>
			<script src="${pageContext.servletContext.contextPath}/resources/plugins/select2/js/select2.min.js" type="text/javascript"></script>
			<script type="text/javascript"
				src="${pageContext.servletContext.contextPath}/resources/plugins/jquery.datatables/media/js/jquery.dataTables.min.js"></script>
			<script
				src="${pageContext.servletContext.contextPath}/resources/plugins/3d-bold-navigation/js/modernizr.js"></script>
			<script
				src="${pageContext.servletContext.contextPath}/resources/plugins/offcanvasmenueffects/js/snap.svg-min.js"></script>
			<script type="text/javascript"
				src="${pageContext.servletContext.contextPath}/resources/plugins/jquery.datatables/extensions/TableTools/js/dataTables.tableTools.min.js"></script>
			<script type="text/javascript"
			src="${pageContext.servletContext.contextPath}/resources/plugins/jquery.datatables/extensions/ColVis/js/dataTables.colVis.min.js"></script>
			<script
				src="${pageContext.servletContext.contextPath}/resources/plugins/jquery-validation/jquery.validate.min.js"
				rel="stylesheet" type="text/javascript"></script>
			<script
				src="${pageContext.servletContext.contextPath}/resources/plugins/jquery-blockui/jquery.blockui.js"
				type="text/javascript"></script>
			<script
				src="${pageContext.servletContext.contextPath}/resources/js/jquery.masterpage.sia.js"
				type="text/javascript"></script>
			<script src="${pageContext.servletContext.contextPath}/resources/js/date.js" type="text/javascript" ></script>
			<script src="${pageContext.servletContext.contextPath}/resources/plugins/bootstrap-datepicker/js/bootstrap-datepicker.js" type="text/javascript"></script>
			<script>
				$(document).ready(function(){
					$('#masterpage').masterPage(
					{
						detailFocusId: '#idBentuk',
						dataUrl: context_path+'modul/kurikulum/rencanapembelajaran/bentuk/json',
						detailUrl: context_path+'modul/kurikulum/rencanapembelajaran/bentuk/edit',
						addUrl: context_path+'modul/kurikulum/rencanapembelajaran/bentuk/simpan',
						editUrl: context_path+'modul/kurikulum/rencanapembelajaran/bentuk/simpan',
						deleteUrl: context_path+'modul/kurikulum/rencanapembelajaran/bentuk/deletemany',
						primaryKey: 'idBentuk',
				        order: [[1,"asc"]],
						editOnClick: false,
						editOnClickRow: true,
						cols: [
							/* id */
							{ 
								"bVisible":    true,
								bSortable: false,
								mRender: function(data,type,full){
									return '<input type="checkbox" class="checkbox-data" name="idBentuk[]" value="'+data+'">';
								}
							},
							/* Nama rumpun mata kuliah */
							{ "bVisible":    true }, 
							/* deskripsi rumpun mata kuliah */
							{ "bVisible":    false }, 
							/*status kurikulum*/
							{ 
								"bVisible":    false, 
								mRender: function(data,type,full){
									if(full[3] == 'false') return "Aktif";
									else return "Non Aktif";
								}
							},
							/* Aksi */
							{ 
								"bVisible":    true,
								bSortable: false,
								mRender: function(data,type,full){
									var action = '<button type="button" class="btn btn-primary editrow">Edit</button>';
									if(full[3]=='false') action += ' <button type="button" class="btn btn-danger deleterow">Non-Aktif</button>'
									return action;
								}
							}
						],
						validationRules: {idBentuk:{required: false},namaBentuk:{required: true}},
						filters: [{id:'#filter', name:'statusBentuk'}],
						callOnFillForm : function(response,options){ 
							$("#idBentuk").val(response.data.idBentuk);
						}
					});
				});
			</script>
		</content>
	</body>
</html>
