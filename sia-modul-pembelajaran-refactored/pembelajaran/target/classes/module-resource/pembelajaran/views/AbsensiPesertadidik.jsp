<%@page import="com.sia.modul.domain.PresensiPd"%>
<%@page import="com.sia.modul.domain.Pd"%>
<%@page import="com.sia.modul.domain.Pemb"%>
<%@page import="java.util.UUID"%>
<%@page import="com.sia.modul.domain.PresensiPengajar"%>
<%@page import="com.sia.modul.domain.StsKehadiran"%>
<%@page import="com.sia.modul.domain.PendidikPengajar"%>
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
	</head>
	<body>
	
	<%
				Integer jumlahPertemuan = (Integer) request.getAttribute("jumlahPertemuan");
				Pemb pemb = (Pemb) request.getAttribute("pemb");
				List<PertemuanPembelajaran> listPertemuanPembelajaran= (ArrayList<PertemuanPembelajaran>) request.getAttribute("listPertemuanPembelajaran");
				List<Pd> listPd= (ArrayList<Pd>) request.getAttribute("listPd");
				List<StsKehadiran> listStsKehadiran= (ArrayList<StsKehadiran>) request.getAttribute("listStsKehadiran");
				List<List<PresensiPd>> listListPresensiPd= (ArrayList<List<PresensiPd>>) request.getAttribute("listListPresensiPd");
		%>
	
	<div class="row" id="masterpage">
				<div class="col-md-12">
				<div class="panel panel-white">
							<div class="panel-heading">
								<h4 class="panel-title">${pemb.mk.namaMK} ${pemb.nmPemb}</h4>
							</div>
							<div class="panel-body">
					<div class="table-responsive">
						<table class="table table-striped table-bordered table-hover table-checkable table-colvis datatable">
							<thead style="text-align: center;">
								<tr>
									<td rowspan="2" style="min-width:150px;vertical-align:middle">NIM</td>
									<td rowspan="2" style="min-width:150px;vertical-align:middle">Peserta</td>
									<td colspan="${jumlahPertemuan }">Pertemuan Pembelajaran</td>
								</tr>
								<tr>
									<%
										for(int i=0;i<jumlahPertemuan;i++)
										{
									%>
											<td style="min-width:60px"><%=i+1 %></td>
									<%
										}
									%>
								</tr>
							</thead>
							<tbody>
							<%
								for(int j=0;j<listPd.size();j++)
								{
							%>
									<tr>
										<td style="vertical-align:middle">
											<%=listPd.get(j).getNimPd() %>
										</td>
										<td style="vertical-align:middle">
											<%=listPd.get(j).getNmPd() %>
										</td>
									<%
										for(int i=0;i<jumlahPertemuan;i++)
										{
									%>
												<td>
													<%								
														if(i < listPertemuanPembelajaran.size())
														{%>
														
															<select class="absenPesertadidik-<%=j %>-<%=i %>" >
														<%
															for(int k=0;k<listStsKehadiran.size();k++)
															{
													%>
																	<option title="<%out.print(listStsKehadiran.get(k).getNmStsKehadiran()); %>"
																	class="absenPesertadidik[<%=j %>][<%=i %>]" id="absenPesertadidik[<%=j %>][<%=i %>][<%=k %>]" 
																		value="<%=listStsKehadiran.get(k).getIdStsKehadiran() %>" 
																		<%
																			if(listListPresensiPd.get(j).get(i)!=null)
																			{
																				if(listListPresensiPd.get(j).get(i).getStsKehadiran().getIdStsKehadiran()==listStsKehadiran.get(k).getIdStsKehadiran())
																				{
																					%>
																					<%="selected" %>
																					<%
																				}
																			}
																			else if(listStsKehadiran.get(k).isaKehadiranAwal()) out.print("selected");
																		%>
																	>
																	<%=listStsKehadiran.get(k).getKodeStsKehadiran() %>
																	</option>
																
													<%
															}
														%>
														</select>
														<%
														}
														else
														{
													%>
													<%
														}
													%>
												</td>
									<%
										}
									%>
									</tr>
								<%
								}
								%>
							</tbody>
						</table>
					</div>
					<button type="button" class="btn btn-primary" onclick="simpan()">Simpan</button>
					<a target="_blank" href="${pageContext.servletContext.contextPath}/absensi/rekappesertadidik/${pemb.idPemb}" class="btn btn-success">Rekap Peserta Didik</a>
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
				src="${pageContext.servletContext.contextPath}/resources/plugins/jquery-blockui/jquery.blockui.js"
				type="text/javascript"></script>
		<script src="${pageContext.servletContext.contextPath}/resources/plugins/ckeditor/ckeditor.js" type="text/javascript" ></script>
		<script src="${pageContext.servletContext.contextPath}/resources/plugins/ckeditor/adapters/jquery.js" type="text/javascript"></script>
		<script src="${pageContext.servletContext.contextPath}/resources/js/sia.method.js" type="text/javascript"></script>
		
		<!-- Script Custom pada halaman. Kamu bisa memisah script pada file terpisah dengan menaruhnya di resource/js/namamodul/namafile.js -->
		
		<script type="text/javascript">
			var simpan;
			var jumlahPertemuan=<%=listPertemuanPembelajaran.size()%>;
			var jumlahPesertadidik =<%=listPd.size()%>;
			var pemb = "<%=pemb.getIdPemb()%>";
			var pendidikPengajar = [<%for(int i=0;i<listPd.size();i++) { if(i!=0)out.print(","); out.print("\""+listPd.get(i).getIdPd()+"\""); }%>];
			var pertemuanPembelajaran = [<%for(int i=0;i<listPertemuanPembelajaran.size();i++) { if(i!=0)out.print(","); out.print("\""+listPertemuanPembelajaran.get(i).getIdPertemuanPembelajaran()+"\""); }%>];
			var presensiPd = [
			                        <%
			                        	for(int i=0;i<listListPresensiPd.size();i++)
			                        	{
			                        		if(i!=0) out.print(",");
			                        		out.print("[");
			                        		for(int j=0;j<listListPresensiPd.get(i).size();j++)
			                        		{
				                        		if(j!=0) out.print(",");
				                        		if(listListPresensiPd.get(i).get(j)!=null)
			                        				out.print("\""+listListPresensiPd.get(i).get(j).getIdPresensiPd()+"\"");
				                        		else out.print("null");
			                        		}
			                        		out.print("]");
			                        	}
			                        %>
			                        
			                        ];
			$(document).ready(function(){
					
				simpan = function(id)
				{
					var object;
					var absenPd = new Array();
					for(i=0;i<jumlahPesertadidik;i++)
					{
						for(j=0;j<jumlahPertemuan;j++)
						{
							var stsKehadiran = $(".absenPesertadidik-"+i+"-"+j+"").val();
							var idPresensi = presensiPd[i][j];
							if(typeof stsKehadiran === 'undefined'){
								stsKehadiran = null;
								 };
							if(typeof idPresensi === 'undefined'){
								idPresensi = null;
								 };
							absen = {
										"idPresensiPd": idPresensi,
										"idPemb": pemb, 
										"idPd": pendidikPengajar[i], 
										"idPertemuanPembelajaran": pertemuanPembelajaran[j], 
										"idStsKehadiran": stsKehadiran
									};
							absenPd.push(absen);
						}
					}
					console.log(absenPd);
					var json_stringify = JSON.stringify(absenPd)
					console.log(json_stringify);
					blockUI($("#masterpage"));
					
					$.ajax({
						url: context_path+"modul/pembelajaran/absensi/pesertadidik/saveabsensi",
						data : json_stringify,
						type : 'post',
						contentType: "application/json", //this is required for spring 3 - ajax to work (at least for me)
					    success: function(data)
						{
							//unblockUI($("#master-detail"));
							if(data.status=='ok')
							{
								console.log(data);
								show_message("Sukses", data.message);
								setTimeout(function(){
			                         location.reload();
			                    }, 3000);
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