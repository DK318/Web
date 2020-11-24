<#import "commons.ftlh" as c>

<@c.page>
    <#if curUser??>
        <p>id: ${curUser.id}</p>
        <p>login: ${curUser.login}</p>
        <p>creation: ${curUser.creationTime}</p>
    <#else>
        <p>No such user.</p>
    </#if>
</@c.page>