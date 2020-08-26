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
							<div class="panel-heading">
								<h4 class="panel-title">Peserta didik</h4>
							</div>
							<div class="panel-body">	
					<div class="row">
						<div class="col-md-4">
							<div class="form-group">
								<label>Angkatan</label>
								<select id="filter" name="filter">
									<option value="">Semua</option>
									<c:forEach items="${listAngkatanPd }" var="angkatanPd">
										<option value="${angkatanPd}">${angkatanPd}</option>
									</c:forEach>
								</select>
							</div>
							<div class="form-group">
								<label>Status KRS</label>
								<select id="filter2" name="filter">
									<option value="false">Semua</option>
									<option value="true">Belum Disetujui</option>
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
										<input class="checkbox-all" type="checkbox" id="flat-checkbox-1">
									</td>
									<td>NIM</td>
									<td>Nama</td>
									<td>Angkatan</td>
									<td>Pendamping Akademik</td>
									<td>Status KRS</td>
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
		
		<div id="listkrs" style="display:none">
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
								</span>
								s/d
								<span id="akhirPenyusunan">
									
								</span>
								| Perubahan: 
								<span id="awalPerubahan">
									
								</span>
								s/d
								<span id="akhirPerubahan">
									
								</span>
								| Pembatalan:
								<span id="awalPembatalan">
									
								</span>
								s/d
								<span id="akhirPembatalan">
									
								</span>
							</h5>
						<table class="table borderless">
							<tr>
								<td><label>NIM</label></td>
								<td style="width:30%">
									<span id="nim">
									</span>
								</td>
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
									<button id="lihat" class="lbl btn-primary" onClick="getKRS()">
										Lihat
									</button>					
								</td>
							</tr>
							<tr>
								<td><label>Nama</label></td>
								<td>
									<span id="nama">
									</span>
								</td>
								<td><label>Pendamping Akademik</label></td>
								<td>${ptk.nmPtk}</td>
							</tr>
							<tr>
								<td><label>IPK / IPS</label></td>
								<td>
									<span id="ipk">
									</span>
									/
									<span id="ips">
									</span>
								</td>
								<td><label>Batas / Sisa</label></td>
								<td>
									<span id="batas">
									</span>
										/
									<span id="sisa">
									</span>
								</td>
							</tr>
						</table>
						<center>
							<div>
								<a id="mkulang" href="${pageContext.servletContext.contextPath}/karturencanastudi/mkharusdiulang/" target="_blank" class="btn btn-primary">MK harus diulang</a>
								<a id="mkekiv" href="${pageContext.servletContext.contextPath}/karturencanastudi/mkpergantianekivalensi/" target="_blank" class="btn btn-primary">MK wajib pergantian kurikulum</a>
								<a id="mksyarat" href="${pageContext.servletContext.contextPath}/karturencanastudi/mkmelanggarprasyarat/" target="_blank" class="btn btn-primary">MK melanggar prasyarat</a>
							</div>
						</center>
					</div>
				</div>
			</div>
		</div>
		
		<div class="row">
				<div class="col-md-12">
				<div class="panel panel-white">
							<div class="panel-heading">
								<h4 class="panel-title" id="title"></h4>
							</div>
							<div class="panel-body">
					<div id="pembayaran">
                    </div>
                    <div class="row">
                    	<div class="col-md-12 panel-body">
                		<button class="btn btn-primary pull-right" onclick="cetak()">Cetak KRS</button>
                		</div>
                	</div>
					<input type="hidden" id="idPd">
						<div class="table-responsive">
						<table id="tableKrs" class="table table-striped table-bordered table-hover">
							<thead>
								<tr>
									<td>Koed MK</td>
									<td>Matakuliah</td>
									<td>Pembelajaran</td>
									<td>SKS</td>
									<td>Status</td>
									<td>
										<span id="aksi">
											Aksi
										</span>
									</td>
								</tr>
							</thead>
							<tbody>
							</tbody>
						</table>
					</div>
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
												<c:if test="${listListSatManMK[status.index][statusPemb.index] !=null }">
													${listListSatManMK[status.index][statusPemb.index].tingkatPemb}
												</c:if>
												 | ${pemb.mk.namaMK} |
													${pemb.nmPemb} | ${pemb.mk.jumlahSKS} | ${pemb.kuota}</option>
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
					<div class="form-group">
						<button type="button" id="cancel" class="btn btn-primary">Kembali</button>
						<%-- <c:if test="${disetujui==true }"> --%>
							<button type="button" id="setujui" class="btn btn-success">Setujui</button>
						<%-- </c:if> --%>
						<button type="button" id="batalkan-persetujuan" class="btn btn-danger">Batalkan Persetujuan</button>
					</div>
				</div>
			</div>
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
			<script type="text/javascript">
			var getKRS;
			var idPd;
			var batalkan,ambil;
			var hapus;
			var dibatalkan = ${dibatalkan};
			var tingkat;
			var batasPengambilan;
			var idTglSmt = "${smtAktif.idTglSmt}";
			var idThnAjaran = "${smtAktif.thnAjaran.idThnAjaran}";
			var idSmt = "${smtAktif.smt.idSmt}";
			var disusun;
			var lihat = function(nomor)
			{
				window.open(context_path+'modul/pembelajaran/pembelajaran/peserta/'+$('#select-'+nomor).val());
			}

			var lihatPendidik = function(nomor)
			{
				window.open(context_path+'modul/pembelajaran/pembelajaran/pendidik/'+$('#select-'+nomor).val());
			}
			
			var lihatPendidik = function(nomor)
			{
				window.open(context_path+'modul/pembelajaran/pembelajaran/pendidik/'+$('#select-'+nomor).val());
			}
			
			var cetak = function()
			{
				window.open(context_path+'modul/pembelajaran/karturencanastudi/cetak/'+idPd+'/'+$('#thnAjaran').val()+'/'+$('#smt').val());
			}
		</script>
		<script>
			$(document).ready(function(){				
				$('#masterpage').masterPage(
				{
					detailFocusId: '#idPd',
					dataUrl: context_path+'modul/pembelajaran/karturencanastudi/pdjson',
					primaryKey: 'idPd',
			        order: [[1,"desc"]],
					editOnClick: false,
					editOnClickRow: false,
					showAddButton:false,
					showDelButton:false,
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
						{ 
							"bVisible":    true,
							bSortable: false,
							bSearchable: false,
							mRender: function(data,type,full){
								if(data=='false')
								return '<label class="label label-warning">Belum Disetujui</label>';
								else if(data=='true') return '<label class="label label-success">Tersetujui</label>';
								else return '<label class="label label-primary">Belum mengambil</label>';
							}
						},
						/* Aksi */
						{ 
							"bVisible":    true,
							bSortable: false,
							bSearchable: false,
							mRender: function(data,type,full){
								var action = '<button type="button" class="btn btn-primary" onclick="getKRS(\''+full[0]+'\',\''+full[2]+'\',\''+full[1]+'\')">Lihat KRS</button>';
								return action;
							}
						}
					],
					validationRules: {idPd:{required: false},nimPd:{required: true},nmPd:{required: true},idPtk:{required: false}},
					filters: [{id:'#filter', name:'angkatanPd'},{id:"#filter2",name:'belumSetuju'}],
					callOnFillForm:function(data,options){
						$('#ptk').val(data.data.ptk==null?"":data.data.ptk.idPtk);
					}
				});
				
				getKRS = function(id,nama,nim)
				{
					$("#lihat").attr("onclick","getKRS(\'"+id+"\',\'"+nama+"\',\'"+nim+"\')");
					idPd = id;
					$("#masterpage").hide();
					$("#listkrs").show();
					$("#title").text("KRS "+nama);
					$("#nama").text(nama);
					$("#nim").text(nim);
					blockUI($("#listkrs"));
					eval("var tempData = {'idPd' : '"+id+"','idThnAjaran':'"+$('#thnAjaran').val()+"','idSmt':'"+$("#smt").val()+"'}");
					$.ajax({
						url: context_path+"modul/pembelajaran/karturencanastudi/ptkgetkrsperiode",
						data : tempData,
						type : 'post',
						success: function(data)
						{
							unblockUI($("#listkrs"));
							if(data.status=='ok')
							{
								show_message("Sukses", data.message);
								var disetujui = false;
								$("#idPd").val(id);
								$("#mkulang").attr('href',context_path+'modul/pembelajaran/karturencanastudi/mkharusdiulang/'+id);
								$("#mksyarat").attr('href',context_path+'modul/pembelajaran/karturencanastudi/mkmelanggarprasyarat/'+id);
								$("#mkekiv").attr('href',context_path+'modul/pembelajaran/karturencanastudi/mkpergantianekivalensi/'+id);
								dibatalkan = data.data.dapatDibatalkan;
								if(data.data.dapatDiubah == false) 
								{
									$("#ambilMatakuliah").hide();
									$("#aksi").text("Nilai");
								}
								else 
								{
									$("#ambilMatakuliah").show();
									$("#aksi").text("Aksi");
									dibatalkan = false;
								}
								console.log(dibatalkan);
								if(data.data.statusPembayaran == false) 
								{
									$("#pembayaran").html('<div class="alert alert-danger text-center" role="alert">Peserta didik belum melakukan pembayaran pada periode ini</div>');
								}
								else 
								{
									$("#pembayaran").html('');
								}
								
								$("#batas").text(data.data.batasPengambilan==null?0:data.data.batasPengambilan.batasPengambilanSks);
								batasPengambilan = data.data.batasPengambilan==null?0:data.data.batasPengambilan.batasPengambilanSks;
								
								$("#awalPenyusunan").text(data.data.awalPenyusunan);
								$("#akhirPenyusunan").text(data.data.akhirPenyusunan);
								$("#awalPerubahan").text(data.data.awalPerubahan);
								$("#akhirPerubahan").text(data.data.akhirPerubahan);
								$("#awalPembatalan").text(data.data.awalPembatalan);
								$("#akhirPembatalan").text(data.data.akhirPembatalan);

								$("#ips").text(data.data.ips==null?"0":data.data.ips.nilaiIps);
								$("#ipk").text(data.data.ipk==null?"0":data.data.ipk.nilaiIpk);
								
								$('#tableKrs tbody').html('');
								sksTerambil =0
								tingkat = data.data.tingkat;
								$('#tableKrs tbody').html('');
								disetujui=true;
								for(i=0;i<data.data.listKrs.length;i++)
								{
									if(data.data.listKrs[i].aKrsDisetujui == false && data.data.listKrs[i].aKrsBatal == false) disetujui=false;
									$('#tableKrs tbody').append(
											"<tr " 
											+ 
											(data.data.tingkat==null?'':
												(data.data.listSatManMK[i]!=null?
												(data.data.tingkat>data.data.listKrs[i].pemb.mk.tingkatPemb?
												'style="background:#f6f2dd" data-toggle="tooltip" data-placement="top" title="Mengambil matakuliah semester bawah"':
												(data.data.tingkat<data.data.listKrs[i].pemb.mk.tingkatPemb?
														'style="background:#f1d9d9"	data-toggle="tooltip" data-placement="top" title="Mengambil matakuliah semester atas"':''
												)
												):''
											))
											+" > <td>"
												+data.data.listKrs[i].pemb.mk.kodeMK
											+"</td> <td>"
												+data.data.listKrs[i].pemb.mk.namaMK
											+"</td> <td>"
												+data.data.listKrs[i].pemb.nmPemb
											+"</td> <td>"
												+data.data.listKrs[i].pemb.mk.jumlahSKS
											+'</td> <td> '
											+
											(data.data.tglSmt.idTglSmt!=idTglSmt?(data.data.listKrs[i].aKrsLulus==true?' <label class="label label-success">Lulus</label> ':'<label class="label label-danger">Tidak Lulus</label> ')+' </td><td> '+(data.data.listKrs[i].konversiNilai!=null?data.data.listKrs[i].konversiNilai.huruf:""):
												(data.data.listKrs[i].aKrsDisetujui==true?
														(data.data.listKrs[i].aKrsBatal==false?'<label class="label label-success">Disetujui</label> </td> <td>'+
															(dibatalkan == true?' <button class="btn btn-danger" onClick="batalkan(\''+data.data.listKrs[i].idKrs+'\')">Batalkan MK</button>':'')
														:'<label class="label label-primary">Terbatalkan</label> </td> <td>'
														)
												:'<label class="label label-warning">Belum Disetujui</label> </td> <td><button type="button" onclick="hapus(\''+data.data.listKrs[i].idKrs+'\')" class="btn btn-danger">Hapus</button> '
												)
											)
											+'</td> </tr>');
									sksTerambil += data.data.listKrs[i].pemb.mk.jumlahSKS;
								}
								if(data.data.tglSmt.idTglSmt!=idTglSmt)
								{
									$('#setujui').hide();
									$('#batalkan-persetujuan').hide();
								}
								else if(disetujui == true)
								{
									$('#setujui').hide();
									if(dibatalkan == false) $('#batalkan-persetujuan').show();
									else $('#batalkan-persetujuan').hide();
								}
								else{
									$('#setujui').show();
									$('#batalkan-persetujuan').hide();
								}
								$("#sisa").text((data.data.batasPengambilan==null?0:data.data.batasPengambilan.batasPengambilanSks)-sksTerambil);
								$("#terambil").text(sksTerambil);
								$('[data-toggle="tooltip"]').tooltip();

								allString = "";
								/*
								for(var i=0;i<data.data.listSatMan.length;i++)
								{
									allString += '<div class="form-group">'
									+'<label class="col-sm-2 control-label">Pembelajaran'
										+data.data.listSatMan[i].nmSatMan
									+'</label>'
									+'<div class="col-sm-6">'
										+'<select id="select-'+i+'" class="form-control">';
											for(var j=0;j<data.data.listListPemb[i].length;j++)
											{
												allString += '<option value="'
												+ data.data.listListPemb[i][j].idPemb
												+'">'
												+ data.data.listListPemb[i][j].mk.kodeMK
												+ " | SMT "
												+ data.data.listListPemb[i][j].mk.tingkatPemb
												+ " | "
												+ data.data.listListPemb[i][j].mk.namaMK
												+ " | "
												+ data.data.listListPemb[i][j].nmPemb
												+ " | "
												+ data.data.listListPemb[i][j].mk.jumlahSKS
												+ " SKS | "
												+ data.data.listListPemb[i][j].kuota
												+'</option>';
											}
										allString +='</select>'
										+'</div>'
										+'<div class="col-sm-4">'
											+' <button type="button" id="button-'+i+'"'
											+'	onClick="ambil('+i+')" class="btn btn-primary">Ambil</button>'
											+' <button type="button" id="lihat-'+i+'"'
											+'	onClick="lihat('+i+')" class="btn btn-primary">Peserta</button>'
											+' <button type="button" id="lihat-'+i+'"'
											+'	onClick="lihatPendidik('+i+')" class="btn btn-primary">Pendidik</button>'
										+'</div>'
									+'</div>';
								}
								$("#ambilMatakuliah").html(allString);
								*/
							}
							else if(data.status=='expired')
							{ document.location=data.message; }
							else
							{ show_message('Error', data.message,true);}
						},
						error: $.ajaxErrorHandler,
						dataType : 'json'
					});
				}

				$("#cancel").click(function(){
					$("#listkrs").hide();
					$("#masterpage").show();
					$("#masterpage").find('.dataTables_length select').change();
					$("#smt").val(idSmt);
					$("#thnAjaran").val(idThnAjaran);
				});

				$("#setujui").click(function(){
					blockUI($("#listkrs"));
					eval("var tempData = {'idPd' : '"+$("#idPd").val()+"'}");
					$.ajax({
						url: context_path+"modul/pembelajaran/karturencanastudi/setujuikrs",
						data : tempData,
						type : 'post',
						success: function(data)
						{
							unblockUI($("#listkrs"));
							if(data.status=='ok')
							{
								show_message("Sukses", data.message);
								var disetujui = false;
								$('#setujui').show();
								$('#batalkan-persetujuan').hide();
								$('#tableKrs tbody').html('');
								sksTerambil =0
								disetujui=true;
								for(i=0;i<data.data.listKrs.length;i++)
								{
									if(data.data.listKrs[i].aKrsDisetujui == false && data.data.listKrs[i].aKrsBatal == false) disetujui=false;
									$('#tableKrs tbody').append(
											"<tr " 
											+ 
											(tingkat==null?'':
												(data.data.listSatManMK[i]!=null?
												(tingkat>data.data.listSatManMK[i].tingkatPemb?
												'style="background:#f6f2dd" data-toggle="tooltip" data-placement="top" title="Mengambil matakuliah semester bawah"':
												(tingkat<data.data.listSatManMK[i].tingkatPemb?
														'style="background:#f1d9d9"	data-toggle="tooltip" data-placement="top" title="Mengambil matakuliah semester atas"':''
												)
												):''
											))
											+" > <td>"
												+data.data.listKrs[i].pemb.mk.kodeMK
											+"</td> <td>"
												+data.data.listKrs[i].pemb.mk.namaMK
											+"</td> <td>"
												+data.data.listKrs[i].pemb.nmPemb
											+"</td> <td>"
												+data.data.listKrs[i].pemb.mk.jumlahSKS
											+'</td> <td> '
												+(data.data.listKrs[i].aKrsDisetujui==true?
													(data.data.listKrs[i].aKrsBatal==false?
														'<label class="label label-success">Disetujui</label> </td> <td>'+(dibatalkan == true?' <button class="btn btn-danger" onClick="batalkan(\''+data.data.listKrs[i].idKrs+'\')">Batalkan MK</button>':'')
														:'<label class="label label-primary">Terbatalkan</label> </td> <td>'
													)
													:'<label class="label label-warning">Belum Disetujui</label> </td> <td><button type="button" onclick="hapus(\''+data.data.listKrs[i].idKrs+'\')" class="btn btn-danger">Hapus</button> '
												)
											+'</td> </tr>');
									sksTerambil += data.data.listKrs[i].pemb.mk.jumlahSKS;
								}
								if(disetujui == true)
								{
									$('#setujui').hide();
									if(dibatalkan == false)$('#batalkan-persetujuan').show();
									else $('#batalkan-persetujuan').hide();
								}
								$("#sisa").text(batasPengambilan-sksTerambil);
								$("#terambil").text(sksTerambil);
								$('[data-toggle="tooltip"]').tooltip();
							}
							else if(data.status=='expired')
							{ document.location=data.message; }
							else
							{ show_message('Error', data.message,true);}
						},
						error: $.ajaxErrorHandler,
						dataType : 'json'
					});
				});
				

				$("#batalkan-persetujuan").click(function(){
					blockUI($("#listkrs"));
					eval("var tempData = {'idPd' : '"+$("#idPd").val()+"'}");
					$.ajax({
						url: context_path+"modul/pembelajaran/karturencanastudi/batalsetuju",
						data : tempData,
						type : 'post',
						success: function(data)
						{
							unblockUI($("#listkrs"));
							if(data.status=='ok')
							{
								show_message("Sukses", data.message);
								var disetujui = false;
								$('#setujui').show();
								$('#batalkan-persetujuan').hide();
								$('#tableKrs tbody').html('');
								sksTerambil =0
								disetujui=true;
								for(i=0;i<data.data.listKrs.length;i++)
								{
									if(data.data.listKrs[i].aKrsDisetujui == false && data.data.listKrs[i].aKrsBatal == false) disetujui=false;
									$('#tableKrs tbody').append(
											"<tr " 
											+ 
											(tingkat==null?'':
												(data.data.listSatManMK[i]!=null?
												(tingkat>data.data.listSatManMK[i].tingkatPemb?
												'style="background:#f6f2dd" data-toggle="tooltip" data-placement="top" title="Mengambil matakuliah semester bawah"':
												(tingkat<data.data.listSatManMK[i].tingkatPemb?
														'style="background:#f1d9d9"	data-toggle="tooltip" data-placement="top" title="Mengambil matakuliah semester atas"':''
												)
												):''
											))
											+" > <td>"
												+data.data.listKrs[i].pemb.mk.kodeMK
											+"</td> <td>"
												+data.data.listKrs[i].pemb.mk.namaMK
											+"</td> <td>"
												+data.data.listKrs[i].pemb.nmPemb
											+"</td> <td>"
												+data.data.listKrs[i].pemb.mk.jumlahSKS
											+'</td> <td> '
												+(data.data.listKrs[i].aKrsDisetujui==true?
													(data.data.listKrs[i].aKrsBatal==false?
														'<label class="label label-success">Disetujui</label> </td> <td>'+(dibatalkan == true?' <button class="btn btn-danger" onClick="batalkan(\''+data.data.listKrs[i].idKrs+'\')">Batalkan MK</button>':'')
														:'<label class="label label-primary">Terbatalkan</label> </td> <td>'
													)
													:'<label class="label label-warning">Belum Disetujui</label> </td> <td>'
												)+'<button type="button" onclick="hapus(\''+data.data.listKrs[i].idKrs+'\')" class="btn btn-danger">Hapus</button> '
											+'</td> </tr>');
									sksTerambil += data.data.listKrs[i].pemb.mk.jumlahSKS;
								}
								if(disetujui == true)
								{
									$('#setujui').hide();
									$('#batalkan-persetujuan').show();
								}
								$("#sisa").text(batasPengambilan-sksTerambil);
								$("#terambil").text(sksTerambil);
								$('[data-toggle="tooltip"]').tooltip();
							}
							else if(data.status=='expired')
							{ document.location=data.message; }
							else
							{ show_message('Error', data.message,true);}
						},
						error: $.ajaxErrorHandler,
						dataType : 'json'
					});
				});

				batalkan = function(id)
				{
					blockUI($("#listkrs"));
					eval("var tempData = {'idKrs' : '"+id+"','idPd':'"+$("#idPd").val()+"'}");
					$.ajax({
						url: context_path+"modul/pembelajaran/karturencanastudi/batalmk",
						data : tempData,
						type : 'post',
						success: function(data)
						{
							unblockUI($("#listkrs"));
							if(data.status=='ok')
							{
								show_message("Sukses", data.message);
								var disetujui = false;
								$('#setujui').show();
								$('#batalkan-persetujuan').hide();
								$('#tableKrs tbody').html('');
								sksTerambil =0
								disetujui=true;
								for(i=0;i<data.data.listKrs.length;i++)
								{
									if(data.data.listKrs[i].aKrsDisetujui == false && data.data.listKrs[i].aKrsBatal == false) disetujui=false;
									$('#tableKrs tbody').append(
											"<tr " 
											+ 
											(tingkat==null?'':
												(data.data.listSatManMK[i]!=null?
												(tingkat>data.data.listSatManMK[i].tingkatPemb?
												'style="background:#f6f2dd" data-toggle="tooltip" data-placement="top" title="Mengambil matakuliah semester bawah"':
												(tingkat<data.data.listSatManMK[i].tingkatPemb?
														'style="background:#f1d9d9"	data-toggle="tooltip" data-placement="top" title="Mengambil matakuliah semester atas"':''
												)
												):''
											))
											+" > <td>"
												+data.data.listKrs[i].pemb.mk.kodeMK
											+"</td> <td>"
												+data.data.listKrs[i].pemb.mk.namaMK
											+"</td> <td>"
												+data.data.listKrs[i].pemb.nmPemb
											+"</td> <td>"
												+data.data.listKrs[i].pemb.mk.jumlahSKS
											+'</td> <td> '
												+(data.data.listKrs[i].aKrsDisetujui==true?(data.data.listKrs[i].aKrsBatal==false?
													'<label class="label label-success">Disetujui</label> </td> <td>'+(dibatalkan == true?' <button class="btn btn-danger" onClick="batalkan(\''+data.data.listKrs[i].idKrs+'\')">Batalkan MK</button>':'')
													:'<label class="label label-primary">Terbatalkan</label> </td> <td>')
													:'<label class="label label-warning">Belum Disetujui</label> </td> <td><button type="button" onclick="hapus(\''+data.data.listKrs[i].idKrs+'\')" class="btn btn-danger">Hapus</button> '
													)
											+'</td> </tr>');
									sksTerambil += data.data.listKrs[i].pemb.mk.jumlahSKS;
								}
								if(disetujui == true)
								{
									$('#setujui').hide();
									if(disusun==true)$('#batalkan-persetujuan').show();
									else $('#batalkan-persetujuan').hide();
								}
								$("#sisa").text(batasPengambilan-sksTerambil);
								$("#terambil").text(sksTerambil);
								$('[data-toggle="tooltip"]').tooltip();
							}
							else if(data.status=='expired')
							{ document.location=data.message; }
							else
							{ show_message('Error', data.message,true);}
						},
						error: $.ajaxErrorHandler,
						dataType : 'json'
					});
				}
				
				hapus = function(id)
				{
					blockUI($("#listkrs"));
					eval("var tempData = {'idKrs' : '"+id+"','idPd':'"+$("#idPd").val()+"'}");
					$.ajax({
						url: context_path+"modul/pembelajaran/karturencanastudi/dosenhapusmk",
						data : tempData,
						type : 'post',
						success: function(data)
						{
							unblockUI($("#listkrs"));
							if(data.status=='ok')
							{
								show_message("Sukses", data.message);
								var disetujui = false;
								$('#setujui').show();
								$('#batalkan-persetujuan').hide();
								$('#tableKrs tbody').html('');
								sksTerambil =0
								disetujui=true;
								for(i=0;i<data.data.listKrs.length;i++)
								{
									if(data.data.listKrs[i].aKrsDisetujui == false && data.data.listKrs[i].aKrsBatal == false) disetujui=false;
									$('#tableKrs tbody').append(
											"<tr " 
											+ 
											(tingkat==null?'':
												(data.data.listSatManMK[i]!=null?
												(tingkat>data.data.listSatManMK[i].tingkatPemb?
												'style="background:#f6f2dd" data-toggle="tooltip" data-placement="top" title="Mengambil matakuliah semester bawah"':
												(tingkat<data.data.listSatManMK[i].tingkatPemb?
														'style="background:#f1d9d9"	data-toggle="tooltip" data-placement="top" title="Mengambil matakuliah semester atas"':''
												)
												):''
											))
											+" > <td>"
												+data.data.listKrs[i].pemb.mk.kodeMK
											+"</td> <td>"
												+data.data.listKrs[i].pemb.mk.namaMK
											+"</td> <td>"
												+data.data.listKrs[i].pemb.nmPemb
											+"</td> <td>"
												+data.data.listKrs[i].pemb.mk.jumlahSKS
											+'</td> <td> '
												+(data.data.listKrs[i].aKrsDisetujui==true?(data.data.listKrs[i].aKrsBatal==false?
													'<label class="label label-success">Disetujui</label> </td> <td>'+(dibatalkan == true?' <button class="btn btn-danger" onClick="batalkan(\''+data.data.listKrs[i].idKrs+'\')">Batalkan MK</button>':'')
													:'<label class="label label-primary">Terbatalkan</label> </td> <td>')
													:'<label class="label label-warning">Belum Disetujui</label> </td> <td><button type="button" onclick="hapus(\''+data.data.listKrs[i].idKrs+'\')" class="btn btn-danger">Hapus</button> '
													)
											+'</td> </tr>');
									sksTerambil += data.data.listKrs[i].pemb.mk.jumlahSKS;
								}
								if(disetujui == true)
								{
									$('#setujui').hide();
									if(disusun==true)$('#batalkan-persetujuan').show();
									else $('#batalkan-persetujuan').hide();
								}
								$("#sisa").text(batasPengambilan-sksTerambil);
								$("#terambil").text(sksTerambil);
								$('[data-toggle="tooltip"]').tooltip();
								
							}
							else if(data.status=='expired')
							{ document.location=data.message; }
							else
							{ show_message('Error', data.message,true);}
						},
						error: $.ajaxErrorHandler,
						dataType : 'json'
					});
				}
				
				ambil = function(nomor)
				{
					blockUI($("#listkrs"));
					eval("var tempData = {'idPemb' : '"+$('#select-'+nomor).val()+"','idPd':'"+$("#idPd").val()+"'}");
					$.ajax({
						url: context_path+"modul/pembelajaran/karturencanastudi/pendidikambilmk",
						data : tempData,
						type : 'post',
						success: function(data)
						{
							unblockUI($("#listkrs"));
							if(data.status=='ok')
							{
								show_message("Sukses", data.message);
								$('#tableKrs tbody').html('');
								sksTerambil =0
								disetujui=true;
								for(i=0;i<data.data.listKrs.length;i++)
								{
									if(data.data.listKrs[i].aKrsDisetujui == false && data.data.listKrs[i].aKrsBatal == false) disetujui=false;
									$('#tableKrs tbody').append(
											"<tr " 
											+ 
											(tingkat==null?'':
												(data.data.listSatManMK[i]!=null?
												(tingkat>data.data.listSatManMK[i].tingkatPemb?
												'style="background:#f6f2dd" data-toggle="tooltip" data-placement="top" title="Mengambil matakuliah semester bawah"':
												(tingkat<data.data.listSatManMK[i].tingkatPemb?
														'style="background:#f1d9d9"	data-toggle="tooltip" data-placement="top" title="Mengambil matakuliah semester atas"':''
												)
												):''
											))
											+" > <td>"
												+data.data.listKrs[i].pemb.mk.kodeMK
											+"</td> <td>"
												+data.data.listKrs[i].pemb.mk.namaMK
											+"</td> <td>"
												+data.data.listKrs[i].pemb.nmPemb
											+"</td> <td>"
												+data.data.listKrs[i].pemb.mk.jumlahSKS
											+'</td> <td> '
												+(data.data.listKrs[i].aKrsDisetujui==true?(data.data.listKrs[i].aKrsBatal==false?
													'<label class="label label-success">Disetujui</label> </td> <td>'+(dibatalkan == true?' <button class="btn btn-danger" onClick="batalkan(\''+data.data.listKrs[i].idKrs+'\')">Batalkan MK</button>':'')
													:'<label class="label label-primary">Terbatalkan</label> </td> <td>')
													:'<label class="label label-warning">Belum Disetujui</label> </td> <td><button type="button" onclick="hapus(\''+data.data.listKrs[i].idKrs+'\')" class="btn btn-danger">Hapus</button> '
													)
											+'</td> </tr>');
									sksTerambil += data.data.listKrs[i].pemb.mk.jumlahSKS;
								}
								if(disetujui == true)
								{
									$('#setujui').hide();
									if(dibatalkan==false)$('#batalkan-persetujuan').show();
									else $('#batalkan-persetujuan').hide();
								}
								$("#sisa").text(batasPengambilan-sksTerambil);
								$("#terambil").text(sksTerambil);
								$('[data-toggle="tooltip"]').tooltip();
							}
							else if(data.status=='expired')
							{ document.location=data.message; }
							else
							{ show_message('Error', data.message,true);}
						},
						error: $.ajaxErrorHandler,
						dataType : 'json'
					});
				}
			});
		</script>
			<jsp:include page="Footer.jsp" />
		</content>
	</body>
</html>