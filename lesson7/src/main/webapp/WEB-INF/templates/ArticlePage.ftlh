<#import "commons.ftlh" as c>

<@c.page>
    <div class="article-creation">
        <div class="article-header">Write your article here:</div>
        <form method="post" action="">
            <input type="hidden" name="action" value="submit"/>
            <table>
                <tbody>
                <tr class="title-field">
                    <td class="name">
                        <label for="title">Title:</label>
                    </td>
                    <td class="value">
                        <input id="title" name="title" value="">
                    </td>
                </tr>
                <tr class="text-field">
                    <td class="name">
                        <label for="text">Text:</label>
                    </td>
                    <td class="value">
                        <label>
                            <textarea name="text" maxlength="1000" cols="50" rows="20"></textarea>
                        </label>
                    </td>
                </tr>
                <tr>
                    <td></td>
                    <td class="error"></td>
                </tr>
                <tr class="button-field">
                    <td colspan="2">
                        <input type="submit" value="Submit article">
                    </td>
                </tr>
                </tbody>
            </table>
        </form>
    </div>

    <script>
        $(function () {
            $(".article-creation form").submit(function () {
                const title = $(this).find("input[name='title']").val();
                const text = $(this).find("textarea").val();

                ajax({action: "submit", title, text}, undefined, "", $(this));

                return false;
            })
        })
    </script>
</@c.page>