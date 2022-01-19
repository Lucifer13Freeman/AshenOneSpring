<#import "components/common.ftlh" as c>
<#import "components/login.ftlh" as l>

<@c.page>
<h3 class="mb-4">Login</h3>
<#--  ${message?ifExists}  -->
<#if messageType?? && Session?? && Session.SPRING_SECURITY_LAST_EXCEPTION??>
    <div class="alert alert-${messageType}" role="alert">
        ${Session.SPRING_SECURITY_LAST_EXCEPTION.message}
    </div>
</#if>
<#if message??>
    <div class="alert alert-${messageType}" role="alert">
        ${message}
    </div>
</#if>
<@l.login "/login" false/>
</@c.page>