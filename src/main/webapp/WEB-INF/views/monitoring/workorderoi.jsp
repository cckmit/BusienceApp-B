<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>oi</title>

    <!-- Bootstrap -->
    <!-- 합쳐지고 최소화된 최신 CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">

    <!-- 부가적인 테마 -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">

    <link href="https://unpkg.com/tabulator-tables@4.9.3/dist/css/tabulator.min.css" rel="stylesheet">

    <link href="https://unpkg.com/tabulator-tables@4.8.4/dist/css/tabulator_modern.min.css" rel="stylesheet">

    <style>
        html, body {
            margin: 0;
            height: 100%;
            overflow: hidden;
        }

        .modal {
                text-align: center;
        }
        
        @media screen and (min-width: 768px) { 
                .modal:before {
                        display: inline-block;
                        vertical-align: middle;
                        content: " ";
                        height: 100%;
                }
        }
        
        .modal-dialog {
                display: inline-block;
                text-align: left;
                vertical-align: middle;
        }
    </style>
  </head>
  <body>
    <!-- Modal -->
    <div class="modal fade" id="myModal" role="dialog">
        <div class="modal-dialog modal-dialog-centered">
        
        <!-- Modal content-->
        <div class="modal-content">
            <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal">×</button>
            <h4 class="modal-title">설비 지정</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal">
                    <div class="form-group">
                      <label for="inputEmail3" class="col-sm-2 control-label">기기명</label>
                      <div class="col-sm-10">
                        <input type="email" class="form-control" id="inputEmail3" placeholder="">
                      </div>
                    </div>
                    <div class="form-group">
                      <label for="inputPassword3" class="col-sm-2 control-label">설비명</label>
                      <div class="col-sm-10">
                        <input type="password" class="form-control" id="inputPassword3" placeholder="">
                      </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button id="settingBtn" type="button" class="btn btn-default">Save</button>
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
            </div>
        </div>
        
        </div>
    </div>

    <div class="row" style="height: 100%; padding: 15px;">

        <div class="col-xs-6" style="height: 100%; padding: 15px;">
            <div style="height: 100%; border: solid;">
                <div id="workorder_tbl" style="height: 80%;"></div>
                <div id="example-table" style="border: solid;">
                    
                </div>
            </div>
        </div>

        <div class="col-xs-6" style="height: 100%; padding: 15px;">
            <div style="height: 100%; border: solid;">
                <nav class="navbar navbar-default">
                    <div class="container-fluid">
                      <div class="navbar-header">
                        <d class="navbar-brand" href="#">
                            작업지시번호 : 20210713-A01044-01 / 지시수량 : 300,000 / 생산수량 : 4,000,000 
                        </d>
                      </div>
                      <ul class="nav navbar-nav navbar-right">
                            <button id="modalbtn" alt="Brand" class="glyphicon glyphicon-cog" />
                      </ul>
                    </div>
                </nav>

                <div class="progress">
                    <div class="progress-bar" role="progressbar" aria-valuenow="60" aria-valuemin="0" aria-valuemax="100" style="width: 60%;">
                      60%
                    </div>
                </div>

                <div id="production_mgmt_tbl"></div>
            </div>
        </div>

    </div>

    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <!-- 합쳐지고 최소화된 최신 자바스크립트 -->
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="https://unpkg.com/tabulator-tables@4.9.3/dist/js/tabulator.min.js"></script>

    <script>
        $("#modalbtn").click(function(){
            $("#myModal").modal();
        });

        var workorder_tbl = new Tabulator("#workorder_tbl", {
            layout:"fitColumns",
            columns:[
                {title:"작업지시번호", field:"ono",headerSort:false,headerHozAlign:"center"},
                {title:"제품명", field:"equipname",headerSort:false,headerHozAlign:"center"},
                {title:"지시수량", field:"orderqty",headerSort:false,formatter:"money",hozAlign:"right", formatterParams: {precision: false},headerHozAlign:"center"},
                {title:"생산수량", field:"productqty",headerSort:false,formatter:"money",hozAlign:"right", formatterParams: {precision: false},headerHozAlign:"center"},
                {title:"작업상태", field:"productstat",headerSort:false, editor:"select", editorParams:{values:{"접수완료":"접수완료", "작업시작":"작업시작", "작업완료":"작업완료"}},headerHozAlign:"center"},
            ]
        });

        var tabledata = [
            {'ono':'20210713-A01044-01','equipname':'제오닉 밀폐 XE011','orderqty':'300000','productqty':'4000000','productstat':'접수완료'}
        ];
        workorder_tbl.setData(tabledata);

        // -------------------------------------------------------------------------------------------------------

        var production_mgmt_tbl = new Tabulator("#production_mgmt_tbl", {
            width:"80%",
            height:"85%",
            layout:"fitColumns",
            columns:[
                {title:"작업지시번호", field:"ono",headerSort:false,headerHozAlign:"center"},
                {title:"제품명", field:"equipname",headerSort:false,headerHozAlign:"center"},
                {title:"지시수량", field:"orderqty",headerSort:false,formatter:"money",hozAlign:"right", formatterParams: {precision: false},headerHozAlign:"center"},
                {title:"생산수량", field:"productqty",headerSort:false,formatter:"money",hozAlign:"right", formatterParams: {precision: false},headerHozAlign:"center"},
                {title:"누적수량", field:"eproductqty",headerSort:false,formatter:"money",hozAlign:"center", formatterParams: {precision: false},headerHozAlign:"center"},
            ]
        });

        var i = 0;
        var result = 0;
        setInterval(function request() {
            result += i;
            i++;
            tabledata2 = [
                {'ono':'20210713-A01044-01','equipname':'제오닉 밀폐 XE011','orderqty':'300000','productqty':i,'eproductqty':result}
            ];

            //production_mgmt_tbl.addData(tabledata2);

            production_mgmt_tbl.setSort([
                {column:"productqty", dir:"desc"} //sort by this first
            ]);
        }, 1000);
        
        var getCookie = function(name) {
        	var value = document.cookie.match('(^|;) ?' + name + '=([^;]*)(;|$)');
        	return value? value[2] : null;
        };
     	
        window.onload = function(){
        	// getCookie(변수이름)
            var is_expend = getCookie("expend");
            console.log("쿠키 is_expend변수에 저장된 값: "+is_expend);
        }
    </script>
  </body>
</html>