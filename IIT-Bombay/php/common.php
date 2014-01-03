<?php
session_start();
include 'config.inc.php';
include 'wrapper.class.php';
$dbLink=new PDO("mysql:host=".$config['dbHost'].";dbname=".$config['dbName'],$config['dbUser'],$config['dbPass']);


