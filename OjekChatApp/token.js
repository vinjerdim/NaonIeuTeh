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

router.post('/save', function (req, res) {
    allowCROS(res);
    var fcmtoken = req.body.token;
    var id = req.body.id;
    var query = {accountID: id};
    var obj = {accountID: id, token: fcmtoken};
    mongoClient.connect(url + "ojek", function (connectErr, db) {
        if (connectErr)
            throw connectErr;
        db.collection("fcm_token").find(query).toArray(function (queryErr, queryRes) {
            if (queryErr)
                throw queryErr;
            console.log("token.js : token searched > ", queryRes.length);
            if (queryRes.length > 0) {
                db.collection("fcm_token").updateOne(query, {$set: {token: fcmtoken}}, function (updateErr, updateRes) {
                    if (updateErr)
                        throw updateErr;
                    console.log("token.js : token updated > ", updateRes.result);
                    db.close();
                });
            } else {
                db.collection("fcm_token").insertOne(obj, function (insertErr, insertRes) {
                    if (insertErr)
                        throw insertErr;
                    console.log("token.js : token added > ", insertRes.result);
                    db.close();
                });
            }
        });
    });
});

router.get('/get', function (req, res) {
    allowCROS(res);
    var id = req.query.id;
    var query = {accountID: id};
    mongoClient.connect(url + "ojek", function (connectErr, db) {
        if (connectErr)
            throw connectErr;
        db.collection("fcm_token").find(query).toArray(function (findErr, findRes) {
            if (findErr)
                throw findErr;
            console.log("token.js : get token > " + findRes);
            db.close();
        });
    });
});

module.exports = router;

