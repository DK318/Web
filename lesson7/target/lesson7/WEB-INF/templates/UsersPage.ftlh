<#-- @ftlvariable name="users" type="ru.itmo.wp.model.domain.User[]" -->
<#import "commons.ftlh" as c>

<@c.page>
    <div class="users datatable">
        <div class="caption">User</div>
        <table>
            <thead>
            <tr class="first-row">
                <th>Id</th>
                <th>Login</th>
            </tr>
            </thead>
            <tbody>
            <template>
                <tr>
                    <td class="user_id"></td>
                    <td class="user_login"></td>
                    <td class="user_admin"><div class="state"></div></td>
                </tr>
            </template>
            <tr class="noData">
                <td colspan="3">
                    No data
                </td>
            </tr>
            </tbody>
        </table>
    </div>

    <script>
        $(function () {
            const $noData = $(".users .noData");
            $noData.find("td").text("Loading...");

            let admin = false;

            ajax({action: "getCurrentUser"}, function (response) {
                if (response["curUser"]) {
                    admin = response["curUser"]["admin"];
                }
            });

            ajax({action: "findAll"}, function(response) {
                if (admin) {
                    $(".users .first-row").append("<th>Admin</th>");
                }

                response["users"].forEach(function(user) {
                    $noData.hide();
                    const $tr = $($(".users template").prop("content")).clone();
                    $tr.find(".user_id").text(user["id"]);
                    $tr.find(".user_login").text(user["login"]);
                    if (admin) {
                        const adminLabel = (user["admin"] ? "disable" : "enable");
                        $tr.find(".user_admin .state").text(user["admin"]);
                        $tr.find(".user_admin").append("<a href='#'>" + adminLabel + "</a>");
                    } else {
                        $tr.find(".user_admin").hide();
                    }
                    $tr.find("tr").attr("data-userId", user["id"]);
                    $(".users tbody").append($tr);
                });

                $(".users tr").click(function() {
                    ajax({
                        action: "findUser",
                        userId: $(this).attr("data-userId")
                    }, function (response) {
                        notify(response["user"].creationTime);
                    });
                });

                $(".users .user_admin a").click(function () {
                    const $link = $(this);
                    const targetUserId = $link.closest("tr").find(".user_id").text();
                    const $state = $link.closest("tr").find(".user_admin .state");
                    const linkState = $link.text();

                    /*alert($link.closest("tr").find(".user_admin .state").text());
                    alert(!$state.text());*/

                    $link.text("Loading...");

                    ajax({action: "switchAdmin", targetUserId, admin: !($state.text() === "true")}, function (response) {
                        if (response["error"]) {
                            error(response["error"]);
                            $link.text(linkState);
                        } else {
                            if ($state.text() === "true") {
                                $link.text("enable");
                            } else {
                                $link.text("disable");
                            }
                            $state.text(!($state.text() === "true"));
                            if ($state.text() === "true") {
                                notify("This user is now admin");
                            } else {
                                notify("This user is now not admin");
                            }
                        }
                    })
                })
            });
        })
    </script>
</@c.page>
