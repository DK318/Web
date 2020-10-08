<#-- @ftlvariable name="user_id" type="java.lang.Long" -->
<#-- @ftlvariable name="post_id" type="java.lang.Long" -->
<#import "./common.ftlh" as c>
<@c.page>
    <#if post_id?? && post_id?is_number>
        <#assign p = c.findBy(posts, "id", post_id)!/>
        <#if p?has_content>
            <@c.makepost p false/>
        <#else>
            Post not found.
        </#if>
    <#elseif user_id?? && user_id?is_number>
        <#list posts?reverse as post>
            <#if post.user_id == user_id>
                <@c.makepost post false/>
            </#if>
        </#list>
    </#if>
</@c.page>