<%@page import="org.joda.time.LocalDate"%>
<%@page import="com.sia.modul.domain.TglSmt"%>
<%@page import="com.sia.modul.domain.AturanPengganti"%>
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
		<%
				AturanPengganti aturanPengganti = (AturanPengganti) request.getAttribute("aturanPengganti");
				TglSmt smtAktif = (TglSmt) request.getAttribute("smtAktif");
			%>
		<div class="row">
					<div class="col-md-8 col-md-offset-2">
					<div class="panel panel-white">
							<div class="panel-heading">
								<h4 class="panel-title text-center" style="float:none">Penyusunan Kartu Rencana Studi</h4>	
							</div>
							<div class="panel-body">
							<h5 class="text-center">
								Pengisian:
								<span id="awalPenyusunan">
									<% 
										if(aturanPengganti!=null && aturanPengganti.getTglAwalSusunKrs()!=null) 
										{
											out.print(aturanPengganti.getTglAwalSusunKrs().toString("dd-MM-yyyy"));
										}
										else if(smtAktif!=null && smtAktif.getTglAwalSusunKrs()!=null)
										{
											out.print(smtAktif.getTglAwalSusunKrs().toString("dd-MM-yyyy"));
										}
									%>
								</span>
								s/d
								<span id="akhirPenyusunan">
									<% 
										if(aturanPengganti!=null && aturanPengganti.getTglAkhirSusunKrs()!=null) 
										{
											out.print(aturanPengganti.getTglAkhirSusunKrs().toString("dd-MM-yyyy"));
										}
										else if(smtAktif!=null && smtAktif.getTglAkhirSusunKrs()!=null)
										{
											out.print(smtAktif.getTglAkhirSusunKrs().toString("dd-MM-yyyy"));
										}
									%>
								</span>
								| Perubahan: 
								<span id="awalPerubahan">
									<% 
										if(aturanPengganti!=null && aturanPengganti.getTglAkhirSusunKrs()!=null) 
										{
											out.print(aturanPengganti.getTglAkhirSusunKrs().plusDays(1).toString("dd-MM-yyyy"));
										}
										else if(smtAktif!=null && smtAktif.getTglAkhirSusunKrs()!=null)
										{
											out.print(smtAktif.getTglAkhirSusunKrs().plusDays(1).toString("dd-MM-yyyy"));
										}
									%>
								</span>
								s/d
								<span id="akhirPerubahan">
									<%
										if(aturanPengganti!=null && aturanPengganti.getTglAkhirUbahKrs()!=null) 
																								{
																									out.print(aturanPengganti.getTglAkhirUbahKrs().toString("dd-MM-yyyy"));
																								}
																								else if(smtAktif!=null && smtAktif.getTglAkhirUbahKrs()!=null)
																								{
																									out.print(smtAktif.getTglAkhirUbahKrs().toString("dd-MM-yyyy"));
																								}
									%>
								</span>
								| Pembatalan:
								<span id="awalPembatalan">
									<%
										if(aturanPengganti!=null && aturanPengganti.getTglAkhirUbahKrs()!=null) 
																								{
																									out.print(aturanPengganti.getTglAkhirUbahKrs().plusDays(1).toString("dd-MM-yyyy"));
																								}
																								else if(smtAktif!=null && smtAktif.getTglAkhirUbahKrs()!=null)
																								{
																									out.print(smtAktif.getTglAkhirUbahKrs().plusDays(1).toString("dd-MM-yyyy"));
																								}
									%>
								</span>
								s/d
								<span id="akhirPembatalan">
									<% 
										if(aturanPengganti!=null && aturanPengganti.getTglAkhirBatalMk()!=null) 
										{
											out.print(aturanPengganti.getTglAkhirBatalMk().toString("dd-MM-yyyy"));
										}
										else if(smtAktif!=null && smtAktif.getTglAkhirBatalMk()!=null)
										{
											out.print(smtAktif.getTglAkhirBatalMk().toString("dd-MM-yyyy"));
										}
									%>
								</span>
							</h5>
						<table class="table borderless">
							<tr>
								<td><label>NIM</label></td>
								<td>${pd.nimPd}</td>
								<td><label>Periode</label></td>
								<td>
									<select id="smt" class="col-md-6">
										<c:forEach items="${semuaSmt}" var="smt">
											<option value="${smt.idSmt}" <c:if test="${smtAktif !=null and smt.idSmt == smtAktif.smt.idSmt }">selected</c:if> > ${smt.nmSmt}</option>
										</c:forEach>
									</select>
									<select id="thnAjaran" class="col-md-4">
										<c:forEach items="${semuaThnAjaran}" var="thnAjaran">
											<option value="${thnAjaran.idThnAjaran}" <c:if test="${smtAktif !=null and thnAjaran.idThnAjaran == smtAktif.thnAjaran.idThnAjaran }">selected</c:if> >${thnAjaran.thnThnAjaran}</option>
										</c:forEach>
									</select>
									<button class="lbl btn-primary" onClick="reportKrs()">
										Lihat
									</button>					
								</td>
							</tr>
							<tr>
								<td><label>Nama</label></td>
								<td>${pd.nmPd}</td>
								<td><label>Pendamping Akademik</label></td>
								<td>${pd.ptk.nmPtk}</td>
							</tr>
							<tr>
								<td><label>IPK / IPS</label></td>
								<td>
									<span id="ipk">
										${ipk!=null?ipk.nilaiIpk:0}
									</span>
									/
									<span id="ips">
										${ipsTerakhir!=null?ipsTerakhir.nilaiIps:0}
									</span>
								</td>
								<td><label>Batas / Sisa</label></td>
								<td>
									<span id="batas">
										${batasAmbil!=null?batasAmbil.batasPengambilanSks:"0"}
									</span>
										/
									<span id="sisa">
										${(batasAmbil!=null?batasAmbil.batasPengambilanSks:0)-sksTerambil}
									</span>
								</td>
							</tr>
						</table>
						<center>
							<div>
								<a href="${pageContext.servletContext.contextPath}/karturencanastudi/mkharusdiulang/${pd.idPd}" target="_blank" class="btn btn-primary">MK harus diulang</a>
								<a href="${pageContext.servletContext.contextPath}/karturencanastudi/mkpergantianekivalensi/${pd.idPd}" target="_blank" class="btn btn-primary">MK wajib pergantian kurikulum</a>
								<a href="${pageContext.servletContext.contextPath}/karturencanastudi/mkmelanggarprasyarat/${pd.idPd}" target="_blank" class="btn btn-primary">MK melanggar prasyarat</a>
							</div>
						</center>
					</div>
				</div>
			</div>
			</div>

			<div class="row" id="masterpage">
					<div class="col-md-8 col-md-offset-2">
					<div class="panel panel-white">
							<div class="panel-body">
					<div id="pembayaran">
						<c:if test="${statusPembayaran ==false }">
							<div class="alert alert-danger text-center" role="alert">
	                       		Peserta didik belum melakukan pembayaran pada periode ini
	                    	</div>
                    	</c:if>
                    </div>
                    <div class="row">
                    	<div class="col-md-12 panel-body">
                		<button class="btn btn-primary pull-right" onclick="cetak()">Cetak KRS</button>
                		</div>
                	</div>
					<div class="table-responsive">
							<table id="tableKrs"
								class="table table-striped table-bordered table-hover">
								<thead>
									<tr>
										<td>Kode</td>
										<td>Matakuliah</td>
										<td>Pembelajaran</td>
										<td>SKS</td>
										<td><span id="aksi">
											<c:choose>
												<c:when test="${dapatDisusun == true}">
													Aksi
												</c:when>
												<c:otherwise>
													Nilai
												</c:otherwise>
											</c:choose></td>
										</span></td>
									</tr>
								</thead>
								<tfoot>
									<tr>
										<td colspan="3" class="text-right">Total SKS</td>
										<td colspan="2">
											<span id="terambil">
												${sksTerambil}
											</span>
										</td>
									</tr>
								</tfoot>
								<tbody>
									<c:forEach items="${listKrs}" var="krs">
										<tr
											<c:choose>
												<c:when test="${tingkat>krs.pemb.mk.tingkatPemb}">
													style="background:#f6f2dd"
													data-toggle="tooltip" 
													data-placement="top" 
													title="Mengambil matakuliah semester bawah"
												</c:when>
												<c:when test="${tingkat<krs.pemb.mk.tingkatPemb}">
													style="background:#f1d9d9"
													data-toggle="tooltip" 
													data-placement="top" 
													title="Mengambil matakuliah semester atas"
												</c:when>
											</c:choose>
										>
											<td>${krs.pemb.mk.kodeMK}</td>
											<td>${krs.pemb.mk.namaMK}</td>
											<td>${krs.pemb.nmPemb}</td>
											<td>${krs.pemb.mk.jumlahSKS}</td>
											<td><c:choose>
													<c:when test="${krs.aKrsBatal==true}">
														<label class="label">Terbatalkan</label>
													</c:when>
													<c:when test="${krs.aKrsDisetujui==true && dapatDisusun == false}">
														${krs.konversiNilai!=null?krs.konversiNilai.huruf:'-'}
													</c:when>
													<c:when test="${dapatDisusun==false}">
														-
													</c:when>
													<c:otherwise>
														<button type="button" class="btn btn-danger"
															onClick="drop('${krs.idKrs}')">Hapus</button>
													</c:otherwise>
												</c:choose></td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
						<div id="setujui">
							<c:choose>
								<c:when test="${disetujui==true}">
									<div class="alert alert-success text-center" role="alert">
		                       			KRS telah di setujui
		                    		</div>
								</c:when>
								<c:otherwise>
									<div class="alert alert-danger text-center" role="alert">
		                       			KRS belum di setujui
		                    		</div>
								</c:otherwise>
							</c:choose>
                    	</div>
						<c:if test="${dapatDisusun == true }">
							<form class="form-horizontal" id="ambilMatakuliah">
								<c:forEach items="${listListPemb}" var="listPemb"
									varStatus="status">
									<div class="form-group">
										<label class="col-sm-2 control-label">Pembelajaran
											${listSatman[status.index].nmSatMan}</label>
										<div class="col-sm-6">
											<select id="select-${status.index}" class="form-control">
												<c:forEach items="${listPemb}" var="pemb" varStatus="statusPemb">
													<option value="${pemb.idPemb}">${pemb.mk.kodeMK} | SMT 
													<c:if test="${listListSatManMK[status.index][statusPemb.index]!=null }">
														${listListSatManMK[status.index][statusPemb.index].tingkatPemb}
													</c:if>
													 | ${pemb.mk.namaMK} |
														${pemb.nmPemb} | ${pemb.mk.jumlahSKS} SKS | ${pemb.kuota}</option>
												</c:forEach>
											</select>
										</div>
										<div class="col-sm-4">
											<button type="button" id="button-${status.index}"
												onClick="ambil(${status.index})" class="btn btn-primary">Ambil</button>
											<button type="button" id="lihat-${status.index}"
												onClick="lihat(${status.index})" class="btn btn-primary">Peserta</button>
											<button type="button" id="lihat-${status.index}"
												onClick="lihatPendidik(${status.index})" class="btn btn-primary">Pendidik</button>
										</div>
									</div>
								</c:forEach>
							</form>
						</c:if>
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
			<script
				src="${pageContext.servletContext.contextPath}/resources/js/sia.method.js"
				type="text/javascript">
			</script>
			<script>
				var sksTerambil = ${sksTerambil};
				var idTglSmt = '${smtAktif!=null?smtAktif.idTglSmt:""}';
				var batasPengambilan = ${batasAmbil!=null?batasAmbil.batasPengambilanSks:0};
			</script>
			<script
				src="${pageContext.servletContext.contextPath}/resources/js/pembelajaran/susun.krs.js"
				type="text/javascript"></script>
			<script type="text/javascript">
				var tingkat = ${tingkat};
				var idPd = "${pd.idPd}";
			</script>
			<script type="text/javascript">
				reportKrs();
			</script>
			<jsp:include page="Footer.jsp" />
		</content>
	</body>
</html>
