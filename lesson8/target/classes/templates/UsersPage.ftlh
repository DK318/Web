<#-- @ftlvariable name="users" type="ru.itmo.wp.domain.User[]" -->
<#import "commons.ftlh" as c>
<#import "/spring.ftl" as spring>

<@c.page>
    <@c.error "disabledForm.disabled"/>
    <@c.error "disabledForm.login"/>
    <@c.error "disabledForm.senderLogin"/>
    <div class="users datatable">
        <div class="caption">User</div>
        <table>
            <thead>
            <tr>
                <th>Id</th>
                <th>Login</th>
                <th>Created</th>
                <#if user??>
                    <th>Is Disabled</th>
                </#if>
            </tr>
            </thead>
            <tbody>
            <#if users?size==0>
                <tr class="noData">
                    <td colspan="3">
                        No data
                    </td>
                </tr>
            <#else>
                <#list users as u>
                <tr>
                    <form method="post" action="">
                        <#if user??>
                            <input type="hidden" name="senderLogin" value="${user.login}">
                        </#if>
                        <td class="id">${u.id}</td>
                        <input type="hidden" name="login" value="${u.login}">
                        <td class="login">${u.login}</td>
                        <td class="creationTime">${u.creationTime}</td>
                        <#if user??>
                            <td class="disabled">
                                <input type="hidden" name="disabled" value="${u.disabled?string}">
                                <#if u.disabled>
                                    <input type="submit" value="Enable">
                                <#else>
                                    <input type="submit" value="Disable">
                                </#if>
                            </td>
                        </#if>
                    </form>
                </tr>
                </#list>
            </#if>
            </tbody>
        </table>
    </div>
</@c.page>
