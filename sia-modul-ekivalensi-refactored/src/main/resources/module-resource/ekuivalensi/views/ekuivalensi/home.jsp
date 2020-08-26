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
	<div class="row" id="masterpage" style="">
			<div class="col-md-12" style="margin-bottom:10px;">
				<div class="panel panel-white">
					<div class="panel-heading clearfix">
						<h4 class="panel-title">Ekuivalensi Kurikulum</h4>
					</div>
					<div class="panel-body">
						<div class="row">
							<div class="col-md-4">
							
							</div>
							<div class="col-md-8 masteractions panel-body">
								<div class="btn-action pull-right">
									<button class="btn btn-success" id="master-to-add-ekuivalensi">Tambah</button>
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
			</div>
		</div>
		
		<div class="row" id="master-add-ekuivalensi" style="display:none">
			<div class="col-md-8 col-md-offset-2" style="margin-bottom:10px;">
				<div class="panel panel-white">
					<div class="panel-heading clearfix">
						<h4 class="panel-title">Kelola Ekuivalensi</h4>
					</div>
					<div class="panel-body">
						<div class="row">
							<div class="col-md-4">
								<div class="form-group">												
								</div>
							</div>
							<div class="col-md-8 masteractions panel-body">
								<div class="btn-action pull-right"></div>
							</div>
						</div>							
						<form class="formdetail">
							<div class="form-group">
								<label>Kurikulum Lama</label>
								<input id="kurikulumLama" class="form-control" placeholder="Berisi Kurikulum Lama" />
								<input name="idKurikulumLama" type="hidden" id="idKurikulumLama" class="form-control" />
							</div>
							<div class="form-group">
								<label>Kurikulum Baru</label>
								<input id="kurikulumBaru" class="form-control" placeholder="Berisi Kurikulum Baru" />
								<input name="idKurikulumBaru" type="hidden" id="idKurikulumBaru" class="form-control" />
							</div>
							<div class="col-md-12 masteractions panel-body">
								<div class="btn-action pull-right">
									<button type="button" id="kelola-ekuivalensi-to-masterpage" class="btn btn-primary">Kembali</button>
									<button type="button" id="kelola-ekuivalensi" class="btn btn-success">Kelola</button>												
								</div>
							</div>										
				        </form>
					</div>
				</div>
			</div>
		</div>
		
		<div class="row" id="manage-ekuivalensi" style="display:none">
			<div class="col-md-4" style="margin-bottom:10px;">
				<div class="panel panel-white">
					<div class="panel-heading clearfix">
						<h4 class="panel-title" id='title-kurikulum-lama'>Kurikulum Lama</h4>
					</div>
					<div class="panel-body">
						<div class="col-md-12 masteractions panel-body">
							<div class="btn-action pull-right">
								<button class="btn btn-success" id='tambah-mk-lama'>Tambah</button>
							</div>
						</div>	
						<table class="table table-striped table-bordered">										
							<thead>
								<tr>
									<th>Kode</th>
									<th>Matakuliah</th>
									<th>SKS</th>
									<th>Sifat</th>						
								</tr>
							</thead>
							<tbody id="mk-lama-selected">
							</tbody>
						</table>	
					</div>
				</div>
			</div>
			<div class="col-md-2" style="margin-bottom:10px;">
				<div class="panel panel-white">
					<div class="panel-heading clearfix">
						<h4 class="panel-title">Relasi</h4>
					</div>
					<div class="panel-body">
						<div class="form-group">
							<select id="dropdown-relasi-mk-lama" class="form-control">
								<option value="1">-Pilih-</option>
								<option value="^">^(AND)</option>
								<option value="/">/(OR)</option>
								<option value="0">Lain</option>
							</select>
							<input class="form-control" id="textbox-relasi-mk-lama" type="text" placeholder="Masukkan relasi" style="margin-top:10px;display:none">
						</div>
						<div class="form-group info" style="cursor:pointer">
							<span class='item'>
								<span class='icon-info'></span>
								info
							</span>
						</div>
					</div>
				</div>
			</div>
			<div class="col-md-4" style="margin-bottom:10px;">
				<div class="panel panel-white">
					<div class="panel-heading clearfix">
						<h4 class="panel-title" id='title-kurikulum-baru'>Kurikulum Baru</h4>
					</div>
					<div class="panel-body">
						<div class="col-md-12 masteractions panel-body">
							<div class="btn-action pull-right">
								<button class="btn btn-success" id='tambah-mk-baru'>Tambah</button>
							</div>
						</div>	
						<table class="table table-striped table-bordered">										
							<thead>
								<tr>
									<th>Kode</th>
									<th>Matakuliah</th>
									<th>SKS</th>
									<th>Sifat</th>						
								</tr>
							</thead>
							<tbody id="mk-baru-selected">
							</tbody>
						</table>	
					</div>
				</div>
			</div>
			<div class="col-md-2" style="margin-bottom:10px;">
				<div class="panel panel-white">
					<div class="panel-heading clearfix">
						<h4 class="panel-title">Relasi</h4>
					</div>
					<div class="panel-body">
						<div class="form-group">
							<select id="dropdown-relasi-mk-baru" class="form-control">
								<option value="1">-Pilih-</option>
								<option value="^">^(AND)</option>
								<option value="/">/(OR)</option>
								<option value="0">Lain</option>
							</select>
							<input class="form-control" id="textbox-relasi-mk-baru" type="text" placeholder="Masukkan relasi" style="margin-top:10px;display:none">
						</div>
						<div class="form-group info" style="cursor:pointer">
							<span class='item'>
								<span class='icon-info'></span>
								info
							</span>
						</div>
					</div>
				</div>
			</div>
			<div class="col-md-12">
				<div class="panel panel-white">
					<div class="panel-body">
						<div class="btn-action pull-right">
							<button type="button" class="btn btn-success" id='add-ekuivalensi' style="margin-bottom:10px;">Tambahkan</button>
							<button type="button" class="btn btn-danger" id='delete-mk' style="margin-bottom:10px;">Hapus Semua</button>
						</div>
					</div>
				</div>
			</div>		
			<div class="col-md-12" style="margin-bottom:10px;">
				<div class="panel panel-white">
					<div class="panel-heading clearfix">
						<h4 class="panel-title">Relasi</h4>
					</div>
					<div class="panel-body">
						<table class="table table-striped table-bordered">
							<thead>
								<tr>
									<th>NO</th>
									<th>Kode</th>
									<th>Matakuliah</th>
									<th>SKS</th>
									<th>Sifat</th>
									<th>Relasi</th>
									<th>Kode</th>
									<th>Matakuliah</th>
									<th>SKS</th>
									<th>Sifat</th>
									<th>Relasi</th>
									<th>Aksi</th>								
								</tr>
							</thead>
							<tbody id="mk-ekuivalen">
							</tbody>
						</table>		
					</div>
				</div>
			</div>
			<div class="col-md-12 masteractions">
				<div class="panel panel-white">
					<div class="panel-body">
						<div class="btn-action pull-right">
							<button type="button" class="btn btn-primary" id="manage-ekuivalensi-to-manage-pekuivalensi" style="margin-bottom:10px;">Kembali</button>
							<button type="button" class="btn btn-success" id="simpan-mk-ekuivalen" style="margin-bottom:10px;">Simpan</button>
						</div>
					</div>
				</div>
			</div>
		</div>	
		
		<div id="modal-kurikulum-lama" class="modal fade">
			<div class="modal-dialog modal-lg">
		    	<div class="modal-content">
		      		<div class="modal-header">
		        		<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
		        		<h4 class="modal-title">Kurikulum</h4>
		      		</div>
		      		<div class="modal-body">
				      	<div id="masterpage-kurikulum-lama">
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
												<td>Satuan Manajemen</td>									
												<td>Kurikulum</td>
												<td>Tahun Mulai</td>
												<td>Tahun Akhir</td>
												<td>Status</td>
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
		<div id="modal-kurikulum-baru" class="modal fade">
			<div class="modal-dialog modal-lg">
		    	<div class="modal-content">
		      		<div class="modal-header">
		        		<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
		        		<h4 class="modal-title">Kurikulum</h4>
		      		</div>
		      		<div class="modal-body">
				      	<div id="masterpage-kurikulum-baru">
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
												<td>Satuan Manajemen</td>									
												<td>Kurikulum</td>
												<td>Tahun Mulai</td>
												<td>Tahun Akhir</td>
												<td>Status</td>
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
		<div id="modal-mk-lama" class="modal fade">
			<div class="modal-dialog modal-lg">
		    	<div class="modal-content">
		      		<div class="modal-header">
		        		<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
		        		<h4 class="modal-title">Matakuliah</h4>
		      		</div>
		      		<div class="modal-body">
				      	<div id="masterpage-mk-lama">
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
												<td>Kode</td>
												<td>Matakuliah</td>
												<td>SKS</td>
												<td>Sifat</td>
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
		<div id="modal-mk-baru" class="modal fade">
			<div class="modal-dialog modal-lg">
		    	<div class="modal-content">
		      		<div class="modal-header">
		        		<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
		        		<h4 class="modal-title">Matakuliah</h4>
		      		</div>
		      		<div class="modal-body">
				      	<div id="masterpage-mk-baru">
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
												<td>Kode</td>
												<td>Matakuliah</td>
												<td>SKS</td>
												<td>Sifat</td>
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
		
		<div id="modal-info-relasi" class="modal fade">
			<div class="modal-dialog modal-lg">
		    	<div class="modal-content">
		      		<div class="modal-header">
		        		<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
		        		<h4 class="modal-title">Keterangan relasi</h4>
		      		</div>
		      		<div class="modal-body">
				      	<p>
				      		Relasi digunakan ketika matakuliah yang berelasi lebih dari 1. </br>
				      		1. Relasi AND(^), digunakan jika operator yang dibutuhkan hanya AND(^). Contoh : a^b^c, a^(b^c) </br>
				      		2. Relasi OR(/), digunakan jika operator yang dibutuhkan hanya OR(/). Contoh : a/b/c, a/(b/c) </br>
				      		3. Lain, digunakan jika membutuhkan kombinasi operator AND (^) dan OR (/). Contoh : b/(c^a), c^(b/a) </br>
				      		Setiap matakuliah disimbolkan dengan huruf a-z. Matakuliah urutan pertama sampai terakhir disesuaikan dengan urutan huruf. </br>
				      		Misalnya untuk relasi j/(z^a). a untuk matakuliah urutan pertama, j untuk matakuliah urutan kedua, z untuk matakuliah urutan ketiga.
				      	</p>
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
			<script src="${pageContext.servletContext.contextPath}/resources/js/ekuivalensi/home.js" type="text/javascript" ></script>
			<jsp:include page="../Footer.jsp" />
		</content>
	</body>
</html>
