<?php

class Wrapper{

 function __construct(){
	global $_SESSION;
	$this->errors=[];
	$this->success=true;
	$this->data=[]; 

 }
 function error($text){
  if(""!=$text){
   $this->errors[]=$text;
  }
  $this->success=false;
 }
 function addKey($k,$v){

  $this->data[$k]=$v;
 }
 function getJSON(){

  $wrap=array('errors'=>$this->errors,'success'=>$this->success,'data'=>$this->data);
  return json_encode($wrap);
 }

}
