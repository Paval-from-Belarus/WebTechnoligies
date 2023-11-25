const onAuthorizationBtnClicked = () => {
    const body = {
        user_name: $('#auth_user_name').val(),
        password: $('#auth_password').val()
    }
    const authorizationUrl = $('#authorization-form').attr('action');
    $.post(authorizationUrl, body, processServerResponse);
}
const onRegistrationBtnClicked = () => {
    const body = {
        user_name: $('#reg_user_name').val(),
        password: $('#reg_password').val(),
        role: $('#admin_role').is(':checked') ? 2 : 1,
        email: $('#user_email').val()
    }
    const registrationUrl = $('#registration-form').attr('action');
    $.post(registrationUrl, body,   function(data, status){
        alert("Data: " + data + "\nStatus: " + status);
    });
}
const processServerResponse = (response) => {
    const body = JSON.parse(response);
    window.location.replace(body['redirect_page']);
}
$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
});