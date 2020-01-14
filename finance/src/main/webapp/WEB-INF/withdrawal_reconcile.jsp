<!DOCTYPE html>
<html lang="en">

<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<link href="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
	<meta http-equiv="X-UA-Compatible" content="ie=edge">
	<title>Withdrawal Reconciliation</title>
</head>
<style>
	body {
		line-height: 1;
	}

	.red {
		color: red;
	}
	.black {
		color: black;
	}

	table.dataTable th,
	table.dataTable td {
		width: 150px;
		display: inline-block;
		overflow: hidden;
	}

	.w15 {
		/* width: calc(10	0% - 20px); */
		font-size: 90%;
		padding: 10px !important;
	}

	::-webkit-scrollbar {
		width: 12px;
	}

	::-webkit-scrollbar-track {
		border-radius: 10px;
	}

	::-webkit-scrollbar-thumb {
		border-radius: 10px;
		-webkit-box-shadow: inset 0 0 6px rgba(0, 0, 0, 0.5);
	}

	/*
    .popup-div {
        position: fixed;
        width: 100%;
        height: 100%;
        background: rgba(0, 0, 0, 0.6);
        top: 0
    }

    .progress {
        width: 150px;
        height: 150px;
        background: none;
        position: fixed;
        left: 50%;
        top: 50%;
        margin-left: -75px;
        margin-top: -75px;
    }

    .progress::after {
        content: "";
        width: 100%;
        height: 100%;
        border-radius: 50%;
        border: 6px solid #eee;
        position: absolute;
        top: 0;
        left: 0;
    }

    .progress>span {
        width: 50%;
        height: 100%;
        overflow: hidden;
        position: absolute;
        top: 0;
        z-index: 1;
    }

    .progress .progress-left {
        left: 0;
    }

    .progress .progress-bar {
        width: 100%;
        height: 100%;
        background: none;
        border-width: 6px;
        border-style: solid;
        position: absolute;
        top: 0;
    }

     .progress .progress-left .progress-bar {
        left: 100%;
        border-top-right-radius: 80px;
        border-bottom-right-radius: 80px;
        border-left: 0;
        -webkit-transform-origin: center left;
        transform-origin: center left;
    }

    .progress .progress-right {
        right: 0;
    }

    .progress .progress-right .progress-bar {
        left: -100%;
        border-top-left-radius: 80px;
        border-bottom-left-radius: 80px;
        border-right: 0;
        -webkit-transform-origin: center right;
        transform-origin: center right;
    }

    .progress .progress-value {
        position: absolute;
        top: 0;
        left: 0;
    } */

	.table-bordered {
		border: 0px solid #dee2e6;
	}
</style>

<body>
	<div class="loader" style="display: none"></div>
	<nav class="navbar navbar-light bg-light">
		<a href="/main_page">
			<h3 style="float: left;">
				<span class="badge badge-info">Back To Dashboard</span>
			</h3>
		</a>
		<h3 style="margin-left:1%">
			<input type="button" class="btn btn-danger" onClick="window.location.reload();" value="Reset">
		</h3>
		<span class="navbar-brand mb-0 h1" style="margin: auto">Withdrawal
			Reconciliation</span> <a href="/users/reset_password" style="margin-right: 1%">
			<h4 style="float: right;">
				<span class="badge badge-secondary">Reset Password</span>
			</h4>
		</a> <a href="/signout">
			<h4 style="float: right;">
				<span class="badge badge-secondary">Logout</span>
			</h4>
		</a>
	</nav>
	<div class="row">
		<div class="col-md-12" style="text-align: center;margin:1%">
		</div>
	</div>
	<!-- <input type="hidden" id="type_click" value="">
	<input type="hidden" id="parse_status" value=""> -->
	<div class="col-md-12 upload_files">
		<div class="row">
			<div class="col-md-4">
				<form id="AdminForm" class="col-md-12" method="POST" enctype="multipart/form-data">
					<fieldset>
						<div class="form-horizontal">
							<div class="row">
								<div class="col-md-12" style="margin: auto">
									<div class="row">
										<div class=" col-md-4"><b>Admin File</b></div>
										<div class="col-md-8" style="margin: auto">
											<div class="input-group">
												<input type="file" id="adminFilename" name="admin_filename"
													class="form-control form-control-sm"
													accept=".csv, application/vnd.openxmlformats-officedocument.spreadsheetml.sheet, application/vnd.ms-excel">
												<div class="input-group-btn">
													<input type="submit" value="Upload"
														class="rounded-0 btn btn-primary 1_disable" disabled>
												</div>
											</div>
										</div>
									</div>
								</div>
								<div class="col-md-12 admin_prog" style="margin: auto;margin-bottom: 4%">
									<div class="row">
										<div class="col-md-4"></div>
										<div class="col-md-12">
											<div class="row">
												<div class="col-md-4">
													<div class="admin_progress_bar">
														<div class="AdminprogressBar" data-value="0"
															style="display: block;"></div>
													</div>
												</div>
												<div class="col-md-4">
													<div>
														<i>
															<h6 class="admin_parsing" style="display: none;">Parsing....
															</h6>
														</i>
													</div>
												</div>
												<div class="col-md-4"></div>
											</div>
										</div>
									</div>
								</div>
							</div>
							<div class="col-md-12">
								<div class="row admin_ddn" style="visibility: hidden;">
									<div class="form-group col-md-4">
										<label for="file_1">Cash Free Id :</label> <select class="form-control admin "
											id="file_1">

										</select>
									</div>
									<div class="form-group col-md-4">
										<label for="file_2">Bank Id :</label> <select class="form-control admin"
											id="file_2">
										</select>
									</div>
									<div class="form-group col-md-4">
										<label for="file_3">Amount :</label> <select class="form-control admin"
											id="file_3">
										</select>
									</div>

								</div>
							</div>
						</div>

					</fieldset>
				</form>
			</div>
			<div class="col-md-4">
				<form id="CashFreeForm" class="col-md-12" method="POST" enctype="multipart/form-data">
					<fieldset>
						<div class="form-horizontal">
							<div class="row">

								<div class="col-md-12" style="margin: auto">
									<div class="row">
										<div class="col-md-4"><b>CashFree File</b></div>
										<div class="col-md-8" style="margin: auto">
											<div class="input-group">
												<input type="file" id="cashfreeFilename" name="cashfree_filename"
													class="form-control form-control-sm"
													accept=".csv, application/vnd.openxmlformats-officedocument.spreadsheetml.sheet, application/vnd.ms-excel">
												<div class="input-group-btn">
													<input type="submit" value="Upload"
														class="rounded-0 btn btn-primary 2_disable" disabled>
												</div>
											</div>
										</div>
									</div>
								</div>
								<div class="col-md-12 cashfree_prog" style="margin: auto;margin-bottom: 4%">
									<div class="row">
										<div class="col-md-4"></div>
										<div class="col-md-12">
											<div class="row">
												<div class="col-md-4">
													<div class="cashfree_progress_bar">
														<div class="CashfreeprogressBar" data-value="0"
															style="display: block;"></div>
													</div>
												</div>
												<div class="col-md-4">
													<div>
														<i>
															<h6 class="cashfree_parsing" style="display: none;">
																Parsing....</h6>
														</i>
													</div>
												</div>
												<div class="col-md-4"></div>
											</div>
										</div>
									</div>
								</div>
							</div>
							<div class="col-md-12">
								<div class="row cashfree_ddn" style="visibility: hidden;">
									<div class="form-group col-md-4"></div>
									<div class="form-group col-md-4">
										<label for="cash_1">Cash Free Id :</label> <select
											class="form-control  cashfreeHeader" id="cash_1">

										</select>
									</div>

									<div class="form-group col-md-4">
										<label for="cash_2">Amount :</label> <select class="form-control cashfreeHeader"
											id="cash_2">
										</select>
									</div>

								</div>
							</div>
						</div>

					</fieldset>
				</form>
			</div>
			<div class="col-md-4">
				<form id="BankForm" class="col-md-12" method="POST" enctype="multipart/form-data">
					<fieldset>
						<div class="form-horizontal">
							<div class="row">

								<div class="col-md-12" style="margin: auto">
									<div class="row">
										<div class="col-md-4"><b>Bank File</b></div>
										<div class="col-md-8" style="margin: auto">
											<div class="input-group">
												<input type="file" id="bankFilename" name="bank_filename"
													class="form-control form-control-sm"
													accept=".csv, application/vnd.openxmlformats-officedocument.spreadsheetml.sheet, application/vnd.ms-excel">
												<div class="input-group-btn">
													<input type="submit" value="Upload"
														class="rounded-0 btn btn-primary 3_disable" disabled>
												</div>
											</div>
										</div>
									</div>
								</div>
								<div class="col-md-12 bank_prog" style="margin: auto;margin-bottom: 4%">
									<div class="row">
										<div class="col-md-4"></div>
										<div class="col-md-12">
											<div class="row">
												<div class="col-md-4">
													<div class="bank_progress_bar">
														<div class="BankprogressBar" data-value="0"
															style="display: block;"></div>
													</div>
												</div>
												<div class="col-md-4">
													<div>
														<i>
															<h6 class="bank_parsing" style="display: none;">
																Parsing....</h6>
														</i>
													</div>
												</div>
												<div class="col-md-4"></div>
											</div>
										</div>
									</div>
								</div>
							</div>
							<div class="col-md-12">
								<div class="row bank_ddn" style="visibility: hidden;">
									<div class="form-group col-md-4"></div>
									<div class="form-group col-md-4">
										<label for="bank_1">Bank Id :</label> <select class="form-control bank"
											id="bank_1">

										</select>
									</div>



									<div class="form-group col-md-4">
										<label for="bank_2">Amount :</label> <select class="form-control bank"
											id="bank_2">
										</select>
									</div>
									<!-- <div class="form-group col-md-2"></div> -->
								</div>
							</div>
						</div>

					</fieldset>
				</form>
			</div>
		</div>
		<input type="hidden" id="file1" value=""> <input type="hidden" id="file2" value=""> <input type="hidden"
			id="file3" value="">
		<input type="hidden" id="parse_status" value="">
		<hr style="width: 90%; margin: auto; margin-bottom: 20px;" id="hr">

		<div class="row reconcile_button">
			<div class="col-md-12 prog_text_div"
				style="color: grey; display: none; text-align: center; margin-left: 1%; margin-bottom: 1%;">
				Progressing...
			</div>
			<div class="col-md-12" style="text-align: center; margin-bottom: -1%; margin-left: 1%;">
				<input type="button" class="btn btn-success " id="compare" value="Reconcile">
			</div>
		</div>
	</div>
	<!-- <div class="col-md-12">
		<div class="row " id="table_reconcile"
			style="display: block; margin-left: 0%; position: relative;">
			<div class="col-md-12" style="margin: auto;"> -->

	<!--	<div class="table-responsive col-sm-4" style="float: left;"
					id="table_1">
					 <div class='row'>
						<div class='col-md-12'>
							<h5 style="text-align: center">
								<span class="badge badge-success">CashFree Transactions</span>
							</h5>

							<div class="row" style="padding: 1%;">
								<div class="col-md-4">
									<button type="button" class="btn btn-sm btn-primary">
										Count: <span class="badge badge-light"
											id="total_cashfree_match">0</span>
									</button>
								</div>
								<div class="col-md-4">
									<button type="button" class="btn btn-sm btn-primary"
										style="float: right;">
										Amount:<span data-toggle="tooltip" title=""
											id="cashfree_tooltip"> <span class="badge badge-light"
											id="cashfree_total">0</span></span>
									</button>
								</div>
								<div class="col-md-4">
									<span style="float: right"> <a href="javascript:void(0)"
										class="downloadCsv" download="CashFree.csv"> <input
											type="button" class="btn-sm btn btn-Info" value="Download">
									</a>
									</span>
								</div>
							</div>

						</div>
					</div> -->
	<!--<div class='row'>
                    <div class='col-md-12'>
                            <table class="table table-bordered table-condensed" style="margin-bottom: 0px;">

                                <thead>
                                    <tr>
                                        <th scope="col" class="w15">Transaction Id</th>
                                        <th scope="col" class="w15">Amount</th>
                                        <th scope="col" class="w15">Diffrence</th>
                                    </tr>
                                </thead>
                            </table>
                        </div>
                        <div class='col-md-12' style="height:390px;overflow-y:auto;">
                            <table class="table table-bordered table-condensed">
                                <tbody id="cashfree_csv">

                                </tbody>
                            </table>
                        </div>
                    </div>
				</div>-->
	<!-- <div class="table-responsive col-sm-4" style="float: left;"
					id="table_2">
					<div class='row'>
						<div class='col-md-12'>
							<h5 style="text-align: center">
								<span class="badge badge-success">Admin Transactions</span>
							</h5>

							<div class="row" style="padding: 1%;">

								<div class="col-md-4">
									<button type="button" class="btn btn-sm btn-primary">
										Count: <span class="badge badge-light" id="total_admin_match">0</span>
									</button>
								</div>
								<div class="col-md-4">
									<button type="button" class="btn btn-sm btn-primary"
										style="float: right;">
										Amount:<span data-toggle="tooltip" title="" id="admin_tooltip">
											<span class="badge badge-light" id="admin_same_total">0</span>
										</span>
									</button>
								</div>
								<div class="col-md-4">
									<span style="float: right"> <a href="javascript:void(0)"
										class="downloadCsv" download="Admin.csv"> <input
											type="button" class="btn-sm btn btn-Info" value="Download">
									</a>
									</span>
								</div>
							</div>

						</div>
					</div> -->
	<!-- <div class='row'>
                        <div class='col-md-12'>
                            <table class="table table-bordered table-condensed" style="margin-bottom: 0px;">

                                <thead>
                                    <tr>
                                        <th scope="col" class="w15">Transaction Id</th>
                                        <th scope="col" class="w15">Amount</th>
                                        <th scope="col" class="w15">Diffrence</th>
                                    </tr>
                                </thead>
                            </table>
                        </div>
                        <div class='col-md-12' style="height:390px;overflow-y:auto;">
                            <table class="table table-bordered table-condensed">
                                <tbody id="admin_csv">
								<tr><td class="w15">733648</td><td class="w15">2555887</td><td class="w15">N/A</td><td class="w15">N/A</td><td class="w15">N/A</td><td class="w15">N/A</td></tr>
								<tr><td class="w15">733649</td><td class="w15">2555888</td><td class="w15">N/A</td><td class="w15">N/A</td><td class="w15">N/A</td><td class="w15">N/A</td></tr>
								<tr><td class="w15">733650</td><td class="w15">2555889</td><td class="w15">N/A</td><td class="w15">N/A</td><td class="w15">N/A</td><td class="w15">N/A</td></tr>
								<tr><td class="w15">733651</td><td class="w15">2555890</td><td class="w15">N/A</td><td class="w15">N/A</td><td class="w15">N/A</td><td class="w15">N/A</td></tr>
								<tr><td class="w15">733652</td><td class="w15">2555891</td><td class="w15">N/A</td><td class="w15">N/A</td><td class="w15">N/A</td><td class="w15">N/A</td></tr>
								<tr><td class="w15">733653</td><td class="w15">2555892</td><td class="w15">N/A</td><td class="w15">N/A</td><td class="w15">N/A</td><td class="w15">N/A</td></tr>
                                </tbody>
                            </table>
                        </div>
                    </div> 
				</div>-->
	<!-- <div class="table-responsive col-sm-4" style="float: right;"
					id="table_3">
					<div class='row'>
						<div class='col-md-12'>
							<h5 style="text-align: center">
								<span class="badge badge-success"> Bank Transactions</span>
							</h5>
							<div class="row" style="padding: 1%;">

								<div class="col-md-4">
									<button type="button" class="btn btn-sm btn-primary">
										Count : <span class="badge badge-light" id="total_bank_match">0</span>
									</button>
								</div>
								<div class="col-md-4">
									<button type="button" class="btn btn-sm btn-primary"
										style="float: right;">
										Amount :<span data-toggle="tooltip" title="" id="bank_tooltip">
											<span class="badge badge-light" id="bank_total">0</span>
										</span>
									</button>
								</div>

								<div class="col-md-4">
									<span style="float: right"> <a href="javascript:void(0)"
										class="downloadCsv" download="Bank.csv"> <input
											type="button" class="btn-sm btn btn-Info" value="Download">
									</a>
									</span>
								</div>
							</div>
						</div>
					</div> -->
	<!--  <div class='row'>
           			<div class='col-md-12'>
                            <table class="table table-bordered table-condensed" style="margin-bottom: 0px;">
                                <thead>
                                    <tr>
                                        <th scope="col" class="w15">Transaction Id</th>
                                        <th scope="col" class="w15">Amount</th>
                                        <th scope="col" class="w15">Diffrence</th>
                                    </tr>
                                </thead>

                            </table>
                        </div>
                        <div class='col-md-12' style="height:390px;overflow-y:auto;">
                            <table class="table table-bordered table-condensed">

                                <tbody id="bank_csv">
                                </tbody>
                            </table>
                        </div>
                    </div>
				</div>-->
	<!-- </div>
		</div>
	</div> -->
	<div class="col-md-12" id="table_reconcile" style="display:none">
		<div class="row">
			<div class="table-responsive col-sm-4">
				<div class="row">
					<div class="col-sm-12">
						<table class="table table-bordered table-condensed" style="margin-bottom: 0px;">
							<thead>
								<tr>
									<th scope="col" class="w15">
										<h4><span class="badge badge-dark">Title</span>
									</th>
									<th scope="col" class="w15">
										<h4><span class="badge badge-dark">Count</span></h4>
									</th>
									<th scope="col" class="w15">
										<h4><span class="badge badge-dark">Total (&#8377;)</span></h4>
									</th>
									<th scope="col" class="w15">
										<h4><span class="badge badge-dark">Download</span></h4>
									</th>
								</tr>
								<tr>
									<td class="w15">
										<h5><span class="badge badge-secondary">Admin</span></h5>
									</td>
									<td class="w15">
										<h5><span class="badge badge-light" id="total_admin_match">0</span></h5>
									</td>
									<td class="w15"><span data-toggle="tooltip" title="" id="admin_tooltip">
											<h5><span class="badge badge-light" id="admin_same_total">0</span></h5>
										</span></td>
									<td class="w15"><a href="javascript:void(0)" class="downloadCsv"
											download="Admin.csv">
											<img src="./images/csv_download.png" alt="" height="35" width="35"
												value="Download">
										</a></td>
								</tr>
								<tr>
									<td class="w15">
										<h5><span class="badge badge-secondary">CashFree</span></h5>
									</td>
									<td class="w15">
										<h5><span class="badge badge-light" id="total_cashfree_match">0</span></h5>
									</td>
									<td class="w15"><span data-toggle="tooltip" title="" id="cashfree_tooltip">
											<h5> <span class="badge badge-light" id="cashfree_total">0</span></h5>
										</span></td>
									<td class="w15"><a href="javascript:void(0)" class="downloadCsv"
											download="CashFree.csv"> <img src="./images/csv_download.png" alt=""
												height="35" width="35" value="Download">
										</a></td>
								</tr>
								<tr>
									<td class="w15">
										<h5><span class="badge badge-secondary">Bank</span></h5>
									</td>
									<td class="w15">
										<h5><span class="badge badge-light" id="total_bank_match">0</span></h5>
									</td>
									<td class="w15"><span data-toggle="tooltip" title="" id="bank_tooltip">
											<h5><span class="badge badge-light" id="bank_total">0</span></h5>
										</span></td>
									<td class="w15"><a href="javascript:void(0)" class="downloadCsv"
											download="Bank.csv"> <img src="./images/csv_download.png" alt="" height="35"
												width="35" value="Download">
										</a></td>
								</tr>
							</thead>
						</table>
					</div>
				</div>
			</div>
			<div class="col-sm-8">
				<div class="row">
					<div class="col-md-12" style="height: 600px; overflow-y: auto;">
						<table class="table table-bordered table-condensed dataTable">
							<thead>
								<tr>
									<th scope="col" class="w15">Cashfree Id</th>
									<th scope="col" class="w15">Bank Id</th>
									<th scope="col" class="w15">Cashfree Amount</th>
									<th scope="col" class="w15">Cashfree Diffrence</th>
									<th scope="col" class="w15">Bank Amount</th>
									<th scope="col" class="w15">Bank Diffrence</th>
								</tr>
							</thead>
							<tbody id="admin_csv">
								<tr>
									<td class="w15">733648</td>
									<td class="w15">2555887</td>
									<td class="w15">N/A</td>
									<td class="w15">N/A</td>
									<td class="w15">N/A</td>
									<td class="w15">N/A</td>
								</tr>
								<tr>
									<td class="w15">733649</td>
									<td class="w15">2555888</td>
									<td class="w15">N/A</td>
									<td class="w15">N/A</td>
									<td class="w15">N/A</td>
									<td class="w15">N/A</td>
								</tr>
								<tr>
									<td class="w15">733648</td>
									<td class="w15">2555887</td>
									<td class="w15">N/A</td>
									<td class="w15">N/A</td>
									<td class="w15">N/A</td>
									<td class="w15">N/A</td>
								</tr>
								<tr>
									<td class="w15">733649</td>
									<td class="w15">2555888</td>
									<td class="w15">N/A</td>
									<td class="w15">N/A</td>
									<td class="w15">N/A</td>
									<td class="w15">N/A</td>
								</tr>
								<tr>
									<td class="w15">733648</td>
									<td class="w15">2555887</td>
									<td class="w15">N/A</td>
									<td class="w15">N/A</td>
									<td class="w15">N/A</td>
									<td class="w15">N/A</td>
								</tr>
								<tr>
									<td class="w15">733649</td>
									<td class="w15">2555888</td>
									<td class="w15">N/A</td>
									<td class="w15">N/A</td>
									<td class="w15">N/A</td>
									<td class="w15">N/A</td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>

	<div class="popup-div" style="display: none">
		<div class="progress auto_progress" data-value='0'>
			<span class="progress-left"> <span class="progress-bar border-primary"></span>
			</span> <span class="progress-right"> <span class="progress-bar border-primary"></span>
			</span>
			<div class="progress-value w-100 h-100 rounded-circle d-flex align-items-center justify-content-center">
				<div class="h2 font-weight-bold ifzero">
					<span class="prog_val">0</span><sup class="small">%</sup>
				</div>
			</div>
		</div>
	</div>
</body>
<script src="/javascripts/jquery.min.js"></script>
<script>
	$(".downloadCsv").click(function () {
		let type = $(this).attr("download");
		// alert(type);
		$.ajax({
			data: {
				typeCsv: type
			},
			url: "/downloadWithdrawalCsv",
			type: "POST",
			success: function (data) {
				if (data) {
					var a = document.createElement("a");
					document.body.appendChild(a);
					a.style = "display:none";
					let blob = new Blob([data], {
						type: "octet/stream"
					}), url = window.URL.createObjectURL(blob);
					a.href = url;
					a.download = type;
					a.click();
					window.URL.revokeObjectURL(url);
				}
			},
			error: function (err) {
				console.log("error in download csv" + err);
			}
		});
	});

	$("#AdminForm").on(
		'submit',
		(function (e) {
			$(".admin_parsing").css({
				"display": "block"
			});
			AdminForm
			e.preventDefault();
			var singleFileUploadInput = document
				.querySelector('#adminFilename');
			var files = singleFileUploadInput.files;
			e.preventDefault();
			var formData = new FormData();
			formData.append('file', files[0])
			$("#parse_status").val(0);
			$.ajax({
				enctype: "multipart/form-data",
				url: '/adminFileUpload',
				type: 'POST',
				data: formData,
				contentType: false,
				processData: false,
				success: function (data) {
					if (data.length > 0) {
						$(".admin_ddn").css({
							"visibility": "visible"
						});
						$("#parse_status").val(1)
						$(".admin_parsing").css({
							"display": "none"
						});

						let datahtml = '';
						for (let i = 0; i < data.length; i++) {
							datahtml = datahtml
								+ "<option value=" + data[i] + ">"
								+ data[i] + "</option>"
						}
						$(".admin").html(datahtml);
					}
				},
				error: function (err) {
					$(".admin_parsing").css({
						"display": "none"
					});
					console.log("errou", err)
				}
			})
		}))
	$("#CashFreeForm").on(
		'submit',
		(function (e) {
			$(".cashfree_parsing").css({
				"display": "block"
			});
			e.preventDefault();
			$("#parse_status").val(0);
			e.preventDefault();
			var singleFileUploadInput = document
				.querySelector('#cashfreeFilename');
			var files = singleFileUploadInput.files;
			var formData = new FormData();
			formData.append('file', files[0]);
			$.ajax({
				enctype: "multipart/form-data",
				url: '/cashfreeFileUpload',
				type: 'POST',
				data: formData,
				contentType: false,
				processData: false,
				success: function (data) {
					if (data.length > 0) {
						$("#parse_status").val(1)
						$(".cashfree_parsing").css({
							"display": "none"
						});
						$(".cashfree_ddn").css({
							"visibility": "visible"
						});
						var datahtml = '';
						for (let i = 0; i < data.length; i++) {
							datahtml = datahtml
								+ "<option value=" + data[i] + ">"
								+ data[i] + "</option>"
						}
						$(".cashfreeHeader").html(datahtml);
					}
				},
				error: function (err) {
					$(".cashfree_parsing").css({
						"display": "none"
					});
					console.log("err", err)
				}
			})
		}))
	$("#BankForm").on(
		'submit',
		(function (e) {
			$(".bank_parsing").css({
				"display": "block"
			});
			$("#parse_status").val(0);
			e.preventDefault();
			var singleFileUploadInput = document
				.querySelector('#bankFilename');
			var files = singleFileUploadInput.files;
			e.preventDefault();
			var formData = new FormData();
			formData.append('file', files[0])
			$.ajax({
				enctype: "multipart/form-data",
				url: '/bankFileUpload',
				type: 'POST',
				data: formData,
				contentType: false,
				processData: false,
				success: function (data) {
					if (data.length > 0) {
						let datahtml = '';
						for (let i = 0; i < data.length; i++) {
							datahtml = datahtml
								+ "<option value=" + data[i] + ">"
								+ data[i] + "</option>"
						}
						$(".bank").html(datahtml);
						$(".bank_ddn").css({
							"visibility": "visible"
						});
						$("#parse_status").val(1);
						$(".bank_parsing").css({
							"display": "none"
						});
					}
				},
				error: function (err) {
					$(".bank_parsing").css({
						"display": "none"
					});
					console.log("err", err)
				}
			})
		}))

	$("#compare").click(
		function (e) {
			$(".prog_text_div").css("display", "block");
			// $(".ifzero").html("fetching");
			$("#compare").css({
				"display": "none"
			});
			// let bank_id = "Withdrawal Id";
			// let cashfree_id = "Cashfree TransferId";
			// let amount = "amount";
			// let cashfreeId = "Transfer Id";
			// let cashfreeamount = "Amount"; //Amount//Amount (in Rs.)
			// let bankId = "Narration"; //DESCRIPTION//Narration
			// let bankamount = "Withdrawal Amt."; //DEBITS//Withdrawal Amt.

			let cashfree_id = $("#file_1 :selected").text();
			let  bank_id= $("#file_2 :selected").text();
			let amount = $("#file_3 :selected").text();
			let cashfreeId = $("#cash_1 :selected").text();
			let cashfreeamount = $("#cash_2 :selected").text();
			let bankId = $("#bank_1 :selected").text();
			let bankamount = $("#bank_2 :selected").text();
			console.log(cashfreeId + "::::" + cashfreeamount);
			if (bank_id == '') {
				alert("Enter bank_id");
				exit();
			}
			if (cashfree_id == '') {
				alert("enter cashfree_id");
				exit();
			}
			if (amount == '') {
				alert("enter amount");
				exit();
			}
			if (cashfreeId == '') {
				alert("enter cashfreeId");
				exit();
			}
			if (cashfreeamount == '') {
				alert("enter cashfreeamount");
				exit();
			}
			if (bank_id == '') {
				alert("enter bank_id");
				exit();
			}
			if (bankamount == '') {
				alert("enter bankamount");
				exit();
			}
			$.ajax({
				url: '/compareWithdraw',
				type: "POST",
				async: true,
				data: {
					adminBankId: bank_id,
					adminCashfreeId: cashfree_id,
					adminAmount: amount,
					cashfreeId: cashfreeId,
					cashfreeAmount: cashfreeamount,
					bankId: bankId,
					bankAmount: bankamount,

				},
				success: function (data) {
					if (data) {
						// $(".popup-div").css({
						//     "display": "none"
						// })
						$(".prog_text_div").css("display", "none");
						$("#cashfree_csv").html(data.cashfreeHtml)
						$("#admin_csv").html(data.adminHtml)
						$("#bank_csv").html(data.bankHtml)

						$("#table_reconcile").css({
							"display": "block"
						});
						$(".upload_files").css({
							"display": "none"
						});
						$("#total_admin_match").html(data.adminSize), $(
							"#total_cashfree_match").html(
								data.cashfreeSize), $("#total_bank_match")
									.html(data.bankSize), $("#cashfree_total")
										.html(data.cashfreeTotalAmount), $(
											"#bank_total").html(data.bankTotalAmount),
							$("#admin_same_total").html(
								data.adminTotalAmount)

					}
				},
				error: function (err) {
					// alert("errr", err);
					console.log("errr", err)
				}
			});

		});

	$('#adminFilename').change(function () {
		if ($(this).val()) {
			$('.1_disable').attr('disabled', false);
		}
	});
	$("#cashfreeFilename").change(function () {
		if ($(this).val()) {
			$('.2_disable').attr('disabled', false);
		}
	});
	$("#bankFilename").change(function () {
		if ($(this).val()) {
			$('.3_disable').attr('disabled', false);
		}
	});

	// var myVar

	// function calling_fun() {
	// 	$(".prog_text_div").css("display", "block");
	// 	myVar = setInterval(start_progress, 1000)
	// }

	// function start_progress() {

	// 	$.ajax({
	// 		url: '/withdraw/get_parsing_status', //'/withdraw/compare',
	// 		type: 'GET',
	// 		success: function (data) {
	// 			console.log(data)
	// 			if (data.success) {
	// 				if (data.total_percent != 100) {
	// 					$(".prog_text").html(parseInt(data.total_percent));
	// 				} else if (data.total_percent == 100) {

	// 					clearInterval(myVar)
	// 				}
	// 			}
	// 		},
	// 		error: function (err) {
	// 			clearInterval(myVar);
	// 			console.log(err, "errr")
	// 		}
	// 	})
	// }

	// ============================progressloader==========================
	// function prog_load() {
	//     $(".progress").each(function () {
	//         var value = $(this).attr('data-value');
	//         var left = $(this).find('.progress-left .progress-bar');
	//         var right = $(this).find('.progress-right .progress-bar');

	//         if (value > 0) {
	//             if (value <= 50) {
	//                 right.css('transform', 'rotate(' + percentageToDegrees(value) + 'deg)')
	//             } else {
	//                 right.css('transform', 'rotate(180deg)')
	//                 left.css('transform', 'rotate(' + percentageToDegrees(value - 50) + 'deg)')
	//             }
	//         }
	//     })

	// }

	// function percentageToDegrees(percentage) {
	//     return percentage / 100 * 360
	// }
</script>

</html>