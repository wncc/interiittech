<?php 

require 'includes/DB_CONNECT.php';

db_dc(1);
$sql1 = mysql_query("SELECT * FROM bloodcamp WHERE active = '1'");
$rows = mysql_num_rows($sql1);
for($i = 1; $i <= $rows; $i++) {
	$arr = mysql_fetch_array($sql1);
	$name = $arr['organizer'];
	$location = $arr['place'];
	$date = $arr['date'];
	$time_start = $arr['timestart'];
	$time_end = $arr['timeend'];
	echo "$i).  $name<br>at $location<br>on $date<br>from $time_start to $time_end<br><br>";
}
db_dc(0);
?>
