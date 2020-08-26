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
									<h4 class="panel-title">Mata Kuliah</h4>
								</div>
								<div class="panel-body">  
								<p>Tabel menampilkan prasyarat mata kuliah</p>
										
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
														<input class="checkbox-all" type="checkbox" id="flat-checkbox-1"> 
													</td> 
													<td>Kode MK</td> 
													<td>Nama MK</td> 
													<td>Kode Prasyarat MK</td> 
													<td>Nama Prasyarat MK</td> 
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
									<h4 class="panel-title">Mata Kuliah</h4>
								</div>
								<div class="panel-body">
									<h4 id="title">Kelola prasyarat mata kuliah</h4>
									<form:form role="form" action="login" commandName="prasyaratMK" class="formdetail">  
										<input type="hidden" id="idPrasyaratMK" name="idPrasyaratMK" />
										<div class="form-group">
											<label>Kode dan Nama Mata Kuliah</label>
											<select id="idMK" name="idMK" class="form-control">
													<option value="">Pilih kode dan nama mata kuliah</option> 
												<c:forEach items="${mkList}" var="mk"> 
													<option value="${mk.idMK}">${mk.kodeMK} - ${mk.namaMK}</option>
												</c:forEach> 
											</select> 
										</div> 
										<div class="form-group">
											<label>Kode dan Nama Mata Kuliah Prasyarat</label>
											<select id="mkIdMK" name="mkIdMK" class="form-control">
													<option value="">Pilih kode dan nama mata kuliah prasyarat</option> 
												<c:forEach items="${mkList}" var="mk"> 
													<option value="${mk.idMK}">${mk.kodeMK} - ${mk.namaMK}</option>
												</c:forEach>  
											</select>
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
			<!-- Script Custom pada halaman. Kamu bisa memisah script pada file terpisah dengan menaruhnya di resource/js/namamodul/namafile.js -->
				<script>
					$(document).ready(function(){
						//$("#idMK").select2();
						//$("#mkIdMK").select2();
						$('#masterpage').masterPage(
						{
							detailFocusId: '#idPrasyaratMK',
							dataUrl: context_path+'modul/kurikulum/matakuliah/prasyarat/json',
							detailUrl: context_path+'modul/kurikulum/matakuliah/prasyarat/edit',
							addUrl: context_path+'modul/kurikulum/matakuliah/prasyarat/simpan',
							editUrl: context_path+'modul/kurikulum/matakuliah/prasyarat/simpan',
							deleteUrl: context_path+'modul/kurikulum/matakuliah/prasyarat/deletemany',
							primaryKey: 'idPrasyaratMK',
					        order: [[1,"asc"]],
							editOnClick: false,
							editOnClickRow: true,
							cols: [
								/* id */
								{ 
									"bVisible":    true,
									bSortable: false,
									mRender: function(data,type,full){
										return '<input class="checkbox-data" type="checkbox" name="idPrasyaratMK[]" value="'+data+'">';
									}
								},
								/* kode mata kuliah */
								{ "bVisible":    true }, 
								/* Nama mata kuliah */
								{ "bVisible":    true }, 
								/* kode mata kuliah */
								{ "bVisible":    true }, 
								/* nama mata kuliah */
								{ "bVisible":    true },
								/*status hapus*/
								{ 
									"bVisible":    false, 
									mRender: function(data,type,full){
										if(full[5]=='false') return "Aktif";
										return "Terhapus";
									}
								},
								/* Aksi */
								{ 
									"bVisible":    true,
									bSortable: false,
									mRender: function(data,type,full){
										var action = '<button type="button" class="btn btn-primary editrow">Edit</button>';
										//if(full[5]=='false') return action += ' <button type="button" class="btn btn-danger deleterow">Hapus</button>';
										return action;
									}
								}
							],
							validationRules: {idMK:{required: true}, mkIdMK:{required: true}},
							filters: [{id:'#filter', name:'statusPrasyarat'}],
							callOnFillForm : function(response,options){ 
								console.log(response.data.idPrasyaratMK);
								$("#idPrasyaratMK").val(response.data.idPrasyaratMK); 
								$("#idMK").val(response.data.childMK.idMK);
								$("#mkIdMK").val(response.data.parentMK.idMK);
							}
						});
					});
				</script>
		</content>
	</body>
</html>
	