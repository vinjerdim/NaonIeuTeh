/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
var express = require('express');
var mongoClient = require('mongodb').MongoClient;
var url = "mongodb://localhost:27017/";
var router = express.Router();

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

router.get('/notify', function (req, res) {
    allowCROS(res);
    var driverID = req.query.driverid;
    var passID = req.query.passid;
    var passName = req.query.passname;
    var query = {accountID: driverID};
    console.log("order.js : notify > ", driverID);
    mongoClient.connect(url + "ojek", function (connectErr, db) {
        if (connectErr)
            throw connectErr;
        db.collection("fcm_token").find(query).toArray(function (findErr, findRes) {
            if (findErr)
                throw findErr;
            console.log("messaging.js : get token > " + findRes[0].token);
            var token = findRes[0].token;
            var payload = {
                notification: {
                    title: "Got notif from " + passID,
                },
                data: {
                    id : passID,
                    name : passName
                }
            };

            var options = {
                priority: "high",
                timeToLive: 60 * 60 * 24
            };
            
            messagingAdmin.sendToDevice(token, payload, options)
                    .then(function (sendRes) {
                        console.log("messaging.js : driver notified > " + JSON.stringify(sendRes));
                    }).catch(function (sendErr) {
                console.log("messaging.js : notify failed > " + sendErr);
            });
            db.close();
        });
    });
});

module.exports = router;