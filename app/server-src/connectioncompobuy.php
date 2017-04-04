<?php

class BD {
    
    function __construct()
    { 
        $this->pdo = new PDO('mysql:host=localhost;dbname=Xmalboniga002_compobuy;charset=utf8', 'Xmalboniga002', 'WgFfgTuP');
    }

    function login($nombre,$pass){
        $query='select nombre from usuario where nombre=:nombre and pass=:pass';
        $sth = $this->pdo->prepare($query);
        $sth->bindParam(':nombre', $nombre, PDO::PARAM_STR);
        $sth->bindParam(':pass', $pass, PDO::PARAM_STR);
        $sth->execute();
        return $sth->fetch(PDO::FETCH_ASSOC);
    }

    function register($nombre,$pass,$fechanacimiento,$ciudad){
        $query='insert into usuario(nombre,pass,fechanacimiento,ciudad) values(:nombre,:pass,:fechanacimiento,:ciudad)';
        $sth = $this->pdo->prepare($query);
        $sth->bindParam(':nombre', $nombre, PDO::PARAM_STR);
        $sth->bindParam(':pass', $pass, PDO::PARAM_STR);
        $sth->bindParam(':ciudad', $ciudad, PDO::PARAM_STR);
        $sth->bindParam(':fechanacimiento', $fechanacimiento, PDO::PARAM_STR);
        if($sth->execute()){
            return array('nombre'=>$nombre);
        }
        else{
            return array('error'=> "error");
        }
    }

    function getproducto($idcomp){
        $query='select * from componente where idcomp=:idcomp';
        $sth = $this->pdo->prepare($query);
        $sth->bindParam(':idcomp', $idcomp, PDO::PARAM_INT);
        $sth->execute();
        return $sth->fetch(PDO::FETCH_ASSOC);
    }

    function getcatproduct($nombre){
        $query='select componente.idcomp,componente.nombre from componente,categoria where categoria.nombre=:nombre and categoria.idcat=componente.idcat';
        $sth = $this->pdo->prepare($query);
        $sth->bindParam(':nombre', $nombre, PDO::PARAM_STR);
        $sth->execute();
        return $sth->fetchAll(PDO::FETCH_ASSOC);
    }

    function getallproduct(){
        $query='select idcomp,nombre from componente';
        $sth = $this->pdo->prepare($query);
        $sth->execute();
        return $sth->fetchAll(PDO::FETCH_ASSOC);
    }

    function getallcategoria(){
        $query='select nombre from categoria';
        $sth = $this->pdo->prepare($query);
        $sth->execute();
        return $sth->fetchAll(PDO::FETCH_ASSOC);
    }

    function getcarritousu($nombre){
        $query='select componente.nombre,componente.precio,usu_comp.id from usu_comp,componente where usu_comp.nombre=:nombre and usu_comp.idcomp=componente.idcomp';
        $sth = $this->pdo->prepare($query);
        $sth->bindParam(':nombre', $nombre, PDO::PARAM_STR);
        $sth->execute();
        return $sth->fetchAll(PDO::FETCH_ASSOC);
    }

    function deletecarrito($nombre,$id){
        $query='delete from usu_comp where nombre=:nombre and id=:id';
        $sth = $this->pdo->prepare($query);
        $sth->bindParam(':nombre', $nombre, PDO::PARAM_STR);
        $sth->bindParam(':id', $id, PDO::PARAM_INT);
        if($sth->execute()){
            return array('status'=> "ok");
        }
        else{
            return array('status'=> "error");
        }
    }

    function deleteallcarrito($nombre){
        $query='delete from usu_comp where nombre=:nombre';
        $sth = $this->pdo->prepare($query);
        $sth->bindParam(':nombre', $nombre, PDO::PARAM_STR);
        if($sth->execute()){
            return array('status'=> "ok");
        }
        else{
            return array('status'=> "error");
        }
    }

    function addcarrito($nombre,$idcomp){
        $query='insert into usu_comp(nombre,idcomp) values(:nombre,:idcomp)';
        $sth = $this->pdo->prepare($query);
        $sth->bindParam(':nombre', $nombre, PDO::PARAM_STR);
        $sth->bindParam(':idcomp', $idcomp, PDO::PARAM_INT);
        if($sth->execute()){
            return array('status'=> "ok");
        }
        else{
            return array('status'=> "error");
        }
    }
    
}

