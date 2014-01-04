<?php 

require 'includes/DB_CONNECT.php';

db_dc(1);
$sql1 = mysql_query("SELECT * FROM bloodbank");
$rows = mysql_num_rows($sql1);
for($i = 1; $i <= $rows; $i++) {
	$arr = mysql_fetch_array($sql1);
	$name = $arr['name'];
	$location = $arr['address'];
	echo "$i).  $name<br>$location<br><br>";
}
db_dc(0);
?>
