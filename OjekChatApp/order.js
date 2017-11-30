/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
var express = require('express');
var mysql = require('mysql');
var request = require('request-promise');
var router = express.Router();

function allowCROS(res) {
    res.header('Access-Control-Allow-Origin', "*");
    res.header('Access-Control-Allow-Methods', 'GET,PUT,POST,DELETE');
    res.header('Access-Control-Allow-Headers', 'Content-Type');
}

router.get('/', function (req, res) {
    allowCROS(res);
    var options = {
        uri: 'http://localhost:8080/OjekOnlineApp/ShowDriverList',
        method: 'GET',
        qs : {
            'pick-point' : req.param('pick-point'),
            'destination' : req.param('destination'),
            'pref-driver' : req.param('pref-driver')
        }
    };

    request(options).then(function (response) {
        res.send(response);
    }).catch(function (error) {
        console.log("order.js : Show driver error", error);
        res.sendStatus(404);
    });
});

module.exports = router;