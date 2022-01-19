<#import "components/common.ftlh" as c>

<@c.page>
<h3>${userProfile.username}</h3>
<div>${type}</div>
<ul class="list-group">
    <#list users as user>
        <li class="list-group-item">
            <a href="/user-posts/${user.id}">${user.getUsername()}</a>
        </li>
    </#list>
</ul>
</@c.page>