<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="decorator" uri="http://www.opensymphony.com/sitemesh/decorator"%>
<%@ page session="true" %>

<!DOCTYPE html>
<html>
    
	<head>
        <title><decorator:title default="Administrator | Sistem Informasi Akademik"></decorator:title></title>
        
        <meta content="width=device-width, initial-scale=1" name="viewport"/>
        <meta charset="UTF-8">
        <meta name="description" content="Admin Dashboard Template" />
        <meta name="keywords" content="admin,dashboard" />
        <meta name="author" content="Steelcoders" />
        
        <%@include file="DefaultCss.jsp" %>
        
        <decorator:head></decorator:head>
    
    </head>
    <body class="page-header-fixed">
        <div class="overlay"></div>
        
        <main class="page-content content-wrap">
        
            <div class="navbar">
                <%@include file="Header.jsp" %>
            </div><!-- Navbar -->
            
            <div class="page-sidebar sidebar">
                <%@include file="MenubarContent.jsp" %>
            </div><!-- Page Sidebar -->
            
            <div class="page-inner">
                <div class="page-title">
                    <%@include file="PageTitle.jsp" %>
                </div>
                <div id="main-wrapper">
                	<decorator:body/>
                </div><!-- Main Wrapper -->
                <div class="page-footer">
        			<%@include file="Footer.jsp" %>            
                </div>
            </div><!-- Page Inner -->
            
        </main><!-- Page Content -->
        
        <div class="cd-overlay"></div>
	
        <%@include file="DefaultScripts.jsp" %>
        
        <decorator:getProperty property="page.scripts"/>

    </body>

</html>