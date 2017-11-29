/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
var express = require('express');
var router = express.Router();
var mongoClient = require('mongodb').MongoClient;
var url = "mongodb://localhost:27017/";

function allowCROS(res) {
    res.header('Access-Control-Allow-Origin', "*");
    res.header('Access-Control-Allow-Methods', 'GET,PUT,POST,DELETE');
    res.header('Access-Control-Allow-Headers', 'Content-Type');
}

router.post('/putfcmtoken', function (req, res) {
    allowCROS(res);
    var fcmtoken = req.body.token;
    var id = req.body.id;
    mongoClient.connect(url + "ojek", function (err, db) {
        if (err)
            throw err;
        var query = {accountID: id};
        db.collection("fcm_token").updateOne(query, {$set: {token: fcmtoken}}, function (err, res) {
            if (err)
                throw err;
            if (res.modifiedCount === 0) {
                var obj = {accountID: id, token: fcmtoken};
                db.collection("fcm_token").insertOne(obj, function (error, response) {
                    if (error)
                        throw error;
                    console.log("" + obj + " inserted");
                    db.close();
                });
            } else {
                console.log("Token " + id + " updated");
                db.close();
            }
        });
    });
});

router.get('/getfcmtoken', function (req, res) {
    allowCROS(res);
    var id = req.query.id;
    console.log(id);
    res.end();
});

module.exports = router;

