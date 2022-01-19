<#assign 
    know = Session.SPRING_SECURITY_CONTEXT?? 
>

<#if know>
    <#assign 
        user = Session.SPRING_SECURITY_CONTEXT.authentication.principal 
        name = user.getUsername()
        currentUserId = user.getId()
        isAdmin = user.isAdmin()
    >
<#else>
    <#assign 
        name = ""
        currentUserId = -1
        isAdmin = false
    >
</#if>