const express = require('express')
const router = express.Router();

const controller = require('../controllers/doctorCtrl');
const userVerification = require('../middlewares/userVerification')

router.get("/get",userVerification,controller.getDoctorProfile);
router.post('/update',userVerification,controller.updateDoctorProfile);


module.exports = router;