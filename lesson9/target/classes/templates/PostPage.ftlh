<#import "commons.ftlh" as c>
<#import "/spring.ftl" as spring/>

<@c.page>
    <@c.post post=post/>
    <#if user??>
        <div class="comment-form">
            <div class="header">Add comment</div>
            <form method="post" action="">
                <div class="field">
                    <div class="value">
                        <label for="text"></label><textarea id="text" name="text" cols="40" rows="5">${comment.text!}</textarea>
                    </div>
                    <@c.error "comment.text"/>
                </div>
                <div class="button-field">
                    <input type="submit" value="Add">
                </div>
            </form>
        </div>
    </#if>
    <div class="comments-label">Comments:</div>
    <#list post.comments as comment>
        <div class="comment">${comment.user.login}: ${comment.text}</div>
        <br/>
    </#list>
</@c.page>