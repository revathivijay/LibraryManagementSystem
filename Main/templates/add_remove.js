//console.log('hi')
//$(document).ready(function()
//{
//
// $('form').on('submit', function(event)
//  {
//    console.log('sending data');
//    var bid_arr = [];
//    for(var i=0; i<document.getElementById('no').value; i++)
//    {
//        bid_arr.push(document.getElementById('bid-'+Number(Number(i)+1)).value);
//        console.log(document.getElementById('bid-'+Number(Number(i)+1)))
//    }
//    console.log(bid_arr);
//
//     $.ajax({
//        data : {
////                sid:$('#sid').val(),
//                lid:$('#lid').val(),
////                num:$('#num').val(),
//               },
//           type : 'POST',
//           url : 'http://127.0.0.1:5000/index'
//         });
//
////      $.done(function(data)
////      {
////      //This is not working!!!
////        console.log('hiiii')
////        console.log(data);
////      });
//    event.preventDefault();
//  });
//});


$(document).ready(function()
{
     $('form').on('submit', function(event)
      {
        console.log('Submitted')
        var bid_arr = [];
        for(var i=0; i<document.getElementById('no').value; i++)
        {
            bid_arr.push(document.getElementById('bid-'+Number(Number(i)+1)).value);
            console.log(document.getElementById('bid-'+Number(Number(i)+1)))
        }
        console.log(bid_arr);
        console.log($('#sid').val())
         $.ajax({
            data : {
               sid : $('#sid').val(),
               rev : $('#no').val(),
               dateee: $('#date').val().toString(),
               lid: $('#lid').val(),
               crossDomain: true,
               arr: bid_arr
                   },
               type : 'POST',
               url : 'http://127.0.0.1:5000/index',
               success: function (response) {
//                var resp = JSON.parse(response)
                console.log(response);
            },
              });

              console.log('Completed')
          .done(function(data)
          {

          });
        event.preventDefault();
      });
    });
