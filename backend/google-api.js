const { google } = require('googleapis');
const { logging } = require('googleapis/build/src/apis/logging');
const privatekey = require("./credentials.json");

//https://docs.google.com/spreadsheets/d/1UISEJbBe7UGYYiPw1-3VvFJF1avHzlUv9N92WbRKSyM/edit#gid=1150553803

exports.googleLogin = function(){
    const jwtClient = new google.auth.JWT(
        privatekey.client_email,
        null,
        privatekey.private_key,
        ['https://www.googleapis.com/auth/spreadsheets',
        'https://www.googleapis.com/auth/drive',
        'https://www.googleapis.com/auth/calendar']);
    //authenticate request
    jwtClient.authorize(function (err, tokens) {
    if (err) {
        console.log(err);
        return;
    } else {
    console.log("Successfully connected!");
    }
    });
    return jwtClient
}


exports.getMedicines = function(){
    const jwtClient = this.googleLogin()
    let spreadsheetId = '1UISEJbBe7UGYYiPw1-3VvFJF1avHzlUv9N92WbRKSyM';
    let sheetName = 'Medication Data!A2:A10'
    let sheets = google.sheets('v4');

    return new Promise(function(resolve,reject){
        sheets.spreadsheets.values.get({
            auth: jwtClient,
            spreadsheetId: spreadsheetId,
            range: sheetName
        }, (err, result)=> {
            if(err) return reject (err);
            else return resolve(result);
        })
    })

}

exports.setData = function(values){
    const jwtClient = this.googleLogin()
    let spreadsheetId = '1UISEJbBe7UGYYiPw1-3VvFJF1avHzlUv9N92WbRKSyM';
    //let spreadsheetId = '1_r4_br8aq4t5aWJC5xBmql8trsipFcWAapzXwenYSIg';
    //let sheetName = 'log!A2:B18';
    let sheetName = 'NAMeTA Data!A2:E255'
    let sheets = google.sheets('v4');
    console.log(values)
    return new Promise(function(resolve,reject){
        sheets.spreadsheets.values.append({
            auth: jwtClient,
            spreadsheetId: spreadsheetId,
            range: sheetName,
            valueInputOption:'USER_ENTERED',
            resource: values
        }, (err, result)=> {
            if(err) return reject (err);
            else return resolve(result);
        })
    })
}

