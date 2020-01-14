<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Reset Password</title>

    <link href="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
<script src="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>
<style>
</style>
</head>
<body>
    <div class="container" >
        <div class="row">
            <div class="col-sm-4" style="margin: auto;">
                
                <label>Current Password</label>
                <div class="form-group pass_show"> 
                    <input type="password" value="" id="current_password" class="form-control" placeholder="Current Password"> 
                </div> 
                   <label>New Password</label>
                <div class="form-group pass_show"> 
                    <input type="password" value="" id="password1" class="form-control" placeholder="New Password"> 
                </div> 
                   <label>Confirm Password</label>
                <div class="form-group pass_show"> 
                    <input type="password" value="" id="password2" class="form-control" placeholder="Confirm Password"> 
                </div> 
                <div class="form-group pass_show"> 
                    <input type="button" onclick="change_password()" value="Save" class="form-control bg-btn bg-info" > 
                </div>
            </div>  
        </div>
    </div>
</body>
<script src="/javascripts/jquery.min.js"></script>
<script>
function change_password(){
  pass1=  $("#password1").val();
  pass2= $("#password2").val();
  current_password =$("#current_password").val();
  if(pass1==''){
      alert("Enter password");
      exit();
  }
  if(pass2==''){
      alert("Enter confirm password");
      exit();
  }
  if(current_password==''){
      alert("Enter Current password");
      exit();
  }
  if(pass1==pass2){
     $.ajax({
      url:'/reset_password',
      type:'POST',
      data:{
          password:pass1,
          current_password:current_password
      },
      success:function(data){
          if(data==true){
           window.location.href='/'
          }        
      },error:function(err){
        console.log("errror",err)
      }
  })
}else{
   alert("password not match");
  }
}
</script>
</html>