const express=  require("express");
const config = require('./config').app;
const database = require('./database/database')
const app = express();

//Applying middlewares
app.use(express.json());

//Using Passport
require('./passport/passport');

//Importing routes
const appointmentRouter = require('./routes/appointment');
const doctorRouter = require('./routes/doctor');
const patientRouter = require('./routes/patient');
const googleLogin = require('./routes/googleLogin')

app.use("/api/google",googleLogin);
app.use("/api/patient",patientRouter);

//Listening 

database.au
app.listen(config.local.port,()=>{
    console.log("\n\n App listening... \n\n");
})


