<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <meta http-equiv="X-UA-Compatible" content="ie=edge">
  <title>Division</title>
  <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css"
    integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
  <style>

  </style>
  <style>
    * {
      box-sizing: border-box;
    }

    body {
      font-family: Arial, Helvetica, sans-serif;
    }

    /* Float four columns side by side */
    .column {
      margin-right: 1%;
    float: left;
    width: 32%;
    padding: 0px 24px;
    }

    /* Remove extra left and right margins, due to padding */
    .row {
      margin: 0 -5px;
    }

    /* Clear floats after the columns */
    .row:after {
      content: "";
      display: table;
      clear: both;
    }

    /* Responsive columns */
    @media screen and (max-width: 600px) {
      .column {
        width: 100%;
        display: block;
        margin-bottom: 20px;
      }
    }

    /* Style the counter cards */
    .card {
      position: relative;
      box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2);
      padding: 96px 16px;
      text-align: center;
      background-color: #f1f1f1;
    }

    .text-wrapper{
      position: absolute;
      bottom: 0px;
      width: 100%;
      height: inherit;
      background: #ffffff;
      margin-left: -16px;
      color: #4c8bf5;
      padding: 12px;
    }

    .text-wrapper a{
      margin-bottom: 0px;
      padding: 12px;
      cursor: pointer;
      text-decoration: none;
    }

    .clearfix::after {
      content: "";
      clear: both;
      display: table;
    }

    .main{
      padding: 140px 80px;
    }

    hr {
      margin-top: 0rem;
      margin-bottom: 0rem;
      border: 0;
      border-top: 1px solid rgba(0,0,0,.1);
    }
    img{
      border-radius: 100px;height:100px;width:100px;margin:auto;
    }
    h2{
      margin: 0 auto;text-align: center;height: 100%;vertical-align: middle;padding: 10px;
    }
  </style>
</head>

<body style="background-color: #F3F6FD">


  <div style="background-color: #ffffff;">
    <div style="height: 100%;">
      <h2>Quick Link</h2>
    </div>
    <hr />
    <div class="main clearfix" style="background-color: #F3F6FD">

      <div class="column">
          <a href="/index">
        <div class="card">
          <img src="/images/f3.jpg"  alt="">
          <div class="text-wrapper">
              Payment Report
          </div>
          <!-- <p>Some text</p> -->
        </div>
      </a>
      </div>

      <div class="column">
          <a href="/deposit" class="deposit_reconciliation">
        <div class="card">
            <img src="/images/f2.jpg" alt="">
            <div class="text-wrapper">
               Deposit Reconciliation
            </div>
          <!-- <p>Some text</p> -->
        </div>
      </a>
      </div>

      <div class="column">
          <a href="/withdraw" class="withdraw_reconciliation">
        <div class="card">
            <img src="/images/f1.png" alt="">
            <div class="text-wrapper">
                Withdrawal Reconciliation
            </div>
        </div>
      </a>
      </div>
      </div>
    </div>
  

</body>
<script src="/javascripts/jquery.min.js"></script>

</html>