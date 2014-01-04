<?php
 if (!empty($_POST))
 {
$obj = json_decode($_POST['mydata']);

$blood=$obj->{'blood'};
$user_name=$obj->{'name'};
$place=$obj->{'place'};
$lang=$obj->{'lang'};
$lat=$obj->{'lat'};
$contact=$obj->{'contact'};
$personal=$obj->{'personal'};
$email=$obj->{'email'};
$bday=$obj->{'bday'};
$last=$obj->{'last'};
$uuid=$obj->{'uuid'};
 }
 else
 {
 echo ("false");
 }
$dbhost = 'localhost';
$dbuser = 'root';
$dbpass = 'qwerty';
$conn = mysql_connect($dbhost, $dbuser, $dbpass);
if(! $conn )
{

      die('Could not connect: ' . mysql_error());                               

}
$sql ="INSERT INTO `donor` ( `name`,`blood`,`place`,`lat`,`lang`,`contact`,`email`,`personal`,`bday`,`last_date`,`uuid` )  values ('$user_name','$blood','$place','$lat','$lang','$contact','$email','$personal','$bday','$last','$uuid')";

mysql_select_db('my_app');
//echo ($sql);
$retval = mysql_query( $sql, $conn );
if(! $retval )
{
  die('Could not enter data: ' . mysql_error());
}
//echo ("\nEntered data successfully\n");
mysql_close($conn);
?>

