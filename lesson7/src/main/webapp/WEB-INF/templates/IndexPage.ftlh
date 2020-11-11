<#import "commons.ftlh" as c>

<@c.page>
    Welcome on Codeforces!

    <div class="articles">
        <div class="noData">No data.</div>
        <template>
            <article>
                <div class="title"></div>
                <div class="information"></div>
                <div class="body"></div>
            </article>
        </template>
    </div>
<script>
    $(function () {
        $("aside section").click(function () {
            $(this).css("background-color", "red");
        });

        const $noData = $(".articles .noData");
        $noData.text("Loading...");

        ajax({action: "findAll"}, function (response) {
            if (response["articles"].length !== 0) {
                response["articles"].forEach(function (article) {
                    $noData.hide();
                    const $tpl = $($(".articles template").prop("content")).clone();
                    $tpl.find(".title").text(article["title"]);
                    $tpl.find(".information").text("By " + article["user"]["login"] + ", " + article["creationTime"]);
                    $tpl.find(".body").text(article["text"]);
                    $(".articles").append($tpl);
                });
            } else {
                $noData.text("No data.");
            }
        });
    })
</script>
</@c.page>
