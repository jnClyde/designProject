$(document).ready(function () {
    $.getJSON("getall",function (data) {
        let tbl = $("#tablebody");
        $.each(data,function (i,item) {
            let tr = $("<tr>");
            let td = $("<td>");
            td.append($("<a>",{
                'href':"order?orderId="+item.id,
                'text':item.id
            }));
            tr.append(td);
            tr.append($("<td>",{'text':item.title}));
            tr.append($("<td>",{'text':item.status}));
            tr.append($("<td>",{'text':item.techtask}));
            tbl.append(tr);
        })
    })
});