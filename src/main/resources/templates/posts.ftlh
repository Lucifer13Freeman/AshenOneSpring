<#import "components/common.ftlh" as c>

<@c.page>
<div class="w-50">
    <form method="get" action="/posts" class="form-inline">
        <div class="input-group mb-3"> 
            <input type="text" 
                name="filter" 
                class="form-control"
                value="${filter?ifExists}" 
                placeholder="Search by tag"/>
            <button type="submit" class="btn btn-primary ml-2">Search</button>
        </div>
     </form>
</div>

<#include "components/post_edit.ftlh" />

<#include "components/post_list.ftlh" />

</@c.page>