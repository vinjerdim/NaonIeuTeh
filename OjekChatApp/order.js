/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var express = require('express');
var mysql = require('mysql');
var router = express.Router();
var conn = mysql.createConnection({
       host: 'localhost',
       user: 'root',
       password: '',
       database: 'ojek_online'
    });


router.get('/', function(req,res){
    var request = require('request');
    var driver = req.param('pref-driver');
    var location = req.param('pick-point');
    var destination = req.param('destination');
    var options = {
        url: 'localhost:8080/OjekOnlineApp/ShowDriverList/?pref-driver=' + driver  + '&pick-point' + location + '&destination' + destination,
        method: 'GET'  
    };
    
    request(options, function(err, res, body) {});
//    
//    var sql1 = "SELECT account_id FROM driver_ready NATURAL JOIN pref_location WHERE location=" + location + ";";
//    conn.query(sql1, function(err,result) {
//        if (err) throw err;
//        JSON.stringify(result);
//        
//        result.forEach(function(driver) {
//            var driver_id = driver.account_id;
//            console.log(driver_id);
//        });
//    });
});

module.exports = router;