<?php 

/*********All rights of this script are reserved with Sehaj Duggal, IIT Mandi*********/

function fill_weights_check($a) {

	$check = 0;
	
	for($i = 1; $i <= $a; $i++) {
	
		if(isset($_POST['weight_'.$i]) && empty($_POST['weight_'.$i])) {
			$check = 1;
			break;
		} elseif (isset($_POST['weight_'.$i]) && !empty($_POST['weight_'.$i])) {
			$check = 2;
		}
	
	}
	
	return $check;

}

function weight_is_float($a) {

	$check = 0;
	
	for($i = 1; $i <= $a; $i++) {
	
		$number = $_POST['weight_'.$i];
		$float = is_numeric($number) || is_float($number);
	
		if($float == FALSE) {
			$check = 1;
			break;
		} elseif ($float == TRUE) {
			$check = 2;
		}
	
	}
	
	return $check;

}

function weight_addition_check($a) {

	$add = 0;
	
	for($i = 1; $i <= $a; $i++) {
	
		$number = $_POST['weight_'.$i];
		$add = $add + $number;
		
	}
	
	if ($add < 1.0001 && $add > 0.9999) {
		return TRUE;
	} else {
		return FALSE;
	}	
	
}

function fill_fields_check($s, $a) {

	$check = 0;
	
	for($i = 1; $i <= $s; $i++){
		
		for($j = 1; $j <= $a; $j++) {
			if(isset($_POST[$j.'_'.$i]) && empty($_POST[$j.'_'.$i])) {
				$check = 1;
				break;
			} elseif(isset($_POST[$j.'_'.$i]) && !empty($_POST[$j.'_'.$i])) {
				$check = 2;
			}
		}
		
		if($check == 1) {
			break;
		}
	}

	return $check;
}

function fill_fields_check_interval($n, $s, $a) {

	$check = 0;
	
	for($i = 1; $i <= $s; $i++){
		
		for($j = 1; $j <= $a; $j++) {
			if(isset($_POST[$n.$j.'_'.$i]) && empty($_POST[$n.$j.'_'.$i])) {
				$check = 1;
				break;
			} elseif(isset($_POST[$n.$j.'_'.$i]) && !empty($_POST[$n.$j.'_'.$i])) {
				$check = 2;
			}
		}
		
		if($check == 1) {
			break;
		}
	}

	return $check;
}

function input_is_float($s, $a) {

	$check = 0;
	
	for($i = 1; $i <= $s; $i++){
		
		for($j = 1; $j <= $a; $j++) {
			
			$number = $_POST[$j.'_'.$i];
			$float =  is_numeric($number) || is_float($number);
			if($float == FALSE) {
				$check = 1;
				break;
			} elseif($float == TRUE) {
				$check = 2;
			}
		}
		
		if($check == 1) {
			break;
		}
	}

	return $check;

}

function input_is_float_interval($n, $s, $a) {

	$check = 0;
	
	for($i = 1; $i <= $s; $i++){
		
		for($j = 1; $j <= $a; $j++) {
			
			$number = $_POST[$n.$j.'_'.$i];
			$float =  is_numeric($number) || is_float($number);
			if($float == FALSE) {
				$check = 1;
				break;
			} elseif($float == TRUE) {
				$check = 2;
			}
		}
		
		if($check == 1) {
			break;
		}
	}

	return $check;

}



?>
