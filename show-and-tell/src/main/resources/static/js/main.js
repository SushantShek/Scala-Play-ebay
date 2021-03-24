//(function(){
//    console.log("Hello World!");
//    tableList(${jsonArr});
//})();
function tableList(objArray) {

console.log(" object array");

var a =objArray;
    a = a.replace(/&quot;/g, '"');
    a = a.replace(/(?:\r\n|\r|\n)/g, '');
    JSON.parse(a)
             needHeader = true;
             var array = typeof objArray != 'object' ? JSON.parse(a) : a;

             var str = '<table class= hello-title">';

             // Only create table head if needHeader is set to True..
             if (needHeader) {
                 str += '<thead><tr>';
                 for (var index in array[0]) {
                     str += '<th scope="col">' + index + '</th>';
                 }
                 str += '</tr></thead>';
             }

             // table body
             str += '<tbody>';
             for (var i = 0; i < array.length; i++) {
                 str += (i % 2 == 0) ? '<tr class="alt">' : '<tr>';
                 for (var index in array[i]) {
                     str += '<td>' + array[i][index] + '</td>';
                 }
                 str += '</tr>';
             }
             str += '</tbody>'
             str += '</table>';
             return str;
         }
//
//         function productList() {
//             $.ajax({
//                 url :'/orders',
//                 type : 'GET',
//                 dataType : 'json',
//                 success : function(icecreamList) {
//                     productListSuccess(icecreamList);
//                 },
//                 error : function(request, message, error) {
//                     handleException(request, message, error);
//                 }
//             });
//         }
//
//         function productListSuccess(icecreamList) {
//
//             $.each(icecreamList, function(index, product) {
//                 productAddRow(product);
//             });
//         }
//
//         function productAddRow(product) {
//
//             if ($("#productTable tbody").length == 0) {
//                 $("#productTable").append("<tbody></tbody>");
//             }
//             $("#productTable tbody").append(productBuildTableRow(product));
//         }
//
//         function productBuildTableRow(product) {
//
//             var ret = "<tr>" + "<td>" + product.name + "</td>" + "<td>" +product.price
//                     + "</td>" + "<td>" + product.qty + "</td>" + "<td>" + product.total
//                     + "</td>" + "</tr>";
//             return ret;
//         }
//         productListSuccess([
//           {
//             name: 'Test 1',
//             price: 1,
//             qty: 1,
//             total: 1
//            },
//            {
//             name: 'Test 2',
//             price: 2,
//             qty: 2,
//             total: 4
//            }
//         ]);