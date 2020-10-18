<#-- @ftlvariable name="post_id" type="java.lang.Long" -->
<#import "./common.ftlh" as c>
<@c.page>
    <#if post_id?? && post_id?is_number>
        <#assign p = c.findBy(posts, "id", post_id)!/>
    </#if>
    <#if p?has_content>
        <@c.makepost p false/>
    <#else>
        Post not found.
    </#if>
</@c.page>