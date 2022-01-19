<a class="btn btn-primary" 
    data-bs-toggle="collapse" href="#collapseCreatePost" 
    role="button" 
    aria-expanded="false" 
    aria-controls="collapseCreatePost"
>
    Post editor
</a>
<div class="collapse <#if post??>show</#if>" id="collapseCreatePost">
    <div class="input-group mt-3">
        <form action="/posts" method="post" enctype="multipart/form-data">
            <div class="input-group">
                <input type="text" 
                    class="form-control mb-2 ${(textError??)?string('is-invalid', '')}" 
                    value="<#if post??>${post.text}</#if>"
                    name="text" 
                    placeholder="Input some text..."/>
                <#if textError??>
                    <div class="invalid-feedback">${textError}</div>
                </#if>
            </div>
            <div class="input-group">
                <input type="text" 
                    class="form-control mb-2 ${(tagError??)?string('is-invalid', '')}" 
                    value="<#if post??>${post.tag}</#if>"
                    name="tag" 
                    placeholder="Input some tag..."/>
                <#if tagError??>
                    <div class="invalid-feedback">${tagError}</div>
                </#if>
            </div>
            <div class="form-file input-group">
                <input type="file" 
                    name="file" 
                    class="form-file-input form-control mb-2" 
                />
            </div>
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
            <#if post??><input type="hidden" name="id" value="${post.id!""}"/></#if>
            <div class="input-group">
                <button type="submit" class="btn btn-primary">Save post</button>
            </div>
        </form>
    </div>
</div>