/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function requestMessagingPermission(messaging) {
    messaging.requestPermission().then(function () {
        console.log('Notification permission granted.');
    }).catch(function (err) {
        console.log('Unable to get permission to notify.', err);
    });
}

function getMessagingToken(messaging, accountID) {
    if ('serviceWorker' in navigator) {
        navigator.serviceWorker.register('firebase-messaging-sw.js').then(function (registration) {
            messaging.useServiceWorker(registration);
            messaging.getToken().then(function (currentToken) {
                if (currentToken) {
                    console.log(currentToken);
                    $.post("http://localhost:3000/token/save", {id: accountID, token: currentToken},
                            function (data, status) {
                                console.log(data);
                                console.log(status);
                            });
                } else {
                    console.log('No Instance ID token available. Request permission to generate one.');
                }
            }).catch(function (err) {
                console.log('An error occurred while retrieving token. ', err);
            });
        }, function (err) {
            console.log('ServiceWorker registration failed: ', err);
        });
    } else {
        console.log('ServiceWorker not supported');
    }
}

function listenForTokenRefresh(messaging, accountID) {
    messaging.onTokenRefresh(function () {
        getMessagingToken(messaging, accountID);
    });
}

function listenForMessage(messaging) {
    messaging.onMessage(function (payload) {
        console.log("Message received ", payload);
    });
}

