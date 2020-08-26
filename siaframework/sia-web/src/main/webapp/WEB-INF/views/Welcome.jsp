<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page session="true" %>

<!DOCTYPE html>
<html>
    
	<head>
        <title>Selamat Datang | Sistem Informasi Akademik</title>
        
        <meta content="width=device-width, initial-scale=1" name="viewport"/>
        <meta charset="UTF-8">
        <meta name="description" content="Admin Dashboard Template" />
        <meta name="keywords" content="admin,dashboard" />
        <meta name="author" content="Steelcoders" />
        
        <%@include file="master/DefaultCss.jsp" %>
    
    </head>
    <body class="page-header-fixed compact-menu">
        <div class="overlay"></div>
        
        <main class="page-content content-wrap">
        
            <div class="navbar">
                <%@include file="master/Header.jsp" %>
            </div><!-- Navbar -->
           
            <div class="page-inner">
            
                <div class="page-title">
                    <h3>Selamat Datang</h3>
                </div>
                
                <br/>
              
                <div id="main-wrapper">
                	<div class="row">
						<div class="col-md-12">
							<div class="panel panel-white">
								<div class="panel-heading clearfix">
									<h4 class="panel-title">Modul Tersedia</h4>
								</div>
								<div class="panel-body">
									
									<div class="col-md-8">
						               	
						               	<c:forEach items="${sessionScope.moduleSession}" var="module">
						               		<div id="${module.getIdModul()}" class="col-md-3 module-item">
												<figure>
													<c:choose>
														<c:when test="${not empty module.getBase64EncodedImage()}">
															<img src="data:image/png;base64,${module.getBase64EncodedImage()}" alt="${module.getNamaModul()}" height="150" width="150" style="max-height: 100%; max-width: 100%">
														</c:when>
														<c:otherwise>
															<img src="http://i969.photobucket.com/albums/ae180/salah_udin/Tugas%20Akhir/volume-384-on-5121.png" alt="${module.getNamaModul()}" height="150" width="150" style="max-height: 100%; max-width: 100%">
														</c:otherwise>
													</c:choose>
								                    <figcaption>
								                    	<h3>${module.getNamaModul()}</h3>
								                    	<p>
									                    	<c:set var="i" value="${0}"/>
									                    	<c:forEach items="${module.getMenus()}" var="menu">
									                    		<c:if test="${i > 0}">
									                    			<br/>
									                    		</c:if>
									                    		<a href="${pageContext.servletContext.contextPath}${modulUrlPrefix}${menu.getCompleteUrl()}">${menu.getNamaMenu()}</a>
									                    		<c:set var="i" value="${i + 1}"/>
									                    	</c:forEach>
								                    	</p>
								                    </figcaption>
								                </figure>
								          	</div>
										</c:forEach>
										
									</div>
									
								</div>
							</div>
						</div>
					</div>
                </div><!-- Main Wrapper -->
                
                <div class="page-footer">
                    <%@include file="master/Footer.jsp" %> 
                </div>
                
            </div><!-- Page Inner -->
            
        </main><!-- Page Content -->
        
        <div class="cd-overlay"></div>
        
        <%@include file="master/DefaultScripts.jsp" %>
        
    </body>

</html>