$(function() {
  $('#buttonWithGet').click(function(){
    var userId = $('#userId').val();
    var userName = $('#userName').val();

    $.ajax({
      url: '/forGetMethod?userId=' + userId + '&userName=' + userName,
      success: function(result) {
        $('#result').text(result);
      }
    });
  });

  $('#buttonWithPost').click(function(){
    var userId = $('#userId').val();
    var userName = $('#userName').val();
    var json = {'userId': userId, 'userName': userName };
    $.ajax({
      method: 'POST',
      data: json,
      url: '/forPostMethod',
      success: function(result) {
        $('#result').text(result);
      }
    });
  });
});