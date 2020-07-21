//onSuccess - function which will run onSuccess, takes in the response.
function getUser(onSuccess) {
    $.ajax({
        method: "POST",
        url: "get-user",
        timeout: 3000,
        error: function () {
            console.error("Failed to get ajax response");
        },
        success: function (response) {
            if (response === "signup.html") {
                window.location.assign(response);
            } else {
                response = JSON.parse(response);
                $(".username").text(response.username);
                $(".balance").text(response.balance);
                onSuccess(response) //response is user json
            }
        }
    });
}