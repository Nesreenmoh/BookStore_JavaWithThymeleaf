var href;
$(document).ready(function () {
  $('.deletebtn').on('click', function (event) {
    event.preventDefault();
    href = $(this).attr('href');
    console.log(href);
    $('#btnYes').attr('href', href);
    $('.myForm #deleteModal').modal();
  });
});
