$(function () {
    $("#new_device_button").on('submit', (event) => reset_form(event));
});

function reset_form(event) {
 event.reset();

  $("#new_device_form").hide();
}