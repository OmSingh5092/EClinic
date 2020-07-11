const googleCtrl = require('../controllers/googleCtrl');

const express = require('express');
const router = express.Router();


router.post("/google/patient",googleCtrl.patientSignIn);
router.post("/google/doctor",googleCtrl.doctorSignIn);

module.exports = router;