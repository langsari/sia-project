<%@page import="javax.print.attribute.standard.PresentationDirection"%>
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
				List<PendidikPengajar> listPendidikPengajar= (ArrayList<PendidikPengajar>) request.getAttribute("listPendidikPengajar");
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
									<td rowspan="2" style="min-width:150px;vertical-align:middle">Persentase kehadiran</td>
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
										Integer kehadiran = 0;
										for(int i=0;i<jumlahPertemuan;i++)
										{
											
											if(i<listPertemuanPembelajaran.size() && listListPresensiPengajar.get(j)!= null && listListPresensiPengajar.get(j).get(i)!=null && listListPresensiPengajar.get(j).get(i).getStsKehadiran().getaAbsen() == false)
												kehadiran++;
									%>
												<td>
													<%								
														if(i < listPertemuanPembelajaran.size())
														{
															if(listListPresensiPengajar.get(j).get(i)!=null)
															{
													%>
																	<span title="<%out.print(listListPresensiPengajar.get(j).get(i).getStsKehadiran().getNmStsKehadiran()); %>" >
																		<%=listListPresensiPengajar.get(j).get(i).getStsKehadiran().getKodeStsKehadiran() %>
																	</span>
													<%
															}
															else out.print("-");
														}
														else
														{
													%>
														 -
													<%
														}
													%>
												</td>
									<%
										}
										Double persentase;
										if(listPertemuanPembelajaran.size()>0) 
										{
											persentase = kehadiran*100.0/listPertemuanPembelajaran.size();
											persentase = Math.round(persentase*100)/100.0;
										}
										else persentase = 0.0;
									%>
										<td>
											<%=persentase %> %
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
		
		<!-- Script Custom pada halaman. Kamu bisa memisah script pada file terpisah dengan menaruhnya di resource/js/namamodul/namafile.js -->
		
			<jsp:include page="Footer.jsp" />
		</content>
	</body>
</html>
		