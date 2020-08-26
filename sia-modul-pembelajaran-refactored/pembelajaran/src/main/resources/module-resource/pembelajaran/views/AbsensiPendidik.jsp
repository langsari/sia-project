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
		<link href="${pageContext.servletContext.contextPath}/resources/plugins/x-editable/bootstrap3-editable/css/bootstrap-editable.css" rel="stylesheet">
		<link href="${pageContext.servletContext.contextPath}/resources/plugins/x-editable/inputs-ext/typeaheadjs/lib/typeahead.js-bootstrap.css" rel="stylesheet">
		<link href="${pageContext.servletContext.contextPath}/resources/plugins/x-editable/inputs-ext/address/address.css" rel="stylesheet">
		<link rel="stylesheet" href="${pageContext.servletContext.contextPath}/resources/plugins/jquery.datatables/media/css/jquery.dataTables.min.css">
		<link rel="stylesheet" href="${pageContext.servletContext.contextPath}/resources/plugins/jquery.datatables/extensions/ColVis/css/dataTables.colVis.min.css">
		<link href="${pageContext.servletContext.contextPath}/resources/plugins/gritter/css/jquery.gritter.css" rel="stylesheet" type="text/css" />
	</head>
	<body>
	
	<%
		Integer jumlahPertemuan = (Integer) request.getAttribute("jumlahPertemuan");
		Pemb pemb = (Pemb) request.getAttribute("pemb");
		List<PertemuanPembelajaran> listPertemuanPembelajaran= (ArrayList<PertemuanPembelajaran>) request.getAttribute("listPertemuanPembelajaran");
		List<PendidikPengajar> listPendidikPengajar= (ArrayList<PendidikPengajar>) request.getAttribute("listPendidikPengajar");
		List<StsKehadiran> listStsKehadiran= (ArrayList<StsKehadiran>) request.getAttribute("listStsKehadiran");
		List<List<PresensiPengajar>> listListPresensiPengajar= (ArrayList<List<PresensiPengajar>>) request.getAttribute("listListPresensiPengajar");
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
									<td rowspan="2" style="min-width:150px;vertical-align:middle">NIP</td>
									<td rowspan="2" style="min-width:150px;vertical-align:middle">Pendidik</td>
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
								for(int j=0;j<listPendidikPengajar.size();j++)
								{
							%>
									<tr>
										<td style="vertical-align:middle">
											<%=listPendidikPengajar.get(j).getPtk().getNipPtk() %>
										</td>
										<td style="vertical-align:middle">
											<%=listPendidikPengajar.get(j).getPtk().getNmPtk() %>
										</td>
									<%
										for(int i=0;i<jumlahPertemuan;i++)
										{
									%>
												<td>
													<%								
														if(i < listPertemuanPembelajaran.size())
														{
														%>
															
															<select class="absenPendidik-<%=j %>-<%=i %>" >
														<%
															for(int k=0;k<listStsKehadiran.size();k++)
															{
													%>
																	<!-- <label title="<%out.print(listStsKehadiran.get(k).getNmStsKehadiran()); %>" for="absenPendidik[<%=j %>][<%=i %>][<%=k %>]">
																	<a href="#" class="absen" data-type="radiolist" data-value="2," data-title="Absensi" class="editable editable-radiolist"></a>
																	<input type="radio" class="absenPendidik[<%=j %>][<%=i %>]" id="absenPendidik[<%=j %>][<%=i %>][<%=k %>]" 
																		name="absenPendidik-<%=j %>-<%=i %>" 
																		value="<%=listStsKehadiran.get(k).getIdStsKehadiran() %>" 
																		<%
																			if(listListPresensiPengajar.get(j).get(i)!=null)
																			{
																				if(listListPresensiPengajar.get(j).get(i).getStsKehadiran().getIdStsKehadiran()==listStsKehadiran.get(k).getIdStsKehadiran())
																				{
																					%>
																					<%="checked" %>
																					<%
																				}
																			}
																		%>
																	/>
																	<%=listStsKehadiran.get(k).getKodeStsKehadiran() %>
																	</label> -->
																	<option title="<%out.print(listStsKehadiran.get(k).getNmStsKehadiran()); %>"
																	class="absenPendidik[<%=j %>][<%=i %>]" id="absenPendidik[<%=j %>][<%=i %>][<%=k %>]" 
																		value="<%=listStsKehadiran.get(k).getIdStsKehadiran() %>" 
																		<%
																			if(listListPresensiPengajar.get(j).get(i)!=null)
																			{
																				if(listListPresensiPengajar.get(j).get(i).getStsKehadiran().getIdStsKehadiran()==listStsKehadiran.get(k).getIdStsKehadiran())
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
														else if(i>listPertemuanPembelajaran.size())
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
					<a target="_blank" href="${pageContext.servletContext.contextPath}/absensi/rekappendidik/${pemb.idPemb}" class="btn btn-success">Rekap Pendidik</a>
				</div>
			</div>
		</div>
	
		<content tag="scripts">			
			<script type="text/javascript" src="${pageContext.servletContext.contextPath}/resources/plugins/jquery.datatables/media/js/jquery.dataTables.min.js"></script>
			<script type="text/javascript" src="${pageContext.servletContext.contextPath}/resources/plugins/jquery.datatables/extensions/ColVis/js/dataTables.colVis.min.js"></script>
			<script src="${pageContext.servletContext.contextPath}/resources/plugins/bootstrap-datepicker/js/bootstrap-datepicker.js" type="text/javascript"></script>
			<script src="${pageContext.servletContext.contextPath}/resources/plugins/gritter/js/jquery.gritter.js" rel="stylesheet" type="text/javascript"></script>
			<script src="${pageContext.servletContext.contextPath}/resources/plugins/jquery-validation/jquery.validate.min.js" rel="stylesheet" type="text/javascript"></script>
			<script src="${pageContext.servletContext.contextPath}/resources/plugins/ckeditor/ckeditor.js" type="text/javascript" ></script>
			<script src="${pageContext.servletContext.contextPath}/resources/plugins/ckeditor/adapters/jquery.js" type="text/javascript"></script>
			<script src="${pageContext.servletContext.contextPath}/resources/js/sia.method.js" type="text/javascript"></script>
			<script	src="${pageContext.servletContext.contextPath}/resources/js/jquery.masterpage.sia.js" type="text/javascript"></script>
			
			<script type="text/javascript">
				var simpan;
				var jumlahPertemuan=<%=listPertemuanPembelajaran.size()%>;
				var jumlahPendidik =<%=listPendidikPengajar.size()%>;
				var pemb = "<%=pemb.getIdPemb()%>";
				var pendidikPengajar = [<%for(int i=0;i<listPendidikPengajar.size();i++) { if(i!=0)out.print(","); out.print("\""+listPendidikPengajar.get(i).getIdPendidikPengajar()+"\""); }%>];
				var pertemuanPembelajaran = [<%for(int i=0;i<listPertemuanPembelajaran.size();i++) { if(i!=0)out.print(","); out.print("\""+listPertemuanPembelajaran.get(i).getIdPertemuanPembelajaran()+"\""); }%>];
				var presensiPengajar = [
				                        <%
				                        	for(int i=0;i<listListPresensiPengajar.size();i++)
				                        	{
				                        		if(i!=0) out.print(",");
				                        		out.print("[");
				                        		for(int j=0;j<listListPresensiPengajar.get(i).size();j++)
				                        		{
					                        		if(j!=0) out.print(",");
					                        		if(listListPresensiPengajar.get(i).get(j)!=null)
				                        				out.print("\""+listListPresensiPengajar.get(i).get(j).getIdPresensiPengajar()+"\"");
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
						var absenPendidik = new Array();
						for(i=0;i<jumlahPendidik;i++)
						{
							for(j=0;j<jumlahPertemuan;j++)
							{
								var stsKehadiran = $(".absenPendidik-"+i+"-"+j+"").val();
								var idPresensi = presensiPengajar[i][j];
								if(typeof stsKehadiran === 'undefined'){
									stsKehadiran = null;
									 };
								if(typeof idPresensi === 'undefined'){
									idPresensi = null;
									 };
								absen = {
											"idPresensiPengajar": idPresensi,
											"idPemb": pemb, 
											"idPendidikPengajar": pendidikPengajar[i], 
											"idPertemuanPembelajaran": pertemuanPembelajaran[j], 
											"idStsKehadiran": stsKehadiran
										};
								absenPendidik.push(absen);
							}
						}
						console.log(absenPendidik);
						var json_stringify = JSON.stringify(absenPendidik)
						console.log(json_stringify);
						blockUI($("#masterpage"));
						$.ajax({
							url: context_path+"modul/pembelajaran/absensi/pendidik/saveabsensi",
							data : json_stringify,
							type : 'post',
							contentType: "application/json", //this is required for spring 3 - ajax to work (at least for me)
						    success: function(data)
							{
								//unblockUI($("#masterpage"));
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
					}/* 
					$(".absen").editable({
						name    : 'myradio',
						source:[
						        {value:1, text:"hadir"},
						        {value:2, text:"absen"},
						        {value:3, text:"sakit"},
						        {value:4, text:"ijin"}
						]
					}); */
				});
			</script>
			<jsp:include page="Footer.jsp" />
		</content>
	</body>
</html>