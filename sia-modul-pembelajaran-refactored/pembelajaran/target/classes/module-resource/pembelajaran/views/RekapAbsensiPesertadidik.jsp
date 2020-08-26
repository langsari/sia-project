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
				List<Pd> listPd= (ArrayList<Pd>) request.getAttribute("listPd");
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
									<td rowspan="2" style="min-width:150px;vertical-align:middle">Persentase kehadiran</td>
									<td rowspan="2" style="min-width:150px;vertical-align:middle">Minimal kehadiran</td>
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
										Integer kehadiran = 0;
										for(int i=0;i<jumlahPertemuan;i++)
										{
											
											if(i<listPertemuanPembelajaran.size() && listListPresensiPd.get(j)!= null && listListPresensiPd.get(j).get(i)!=null && listListPresensiPd.get(j).get(i).getStsKehadiran().getaAbsen() == false)
												kehadiran++;
									%>
												<td>
													<%								
														if(i < listPertemuanPembelajaran.size())
														{
															if(listListPresensiPd.get(j).get(i)!=null)
															{
													%>
																	<span title="<%out.print(listListPresensiPd.get(j).get(i).getStsKehadiran().getNmStsKehadiran()); %>" >
																		<%=listListPresensiPd.get(j).get(i).getStsKehadiran().getKodeStsKehadiran() %>
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
											persentase = kehadiran*100.0/(jumlahPertemuan<listPertemuanPembelajaran.size()?jumlahPertemuan:listPertemuanPembelajaran.size());
											persentase = Math.round(persentase*100)/100.0;
										}
										else persentase = 0.0;
									%>
										<td>
											<span class="persentase"><%=persentase %></span> %
										</td>
										<td>
											${minimalKehadiran} %
										</td>
									</tr>
								<%
								}
								%>
							</tbody>
						</table>
					</div>
					<br/>
					<br/>
					<table class="table table-striped table-bordered table-hover" style="max-width:50%">
						<thead>
							<tr>
								<td>Tanda Warna</td>
								<td>Keterangan</td>
							</tr>
						</thead>
						<tbody>
							<tr style="height:50px">
								<td style="width:150px;background:#f25656"></td>
								<td>Kurang dari batas ketidak hadiran</td>
							</tr>
						</tbody>
					</table>
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
		
		<script type="text/javascript">
			var minimal = ${minimalKehadiran};
			$(".persentase").each(function(){
				console.log($(this).text());
				if(parseFloat($(this).text())<minimal)
				{
					$(this).parent().parent().attr('style','background:#f25656; color:white; font-weight:700');
				}
			});
		</script>
			<jsp:include page="Footer.jsp" />
		</content>
	</body>
</html>
	