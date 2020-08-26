<%@page import="java.util.ArrayList"%>
<%@page import="com.sia.modul.domain.PertemuanPembelajaran"%>
<%@page import="java.util.List"%>
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
		<link href="${pageContext.servletContext.contextPath}/resources/plugins/datepicker/bootstrap-datepicker-master/css/datepicker.css" rel="stylesheet" type="text/css"/>
	</head>
	<body>
		<div class="row" id="masterpage">
				<div class="col-md-12">
				<div class="panel panel-white">
							<div class="panel-heading">
								<h4 class="panel-title">${pemb.mk.namaMK} ${pemb.nmPemb}</h4>
							</div>
							<div class="panel-body">
					<div class="table-responsive">
						<table class="table table-striped table-bordered table-hover table-checkable table-colvis datatable">
							<thead>
								<tr>
									<td>Pertemuan</td>
									<td>Berita Acara</td>
								</tr>
							</thead>
							<tbody>
							<%
								Integer jumlahPertemuan = (Integer) request.getAttribute("jumlahPertemuan");
								List<PertemuanPembelajaran> listPertemuanPembelajaran= (ArrayList<PertemuanPembelajaran>) request.getAttribute("listPertemuanPembelajaran");
								for(int i=0;i<jumlahPertemuan;i++)
								{
							%>
									<tr>
										<td>Pertemuan <%=i+1 %></td>
										<td><% if(listPertemuanPembelajaran.size() > i) {%>
											<button class="btn btn-primary" onClick="getBeritaAcara('<%=listPertemuanPembelajaran.get(i).getIdPertemuanPembelajaran()%>')" >Lihat Berita Acara</button>
											<%} 
										else if(listPertemuanPembelajaran.size() == i)
										{
											%>
											<button class="btn btn-success" onClick="isiBeritaAcara('<%=i+1 %>')" >Isi Berita Acara</button>
											<%
										}
										else
										{
											%>
											<label class="label label-warning">Pengisian berita acara belum dapat dilakukan</label>
											<%
										}
											%>
										</td>
									</tr>
							<%
								}
							%>
							</tbody>
						</table>
					</div>
				</div>
			</div>
		</div>
		</div>
		
		<div class="row" id="master-detail" style="display:none;">
				<div class="col-md-6 col-md-offset-3" >
				<div class="panel panel-white">
							<div class="panel-heading">
								<h4 class="panel-title" id="title">Berita Acara</h4>
							</div>
							<div class="panel-body">
						<div class="form-group">
							<label>Pembelajaran</label>
							<input type="text" name="namapembelajaran" class="form-control" value="${pemb.mk.namaMK} ${pemb.nmPemb}" disabled />
						</div>
						<div class="form-group">
							<label>Pendidik</label>
							<ul>
								<c:forEach items="${listPendidikPengajar}" var="pendidikPengajar">
									<li>${pendidikPengajar.ptk.nmPtk}</li>
								</c:forEach>
							</ul>
						</div>
						<div class="form-group">
							<label>Pertemuan ke</label>
							<input type="text" id="hitungpertemuan" name="hitungpertemuan" class="form-control" value="" disabled />
							<input id="idPembRepo" type="hidden" value="${pemb.idPemb}" name="idPemb"/>
						</div>
					<form:form role="form" commandName="pertemuanPembelajaran" class="formdetail">
						<div class="form-group">
							<label>Tanggal Pertemuan</label>
							<form:input path="tglPertemuan" class="form-control date-picker" placeholder="Berisi tanggal perkuliahan" />
						</div>
						<div class="form-group">
							<label>Materi</label>
							<form:input path="materi" class="form-control" placeholder="Berisi materi" />
							<form:hidden path="idPertemuanPembelajaran" class="form-control" />
							<input type="hidden" value="${pemb.idPemb}" name="idPemb" id="idPemb"/>
							<form:hidden path="pertemuan" class="form-control"/>
						</div>
						<div class="form-group">
							<label>Kendala Pertemuan</label>
							<form:textarea path="kendalaPerkuliahan" class="form-control ckeditor" placeholder="Berisi kendala perkuliahan" />
						</div>
						<div class="form-group">
							<label>Tanggapan Peserta didik</label>
							<form:textarea path="tanggapanPd" class="form-control ckeditor" placeholder="Berisi tanggapan peserta didik" />
						</div>
						<div class="form-group detailcontrol">
						<button type="button" class="btn btn-success save">Simpan</button>
						<button type="button" class="btn btn-primary cancel">Kembali</button>
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
			
		<script src="${pageContext.servletContext.contextPath}/resources/plugins/ckeditor/ckeditor.js" type="text/javascript" ></script>
		<script src="${pageContext.servletContext.contextPath}/resources/plugins/ckeditor/adapters/jquery.js" type="text/javascript"></script>
		<script src="${pageContext.servletContext.contextPath}/resources/js/sia.method.js" type="text/javascript"></script>
		<script src="${pageContext.servletContext.contextPath}/resources/js/date.js" type="text/javascript" ></script>
		
		<script src="${pageContext.servletContext.contextPath}/resources/plugins/datepicker/bootstrap-datepicker-master/js/bootstrap-datepicker.js" type="text/javascript"></script>
		
		<!-- Script Custom pada halaman. Kamu bisa memisah script pada file terpisah dengan menaruhnya di resource/js/namamodul/namafile.js -->
		<script type="text/javascript">
			$( '.ckeditor' ).ckeditor(); 
			var isiBeritaAcara;
			var getBeritaAcara;
			$(document).ready(function(){

				$('.date-picker').datepicker({
					format: "dd-mm-yyyy"
				});
			
				isiBeritaAcara = function(id)
				{
					$("#masterpage").hide();
					$("#master-detail").show();
					$.resetForm($("#pertemuanPembelajaran"));
					$("#pertemuan").val(id);
					$("#idPemb").val($("#idPembRepo").val());
					$("#hitungpertemuan").val(id);
					for(instance in CKEDITOR.instances)
					{
						CKEDITOR.instances[instance].setData("");
					}
					$('.date-picker').datepicker('update',Date.today());
				}
				$(".cancel").click(function(){
					$("#masterpage").show();
					$("#master-detail").hide();
				});
				
				$(".save").click(function(){
					var formValidator = $.initInputValidation($("#pertemuanPembelajaran"), {pertemuan:{required: true}});
					//formValidator.form();
					for(instance in CKEDITOR.instances)
					{
						CKEDITOR.instances[instance].updateElement();
					}
					
					if(formValidator.form()){
						blockUI($("#pesertadidik"));
						var formdata = $("#pertemuanPembelajaran").serialize();
						$.ajax({
							url: context_path+"modul/pembelajaran/beritaacara/simpan",
							data : formdata,
							type : 'post',
							success: function(data)
							{
								unblockUI($("#pertemuanPembelajaran"));
								if(data.status=='ok')
								{
									show_message("Sukses", data.message);
									setTimeout(function(){
				                         location.reload();
				                    }, 3000);
								}
								else if(data.status=="sudah ada")
								{
									show_message("Error", data.message);
									setTimeout(function(){
				                         location.reload();
				                    }, 3000);
								}
								else if(data.status=='expired')
								{ document.location=data.message; }
								else
								{ 
									show_message('Error', data.message);
									if(data.data!=null)
		                			for(i=0;i<data.data.length;i++)
		               				{
		                				if($('#'+data.data[i].field).parent().find('.error').length>0)
		                				{
		                					if(data.data[i].bindingFailure) $('#'+data.data[i].field).parent().find('.error').text("input tidak valid");
		                					else $('#'+data.data[i].field).parent().find('.error').text(data.data[i].defaultMessage);
		                				}
		                				else 
		                				{
		                					if(data.data[i].bindingFailure) $('#'+data.data[i].field).after('<label for="'+data.data[i].field+'" class="error text-danger">input tidak valid</label>');
		                					else $('#'+data.data[i].field).after('<label for="'+data.data[i].field+'" class="error text-danger">'+data.data[i].defaultMessage+'</label>');
		                				}
		                				$('#'+data.data[i].field).parent().find('.error').show();
		               				}
								}
							},
							error: $.ajaxErrorHandler,
							dataType : 'json'
						});
					}
				});
				
				getBeritaAcara = function(id)
				{
					$("#masterpage").hide();
					$("#master-detail").show();
					blockUI($("#master-detail"));
					eval("var tempData = {'idPertemuanPembelajaran' : '"+id+"'};");
					$.ajax({
						url: context_path+"modul/pembelajaran/beritaacara/edit",
						data : tempData,
						type : 'post',
						success: function(data)
						{
							unblockUI($("#master-detail"));
							if(data.status=='ok')
							{
								//$.resetForm($("#pertemuanPembelajaran"));
								$.fillToForm($("#pertemuanPembelajaran"), data.data);
								$("#idPemb").val(data.data.pemb.idPemb);
								$("#hitungpertemuan").val(data.data.pertemuan);
								$("#namapembelajaran").val(data.data.pemb.mk.namaMK+" "+data.data.pemb.nmPemb);
								for(instance in CKEDITOR.instances)
								{
									CKEDITOR.instances[instance].setData($("#"+instance).val());
								}
								$('#tglPertemuan').datepicker('update',data.data.tglPertemuan==null?"":(data.data.tglPertemuan.values[2]<10?"0"+data.data.tglPertemuan.values[2]:data.data.tglPertemuan.values[2])+"-"+(data.data.tglPertemuan.values[1]<10?"0"+data.data.tglPertemuan.values[1]:data.data.tglPertemuan.values[1])+"-"+data.data.tglPertemuan.values[0]);
							}
							else if(data.status=='expired')
							{ document.location=data.message; }
							else
							{ show_message('Error', data.message);}
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
