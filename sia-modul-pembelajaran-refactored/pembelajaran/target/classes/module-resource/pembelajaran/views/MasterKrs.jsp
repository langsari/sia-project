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
								<h4 class="panel-title">Pengambilan KRS</h4>
							</div>
							<div class="panel-body">
						<div class="row">
						<div class="col-md-6 form-horizontal">
							<div class="form-group">
								<label class="col-sm-4 control-label">Status Aktif</label>
								<div class="col-sm-8">
									<select class="form-control" id="filter2" name="filter2">
										<option value="">Semua</option>
										<option value="false">Aktif</option>
										<option value="true">Terhapus</option>
									</select>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-4 control-label">Status KRS</label>
								<div class="col-sm-8">
									<select class="form-control" id="filter3">
										<option value="">Semua</option>
										<option value="false">Belum di setujui</option>
										<option value="true">Tersetujui</option>
									</select>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-4 control-label">Status Pembatalan</label>
								<div class="col-sm-8">
									<select class="form-control" id="filter4">
										<option value="">Semua</option>
										<option value="false">Tidak di batalkan</option>
										<option value="true">Terbatalkan</option>
									</select>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-4 control-label">Tahun Ajaran</label>
								<div class="col-sm-8">
		                            <select class="form-control" id="filter" name="filter">
		                            	<option value="">Semua</option>
		                            	<c:forEach items="${listTglSmt}" var="tglSmt">
		                            		<option value="${tglSmt.idTglSmt}" <c:if test="${tglSmt.aTglSmtAktif == true}">selected="true"</c:if> >${tglSmt.thnAjaran.thnThnAjaran} ${tglSmt.smt.nmSmt}</option>
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
									<td>
										<input class="checkbox-all" type="checkbox">
									</td>
									<td>NIM</td>
									<td>Nama</td>
									<td>Angkatan</td>
									<td>Matakuliah</td>
									<td>Pembelajaran</td>
									<td>SKS</td>
									<td>Waktu Ambil</td>
									<td>Status Hapus</td>
									<td>Waktu Hapus</td>
									<td>Disetujui</td>
									<td>Dibatalkan</td>
									<td>Waktu batal</td>
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
					dataUrl: context_path+'modul/pembelajaran/pengambilankrs/json',
					primaryKey: 'idKrs',
			        order: [[7,"desc"]],
					editOnClick: false,
					editOnClickRow: false,
					showAddButton: false,
					showDelButton: false,
					cols: [
						/* idKrs */
						{ 
							"bVisible":    false,
							bSortable: false,
							bSearchable: false,
							mRender: function(data,type,full){
								return '<input class="checkbox-data" type="checkbox" name="idSmt[]" value="'+data+'"/>';
							}
						},
						/* nimPd */
						{ "bVisible":    true },
						/* nmPd */
						{ "bVisible":    true },
						/* angkatanPd */
						{ "bVisible":    false },
						/* namaMK */
						{ "bVisible":    true },
						/* nmPemb */
						{ "bVisible":    true },
						/* jumlahSKS */
						{ "bVisible":    true },
						/* waktuAmbil */
						{ "bVisible":    true },
						/* krsTerhapus */
						{ 
							"bVisible":    false,
							mRender: function(data,type,full){
								if(data=='false') return "Aktif";
								else return "terhapus";
							}
						},
						/* waktuHapus */
						{ "bVisible":    false },
						/* aKrsDisetujui */
						{ "bVisible":    false },
						/* aKrsTerbatalkan */
						{ 
							"bVisible":    false,
							mRender: function(data,type,full){
								if(data=='false') return "";
								else return "terbatalkan";
							} 
						},
						/* waktuBatal */
						{ "bVisible":    false }
					],
					validationRules: {idPtk:{required: false},nipPtk:{required: true},nmPtk:{required: true}},
					filters: [{id:'#filter2', name:'aKrsTerhapus'},{id:'#filter', name:'id_tgl_smt'},{id:'#filter3', name:'aKrsDisetujui'},{id:'#filter4', name:'aKrsBatal'}]
				});
			});
		</script>
		<jsp:include page="Footer.jsp" />
		</content>
	</body>
</html>
