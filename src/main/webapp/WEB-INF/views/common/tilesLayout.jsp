<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<!DOCTYPE html>
<html lang="ko">
  <head>
  	<link rel="icon" href="data:;base64,iVBORw0KGgo=">
    <meta charset="UTF-8">
    <title>비지언스 MES</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <%-- <meta name="_csrf" content="${_csrf.token}">
	<meta name="_csrf_header" content="${_csrf.headerName}"> --%>
    <jsp:include page="common_css.jsp"></jsp:include>
    <jsp:include page="common_js.jsp"></jsp:include>
  </head>
   <body>
    <section id="wrapper" class="content">
      <tiles:insertAttribute name="header"/>
      <tiles:insertAttribute name="left"/>
      <tiles:insertAttribute name="body"/>
    </section>
  </body>
  
</html>