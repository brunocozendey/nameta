const express = require('express')
const bodyParser = require('body-parser')
const { conversation } = require('@assistant/conversation')
const sheets = require('./google-api')

function nullTest(item,index) {
  if (!item[index]) return item[index] == "";
}
// Create an app instance
const app = conversation()

// Register handlers for Actions SDK
/* 
app.handle('greeting', conv => {
  
    var texto = ""
  conv.add('Lista de medicamentos cadastrada:')
  */
  sheets.getMedicines().then(res=>{
    app.handle('greeting', conv => {
      conv.add('Lista de medicamentos cadastrada:'+ res.data.values)
  })

  //console.log("listas",sheets.list())
  //for (let row of listas) {
    //conv.add(row[0])
    //console.log(row[0])
  //}
})

app.handle('remedio', conv => {
    console.log("OK")
    console.log("Req_assistant",conv.intent)
    try{
      var unit_weight = conv.intent.params.unit_weight.resolved
      var brand_name = conv.intent.params.brand_name.resolved
      var amount_medicine = conv.intent.params.amount_medicine.resolved
      var medicine_type = conv.intent.params.type.resolved
    }
    catch(err){
      console.log(err)
    }

    let values = {
        "majorDimension": "ROWS",
        "values": [
          [amount_substance, unit_weight,brand_name, amount_medicine, medicine_type],
        ]
      }
    sheets.setData(values)
    conv.add('Adicionado: '+ unit_of_measure + " " + brand_name + " " + amount_medicine + " " + type )

  })

const expressApp = express().use(bodyParser.json())

expressApp.post('/', app)
expressApp.get('/', function (req, res) {
  res.send('NAMeTA RUNNING ..')})

expressApp.listen(process.env.PORT || 3000)