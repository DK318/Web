<#-- @ftlvariable name="user_id" type="java.lang.Long" -->
<#import "./common.ftlh" as c>
<@c.page>
    <#if user_id?? && user_id?is_number>
        <#assign flag = false/>
        <#list posts?reverse as post>
            <#if post.user_id == user_id>
                <@c.makepost post false/>
                <#assign flag = true/>
            </#if>
        </#list>
        <#if flag == false>
            No posts from this user.
        </#if>
    <#else>
        Bad user id.
    </#if>
</@c.page>