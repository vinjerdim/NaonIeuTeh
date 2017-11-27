/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var express = require('express');
var router = express.Router();
var mysql = require('mysql');
var conn = mysql.createConnection({
    host: 'localhost',
    user: 'root',
    password: '',
    database: 'ojek_account'
});

function allowCROS(res) {
    res.header('Access-Control-Allow-Origin', "*");
    res.header('Access-Control-Allow-Methods', 'GET,PUT,POST,DELETE');
    res.header('Access-Control-Allow-Headers', 'Content-Type');
}

router.get('/', function (req, res) {
    console.log("get through findorder route!");
});

router.get('/find', function (req, res) {
    allowCROS(res);
    var account_id = req.param('accountid');
    var sql = "UPDATE account SET driver_ready = '1' WHERE account_id = '" + account_id + "'";
    conn.query(sql, function (err, result) {
        if (err)
            throw err;
        console.log("Result: " + result);
        res.send(account_id + " " + req.param('accountid'));
    });
});

router.get('/cancel', function (req, res) {
    allowCROS(res);
    var account_id = req.param('accountid');
    var sql = "UPDATE account SET driver_ready = '0' WHERE account_id = '" + account_id + "'";
    conn.query(sql, function (err, result) {
        if (err)
            throw err;
        console.log("Result: " + result);
        res.send(account_id + " " + req.param('accountid'));
    });
});

module.exports = router;