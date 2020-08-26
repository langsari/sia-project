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
				<div class="col-md-12" style="margin-bottom:10px;">
					<div class="panel panel-white">
							<div class="panel-heading clearfix">
								<h4 class="panel-title">Periode</h4>
							</div>
							<div class="panel-body">
					
					<div class="row">
						<div class="col-md-4">
							<div class="form-group">
								<label>Status Aktif</label>
								<select id="filter" name="filter">
									<option value="">Semua</option>
									<option value="true">Aktif</option>
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
										<input class="checkbox-all" type="checkbox" id="flat-checkbox-1"/>
									</td>
									<td>Tahun ajaran</td>
									<td>Semester</td>
									<td>Tanggal akhir pembayaran</td>
									<td>Tanggal awal penyusunan KRS</td>
									<td>Tanggal akhir penyusunan KRS</td>
									<td>Tanggal akhir persetujuan KRS</td>
									<td>Tanggal akhir pembatalan MK</td>
									<td>Tanggal akhir penilaian</td>
									<td>Status Aktif</td>
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
								<h4 class="panel-title">Detil Periode</h4>
							</div>
							<div class="panel-body">
					
					<h4 id="title">Tanggal Penting dalam Semester</h4>
					<form:form role="form" commandName="tglSmt" class="formdetail">
						<div class="form-group">
							<label>Tahun Ajaran</label>
                            <select class="form-control" id="thnAjaran" name="idThnAjaran">
                            	<c:forEach items="${listThnAjaran}" var="thnAjaran">
                            		<option value="${thnAjaran.idThnAjaran}">${thnAjaran.thnThnAjaran}</option>
                            	</c:forEach>
                            </select>
							<form:hidden path="idTglSmt" class="form-control" />
						</div>
						<div class="form-group">
							<label>Semester</label>
                            <select class="form-control" id="smt" name="idSmt">
                            	<c:forEach items="${listSmt}" var="smt">
                            		<option value="${smt.idSmt}">${smt.nmSmt}</option>
                            	</c:forEach>
                            </select>
						</div>
						<div class="form-group">
							<label>Tanggal Akhir Pembayaran</label>
							<form:input path="tglAkhirBayar" class="form-control date-picker" placeholder="Berisi tanggal akhir pembayaran semester" />
						</div>
						<hr/>
						<div class="checkbox">
							<label>
								<input type="checkbox" id="penyusunan-krs" />
								Mahasiswa dapat menyusun KRS
							</label>
						</div>
						<div id="penyusunan-field">
							<div class="form-group">
								<label>Tanggal Awal Penyusunan KRS</label>
								<form:input path="tglAwalSusunKrs" class="form-control date-picker" placeholder="Berisi tanggal awal penyusunan KRS" />
							</div>
							<div class="form-group">
								<label>Tanggal Akhir Penyusunan KRS</label>
								<form:input path="tglAkhirSusunKrs" class="form-control date-picker" placeholder="Berisi tanggal akhir penyusunan KRS" />
							</div>
							<div class="form-group">
								<label>Tanggal Akhir Perubahan KRS</label>
								<form:input path="tglAkhirUbahKrs" class="form-control date-picker" placeholder="Berisi tanggal akhir perubahan KRS" />
							</div>
						</div>
						<hr/>
						<div class="checkbox">
							<label>
								<input type="checkbox" id="pembatalan-mk" />
								Dosen wali dapat membatalkan MK
							</label>
						</div>
						<div id="pembatalan-field">
							<div class="form-group">
								<label>Tanggal Akhir Pembatalan Matakuliah</label>
								<form:input path="tglAkhirBatalMk" class="form-control date-picker" placeholder="Berisi tanggal akhir pembatalan KRS" />
							</div>
						</div>
						<hr/>
						<div class="form-group">
							<label>Tanggal Akhir Penilaian</label>
							<form:input path="tglAkhirPenilaian" class="form-control date-picker" placeholder="Berisi tanggal akhir penilaian" />
						</div>
						<div class="checkbox">
							<label>
								<form:checkbox path="aTglSmtAktif" class="aTglSmtAktif" />
								Tanggal penting sedang aktif
							</label>
						</div>
						<div class="form-group detailcontrol">
						</div>
			        </form:form>
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
			<script src="${pageContext.servletContext.contextPath}/resources/js/date.js" type="text/javascript" ></script>
			<script src="${pageContext.servletContext.contextPath}/resources/plugins/bootstrap-datepicker/js/bootstrap-datepicker.js" type="text/javascript"></script>
			<script>
			$(document).ready(function(){
				$('.date-picker').datepicker({
					format: "dd-mm-yyyy"
				});
				
				$('#penyusunan-krs').change(function() {
			        if ($(this).prop("checked") == true) {
			        	$('#penyusunan-field .date-picker').datepicker("update",Date.today());
			        	$('#penyusunan-field .date-picker').datepicker("update","");
			        	$('#penyusunan-field').show();
			        } else {
			        	$('#penyusunan-field').hide();
			        	$('#penyusunan-field .date-picker').datepicker("update",Date.today());
			        	$('#penyusunan-field .date-picker').datepicker("update","");
			        }
			    });
				
				$('#pembatalan-mk').change(function() {
			        if ($(this).prop("checked") == true) {
			        	$('#pembatalan-field .date-picker').datepicker("update",Date.today());
			        	$('#pembatalan-field .date-picker').datepicker("update","");
			        	$('#pembatalan-field').show();
			        } else {
			        	$('#pembatalan-field').hide();
			        	$('#pembatalan-field .date-picker').datepicker("update",Date.today());
			        	$('#pembatalan-field .date-picker').datepicker("update","");
			        }
			    });
				
				$('#masterpage').masterPage(
				{
					detailFocusId: '#idTglSmt',
					dataUrl: context_path+'modul/pembelajaran/tglsemester/json',
					detailUrl: context_path+'modul/pembelajaran/tglsemester/edit',
					addUrl: context_path+'modul/pembelajaran/tglsemester/simpan',
					editUrl: context_path+'modul/pembelajaran/tglsemester/simpan',
					deleteUrl: context_path+'modul/pembelajaran/tglsemester/deletemany',
					primaryKey: 'idTglSmt',
			        order: [[1,"desc"]],
					editOnClick: false,
					editOnClickRow: true,
					cols: [
						/* idTglSmt */
						{ 
							"bVisible":    true,
							bSortable: false,
							bSearchable: false,
							mRender: function(data,type,full){
								return '<input class="checkbox-data" type="checkbox" name="idTglSmt[]" value="'+data+'"/>';
							}
						},
						/* idThnAjaran */
						{ "bVisible":    true },
						/* idSmt */
						{ "bVisible":    true },
						/* tglAkhirBayar */
						{ "bVisible":    true },
						/* tglAwalSusunKrs */
						{ "bVisible":    false },
						/* tglAkhirSusunKrs */
						{ "bVisible":    false },
						/* tglAkhirUbahKrs */
						{ "bVisible":    false },
						/* tglAkhirBatalMk */
						{ "bVisible":    false },
						/* tglAkhirPenilaian */
						{ "bVisible":    true },
						/* aTglSmtAktif */
						{ 
							"bVisible":    true,
							bSortable: false,
							bSearchable: false,
							mRender: function(data,type,full){
								if(data=="true") return "Ya";
								else return "Tidak";
							}
						},
						/* aTglSmtTerhapus */
						{ "bVisible":    false },
						/* Aksi */
						{ 
							"bVisible":    true,
							bSortable: false,
							bSearchable: false,
							mRender: function(data,type,full){
								var action = '<button type="button" class="btn btn-primary editrow">Edit</button>';
								if(full[10]=='false') action += ' <button type="button" class="btn btn-danger deleterow">Hapus</button>'
								return action;
							}
						}
					],
					validationRules: {idSmt:{required: false},nmSmt:{required: true},jmlPertemuan:{required: true,digits:true},smtTerhitung:{required: false}},
					filters: [{id:'#filter', name:'a_tgl_smt_terhapus'}],
					callOnFillForm: function(data, options){
						$('.formdetail').find("input[type=checkbox]:not(.switchery), input[type=radio]:not(.no-uniform)").each(function(){
							$(this).uniform();
						});
						$('#thnAjaran').val(data.data.thnAjaran.idThnAjaran);
						$('#smt').val(data.data.smt.idSmt);
						$('.date-picker').datepicker('update',Date.today());
						if(data.data.tglAwalSusunKrs!=null) 
						{
							$('#penyusunan-krs').prop("checked",true);
							$('#penyusunan-krs').uniform();
							$('#penyusunan-field').show();	
						}
						else
						{
							$('#penyusunan-krs').prop("checked",false);
							$('#penyusunan-krs').uniform();
							$('#penyusunan-field').hide();	
						}
						if(data.data.tglAkhirBatalMk!=null) 
						{
							$('#pembatalan-mk').prop("checked",true);
							$('#pembatalan-mk').uniform();
							$('#pembatalan-field').show();	
						}
						else
						{
							$('#pembatalan-mk').prop("checked",false);
							$('#pembatalan-mk').uniform();
							$('#pembatalan-field').hide();	
						}
						$('#tglAkhirBayar').datepicker('update',data.data.tglAkhirBayar==null?"":(data.data.tglAkhirBayar.values[2]<10?"0"+data.data.tglAkhirBayar.values[2]:data.data.tglAkhirBayar.values[2])+"-"+(data.data.tglAkhirBayar.values[1]<10?"0"+data.data.tglAkhirBayar.values[1]:data.data.tglAkhirBayar.values[1])+"-"+data.data.tglAkhirBayar.values[0]);
						$('#tglAwalSusunKrs').datepicker('update',data.data.tglAwalSusunKrs==null?"":(data.data.tglAwalSusunKrs.values[2]<10?"0"+data.data.tglAwalSusunKrs.values[2]:data.data.tglAwalSusunKrs.values[2])+"-"+(data.data.tglAwalSusunKrs.values[1]<10?"0"+data.data.tglAwalSusunKrs.values[1]:data.data.tglAwalSusunKrs.values[1])+"-"+data.data.tglAwalSusunKrs.values[0]);
						$('#tglAkhirSusunKrs').datepicker('update',data.data.tglAkhirSusunKrs==null?"":(data.data.tglAkhirSusunKrs.values[2]<10?"0"+data.data.tglAkhirSusunKrs.values[2]:data.data.tglAkhirSusunKrs.values[2])+"-"+(data.data.tglAkhirSusunKrs.values[1]<10?"0"+data.data.tglAkhirSusunKrs.values[1]:data.data.tglAkhirSusunKrs.values[1])+"-"+data.data.tglAkhirSusunKrs.values[0]);
						$('#tglAkhirUbahKrs').datepicker('update',data.data.tglAkhirUbahKrs==null?"":(data.data.tglAkhirUbahKrs.values[2]<10?"0"+data.data.tglAkhirUbahKrs.values[2]:data.data.tglAkhirUbahKrs.values[2])+"-"+(data.data.tglAkhirUbahKrs.values[1]<10?"0"+data.data.tglAkhirUbahKrs.values[1]:data.data.tglAkhirUbahKrs.values[1])+"-"+data.data.tglAkhirUbahKrs.values[0]);
						$('#tglAkhirBatalMk').datepicker('update',data.data.tglAkhirBatalMk==null?"":(data.data.tglAkhirBatalMk.values[2]<10?"0"+data.data.tglAkhirBatalMk.values[2]:data.data.tglAkhirBatalMk.values[2])+"-"+(data.data.tglAkhirBatalMk.values[1]<10?"0"+data.data.tglAkhirBatalMk.values[1]:data.data.tglAkhirBatalMk.values[1])+"-"+data.data.tglAkhirBatalMk.values[0]);
						$('#tglAkhirPenilaian').datepicker('update',data.data.tglAkhirPenilaian==null?"":(data.data.tglAkhirPenilaian.values[2]<10?"0"+data.data.tglAkhirPenilaian.values[2]:data.data.tglAkhirPenilaian.values[2])+"-"+(data.data.tglAkhirPenilaian.values[1]<10?"0"+data.data.tglAkhirPenilaian.values[1]:data.data.tglAkhirPenilaian.values[1])+"-"+data.data.tglAkhirPenilaian.values[0]);
					},
					callOnAddAfterReset:function(options){
						$('.formdetail').find("input[type=checkbox]:not(.switchery), input[type=radio]:not(.no-uniform)").each(function(){
							$(this).uniform();
						});
						$('.date-picker').datepicker('update',Date.today());
						$('.date-picker').datepicker('update','');
						$('#penyusunan-krs').uniform();
						$('#penyusunan-field').hide();
						$('#pembatalan-mk').uniform();
						$('#pembatalan-field').hide();
					}
				});
			});
		</script>
			<jsp:include page="Footer.jsp" />
		</content>
	</body>
</html>
