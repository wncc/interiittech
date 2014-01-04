<?php 

//Creating db connection function
//db connect if $conn_or_disconn == 1 else db closed
 
function db_dc($conn_or_disconn) {

	require 'configs.php';
	if ($conn_or_disconn == 1) {
		if(!mysql_connect(DB_HOST, DB_USER, DB_PASSWORD) || !mysql_select_db(DB_DATABASE)) {
			die(mysql_error());
			} 
	} else {
		mysql_close();
	}

}



?>