const express = require('express')
const router = express.Router();

const controller = require('../controllers/patientCtrl');
const userVerification = require('../middlewares/userVerification')

router.get("/get",userVerification,controller.getPatientProfile);


module.exports = router;