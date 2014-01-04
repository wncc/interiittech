<?php
	$username = "a9919284_parking";
	$password = "erika1";
	$hostname = "mysql6.000webhost.com";

	$dbhandle = mysql_connect($hostname, $username, $password) 
  		or die("Unable to connect to MySQL");
	echo "Connected to MySQL<br>";
	
	$selected = mysql_select_db("a9919284_parking",$dbhandle) 
  		or die("Could not select examples");
        $total =  intval($_POST[total]);
        $avail  = intval($_POST[avail]);
        $latitude = doubleval($_POST[latitude]);
        $longitude  = doubleval($_POST[longitude]);
	
        
        $sql="INSERT INTO names (name, total, avail, latitude, longitude)
              VALUES
             ('$_POST[name]','$total','$avail','$latitude','$longitude')";
	
	$retval = mysql_query($sql);
	if(! $retval ){
		die('Could not enter data: ' . mysql_error());
	}
	echo "Entered data successfully\n";
?>
