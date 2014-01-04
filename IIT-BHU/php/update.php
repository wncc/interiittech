<?php
	$username = "root";
	$password = "";
	$hostname = "localhost";

	$dbhandle = mysql_connect($hostname, $username, $password) 
  		or die("Unable to connect to MySQL");
	echo "Connected to MySQL<br>";
	
	$selected = mysql_select_db("parking",$dbhandle) 
  		or die("Could not select examples");
	
	$sql = 'INSERT INTO names '.
       		'(name,total,avail,latitude,longitude) '.
       		'VALUES ('$_POST[name]','$_POST[total]','$_POST[avail]','$_POST[latitude]','$_POST[longitude]')';
	
	$retval = mysql_query($sql);
	if(! $retval ){
		die('Could not enter data: ' . mysql_error());
	}
	//echo "Entered data successfully\n";
?>
