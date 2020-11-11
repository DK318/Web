<#-- @ftlvariable name="articles" type="ru.itmo.wp.model.domain.Article[]" -->
<#import "commons.ftlh" as c>

<@c.page>
    <div class="articles datatable">
        <div class="result"></div>
        <div class="caption">Articles</div>
        <table>
            <thead>
            <tr>
                <th>Id</th>
                <th>Title</th>
                <th>Hide/Show</th>
            </tr>
            </thead>
            <tbody>
            <#list articles as article>
                <tr>
                    <td class="article-id">${article.id}</td>
                    <td class="article-title">${article.title}</td>
                    <td class="article-hidden">
                        <#if article.hidden>
                            <button class="hide-show-button">Show</button>
                        <#else>
                            <button class="hide-show-button">Hide</button>
                        </#if>
                        <div class="loading"></div>
                    </td>
                </tr>
            </#list>
            </tbody>
        </table>
    </div>

    <script>
        $(function () {
            $(".hide-show-button").click(function () {
                const $button = $(this);
                const $loading = $button.closest("tr").find(".loading");
                const articleId = $button.closest("tr").find(".article-id").text();
                const hidden = $button.text() !== "Show";

                $loading.text("Loading...");

                ajax({action: "hideOrShowArticle", articleId, hidden}, function (response) {
                    if (response["error"]) {
                        error(response["error"]);
                    } else {
                        let label = ($button.text() === "Show" ? "Hide" : "Show");
                        $button.text(label);
                        notify("Success");
                    }
                    $loading.text("");
                });
            })
        })
    </script>
</@c.page>