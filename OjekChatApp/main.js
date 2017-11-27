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

app.post('/makevisible', function (req, res) {
    allowCROS(res);
    var registrationToken = "dAxBbkJncx8:APA91bFDNW34NSmvkko0cJc2T3ttPM2c8nBS2UkkEzyFwXj5vySz3oSMkyxpRyPxzIYCFMxmd_0WgrOPbaLKRHYc3bM6eKC-Ybm6B3eX7p3fcegK3shQsd7yA0jcJOAtY-mCN9tNQRTg";
    var payload = {
        notification: {
            title: "$GOOG up 1.43% on the day",
            body: "$GOOG gained 11.80 points to close at 835.67, up 1.43% on the day."
        }
    };
    messagingAdmin.sendToDevice(registrationToken, payload).then(function (response) {
        console.log("Successfully sent message:", response.results);
    }).catch(function (error) {
        console.log("Error sending message:", error);
    });
    res.send('OK');
});

app.post('/putfcmtoken', function (req, res) {
    allowCROS(res);
    var fcmtoken = req.body.token;
    var id = req.body.id;
    mongoClient.connect(url + "ojek", function (err, db) {
        if (err)
            throw err;
        var query = {accountID: id};
        db.collection("fcm_token").find(query, {_id: false}).toArray(function (err, result) {
            if (err)
                throw err;
            if (result.length === 1) {
                db.collection("customers").updateOne(query, {accountID: id, token: fcmtoken}, function (err, res) {
                    if (err)
                        throw err;
                    console.log("1 document updated " + fcmtoken);
                    db.close();
                });
                res.end();
            } else {
                db.collection("fcm_token").insertOne({accountID: id, token: fcmtoken}, function (err, res) {
                    if (err)
                        throw err;
                    console.log("1 document inserted");
                    db.close();
                });
                res.end();
            }
        });
    });
});

app.get('/getfcmtoken', function (req, res) {
    allowCROS(res);
    var id = req.query.id;
    var query = {accountID: id};
    mongoClient.connect(url + "ojek", function (err, db) {
        if (err)
            throw err;
        db.collection("fcm_token").find(query, {_id: false}).toArray(function (err, result) {
            if (err)
                throw err;
            console.log(result);
            res.end();
        });
    });
});

app.use('/findorder', findorder);

app.use('/order', order);

app.use('/', mongoHandler);

console.log("Listening on Port 3000");
app.listen(3000);