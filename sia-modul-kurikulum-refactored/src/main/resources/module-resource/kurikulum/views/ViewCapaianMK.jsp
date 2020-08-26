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
						<p>Tabel menampilkan capaian pembelajaran untuk mata kuliah</p>
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
											<td>Mata Kuliah</td>
											<td>Capaian MK</td>
											<td>Deskripsi Capaian MK</td>  
											<td>Capaian Satuan Manajemen</td>
											<td>Deskripsi Capaian Satuan Manajemen</td>
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
									<h4 class="panel-title">Capaian Belajar</h4>
								</div>
								<div class="panel-body">
									<h4 id="title">Kelola capaian belajar untuk mata kuliah</h4>
									<form:form role="form" commandName="capPembMK" class="formdetail">  
										<div class="form-group">
											<label>Mata Kuliah</label>
											<select id="idMK" name="idMK" class="form-control">
													<option value="">Pilih kode dan nama mata kuliah</option> 
												<c:forEach items="${mkList}" var="mk"> 
													<option value="${mk.idMK}">${mk.kodeMK} - ${mk.namaMK}</option>
												</c:forEach> 
											</select>
										</div>   
										<div class="form-group">
											<label>Capaian Pembelajaran dari Satuan Manajemen</label> 
											<br />  
											
											<button type="button" class="btn btn-primary" onclick="showModal()">Tambah capaian pembelajaran satuan manajemen</button>
										</div>  
										 <div id="capPembDiv">   
										 <input type='hidden' name='idCapPemb[]' value="" />
										 </div> 
										<div class="form-group">
											<label>Nama Capaian Belajar Mata Kuliah</label>
											<form:input path="namaCapPembMK" class="form-control" placeholder="Berisi nama capaian pembelajaran untuk mata kuliah" required="true" />
											<form:hidden path="idCapPembMK" class="form-control" />
										</div> 
										<div class="form-group">
											<label>Deskripsi Capaian Belajar Mata Kuliah</label>
											<form:input path="deskripsiCapPembMK" class="form-control" placeholder="Berisi deskripsi capaian pembelajaran untuk mata kuliah" />
										</div>
										
										<div class="form-group detailcontrol" id="triggerbutton">
										</div>
							        </form:form>
							        </div>
						</div>
					</div>
				</div> 
				<div id="myModal" class="modal fade">
				  <div class="modal-dialog modal-lg">
				    <div class="modal-content">
				      <div class="modal-header">
				        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
				        <h4 class="modal-title">Capaian Pembelajaran dari Satuan Manajemen</h4>
				      </div>
				      <div class="modal-body">
				      	<div id="masterpageCapPembMK"> 
							<form class="tableform">
								<table class="table table-striped table-bordered table-hover table-checkable table-colvis datatable" style="width:100%">
									<thead>
										<tr>
											<td>
												#
											</td>
											<td>Tahun Kurikulum</td>
											<td>Nama Kurikulum</td>
											<td>Nama Satuan Manajemen</td>
											<td>Nama Capaian</td>
											<td>Deskripsi Capaian</td>  
										</tr>
									</thead>
									<tbody>
											 
									</tbody>
								</table>
							</form>
						</div>
				      </div>
				      <div class="modal-footer">
				      </div>
				    </div><!-- /.modal-content -->
				  </div><!-- /.modal-dialog -->
				</div><!-- /.modal -->
							
		
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
					var showModal;
						//$("#idMK").select2();
						$('#masterpage').masterPage(
						{ 
							detailFocusId: '#idCapPembMK',
							dataUrl: context_path+'modul/kurikulum/matakuliah/capaianbelajar/json',
							detailUrl: context_path+'modul/kurikulum/matakuliah/capaianbelajar/edit',
							addUrl: context_path+'modul/kurikulum/matakuliah/capaianbelajar/simpan',
							editUrl: context_path+'modul/kurikulum/matakuliah/capaianbelajar/simpan',
							deleteUrl: context_path+'modul/kurikulum/matakuliah/capaianbelajar/deletemany',
							primaryKey: 'idCapPembMK',
					        order: [[3,"asc"]],
							editOnClick: false,
							editOnClickRow: true,
							cols: [
								/* id */
								{ 
									"bVisible":    true,
									bSortable: false,
									mRender: function(data,type,full){ 
										//console.log(data);
										return '<input type="checkbox" class="checkbox-data" name="idCapPembMK[]" value="'+data+'">';
									}
								},
								/* mata kuliah */
								{ "bVisible":    true }, 
								/* nama capaian MK */
								{ "bVisible":    true },
								/* deskripsi capaian MK */
								{ "bVisible":    true }, 
								/* nama capaian induk */
								{ "bVisible":    true }, 
								/* deskripsi capaian */
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
										if(full[5]=='false') return action += ' <button type="button" class="btn btn-danger deleterow">Hapus</button>';
										return action;
									}
								}
							],
							validationRules: {idMK:{required: true}, namaCapPembMK:{required: true}},
							filters: [{id:'#filter', name:'statusCapPembMK'}],
							callOnFillForm : function(response,options){   
								$("#idCapPembMK").val(response.data.idCapPembMK); 
								$("#idMK").val(response.data.mk.idMK);
								$.ajax({
									type: 'get',
									url : context_path+'modul/kurikulum/matakuliah/capaianbelajar/getcappemblist', 
									dataType : 'json',
									data : {'idCapPembMK' : $("#idCapPembMK").val()},
									contentType : 'application/json; charset=utf-8', 
									traditional : true, 
									success : function(data){  
										var labelId = "indukCapPemb"; 
										$("#capPembDiv").html("<input type='hidden' name='idCapPemb[]' value=''/>"); 
										if(data.data!=null){
											for(var i=0; i<data.data.length; ++i){
												if(data.data[i].capPemb != null){
													//console.log(data.data[i].capPemb.namaCapPemb);
													$("#capPembDiv").append(
															"<div class='alert alert-warning alert-dismissable'>"
																+ "<button type='button' id='button1' class='close' data-dismiss='alert' aria-hidden='true' onclick='removeHiddenId(\"" + labelId + i + "\")'>x</button>" 
																+"<p>"+data.data[i].capPemb.namaCapPemb+"<p>"
															+"</div>"
															+"<input type='hidden' id='"+ labelId + i +"' name='idCapPemb[]' value='"+data.data[i].capPemb.idCapPemb+"' />")
												} 
												else{ 
														$("#capPembDiv").append("<input type='hidden' name='idCapPemb[]' value=''/>"); 
												}
											}  
										}  
									},
									error: function(e){
										alert("Data capaian satuan manajemen tidak ditemukan");
									}
								}); 
							} 
							
						});
						
						removeHiddenId = function(id){ 
							var str = '#' + id;
							var hiddenLabel = $(str);
							//console.log(hiddenLabel);
							hiddenLabel.remove();
						}
						
						showModal = function (){
							$('#myModal').modal('show');
						}
// 						$("#triggerbutton").click(function(){
// 							$("#capPembDiv").each(function(index, element) {
// 								$(element).remove();
// 							});
// 						})
						
						$('#myModal').on('shown.bs.modal', function (e) {
							$("#masterpageCapPemb").find('.dataTables_length select').change();
							  //if (!data) return e.preventDefault() // stops modal from being shown
						})
						$('#masterpageCapPembMK').masterPage(
						{
							detailFocusId: '#idCapPemb',
							dataUrl: context_path+'modul/kurikulum/matakuliah/capaianbelajar/subcapaian/json',
							detailUrl: context_path+'modul/kurikulum/matakuliah/capaianbelajar/subcapaian/edit',
							primaryKey: 'idCapPemb',
					        order: [[3,"asc"]],
							editOnClick: false,
							dialogDetail: '',
							editOnClickRow: false,
							cols: [
								/* id capaian pembelajaran */
								{ 
									"bVisible":    true,
									bSortable: false,
									bSearchable: false,
									mRender: function(data,type,full){
										//console.log(data);
										return '<button type="button" class="btn btn-primary">Pilih</button>';
									}
								},
								/* tahun kurikulum */
								{ "bVisible":    false },
								/* nama kurikulum  */
								{ "bVisible":    true },
								/* nama satuan manajemen */
								{ "bVisible":    false },
								/* nama capaian */
								{ "bVisible":    true }, 
								/* deskripsi */
								{ "bVisible":    true }, 
							],
							callOnSelect : function(aData, options){
								//console.log(aData);
								$("#capPembDiv").append(
										"<div class='alert alert-warning alert-dismissable'>"
											+"<button type='button' class='close' data-dismiss='alert' aria-hidden='true'>x</button>"
											+"<p>"+aData[4]+"<p>"
										+"</div>" 
 										+"<input type='hidden' name='idCapPemb[]' id='labelId' value='"+ aData[0] +"' />"
										);  
								$('#myModal').modal('toggle'); 
							}
						});
				});
				</script>
		</content>
	</body>
</html>
		