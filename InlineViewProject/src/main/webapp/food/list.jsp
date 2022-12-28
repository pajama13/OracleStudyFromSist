<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR" import="java.util.*,com.sist.dao.*"%>
<%
	//자바 코딩 위치
	FoodDAO dao=new FoodDAO();
	String strPage=request.getParameter("page"); //입력이 아닌 선택된 값을 받아옴
	if(strPage==null) //맨처음은 선택 받기 전이므로 null값
	{
		strPage="1";
	}
	int totalpage=dao.foodTotalPage(); //총페이지 구하기
	int curpage=Integer.parseInt(strPage); //현재페이지
	ArrayList<FoodVO> list=dao.foodListData(curpage);

%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>Insert title here</title>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
<style type="text/css">
.container{
	margin-top:50px;
	
}
.row{
	margin:0px auto;
	width:1200px;
}
h1{
	text-align:center
}
</style>
</head>
<body>
	<div class="container">
	  <div class="row">
	    <%
	      for(FoodVO vo:list)
	      {
	    %>
	    	   <!-- col-md-N 관련 : 12가 되면 넘어감, 3이면 4개씩, 6이면 2개씩-->
	    	   <div class="col-md-3">
		  	    <div class="thumbnail">
			      <a href="detail.jsp?fno=<%=vo.getFno()%>">
			        <img src="<%=vo.getPoster() %>" alt="Lights" style="width:100%">
			        <div class="caption">
			          <p><%=vo.getName() %></p>
			        </div>
			      </a>
			    </div>
			  </div>
	    <%
	      }
	    %>
	  </div>
	  <div style="height:30px"></div>
	    <div class="text-center">
	      <a href="list.jsp?psge=<%=curpage>1?curpage-1:curpage %>" class="btn btn-sm btn-danger">이전</a>
	        <%=curpage %> page / <%=totalpage %> pages
	      <a href="list.jsp?page=<%=curpage<totalpage?curpage+1:curpage %>" class="btn btn-sm btn-danger">다음</a>
	    </div>
	</div>
</body>
</html>