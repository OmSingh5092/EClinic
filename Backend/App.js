const express = require("express");
const config = require("./config").app;
const database = require("./database/database");
const app = express();

//Applying middlewares
app.use(express.json());

//Using Passport
require("./passport/passport");

//Importing routes
const appointmentRouter = require("./routes/appointment");
const doctorRouter = require("./routes/doctor");
const patientRouter = require("./routes/patient");
const googleLogin = require("./routes/googleLogin");

app.use("/api/google", googleLogin);
app.use("/api/patient", patientRouter);
app.use("/api/appointment", appointmentRouter);
app.use("/api/doctor", doctorRouter);

app.get("/", (req, res) => {
  return res.status(200).json({
    success: true,
    message: "APIs are working fine!",
  });
});

//Listening
app.listen(config.local.port, () => {
  console.log("\n\n App listening... \n\n");
});
