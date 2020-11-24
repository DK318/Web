<#-- @ftlvariable name="noticeForm" type="ru.itmo.wp.form.NoticeForm" -->
<#import "commons.ftlh" as c>
<#import "/spring.ftl" as spring>

<@c.page>
    <form method="post" action="">
        <label>
            <textarea name="content" rows="5" cols="50">${noticeForm.content!}</textarea>
        </label>
        <br>
        <@c.error "noticeForm.content"/>
        <input type="submit" value="Add notice">
    </form>
</@c.page>