<%@include file="include.jsp"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Currency Conversion</title>
<script src="http://code.jquery.com/jquery-1.10.2.js"></script>
<script src="http://code.jquery.com/ui/1.11.0/jquery-ui.js"></script>
<link rel="stylesheet" href="http://code.jquery.com/ui/1.11.0/themes/smoothness/jquery-ui.css">
</head>
<script>
$(document).ready(function() {
    $(function() {
        $("#conversionDate").datepicker({dateFormat: 'dd/mm/yy'}).datepicker({ maxDate: new Date});
        $("#sourceAmount").keydown(function (e) {
            // Allow: backspace, delete, tab, escape, enter and .
            if ($.inArray(e.keyCode, [46, 8, 9, 27, 13, 110, 190]) !== -1 ||
                 // Allow: Ctrl+A
                (e.keyCode == 65 && e.ctrlKey === true) ||
                 // Allow: Ctrl+C
                (e.keyCode == 67 && e.ctrlKey === true) ||
                 // Allow: Ctrl+X
                (e.keyCode == 88 && e.ctrlKey === true) ||
                 // Allow: home, end, left, right
                (e.keyCode >= 35 && e.keyCode <= 39)) {
                     // let it happen, don't do anything
                     return;
            }
            // Ensure that it is a number and stop the keypress
            if ((e.shiftKey || (e.keyCode < 48 || e.keyCode > 57)) && (e.keyCode < 96 || e.keyCode > 105)) {
                e.preventDefault();
            }
        });
    });
});
</script>
<body>

	   <div align="right" style="padding-right:5%;">
		<strong>Welcome ${userNameData}</strong><form:form action="logout"><input type="submit" value="Logout" /></form:form>
	   </div>
	   
	   <div style="border: 10px;border-color:#00F;" align="center">
			<center><h3 class="txt-bluedk"><strong>Currency Converter</strong></h3></center>
			<center><hr color="#1874CD" width="70%"/></center>
			<center><h5 class="txt-bluedk"><strong>Please enter your data to get the relevant currency values</strong></h5></center>
			<center><hr color="#1874CD" width="70%"/></center>
			
		<form:form id="currConvForm" method="post" action="convert" modelAttribute="conversionBean">
			
		   <table width="75%" align="center" style="padding-top: 10px; padding-right:5%">
			  <tr>
				<td ><strong><form:label path="sourceAmount">Amount</form:label></strong></td>
				<td><form:input id="sourceAmount" name="sourceAmount" path="sourceAmount" /></td>
				
				<td align="right"><strong><form:label path="fromCurrency">From Currency</form:label></strong></td>
				<td><form:select id="fromCurrency" path="fromCurrency" items="${currencyList}" name="fromCurrency" itemLabel="currencyName" itemValue="currencyCode" /></td>
				
			  	<td align="right"><strong><form:label path="toCurrency">To Currency</form:label></strong></td>
			  	<td><form:select id="toCurrency" name="toCurrency" path="toCurrency" items="${currencyList}" itemLabel="currencyName" itemValue="currencyCode"/></td>

			  	<td align="right"><strong><form:label path="conversionDate">Date</form:label></strong></td>
				<td><form:input id="conversionDate" name="conversionDate" path="conversionDate" readonly="true" maxlength="10"/></td>
				<td><input type="submit" value="Convert" /></td>
			  </tr>

			  <tr>
			  	<td>&nbsp;</td>
				<td align="right"><form:errors name="sourceAmount" path="sourceAmount" cssStyle="color:red;font-size:13px;"/></td>
				<td width="20%">&nbsp;</td>
				<td align="right"><form:errors name="fromCurrency" path="fromCurrency" cssStyle="color:red;font-size:13px;"/></td>
				<td width="20%">&nbsp;</td>
				<td align="right"><form:errors name="toCurrency" path="toCurrency" cssStyle="color:red;font-size:13px;"/></td>
				<td width="20%">&nbsp;</td>
				<td align="right"><form:errors name="conversionDate" path="conversionDate" cssStyle="color:red;font-size:13px;"/></td>																
			  </tr>			  
			  
			</table>
		</form:form>
		<div>
			<font color="red" ><core:out value="${errorMessage}"/></font>
			<core:if test="${not empty convertedAmountMessage}">
					<h3 class="txt-bluedk">${convertedAmountMessage}</h3>
			</core:if>
		</div>
	  </div>		 
		
		<div align="center" style="width:100%; padding-right:50%;">
		<core:if test="${not empty userActivityList}">
			<h3 class="txt-bluedk"><strong>Last viewed results</strong></h3>		
			<display:table name="userActivityList" style="font-size: 10pt;border : 1px solid #666;background:#ddbbbb;margin: 20px 0 20px 0 !important;" sort="list"
			defaultorder="descending" defaultsort="6" length="10">
				  <display:column property="convertedDate" title="Conversion Date" format="{0,date,dd-MM-yyyy}" style="text-align:center;border : 1px solid #666"/>
				  <display:column property="fromCurrency" title="From Currency" style="text-align:center;border:1px solid #666;"/>
				  <display:column property="sourceAmount" title="Amount" style="text-align:right;border : 1px solid #666"  format="{0,number}"/>
				  <display:column property="toCurrency" title="To Currency" style="text-align:center;border:1px solid #666"  />
				  <display:column property="convertedAmount" title="Converted Amount" style="text-align:right;border : 1px solid #666" class="odd"  />
				  <display:column property="createdDate" title="" style="display:none;"/>
			</display:table>
		</core:if>
		</div>
</body>
</html>