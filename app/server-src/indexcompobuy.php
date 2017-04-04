<?php
if (isset($_POST['func'])) {
	require_once "connectioncompobuy.php";
	$bd=new BD();
	switch ($_POST['func']) {
		case 'login':
			$json=$bd->login($_POST['nombre'],$_POST['pass']);
			if (!isset($json['nombre'])) {
				$json = array('error' => "error" );
			}
			break;
		case 'register':
			$json=$bd->register($_POST['nombre'],$_POST['pass'],$_POST['fechanacimiento'],$_POST['ciudad']);
			break;
		case 'getproducto':
			$json=$bd->getproducto($_POST['idcomp']);
			if (!isset($json['nombre'])) {
				$json = array('error' => "error" );
			}
			break;
		case 'getcatproduct':
			$json=$bd->getcatproduct($_POST['nombre']);
			if (!isset($json[0])) {
				$json = array('error' => "error" );
			}
			break;
		case 'getallproduct':
			$json=$bd->getallproduct();
			if (!isset($json[0])) {
				$json = array('error' => "error" );
			}
			break;
		case 'getallcategoria':
			$json=$bd->getallcategoria();
			if (!isset($json[0])) {
				$json = array('error' => "error" );
			}
			break;
		case 'getcarritousu':
			$json=$bd->getcarritousu($_POST['nombre']);
			if (!isset($json[0])) {
				$json = array('error' => "error" );
			}
			break;
		case 'deletecarrito':
			$json=$bd->deletecarrito($_POST['nombre'],$_POST['id']);
			break;
		case 'deleteallcarrito':
			$json=$bd->deleteallcarrito($_POST['nombre']);
			break;
		case 'addcarrito':
			$json=$bd->addcarrito($_POST['nombre'],$_POST['idcomp']);
			break;
		default:
			$json="";
			break;
	}
	echo json_encode($json);
}
?>