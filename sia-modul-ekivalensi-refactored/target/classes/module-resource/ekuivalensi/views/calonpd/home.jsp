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
					<h4 class="panel-title">Peserta Didik Alih Jenjang</h4>
				</div>
				<div class="panel-body">
					<div class="row">
						<div class="col-md-4">
							<div class="form-group">
								<label>Status Ekuivalensi</label>
								<select id="filter-aEkuivalensi" class="form-control">
									<option value="">Semua</option>
									<option value="true">Sudah</option>
									<option value="false" selected>Belum</option>									
								</select>
							</div>
						</div>
						<div class="col-md-8 masteractions panel-body">
							<div class="btn-action pull-right"></div>
						</div>
					</div>
					<form class="tableform">
						<div class="table-responsive">
							<input type="hidden" id="idCalonPD-hidden" />
							<input type="hidden" id="nmCalonPD-hidden" />
							<table class="table table-striped table-bordered table-hover table-checkable table-colvis datatable">
								<thead>
									<tr>
										<td>
											<input class="checkbox-all" type="checkbox" id="flat-checkbox-1"/>
										</td>
										<td>Satuan Manajemen</td>
										<td>Nama Peserta Didik Alih Jenjang</td>									
										<td>Perguruan Tinggi Asal</td>
										<td>Katalog</td>
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
	
	<div class="row" id="ekuivalensi-alihjenjang" style="display:none">
		<div class="col-md-12" style="margin-bottom:10px;">
			<input type="hidden" id="idKurikulum-hidden" />
			<div class="col-md-6" id="ekuivalensi-mkluar" style="">
				<div class="panel panel-white">
					<div class="panel-heading clearfix">
						<h4 class="panel-title" id="title-ekuivalensi-alihjenjang"></h4>
					</div>
					<div class="panel-body">
						<div class="row">
							<div class="col-md-4">
								<div class="form-group">
								</div>
							</div>
							<div class="col-md-8 masteractions panel-body">
								<div class="btn-action pull-right">
									<button type="button" class="btn-success btn pull-right" onclick="show_modal_mkluar()">Tambah</button>
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
											<td>Nama Matakuliah</td>
											<td>SKS</td>														
											<td>Nilai</td>
											<td>Lulus</td>									
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
				
			<div class="col-md-6" id="ekuivalensi-mk" style="">
				<div class="panel panel-white">
					<div class="panel-heading clearfix">
						<h4 class="panel-title">Matakuliah Terakui</h4>
					</div>
					<div class="panel-body">
						<div class="row">
							<div class="col-md-4">
								<div class="form-group">
								</div>
							</div>
							<div class="col-md-8 masteractions panel-body">
								<div class="btn-action pull-right">
									<button type="button" id="add-mk" class="btn-success btn pull-right" onclick="show_modal_mk()">Tambah</button>
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
											<td>Nama Calon Peserta Didik</td>
											<td>Nama Matakuliah</td>
											<td>SKS</td>									
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
		<div class="col-md-12">
			<div class="panel panel-white">
				<div class="panel-body">
					<div class="btn-action pull-right">
						<button type="button" class="btn-primary btn" onclick="manage_ekuivalensi_to_master_calonpd()">Kembali</button>
						<button type="button" class="btn-success btn pull-right" style="margin-left:10px;" onclick="cek_ekuivalensi()">Cek Penyetaraan</button>																
					</div>
				</div>
			</div>
		</div>
	</div>
		
	<div id="modal-mkluar" class="modal fade">
		<div class="modal-dialog modal-lg">
	    	<div class="modal-content">
	      		<div class="modal-header">
	        		<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	        		<h4 class="modal-title">Matakuliah</h4>
	      		</div>
	      		<div class="modal-body">
	      			<div id="masterpage-mkluar">
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
											<td>Katalog</td>
											<td>Kode</td>
											<td>Matakuliah</td>									
											<td>SKS</td>
											<td>Deskripsi Matakuliah</td>
											<td>Status Hapus</td>
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
	
	<div id="modal-mk" class="modal fade">
		<div class="modal-dialog modal-lg">
	    	<div class="modal-content">
	      		<div class="modal-header">
	        		<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	        		<h4 class="modal-title">Matakuliah</h4>
	      		</div>
	      		<div class="modal-body">
			      	<div id="masterpage-mk">
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
	<div id="modal-nilai" class="modal fade">
		<div class="modal-dialog modal-lg">
	    	<div class="modal-content">
	      		<div class="modal-header">
	        		<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	        		<h4 class="modal-title">Masukkan Nilai</h4>
	      		</div>
	      		<div class="modal-body">
	      			<input type="hidden" id="id-mkluar"/>
			      	<div class="form-group">
						<label>Nilai</label>
						<select id="konversiNilai" class="form-control">
							<option value="0">--Pilih--</option>
							<c:forEach items="${listKonversiNilai}" var="konversiNilai">
								<option value="${konversiNilai.idKonversi}">${konversiNilai.huruf}</option>
							</c:forEach>
						</select>
					</div>
					<div class="checkbox">
						<label>
							<input type="checkbox" id="alulus" />
							Lulus
						</label>
					</div>
	      		</div>
		        <div class="modal-footer">
		        	<button type="button" class="btn btn-primary" id="btn-cancel-modal-nilai">Cancel</button>
		        	<button type="button" class="btn btn-success" id="btn-success-modal-nilai">OK</button>
		        </div>
	    	</div><!-- /.modal-content -->
	  	</div><!-- /.modal-dialog -->
	</div><!-- /.modal -->
	
	<div class="row" id="mk-wajib-ambil" style="display:none">
		<div class="col-md-8 col-md-offset-2" id='block-calonpd-detail' style="margin-bottom:10px;">
			<div class="panel panel-white">
				<div class="panel-heading clearfix">
					<h4 class="panel-title"></h4>
				</div>
				<div class="panel-body">
					<div class="table-responsive">
						<table class="table borderless">
							<tbody id="calon-pd-detail">
							</tbody>
						</table>
					</div>
				</div>
			</div>
		</div>
		<div class="col-md-12" style="margin-bottom:10px; font-size:11px" id="block-mk">
			<div class="panel panel-white">
				<div class="panel-heading clearfix">
					<h4 class="panel-title"></h4>
				</div>
				<div class="panel-body">		
					<div class="row">
						<div class="col-md-12" style='display:none' id='tabel-mk-wajib'>						
							<div class="table-responsive">
								<table class="table table-striped table-bordered table-hover">
									<thead>
										<tr>
											<th>Kode</th>
											<th>Matakuliah</th>
											<th>SKS</th>											
											<th>Status</th>
											<th>Relasi</th>
											<th>Kode</th>
											<th>Matakuliah</th>
											<th>SKS</th>
											<th>Sifat</th>											
<!-- 											<th>Status</th> -->
											<th>Ambil</th>
											<th>Relasi</th>							
										</tr>
									</thead>
									<tbody id="mk-wajib">
									</tbody>
								</table>
							</div>
						</div>
						<div class="col-md-5" style='display:none' id='tabel-mk-alihjenjang'>						
							<div class="table-responsive">
								<table class="table table-striped table-bordered table-hover">
									<thead>
										<tr>
											<th>Kode</th>
											<th>Matakuliah</th>
											<th>SKS</th>
											<th>Status</th>																		
										</tr>
									</thead>
									<tbody id="mk-alihjenjang">
									</tbody>
								</table>
							</div>
						</div>
						<div class="col-md-7" style='display:none' id='tabel-mk-satman'>						
							<div class="table-responsive">
								<table class="table table-striped table-bordered table-hover">
									<thead>
										<tr>											
											<th>Kode</th>
											<th>Matakuliah</th>
											<th>SKS</th>
											<th>Sifat</th>
<!-- 											<th>Status</th> -->
											<th>Ambil</th>							
										</tr>
									</thead>
									<tbody id="mk-satman">
									</tbody>
								</table>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="col-md-12">
			<div class="panel panel-white">
				<div class="panel-body">
					<div class="btn-action pull-right">
						<button type="button" class="btn-primary btn" onclick="manage_mkwajib_to_master_calonpd()">Batal</button>
						<button type="button" class="btn-success btn pull-right" style="margin-left:10px;" onclick="simpan_mkwajib()">Simpan</button>
<!-- 						<button type="button" class="btn-danger btn pull-right" style="margin-left:10px;" onclick="simpan_permanen()">Simpan Permanen</button>							 -->
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<div id="modal-ekuivalensi" class="modal fade">
		<div class="modal-dialog modal-lg">
	    	<div class="modal-content">
	      		<div class="modal-header">
	        		<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	        		<h4 class="modal-title">Ekuivalensi</h4>
	      		</div>
	      		<div class="modal-body">
			      	<div id="masterpage-modal-ekuivalensi">
			      		<div class="row">
							<div class="col-md-4">
								<div class="form-group">
									<div class="form-group">
										<label>Kurikulum</label>
										<select id="filter-statusBerlaku" class="form-control">
											<option value="">Semua</option>
											<option value="true" selected>Dipakai</option>
											<option value="false">Tidak Dipakai</option>									
										</select>
									</div>
			                    </div>
			                 </div>
			            </div>
						<form class="tableform">
							<div class="table-responsive">								
								<table class="table table-striped table-bordered table-hover table-checkable table-colvis datatable" style="width:100%">
									<thead>
										<tr>
											<td>
												#
											</td>									
											<td>Katalog</td>
											<td>Kurikulum</td>		
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
		
	<div id="modal-kurikulum" class="modal fade">
		<div class="modal-dialog modal-lg">
	    	<div class="modal-content">
	      		<div class="modal-header">
	        		<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	        		<h4 class="modal-title">Kurikulum</h4>
	      		</div>
	      		<div class="modal-body">
			      	<div id="masterpage-modal-kurikulum">
			      		<div class="row">
							<div class="col-md-4">
								<div class="form-group">
									<div class="form-group">
										<label>Kurikulum</label>
										<select id="filter-kurikulumStatusBerlaku" class="form-control">
											<option value="">Semua</option>
											<option value="true" selected>Dipakai</option>
											<option value="false">Tidak Dipakai</option>									
										</select>
									</div>
			                    </div>
			                 </div>
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
	<!-- akhir dari content content -->
	
		<content tag="scripts">			
			<script type="text/javascript" src="${pageContext.servletContext.contextPath}/resources/plugins/jquery.datatables/media/js/jquery.dataTables.min.js"></script>
			<script type="text/javascript" src="${pageContext.servletContext.contextPath}/resources/plugins/jquery.datatables/extensions/ColVis/js/dataTables.colVis.min.js"></script>
			<script src="${pageContext.servletContext.contextPath}/resources/plugins/bootstrap-datepicker/js/bootstrap-datepicker.js" type="text/javascript"></script>
			<script src="${pageContext.servletContext.contextPath}/resources/plugins/gritter/js/jquery.gritter.js" rel="stylesheet" type="text/javascript"></script>
			<script src="${pageContext.servletContext.contextPath}/resources/plugins/jquery-validation/jquery.validate.min.js" rel="stylesheet" type="text/javascript"></script>
			<script	src="${pageContext.servletContext.contextPath}/resources/js/jquery.masterpage.sia.js" type="text/javascript"></script>					
			<script src="${pageContext.servletContext.contextPath}/resources/js/calonpd/home.js" type="text/javascript" ></script>
			<script>
				$('#masterpage').masterPage(
				{
					detailFocusId: '#idCalonPD',
					dataUrl: context_path+'modul/ekuivalensi/calonpd/json',
					detailUrl: context_path+'modul/ekuivalensi/calonpd/edit',
					addUrl: context_path+'modul/ekuivalensi/calonpd/simpan',
					editUrl: context_path+'modul/ekuivalensi/calonpd/simpan',
					deleteUrl: context_path+'modul/ekuivalensi/calonpd/deletemany',
					primaryKey: 'idCalonPD',
			        order: [[2,"asc"]],
					editOnClick: false,
					editOnClickRow: true,
					showAddButton: false,
					showDelButton: false,
					columnExclude:[0,5],
					cols: [
						/* idCalonPD */
						{ 
							"bVisible":    true,
							bSortable: false,
							bSearchable: false,
							mRender: function(data,type,full){
								return '<input class="checkbox-data" type="checkbox" name="idCalonPD[]" value="'+data+'"/>';
							}
						},
						/* satMan */
						{ "bVisible":    true },
						/* nmCalonPD */
						{ "bVisible":    true },
						/* ptAsal */
						{ "bVisible":    true },
						/* katalogAlihjenjang */
						{ "bVisible":    true },
						/* aEkuivalensi */
						{ 
							"bVisible":    false,
							bSortable: true,
							bSearchable: true,
							mRender: function(data){
								if(data == "false")
									return "Belum";
								else return "Sudah";
							}
						},
						/* Aksi */
						{ 
							"bVisible":    true,
							bSortable: false,
							bSearchable: false,
							mRender: function(data,type,full){
								var action = "";
								if(full[5] == 'false')
								{								
									if(full[4] != "")
										action += ' <button type="button" class="btn btn-success" onclick="modal_katalog_kurikulum(\''+full[0]+'\',\''+full[2]+'\')">Kelola Ekuivalensi</button>';
									action += ' <button type="button" class="btn btn-success" onclick="modal_kurikulum(\''+full[0]+'\',\''+full[2]+'\')">Kelola MK Wajib</button>';
									<c:if test = "${admin==true}">
										action += ' <button type="button" class="btn btn-success" onclick="simpan_permanen(\''+full[0]+'\')">Simpan Permanen</button>';
									</c:if>
								}
								else {
									action += ' <button type="button" class="btn btn-success report" onclick="laporan(\''+full[0]+'\')">Laporan Alih Jenjang</button>';
									action += ' <button type="button" class="btn btn-danger" onclick="buka_ekuivalensi(\''+full[0]+'\')">Batalkan Ekuivalensi</button>';
								}
								return action;
							}
						}
					],
					filters: [{id:'#filter-aEkuivalensi', name:'aEkuivalensi'}],	
				});
			</script>
			<jsp:include page="../Footer.jsp" />
		</content>
	</body>
</html>