<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page session="false"%>


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
							<h4 class="panel-title">Pembelajaran</h4>
						</div>
						<div class="panel-body">
							<div class="row">
								<div class="col-md-4">
									<div class="form-group">
										<label>Tahun Ajaran</label> <select class="form-control"
											id="filter" name="filter">
											<option value=""></option>
											<c:forEach items="${listTglSmt}" var="tglSmt">
												<option value="${tglSmt.idTglSmt}"
													<c:if test="${tglSmt.aTglSmtAktif == true}">selected="true"</c:if>>${tglSmt.thnAjaran.thnThnAjaran}
													${tglSmt.smt.nmSmt}</option>
											</c:forEach>
										</select>
									</div>
								</div>
								<div class="col-md-8 masteractions panel-body">
									<div class="btn-action pull-right"></div>
								</div>
							</div>
							<form class="tableform">
								<div class="table-responsive">
									<table
										class="table table-striped table-bordered table-hover table-checkable table-colvis datatable">
										<thead>
											<tr>
												<td><input class="checkbox-all" type="checkbox">
												</td>
												<td>Kode</td>
												<td>Matakuliah</td>
												<td>Nama</td>
												<td>Tahun ajaran</td>
												<td>Semester</td>
												<td>Kuota peserta didik</td>
												<td>Pertemuan dalam seminggu</td>
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

			<div class="row" id="master-detail" style="display: none;">
				<div class="col-md-6 col-md-offset-3">
					<div class="panel panel-white">
						<div class="panel-heading clearfix">
							<h4 class="panel-title">Detil Pembelajaran</h4>
						</div>
						<div class="panel-body">
							<form:form role="form" commandName="pemb" class="formdetail">
								<div class="form-group">
									<label>Tahun Ajaran</label> <input type="text" id="nmThnAjaran"
										class="form-control" disabled />
								</div>
								<div class="form-group">
									<label>Matakuliah</label> <input type="text"
										onclick="showModal()" name="namaMK" id="namaMK"
										class="form-control" placeholder="Berisi Matakuliah" /> <input
										type="hidden" name="idMK" id="idMK" />
								</div>
								<div class="form-group">
									<label>Nama Pembelajaran</label>
									<form:input path="nmPemb" class="form-control"
										placeholder="Berisi nama pembelajaran" />
									<form:hidden path="idPemb" class="form-control" />
								</div>
								<div class="form-group">
									<label>Kuota peserta didik</label>
									<form:input path="kuota" class="form-control"
										placeholder="Berisi jumlah maksimum peserta didik" />
								</div>
								<div class="form-group">
									<label>Pertemuan dalam seminggu</label>
									<form:input path="temuDalamSeminggu" class="form-control"
										placeholder="Berisi jumlah pertemuan dalam seminggu" />
								</div>
								<div class="form-group">
									<label>Kelas Untuk</label>
									<div id="listSatMan">
									<c:forEach items="${listAnak}" var="satMan" varStatus="status">
										<div class="checkbox">
											<label for="satman-${status.index}">
												<input type="checkbox" name="satMan[]" class="checkboxSatMan"
													id="satman-${status.index}" value="${satMan.idSatMan}" /> ${satMan.nmSatMan}
											</label>
										</div>
									</c:forEach>
									</div>
								</div>
								<div class="form-group detailcontrol"></div>
							</form:form>
						</div>
					</div>
				</div>
			</div>

			<div class="row" id="master-pengajar" style="display: none;">
				<div class="col-md-12">
					<div class="row">
						<div id="masterPendidikPengajar" class="col-md-12"
							style="margin-bottom: 10px;">
							<div class="panel panel-white">
								<div class="panel-heading clearfix">
									<h4 id="title" class="panel-title">Pengajar</h4></br>
								</div>
								<div class="panel-heading clearfix">
									<h4>Penanggung Jawab : <span id="ketuaPengajar"></span></h4>
								</div>
								<div class="panel-body">
									<div class="row">
										<div class="col-md-12 masteractions panel-body">
											<div class="btn-action pull-right">
												<button type="button" class="btn btn-default pendidik-close"
													style="display: none">Tutup</button>
												<button type="button" class="btn btn-success pendidik-add">Tambah Pengajar</button>
											</div>
										</div>
									</div>
									<form class="tableform">
										<div class="table-responsive">
											<table
												class="table table-striped table-bordered table-hover table-checkable table-colvis datatable">
												<thead>
													<tr>
														<td><input class="checkbox-all" type="checkbox">
														</td>
														<td>NIP</td>
														<td>Nama</td>
														<td>Ketua</td>
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
						<div id="masterPendidik" class="col-md-6"
							style="margin-bottom: 10px; display: none">
							<div class="panel panel-white">
								<div class="panel-heading clearfix">
									<h4 class="panel-title">Pendidik</h4>
								</div>

								<div class="panel-body">
									<div class="row">
										<div class="col-md-8">
											<div class="form-group">
												<label>Satuan Manajemen</label> <select id="satman-ptk-filter"
													class="form-control">
													<c:forEach items="${listSatManPtk}" var="satMan">
														<option value="${satMan.idSatMan}"
															<c:if test="${satMan.idSatMan == idSatMan }"> selected </c:if>
														>
														${satMan.nmSatMan}
														</option>
													</c:forEach>
												</select>
											</div>
										</div>
										<div class="col-md-4 masteractions panel-body">
											<div class="btn-action pull-right">
												<button type="button" class="btn btn-primary tambahkan-all">Tambahkan</button>
											</div>
										</div>
									</div>
									<form class="tableform">
										<input type="hidden" id="idPemb-pendidik" name="idPemb" />
										<div class="table-responsive">
											<table
												class="table table-striped table-bordered table-hover table-checkable table-colvis datatable">
												<thead>
													<tr>
														<td><input class="checkbox-all" type="checkbox">
															</div></td>
														<td>NIP</td>
														<td>Nama</td>
														<td>Status</td>
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
						<hr />
						<div class="col-md-12">
							<div class="panel panel-white">
								<div class="panel-body">
									<div class="form-group detailcontrol">
										<button type="button" onClick="kembali()"
											class="btn btn-primary cancel">Kembali</button>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>

			<div class="row" id="master-pesertadidik" style="display: none;">
				<div class="col-md-12">
					<div class="row">
						<div id="masterPesertaPembelajaran" class="col-md-12"
							style="margin-bottom: 10px;">

							<div class="panel panel-white">
								<div class="panel-heading clearfix">
									<h4 class="panel-title" id="title-peserta">Peserta
										Pembelajaran</h4>
								</div>
								<div class="panel-body">
									<div class="row">
										<div class="col-md-12 masteractions panel-body">
											<div class="btn-action pull-right">
												<button type="button" class="btn btn-default peserta-close"
													style="display: none">Tutup</button>
												<button type="button" class="btn btn-success rombel-add">Tambah
													dari rombel</button>
												<button type="button" class="btn btn-success pd-add">Tambah
													Peserta</button>
												<button type="button" class="btn btn-danger peserta-delete">Hapus</button>
											</div>
										</div>
									</div>
									<form class="tableform">
										<input type="hidden" id="idPemb-peserta" name="idPemb" />

										<div class="table-responsive">
											<table
												class="table table-striped table-bordered table-hover table-checkable table-colvis datatable">
												<thead>
													<tr>
														<td><input class="checkbox-all" type="checkbox">
														</td>
														<td>NIM</td>
														<td>Nama</td>
														<td>Angkatan</td>
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
							<div id="masterRombel" class="col-md-6"
								style="margin-bottom: 10px; display: none">

								<div class="panel panel-white">
									<div class="panel-heading clearfix">
										<h4 class="panel-title">Rombel</h4>
									</div>
									<div class="panel-body">
										<div class="row">
											<div class="col-md-12 masteractions panel-body">
												<div class="btn-action pull-right">
													<button type="button"
														class="btn btn-primary tambahkan-rombel-all">Tambahkan</button>
												</div>
											</div>
										</div>
										<form class="tableform">
											<input type="hidden" id="idPemb-rombel" name="idPemb" />

											<div class="table-responsive">
												<table
													class="table table-striped table-bordered table-hover table-checkable table-colvis datatable">
													<thead>
														<tr>
															<td><input class="checkbox-all" type="checkbox">
															</td>
															<td>Nama Rombel</td>
															<td>Tanggal terbuat</td>
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
								<div id="masterPd" class="col-md-6"
									style="margin-bottom: 10px; display: none">
									<div class="panel panel-white">
										<div class="panel-heading clearfix">
											<h4 class="panel-title">Peserta didik</h4>
										</div>
										<div class="panel-body">
											<div class="row">
												<div class="col-md-8">
													<div class="form-group">
														<label>Prodi</label> <select id="satman-filter"
															class="form-control">
															<c:forEach items="${listAnak}" var="satMan">
																<option value="${satMan.idSatMan}">${satMan.nmSatMan}</option>
															</c:forEach>
														</select>
													</div>
													<div class="form-group">
														<label>Angkatan</label> <select id="pd-filter"
															class="form-control">
															<option value="">Semua</option>
															<c:forEach items="${listAngkatan}" var="angkatan">
																<option value="${angkatan}">${angkatan}</option>
															</c:forEach>
														</select>
													</div>
												</div>
												<div class="col-md-4 masteractions">
													<div class="btn-action pull-right">
														<button type="button"
															class="btn btn-primary tambahkan-pd-all">Tambahkan</button>
													</div>
												</div>
											</div>
											<form class="tableform">
												<input type="hidden" id="idPemb-pd" name="idPemb" />
												<div class="table-responsive">
													<table
														class="table table-striped table-bordered table-hover table-checkable table-colvis datatable">
														<thead>
															<tr>
																<td><input class="checkbox-all" type="checkbox">
																</td>
																<td>NIM</td>
																<td>Nama</td>
																<td>Angkatan</td>
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
								<hr />
								<div class="col-md-12">
									<div class="panel panel-white">
										<div class="panel-body">
											<div class="form-group detailcontrol">
												<button type="button" onClick="kembali()"
													class="btn btn-primary cancel">Kembali</button>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>

						<div id="myModal" class="modal fade">
							<div class="modal-dialog modal-lg">
								<div class="modal-content">
									<div class="modal-header">
										<button type="button" class="close" data-dismiss="modal"
											aria-label="Close">
											<span aria-hidden="true">&times;</span>
										</button>
										<h4 class="modal-title">Matakuliah</h4>
									</div>
									<div class="modal-body">
										<div id="masterpageMK">
											<div class="form-group">
												<label>Tahun Ajaran</label> <select class="form-control"
													id="filter-mk" name="filter-mk">
													<c:forEach items="${listKurikulum}" var="kurikulum">
														<option value="${kurikulum.idKurikulum}">${kurikulum.namaKurikulum} ${kurikulum.thnMulai} - ${kurikulum.thnAkhir}</option>
													</c:forEach>
												</select>
											</div>
											<form class="tableform">
												<div class="table-responsive">
												<table
													class="table table-striped table-bordered table-hover table-checkable table-colvis datatable"
													style="width: 100%">
													<thead>
														<tr>
															<td>#</td>
															<td>Kode</td>
															<td>Kurikulum</td>
															<td>Satuan Manajemen</td>
															<td>Rumpun Matakuliah</td>
															<td>Nama Matakuliah</td>
															<td>Semester</td>
															<td>Deskripsi</td>
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
									<div class="modal-footer"></div>
								</div>
								<!-- /.modal-content -->
							</div>
							<!-- /.modal-dialog -->
						</div>
						<!-- /.modal -->
						<!-- Script Custom pada halaman. Kamu bisa memisah script pada file terpisah dengan menaruhnya di resource/js/namamodul/namafile.js -->


						<!-- akhir script custom pada halaman -->
						<!-- akhir dari content content -->
		<content tag="scripts">			
			<script type="text/javascript" src="${pageContext.servletContext.contextPath}/resources/plugins/jquery.datatables/media/js/jquery.dataTables.min.js"></script>
			<script type="text/javascript" src="${pageContext.servletContext.contextPath}/resources/plugins/jquery.datatables/extensions/ColVis/js/dataTables.colVis.min.js"></script>
			<script src="${pageContext.servletContext.contextPath}/resources/plugins/bootstrap-datepicker/js/bootstrap-datepicker.js" type="text/javascript"></script>
			<script src="${pageContext.servletContext.contextPath}/resources/plugins/gritter/js/jquery.gritter.js" rel="stylesheet" type="text/javascript"></script>
			<script src="${pageContext.servletContext.contextPath}/resources/plugins/jquery-validation/jquery.validate.min.js" rel="stylesheet" type="text/javascript"></script>
			<script	src="${pageContext.servletContext.contextPath}/resources/js/jquery.masterpage.sia.js" type="text/javascript"></script>
			<script type="text/javascript">
				var idSatMan = "${idSatMan}";
			</script>
			<script
				src="${pageContext.servletContext.contextPath}/resources/js/pembelajaran/pembelajaran.js"
				type="text/javascript"></script>
			<script>
				var namaThnAjaran = "<c:forEach items="${listTglSmt}" var="tglSmt"><c:if test="${tglSmt.aTglSmtAktif == true}">${tglSmt.thnAjaran.thnThnAjaran} ${tglSmt.smt.nmSmt}</c:if></c:forEach>";
			</script>
			<jsp:include page="Footer.jsp" />
		</content>
	</body>
</html>
