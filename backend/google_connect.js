let { google } = require('googleapis');
let privatekey = require("./credentials.json");

// configure a JWT auth client
let jwtClient = new google.auth.JWT(
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

//Google Sheets API
let spreadsheetId = '1_r4_br8aq4t5aWJC5xBmql8trsipFcWAapzXwenYSIg';
let sheetName = 'dados!A2:B18'
let sheets = google.sheets('v4');
sheets.spreadsheets.values.get({
   auth: jwtClient,
   spreadsheetId: spreadsheetId,
   range: sheetName
}, function (err, response) {
   if (err) {
       console.log('The API returned an error: ' + err);
   } else {
        console.log(response.data.values)
   }
});

let values = {
    "majorDimension": "ROWS",
    "values": [
      ["Teste3", "Teste3@gmail.com"],
    ]
  }

sheets.spreadsheets.values.append({
    auth: jwtClient,
    spreadsheetId: spreadsheetId,
    range: 'log!A1:B255',
    valueInputOption:'USER_ENTERED',
    resource: values
 }, function (err, response) {
    if (err) {
        console.log('The API returned an error: ' + err);
    } else {
        console.log(response);
    }
 });
 
 