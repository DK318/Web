<#import "./common.ftlh" as c>

<@c.page>
    <#list posts?reverse as post>
        <@c.makepost post true/>
    </#list>
</@c.page>
