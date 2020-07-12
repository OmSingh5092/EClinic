const googleCtrl = require('../controllers/googleCtrl');

const express = require('express');
const router = express.Router();

router.post("/patient",googleCtrl.patientSignIn);
router.post("/doctor",googleCtrl.doctorSignIn);

module.exports = router;