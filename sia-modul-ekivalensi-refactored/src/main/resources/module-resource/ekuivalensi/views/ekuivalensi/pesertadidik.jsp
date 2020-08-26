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
		<div class="col-md-12" style="margin-bottom:10px;">
			<div class="panel panel-white">
				<div class="panel-heading clearfix">
					<h4 class="panel-title">Peserta Didik</h4>
				</div>
				<div class="panel-body">
					<div class="row">
						<div class="col-md-4">
							<div class="form-group">
								<span>Kurikulum Lama</span>
								<select id="filter-kurikulum-lama" name="filter" class='form-control'>
									<option value="">Semua</option>
									<c:forEach items="${listKurikulumLama}" var="kurikulumLama">
									<option value="${kurikulumLama.idKurikulum}">${kurikulumLama.namaKurikulum}</option>
									</c:forEach>
								</select>
							</div>
							<div class="form-group">
								<label>Kurikulum Baru</label>
								<select id="filter-kurikulum-baru" name="filter" class='form-control'>
									<option value="">Semua</option>
									<c:forEach items="${listKurikulumBaru}" var="kurikulumBaru">
									<option value="${kurikulumBaru.idKurikulum}">${kurikulumBaru.namaKurikulum}</option>
									</c:forEach>
								</select>
							</div>
						</div>
						<div class="col-md-8 masteractions panel-body">
							<div class="btn-action pull-right">
								<button class='btn btn-success' type='button' id='add-pd'>Tambah</button>
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
										<td>NIM</td>
										<td>Mahasiswa</td>				
										<td>Pendidik</td>
										<td>Kurikulum Lama</td>
										<td>Kurikulum Baru</td>				
										<td>Tanggal Ekuivalensi</td>
										<td>ID Kurikulum Lama</td>
										<td>ID Kurikulum Baru</td>				
										<td>ID PD</td>
										<td>Status Ekuivalensi</td>
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
	
	<div class="row" id="mk-wajib-ambil" style="display:none">		
		<input type="hidden" id="idPd-hidden">	
		<div class="col-md-8 col-md-offset-2" style="margin-bottom:10px;">
			<div class="panel panel-white" id="block-pd-detail">
				<div class="panel-heading clearfix">
					<h4 class="panel-title">Data Ekuivalensi</h4>
				</div>
				<div class="panel-body">
					<div class="table-responsive">
						<table class="table borderless">
							<tbody id="pd-detail">
							</tbody>
						</table>
					</div>
				</div>
			</div>
		</div>
		<div class="col-md-12" style="margin-bottom:10px;" id="block-mk">
			<div class="panel panel-white">
				<div class="panel-heading clearfix">
					<h4 class="panel-title"></h4>
				</div>
				<div class="panel-body" id="block-mk">								
					<div class="table-responsive">
						<div class="col-md-6" style="margin-bottom:10px;">
							<table class="table table-striped table-bordered table-hover">
								<thead>
									<tr>
										<th>Kode</th>
										<th>Matakuliah</th>
										<th>SKS</th>
										<th>Sifat</th>
										<th>Status</th>
										<th>Hapus</th>								
									</tr>
								</thead>
								<tbody id="mk-lama">	
									</tr>
								</tbody>
							</table>
						</div>
						<div class="col-md-6" style="margin-bottom:10px;">
							<table class="table table-striped table-bordered table-hover">
								<thead>
									<tr>
										<th>Kode</th>
										<th>Matakuliah</th>
										<th>SKS</th>
										<th>Sifat</th>
										<th>Status</th>
										<th>Ambil</th>									
									</tr>
								</thead>
								<tbody id="mk-baru">
								</tbody>
							</table>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="col-md-12">
			<div class="panel panel-white">
				<div class="panel-body">
					<div class="btn-action pull-right">
						<button type="button" class="btn-primary btn" onclick="manage_mkwajib_to_masterpage()">Batal</button>
						<button type="button" class="btn-danger btn" onclick="simpan_mkwajib(0)">Simpan Permanen</button>
						<button type="button" class="btn-success btn pull-right" style="margin-left:10px;" onclick="simpan_mkwajib(1)">Simpan</button>							
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<div id="modal-peserta-didik" class="modal fade">
		<input type="hidden" id="idRelasi-hidden">
		<div class="modal-dialog modal-lg">
	    	<div class="modal-content">
	      		<div class="modal-header">
	        		<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	        		<h4 class="modal-title">Mahasiswa</h4>
	      		</div>
	      		<div class="modal-body">
			      	<div id="masterpage-modal-peserta-didik">
						<div class="form-group">
	                    </div>
						<form class="tableform">
							<div class="table-responsive">
								<table class="table table-striped table-bordered table-hover table-checkable table-colvis datatable" style="width:100%">
									<thead>
										<tr>
											<td>
												#
											</td>									
											<td>NIM</td>
											<td>Mahasiswa</td>
											<td>Angkatan</td>
										</tr>
									</thead>
									<tbody>
									</tbody>
								</table>
							</div>
						</form>
					</div>
	      		</div>
		        <div class="modal-footer">					        	
		        </div>
	    	</div><!-- /.modal-content -->
	  	</div><!-- /.modal-dialog -->
	</div><!-- /.modal -->
	
	<div id="modal-ekuivalensi" class="modal fade">
		<div class="modal-dialog modal-lg">
	    	<div class="modal-content">
	      		<div class="modal-header">
	        		<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	        		<h4 class="modal-title">Ekuivalensi</h4>
	      		</div>
	      		<div class="modal-body">
			      	<div id="masterpage-modal-ekuivalensi">
						<div class="form-group">
	                    </div>
						<form class="tableform">
							<div class="table-responsive">
								<table class="table table-striped table-bordered table-hover table-checkable table-colvis datatable" style="width:100%">
									<thead>
										<tr>
											<td>
												#
											</td>									
											<td>Kurikulum Lama</td>
											<td>Kurikulum Baru</td>
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
		        <div class="modal-footer">					        	
		        </div>
	    	</div><!-- /.modal-content -->
	  	</div><!-- /.modal-dialog -->
	</div><!-- /.modal -->
	
		<content tag="scripts">			
			<script type="text/javascript" src="${pageContext.servletContext.contextPath}/resources/plugins/jquery.datatables/media/js/jquery.dataTables.min.js"></script>
			<script type="text/javascript" src="${pageContext.servletContext.contextPath}/resources/plugins/jquery.datatables/extensions/ColVis/js/dataTables.colVis.min.js"></script>
			<script src="${pageContext.servletContext.contextPath}/resources/plugins/bootstrap-datepicker/js/bootstrap-datepicker.js" type="text/javascript"></script>
			<script src="${pageContext.servletContext.contextPath}/resources/plugins/gritter/js/jquery.gritter.js" rel="stylesheet" type="text/javascript"></script>
			<script src="${pageContext.servletContext.contextPath}/resources/plugins/jquery-validation/jquery.validate.min.js" rel="stylesheet" type="text/javascript"></script>
			<script	src="${pageContext.servletContext.contextPath}/resources/js/jquery.masterpage.sia.js" type="text/javascript"></script>					
			<script src="${pageContext.servletContext.contextPath}/resources/js/ekuivalensi/pesertadidik.js" type="text/javascript" ></script>
			<jsp:include page="../Footer.jsp" />
		</content>
	</body>
</html>
