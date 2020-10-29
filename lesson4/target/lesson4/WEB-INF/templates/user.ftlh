<#-- @ftlvariable name="user_id" type="java.lang.Long" -->
<#-- @ftlvariable name="handle" type="java.lang.String" -->
<#import "./common.ftlh" as c>

<@c.page>
    ${users[0]}
    <#if handle??>
        <#assign u = c.findBy(users, "handle", handle)!/>
    <#elseif user_id?? && user_id?is_number>
        <#assign u = c.findBy(users, "id", user_id)!/>
    </#if>
        <#if u?has_content>
            <div class="userBox">
                <@c.userlink u false/>
                <img class="userPic" src="/img/no-title.jpg" alt="User Picture">
                <ul>
                    <li>id: ${u.id}</li>
                    <li>handle: ${u.handle}</li>
                    <li>name: ${u.name}</li>
                    <li>posts: <a href="/posts?user_id=${u.id}">${c.countPosts(u)}</a></li>
                </ul>
            </div>
            <div class="arrows">
                <#list users as item>
                    <#if item.id == u.id>
                        <#if item?is_first>
                            &larr;
                        <#else>
                            <a href="/user?handle=${users[item_index - 1].handle}">&larr;</a>
                        </#if>
                        <#if item?is_last>
                            &rarr;
                        <#else>
                            <a href="/user?handle=${users[item_index + 1].handle}">&rarr;</a>
                        </#if>
                    </#if>
                </#list>
            </div>
        <#else>
            No such user
        </#if>
</@c.page>
