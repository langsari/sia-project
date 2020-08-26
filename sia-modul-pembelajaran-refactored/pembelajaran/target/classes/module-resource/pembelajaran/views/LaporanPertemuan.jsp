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
								<h4 class="panel-title">Pertemuan</h4>
							</div>
							<div class="panel-body">
					<div class="row">
						<div class="col-md-4">
						<form id="filter">	
							<div class="form-group">
								<label>Tahun Ajaran</label>
	                            <select class="form-control" id="thnAjaran" name="idThnAjaran">
	                            	<c:forEach items="${listThnAjaran}" var="thnAjaran">
	                            		<option value="${thnAjaran.idThnAjaran}" <c:if test="${thnAjaran.idThnAjaran == smtAktif.thnAjaran.idThnAjaran}">selected="true"</c:if> >${thnAjaran.thnThnAjaran}</option>
	                            	</c:forEach>
	                            </select>
                            </div>
							<div class="form-group">
								<label>Semester</label>
	                            <select class="form-control" id="smt" name="idSmt">
	                            	<c:forEach items="${listSmt}" var="smt">
	                            		<option value="${smt.idSmt}" <c:if test="${smt.idSmt == smtAktif.smt.idSmt}">selected="true"</c:if> >${smt.nmSmt}</option>
	                            	</c:forEach>
	                            </select>
                            </div>
							<div class="form-group">
								<label>Prodi</label>
	                            <select class="form-control" id="satMan" name="idSatMan">
	                            	<c:forEach items="${listSatMan}" var="satMan">
	                            		<option value="${satMan.idSatMan}">${satMan.nmSatMan}</option>
	                            	</c:forEach>
	                            </select>
                            </div>
							<div class="form-group">
                        		<button type="button" class="btn btn-primary" onclick="refreshData()">Filter Data</button>
                        	</div>
                        </form>
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
									<td>Kode</td>
									<td>Matakuliah</td>
									<td>Pembelajaran</td>
									<td>Penanggung Jawab</td>
									<td>Total Pertemuan</td>
									<td>Realisasi Pertemuan</td>
									<td>Presentase Pertemuan</td>
									<td>Pertemuan Minimal</td>
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
		<content tag="scripts">			
			<script type="text/javascript" src="${pageContext.servletContext.contextPath}/resources/plugins/jquery.datatables/media/js/jquery.dataTables.min.js"></script>
			<script type="text/javascript" src="${pageContext.servletContext.contextPath}/resources/plugins/jquery.datatables/extensions/ColVis/js/dataTables.colVis.min.js"></script>
			<script src="${pageContext.servletContext.contextPath}/resources/plugins/bootstrap-datepicker/js/bootstrap-datepicker.js" type="text/javascript"></script>
			<script src="${pageContext.servletContext.contextPath}/resources/plugins/gritter/js/jquery.gritter.js" rel="stylesheet" type="text/javascript"></script>
			<script src="${pageContext.servletContext.contextPath}/resources/plugins/jquery-validation/jquery.validate.min.js" rel="stylesheet" type="text/javascript"></script>
			<script	src="${pageContext.servletContext.contextPath}/resources/js/jquery.masterpage.sia.js" type="text/javascript"></script>
			<script
				src="${pageContext.servletContext.contextPath}/resources/js/sia.method.js"
				type="text/javascript"></script>
			<script>
				var refreshData;
			</script>
			<script type="text/javascript">
		$(document).ready(function(){
			var table = $('.datatable').DataTable({
				"sDom": "<'row'<'dataTables_header clearfix'<'col-md-3'<l>><'col-md-9'f<'pull-right'CT>>r>>t<'row-fluid'<'dataTables_footer clearfix'<'col-md-6'i><'col-md-6'p>>>",
				tableTools: {
					"sSwfPath": context_path+"resources/plugins/jquery.datatables/extensions/TableTools/swf/copy_csv_xls_pdf.swf"
				},
				"oColVis": {
					"buttonText": "Columns <i class='icon-angle-down'></i>",
					"iOverlayFade": 0
				},
				"aoColumns": [
					/* Kode */
					{ "bVisible":    true },
					/* Matakuliah */
					{ "bVisible":    true },
					/* Pembelajaran */
					{ "bVisible":    true },
					/* Ketua */
					{ "bVisible":    true },
					/* Total Pertemuan */
					{ "bVisible":    false },
					/* Realisasi Pertemuan */
					{ "bVisible":    false },
					/* Presentase Pertemuan */
					{ "bVisible":    true },
					/* Pertemuan minimal*/
					{ "bVisible":    true },
					/* Aksi */
					{ 
						"bVisible":    true,
						bSortable: false,
						bSearchable: false,
						mRender: function(data,type,full){
							var action =' <a target="_blank" href="'+context_path+'modul/pembelajaran/absensi/rekappesertadidik/'+data+'" class="btn btn-primary">Absensi Peserta didik</a>';
							action +=' <a target="_blank" href="'+context_path+'modul/pembelajaran/absensi/rekappendidik/'+data+'" class="btn btn-primary">Absensi Pendidik</a>';
							action+=' <a target="_blank" href="'+context_path+'modul/pembelajaran/beritaacara/laporan/'+data+'" class="btn btn-success">Berita Acara</a>';
							return action;
						}
					}
				],
	        });
			
			refreshData = function(){
				blockUI($(".datatable"));
				var formdata = $("#filter").serialize();
				$.ajax({
					url: context_path+'modul/pembelajaran/absensi/pembabsensi',
					data : formdata,
					type : 'post',
					success: function(data)
					{
						unblockUI($(".datatable"));
						if(data.status=='ok')
						{
							console.log(table);
							table.clear();
							show_message("Sukses", data.message)
							for(var i=0;i<data.data.length;i++)
							{
								table.row.add(data.data[i]).draw();	
							}
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
			
			refreshData();
		});
		</script>
		<jsp:include page="Footer.jsp" />
		</content>
	</body>
</html>