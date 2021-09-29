<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<!DOCTYPE html>
<html lang="ko">
  <head>
  	<link rel="shortcut icon" href="#">
    <meta charset="UTF-8">
    <title>비지언스 MES</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <jsp:include page="common_css.jsp"></jsp:include>
    <jsp:include page="common_js.jsp"></jsp:include>
  </head>
   <body>
    <section id="wrapper" class="content">
      <tiles:insertAttribute name="body"/>
    </section>
  </body>
  
</html>