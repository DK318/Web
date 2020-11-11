window.notify = function (message) {
    $.notify(message, {
        position: "right bottom",
        className: "success"
    });
}

window.error = function (message) {
    $.notify(message, {
        position: "right bottom"
    });
}

let ajax = function (data, success, url, form) {
    let defaultSuccess = function (response) {
        if (response["error"]) {
            form.find(".error").text(response["error"]);
        } else {
            location.href = response["redirect"];
        }
    };

    if (success === undefined) {
        success = defaultSuccess;
    }

    let ajaxObject = {
        type: "POST",
        dataType: "json",
        data,
        success
    };

    if (url !== undefined) {
        ajaxObject.url = url;
    }

    $.ajax(ajaxObject);
}