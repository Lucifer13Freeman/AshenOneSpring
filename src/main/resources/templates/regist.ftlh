<#import "components/common.ftlh" as c>
<#import "components/login.ftlh" as l>

<@c.page>
<h3 class="mb-4">Registration</h3>
${message?ifExists}
<@l.login "/regist" true/>
</@c.page>