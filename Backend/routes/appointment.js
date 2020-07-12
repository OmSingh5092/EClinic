const express = require('express')
const router = express.Router();

const userVerification = require('../middlewares/userVerification')

const controller = require('../controllers/appointmentCtrl');
const validate = require('../middlewares/validate').appointmentValidation;

router.post('/add',userVerification ,controller.addAppointment);
router.post('/update',userVerification,controller.updateAppointment);
router.get('/get',userVerification ,controller.getAppointment)
router.get('/get/doctor',userVerification,controller.getAppointmentDoctor);
router.delete('/delete',userVerification,controller.deleteAppointment);

module.exports = router;