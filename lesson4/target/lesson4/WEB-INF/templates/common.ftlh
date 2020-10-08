<#-- @ftlvariable name="post_id" type="java.lang.Long" -->
<#-- @ftlvariable name="is_short" type="java.lang.Boolean" -->
<#macro page>
    <!DOCTYPE html>
    <html lang="ru">
    <head>
        <meta charset="UTF-8">
        <title>Codeforces</title>
        <link rel="stylesheet" href="/css/normalize.css">
        <link rel="stylesheet" href="/css/style.css">
        <link rel="stylesheet" href="/css/user.css">
        <link rel="stylesheet" href="/css/table.css">
        <link rel="stylesheet" href="/css/post.css">
        <link rel="icon" href="/favicon.ico">
    </head>
    <body>
    <@header/>
    <div class="middle">
        <@sidebar/>
        <main>
            <#nested/>
        </main>
    </div>
    <@footer/>
    </body>
    </html>
</#macro>

<#macro header>
    <header>
        <a href="#" class="logo"><img alt="Codeforces" title="Codeforces"
                                      src="/img/codeforces-vs-coronavirus-65.png"></a>
        <div class="languages">
            <a href="#"><img alt="In English" title="In English"
                             src="/img/gb.png"></a>
            <a href="#"><img alt="In Russian" title="In Russian"
                             src="/img/ru.png"></a>
        </div>
        <div class="enter-or-register-box">
            <#if user??>
                <@userlink user true/>
                |
                <a href="/logout">Logout</a>
            <#else>
                <a href="/enter">Enter</a>
                |
                <a href="/register">Register</a>
            </#if>
        </div>
        <nav>
            <#assign items=[
            {"href": "/index", "name": "Home"},
            {"href": "/misc/help", "name": "Help"},
            {"href": "/contests", "name": "Contests"},
            {"href": "/users", "name": "Users"}
            ]/>
            <ul>
                <#list items as item>
                    <li>
                        <#if item.href == href>
                            <a class="underlined" href="${item.href}">${item.name}</a>
                        <#else>
                            <a href="${item.href}">${item.name}</a>
                        </#if>
                    </li>
                </#list>
            </ul>
        </nav>
    </header>
</#macro>

<#macro sidebar>
    <aside>
        <#list posts?reverse as post>
            <@info post/>
        </#list>
    </aside>
</#macro>

<#macro footer>
    <footer>
        2010-2020 &copy; by Mike Mirzayanov
    </footer>
</#macro>

<#macro userlink user nameOnly>
    <#if nameOnly>
        <a href="/user?handle=${user.handle}">${user.name}</a>
    <#else>
        <a class="${user.color} username" href="/user?handle=${user.handle}">${user.handle}</a>
    </#if>
</#macro>

<#macro tablerow user>
    <tr>
        <td>${user.id}</td>
        <td>${user.name}</td>
        <td><@userlink user false/></td>
    </tr>
</#macro>

<#function findBy items key value>
    <#list items as item>
        <#if item[key] == value>
            <#return item/>
        </#if>
    </#list>
</#function>

<#function countPosts user>
    <#assign count = 0/>
    <#list posts as post>
        <#if post.user_id == user.id>
            <#assign count++/>
        </#if>
    </#list>
    <#return count/>
</#function>

<#macro formatpost post is_short>
    <#if post.text?length gt 250 && is_short>
        ${post.text?string[0..250]}...
    <#else>
        ${post.text}
    </#if>
</#macro>

<#macro makepost post is_short>
    <article>
        <div class="title">${post.title}</div>
        <div class="information">By <@userlink findBy(users, "id", post.user_id) false/></div>
        <div class="body">
            <@formatpost post is_short/>
        </div>
    </article>
</#macro>

<#macro info post>
    <section>
        <div class="header">Post#${post.id}</div>
        <div class="body">
            <@formatpost post true/>
        </div>
        <div class="footer">
            <a href="/post?post_id=${post.id}">View all</a>
        </div>
    </section>
</#macro>