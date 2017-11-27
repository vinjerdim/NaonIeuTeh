/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
var express = require('express');
var router = express.Router();
var mongoClient = require('mongodb').MongoClient;
var url = "mongodb://localhost:27017/";

router.post('/putfcmtoken', function (req, res) {
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
                db.collection("customers").updateOne(query, {$set: {token:fcmtoken}}, function (err, res) {
                    if (err)
                        throw err;
                    console.log("1 document updated");
                    db.close();
                });
            } else {
                db.collection("fcm_token").insertOne({accountID: id, token: fcmtoken}, function (err, res) {
                    if (err)
                        throw err;
                    console.log("1 document inserted");
                    db.close();
                });
            }
        });
    });
});

module.exports = router;

