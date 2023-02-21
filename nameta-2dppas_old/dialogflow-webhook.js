const express = require('express')
const bodyParser = require('body-parser')
const sheets = require('./google-api')

const app = express()

app.use(express.json())
app.use(express.urlencoded({ extended: true }))

app.post('/', (req,res,next)=>{
  console.log(req.body.queryResult.action)
  console.log(req.body.queryResult.allRequiredParamsPresent)
  if (req.body.queryResult.action === "set_medicine" && req.body.queryResult.allRequiredParamsPresent){

    var amount = req.body.queryResult.parameters.amount
    var type  = req.body.queryResult.parameters.type
    var brand_name = req.body.queryResult.parameters.brand_name
    var unit_weight_amount = req.body.queryResult.parameters.unit_weight.amount
    var unit_weight_unit = req.body.queryResult.parameters.unit_weight.unit 
    var name = req.body.queryResult.parameters.name

    let values = {
        "majorDimension": "ROWS",
        "values": [
          [new Date(Date.now()), amount, type,brand_name, name,  unit_weight_amount, unit_weight_unit],
        ]
      }
    sheets.setData(values)
    res.status(201).send({
      fulfillmentText: 'Adicionado: '+ amount + " " + type + " " + brand_name + " " + name + "" + unit_weight_amount+unit_weight_unit ,
      source: 'nameta-backend'
    });
  }
  else {
        res.status(400).send();
      }
});

app.get('/', function (req, res) {
  res.satus(200).send('NAMeTA RUNNING ..')})

app.listen(process.env.PORT || 3000, ()=>{
  console.log(`server is running on port ${process.env.PORT||3000}`)
})