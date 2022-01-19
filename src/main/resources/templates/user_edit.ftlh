<#import "components/common.ftlh" as c>

<@c.page>
Edit user

<form action="/user" method="post">
    <input class="form-control" type="text" name="username" value="${user.username}"/>
    <#list roles as role>
    <div>
        <label><input type="checkbox" name="${role}" ${user.roles?seq_contains(role)?string("checked", "")}/>${role}</label>
    </div>
    </#list>
    <input class="form-control" type="hidden" value="${user.id}" name="userId"/>
    <input type="hidden" value="${_csrf.token}" name="_csrf"/>
    <button class="btn btn-primary" type="submit">Save</button>
</form>
</@c.page>