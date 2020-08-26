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
								<h4 class="panel-title">Peserta didik</h4>
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
										<div class="checkbox">
											<input class="checkbox-all" type="checkbox" id="flat-checkbox-1">
										</div>
									</td>
									<td>NIM</td>
									<td>Nama</td>
									<td>Angkatan</td>
									<td>Pendamping Akademik</td>
									<td>Status Hapus</td>
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
								<h4 class="panel-title">Detil Peserta Didik</h4>
							</div>
							<div class="panel-body">
					<form:form role="form" commandName="pd" class="formdetail">
						<div class="form-group">
							<label>NIM Peserta didik</label>
							<form:input path="nimPd" class="form-control" placeholder="Berisi NIM Peserta didik" />
							<form:hidden path="idPd" class="form-control" />
						</div>
						<div class="form-group">
							<label>Nama Peserta didik</label>
							<form:input path="nmPd" class="form-control" placeholder="Berisi nama Peserta didik" />
						</div>
						<div class="form-group">
							<label>Angkatan Peserta didik</label>
							<form:input path="angkatanPd" class="form-control" placeholder="Berisi angkatan Peserta didik" />
						</div>
						<div class="form-group">
							<label>Dosen wali</label>
                            <select class="form-control" id="ptk" name="idPtk">
                            	<option value=""></option>
                            	<c:forEach items="${listPtk}" var="ptk">
                            		<option value="${ptk.idPtk}">${ptk.nmPtk}</option>
                            	</c:forEach>
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
			<!-- Script Custom pada halaman. Kamu bisa memisah script pada file terpisah dengan menaruhnya di resource/js/namamodul/namafile.js -->
		<script>
			$(document).ready(function(){				
				$('#masterpage').masterPage(
				{
					detailFocusId: '#idPd',
					dataUrl: context_path+'modul/pembelajaran/pesertadidik/json',
					detailUrl: context_path+'modul/pembelajaran/pesertadidik/edit',
					addUrl: context_path+'modul/pembelajaran/pesertadidik/simpan',
					editUrl: context_path+'modul/pembelajaran/pesertadidik/simpan',
					deleteUrl: context_path+'modul/pembelajaran/pesertadidik/deletemany',
					primaryKey: 'idPd',
			        order: [[1,"asc"]],
					editOnClick: false,
					editOnClickRow: true,
					cols: [
						/* idPd */
						{ 
							"bVisible":    true,
							bSortable: false,
							bSearchable: false,
							mRender: function(data,type,full){
								return '<input class="checkbox-data" type="checkbox" name="idPd[]" value="'+data+'"/>';
							}
						},
						/* nimPd */
						{ "bVisible":    true },
						/* nmPd */
						{ "bVisible":    true },
						/* angkatanPd */
						{ "bVisible":    true },
						/* ptk */
						{ "bVisible":    true },
						/* aPtkterhapus */
						{ "bVisible":    false },
						/* Aksi */
						{ 
							"bVisible":    true,
							bSortable: false,
							bSearchable: false,
							mRender: function(data,type,full){
								var action = '<button type="button" class="btn btn-primary editrow">Edit</button>';
								if(full[5]=='false') action += ' <button type="button" class="btn btn-danger deleterow">Hapus</button>'
								return action;
							}
						}
					],
					validationRules: {idPd:{required: false},nimPd:{required: true},nmPd:{required: true},idPtk:{required: false}},
					filters: [{id:'#filter', name:'a_pd_terhapus'}],
					callOnFillForm:function(data,options){
						$('#ptk').val(data.data.ptk==null?"":data.data.ptk.idPtk);
					}
				});
			});
		</script>
			<jsp:include page="Footer.jsp" />
		</content>
	</body>
</html>
