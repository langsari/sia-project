<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>Kelola Nilai Kelas - ${namaKelas}</title>
		
		<!-- Styles -->
		<link
			href="${pageContext.servletContext.contextPath}/resources/plugins/uniform/css/uniform.default.min.css"
			rel="stylesheet" />
		<link
			href="${pageContext.servletContext.contextPath}/resources/plugins/line-icons/simple-line-icons.css"
			rel="stylesheet" type="text/css" />
		<link
			href="${pageContext.servletContext.contextPath}/resources/plugins/switchery/switchery.min.css"
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
			
		<script
			src="${pageContext.servletContext.contextPath}/resources/plugins/3d-bold-navigation/js/modernizr.js"></script>
		<script
			src="${pageContext.servletContext.contextPath}/resources/plugins/offcanvasmenueffects/js/snap.svg-min.js"></script>
		
		<!-- css dan js spesifik -->
		
		<link
			href="${pageContext.servletContext.contextPath}/resources/plugins/jquery.datatables/media/css/jquery.dataTables.min.css"
			rel="stylesheet" type="text/css" />
		
		<link
			href="${pageContext.servletContext.contextPath}/resources/plugins/jquery.datatables/extensions/TableTools/css/dataTables.tableTools.min.css"
			rel="stylesheet" type="text/css" />
		
		        
	</head>
	<body>
		<!-- content -->
		<div class="container">
			<div class="row">
				<div class="col-md-6 col-md-offset-3">
					<div class="panel panel-white">
						<div class="panel-heading">
							<h4 class="panel-title">Daftar Kelas</h4>
						</div>
						<div class="panel-body">						
							<form method="post" action="">
								<div class="form-group">
									<select class="form-control" name="idPemb">
										<c:forEach var="kelas" items="${listKelas}">
										<option value="${kelas.getIdPemb()}"><c:out value="${kelas.getMk().getNamaMK()} ${kelas.getNmPemb()}"></c:out></option>
										</c:forEach>
									</select>
								</div>
								<button type="submit" class="btn btn-primary pull-right">Buka</button>
							</form>
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-md-12">
					<div class="panel panel-white">
						<div class="panel-heading">
							<h4 class="panel-title">Kelola Nilai</h4>
						</div>
						<div class="panel-body">
							<form action="simpan_nilai/">
								<div class="pull-right" id="buttonGroupAtas">
								</div>
								<table class="table" id="tabelNilai">
									<thead>
										<tr>
											<th style="width:10%">NRP</th>
											<th style="width:40%">Nama Mahasiswa</th>
											<c:forEach var="komponen" items="${listKomponen}">
												<th class="kolom-komponen"><c:out value="${komponen.getNamaKomponen()}"></c:out></th>
											</c:forEach>
											<th>Nilai Akhir</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach var="krs" items="${krsInfo}">
											<tr class="mahasiswa" name="${krs.getIdKrs()}">
												<td><c:out value="${krs.getPd().getNimPd()}"></c:out></td>
												<td><c:out value="${krs.getPd().getNmPd()}"></c:out></td>
												<c:forEach var="komponen" items="${listKomponen}">
													<td class="komponen-nilai" name="${komponen.getIdKomponen()}">
														<c:set var="resultNilai" value="0" scope="page"></c:set>
														<c:forEach var="nilai" items="${listNilai}">
															<c:if test="${nilai.getKrs().getIdKrs() == krs.getIdKrs() && nilai.getKomponenNilai().getIdKomponen() ==  komponen.getIdKomponen()}">
																<c:set var="resultNilai" value="${nilai.getNilai()}" scope="page"></c:set>
															</c:if>
														</c:forEach>
														<input type="text" class="form-control nilai" value='${resultNilai}' />
													</td>
												</c:forEach>
												<td><input type="text" class="form-control nilai-akhir" value="${krs.getNilaiAkhir()}" disabled/></td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
								<br />
								<div class="pull-right">
									<button type="button" class="btn btn-success" id="tombolSimpanNilai">Simpan</button>
									<button type="button" class="btn btn-primary" data-toggle="modal" data-target="#modalUpload"><span class="glyphicon glyphicon-upload"></span> Unggah File</button>
									<button type="button" class="btn btn-warning" data-toggle="modal" data-target="#modalKomponen">Atur Komponen</button>
								</div>
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
		<!-- end of content -->
		
		<!-- modal komponen -->
		<div class="modal fade" id="modalKomponen" tabindex="-1" role="dialog" aria-labelledby="modalKomponenTitle" aria-hidden="true" data-backdrop="static">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" id="closeKomponen" data-dismiss="modal" aria-hidden="true">×</button>
						<h4 class="modal-title" id="modalKomponenTitle">Atur Komponen Nilai</h4>
					</div>
					<div class="modal-body">
						<p>Silahkan atur nama komponen penilaian dan persentase setiap komponen di bawah.
						Untuk persentase komponen cukup tuliskan angkanya saja, tidak perlu memakai simbol %.
						Total persentase komponen tidak boleh melebihi 100%.</p>
						<div class="row">
							<div class="col-md-12">
								<form id="formKomponen">
									<table class="table" id="tabelKomponen">
										<thead>
											<tr>
												<th>Nama Komponen</th>
												<th>Persentase Komponen</th>
												<th>Action</th>
											</tr>
										</thead>
										<tbody>
											<c:forEach var="komp" items="${listKomponen }">
											<tr class="komponen-modal" id="${komp.getIdKomponen()}" name="${komp.getIdKomponen()}">													
												<td><input type="text" class="form-control nama-komponen" value="<c:out value="${komp.getNamaKomponen()}"></c:out>"/></td>
												<td><input type="text" class="form-control persentase-komponen" value="<c:out value="${komp.getPersentaseKomponen()}"></c:out>"/></td>
												<td><button type="button" class="btn btn-danger tombolHapusKomponen"><i class="glyphicon glyphicon-remove"></i></button></td>
											</tr>
											</c:forEach>
											<tr id="newRowKomponen">
												<td><input type="text" class="form-control" id="namaKomponenNew"/></td>
												<td><input type="text" class="form-control" id="persentaseKomponenNew"/></td>
												<td><button type="button" class="btn btn-success" id="tombolTambahKomponen"><i class="glyphicon glyphicon-plus"></i></button></td>
											</tr>
										</tbody>
									</table>
									<button type="button" class="btn btn-primary pull-right" id="tombolSimpanKomponen" data-loading-text="Menyimpan...">Simpan</button>
								</form>
							</div>
						</div>
					</div>
					<div class="modal-footer">
					</div>
				</div>
			</div>
		</div>
		<!-- end of modal -->
		
		<!-- modal upload file -->
		<div class="modal fade" id="modalUpload" tabindex="-1" role="dialog" aria-labelledby="modalUploadTitle" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
						<h4 class="modal-title" id="modalUploadTitle">Unggah File Excel</h4>
					</div>
					<div class="modal-body">
						<form method="post" action="unggah_file/" enctype="multipart/form-data" id="formUpload">
							<div class="form-group">
								<input type="file" class="form-control" name="file" id="inputFile" />
							</div>
							<button type="submit" class="btn btn-primary" id="buttonUploadFile" data-loading-text="Mengunggah...">Unggah</button>
						</form>
					</div>
					<div class="modal-footer">
					</div>
				</div>
			</div>
		</div>
		<!-- end of modal -->
		
		<!-- script khusus-->
		<content tag="scripts">
			<script src="${pageContext.servletContext.contextPath}/resources/plugins/jquery.datatables/media/js/jquery.dataTables.min.js"></script>
			<script src="${pageContext.servletContext.contextPath}/resources/plugins/jquery.datatables/extensions/TableTools/js/dataTables.tableTools.min.js"></script>
			<script>
				$(document).ready(function() {
					var idPemb = "${idPemb}";
				
					updateNilaiAkhir();
					
					var table = $("#tabelNilai").DataTable({paging:false});
					
					var tableTools = new $.fn.dataTable.TableTools(table, {
						"sSwfPath": context_path+"resources/plugins/jquery.datatables/extensions/TableTools/swf/copy_csv_xls_pdf.swf",
						"aButtons" : [{
							"sExtends" : "csv", 
							"sButtonText" : "Unduh File",
							"fnCellRender" : function ( sValue, iColumn, nTr, iDataIndex ) {
								if($(sValue).is("input")) {
									return $(sValue).val();
								}
								return sValue;
							}
							}]
					});
					
					$("#buttonGroupAtas").append($(tableTools.fnContainer()));
					
					//script merubah nilai akhir di tabel
					$(".nilai").change(function() {
						var baris = $(this).closest("tr");
						var elemenNilai = $(baris).find("input.nilai");
						var nilaiAkhir = 0;
						
						$(elemenNilai).each(function(index, element) {
							var nilai = $(element).val();
							var idKomp = $(element).closest("td").attr("name");
							var persenKomp = $("#" + idKomp).find("input.persentase-komponen").val();
							
							nilaiAkhir += (nilai*persenKomp)/100;
						});
						
						$(baris).find("input.nilai-akhir").val(nilaiAkhir);
					});
					
					function updateNilaiAkhir() {
						$(".mahasiswa").each(function(index, element) {
							var baris = $(element);
							var elemenNilai = $(baris).find("input.nilai");
							var nilaiAkhir = 0;
							
							$(elemenNilai).each(function(index, element) {
								var nilai = $(element).val();
								var idKomp = $(element).closest("td").attr("name");
								var persenKomp = $("#" + idKomp).find("input.persentase-komponen").val();
								
								nilaiAkhir += (nilai*persenKomp)/100;
							});
							
							$(baris).find("input.nilai-akhir").val(nilaiAkhir);
						});
					}
					
					//script tambah komponen
					$("#tombolTambahKomponen").click(function() {
						if($("#namaKomponenNew").val() != "" && $("#persentaseKomponenNew").val() != "") {
							var namaKomp = $("#namaKomponenNew").val();
							var persenKomp = $("#persentaseKomponenNew").val();
							var totalPersen = 0.0;
							
							$("input.persentase-komponen").each(function(index, element) {
								totalPersen += parseFloat($(element).val());
							});
							
							if(parseFloat(totalPersen + parseFloat(persenKomp)) > 100.0) {
								toastr["warning"]("Total persentase tidak boleh di atas 100", "Peringatan");
							}
							else {
								var komp = {
										"idKomponen" : null,
										"namaKomponen" : namaKomp,
										"persentaseKomponen" : persenKomp,
										"aKompAktif" : true,
										"pemb" : null
								};
								
								$.ajax({
									url : idPemb + "/tambah_komponen/",
									type : "POST",
									contentType: "application/json",
									data : JSON.stringify(komp),
									success : function(data) {
										if(data.status == "ok") {
											$("#newRowKomponen").before('<tr id="' + data.data + '"' + 'name="'+ data.data + '">'
												+ '<td><input type="text" class="form-control nama-komponen" value="' + namaKomp + '"/></td>'
												+ '<td><input type="text" class="form-control persentase-komponen" value="' + persenKomp +'"/></td>'
												+ '<td><button type="button" class="btn btn-danger tombolHapusKomponen"><i class="glyphicon glyphicon-minus"></i></button></td>'
												+ '</tr>'
											);
											toastr["success"](data.message, "Sukses");
										}
									}
								});
							}
							
							$("#namaKomponenNew").val("");
							$("#persentaseKomponenNew").val("");
						}
						else {
							toastr["warning"]("Data komponen nilai baru kurang lengkap", "Peringatan");
						}
					});
					
					//hapus komponen
					$("body").on("click", ".tombolHapusKomponen", function() {
						var idKomponen = $(this).closest("tr").attr('name');
						var button = $(this);
						$.ajax({
							url : "hapus_komponen/",
							type : "POST",
							dataType : "json",
							data : {"idKomp" : idKomponen},
							success : function(data) {
								$(button).closest("tr").remove();
								toastr["success"](data.message, "Sukses");
							}
						});
					});
					
					//simpan komponen
					$("#tombolSimpanKomponen").click(function() {
						var totalPersen = 0.0;
						$("input.persentase-komponen").each(function(index, element) {
							totalPersen += parseFloat($(element).val());
						});
						
						if(totalPersen > 100) {
							toastr["warning"]("Total persentase tidak boleh di atas 100", "Peringatan");
						}
						else if(totalPersen < 100) {
							toastr["warning"]("Total persentase tidak boleh di bawah 100", "Peringatan");
						}
						else {
							$("#tombolSimpanKomponen").button("loading");
							var listKomponen = new Array();
							$("tr.komponen-modal").each(function(index, element) {
								var idKomponen = $(element).attr("name");
								var namaKomponen = $(element).find("input.nama-komponen").val();
								var persentaseKomponen = $(element).find("input.persentase-komponen").val();
								
								var komponen = {
										"idKomponen" : idKomponen,
										"namaKomponen" : namaKomponen,
										"persentaseKomponen" : persentaseKomponen,
										"aKompAktif" : true,
										"pemb" : null
								};
								
								listKomponen.push(komponen);
							});
							
							$.ajax({
								url : idPemb + "/simpan_komponen/",
								type : "POST",
								contentType : "application/json",
								data : JSON.stringify(listKomponen),
								success : function(data) {
									if(data.status == "ok") {
										$("#tombolSimpanKomponen").button("reset");
										toastr["success"](data.message, "Sukses");
										location.reload();
									}
								}
							});
						}
					});
					
					//tombol close komponen
					$("#closeKomponen").click(function() {
						location.reload();
					});
					
					//submit nilai
					$("#tombolSimpanNilai").click(function() {
						var listNilai = new Array();
						var nilaiValid = true;
						
						$("tr.mahasiswa").each(function(index, element) {
							var idKrs = $(element).attr("name");
							var komponens = $(element).find("td.komponen-nilai");
							
							$(komponens).each(function(index, element) {
								var idKomp = $(element).attr("name");
								var nilai = $(element).find("input").val();
								
								if(!((nilai <= 100) && (nilai >= 0))) {
									$(element).find("input").addClass("has-error");
									nilaiValid = false;
								}
								else {
									$(element).find("input").removeClass("has-error");
								}
								
								var objNilai = {
										"idKrs" : idKrs,
										"idKomp" : idKomp,
										"nilai" : nilai
								};
								
								listNilai.push(objNilai);
							});
						});
						
						if(nilaiValid) {
							$.blockUI({message : '<p>Sedang menyimpan nilai...</p>'});
							$.ajax({
								url : idPemb + "/simpan_nilai/",
								type : "POST",
								contentType : "application/json",
								data : JSON.stringify(listNilai),
								success : function(data) {
									if(data.status == "ok") {
										$.unblockUI();
										toastr["success"](data.message, "Sukses");
									}
								}
							});
						}
						else {
							toastr["warning"]("Nilai harus berupa angka dan berada pada rentang 0 - 100.", "Peringatan");
						}
					});
					
					//upload file
					$("#formUpload").submit(function(e) {
						e.preventDefault();
						$("#buttonUploadFile").button("loading");
						
						var daftarKomponen = new Array();
						$("th.kolom-komponen").each(function(index, element) {
							var namaKomponen = $(element).text();
							daftarKomponen.push(namaKomponen);
						});
						
						var formData = new FormData();
						var input = $("#inputFile");
						formData.append("file", input[0].files[0]);
						formData.append("daftarKomponen", daftarKomponen);
						$.ajax({
							url : "unggah_file/",
							type : "POST",
							data : formData,
							processData: false,
							contentType: false,
							success : function(data) {
								if(data.status == "ok") {
									toastr["success"](data.message, "Sukses");
									var daftarNilai = data.data;
									
									for(var i=0; i<daftarNilai.length; i++) {
										var row = $("td").filter(function() {
											return $(this).text() == daftarNilai[i][0]
										}).closest("tr");
										
										var komponens = $(row).find("td.komponen-nilai");
										$(komponens).each(function(index, element) {
											$(element).find("input").val(daftarNilai[i][index+2]);
										});
									}
									updateNilaiAkhir();
								}
								else if(data.status == "warning") {
									toastr["warning"](data.message, "Peringatan");
								}
								else if(data.status == "fail") {
									toastr["error"](data.message, "Error");
								}
								
								$("#buttonUploadFile").button("reset");
							}
						});
					});
				});
			</script>
			<!-- end of script -->
			
			<%@include file="footer.jsp" %>
		</content>
	</body>
</html>