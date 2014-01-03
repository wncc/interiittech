<?php

include 'common.php';


switch ($_REQUEST['action']){

 case "createaccount":
  echo createAccount($_REQUEST['username'],$_REQUEST['password1'],$_REQUEST['password2']);
 break;
 case 'login':
  echo login($_REQUEST['username'],$_REQUEST['password']);
  break;
 case 'currentuser':
  $wrap=new Wrapper();
  $wrap->addKey('username',$_SESSION['user']);
  echo $wrap->getJSON();
  break;
 case 'post':
  echo post();
 default:
  $wrap=new Wrapper();
  $wrap->error("Method not implemented");
  echo $wrap->getJSON();
 
 }

function post(){
	global $dbLink,$config;
	$wrap-new Wrapper();
	if($_REQUEST['title']==""){
		$wrap->error("Please enter a title");
	}
	if($_REQUEST['description']==""){
		$wrap->error("Please enter a title");
	}
	if($_REQUEST['lat']=="" || $_REQUEST['long']==""){
		$wrap->error("Coordinates not set");
	}
}
function login($u,$p){
	global $_SESSION;
	$wrap=new Wrapper();
	$st=$dbLink->prepare("select username from users where username=:u and password=MD5(:p)");
	$st->execute(array('u'=>$u,'p'=>$p));
	if($st->rowCount()>0){
		$_SESSION['user']=$u;
		$wrap->addKey('username',$u);
		return $wrap->getJSON();
	}
	$wrap->error("Bad username or password");
	return $wrap->getJSON();
}

function createAccount($user,$p1,$p2){
	global $dbLink;
	$wrap=new Wrapper();
	if($p1!=$p2){
		$wrap->error("Passwords do not match");
	}
	if(strlen($user)<6){
		$wrap->error("Username must have at least 6 characters");
	}
	if(strlen($p1)<6){
		$wrap->error("Password must have at least 6 characters");
	}
	
	if($wrap->success){
		$st=$dbLink->prepare("select username from users where username=:u");
		$st->execute(array('u'=>$user));
		if($st->rowCount()>0){
			$wrap->error("Username already exists");
			return $wrap->getJSON();
		}
		$dbLink->prepare("insert into users(username,password) values(:u,MD5(:p))")->execute(array('u'=>$user,'p'=>$p1));

	}
	return $wrap->getJSON();
}
