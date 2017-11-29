/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
var express = require('express');
var app = express();
var mongoHandler = require('./mongoHandler');
var order = require('./order');
var findorder = require('./findorder');

var mongoClient = require('mongodb').MongoClient;
var url = "mongodb://localhost:27017/";
var bodyParser = require('body-parser');

app.use(bodyParser.json());

app.use(bodyParser.urlencoded({extended: true}));

var admin = require('firebase-admin');
var key = require('./serviceAccountKey');

admin.initializeApp({
    credential: admin.credential.cert(key),
    databaseURL: "https://naonieuteh-1c736.firebaseio.com"
});

var messagingAdmin = admin.messaging();

function allowCROS(res) {
    res.header('Access-Control-Allow-Origin', "*");
    res.header('Access-Control-Allow-Methods', 'GET,PUT,POST,DELETE');
    res.header('Access-Control-Allow-Headers', 'Content-Type');
}

app.get('/', function (req, res) {
    allowCROS(res);
    res.send("Hello");
    console.log("accessed");
});

app.use('/findorder', findorder);

app.use('/order', order);

app.use('/', mongoHandler);

console.log("Listening on Port 3000");
app.listen(3000);