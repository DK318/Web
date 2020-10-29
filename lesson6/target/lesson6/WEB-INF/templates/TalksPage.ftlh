<#-- @ftlvariable name="error" type="java.lang.String" -->
<#-- @ftlvariable name="users" type="ru.itmo.wp.model.domain.User[]" -->
<#-- @ftlvariable name="talks" type="ru.itmo.wp.model.domain.Talk[]" -->
<#import "commons.ftlh" as c>

<@c.page>
    <div class="send-message">
        <form method="post" action="">
            <input type="hidden" name="action" value="submit">
            <label>
                <select name="select">
                    <option selected="selected">Choose receiver</option>
                    <#list users as user>
                        <option>${user.login}</option>
                    </#list>
                </select>
            </label>
            <br/>
            <label>
                <textarea name="textarea" maxlength="300" cols="30" rows="10"></textarea>
            </label>
            <div class="button-field">
                <input type="submit" value="Send message">
            </div>
        </form>
    </div>
    <#if error??>
        <div class="send-error">
            ${error!}
        </div>
    </#if>
    <div class="datatable">
        <div class="caption">Messages</div>
        <table>
            <thead>
            <tr>
                <th>Id</th>
                <th>Sender</th>
                <th>Receiver</th>
                <th>Text</th>
                <th>Send time</th>
            </tr>
            </thead>
            <tbody>
            <#list talks as talk>
                <tr>
                    <td>${talk.id}</td>
                    <td>${talk.sourceUserLogin}</td>
                    <td>${talk.targetUserLogin}</td>
                    <td>${talk.text}</td>
                    <td>${talk.creationTime}</td>
                </tr>
            </#list>
            </tbody>
        </table>
    </div>
</@c.page>