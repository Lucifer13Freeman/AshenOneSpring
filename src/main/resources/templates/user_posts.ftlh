<#import "components/common.ftlh" as c>

<@c.page>
<h3>${userProfile.username}</h3>
<#if !isCurrentUser>
    <#if isFollower>
        <a class="btn btn-info" href="/user/unfollow/${userProfile.id}">Unfollow</a>
    <#else>
        <a class="btn btn-info" href="/user/follow/${userProfile.id}">Follow</a>
    </#if>
</#if>
<div class="container my-3">
    <div class="row">
        <div class="col">
            <div class="card">
                <div class="card-body">
                    <div class="card-title">Subscriptions</div>
                    <h3 class="card-text">
                        <a href="/user/subscriptions/${userProfile.id}/list">${subscriptionsCount}</a>
                    </h3>
                </div>
            </div>
        </div>
        <div class="col">
            <div class="card">
                <div class="card-body">
                    <div class="card-title">Followers</div>
                    <h3 class="card-text">
                        <a href="/user/followers/${userProfile.id}/list">${subscribersCount}</a>
                    </h3>
                </div>
            </div>
        </div>
    </div>
</div>    

<#if isCurrentUser>
    <#include "components/post_edit.ftlh" />
</#if>

<#include "components/post_list.ftlh" />

</@c.page>