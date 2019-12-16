<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<head>

    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title><spring:message code="welcome" /></title>

    <script src="https://code.jquery.com/jquery-3.4.1.js"></script>

    <script type="text/javascript">
        $(document).ready(function() {
            var selItem = localStorage.getItem("locales");
            $('#locales').val(selItem ? selItem : 'en_US');
            $("#locales").change(function() {
                var selectedOption = $('#locales').val();
                if (selectedOption) {
                    window.location.replace('?lang=' + selectedOption);
                    localStorage.setItem("locales", selectedOption);
                }
            });
        });
    </script>

    <style>
        legend {
            width: auto;
            margin-left: auto;
            margin-right: auto;
        }
    </style>
</head>
<body>
<div style="width: 600px; margin: auto;">
    <fieldset>
        <legend>
            <spring:message code="welcome" />
        </legend>
        <p>
            <label><%--<spring:message code="chooseLang" />--%></label>   <select
                id="locales">
            <option value="zh_CN">中文</option>
            <option value="en_US">English</option>
        </select>
        </p>
    </fieldset>
    <div style="clear: both"></div>
    <div>
        <%--<spring:message code="copyright" />--%>
        ©
        <%--<spring:message code="year" />--%>
    </div>
</div>
</body>