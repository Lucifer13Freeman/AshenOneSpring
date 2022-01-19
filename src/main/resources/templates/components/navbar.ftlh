<#include "security.ftlh" >
<#import "login.ftlh" as l>

<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
  <div class="container-fluid">
    <a class="navbar-brand" href="/">AshenOne</a>
    <button 
      class="navbar-toggler" 
      type="button" 
      data-bs-toggle="collapse" 
      data-bs-target="#navbarContent" 
      aria-controls="navbarContent" 
      aria-expanded="false" 
      aria-label="Toggle navigation"
    >
      <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarContent">
      <ul class="navbar-nav me-auto">
        <li class="nav-item">
          <a class="nav-link" href="/">Home</a>
        </li>
        <#if user??>
        <li class="nav-item">
          <a class="nav-link" href="/posts">Posts</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="/user-posts/${currentUserId}">My posts</a>
        </li>
        </#if>
        <#if isAdmin>
        <li class="nav-item">
          <a class="nav-link" href="/user">User list</a>
        </li>
        </#if>
      </ul>
      <ul class="navbar-nav ms-auto">
      <#if user??>
        <li class="nav-item">
          <a class="nav-link me-3" href="/user/profile">${name}</a>
        </li>
        <@l.logout />
      <#else>
        <li class="nav-item">
          <a class="nav-link me-3 btn btn-success" href="/regist">Regist</a>
        </li>
        <li class="nav-item">
          <a class="nav-link btn btn-primary" href="/login">Login</a>
        </li>
      </#if>
      </ul>
    </div>
  </div>
</nav> 