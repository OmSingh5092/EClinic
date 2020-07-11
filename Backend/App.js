const express=  require("express");
const app = express();

//Applying middlewares

app.use(express.json());

//Importing routes
const patient = 
