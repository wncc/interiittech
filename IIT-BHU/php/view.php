<?php
	$username = "root";
	$password = "";
	$hostname = "localhost";

	$dbhandle = mysql_connect($hostname, $username, $password) 
  		or die("Unable to connect to MySQL");
	//echo "Connected to MySQL<br>";
	
	$selected = mysql_select_db("parking",$dbhandle) 
  		or die("Could not select examples");
	
	$result = mysql_query("SELECT * FROM names");
	$rows = array();
	while ($row = mysql_fetch_array($result)) {
   		$rows[] = $row;
	}
	print json_encode($rows);
?>
