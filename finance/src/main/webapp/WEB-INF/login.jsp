<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <meta http-equiv="X-UA-Compatible" content="ie=edge">
  <meta http-equiv='Cache-Control' , content='no-store, no-cache, must-revalidate'>
  <meta http-equiv='Pragma' , content='no-cache'>
  <meta http-equiv='Expires' , content='-1'>
  <title>Login</title>
  <link href="/stylesheets/bootstrap.min.css" rel="stylesheet" />
  <style>
    /* BASIC */

    html {
      background-color: #56baed;
    }

    body {
      font-family: "Poppins", sans-serif;
      height: 100vh;
    }

    a {
      color: #92badd;
      display: inline-block;
      text-decoration: none;
      font-weight: 400;
    }

    h2 {
      text-align: center;
      font-size: 16px;
      font-weight: 600;
      text-transform: uppercase;
      display: inline-block;
      margin: 40px 8px 10px 8px;
      color: #cccccc;
    }



    /* STRUCTURE */

    .wrapper {
      display: flex;
      align-items: center;
      flex-direction: column;
      justify-content: center;
      width: 100%;
      min-height: 100%;
      padding: 20px;
    }

    #formContent {
      -webkit-border-radius: 10px 10px 10px 10px;
      border-radius: 10px 10px 10px 10px;
      background: #fff;
      padding: 30px;
      width: 90%;
      max-width: 450px;
      position: relative;
      padding: 0px;
      -webkit-box-shadow: 0 30px 60px 0 rgba(0, 0, 0, 0.3);
      box-shadow: 0 30px 60px 0 rgba(0, 0, 0, 0.3);
      text-align: center;
    }

    #formFooter {
      background-color: #f6f6f6;
      border-top: 1px solid #dce8f1;
      padding: 25px;
      text-align: center;
      -webkit-border-radius: 0 0 10px 10px;
      border-radius: 0 0 10px 10px;
    }



    /* TABS */

    h2.inactive {
      color: #cccccc;
    }

    h2.active {
      color: #0d0d0d;
      border-bottom: 2px solid #5fbae9;
    }
    /* FORM TYPOGRAPHY*/

    input[type=button],
    input[type=submit],
    input[type=reset] {
      background-color: #56baed;
      border: none;
      color: white;
      font-weight: bold;
    font-family: Arial;
      padding: 13px 80px;
      text-align: center;
      text-decoration: none;
      display: inline-block;
      text-transform: uppercase;
      font-size: 16px;
      -webkit-box-shadow: 0 10px 30px 0 rgba(95, 186, 233, 0.4);
      box-shadow: 0 10px 30px 0 rgba(95, 186, 233, 0.4);
      -webkit-border-radius: 5px 5px 5px 5px;
      border-radius: 5px 5px 5px 5px;
      margin: 5px 20px 40px 20px;
      -webkit-transition: all 0.3s ease-in-out;
      -moz-transition: all 0.3s ease-in-out;
      -ms-transition: all 0.3s ease-in-out;
      -o-transition: all 0.3s ease-in-out;
      transition: all 0.3s ease-in-out;
    }

    input[type=button]:hover,
    input[type=submit]:hover,
    input[type=reset]:hover {
      background-color: #39ace7;
    }

    input[type=button]:active,
    input[type=submit]:active,
    input[type=reset]:active {
      -moz-transform: scale(0.95);
      -webkit-transform: scale(0.95);
      -o-transform: scale(0.95);
      -ms-transform: scale(0.95);
      transform: scale(0.95);
    }

    input[type=text],
    input[type=password] {
      background-color: #f6f6f6;
    border: none;
    color: #0d0d0d;
    
    padding: 15px 32px;
    text-align: center;
    text-decoration: none;
    display: inline-block;
    font-size: 16px;
    margin-bottom: 10px;
    width: 100%;
    border: 2px solid #eeeeee;
    -webkit-transition: all 0.5s ease-in-out;
    -moz-transition: all 0.5s ease-in-out;
    -ms-transition: all 0.5s ease-in-out;
    -o-transition: all 0.5s ease-in-out;
    transition: all 0.5s ease-in-out;
    -webkit-border-radius: 5px 5px 5px 5px;
    border-radius: 5px 5px 5px 5px;
    box-sizing: border-box;
    }

    input[type=text],
    input[type=password]:focus {
      background-color: #fff;
      /* border-bottom: 2px solid #5fbae9; */
    }

    input[type=text],
    input[type=password]:placeholder {
      color: #cccccc;
    }



    /* ANIMATIONS */

    /* Simple CSS3 Fade-in-down Animation */
    .fadeInDown {
      -webkit-animation-name: fadeInDown;
      animation-name: fadeInDown;
      -webkit-animation-duration: 1s;
      animation-duration: 1s;
      -webkit-animation-fill-mode: both;
      animation-fill-mode: both;
    }

    @-webkit-keyframes fadeInDown {
      0% {
        opacity: 0;
        -webkit-transform: translate3d(0, -100%, 0);
        transform: translate3d(0, -100%, 0);
      }

      100% {
        opacity: 1;
        -webkit-transform: none;
        transform: none;
      }
    }

    @keyframes fadeInDown {
      0% {
        opacity: 0;
        -webkit-transform: translate3d(0, -100%, 0);
        transform: translate3d(0, -100%, 0);
      }

      100% {
        opacity: 1;
        -webkit-transform: none;
        transform: none;
      }
    }

    /* Simple CSS3 Fade-in Animation */
    @-webkit-keyframes fadeIn {
      from {
        opacity: 0;
      }

      to {
        opacity: 1;
      }
    }

    @-moz-keyframes fadeIn {
      from {
        opacity: 0;
      }

      to {
        opacity: 1;
      }
    }

    @keyframes fadeIn {
      from {
        opacity: 0;
      }

      to {
        opacity: 1;
      }
    }

    .fadeIn {
      opacity: 0;
      -webkit-animation: fadeIn ease-in 1;
      -moz-animation: fadeIn ease-in 1;
      animation: fadeIn ease-in 1;

      -webkit-animation-fill-mode: forwards;
      -moz-animation-fill-mode: forwards;
      animation-fill-mode: forwards;

      -webkit-animation-duration: 1s;
      -moz-animation-duration: 1s;
      animation-duration: 1s;
    }

    .fadeIn.first {
      -webkit-animation-delay: 0.4s;
      -moz-animation-delay: 0.4s;
      animation-delay: 0.4s;
      height: 100px;
      width: 100px;
      margin: auto;
    }

    .fadeIn.second {
      -webkit-animation-delay: 0.6s;
      -moz-animation-delay: 0.6s;
      animation-delay: 0.6s;
    }

    .fadeIn.third {
      -webkit-animation-delay: 0.8s;
      -moz-animation-delay: 0.8s;
      animation-delay: 0.8s;
    }

    .fadeIn.fourth {
      -webkit-animation-delay: 1s;
      -moz-animation-delay: 1s;
      animation-delay: 1s;
    }
    form{
      padding: 10px;
    }
    /* Simple CSS3 Fade-in Animation */
    .underlineHover:after {
      display: block;
      left: 0;
      bottom: -10px;
      width: 0;
      height: 2px;
      background-color: #56baed;
      content: "";
      transition: width 0.2s;
    }

    .underlineHover:hover {
      color: #0d0d0d;
    }

    .underlineHover:hover:after {
      width: 100%;
    }



    /* OTHERS */

    *:focus {
      outline: none;
    }

    #icon {
      width: 100%;
    height: 100%;
    object-fit: contain;
    }

    /* ==================================OTP MOD============================== */
    .modalbox .box {
      background-color: #fff;
    margin: 0 auto;
    min-width: 210px;
    padding: 40px;
    width: 30%;
    margin-top: 100px;
    border-radius: 5px;
    }

    .modalbox .title {
      font-family: arial;
    font-size: 16px;
    letter-spacing: 0.2em;
    margin: 0;
    padding: 0 0 5px;
    /* text-transform: uppercase; */
    color: #fff;
    background: none;
    text-align: center;
    }

    .modalbox .content {
      display: block;
      font-family: Verdana;
      font-size: 18px;
      line-height: 22px;
      padding: 10px 0 0;
      color: #fff;
      text-align: center;
    }

    .modalbox .close {
      color: #fff;
      display: block;
      float: right;
      font-family: Verdana;
      font-size: 22px;
      height: 25px;
      text-decoration: none;
    }


    .modalbox {
      display: none;
    position: fixed;
    z-index: 9999;
    width: 100%;
    height: 100%;
    top: 0;
    left: 0;
    color: #333333;
    background: rgba(0,0,0,0.5);
    }

    .modalbox:target {
      display: block;
      outline: none;
    }


    .link-modal {
      width: 90%;
      text-align: center;
      margin: 0 auto;
      padding-top: 400px;
      padding-left: 15px;
    }
    .link-modal a {
      border: 1px solid #fff;
      color: #fff;
      font-family: Verdana;
      font-size: 20px;
      letter-spacing: 0.3em;
      padding: 10px;
      text-decoration: none;
      text-transform: uppercase;
    }
    .otp{
      color: #0d0d0d;
        padding: 15px 32px;
        text-align: center;
        text-decoration: none;
        display: inline-block;
        font-size: 16px;
        margin: 5px;
        border: 2px solid #f6f6f6;
        transition: all 0.5s ease-in-out;
        -webkit-border-radius: 5px 5px 5px 5px;
        border-radius: 5px 5px 5px 5px;
        display: block;
        width: 100%;
    }

    /* ====================================================================== */
  </style>
</head>

<body>
  <div class="wrapper fadeInDown">

    <div id="formContent">
      <!-- Tabs Titles -->
      <input type="hidden" name="user" id="user" value="">
      <!-- Icon -->
      <div class="fadeIn first">
        <img src="./images/jg-icon.png" id="icon" alt="User Icon" />

      </div>
      <p id="msg1"></p>


      <!-- Login Form -->
      <form method="POST">
        <input type="text" id="login" class="fadeIn second" name="username" placeholder="Username">
        <input type="password" id="password" class="fadeIn third" name="password" placeholder="Password">
        <input type="button" class="fadeIn fourth" value="Log In">
      </form>
    </div>
  </div>
  <!-- --------------------------OTP VERIFY POPUP MODAL---------------------------- -->
  <div class="modalbox" id="text">
    <div class="box">
      <a class="title" id="otp_error" href="javacsript:void(0)" style="float: none;color: red;"></a>
      <p class="title"style="color:black;font-weight: bold;font-family: arial;">Please enter OTP</p>
      <input type="text" name="otp" id="otp" class="otp" placeholder="Your OTP">
      <div class="content">
        <input type="button" id="otpVerify"  value="Submit">
          <!-- <a href="javascript:void(0);" id="otpVerify" class="btn btn-primary">submit</a> -->
        
      </div>
    </div>
  </div>


  <!-- --------------------------------------------------------------------------- -->
</body>
<script src="/javascripts/jquery.min.js"></script>
<script src="/javascripts/bootstrap.min.js"></script>
<script>
  $("#otpVerify").click(function (){
    let otp = $("#otp").val();
    if (otp == '' || otp == undefined) {
      $('#otp_error').css({
        "color": "red",
        "font-size": "15px"
      }).html("Enter OTP!!").fadeIn(500).fadeOut(8000);
      return false;
    }
    $.ajax({
      url: '/otpVerify',
      type: 'POST',
      data: {
        otp: otp,
        username:$("#user").val()
      },
      success: function (data) {
       if (data.success == true) {
          window.location.href = "/main_page";
        } else {
          $('#otp_error').css({
            "color": "red",
            "font-size": "15px"
          }).html("Enter Valid OTP!!").fadeIn(500).fadeOut(8000);
          return false;
        }
      },
      error: function (err) {
        $('#otp_error').css({
          "color": "red",
          "font-size": "15px"
        }).html(err.message).fadeIn(500).fadeOut(8000);
      
      }
    });
  });

  $(".fourth").click(function () {
    event.preventDefault();
    let user = $(".second").val();
    let pass = $(".third").val();
    if (user == '') {
      $('#msg1').css({
        "display":"block",
        "color": "red",
        "font-size": "15px"
      }).html("Enter Valid username").fadeIn(500).fadeOut(8000);
      return false;
    }
    if (pass == '') {
      $('#msg1').css({
        "display":"block",
        "color": "red",
        "font-size": "15px"
      }).html("Enter Valid Password").fadeIn(500).fadeOut(8000);
      return false;
    }
    $.ajax({
      url: '/',
      type: 'POST',
      data: {
        username: user,
        password: pass
      },
      success: function (data) {
        if (data.success == true) {
          $(".modalbox").show();
          $("#formContent").css({
            opacity: 0.4
          });
          $("#user").val(data.msg);

        } else if (data.success == false) {
          $('#msg1').css({
            "display":"block",
            "color": "red",
            "font-size": "15px"
          }).html("Enter Valid Credendtials").fadeIn(500).fadeOut(8000);
        }
      },
      error(err) {
        console.log("error in login", err)
      }
    });
  })
</script>
</html>