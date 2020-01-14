<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>

<head>
    <meta http-equiv='Cache-Control' , content='no-store, no-cache, must-revalidate'>
    <meta http-equiv='Pragma' , content='no-cache'>
    <meta http-equiv='Expires' , content='-1'>
    <link href="/stylesheets/bootstrap.min.css" rel="stylesheet" />
    <title>${title}</title>
    <link rel='stylesheet' href='/stylesheets/style.css' />
    <style>
        .dropdown-content a {
            color: black;
            padding: 12px 16px;
            text-decoration: none;
            display: block;
        }

        .dropdown a:hover {
            background-color: black;
        }

        .day,
        .month,
        .year {
            width: inherit;
            display: inline-block;

        }

        .month,
        .year {
            margin-left: 2px;
        }
    </style>
</head>

<body>


    <div class="container">
        <p>&nbsp;</p>
        <p>&nbsp;</p>
        <div class='col-md-12 ' style="margin-left: -3%;width: 105%;">
            <div class='row '>
                <div class='col-md-12 ' style='margin-top: -50px;'>

                    <ul class='nav nav-pills topnav'>
                        <li class='active'><a data-toggle='tab' href='#vis_opgave1' id="changehiddenval">Employee</a>

                        </li>
                        <li><a data-toggle='tab' href='#rediger_opgave1' id="getAllVendor">Vendor</a>

                        </li>


                        <c:if test="${ user_status == 1}">
                            <li><a data-toggle='tab' href='#rep' id="getAllBeneficialOnClick">Payout File Generation</a>

                            </li>
                            <li><a data-toggle='tab' href='#bydatesearch' id="search">Report Search</a>
                            </li>
                          </c:if>
                       <c:if test="${ user_status != 1}">
                        <li><a data-toggle='tab' href='#bydatesearch' id="search">Report Search</a>
                        </li>
                        <li><a data-toggle='tab' style="visibility:hidden">Payout File Generation</a>

                        </li>
						</c:if>
                     
                        <li> <a class="btn  signout " style="margin-left: 285%;padding: 3px;background: #f2f2f2;
              color: black;margin-top: 5px;" href='javascript:void(0)' id="reset_password">Reset Password</a>
                        </li>
                        <li> <a class="btn  signout " style="margin-left: 570%;padding: 3px;background: #f2f2f2;
              color: black;margin-top: 5px;" href='/signout'>Signout</a>
                        </li>
                    </ul>
                </div>
            </div>
            <div style="text-align: center" id="msg"></div>
        </div>
        <div class="tab-content">
            <div id='vis_opgave1' class='tab-pane fade in active'>
                <div class='row'>


                    <!-- ================Employee table=========================================== -->
                    <table class="table table-responsive">
                        <c:if test="${ user_status == 1}">
                            <caption>
                                <a href="javascript:void(0)" class="btn  btn-success addemployee" type="Employee">
                                    Add Employee
                                </a>
                            </caption>
                        </c:if>
                        <thead>
                            <tr>
                                <th scope="col">#</th>
                                <th scope="col"></th>
                                <th scope="col">Acc.Name</th>
                                <th scope="col">Name</th>
                                <th scope="col">Location</th>
                                <th scope="col">Acc.No.</th>
                                <th scope="col">Ifsc.</th>
                                <th scope="col">Bank</th>
                                <c:if test="${ user_status == 1}">
                                    <th scope="col">Action</th>
                                </c:if>
                            </tr>
                        </thead>
                        <tbody id="row">
                            <c:if test="${not empty employeeData}">
                                <c:forEach var="employeeData" items="${employeeData}" varStatus="counter">

                                    <tr id="employee${ employeeData.empid }">
                                        <th scope="row" class="sequence${employeeData.empid }">${counter.count}</th>
                                        <td></td>
                                        <td>${ employeeData.account_name }</td>
                                        <td>${ employeeData.name }</td>
                                        <td>${ employeeData.location }</td>
                                        <td>${employeeData.account_no }</td>
                                        <td>${ employeeData.ifsc }</td>
                                        <td>${ employeeData.bank }</td>
                                        <c:if test="${ user_status == 1}">
                                            <td>
                                                <a href="javascript:void(0)" class="btn-info btn-sm employeeEdit "
                                                    empid="${ employeeData.empid}" type="Employee">Edit</a>&nbsp
                                                <a class="btn-danger btn-sm employeeDelete" href="javascript:void(0)"
                                                    empid2="${ employeeData.empid}">Delete</a></td>
                                        </c:if>
                                    </tr>
                                </c:forEach>
                            </c:if>
                        </tbody>
                    </table>

                    <!-- ================End Employee table=========================================== -->
                </div>
            </div>
            <div id='rediger_opgave1' class='tab-pane fade'>
                <div class='row'>

                    <!-- ================Vendor table=========================================== -->

                    <table class=" table table-responsive">
                        <c:if test="${ user_status == 1}">
                        <caption>
                            <a href="javascript:void(0)" class="btn  btn-success addvendor" type="Vendor">
                                Add Vendor
                            </a>
                        </caption>
                    </c:if>
                        <thead>
                            <tr>
                                <th scope="col">#</th>
                                <th scope="col"></th>
                                <th scope="col">Acc.Name</th>
                                <th scope="col">Name</th>
                                <th scope="col">Location</th>
                                <th scope="col">Acc.No.</th>
                                <th scope="col">Ifsc.</th>
                                <th scope="col">Bank</th>
                                <c:if test="${ user_status == 1}">
                                <th scope="col">Action</th>
                            </c:if>
                            </tr>
                        </thead>
                        <tbody id="vendorrow">

                        </tbody>
                    </table>
                    <!-- ================End Vendor table=========================================== -->
                </div>
            </div>

            <!-- ============================Report Generation========================== -->
            <div id='bydatesearch' class='tab-pane fade'>
                <div class='row'>

                    <table class="table table-responsive" id="entry-list1">
                        <caption>
                            <div class="dateDropdown  form-group " id="searchbydate">
                                <label for="searchbydate">Please enter the date:</label>
                                <input class="form-control serachbydt" name="searchbydate" id="selected_dt" type="text"
                                    placeholder="dd.mm.yyyy" />
                            </div>
                        </caption>
                        <thead>
                            <tr>
                                <th scope="col">#</th>
                                <th scope="col">Generated By</th>
                                <th scope="col">Date</th>
                                <th scope="col">Total Beneficiaries</th>
                                <th scope="col">Total Amount</th>
                                <th scope="col">Action</th>

                            </tr>
                        </thead>
                        <tbody id="report_row">

                        </tbody>
                    </table>

                </div>
            </div>

            <!-- ================================================================================== -->
            <div id='rep' class='tab-pane fade'>
                <div class='row'>
                    <!-- ================PFG table=========================================== -->
                    <table class="table table-responsive" id="entry-list">
                        <caption>
                            <!-- BeneficiaryModal -->
                            <a href="javascript:void(0)" class="btn btn-success" id="BeneficiaryRow">
                                Add Benficiary
                            </a><input type="hidden" id="my_type" value="Employee">
                            <div class="col-md-2 add_bene">
                                <select class="form-control" id="user_type1">
                                    <option value="Employee" selected="selected">Employee</option>
                                    <option value="Vendor">Vendor</option>
                                </select>
                            </div>
                            <div class="col-md-4" style="float:right;">
                                <a href="javascript:void(0)" class="btn btn-primary clear"
                                    onclick="clearBeneficiary()">Clear</a>
                                <a href="javascript:void(0)" class="btn btn-primary gen"
                                    onclick="generateFile()">Generate</a>
                                <input type="button" value="Download" id="pdf" class="btn btn-secondary">
                            </div>
                        </caption>
                        <thead>
                            <tr>
                                <th scope="col">#</th>
                                <th scope="col">Name</th>
                                <th scope="col">Acc No</th>
                                <th scope="col">Acc Name</th>
                                <th scope="col">Location</th>
                                <th scope="col">Ifsc</th>
                                <th scope="col">Bank</th>
                                <th scope="col">Comment</th>
                                <th scope="col">(₹)Amount</th>
                            </tr>
                        </thead>
                        <tbody id="benefit_row">

                        </tbody>
                    </table>

                    <!-- ================End PFG table=========================================== -->

                </div>
            </div>
        </div>
        <p>&nbsp;</p>
        <p>&nbsp;</p>
        <div id='content'></div>
<div id="elementH"></div>
    </div>
     <script src="http://html2canvas.hertzen.com/dist/html2canvas.min.js"></script>
    <script src="/javascripts/jquery.min.js"></script>
    <script src="/javascripts/bootstrap.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jspdf/1.5.3/jspdf.min.js"></script>
    <script>
        // ====================================PFG======================================================
        function generateFile() {
            if (!$(".for_generate_button")[0]) {
                exit();
            }

            $(".new2").remove();
            var arr = '';
            let count = 0;
            let sum_of_amount = 0;

            $('#benefit_row tr').not(':last').each(function () {
                let name = $(this).find(".nam").html();
                let acc_name = $(this).find(".ac_name").html();
                let acc_no = $(this).find(".ac_no").html();
                let locat = $(this).find(".locat").html();
                let bank = $(this).find(".ban1").html();
                let ifsc = $(this).find(".if").html();
                let comment1 = $(this).find(".com1").html();
                let comment2 = $(this).find(".com1").html();
                let amount = $(this).find(".amm").html();
                let start = new Date();
                let d, m, y;
                count = count + 1;
                d = start.getDate();
                m = start.getMonth() + 1;
                if (d < 10) {
                    d = '0' + d;
                }
                if (m < 10) {
                    m = '0' + m;
                }
                sum_of_amount = parseInt(sum_of_amount) + parseInt(amount);
                y = start.getFullYear();
                arr = arr + "N,," + acc_no + "," + amount + "," + acc_name + ",,,,,,,,," + comment1 + "," +
                    comment2 +
                    ",,,,,,,," + d + "/" + m + "/" + y + ",," + ifsc + "," + bank + "," + locat +
                    ",accounting@jungleegames.com" + " \n";
            });
            $.ajax({
                url: '/generatefile',
                type: 'POST',
                data: {
                    file_content: arr,
                    type: $("#my_type").val(),
                    count: count,
                    amount: sum_of_amount
                },
                success: function (data) {
                    if (data.success == true) {
                    var a= document.createElement("a");
                    document.body.appendChild(a);
                    a.style="display:none";
			        let blob = new Blob([data.data], {type: "octet/stream"}),
			         url = window.URL.createObjectURL(blob);
			        a.href = url;
			        a.download = data.msg;
			        a.click();
			        window.URL.revokeObjectURL(url);
              	  }
                },
                error: function (err) {
                    console.log(err, "errrore");
                }
            })
        }

        $('body').on("change", "#user_type1", function () {
            $("#my_type").val($(this).val());
            $(".new2").remove();
        });

        $('body').on('click', ".rm", function () {
            $("#b_user_click1").val($(this).html());
            $(".rm").remove();
            let name = $(this).html();
            let type = $('#my_type').val();

            $.ajax({
                url: '/get_beneficiary_details',
                dataType: 'json',
                type: "POST",
                data: {
                    type: type,
                    name: name
                },
                success: function (data) {
                    if (data.success == true) {
                        $("#b_id").val(data.msg._id)
                        $("#b_name").val(data.msg.name)
                        $("#b_location").val(data.msg.location);
                        $("#ben_account_name").val(data.msg.account_name);
                        $("#b_ifsc").val(data.msg.ifsc);
                        $("#b_bank").val(data.msg.bank);
                        $("#b_account").val(data.msg.account_no);
                        $("#type").val(data.type);
                    } else {
                        console.log("else data...", data)
                    }
                },
                error: function (err) {
                    console.log("err2=", err);
                }
            })

        })

        $('body').on('keyup', "#b_user_click1", function () {
            let val = $(this).val();
            $(".rm").remove();
            let type = $('#my_type').val();
            let names = '';
          
            $.ajax({
                url: '/get_user_names',
                dataType: 'json',
                type: "POST",
                data: {
                    type: type,
                    val: val
                },
                success: function (data) {
                    if (data.length>0) {
                    names ='';
                        for (let k = 0; k < data.length; k++) {
                            names = names + `<a href="#" class="form-control rm">` +data[k]
                                 + `</a>`
                        }console.log(names);
                        $("#b_user_name1").html(names);
                    } else {
                        console.log("No names found");
                    }
                },
                error: function (err) {
                    console.log("err3=", err);
                }
            })

        });


        // =============================================================================================


        $('body').on('click', ".ddn", function () {
            $(".ifs").val($(this).attr('value'));
            $(".ddn").remove();
            $.ajax({
                url: '/getBank',
                dataType: 'json',
                type: "POST",
                data: {
                    ifsc: $(".ifs").val()
                },
                success: function (data) {
                    if (data.success == true) {
                        $(".bank").val(data.msg.BANK);
                        $(".loca").val(data.msg.CITY);
                        $(".ddn").remove();
                    }
                },
                error: function (err) {
                    console.log("err4=", err);
                }
            })
        })

        $('body').on('keyup', ".ifs", function () {
            $(".ddn").remove();
            $.ajax({
                url: '/getBankDetails',
                dataType: 'json',
                type: "POST",
                data: {
                    ifsc: $(this).val()
                },
                success: function (data) {
                    if (data.success == true) {
                        $(".option").html(data.msg)
                    }
                },
                error: function (err) {
                    console.log("err5=", err);
                }
            })
        });


        $("#changehiddenval").click(function () {
            $(".new").remove();
            window.localStorage.clear();
        })


        $('body').on('click', ".addemployee", function () {
            $(".new").remove();
            let new_tr = `<tr class="new"><td>#</td><td></td><td><input type="text" class="form-control ls " id="acc_name_1" value=""></td>
      <td><input type="text" class="form-control ls" id="name_1" value=""></td><td><input type="text"  class="form-control ls loca" value=""></td>
      <td><input type="text" class="form-control ls" value=""></td><td><input type="text" class="form-control ls ifs"  name="ifsc" style="text-transform:uppercase" value="">
        <div style="position:absolute" class ="option" ></div></td>
      <td><input type="text" class="form-control ls bank" name="bank"  value=""></td> &nbsp &nbsp &nbsp &nbsp
      <td style="width: 14%;padding: 8px;">
        <a href="javascript:void(0)" class="btn btn-success  employeeAdd" > Save</a>
      <a href="javascript:void(0)" class="btn btn-warning backAdd" > Back</a></td>
       </tr>`
            if ($(".ls").val() != '') {
                $(".ls").removeClass();
                $("#row").prepend(new_tr);
            }
        });
        $('body').on('click', ".employeeAdd", function () {

            let name = $(".new").find('input').eq(1).val();
            let acc_name = $(".new").find('input').eq(0).val();
            let loc = $(".new").find('input').eq(2).val();
            let acc_no = $(".new").find('input').eq(3).val();
            let ifsc = $(".new").find('input').eq(4).val();
            let bank = $(".new").find('input').eq(5).val();
            // alert(name);
            $.ajax({
                url: '/saveEmployee',
                data: {
                    name: name,
                    location: loc,
                    account_name: acc_name,
                    bank: bank,
                    account_no: acc_no,
                    ifsc: ifsc
                },
                dataType: 'json',
                type: "POST",
                success: function (data) {
                    if (data.success == true) {
                        dt = `<tr id=` + "employee" + data.msg.empid + `>
                <th scope="row" >1</th>
                      <td>` + data.msg.empid + `</td>
                      <td>` + data.msg.account_name + `</td>
                      <td>` + data.msg.name + `</td>
                      <td>` + data.msg.location + `</td>
                      <td>` + data.msg.account_no + `</td>
                      <td>` + data.msg.ifsc + `</td>
                      <td>` + data.msg.bank + `</td>
                      <td>
                        <a href="javascript:void(0)" class="btn-info btn-sm employeeEdit"  empid=` + data.msg.empid + ` type="Employee" >Edit</a>&nbsp   
                        <a class="btn-danger btn-sm employeeDelete" href="javascript:void(0)"  empid2=` + data.msg
                            .empid + `>Delete</a></td>
                    </tr>`
                        $("#row").append(dt);
                        $('#msg').css({
                            "color": "green",
                            "font-size": "15px"
                        }).html(data.data).fadeIn(500).fadeOut(8000);
                        location.reload();
                    } else {
                        $('#msg').css({
                            "color": "red",
                            "font-size": "15px"
                        }).html(data.data).fadeIn(500).fadeOut(8000);
                    }
                },
                error: function (err) {
                    $('#msg').css({
                        "color": "red",
                        "font-size": "15px"
                    }).html(err.msg).fadeIn(500).fadeOut(8000);
                }
            })
        });


        $('body').on('keyup', "#acc_name_1", function () {
            $("#name_1").val($(this).val());
        })
        $('body').on('keyup', "#acc_name_2", function () {
            $("#name_2").val($(this).val());
        })
        $('body').on('click', ".backAdd", function () {
            $("#row").find('tr:eq(0)').remove();
        });
        $('body').on('click', ".employeeEdit", function () {

            if (localStorage.getItem("doc") !== null) {
                let doc;
                doc = localStorage.getItem("doc")
                doc = JSON.parse(doc)
                dt = `<tr id=` + "employee" + doc.id + `>
                      <th scope="row" class="sequence` + doc.id + `">` + doc.seq + `</th>
                      <td></td>
                      <td>` + doc.acc_name + `</td>
                      <td>` + doc.name + `</td>
                      <td>` + doc.loc + `</td>
                      <td>` + doc.acc_no + `</td>
                      <td>` + doc.ifsc + `</td>
                      <td>` + doc.bank + `</td>
                      <td>
                        <a href="javascript:void(0)" class="btn-info btn-sm employeeEdit "  empid=` + doc.id + `  >Edit</a>&nbsp   
                        <a class="btn-danger btn-sm employeeDelete" href="javascript:void(0)"  empid2=` + doc.id + `>Delete</a></td>
                    </tr>`
                $("#employee" + doc.id).replaceWith(dt);
                localStorage.clear()
            }
            let id = $(this).attr("empid");
            let current_tr = "employee" + id;
            let seq = $("#" + current_tr).find('th:eq(0)').html();
            let ids = $(this).attr("empid"); //$("#" + current_tr).find('td:eq(0)').html();
            let name = $("#" + current_tr).find('td:eq(2)').html();
            let acc_name = $("#" + current_tr).find('td:eq(1)').html();
            let loc = $("#" + current_tr).find('td:eq(3)').html();
            let acc_no = $("#" + current_tr).find('td:eq(4)').html();
            let ifsc = $("#" + current_tr).find('td:eq(5)').html();
            let bank = $("#" + current_tr).find('td:eq(6)').html();

            let doc1 = {
                "id": id,
                "seq": seq,
                "ids": ids,
                "name": name,
                "acc_name": acc_name,
                "loc": loc,
                "acc_name": acc_name,
                "loc": loc,
                "acc_no": acc_no,
                "ifsc": ifsc,
                "bank": bank
            }
            doc1 = JSON.stringify(doc1)
            localStorage.setItem("doc", doc1);

            let new_tr = `<td>` + seq + `</td><td></td><td><input type="text" class="form-control" value="` +
                acc_name + `"></td>
      <td><input type="text" class="form-control" value="` + name +
                `"></td><td><input type="text" class="form-control" value="` + loc + `"></td>
      <td><input type="text" class="form-control" value="` + acc_no +
                `"></td><td><input type="text" class="form-control" value="` + ifsc + `"></td>
      <td><input type="text" class="form-control"  value="` + bank + `"></td>
      <td style="width: 12%;"><a href="javascript:void(0)" class="btn-info btn-sm saveEdit" saveid1=` + ids + `>Save</a>&nbsp   
      <a class="btn-primary btn-sm backEdit" href="javascript:void(0)"  backid1=` + ids + `>Back</a></td>`

            $("#" + current_tr).html(new_tr);
        });

        $('body').on('click', ".backEdit", function () {
            doc = localStorage.getItem("doc")
            doc = JSON.parse(doc)
            dt = `<tr id=` + "employee" + doc.id + `>
                      <th scope="row" class="sequence` + doc.id + `">` + doc.seq + `</th>
                      <td></td>
                      <td>` + doc.acc_name + `</td>
                      <td>` + doc.name + `</td>
                      <td>` + doc.loc + `</td>
                      <td>` + doc.acc_no + `</td>
                      <td>` + doc.ifsc + `</td>
                      <td>` + doc.bank + `</td>
                      <td>
                        <a href="javascript:void(0)" class="btn-info btn-sm employeeEdit "  empid=` + doc.id + `  >Edit</a>&nbsp   
                        <a class="btn-danger btn-sm employeeDelete" href="javascript:void(0)"  empid2=` + doc.id + `>Delete</a></td>
                    </tr>`
            $("#employee" + doc.id).replaceWith(dt);
        })

        $('body').on('click', ".saveEdit", function () {
            let id = $(this).attr("saveid1");
            let current_tr = "employee" + $(this).attr("saveid1");
            let name = $("#" + current_tr).find('input').eq(1).val();
            let acc_name = $("#" + current_tr).find('input').eq(0).val();
            let loc = $("#" + current_tr).find('input').eq(2).val();
            let acc_no = $("#" + current_tr).find('input').eq(3).val();
            let ifsc = $("#" + current_tr).find('input').eq(4).val();
            let bank = $("#" + current_tr).find('input').eq(5).val();
            $.ajax({
                url: '/updateEmployeeData/' + id,
                data: {
                    empid: id,
                    name: name,
                    account_name: acc_name,
                    location: loc,
                    bank: bank,
                    account_no: acc_no,
                    ifsc: ifsc
                },
                dataType: 'json',
                type: "POST",
                success: function (data) {
                    if (data.success == true) {
                    alert(data.msg.empid);
                console.log(data+":::"+data.msg.empid+":::");
                        dt = `<tr id=` + "employee" + data.msg.empid + `>
                      <th scope="row" class="sequence` + $(".sequence" + data.msg.empid).html() + `">` + $(
                                ".sequence" + data.msg.empid).html() + `</th>
                      <td>` + data.msg.empid + `</td>
                      <td>` + data.msg.account_name + `</td>
                      <td>` + data.msg.name + `</td>
                      <td>` + data.msg.location + `</td>
                      <td>` + data.msg.account_no + `</td>
                      <td>` + data.msg.ifsc + `</td>
                      <td>` + data.msg.bank + `</td>
                      <td>
                        <a href="javascript:void(0)" class="btn-info btn-sm employeeEdit"  empid=` + data.msg.empid + ` type="Employee" >Edit</a>&nbsp   
                        <a class="btn-danger btn-sm employeeDelete" href="javascript:void(0)"  empid2=` + data.msg
                            .empid + `>Delete</a></td>
                    </tr>`
                        $("#employee" + data.msg.empid).replaceWith(dt);
                        $('#msg').css({
                            "color": "green",
                            "font-size": "15px"
                        }).html("Success").fadeIn(500).fadeOut(1000);

                        location.reload();
                    } else {
                        $('#msg').css({
                            "color": "red",
                            "font-size": "15px"
                        }).html(data.data).fadeIn(500).fadeOut(8000);
                    }
                },
                error: function (err) {
                    $('#msg').css({
                        "color": "red",
                        "font-size": "15px"
                    }).html(err.data).fadeIn(500).fadeOut(8000);
                }
            })

        })

        $('body').on('click', ".employeeDelete", function () {
            if (!confirm('Do you want to delete..?')) {
                exit();
            }
            let id = $(this).attr("empid2");
            $.ajax({
                url: '/deleteEmployee/' + id,
                dataType: 'json',
                type: "GET",
                success: function (data) {
                    if (data) {
                        $('#employee' + id).remove();
                        location.reload();
                    } else {
                        // alert(data.msg);
                    }
                },
                error: function (err) {
                    alert(err);
                    console.log(err);
                }
            })

        });

        // ==========================================Vendor=======================================

        $('body').on('click', ".addvendor", function () {
            $(".new1").remove();
            let new_tr = `<tr class="new1"><td>#</td><td></td><td><input type="text" class="form-control ls1" value="" id="acc_name_2"></td>
      <td><input type="text" class="form-control ls1" id="name_2" value=""></td><td><input type="text" class="form-control ls1 loca" value=""></td>
      <td><input type="text" class="form-control ls1" value=""></td><td><input type="text" class="form-control ls1 ifs"  name="ifsc" style="text-transform:uppercase" value="">
        <div style="position:absolute" class="option"></div>  </td>
      <td><input type="text" class="form-control ls1 bank" name="bank"  value=""></td> &nbsp &nbsp &nbsp &nbsp
      <td style="width: 14%;padding: 8px;">
        <a href="javascript:void(0)" class="btn btn-success  vendorAdd" > Save</a>
      <a href="javascript:void(0)" class="btn btn-warning backAdd1" > Back</a></td>
       </tr>`
            if ($(".ls1").val() != '') {
                $(".ls1").removeClass();
                $("#vendorrow").prepend(new_tr);
            }
        });

        $('body').on('click', ".vendorAdd", function () {

            let name = $(".new1").find('input').eq(1).val();
            let acc_name = $(".new1").find('input').eq(0).val();
            let loc = $(".new1").find('input').eq(2).val();
            let acc_no = $(".new1").find('input').eq(3).val();
            let ifsc = $(".new1").find('input').eq(4).val();
            let bank = $(".new1").find('input').eq(5).val();
            $.ajax({
                url: '/saveVendor',
                data: {
                    name: name,
                    location: loc,
                    account_name: acc_name,
                    bank: bank,
                    account_no: acc_no,
                    ifsc: ifsc
                },
                dataType: 'json',
                type: "POST",
                success: function (data) {
                    if (data.success == true) {
                        let k = parseInt($('#vendorrow tr:last-child th:first-child').html()) + 1;
                        dt = `<tr id=` + "vendor" + data.msg.vendorid + `>
                <th scope="row" >` + k + `</th>
                      <td></td>
                      <td>` + data.msg.account_name + `</td>
                      <td>` + data.msg.name + `</td>
                      <td>` + data.msg.location + `</td>
                      <td>` + data.msg.account_no + `</td>
                      <td>` + data.msg.ifsc + `</td>
                      <td>` + data.msg.bank + `</td>
                      <td>
                        <a href="javascript:void(0)" class="btn-info btn-sm vendorEdit"  vendorid=` + data.msg
                            .vendorid + ` type="Vendor" >Edit</a>&nbsp   
                        <a class="btn-danger btn-sm vendorDelete" href="javascript:void(0)"  vendorid2=` + data.msg
                            .vendorid + `>Delete</a></td>
                    </tr>`

                        $("#vendorrow").append(dt);
                        //  $(".new").remove();
                        $('#msg').css({
                            "color": "green",
                            "font-size": "15px"
                        }).html(data.msg).fadeIn(500).fadeOut(8000);
                        $(".new1").remove();
                    } else {
                        $('#msg').css({
                            "color": "red",
                            "font-size": "15px"
                        }).html(data.data).fadeIn(500).fadeOut(8000);
                    }
                },
                error: function (err) {
                    $('#msg').css({
                        "color": "red",
                        "font-size": "15px"
                    }).html(err.data).fadeIn(500).fadeOut(8000);
                }
            })
        });

        $('body').on('click', ".backAdd1", function () {
            $("#vendorrow").find('tr:eq(0)').remove();
        });

        $('body').on('click', ".vendorDelete", function () {
            if (!confirm('Do you want to delete..?')) {
                exit();
            }
            let id = $(this).attr("vendorid2");
            $.ajax({
                url: '/deleteVendor/' + id,
                dataType: 'json',
                type: "GET",
                success: function (data) {
                    if (data) {
                        $('#vendor' + id).remove();

                    } else {
                        // alert(data.msg);
                    }
                },
                error: function (err) {
                    alert(err);
                    console.log(err);
                }
            })
        })
        $("#getAllVendor").click(function () {
            window.localStorage.clear();
            $.ajax({
                url: '/getAllVendor',
                dataType: 'json',
                type: 'GET',
                success: function (data) {

                    if (data.success == true) {
                        let k = 0;
                        let dt = '';
                        for (let i = 0; i < data.msg.length; i++) {
                            k = i + 1;

                            dt = dt + `<tr id=` + "vendor" + data.msg[i].vendorid + `>
                <th scope="row" class="sequence1` + data.msg[i].vendorid + `" >` + k + `</th>
                      <td></td>
                      <td>` + data.msg[i].account_name + `</td>
                      <td>` + data.msg[i].name + `</td>
                      <td>` + data.msg[i].location + `</td>
                      <td>` + data.msg[i].account_no + `</td>
                      <td>` + data.msg[i].ifsc + `</td>
                      <td>` + data.msg[i].bank + `</td>
                      <c:if test="${ user_status == 1}"> 
    
                      <td>
                        <a href="javascript:void(0)" class="btn-info btn-sm vendorEdit"  vendorid=` + data.msg[i]
                                .vendorid + ` type="Vendor" >Edit</a>&nbsp   
                        <a class="btn-danger btn-sm vendorDelete" href="javascript:void(0)"  vendorid2=` + data.msg[i]
                                .vendorid + `>Delete</a></td>
                    </c:if>
                    </tr>`
                        }

                        $("#vendorrow").html(dt);
                    } else {
                        $("#vendorrow").css({
                            "color": "red",
                            "font-size": "15px",
                            "text-align": "center"
                        }).html(data.data);
                    }
                },
                error: function (err) {
                    $("#vendorrow").css({
                        "color": "red",
                        "font-size": "15px",
                        "text-align": "center"
                    }).html(err.data);
                }
            });
        });


        $('body').on('click', ".backEdit1", function () {
            doc = localStorage.getItem("doc1")
            doc = JSON.parse(doc)
            dt = `<tr id=` + "vendor" + doc.id + `>
                      <th scope="row" class="sequence` + doc.id + `">` + doc.seq + `</th>
                      <td></td>
                      <td>` + doc.acc_name + `</td>
                      <td>` + doc.name + `</td>
                      <td>` + doc.loc + `</td>
                      <td>` + doc.acc_no + `</td>
                      <td>` + doc.ifsc + `</td>
                      <td>` + doc.bank + `</td>
                      <td>
                        <a href="javascript:void(0)" class="btn-info btn-sm vendorEdit "  vendorid=` + doc.id + `  >Edit</a>&nbsp   
                        <a class="btn-danger btn-sm vendorDelete" href="javascript:void(0)"  vendorid2=` + doc.id + `>Delete</a></td>
                    </tr>`
            $("#vendor" + doc.id).replaceWith(dt);
        })
        $('body').on('click', ".vendorEdit", function () {
            if (localStorage.getItem("doc1") !== null) {
                let doc;
                doc = localStorage.getItem("doc1")
                doc = JSON.parse(doc)
                dt = `<tr id=` + "vendor" + doc.id + `>
                      <th scope="row" class="seq` + doc.id + `">` + doc.seq + `</th>
                      <td></td>
                      <td>` + doc.acc_name + `</td>
                      <td>` + doc.name + `</td>
                      <td>` + doc.loc + `</td>
                      <td>` + doc.acc_no + `</td>
                      <td>` + doc.ifsc + `</td>
                      <td>` + doc.bank + `</td>
                      <td>
                        <a href="javascript:void(0)" class="btn-info btn-sm vendorEdit "  vendorid=` + doc.id + `  >Edit</a>&nbsp   
                        <a class="btn-danger btn-sm vendorDelete" href="javascript:void(0)"  vendorid2=` + doc.id + `>Delete</a></td>
                    </tr>`
                $("#vendor" + doc.id).replaceWith(dt);
                localStorage.clear()
            }
            let id = $(this).attr("vendorid");
            let current_tr = "vendor" + id;
            let seq = $("#" + current_tr).find('th:eq(0)').html();
            let ids = $(this).attr("vendorid");
            let name = $("#" + current_tr).find('td:eq(2)').html();
            let acc_name = $("#" + current_tr).find('td:eq(1)').html();
            let loc = $("#" + current_tr).find('td:eq(3)').html();
            let acc_no = $("#" + current_tr).find('td:eq(4)').html();
            let ifsc = $("#" + current_tr).find('td:eq(5)').html();
            let bank = $("#" + current_tr).find('td:eq(6)').html();

            let doc1 = {
                "id": id,
                "seq": seq,
                "ids": ids,
                "name": name,
                "acc_name": acc_name,
                "loc": loc,
                "acc_name": acc_name,
                "loc": loc,
                "acc_no": acc_no,
                "ifsc": ifsc,
                "bank": bank
            }
            doc1 = JSON.stringify(doc1)
            localStorage.setItem("doc1", doc1);

            let new_tr = `<td>` + seq + `</td><td></td><td><input type="text" class="form-control" value="` +
                acc_name + `"></td>
      <td><input type="text" class="form-control" value="` + name +
                `"></td><td><input type="text" class="form-control" value="` + loc + `"></td>
      <td><input type="text" class="form-control" value="` + acc_no +
                `"></td><td><input type="text" class="form-control" value="` + ifsc + `" style="text-transform:uppercase"></td>
      <td><input type="text" class="form-control" value="` + bank + `"></td>
      <td style="width: 12%;"><a href="javascript:void(0)" class="btn-info btn-sm saveEdit1" saveid1=` + ids + `>Save</a>&nbsp   
      <a class="btn-primary btn-sm backEdit1" href="javascript:void(0)"  backid2=` + ids + `>Back</a></td>`

            $("#" + current_tr).html(new_tr);
        });
        $('body').on('click', ".saveEdit1", function () {
            let id = $(this).attr("saveid1");
            let current_tr = "vendor" + $(this).attr("saveid1");
            let seq = $("#" + current_tr).find('td:eq(0)').html();
            let name = $("#" + current_tr).find('input').eq(1).val();
            let acc_name = $("#" + current_tr).find('input').eq(0).val();
            let loc = $("#" + current_tr).find('input').eq(2).val();
            let acc_no = $("#" + current_tr).find('input').eq(3).val();
            let ifsc = $("#" + current_tr).find('input').eq(4).val();
            let bank = $("#" + current_tr).find('input').eq(5).val();
            $.ajax({
                url: '/updateVendorData/' + id,
                data: {
                    vendorid: id,
                    name: name,
                    account_name: acc_name,
                    location: loc,
                    bank: bank,
                    account_no: acc_no,
                    ifsc: ifsc
                },
                dataType: 'json',
                type: "POST",
                success: function (data) {
                    if (data.success == true) {
                        dt = `<tr id=` + "vendor" + data.msg.vendorid + `>
                      <th scope="row" class="sequence` + $(".sequence" + data.msg.vendorid).html() + `">` + seq + `</th>
                      <td></td>
                      <td>` + data.msg.account_name + `</td>
                      <td>` + data.msg.name + `</td>
                      <td>` + data.msg.location + `</td>
                      <td>` + data.msg.account_no + `</td>
                      <td>` + data.msg.ifsc + `</td>
                      <td>` + data.msg.bank + `</td>
                      <td>
                        <a href="javascript:void(0)" class="btn-info btn-sm vendorEdit"  vendorid=` + data.msg
                            .vendorid + ` type="Vendor" >Edit</a>&nbsp   
                        <a class="btn-danger btn-sm vendorDelete" href="javascript:void(0)"  vendorid2=` + data.msg
                            .vendorid + `>Delete</a></td>
                    </tr>`
                        $("#vendor" + data.msg.vendorid).replaceWith(dt);
                        $('#msg').css({
                            "color": "green",
                            "font-size": "15px"
                        }).html("Success").fadeIn(500).fadeOut(1000);

                    } else {
                        $('#msg').css({
                            "color": "red",
                            "font-size": "15px"
                        }).html(data.msg).fadeIn(500).fadeOut(8000);
                    }
                },
                error: function (err) {
                    $('#msg').css({
                        "color": "red",
                        "font-size": "15px"
                    }).html(err.msg).fadeIn(500).fadeOut(8000);
                }
            })

        })

        // ===========================================================================================

        // =================================Beneficiary =================================================
        $('body').on('click', "#BeneficiaryRow", function () {
            $(".new2").remove();
            let new_tr = `<tr class="new2"><td>#</td>
      <td>
        <input type="text" class="form-control ls2" value="" id="b_user_click1">
        <div style="position: absolute;z-index:99;" id="b_user_name1"></div>
      </td><input type="hidden" id="b_id">
      <td><input type="text" class="form-control ls2" value="" id="b_account" readonly></td>
      <td><input type="text" class="form-control ls2" value="" id="ben_account_name" readonly></td>
      <td><input type="text" class="form-control ls2" value="" id="b_location" readonly></td>
      <td><input type="text" class="form-control ls2" value="" id="b_ifsc"  style="text-transform:uppercase" readonly></td>
      <td><input type="text" class="form-control ls2" value="" id="b_bank" readonly></td>
      <td> <input type="text" class="form-control ls2" value=""  id="comment1" required> </td>
      <td style="width: 10%;"><input type="number" class="form-control ls2"  value="" id="amount" ></td> &nbsp &nbsp &nbsp &nbsp
      <td style="width: 14%;padding: 6px;">
        <a href="javascript:void(0)" class="btn btn-success  beneficiaryAdd" > Save</a>
      <a href="javascript:void(0)" class="btn btn-warning backAdd2" > Back</a></td>
       </tr>`
            if ($(".ls2").val() != '') {
                $(".ls2").removeClass();
                $("#benefit_row").prepend(new_tr);
            }
        });
        $('body').on('click', ".beneficiaryAdd", function () {
            let type = $("#my_type").val();
            let name = $(".new2").find('input').eq(0).val();
            let acc_no = $(".new2").find('input').eq(2).val();
            let acc_name = $(".new2").find('input').eq(3).val();
            let location = $(".new2").find('input').eq(4).val();
            let ifsc = $(".new2").find('input').eq(5).val();
            let bank = $(".new2").find('input').eq(6).val();
            let comment1 = $(".new2").find('input').eq(7).val();
            let comment2 = $(".new2").find('input').eq(7).val();
            let amount = $(".new2").find('input').eq(8).val();
            if (amount == '') {
                $('#msg').css({
                    "color": "red",
                    "font-size": "15px"
                }).html("Enter valid amount").fadeIn(500).fadeOut(8000);
                exit();
            }
            if (name == '') {
                $('#msg').css({
                    "color": "red",
                    "font-size": "15px"
                }).html("Enter valid Name").fadeIn(500).fadeOut(8000);
                exit();
            }
            if (acc_no == '') {
                $('#msg').css({
                    "color": "red",
                    "font-size": "15px"
                }).html("Enter valid Account Number").fadeIn(500).fadeOut(8000);
                exit();
            }
            if (acc_name == '') {
                $('#msg').css({
                    "color": "red",
                    "font-size": "15px"
                }).html("Enter valid Account Name").fadeIn(500).fadeOut(8000);
                exit();
            }
            if (location == '') {
                $('#msg').css({
                    "color": "red",
                    "font-size": "15px"
                }).html("Enter valid Location").fadeIn(500).fadeOut(8000);
                exit();
            }
            if (ifsc == '') {
                $('#msg').css({
                    "color": "red",
                    "font-size": "15px"
                }).html("Enter valid Ifsc").fadeIn(500).fadeOut(8000);
                exit();
            }
            if (bank == '') {
                $('#msg').css({
                    "color": "red",
                    "font-size": "15px"
                }).html("Enter valid Bank").fadeIn(500).fadeOut(8000);
                exit();
            }
            if (comment1 == '') {
                $('#msg').css({
                    "color": "red",
                    "font-size": "15px"
                }).html("Enter Comment").fadeIn(500).fadeOut(8000);
                exit();
            }
            $.ajax({
                url: '/verify_beneficiary',
                data: {
                    name: name,
                    type: type
                },
                type: "post",
                success: function (data) {
                    if (data.success == true) {
                        if (type == "Employee") {
                            t = 'style="color:black;"';
                        } else if (type == "Vendor") {
                            t = 'style="color:red;"';
                        }
                        let current_time = Date.now();
                        let k = parseInt($('#benefit_row tr').last().prev().find("th").html()) + 1;
                        if (isNaN(k)) {
                            k = 1
                        }
                        dt = `<tr ` + t + ` id= "benefit` + current_time + `" class="for_generate_button">
                   <th scope="row" >` + k + `</th>
                      <td class="nam">` + name + `</td>
                      <td class="ac_no">` + acc_no + `</td>
                      <td class="ac_name">` + acc_name + `</td>
                      <td class="locat">` + location + `</td>
                      <td class="if">` + ifsc + `</td>
                      <td class="ban1">` + bank + `</td>
                      <td class="com1">` + comment1 + `</td>
                      <td class="amm">` + amount + `</td>
                      <td>

                        <a class="btn-danger btn-sm benefitDelete" href="javascript:void(0)"  benefitd2="` +
                            current_time + `">Delete</a></td>
                    </tr>`
                        let new1 = parseInt(amount) + parseInt($("#amt").val())
                        $("#amt").val(new1);
                        $(".visibleamt").html(new1);
                        $("#benefit_row").find('tr:last').prev().after(dt);
                        // $(".new2").remove();
                        $(".ls2").val(''); //clear all text field after save 
                        $('#msg').css({
                            "color": "green",
                            "font-size": "15px"
                        }).html("Success").fadeIn(500).fadeOut(8000);

                    } else {
                        $('#msg').css({
                            "color": "red",
                            "font-size": "15px"
                        }).html("Enter valid Name").fadeIn(500).fadeOut(8000);
                    }

                },
                error: function (err) {
                    console.log("error in chek name", err.message)
                }
            })

        });

        $('body').on('click', "#getAllBeneficialOnClick", function () {
            window.localStorage.clear();
            if ($("#benefit_row tr").length == 0) {
                s2 = `<tr> <th scope="row" colspan="8" style="text-align:center" >Total</th>
                          <td colspan="2" class="visibleamt">0 INR</td></tr>
                        <input type="hidden" id="amt" value="0">`
                $("#benefit_row").append(s2);
            }

        });

        $('body').on('click', ".benefitDelete", function () {
            if (!confirm('Do you want to delete..?')) {
                exit();
            }
            let id = $(this).attr("benefitd2");
            let amt = $("#amt").val();
            del = parseInt($('#benefit' + id).find("td:eq(7)").text());
            alert(del)
            $(".visibleamt").html(amt - del);
            $("#amt").val(amt - del)
            $('#benefit' + id).remove();
        });

        $('body').on('click', ".backAdd2", function () {
            $("#benefit_row").find('tr:eq(0)').remove();
        });

        $('body').on('change', "#searchbydate", function () {

            // alert($('.month option:selected').index()+1);
            selected_day = $('.day').val();
            selected_month = $('.month option:selected').index() + 1
            selected_year = $('.year').val();
            if (selected_day < 10) {
                selected_day = '0' + selected_day;
            }
            if (selected_month < 10) {
                selected_month = '0' + selected_month;
            }
            $.ajax({
                url: '/get_beneficial_by_date',
                type: 'POST',
                data: {
                    selected_day: selected_day,
                    selected_month: selected_month,
                    selected_year: selected_year,
                },
                success: function (data) {
                if(data.success){
                    let tr = '';
                    total_amt = 0;
                    sequence = 0;
                    for (let m = 0; m < data.msg.length; m++) {
                        sequence = sequence + 1;
                        let generate_date = convert(data.msg[m].date);
                        tr = tr + `<tr id="` + data.msg[m]._id + `">
			            <th scope="row" >` + sequence + `</th>
			            <td>` + data.data + `</td>
			            <td>` + generate_date + `</td>
			            <td>` + data.msg[m].count + `</td>
			            <td>` + data.msg[m].amount + `</td>
			            <td ids="` + data.msg[m]._id + `" class="get_my_report btn btn-primary btn-sm">CSV</td>
			            <td ids1="` + data.msg[m]._id + `" style="margin-left:2px;" class="get_my_pdfreport btn btn-primary btn-sm">PDF</td>
			            </tr>`
                    }
                    $("#report_row").html(tr);
                   }
                },
                error: function (err) {
                    console.log(err, "error in date ajax");
                }
            })
        })

        $('body').on('click', ".get_my_report", function () {
            $.ajax({
                url: '/get_myreport',
                type: 'POST',
                data: {
                    id: $(this).attr("ids")
                },
                success: function (data) {
                    if (data.success == true) {
                    var a= document.createElement("a");
                    document.body.appendChild(a);
                    a.style="display:none";
			        let blob = new Blob([data.data], {type: "octet/stream"}),
			       	url = window.URL.createObjectURL(blob);
			        a.href = url;
			        a.download = data.msg;
			        a.click();
			        window.URL.revokeObjectURL(url);
                    }
                },
                error(err) {
                    console.log("error in get my report", err);
                }
            });
        })

        function convert(str) {
            var date = new Date(str),
                mnth = ("0" + (date.getMonth() + 1)).slice(-2),
                day = ("0" + date.getDate()).slice(-2);
            hours = ("0" + date.getHours()).slice(-2);
            minutes = ("0" + date.getMinutes()).slice(-2);
            date_1 = [date.getFullYear(), mnth, day].join("-");
            time_1 = [hours, minutes].join(":");
            return [date_1, time_1].join(" ");
        }

        // =====================================================

        function download_csv(csv, filename) {
            var csvFile;
            var downloadLink;
            csvFile = new Blob([csv], {
                type: "text/csv"
            });
            downloadLink = document.createElement("a");
            downloadLink.download = filename;
            downloadLink.href = window.URL.createObjectURL(csvFile);
            downloadLink.style.display = "none";
            document.body.appendChild(downloadLink);
            downloadLink.click();
        }

        function export_table_to_csv(html, filename) {
            var csv = [];
            var rows = $("#entry-list1 tr");
            for (var i = 0; i < rows.length; i++) {
                var row = [],
                    cols = rows[i].querySelectorAll("td, th");
                for (var j = 0; j < cols.length; j++)
                    row.push(cols[j].innerText);
                csv.push(row.join(","));
            }
            // Download CSV
            download_csv(csv.join("\n"), filename);
        }

        $('body').on('change', "#CSV", function () {
            var html = document.querySelector("table").outerHTML;
            export_table_to_csv(html, "report.csv");
        })

        $('body').on('click', "#reset_password", function () {
            window.location.href = '/reset_password';
        });

        $('body').on('click', "#pdf", function () {
            if (!$(".for_generate_button")[0]) {
                exit();
            }
            $(".new2").remove();
            var arr = '';
            let count = 0;
            let sum_of_amount = 0;
            let d1 = '';
            let table_head = '';
            let table_bottom = '';
            $('#benefit_row tr').not(':last').each(function () {
                let name = $(this).find(".nam").html();
                let acc_name = $(this).find(".ac_name").html();
                let acc_no = $(this).find(".ac_no").html();
                let locat = $(this).find(".locat").html();
                let bank = $(this).find(".ban1").html();
                let ifsc = $(this).find(".if").html();
                let comment1 = $(this).find(".com1").html();
                let comment2 = $(this).find(".com1").html();
                let amount = $(this).find(".amm").html();
                let start = new Date();
                let d, m, y;
                count = count + 1;
                d = start.getDate();
                m = start.getMonth() + 1;
                if (d < 10) {
                    d = '0' + d;
                }
                if (m < 10) {
                    m = '0' + m;
                }
                sum_of_amount = parseInt(sum_of_amount) + parseInt(amount);
                y = start.getFullYear();
                d1 = `<tr scope="row">
                <th style="border: 1px solid black;text-align: center;">` + count + `</th>
                <td style="border: 1px solid black;text-align: center;">` + name + `</td>
                <td style="border: 1px solid black;text-align: center;">` + comment1 + `</td>
                <td style="border: 1px solid black;text-align: center;">` + d + "/" + m + "/" + y + `</td>
                <td style="border: 1px solid black;text-align: center;">` + amount + `</td></tr>`;

                arr = arr + d1;
            });
            table_head =
                "<div style='width: 100px; margin-top: -40px; height: 0px; float: right;''><img style='margin-top: 0px; width: 60px; height: 60px;' src='http://13.235.184.241:4000/images/jg-icon.png' /></div><div style='text-align: center;'><div style='text-align:center'><h3 style='margin-top: 40px'><b>Payout Approval Document</b></h3></div><table style='border: 1px solid black;border-collapse: collapse;width: 70%;margin: auto; '><thead ><tr><th style='border: 1px solid black;'>#</th><th style='border: 1px solid black;'>Name</th><th style='border: 1px solid black;'>Comment</th><th style='border: 1px solid black;'>Date</th><th style='border: 1px solid black;'>Amount</th></tr></thead><tbody>";
            table_bottom =
                '<tr style="text-align:center";><td colspan=4>Total</td><td style="border:1px solid">' +
                sum_of_amount + '</td></tr></tbody></table>';
            $.ajax({
                url: '/pdf_download',
                type: 'POST',
                data: {
                    top: table_head,
                    main_content: arr,
                    bottom: table_bottom
                },
                success: function (data) {
                    if (data.success == true) {
                   		 $("#content").html(data.data);
                     	var divContents = $("#content").html();
			            var printWindow = window.open('', '', 'height=400,width=800');
			            printWindow.document.write('</head><body >');
			            printWindow.document.write(divContents);
			            printWindow.document.write('</body></html>');
			            printWindow.document.close();
			            printWindow.print();
			             $("#content").empty();
                    } else if (data.success == false) {
                        console.log(data.msg)
                        alert(data.msg)
                    }
                },
                error: function (err) {
                    console.log("erros in pdf", err.message);
                }
            })

        });

        $('body').on('click', "#search", function () {
            window.localStorage.clear();
            const monthNames = ["Jan", "Feb", "Mar", "Apr", "May", "Jun",
                "Jul", "Aug", "Sept", "Oct", "Nov", "Dec"
            ];
            let selected_date = new Date();

            selected_day = $('.day').val(selected_date.getDate());
            selected_month = $('.month').val(monthNames[selected_date.getMonth()]);
            selected_year = $('.year').val(selected_date.getFullYear());
            selected_month = selected_date.getMonth() + 1;
            selected_day = selected_date.getDate();
            selected_year = selected_date.getFullYear();

            if (selected_day < 10) {

                selected_day = '0' + selected_day;
            }
            if (selected_month < 10) {
                selected_month = '0' + selected_month;
            }
            $.ajax({
                url: '/get_beneficial_by_date',
                type: 'POST',
                data: {
                    selected_day: selected_day,
                    selected_month: selected_month,
                    selected_year: selected_year,
                },
                success: function (data) {
                    let tr = '';
                    total_amt = 0;
                    sequence = 0;
                    for (let m = 0; m < data.msg.length; m++) {
                        sequence = sequence + 1;
                        let generate_date = convert(data.msg[m].date);
                        tr = tr + `<tr id="` + data.msg[m]._id + `">
			            <th scope="row" >` + sequence + `</th>
			            <td>` + data.msg[m].person + `</td>
			            <td>` + generate_date + `</td>
			            <td>` + data.msg[m].count + `</td>
			            <td>` + data.msg[m].amount + `</td>
			            <td ids="` + data.msg[m]._id + `" class="get_my_report btn btn-primary btn-sm">CSV</td>
			            <td ids1="` + data.msg[m]._id + `" style="margin-left:2px;" class="get_my_pdfreport btn btn-primary btn-sm">PDF</td>
			           </tr>`
                    }
                    $("#report_row").html(tr);
                },
                error: function (err) {
                    console.log(err, "error in date ajax");
                }
            })
        })

        $('body').on('click', ".get_my_pdfreport", function () {
           // table_head =
            //    "<div style='width: 100px; margin-top: -40px; height: 0px; float: right;><img style='margin-top: 0px; width: 60px; height: 60px;' src='http://13.235.184.241:4000/images/jg-icon.png' /></div><div style='text-align: center;'><div style='text-align:center'><h3 style='margin-top: 40px'><b>Payout Approval Document</b></h3></div><table style='border: 1px solid black;border-collapse: collapse;width: 70%;margin: auto; '><tbody>";
            $.ajax({
                url: '/pdf_report_download',
                type: 'POST',
                data: {
              //     top: table_head,
                    id: $(this).attr("ids1"),

                },
                success: function (data) {
                    if (data.success == true) {
                    console.log(data.data);
                     	$("#content").html(data.data);
                     	var divContents = $("#content").html();
			            var printWindow = window.open('', '', 'height=400,width=800');
			            printWindow.document.write('</head><body >');
			            printWindow.document.write(divContents);
			            printWindow.document.write('</body></html>');
			            printWindow.document.close();
                        printWindow.print();
                        $("#content").empty();
                    } else if (data.success == false) {
                        alert(data.msg)
                    }
                },
                error: function (err) {
                    console.log("erros in pdf", err);
                }
            })
        });
    </script>

    <script>
        var s,
            DateWidget = {
                settings: {
                    months: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sept', 'Oct', 'Nov', 'Dec'],
                    day: new Date().getUTCDate(),
                    currMonth: new Date().getUTCMonth(),
                    currYear: new Date().getUTCFullYear(),
                    yearOffset: 21,
                    containers: $(".dateDropdown")
                },

                init: function () {
                    s = this.settings;
                    DW = this;
                    s.containers.each(function () {
                        DW.removeFallback(this);
                        DW.createSelects(this);
                        DW.populateSelects(this);
                        DW.initializeSelects(this);
                        DW.bindUIActions();
                    })
                },

                getDaysInMonth: function (month, year) {
                    return new Date(year, month, 0).getDate();
                },

                addDays: function (daySelect, numDays) {
                    $(daySelect).empty();

                    for (var i = 0; i < numDays; i++) {
                        $("<option />")
                            .text(i + 1)
                            .val(i + 1)
                            .appendTo(daySelect);
                    }
                },

                addMonths: function (monthSelect) {
                    for (var i = 0; i < 12; i++) {
                        $("<option />")
                            .text(s.months[i])
                            .val(s.months[i])
                            .appendTo(monthSelect);
                    }
                },

                addYears: function (yearSelect) {
                    for (var i = 0; i < s.yearOffset; i++) {
                        $("<option />")
                            .text(i + s.currYear)
                            .val(i + s.currYear)
                            .appendTo(yearSelect);
                    }
                },

                removeFallback: function (container) {
                    $(container).empty();
                },

                createSelects: function (container) {
                    $("<select class='day form-control'>").appendTo(container);
                    $("<select class='month form-control'>").appendTo(container);
                    $("<select class='year form-control'>").appendTo(container);
                },

                populateSelects: function (container) {
                    DW.addDays($(container).find('.day'), DW.getDaysInMonth(s.currMonth, s.currYear));
                    DW.addMonths($(container).find('.month'));
                    DW.addYears($(container).find('.year'));
                },

                initializeSelects: function (container) {
                    $(container).find('.day').val(s.day);
                    $(container).find('.currMonth').val(s.month);
                    $(container).find('.currYear').val(s.year);
                },

                bindUIActions: function () {
                    $(".month").on("change", function () {
                        var daySelect = $(this).prev(),
                            yearSelect = $(this).next(),
                            month = s.months.indexOf($(this).val()) + 1,
                            days = DW.getDaysInMonth(month, yearSelect.val());
                        DW.addDays(daySelect, days);
                    });

                    $(".year").on("change", function () {
                        var daySelect = $(this).prev().prev(),
                            monthSelect = $(this).prev(),
                            month = s.months.indexOf(monthSelect.val()) + 1,
                            days = DW.getDaysInMonth(month, $(this).val());
                        DW.addDays(daySelect, days);
                    });
                }
            };

        DateWidget.init();

        function clearBeneficiary() {
            $('#benefit_row tr').not(':last').remove();
            $("#amt").val(0);
            $(".visibleamt").html(0);
        }
    </script>
</body>

</html>