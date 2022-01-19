<#include "security.ftlh">
<#import "pagination.ftlh" as p>

<div class="row row-cols-1 row-cols-md-2 row-cols-lg-3 g-4 my-4" id="post-list">
    <#list page.content as post>
    <div class="col">
        <div class="card h-100" data-id="${post.id}">
            <#if post.filename??>
            <img src="/img/${post.filename}" class="card-img-top"/>
            </#if>
            <div class="m-2 card-body">
                <div><i>${post.tag}</i></div>
                <div><span class="mt-2 card-text">${post.text}</span></div>
            </div>
            <div class="card-footer text-muted d-flex container">
                <div class="row">
                    <div class="d-flex me-auto">
                        <a class="col align-self-center" href="/user-posts/${post.author.id}">${post.authorName}</a>
                    </div>
                    <div class="d-flex ms-auto">
                        <a class="col align-self-center" href="/posts/${post.id}/like">
                            <#if post.isLiked>
                                <i class="fas fa-heart"></i>
                            <#else>
                                <i class="far fa-heart"></i>
                            </#if>
                            ${post.likes}
                        </a>
                    <#if post.author.id == currentUserId>
                        <a href="/user-posts/${post.author.id}?post=${post.id}"
                            class="btn btn-secondary">Edit
                        </a>
                        <a href="/del-user-posts/${post.author.id}?post=${post.id}"
                            class="btn btn-danger ms-2">Delete
                        </a>
                    </#if>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <#else>
        No posts...
    </#list>
</div>

<@p.pagination url page />