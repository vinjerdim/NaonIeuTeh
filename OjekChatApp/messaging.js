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
    var src = req.query.source;
    var dest = req.query.destination;
    var passName = req.query.passname;
    var notifCode = req.query.notifcode;
    var query = {accountID: dest};
    console.log("messaging.js : notify > ", src);

    mongoClient.connect(url + "ojek", function (connectErr, db) {
        if (connectErr)
            throw connectErr;
        db.collection("fcm_token").find(query).toArray(function (findErr, findRes) {
            if (findErr)
                throw findErr;
            console.log("messaging.js : notify : get token > " + findRes[0].token);
            var token = findRes[0].token;
            var payload;
            if (notifCode === "1") {
                payload = {
                    notification: {
                        title: "Change page"
                    },
                    data: {
                        senderid: src,
                        name: passName,
                        code: notifCode
                    }
                };
            } else if (notifCode === "2") {
                payload = {
                    notification: {
                        title: "Reload chat"
                    },
                    data: {
                        senderid: src,
                        code: notifCode
                    }
                };
            }
            var options = {
                priority: "high",
                timeToLive: 60 * 60 * 24
            };

            messagingAdmin.sendToDevice(token, payload, options).then(function (sendRes) {
                console.log("messaging.js : notify : success > " + JSON.stringify(sendRes));
            }).catch(function (sendErr) {
                console.log("messaging.js : notify : failed > " + sendErr);
            });
            db.close();
        });
    });
});

router.get('/send', function (req, res) {
    allowCROS(res);
    var driver = req.query.driverid;
    var pass = req.query.passid;
    var message = req.query.message;
    var sender = req.query.sender;
    var query = {driverID: driver, passID: pass};

    mongoClient.connect(url + "ojek", function (connectErr, db) {
        if (connectErr)
            throw connectErr;
        db.collection("chat_history").find(query).toArray(function (queryErr, queryRes) {
            if (queryErr)
                throw queryErr;
            console.log("messaging.js : send : find message ", JSON.stringify(queryRes));
            if (queryRes.length === 0) {
                var obj = {driverID: driver, passID: pass, chat: [{id: sender, msg: message}]};
                db.collection("chat_history").insertOne(obj, function (insertErr, insertRes) {
                    if (insertErr)
                        throw insertErr;
                    console.log("message.js : new conversation > ", insertRes.result);
                    db.close();
                });
            } else {
                var temp = queryRes[0].chat;
                temp.push({id: sender, msg: message});
                db.collection("chat_history").updateOne(query, {$set: {chat: temp}}, function (updateErr, updateRes) {
                    if (updateErr)
                        throw updateErr;
                    console.log("message.js : conversation added > ", updateRes.result);
                    db.close();
                });
            }
            db.close();
        });
    });
});

router.get('/get', function (req, res) {
    allowCROS(res);
    var driver = req.query.driverid;
    var pass = req.query.passid;
    var query = {driverID: driver, passID: pass};

    mongoClient.connect(url + "ojek", function (connectErr, db) {
        if (connectErr)
            throw connectErr;
        db.collection("chat_history").find(query).toArray(function (queryErr, queryRes) {
            if (queryErr)
                throw queryErr;
            console.log("messaging.js : get : find message ", JSON.stringify(queryRes));
            if (queryRes.length >  0) {
                res.send(queryRes[0]);
            } else {
                res.send({driverID: driver, passID: pass, chat: []});
            }
        });
    });
});

module.exports = router;