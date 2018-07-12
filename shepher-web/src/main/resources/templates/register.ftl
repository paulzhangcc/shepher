<!DOCTYPE html>
<html lang="en">
<#include "./header.ftl">
<body>
<#include "./nav.ftl">


<#include "./script.ftl">

<div class="container">
    <div class="col-md-3"></div>
    <div class="col-md-5">
        <form class="form-horizontal" action="/register" method="POST">
            <fieldset>
                <legend>Register</legend>
                <div class="form-group">
                    <label for="inputUsername" class="col-lg-2 control-label">Username</label>
                    <div class="col-lg-10">
                        <input type="text" class="form-control" id="inputUsername" name="username" placeholder="Username">
                    </div>
                </div>
                <div class="form-group">
                    <label for="inputPassword" class="col-lg-2 control-label">Password</label>
                    <div class="col-lg-10">
                        <input type="password" class="form-control" id="inputPassword" name="password" placeholder="Password">
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-lg-10 col-lg-offset-2">
                        <button type="submit" class="btn btn-success btn-block">Register</button>
                    </div>
                </div>

                <div class="form-group">
                    <div class="col-lg-10 col-lg-offset-2">
                        <button type="button" id="GoLogin" class="btn btn-success btn-block">GoLogin</button>
                    </div>
                </div>
            </fieldset>
        </form>
    </div>
    <div class="col-md-4"></div>
</div>
<script>
    $("#GoLogin").click(function () {
        window.location.href="/login";
    })
</script>
</body>
</html>
