<%@ page language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>占座功能实现</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <link rel="stylesheet" type="text/css" href="css/jquery.seat-charts.css">
    <link rel="stylesheet" type="text/css" href="css/style.css">
</head>
<body>
<div class="wrapper">
    <div class="container">
        <div id="seat-map">
            <div class="front-indicator">自习室</div>
        </div>
<%--        <div class="booking-details">--%>
<%--            <h3>已选中的座位 (<span id="counter">0</span>):</h3>--%>
<%--            <ul id="selected-seats">--%>
<%--            </ul>--%>
<%--            <p>总价: <b>$<span id="total">0</span></b></p>--%>
<%--            <p><button class="checkout-button">结算</button></p>--%>
<%--            <div id="legend"></div>--%>
<%--        </div>--%>
    </div>
</div>
<script src="js/jquery-1.11.0.min.js"></script>
<script src="js/jquery.seat-charts.min.js"></script>
<script>
    var firstSeatLabel = 1;
    var map = [];
    var unAvaliableSeat = [];

    function draw() {
        var $cart = $('#selected-seats'),
            $counter = $('#counter'),
            $total = $('#total'),
            sc = $('#seat-map').seatCharts({
                map,
                // seats: {
                //     f: {
                //         price   : 100,
                //         classes : 'first-class', //your custom CSS class
                //         category: '头等舱'
                //     },
                //     e: {
                //         price   : 40,
                //         classes : 'economy-class', //your custom CSS class
                //         category: '经济舱'
                //     }
                //
                // },
                // naming : {
                //     top : false,
                //     getLabel : function (character, row, column) {
                //         return firstSeatLabel++;
                //     },
                // },
                // legend : {
                //     node : $('#legend'),
                //     items : [
                //         [ 'f', 'available',   '头等舱' ],
                //         [ 'e', 'available',   '经济舱'],
                //         [ 'f', 'unavailable', '已预定']
                //     ]
                // },
                //通过this.settings.label可以获得当前座位号
                //
                click: function () {
                    if (this.status() == 'available') {
                        // $('<li>'+this.data().category+this.settings.label+'号座位'+'：<br/>价格：<b>$'+this.data().price+'</b> <a href="#" class="cancel-cart-item">[删除]</a></li>')
                        //     .attr('id','cart-item-'+this.settings.id)
                        //     .data('seatId', this.settings.id)
                        //     .appendTo($cart);
                        // $counter.text(sc.find('selected').length+1);
                        // $total.text(recalculateTotal(sc)+this.data().price);

                        return 'selected';
                    } else if (this.status() == 'selected') {
                        //update the counter
                        $counter.text(sc.find('selected').length-1);
                        //and total
                        // $total.text(recalculateTotal(sc)-this.data().price);

                        //remove the item from our cart
                        $('#cart-item-'+this.settings.id).remove();

                        //seat has been vacated
                        return 'available';
                    } else if (this.status() == 'unavailable') {
                        //seat has been already booked
                        return 'unavailable';
                    } else {
                        return this.style();
                    }
                }
            });

        //this will handle "[cancel]" link clicks
        $('#selected-seats').on('click', '.cancel-cart-item', function () {
            //let's just trigger Click event on the appropriate seat, so we don't have to repeat the logic here
            sc.get($(this).parents('li:first').data('seatId')).click();
        });

        //let's pretend some seats have already been booked
        sc.get(unAvaliableSeat).status('unavailable');

    }

    function recalculateTotal(sc) {
        var total = 0;

        //basically find every selected seat and sum its price
        sc.find('selected').each(function () {
            total += this.data().price;
        });

        return total;
    }


</script>
<p>输入您要查询的房间号</p>
<input type="text" id="room"/><br/>
<p>输入要设置为过道的坐标</p>
<input type="text" id="unavaliable"><br/>

<button onclick="makeTb()">打印</button>
<button onclick="reset()">重置当前自习室布局</button>
<script type="text/javascript">
    function reset() {
        unAvaliableSeat = [];
        makeTb();
    }

    function makeTb(room) {
        var xmlhttp;
        var length;
        var wide;
        room = getEl("room").value;
        if (window.XMLHttpRequest)
        {
            // IE7+, Firefox, Chrome, Opera, Safari 浏览器执行代码
            xmlhttp=new XMLHttpRequest();
        }
        else
        {
            // IE6, IE5 浏览器执行代码
            xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
        }
        xmlhttp.onreadystatechange=function()
        {
            if (xmlhttp.readyState==4 && xmlhttp.status==200)
            {
                console.log(xmlhttp.responseText);
                var obj = JSON.parse(xmlhttp.responseText);
                length = obj.row;
                wide = obj.col;
                console.log(length+wide);
                for (var i = 0; i < length; i ++ ) {
                    var str = "";
                    for (var j = 0; j < wide; j ++ ) {
                        str += "e";
                    }
                    map.push(str);
                }
                console.log(map);
                $(document).ready(draw());
            }
        }
        xmlhttp.open("POST","http://123.57.63.212:8080/yike/seat/getRoomRowAndCol?roomid="+room,true);
        xmlhttp.send();
        // var wide = getEl('wide').value;
        // var length = getEl('length').value;
        var unavaliable = getEl('unavaliable').value;
        // console.log(wide);
        // console.log(length);
        console.log(unavaliable);
        unAvaliableSeat = unavaliable.split(",");
        console.log(unAvaliableSeat);

    }
    function getEl(id) {
        return document.getElementById(id);
    }
</script>
</body>
</html>