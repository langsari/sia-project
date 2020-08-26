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
						<h4 class="panel-title">Katalog SatMan</h4>
					</div>
					<div class="panel-body">
						<div class="row">
							<div class="col-md-4">	
								<div class="form-group">
									<label>Katalog</label>
									<select id="filter-katalog" name="filter" class='form-control'>
										<option value="">Semua</option>
										<c:forEach items="${listKatalog}" var="katalog">
										<option value="${katalog.idKatalogAlihjenjang}">${katalog.nmKatalog}</option>
										</c:forEach>
									</select>
								</div>
								<div class="form-group">
									<label>Satuan Manajemen</label>
									<select id="filter-satman" name="filter" class='form-control'>
										<c:if test = "${admin==true}">
										<option value="">Semua</option>
										</c:if>										
										<c:forEach items="${listSatMan}" var="satMan">
										<option value="${satMan.idSatMan}">${satMan.nmSatMan}</option>
										</c:forEach>
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
												<input class="checkbox-all" type="checkbox">
											</td>
											<td>ID Katalog</td>
											<td>Katalog</td>		
											<td>ID SatMan</td>							
											<td>Satuan Manajemen</td>
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
			<div class="col-md-6 col-md-offset-3" style="margin-bottom:10px;">
				<div class="panel panel-white">
					<div class="panel-heading clearfix">
						<h4 class="panel-title">Kelola Katalog SatMan</h4>
					</div>
					<div class="panel-body">
						<form:form role="form" commandName="katalogSatMan" class="formdetail">							
							<div class="form-group">
								<label>Katalog</label>
								<select id="katalog" name="idKatalog" class="form-control">								
									<c:forEach items="${listKatalog}" var="katalog">
										<option value="${katalog.idKatalogAlihjenjang}">${katalog.nmKatalog}</option>
									</c:forEach>
								</select>
							</div>
							<div class="form-group">
								<label>Satuan Manajemen</label>
								<select id="satMan" name="idSatMan" class="form-control">								
									<c:forEach items="${listSatMan}" var="satMan">
										<option value="${satMan.idSatMan}">${satMan.nmSatMan}</option>
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
		
		<div class="row" id="masterpage-katalog-kurikulum" style="display:none">
			<div class="col-md-12" style="margin-bottom:10px;">
				<div class="panel panel-white">
					<div class="panel-heading clearfix">
						<h4 class="panel-title">Katalog Kurikulum</h4>
					</div>
					<div class="panel-body">
						<div class="row">
							<div class="col-md-4">											
							</div>
							<div class="col-md-8 masteractions panel-body">
								<div class="btn-action pull-right">	
									<button class="btn btn-primary" id='katalog-kurikulum-to-masterpage'>Kembali</button>
									<button class="btn btn-success" id='tambah-katalog-kurikulum'>Tambah</button>								
								</div>
							</div>
						</div>
						<form class="tableform">
							<div class="table-responsive">
							<input type="hidden" id="idKatalogSatMan-hidden" name = "idKatalogSatMan" />
							<input type="hidden" id="idKatalog-hidden" name = "idKatalog" />
							<input type="hidden" id="nmKatalog-hidden" name = "nmKatalog" />
							<input type="hidden" id="idSatMan-hidden" name = "idSatMan" />
								<table class="table table-striped table-bordered table-hover table-checkable table-colvis datatable">
									<thead>
										<tr>
											<td>
												<input class="checkbox-all" type="checkbox">
											</td>											
											<td>Katalog</td>									
											<td>Kurikulum</td>
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
		
		<div class="row" id="manage-ekuivalensi" style="display:none">
			<input type="hidden" id="idSatMan-hidden" />
			<input type="hidden" id="idKurikulum-hidden" name = "idKurikulum" />
			<div class="col-md-4" style="margin-bottom:10px;">
				<div class="panel panel-white">
					<div class="panel-heading clearfix">
						<h4 class="panel-title" id="title-ekuivalensi-mk-alihjenjang"></h4>
					</div>
					<div class="panel-body">
						<div class="col-md-12 masteractions panel-body">
							<div class="btn-action pull-right">
								<button class="btn btn-success" id='tambah-mk-alihjenjang'>Tambah</button>
							</div>
						</div>	
						<table class="table table-striped table-bordered">										
							<thead>
								<tr>
									<th>Kode</th>
									<th>Matakuliah</th>
									<th>SKS</th>					
								</tr>
							</thead>
							<tbody id="mk-alihjenjang-selected">
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
							<select id="dropdown-relasi-mk-alihjenjang" class="form-control">
								<option value="1">-Pilih-</option>
								<option value="^">^(AND)</option>
								<option value="/">/(OR)</option>
								<option value="0">Lain</option>
							</select>
							<input class="form-control" id="textbox-relasi-mk-alihjenjang" type="text" placeholder="Masukkan relasi" style="margin-top:10px;display:none">										
						</div>
						<div class="form-group" id='info' style="cursor:pointer">
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
						<h4 class="panel-title" id="title-ekuivalensi-mk-satman"></h4>
					</div>
					<div class="panel-body">
						<div class="col-md-12 masteractions panel-body">
							<div class="btn-action pull-right">
								<button class="btn btn-success" id='tambah-mk-satman'>Tambah</button>
							</div>
						</div>	
						<table class="table table-striped table-bordered">										
							<thead>
								<tr>
									<th>Kode</th>
									<th>Matakuliah</th>
									<th>SKS</th>
<!-- 									<th>Semester</th> -->
									<th>Sifat</th>															
								</tr>
							</thead>
							<tbody id="mk-satman-selected">
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
							<select id="dropdown-relasi-mk" class="form-control">
								<option value="1">-Pilih-</option>
								<option value="^">^(AND)</option>
								<option value="/">/(OR)</option>
								<option value="0">Lain</option>
							</select>
							<input class="form-control" id="textbox-relasi-mk" type="text" placeholder="Masukkan relasi" style="margin-top:10px;display:none">										
						</div>
						<div class="form-group" id='info' style="cursor:pointer">
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
									<th>Relasi</th>
									<th>Kode</th>
									<th>Matakuliah</th>
									<th>SKS</th>
<!-- 									<th>Semester</th> -->
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
							<button type="button" class="btn btn-primary" id="manage-ekuivalensi-to-masterpage-kurikulum" style="margin-bottom:10px;">Kembali</button>
							<button type="button" class="btn btn-success" id="simpan-mk-ekuivalen" style="margin-bottom:10px;">Simpan</button>
						</div>
					</div>
				</div>
			</div>
		</div>	
		
		<div id="modal-kurikulum" class="modal fade">
			<div class="modal-dialog modal-lg">
		    	<div class="modal-content">
		      		<div class="modal-header">
		        		<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
		        		<h4 class="modal-title">Kurikulum</h4>
		      		</div>
		      		<div class="modal-body">
				      	<div id="masterpage-modal-kurikulum">
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
		    	</div>
		  	</div>
		</div>
		
		<div id="modal-mk-alihjenjang" class="modal fade">
			<div class="modal-dialog modal-lg">
		    	<div class="modal-content">
		      		<div class="modal-header">
		        		<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
		        		<h4 class="modal-title">Matakuliah</h4>
		      		</div>
		      		<div class="modal-body">
				      	<div id="masterpage-modal-mk-alihjenjang">
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
												<td>Deskripsi</td>
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
		    	</div>
		  	</div>
		</div>
		
		<div id="modal-mk-satman" class="modal fade">
			<div class="modal-dialog modal-lg">
		    	<div class="modal-content">
		      		<div class="modal-header">
		        		<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
		        		<h4 class="modal-title">Kurikulum</h4>
		      		</div>
		      		<div class="modal-body">
				      	<div id="masterpage-modal-mk-satman">
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
<!-- 												<td>Semester</td> -->
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
		    	</div>
		  	</div>
		</div>
		
		<div id="modal-info-relasi" class="modal fade">
			<div class="modal-dialog modal-lg">
		    	<div class="modal-content">
		      		<div class="modal-header">
		        		<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
		        		<h4 class="modal-title">Keterangan relasi</h4>
		      		</div>
		      		<div class="modal-body">
				      	<p>
				      		Relasi digunakan ketika matakuliah yang direlasikan lebih dari 1. </br>
				      		1. Relasi AND(^), digunakan jika operator yang dibutuhkan hanya AND(^). Contoh : Bhs. Indonesia AND Kewarganegaraan</br>
				      		   Kedua matakuliah harus lulus supaya bebas dari ekuivalensi. </br>
				      		2. Relasi OR(/), digunakan jika operator yang dibutuhkan hanya OR(/). Contoh : Agama Islam OR Agama Kristen OR Agama Hindu, dll</br>
				      		   Minimal satu matakuliah harus lulus supaya bebas dari ekuivalensi. </br>
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
<!-- akhir dari content content -->
		<content tag="scripts">			
			<script type="text/javascript" src="${pageContext.servletContext.contextPath}/resources/plugins/jquery.datatables/media/js/jquery.dataTables.min.js"></script>
			<script type="text/javascript" src="${pageContext.servletContext.contextPath}/resources/plugins/jquery.datatables/extensions/ColVis/js/dataTables.colVis.min.js"></script>
			<script src="${pageContext.servletContext.contextPath}/resources/plugins/bootstrap-datepicker/js/bootstrap-datepicker.js" type="text/javascript"></script>
			<script src="${pageContext.servletContext.contextPath}/resources/plugins/gritter/js/jquery.gritter.js" rel="stylesheet" type="text/javascript"></script>
			<script src="${pageContext.servletContext.contextPath}/resources/plugins/jquery-validation/jquery.validate.min.js" rel="stylesheet" type="text/javascript"></script>
			<script	src="${pageContext.servletContext.contextPath}/resources/js/jquery.masterpage.sia.js" type="text/javascript"></script>					
			<script src="${pageContext.servletContext.contextPath}/resources/js/katalogsatman/home.js" type="text/javascript" ></script>
			<script>
			$(document).ready(function(){
			$('#masterpage').masterPage(
			{
				detailFocusId: '#idKatalogSatMan',
				dataUrl: context_path+'modul/ekuivalensi/katalogsatman/json',
				addUrl: context_path+'modul/ekuivalensi/katalogsatman/simpan',
				editUrl: context_path+'modul/ekuivalensi/katalogsatman/simpan',
				deleteUrl: context_path+'modul/ekuivalensi/katalogsatman/deletemany',
				primaryKey: 'idKatalogSatMan',
		        order: [[1,"asc"]],
		        columnExclude:[0,1,3],        
				editOnClick: false,
				editOnClickRow: true,
				<c:if test = "${admin==false}">
				showAddButton : false,
				showDelButton : false,
				</c:if>
				cols: [
					/* idKatalogSatMan */
					{ 
						"bVisible":    true,
						bSortable: false,
						bSearchable: false,
						mRender: function(data,type,full){
							return '<input class="checkbox-data" type="checkbox" name="idKatalogSatMan[]" value="'+data+'">';
						}
					},
					/* idKatalogAlihjenjang */
					{ "bVisible":    false },
					/* nmKatalog */
					{ "bVisible":    true },
					/* idSatMan */
					{ "bVisible":    false },
					/* nmSatMan */
					{ "bVisible":    true },
					/* Aksi */
					{ 
						"bVisible":    true,
						bSortable: false,
						bSearchable: false,
						mRender: function(data,type,full){
							var action = '';
							<c:if test = "${admin==true}">
								action += ' <button type="button" class="btn btn-danger deleterow">Hapus</button>';
							</c:if>
								
								action += ' <button type="button" class="btn btn-success" onclick="masterpage_to_masterkatalogkurikulum(\''+full[0]+'\',\''+full[1]+'\',\''+full[2]+'\',\''+full[3]+'\')">Kelola</button>';
							return action;
						}
					}
				],
				validationRules: {idKatalogAlihjenjang:{required: false},idKatalog:{required: true}, idSatMan:{required: false}},
				filters: [{id:'#filter-katalog', name:'idKatalog'},{id:'#filter-satman', name:'idSatMan'}],
				callOnFillForm:function(data,option)
				{
					$("#katalog").val(data.data.katalog.idKatalogAlihjenjang);
					$("#satMan").val(data.data.satMan.idSatMan);
				},
			});
			});
			</script>
			<jsp:include page="../Footer.jsp" />
		</content>
	</body>
</html>
