<!DOCTYPE html>
<html>

<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="../stylesheets/bootstrap.min1.css" rel="stylesheet" id="bootstrap-css">
    <link href="../stylesheets/loading-bar.css" rel="stylesheet">
    <title>Deposit Reconciliation</title>
    <style>
        ::-webkit-scrollbar {
            display: none;
        }

        .admin,
        .gateway {
            width: 75%;
            height: 40%;
        }

        .row {
            margin-top: 2%;
        }

        .vl {
            border-left: 1px solid black;
            height: 160px;
        }

        /* .loader {
            position: fixed;
            left: 0px;
            top: 0px;
            width: 100%;
            height: 100%;
            z-index: 9999;
            background: url('/images/loader.gif') 50% 50% no-repeat rgb(249, 249, 249);
            opacity: 0.8;
        } */
        .parsing1{
            color:grey;
            font-weight: bold;
        }
        .parsing {
            /* .progress_bar { */
            color:grey;
            position: fixed;
            left: 0px;
            top: 0px;
            width: 100%;
            height: 100%;
            z-index: 9999;
            /* background:  50% 50% no-repeat rgb(249, 249, 249); */
            font-weight: bold;
            margin: auto;
            opacity: 0.8;
        }

        .table-condensed {
            font-size: 12px;
        }


        .table-bordered tbody td,
        .table-bordered thead th {
            border-bottom-width: 2px;
            width: 20%;
        }

        .ldBar-label {
            position: absolute;
            top: -17%;
            right: -233%;

        }
    </style>

</head>

<body>
    <!-- <div class="loader" style="display: none"></div> -->
    <nav class="navbar navbar-light bg-light">
        <a href="/main_page">
            <h3 style="float:left;"><span class="badge badge-info">Back To Dashboard</span></h3>
        </a>
        <span class="navbar-brand mb-0 h1" style="margin:auto">Deposit Reconciliation</span>
        <a href="/users/reset_password" style="margin-right:1%">
            <h4 style="float:right;"><span class="badge badge-secondary">Reset Password</span></h4>
        </a>
        <a href="/signout">
            <h4 style="float:right;"><span class="badge badge-secondary">Logout</span></h4>
        </a>
    </nav>

    <div class="row">
        <div class="col-md-12" style="text-align: center;margin-bottom: -1%;margin-left: 1%;">
            <input type="button" class="btn btn-danger" onClick="window.location.reload();" value="Reset">
        </div>
    </div><input type="hidden" id="type_click" value="">
    <div class="row" style="margin-left: 1%;" id="file_upload_div">
        <div class="col-md-6">
            <form id="fileUploadForm" class="col-md-12" method="POST" action="#" enctype="multipart/form-data">
                <fieldset>
                    <div class="form-horizontal">
                        <div class="row">
                            <div class="col-md-12" style="margin:auto">

                                <label class="control-label col-md-4" for="filename" style="float:left;"><span> <b
                                            style="line-height: 44px;"> Admin
                                            File</b></span></label>
                                <div class="col-md-8" style="width: 55%;float:left;">
                                    <div class="input-group">
                                        <input type="hidden" id="filename" name="filename" value="">
                                        <input type="file" id="uploadedFile" name="file"
                                            class="form-control form-control-sm"
                                            accept=".csv, application/vnd.openxmlformats-officedocument.spreadsheetml.sheet, application/vnd.ms-excel">
                                        <div class="input-group-btn">
                                            <input type="submit" value="Upload"
                                                class="rounded-0 btn btn-primary 1_disable" 
                                                disabled>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-12 prog" style="margin:auto">
                                <div class="row">
                                    <div class="col-md-4"></div>
                                    <div class="col-md-8">
                                        <div class="row">
                                            <div class="col-md-8">
                                                <div class="progress_bar">
                                                    <div class="progressBar" data-value="0"
                                                        style="display: block;height: 20px;"></div>
                                                </div>
                                            </div>
                                            <div class="col-md-4">
                                                <div>
                                                    <i>
                                                        <h6 class="parsing" 
                                                            style="display: none;margin-top: 15%;margin-left: 30%;">
                                                            Parsing....</h6>
                                                    </i></div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row" id="admin_row" style="visibility:hidden;">
                            <div class="col-md-3"></div>
                            <div class="form-group col-md-3">
                                <label for="file_1">Transaction :</label>
                                <select class="form-control admin " id="file_1">

                                </select>
                            </div>
                            <div class="form-group col-md-3">
                                <label for="file_2">Amount :</label>
                                <select class="form-control admin" id="file_2">
                                </select>
                            </div>
                            <div class="form-group col-md-3">
                                <label for="file_3">Date :</label>
                                <select class="form-control admin" id="file_3">
                                </select>
                            </div>

                        </div>
                    </div>

                </fieldset>
            </form>
        </div>


        <div class="col-md-5 vl">
            <form id="fileUploadForm2" class="col-md-12" action="/deposit/upload_payment" method="POST"
                enctype="multipart/form-data">
                <fieldset>
                    <div class="form-horizontal">
                        <div class="row">
                            <div class="col-md-12">
                                <label class="control-label col-md-4 " style="float:left;" for="Payment"><span> <b
                                            style="line-height: 44px;"> Payment
                                            Gateway </b></span></label>
                                <div class="col-md-8" style="float:left">
                                    <div class="input-group">
                                        <input type="hidden" id="payment" name="payment" value="">
                                        <input type="file" id="paymentFile" name="file"
                                            class="form-control form-control-sm "
                                            accept=".csv, application/vnd.openxmlformats-officedocument.spreadsheetml.sheet, application/vnd.ms-excel">
                                        <div class="input-group-btn">
                                            <input type="submit" value="Upload"
                                                class="rounded-0 btn btn-primary 2_disable" 
                                                disabled>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-12 prog1">
                                <div class="row">
                                    <div class="col-md-4"></div>
                                    <div class="col-md-8">
                                        <div class="row">
                                            <div class="col-md-8">
                                                <div class="progress_bar1">
                                                    <div class="progressBar1" data-value="0"
                                                        style="display: block;height: 20px;"></div>
                                                </div>
                                            </div>
                                            <div class="col-md-4">
                                                <div>
                                                    <i>
                                                        <h6 class="parsing1" style="display: none;    margin-top: -15%;
                                                            margin-left: -96%;">
                                                            Parsing....</h6>
                                                    </i></div>
                                            </div>
                                        </div>
                                    </div>
                                </div>

                            </div>

                        </div>
                        <div class="row" id="gateway_row" style="visibility:hidden">
                            <div class="col-md-3"></div>
                            <div class="form-group  col-md-3">
                                <label for="pay_1"> Transaction :</label>
                                <select class="form-control gateway" id="pay_1">

                                </select>
                            </div>
                            <div class="form-group col-md-3">
                                <label for="pay_2">Amount :</label>
                                <select class="form-control gateway" id="pay_2">
                                </select>
                            </div>
                            <div class="form-group col-md-3">
                                <label for="pay_3">Date : </label>
                                <select class="form-control gateway" id="pay_3">
                                </select>


                            </div>
                        </div>
                </fieldset>
            </form>
        </div>
    </div>

    <input type="hidden" id="file1" value="">
    <input type="hidden" id="file2" value="">
    <input type="hidden" id="parse_status" value="">
    <hr style="width: 90%;margin:auto;margin-bottom: 20px;" id="hr">
    <div class="row reconcile_button">
            <div class="col-md-12 prog_text_div"
            style="color:grey; display: none;text-align: center;margin-left: 1%;margin-bottom: 1%;">
            
            <span class="prog_text">Preparing....</span></div>
        <div class="col-md-12" style="text-align: center; margin-bottom: -1%; margin-left: 1%;">
            <input type="button" class="btn btn-success " id="compare" value="Reconcile" >
        </div>
    </div>

    <div class="row " id="table_reconcile" style="display: none;margin-left: 0%;position:relative;">
        <div class="col-md-12" style="margin: auto;">
            <div class="table-responsive col-sm-6" style="float:left;" id="table_2">
                <div class='row'>
                    <div class='col-md-12'>
                        <h5 style="text-align: center">
                            <span class="badge badge-success">Admin Transactions</span>
                        </h5>

                        <div class="row">

                            <div class="col-md-4">
                                <button type="button" class="btn btn-sm btn-primary">
                                    Count: <span class="badge badge-light" id="total_match_transaction">0</span>
                                </button>
                            </div>
                            <div class="col-md-4">
                                <button type="button" class="btn btn-sm btn-primary" style="float:right;">
                                    Amount:<span data-toggle="tooltip" title="" id="reconcile_tooltip">
                                        <span class="badge badge-light" id="same_total">0</span></span>
                                </button>
                            </div>
                            <div class="col-md-4">
                                <span style="float: right">
                                    <a href="javascript:void(0)"  class="common_download" download="Admin.csv">
                                        <input type="button" class="btn-sm btn btn-Info" value="Download">
                                    </a>
                                </span>
                            </div>
                        </div>

                    </div>
                </div>
                <div class='row'>
                    <div class='col-md-12'>
                        <table class="table table-bordered table-condensed" style="margin-bottom: 0px;">

                            <thead>
                                <tr>
                                    <th scope="col">Transaction Id</th>
                                    <th scope="col">Date</th>
                                    <th scope="col">Amount</th>
                                    <th scope="col">Gateway</th>
                                    <th scope="col">Diffrence</th>
                                </tr>
                            </thead>
                        </table>
                    </div>
                    <div class='col-md-12' style="height:340px;overflow-y:auto;">
                        <table class="table table-bordered table-condensed">

                            <tbody id="diff_csv">

                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
            <div class="table-responsive col-sm-6 " style="float:left;" id="table_1">
                <div class='row'>
                    <div class='col-md-12'>
                        <h5 style="text-align: center">
                            <span class="badge badge-success"> Gateway Transactions</span>
                        </h5>
                        <div class="row">

                            <div class="col-md-4">
                                <button type="button" class="btn btn-sm btn-primary">
                                    Count : <span class="badge badge-light" id="total_unmatch_transaction1">0</span>
                                </button>
                            </div>
                            <div class="col-md-4">
                                <button type="button" class="btn btn-sm btn-primary" style="float:right;">
                                    Amount :<span data-toggle="tooltip" title="" id="gateway_tooltip">
                                        <span class="badge badge-light" id="gateway_total">0</span></span>
                                </button>
                            </div>

                            <div class="col-md-4">
                                <span style="float: right">
                                    <a href="javascript:void(0)" class="common_download" download="Gateway.csv">
                                        <input type="button" class="btn-sm btn btn-Info" value="Download">
                                    </a>
                                </span>
                            </div>
                        </div>
                    </div>
                </div>
                <div class='row'>
                    <div class='col-md-12'>
                        <table class="table table-bordered table-condensed" style="margin-bottom: 0px;">
                            <thead>
                                <tr>
                                    <th scope="col">Transaction Id</th>
                                    <th scope="col">Date</th>
                                    <th scope="col">Amount</th>
                                    <th scope="col">Admin</th>
                                    <th scope="col">Diffrence</th>
                                </tr>
                            </thead>

                        </table>
                    </div>
                    <div class='col-md-12' style="height:340px;overflow-y:auto;">
                        <table class="table table-bordered table-condensed">

                            <tbody id="gateway_csv">
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>

        </div>
    </div>
</body>
<script src="../javascripts/bootstrap.min1.js"></script>
<script src="../javascripts/jquery.min1.js"></script>

<script>

    // =======================file upload progress bar=========================

    $("#fileUploadForm").on('submit', (function (e) {
      console.log("inside")
        $(".parsing").css({
            "display": "block"
        });
        var singleFileUploadInput = document.querySelector('#uploadedFile');
        var files = singleFileUploadInput.files;
        e.preventDefault();
        var formData = new FormData();
        formData.append('file',files[0])
        $("#parse_status").val(0);
        $.ajax({
          	enctype: "multipart/form-data",
            url: '/uploadFile1',
            type: "POST",
            data: formData,
            cache: false,
            //======= contentType and processData are necessary for form data=====================

            contentType: false,
            processData: false,
            success: function (data) {
                if (data.length>0) {
 				var datahtml='';
                for(let i=0;i<data.length;i++){
               	 datahtml=datahtml+"<option value=" + data[i] + ">" + data[i] + "</option>"
                }
                    $(".admin").html(datahtml);
                    $("#parse_status").val(1)
                    $(".parsing").css({
                        "display": "none"
                    });
                   // $("#file1").val(data.path);
                    $('#msg').css({
                        "color": "green",
                        "font-size": "15px"
                    }).html('Admin File Uploaded Succesfully...!').fadeIn(200).fadeOut(3000);

                    $("#admin_row").css({
                        "visibility": "visible"
                    });

                } else {
                    $('#msg').css({
                        "color": "red",
                        "font-size": "15px"
                    }).html('Admin File NOt Uploaded...!').fadeIn(500);

                    $(".parsing").css({
                        "display": "none"
                    });

                }
            },
            error: function (err) {
                $(".parsing").css({
                    "display": "none"
                });
                $('#msg').css({
                    "color": "red",
                    "font-size": "15px"
                }).html('Admin File Not Uploaded...!').fadeIn(500);

            }
        })
    }))

    $("#fileUploadForm2").on('submit', (function (e) {
        $(".parsing1").css({
            "display": "block"
        });
       var singleFileUploadInput = document.querySelector('#paymentFile');
        var files = singleFileUploadInput.files;
        e.preventDefault();
        var formData = new FormData();
        formData.append('file',files[0])
        $("#parse_status").val(0);
        $.ajax({
            type: "POST",
            enctype: "multipart/form-data",
            url: '/uploadFile2',
            data: formData,
            contentType: false,
            processData: false,
            success: function (data) {
                if (data.length>0) {
                var datahtml='';
                for(let i=0;i<data.length;i++){
               	 datahtml=datahtml+"<option value=" + data[i] + ">" + data[i] + "</option>"
                }
                    $(".gateway").html(datahtml);
                    $(".parsing1").css({
                        "display": "none"
                    });
                    //$("#file2").val(data.path);
                    $('#msg').css({
                        "color": "green",
                        "font-size": "15px"
                    }).html('Gateway File Uploaded Succesfully...!').fadeIn(500).fadeOut(
                        3000);
                    $("#parse_status").val(1)
                    $("#gateway_row").css({
                        "visibility": "visible"
                    });

                } else {
                    $('#msg').css({
                        "color": "red",
                        "font-size": "15px"
                    }).html('Gateway File Not Uploaded...!').fadeIn(500);
                    $("#parse_status").val(1)
                    $(".parsing1").css({
                        "display": "none"
                    });
                }
            },
            error: function (err) {
                $(".parsing1").css({
                    "display": "none"
                });
                $('#msg').css({
                    "color": "red",
                    "font-size": "15px"
                }).html('Gateway File NOt Uploaded...!').fadeIn(500);

            }
        })

    }))

 $(".common_download").click(function () {
 let type=$(this).attr("download");
 alert(type);
	 $.ajax({
		data :{typeCsv:type},
		url:"/downloadCsv",
		type:"POST",
	 	success:function(data){
	 		if(data){
		 		var a= document.createElement("a");
		        document.body.appendChild(a);
		        a.style="display:none";
		        let blob = new Blob([data], {type: "octet/stream"}),
		       	url = window.URL.createObjectURL(blob);
		        a.href = url;
		        a.download = type;
		        a.click();
		        window.URL.revokeObjectURL(url);
		       }
	 	},
	 	error:function(err){
	 		console.log("error in download csv"+err);
	 	}
	 	});
 });
    $("#compare").click(function () {

        let col1 = $("#file_1 :selected").text();;
        let amount1 = $("#file_2 :selected").text();
        let date1 = $("#file_3 :selected").text();
        let col2 = $("#pay_1 :selected").text();
        let amount2 = $("#pay_2 :selected").text();
        let date2 = $("#pay_3 :selected").text();


        // also remove hidden file input value
        // let col1 = 'internalTransactionID';//x'Internal txn Id';//'Order Id';
        // let amount1 = 'amount';// 'Amt.';//
        // let date1 = 'txnInitiatedTime';//'Day of Payment init date';//
        // let col2 = 	'Sm Tramsaction Id';//'CashFree Reference Id';////'Merchant Reference Id';//
        // let amount2 ='Total Amount';// 'Transaction Amount';////
        // let date2 = 'Transaction date';//'Transaction Time';//
       	

        if (col1 == '') {
            alert("Enter column 1");
            exit();
        }
        if (amount1 == '') {
            alert("enter amount1")
            exit();
        }
        if (date1 == '') {
            alert("enter date1")
            exit();
        }
        if (col2 == '') {
            alert("enter col2")
            exit();
        }
        if (amount2 == '') {
            alert("enter amount2")
            exit();
        }
        if (date2 == '') {
            alert("enter date2")
            exit();
        }

        $.ajax({
            url: '/compare',
            type: "POST",
            async: true,
            data: {
                adminFileId: col1,
                adminFileAmount: amount1,
                adminFileDate: date1,
                gatewayFileId: col2,
                gatewayFileAmount: amount2,
                gatewayFileDate: date2,
              
            },
            success: function (data) {
              		
                if (data) {
                    $(".prog_text_div").css("display", "none");
                  
                    $("#table_reconcile").css({
                        "display": "block"
                    });
                    //$(".reconcile_button").remove();
                    $("#gateway_csv").html(data.gatewayHtmlArray);
                    $("#diff_csv").html(data.adminHtmlArray);
                    $("#same_total").html(data.adminAmount + " INR");
                    $("#gateway_total").html(data.gatewayAmount + " INR");
                    $("#total_unmatch_transaction1").html(data.gatewayCount);
                    $("#reconcile_tooltip").attr("title", data.adminAmountword);
                    $("#gateway_tooltip").attr("title", data.gatewayAmountword);
                   
                    $("#file_upload_div").css({
                        "display": "none"
                    })
                    $("#total_match_transaction").html(data.adminCount);
                    $("#compare").css({
                        "display": "none"
                    });
                    //$("#hr").css({
                    //    "display": "none"
                    //});

                }
            },
            error: function (err) {
                alert("errr", err);
            }
        })
    });
    var myVar

function calling_fun() {
    $(".prog_text_div").css("display", "block");
    $("#compare").css("display", "none");
    myVar = setInterval(start_progress, 1000)
}

function start_progress() {
    $.ajax({
        url: '/get_parsing_status', //'/withdraw/compare',
        type: 'GET',
        success: function (data) {
            // console.log(data)
            if (data.success) {
                if (data.total_percent == 0) {
                    $(".prog_text").html("Preparing....");
                }
                if (data.total_percent != 100 && data.total_percent!= 0) {
                    $(".prog_text").html("Progress "+parseInt(data.total_percent)+"%");
                } else if (data.total_percent == 100) {
                    clearInterval(myVar)
                } 
                
            }
        },error: function (err) {
            clearInterval(myVar);
            console.log(err, "errr")
        }
    })
}


    $(document).ready(
        function () {
            $('#uploadedFile').change(
                function () {
                    if ($(this).val()) {
                        $('.1_disable').attr('disabled', false);
                    }
                }
            );
            $("#paymentFile").change(
                function () {
                    if ($(this).val()) {
                        $('.2_disable').attr('disabled', false);
                    }
                }
            );
        });
</script>

</html>