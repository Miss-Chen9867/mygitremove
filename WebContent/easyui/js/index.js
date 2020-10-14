var limitX = 50, limitY = 50;
var containerW = $('.container').width();
var containerH = $('.container').height();
$( ".container" ).mousemove(function( e ) {
  var mouseY = Math.min(e.clientY/(containerH*.01), limitY);
  var mouseX = Math.min(e.clientX/(containerW*.01), limitX);
  $('.pupil').css('top', mouseY);
  $('.pupil').css('left', mouseX);
  console.log(e.clientY+'  '+e.clientX);
});